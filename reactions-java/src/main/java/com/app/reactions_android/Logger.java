package com.app.reactions_android;

import android.util.Log;

/**
 * @Author rahulravindran
 */

public class Logger {

    public static void debug(String tag, String logmessage) {
        if (enableLogging()) {
            Log.d(tag, logmessage);
        }
    }

    public static void verbose(String tag, String logmessage) {
        if (enableLogging()) {
            Log.v(tag, logmessage);
        }
    }


    static boolean enableLogging() {
        return BuildConfig.DEBUG;
    }
}


