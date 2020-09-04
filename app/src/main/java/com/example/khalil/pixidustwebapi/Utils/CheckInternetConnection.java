package com.example.khalil.pixidustwebapi.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Khalil on 1/30/2018.
 */

public class CheckInternetConnection {
        public static boolean IsNetworkAvailable(Context context) {
            final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }
    }
