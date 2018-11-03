package fitchat.com.fitchat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static  Model instance = new Model();
    public static Model getInstance() { return instance; }

    private FirebaseFirestore db;
    private List<User> accounts;
    private User currentUser;
    private FirebaseAuth mAuth;

    private Model() {
        accounts = new ArrayList<>();
        setAccounts();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getAccounts() {
        return accounts;
    }

    public void setAccounts() {
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Users");
        query.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            accounts.add(user);
                            user.setReference(document.getReference());
                            Log.d("getAccount", user.toString());
                        }
                    } else {
                        Log.d("getAccount", "failed");
                    }
                }
            });
    }
}
