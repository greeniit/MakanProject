package com.makan.app.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.makan.R;

/**
 * Created by roykmathew on 27/06/17.
 */
public class BaseFragment extends Fragment{

    protected AlertDialog progressDialog;

    protected void showProgressDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progress_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        progressDialog = dialogBuilder.create();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.show();
    }

    protected void dismissProgressDialog(){

        if(progressDialog!=null&&progressDialog.isShowing()){

            progressDialog.dismiss();
        }
    }
}
