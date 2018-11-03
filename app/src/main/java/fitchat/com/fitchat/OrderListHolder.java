package fitchat.com.fitchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OrderListHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    ImageView reorderView;

    public OrderListHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textview_text);
        reorderView = itemView.findViewById(R.id.imageview_reorder);
    }
}


