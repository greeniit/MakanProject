package com.makan.app.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makan.R;
import com.makan.app.callback.DialogCallback;
import com.makan.app.callback.SortOptionSelectionCallback;
import com.makan.app.core.Codes;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utility {

    public void moveToActivity(Context context, Class className, Bundle bundle) {

        Intent intent = new Intent();
        intent.setClass(context, className);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    public void moveToActivityForResult(Activity activity, Class className, Bundle bundle) {

        Intent intent = new Intent();
        intent.setClass(activity, className);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        activity.startActivityForResult(intent,100);
    }

    public void openShareIntent(Context context, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void loadImage(Context context, String url, ImageView imageView) {

        Glide.with(context).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public void showMessageAlertDialog(Context context, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void showMessageAlertDialog(Context context, String message, final int dialogId, final DialogCallback dialogCallback) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialogCallback.onDialogDismissed(dialogId);
                    }
                });
        alertDialog.show();
    }

    public String getErrorMessage(Context context, int errorCode) {

        switch (errorCode) {

            case Codes.ERROR_NETWORK:

                return context.getResources().getString(R.string.error_network);


            case Codes.ERROR_UNEXPECTED:

                return context.getResources().getString(R.string.error_unexpected);


            case Codes.ERROR_UNABLE_CONNECT_TO_SERVER:

                return context.getResources().getString(R.string.error_server_conection);

            case Codes.ERROR_INVALID_CREDENTIALS:

                return context.getResources().getString(R.string.error_invalid_credentials);


            default:

                return context.getResources().getString(R.string.error_unexpected);

        }
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null) {

            return true;
        }

        return false;
    }

    public static String getMonth(int month) {

        switch (month) {

            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sept";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";

        }

        return "";
    }

    public Dialog onCreateDialogSingleChoice(Context context, final SortOptionSelectionCallback sortOptionSelectionCallback) {

        final int[] selectedChoice = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] array = {"Price: Low to High", "Price: High to Low", "Area: Low to High", "Area: High to Low"};
        builder.setTitle("Sort By")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        selectedChoice[0] = which;

                    }

                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        sortOptionSelectionCallback.onSortOptionSelected(selectedChoice[0]);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public void hideSoftKeyBoard(Activity context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
