package com.tj.drawwithfrineds.Persistence;

/**
 * Created by TJ on 5/1/2018.
 */

public class AppFiles {
    private static AppFiles instance = null;

    public static AppFiles getInstance() {
        if (instance == null) {
            instance = new AppFiles();
            return instance;
        } else {
            return instance;
        }
    }

    
}
