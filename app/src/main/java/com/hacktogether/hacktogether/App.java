package com.hacktogether.hacktogether;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Eddy on 1/23/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);

        // Register any ParseObject subclass. Must be done before calling Parse.initialize()
        //ParseObject.registerSubclass(App.class);
        System.out.println("parse init");
        Parse.initialize(this, getResources().getString(R.string.parse_app_id),
                getResources().getString(R.string.parse_client_key));
    }
}