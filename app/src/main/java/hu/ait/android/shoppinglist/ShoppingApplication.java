package hu.ait.android.shoppinglist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sarahjin on 11/8/17.
 */

public class ShoppingApplication extends Application {
    private Realm realmItem;

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realmItem = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmItem.close();
    }

    public Realm getRealmItem() {
        return realmItem;
    }
}
