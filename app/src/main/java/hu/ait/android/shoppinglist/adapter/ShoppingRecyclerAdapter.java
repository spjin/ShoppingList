package hu.ait.android.shoppinglist.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import hu.ait.android.shoppinglist.MainActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.ShoppingApplication;
import hu.ait.android.shoppinglist.data.Item;
import hu.ait.android.shoppinglist.touch.ShoppingTouchHelperAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by sarahjin on 11/9/17.
 */

public class ShoppingRecyclerAdapter extends RecyclerView.Adapter<ShoppingRecyclerAdapter.ViewHolder>
implements ShoppingTouchHelperAdapter {

    public static final String ITEM_ID = "itemID";
    private List<Item> shopList;

    private MainActivity context;
    private Realm realmShop;

    public ShoppingRecyclerAdapter(MainActivity context, Realm realmShop){
        this.context = context;
        this.realmShop = realmShop;

        shopList = new ArrayList<Item>();

        RealmResults<Item> itemResult = realmShop.where(Item.class).findAll().sort("itemName", Sort.ASCENDING);

        for (Item it : itemResult) {
            shopList.add(it);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shopping_row, parent, false);

        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item itemData = shopList.get(position);

        String itType = itemData.getCategory();

        if(itType != null){
            if(itType.equals(context.getString(R.string.food))) {
                holder.ivType.setImageResource(R.drawable.food);
            }
            else if(itType.equals(context.getString(R.string.electronic))) {
                holder.ivType.setImageResource(R.drawable.cellphone);
            }
            else if(itType.equals(context.getString(R.string.book))){
                holder.ivType.setImageResource(R.drawable.book);
            }
        }
        
        holder.tvName.setText(itemData.getItemName());
        holder.tvDesc.setText(itemData.getItemDescription());
        holder.tvPrice.setText(itemData.getPrice());
        holder.cbPurchased.setChecked(itemData.isPurchased());

        holder.cbPurchased.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                realmShop.beginTransaction();
                itemData.setPurchased(isChecked);

                realmShop.commitTransaction();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)context).openEditActivity(
                        holder.getAdapterPosition(),
                        shopList.get(holder.getAdapterPosition()).getItemID()
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public void onItemDismiss(int position) {

        Item itemToDelete = shopList.get(position);
        realmShop.beginTransaction();
        itemToDelete.deleteFromRealm();
        realmShop.commitTransaction();

        shopList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(shopList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(shopList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void deleteAll(){
        realmShop.beginTransaction();
        for(Item i : shopList){
            i.deleteFromRealm();
        }
        realmShop.commitTransaction();
        shopList.clear();
        notifyDataSetChanged();

    }

    public void addItem(String itemID){
        realmShop.beginTransaction();

        Item newItem = realmShop.where(Item.class).
                equalTo(ITEM_ID, itemID).findFirst();

        newItem.setCreateDate(
                new Date(System.currentTimeMillis()).toString());

        realmShop.commitTransaction();

        shopList.add(0, newItem);
        notifyItemInserted(0);
    }

    public void updateItem(String itemIDThatWasEdited, int positionToEdit) {
        Item item = realmShop.where(Item.class).equalTo(ITEM_ID, itemIDThatWasEdited).findFirst();

        shopList.set(positionToEdit, item);

        notifyItemChanged(positionToEdit);
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivType;
        private TextView tvName;
        private TextView tvDesc;
        private TextView tvPrice;
        private CheckBox cbPurchased;
        private Button btnEdit;


        public ViewHolder(View itemView) { 
            super(itemView);
            
            ivType = itemView.findViewById(R.id.ivType);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cbPurchased = itemView.findViewById(R.id.cbPurchased);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
