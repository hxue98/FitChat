package fitchat.com.fitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserDetailActivity extends AppCompatActivity {

    private TextView mName, mAge, mGender, mFavorite;
    private Button chat;
    private ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        mName = findViewById(R.id.user_detail_name);
        mAge = findViewById(R.id.user_detail_age);
        mGender = findViewById(R.id.user_detail_gender);
        mFavorite = findViewById(R.id.user_detail_favorite_exercise);

        User user = Model.getInstance().getCurrentSelectedUser();
        mName.setText(user.getName());
        mAge.setText(String.valueOf(user.getAge()));
        mGender.setText(user.getGender());
        mFavorite.setText(user.getFavoriteExercise());


        chat = findViewById(R.id.user_detail_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailActivity.this, UserChatActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
