package com.example.realmexercise.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;


public class DialogRepository {

    private static AlertDialog.Builder builder;

    public static View init(Context context, int layout, String message){
        builder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(layout, null);
        builder.setView(view);
        builder.setMessage(message);
        return view;

    }

    public static void addPositiveButton(String message, DialogInterface.OnClickListener clickListener){

        builder.setPositiveButton(message, clickListener);
    }

    public static void addNegativeButton(String message, DialogInterface.OnClickListener clickListener){
        builder.setNegativeButton(message, clickListener);
    }

    public static void showDialog(){
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
