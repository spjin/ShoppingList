package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;
import java.util.UUID;

import hu.ait.android.shoppinglist.data.Item;

/**
 * Created by sarahjin on 11/8/17.
 */

public class NewItemActivity extends AppCompatActivity{

    private static final String ITEM_ID = "itemID";

    private ImageView ivType;
    private EditText etItemText;
    private EditText etDescription;
    private EditText etPrice;
    private EditText etType;
    private CheckBox cbPurchased;

    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        if(getIntent().hasExtra(MainActivity.KEY_ITEM_ID)){
            String itemId = getIntent().getStringExtra(MainActivity.KEY_ITEM_ID);

            itemToEdit = ((ShoppingApplication)getApplication()).getRealmItem().where(Item.class).
                    equalTo(ITEM_ID, itemId).findFirst();
        }

        setUp();
        Button btnSave = findViewById(R.id.btnSave);

        if(itemToEdit != null){
            editItem();
        }

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((ShoppingApplication)getApplication()).getRealmItem().beginTransaction();

                if(itemToEdit == null){
                    createItem();
                }

                saveItem();
                ((ShoppingApplication)getApplication()).getRealmItem().commitTransaction();

                Intent intentResult = new Intent();
                intentResult.putExtra(MainActivity.KEY_ITEM_ID, itemToEdit.getItemID());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });
    }

    private void setUp(){
        ivType = findViewById(R.id.ivType);
        etItemText = findViewById(R.id.etItemText);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etType = findViewById(R.id.etType);
        cbPurchased = findViewById(R.id.cbPurchased);
    }

    private void editItem(){
        etItemText.setText(itemToEdit.getItemName());
        etDescription.setText(itemToEdit.getItemDescription());
        etPrice.setText(itemToEdit.getPrice());
        etType.setText(itemToEdit.getCategory());
        cbPurchased.setChecked(itemToEdit.isPurchased());

    }

    private void createItem(){
        itemToEdit = ((ShoppingApplication)getApplication()).getRealmItem()
                .createObject(Item.class, UUID.randomUUID().toString());
        itemToEdit.setCreateDate(
                new Date(System.currentTimeMillis()).toString());
        itemToEdit.setPurchased(false);
    }

    private void saveItem(){
        itemToEdit.setItemName(etItemText.getText().toString());
        itemToEdit.setItemDescription(etDescription.getText().toString());
        itemToEdit.setPrice(etPrice.getText().toString());
        itemToEdit.setCategory(etType.getText().toString());

        itemToEdit.setPurchased(cbPurchased.isChecked());
    }
}
