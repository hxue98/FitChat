package fitchat.com.fitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private EditText mName, mAge, mWeight, mFavorite;
    private Spinner mGender;
    private Button mOk;
    FirebaseFirestore db;
    private String name, favorite, gender;
    private int age;
    private double weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = FirebaseFirestore.getInstance();

        mName = findViewById(R.id.profile_name);
        mAge = findViewById(R.id.profile_age);
        mWeight = findViewById(R.id.profile_weight);
        mFavorite = findViewById(R.id.profile_favorite_exercise);
        mGender = findViewById(R.id.profile_gender);
        mOk = findViewById(R.id.profile_ok);
        ArrayList<String> arrayGender = new ArrayList<>(Arrays.asList("male", "female"));

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayGender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(adapter);
        if (Model.getInstance().isEditflag()) {
            User user = Model.getInstance().getCurrentUser();
            mName.setText(user.getName());
            mAge.setText(String.valueOf(user.getAge()));
            mWeight.setText(String.valueOf(user.getWeight()));
            mFavorite.setText(user.getFavoriteExercise());
            if (user.getGender().equals("female")) {
                mGender.setSelection(1);
            }


        }
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInteger(mAge.getText().toString())) {
                    Toast.makeText(ProfileActivity.this, "Please enter Integer for age", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isDouble(mWeight.getText().toString())) {
                    Toast.makeText(ProfileActivity.this, "Please enter decimal number for weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                name = mName.getText().toString();
                age = Integer.parseInt(mAge.getText().toString());
                if (mGender.getSelectedItemPosition() == 0) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                weight = Double.parseDouble(mWeight.getText().toString());
                favorite = mFavorite.getText().toString();
                Model.getInstance().setCurrentUser(new User(name, gender, age, weight, 0, 0, favorite, false));
                pushNewUserToDatabase(Model.getInstance().getCurrentUser());
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void pushNewUserToDatabase(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", mName.getText().toString());
        userMap.put("age", Long.parseLong(mAge.getText().toString()));
        userMap.put("weight", Double.parseDouble(mWeight.getText().toString()));
        userMap.put("favoriteExercise", mFavorite.getText().toString());
        if (mGender.getSelectedItemPosition() == 0) {
            userMap.put("gender", "male");
        } else {
            userMap.put("gender", "female");
        }
        userMap.put("longtitude", 0);
        userMap.put("latitude", 0);
        userMap.put("isPairing", false);
        userMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .set(userMap, SetOptions.merge());
    }

}
