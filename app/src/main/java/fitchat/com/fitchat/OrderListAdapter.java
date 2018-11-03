package fitchat.com.fitchat;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by Srijith on 08-10-2017.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        SwipeAndDragHelper.ActionCompletionContract {
    private static final int USER_TYPE = 1;
    private static final int HEADER_TYPE = 2;
    private List<String> ordersList;
    private ItemTouchHelper touchHelper;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case USER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_item, parent, false);
                return new OrderListHolder(view);
            case HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_item, parent, false);
                return new OrderListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == USER_TYPE) {
            ((OrderListHolder) holder).mTextView.setText(ordersList.get(position));
            ((OrderListHolder) holder).reorderView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        touchHelper.startDrag(holder);
                    }
                    return false;
                }
            });
        } else {
            SectionHeaderViewHolder headerViewHolder = (SectionHeaderViewHolder) holder;
            headerViewHolder.sectionTitle.setText(ordersList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return ordersList == null ? 0 : ordersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(ordersList.get(position))) {
            return HEADER_TYPE;
        } else {
            return USER_TYPE;
        }
    }

    public void setOrderList(List<String> ordersList) {
        this.ordersList = ordersList;
        notifyDataSetChanged();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        String targetText = ordersList.get(oldPosition);
        String string  = targetText;
        ordersList.remove(oldPosition);
        ordersList.add(newPosition, string);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        ordersList.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {

        this.touchHelper = touchHelper;
    }
}
