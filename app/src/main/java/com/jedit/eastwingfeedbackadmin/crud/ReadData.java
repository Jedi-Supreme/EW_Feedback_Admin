package com.jedit.eastwingfeedbackadmin.crud;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jedit.eastwingfeedbackadmin.activities.LoginActivity;
import com.jedit.eastwingfeedbackadmin.database.Database;
import com.jedit.eastwingfeedbackadmin.models.Company;
import com.jedit.eastwingfeedbackadmin.models.Department;
import com.jedit.eastwingfeedbackadmin.models.Question;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;
import com.jedit.eastwingfeedbackadmin.utilities.intentUtil;

import java.util.List;

public class ReadData {

    public static DatabaseReference comp_ref = commonUtils.companyReference();

    public static void load_online_company_Data(ProgressBar progressBar, Context activityContext){

        String uid;

        Log.e("fetch:","Loading Company Started");

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();

            Log.e("user:",uid);

            comp_ref.child(uid).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Company company = dataSnapshot.getValue(Company.class);

                    Log.e("Data fetch:","Loading Company happening");

                    if (company != null){

                        SharedPreferences.Editor pref_editor = commonUtils.app_pref(activityContext).edit();

                        pref_editor.putString(AppConstants.COMPANY_NAME_KEY,company.getName());

                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            pref_editor.putString(AppConstants.COMPANY_UUID_KEY, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }

                        pref_editor.apply();
                        comp_ref.removeEventListener(this);
                        intentUtil.toDashboard((Activity) activityContext,AppConstants.COMPANY_LOAD_KEY);

                    }else {

                        intentUtil.toDashboard((Activity) activityContext,AppConstants.COMPANY_SETUP_KEY);

                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.clearAnimation();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }

    }

    public static List<Department> loadDepartments_local(Context context){

        Database appDb = Database.getInstance(context);

        return appDb.feedbackDAO().getAllDepartments();
    }

    public static List<Question> loadQuestions_local(Context context, String departmentID){

        Database appDb = Database.getInstance(context);

        return appDb.feedbackDAO().getDepartment_questions(departmentID);
    }

}
