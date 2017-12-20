package hu.ait.android.shoppinglist.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import hu.ait.android.shoppinglist.touch.ShoppingTouchHelperAdapter;

/**
 * Created by sarahjin on 11/9/17.
 */

public class ShoppingItemTouchHelperCallback extends
        ItemTouchHelper.Callback {

    private ShoppingTouchHelperAdapter shoppingTouchHelperAdapter;

    public ShoppingItemTouchHelperCallback(ShoppingTouchHelperAdapter todoTouchHelperAdapter) {
        this.shoppingTouchHelperAdapter = todoTouchHelperAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    public boolean isItemViewSwiperEnabled(){
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        shoppingTouchHelperAdapter.onItemMove(
                viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        shoppingTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}