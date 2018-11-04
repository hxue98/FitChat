package fitchat.com.fitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class DiscussionActivity extends AppCompatActivity {

    private CollectionReference currentToSelected, selectedToCurrent;
    private String currentUid,selectedUid;
    List<Message> array;
    boolean doneLoad = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        Button send = findViewById(R.id.user_chat_send);
        View recyclerView = findViewById(R.id.history_list);
        assert recyclerView != null;
        EditText textField = findViewById(R.id.user_chat_textfield);
        array = new ArrayList<>();
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Model.getInstance().getCurrentSelectedUser().getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    selectedUid = (String) document.get("uid");
                }
                while(selectedUid == null) {}
                Log.d("Disccusion", "Loop");
                currentToSelected = Model.getInstance().getCurrentUser().getReference().collection("Chat").
                        document(selectedUid).collection("History");
                selectedToCurrent = Model.getInstance().getCurrentSelectedUser().getReference().collection("Chat").
                        document(currentUid).collection("History");
                currentToSelected.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("Chat", "Database failure");
                        } else {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Message m = new Message((String) doc.get("text"), (Date) doc.get("time"));
                                if (!array.contains(m))
                                    array.add(m);                                Collections.sort(array);
                                Log.d("Discussion", (String) doc.get("text"));
                            }
                        }
                        setupRecyclerView((RecyclerView) recyclerView);
                        ((RecyclerView) recyclerView).scrollToPosition(array.size()-1);

                    }
                });
                selectedToCurrent.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("Chat", "Database failure");
                        } else {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Message m = new Message((String) doc.get("text"), (Date) doc.get("time"));
                                if (!array.contains(m))
                                    array.add(m);
                                Collections.sort(array);
                                Log.d("Discussion", (String) doc.get("text"));
                            }
                        }
                        doneLoad = true;
                        ((RecyclerView) recyclerView).scrollToPosition(array.size()-1);
                    }
                });
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = Model.getInstance().getCurrentUser().getName() + ": " + textField.getText().toString();
                textField.setText("");
                Map<String, Object> message = new HashMap<>();
                message.put("time", Timestamp.now());
                message.put("text", text);
                Model.getInstance().getCurrentUser().getReference().collection("Chat").document(selectedUid).collection("History")
                        .add(message);
                ((RecyclerView) recyclerView).scrollToPosition(array.size()-1);
            }
        });


        //Step 2.  Hook up the adapter to the view

//        ArrayList<Date> array = new ArrayList<>(history.keySet());
//        Collections.sort(array, Comparator.naturalOrder());
//        ArrayList<String> texts = new ArrayList<>();
//        for (Date time : array) {
//            texts.add(history.get(time));
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.history_list, texts);
//        listView.setAdapter(adapter);
    }

    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        //if we are display a searched list, or an item list at location
        Collections.sort(array);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(array));
        recyclerView.scrollToPosition(array.size()-1);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Location object to a text field.
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Message> mMessages;

        /**
         * set the items to be used by the adapter
         *
         * @param messages the list of items to be displayed in the recycler view
         */
        public SimpleItemRecyclerViewAdapter(List<Message> messages) {
            mMessages = messages;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/location_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.message.setText(mMessages.get(position).toString());
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a location in the other.
             */
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Item) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView message;
            public Message mMessage;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                message = view.findViewById(R.id.history_list_string);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }
    }
}
