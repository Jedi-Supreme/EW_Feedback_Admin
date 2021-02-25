package com.jedit.eastwingfeedbackadmin.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jedit.eastwingfeedbackadmin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class commonUtils {

    public static final String db_date_format = "yyyy-MM-dd HH:mm:ss";

    private static String timeMilli_DB_Date(String timeMillis){

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(timeMillis));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(db_date_format, Locale.getDefault());

        return  simpleDateFormat.format(calendar.getTime());
    }

    public static Snackbar MySnackBar(View parent_view, String message, int lenght) {

        final Snackbar snackbar = Snackbar.make(parent_view, message, lenght);
        snackbar.setActionTextColor(parent_view.getContext().getResources().getColor(R.color.colorPrimary));
        snackbar.setAction("Close", v -> snackbar.dismiss());

        return snackbar;
    }

    public static SharedPreferences app_pref(Context context){
        return context.getSharedPreferences("Feedback_pref", Context.MODE_PRIVATE);
    }

    public static String humanDate(String date){
        SimpleDateFormat parseformat = new SimpleDateFormat(db_date_format, Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyy",Locale.getDefault());

        try {
            Date date1 = parseformat.parse(date);
            return dateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double percentage(double amount, double total){

        return (amount/total) * 100;
    }

    //----------------------------------------FIREBASE REFERENCES-----------------------------------
    public static DatabaseReference companyReference (){
        return FirebaseDatabase.getInstance().getReference(AppConstants.COMPANIES_LABEL_KEY);
    }

    public static DatabaseReference departmentReference(){
        return FirebaseDatabase.getInstance().getReference(AppConstants.DEPARTMENTS_LABEL_KEY);
    }

    public static DatabaseReference questionReference(){
        return FirebaseDatabase.getInstance().getReference(AppConstants.QUESTIONS_LABEL_KEY);
    }
    //----------------------------------------FIREBASE REFERENCES-----------------------------------

    public static void togglePasswordIcon(boolean hasFocus, TextInputLayout textInputLayout){
        if (hasFocus){
            textInputLayout.setEndIconActivated(true);
            textInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }else {
            textInputLayout.setEndIconActivated(false);
            textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        }
    }

    public static void showError_Input(TextInputEditText et_input){
        et_input.requestFocus();
        et_input.setError("This field is required");
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static void connection_toast(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
    }

    public static Executor getDBExecutor(){
        return Executors.newFixedThreadPool(6);
    }
}
