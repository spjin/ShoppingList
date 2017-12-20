package hu.ait.android.shoppinglist.touch;

/**
 * Created by sarahjin on 11/9/17.
 */

public interface ShoppingTouchHelperAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);
}