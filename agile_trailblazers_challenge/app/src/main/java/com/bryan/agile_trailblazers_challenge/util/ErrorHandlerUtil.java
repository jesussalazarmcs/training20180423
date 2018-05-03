package com.bryan.agile_trailblazers_challenge.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class ErrorHandlerUtil {

    public static void showErrorDialog(@NonNull Context context, @NonNull String message,
                                       @NonNull String title,
                                       String positive,
                                       DialogInterface.OnClickListener positiveListener,
                                       String negative,
                                       DialogInterface.OnClickListener negativeListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title);

        if (positive != null) {
            builder.setPositiveButton(positive, positiveListener);
        }

        if (negative != null) {
            builder.setNegativeButton(negative, negativeListener);
        }

        builder.show();
    }
}
