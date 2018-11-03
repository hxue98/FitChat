package fitchat.com.fitchat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static  Model instance = new Model();
    public static Model getInstance() { return instance; }

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<User> accounts;
    private FirebaseUser currentUser;

    private Model() {
        accounts = new ArrayList<>();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public List<User> getAccounts() {
        return accounts;
    }

}
