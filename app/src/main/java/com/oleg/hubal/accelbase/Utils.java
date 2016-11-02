package com.oleg.hubal.accelbase;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public static String formatDate(String milliSeconds) {
        Long millis = Long.parseLong(milliSeconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    public static String formatDate(Long milliSeconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
