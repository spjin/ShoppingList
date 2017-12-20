package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import hu.ait.android.shoppinglist.adapter.ShoppingRecyclerAdapter;
import hu.ait.android.shoppinglist.data.Item;
import hu.ait.android.shoppinglist.touch.ShoppingItemTouchHelperCallback;

/**
 * Created by sarahjin on 11/8/17.
 */

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT = 1001;

    public static final String KEY_ITEM_ID = "KEY_ITEM_ID";
    private ShoppingRecyclerAdapter adapter;

    private int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((ShoppingApplication)getApplication()).openRealm();

        RecyclerView recyclerViewItem = (RecyclerView) findViewById(R.id.recyclerItem);
        adapter = new ShoppingRecyclerAdapter(this,
                ((ShoppingApplication)getApplication()).getRealmItem());

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new ShoppingItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewItem);

        recyclerViewItem.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        ((ShoppingApplication)getApplication()).closeRealm();
        super.onDestroy();
    }

    public void clearList(){
        adapter.deleteAll();
    }

    public void openEditActivity(int adapterPosition, String itemID) {
        positionToEdit = adapterPosition;

        Intent intentEdit = new Intent (this, NewItemActivity.class);
        intentEdit.putExtra(KEY_ITEM_ID, itemID);

        startActivityForResult(intentEdit, REQUEST_CODE_EDIT);
    }

    public void openNewItemActivity(){
        Intent intentNewItem = new Intent(this, NewItemActivity.class);

        startActivityForResult(intentNewItem, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){

            String itemIDThatWasEdited = data.getStringExtra(KEY_ITEM_ID);
            if(positionToEdit == -1){
                adapter.addItem(itemIDThatWasEdited);
            }
            else{
                adapter.updateItem(itemIDThatWasEdited, positionToEdit);
            }
        }
        else{
            Toast.makeText(this, "Edit was cancelled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_newItem:
                Toast.makeText(this, R.string.new_item, Toast.LENGTH_SHORT).show();
                openNewItemActivity();
                break;
            case R.id.action_deleteAll:
                Toast.makeText(this, R.string.list_cleared, Toast.LENGTH_SHORT).show();
                clearList();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
