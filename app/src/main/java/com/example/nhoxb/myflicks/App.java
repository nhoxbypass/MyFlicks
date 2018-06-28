package com.example.nhoxb.myflicks;

import android.app.Application;

import com.example.nhoxb.myflicks.data.AppDataManager;
import com.example.nhoxb.myflicks.data.DataManager;

/**
 * Created by tom on 6/28/18.
 */
public class App extends Application {
    static DataManager dataManager;

    public static DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dataManager = new AppDataManager(getString(R.string.api_key));
    }
}
