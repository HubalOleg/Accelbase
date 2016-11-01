package com.oleg.hubal.accelbase;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

/**
 * Created by User on 01.11.2016.
 */

public class Utils {
    public static long getDelayFromEditText(Editable text) {
        long delay = 1;
        String delayText = text.toString();
        if (!delayText.equals("")) {
            delay = Long.parseLong(delayText);
        }
        return delay * 1000;
    }

    public static void showToast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }
}
