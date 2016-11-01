package com.oleg.hubal.accelbase;

import android.text.Editable;

/**
 * Created by User on 01.11.2016.
 */

public class Utility {
    public static long getDelayFromEditText(Editable text) {
        long delay = 1;
        String delayText = text.toString();
        if (!delayText.equals("")) {
            delay = Long.parseLong(delayText);
        }
        return delay * 1000;
    }
}
