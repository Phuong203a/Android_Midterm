package com.example.studentinformationmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

public class LoadingAlert {
    private Activity activity;
    private AlertDialog dialog;
    LoadingAlert(Activity myActivity) {
        activity = myActivity;
    }

    public void setLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_alert, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

    }

    public void closeLoadingDialog() {
        dialog.dismiss();
    }
}
