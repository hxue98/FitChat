package fitchat.com.fitchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<User> pairingUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final User user = new User();
        DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    user.setAge((Long) document.get("age"));
                    user.setName((String) document.get("name"));
                    user.setWeight((Double) document.get("weight"));
                    user.setGender((String) document.get("gender"));
                    user.setFavoriteExercise((String) document.get("favoriteExercise"));
                    user.setLatitude(0);
                    user.setLongitude(0);

                }
            }
        });
        Model.getInstance().setCurrentUser(user);
        ImageButton search = findViewById(R.id.radar_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //final ArrayList<User> user = new ArrayList<>(1);
        final View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching...", 1000)
                        .setAction("Action", null).show();
                pairingUsers.clear();
                FirebaseFirestore.getInstance().collection("Users").whereEqualTo("isPairing", true).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                User user = new User();
                                user.setAge((Long) document.get("age"));
                                user.setName((String) document.get("name"));
                                user.setWeight((Double) document.get("weight"));
                                user.setGender((String) document.get("gender"));
                                user.setFavoriteExercise((String) document.get("favoriteExercise"));
                                user.setLatitude(0);
                                user.setLongitude(0);
                                pairingUsers.add(user);
                            }
                            setupRecyclerView((RecyclerView) recyclerView);

                        }
                    }
                });
                //Step 1.  Setup the recycler view by getting it from our layout in the main window
                Log.d("DashBoard", "click");
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_profile) {
            Model.getInstance().setEditflag(true);

            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getPairingAccount() {
        pairingUsers.clear();
        FirebaseFirestore.getInstance().collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        User user = new User();
                        user.setAge((Long) document.get("age"));
                        user.setName((String) document.get("name"));
                        user.setWeight((Double) document.get("weight"));
                        user.setGender((String) document.get("gender"));
                        user.setFavoriteExercise((String) document.get("favoriteExercise"));
                        user.setLatitude(0);
                        user.setLongitude(0);
                        pairingUsers.add(user);
                        Log.d("Dash4", String.valueOf(pairingUsers.size()));
                    }
                } else {
                    Log.d("Dash3", String.valueOf(pairingUsers.size()));

                }
            }
        });
    }

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        //if we are display a searched list, or an item list at location
        Map<String, Integer> map = new HashMap<>();
        map.put("age", 3);
        map.put("gender", 2);
        map.put("weight", 1);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Sort.sortByPreference(pairingUsers, map)));
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     * <p>
     * In this case, we are just mapping the toString of the Location object to a text field.
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<User> mUsers;

        /**
         * set the users to be used by the adapter
         *
         * @param users the list of users to be displayed in the recycler view
         */
        public SimpleItemRecyclerViewAdapter(List<User> users) {
            mUsers = users;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/location_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Model model = Model.getInstance();
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.mUser = mUsers.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a location in the other.
             */
            holder.mAge.setText(String.valueOf(mUsers.get(position).getAge()));
            holder.mName.setText(mUsers.get(position).getName());
            holder.mDistance.setText(String.valueOf(0));


            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //on a phone, we need to change windows to the detail view
                    Context context = v.getContext();
                    //create our new intent with the new screen (activity)
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    model.setCurrentSelectedUser(holder.mUser);
                    //now just display the new window
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Item) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mName;
            public final TextView mAge;
            public final TextView mDistance;
            public User mUser;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mName = view.findViewById(R.id.user_list_name);
                mAge = view.findViewById(R.id.user_list_age);
                mDistance = view.findViewById(R.id.user_list_distance);
            }


        }
    }
}
