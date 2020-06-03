package com.system.technologyinformation.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    public static SharedPreferences share(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        return sharedPreferences;
    }


    public static String getName(Context context){
        return share(context).getString("url",null);
    }

    public static boolean setName(String url, Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString("url",url);
        Boolean bool = e.commit();
        return bool;
    }
}
