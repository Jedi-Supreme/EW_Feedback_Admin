package com.jedit.eastwingfeedbackadmin.crud;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.jedit.eastwingfeedbackadmin.activities.DashBoard;
import com.jedit.eastwingfeedbackadmin.database.Database;
import com.jedit.eastwingfeedbackadmin.models.Company;
import com.jedit.eastwingfeedbackadmin.models.Department;
import com.jedit.eastwingfeedbackadmin.models.Question;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;

import java.util.List;

public class WriteData {

    static DatabaseReference compRef = commonUtils.companyReference();
    static DatabaseReference deptRef = commonUtils.departmentReference();
    static DatabaseReference questRef = commonUtils.questionReference();


    static SharedPreferences appPref;
    static Database appDb;

    //----------------------------------------ONLINE SAVE-------------------------------------------
    public static void saveCompanyOnline( Activity activity, Company company){

        Log.e("Saving:", "Upload started");

        appPref = commonUtils.app_pref(activity);
        SharedPreferences.Editor prefEditor = appPref.edit();

        compRef.child(company.getUuid()).setValue(company).addOnCompleteListener(task -> {

            if (task.isSuccessful()){

                //save to preference
                prefEditor.putString(AppConstants.COMPANY_NAME_KEY,company.getName());
                prefEditor.putString(AppConstants.INTENT_ACTION_KEY,AppConstants.DEPARTMENT_SETUP_KEY);
                prefEditor.apply();

                Log.e("Saving:", "Upload company data success");
                Toast.makeText(activity,company.getName() + " created Successfully.", Toast.LENGTH_LONG).show();

                //Todo check if department is not empty and save that else move to dashboard

                if (company.getDepartments().size() > 0){
                    saveDepartments_online(activity,company);
                }else {

                    if (activity instanceof DashBoard){
                        hide_Dashboard_fragments(activity);
                    }

                }

            }else {
                Toast.makeText(activity,"Error creating " + company.getName() + " please try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void saveDepartments_online(Activity activity, Company company){

        Log.e("Saving:", "Upload department started");

        deptRef.child(company.getUuid()).setValue(company.getDepartments()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                    saveDepartmentsList_local(activity, company.getDepartments());
                Log.e("Saving:", "Upload departments data success");

                    for (Department department : company.getDepartments()){

                        if (department.getQuestions().size() > 0){
                           saveQuestion_online(activity,company);
                        }else {
                            if (activity instanceof DashBoard){
                                hide_Dashboard_fragments(activity);
                            }
                        }
                    }

            }else {
                Log.e("Saving", "Failed saving department online");
            }
        });
    }

    public static void saveQuestion_online(Activity activity,Company company){

        Log.e("Saving:", "Upload questions started");

        for (Department department : company.getDepartments()){
            questRef.child(company.getUuid())
                    .child(department.getId())
                    .setValue(department.getQuestions()).addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            Log.e("Saving:", "Upload questions success");
                            saveQuestionList_local(activity,department.getQuestions());
                        }
                    });
        }
    }
    //----------------------------------------ONLINE SAVE-------------------------------------------

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-LOCAL SAVE=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static void saveQuestionList_local(Activity activity, List<Question> questions){

        appDb = Database.getInstance(activity);
        commonUtils.getDBExecutor().execute(() -> appDb.feedbackDAO().insert_AllQuestions(questions));
        Log.e("Saving Local:", "Questions local save");

        if (activity instanceof DashBoard){
            hide_Dashboard_fragments(activity);
        }
    }

    public static void saveQuestion_local(Activity activity, Question question){
        appDb = Database.getInstance(activity);
        commonUtils.getDBExecutor().execute(() -> appDb.feedbackDAO().insert_Single_question(question));
        Log.e("Saving Local:", "Single Question local save");
    }

    public static void saveDepartmentsList_local(Activity activity, List<Department> departments){
        appDb = Database.getInstance(activity);
        commonUtils.getDBExecutor().execute(() -> appDb.feedbackDAO().insert_AllDepartment(departments));
        Log.e("Saving Local:", "Departments local save");
    }

    public static void saveDepartment_local(Activity activity, Department department){
        appDb = Database.getInstance(activity);
        commonUtils.getDBExecutor().execute(() -> appDb.feedbackDAO().insert_single_Department(department));
        Log.e("Saving Local:", "Single department local save");
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-LOCAL SAVE=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    public static void updatePassword(Activity activity, String oldPassword, String newPassword){

        SharedPreferences appPref = commonUtils.app_pref(activity);
        SharedPreferences.Editor prefEditor = appPref.edit();

        if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().getEmail() != null){

            Log.e("Update", "Password update process started");

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    oldPassword
            ).
                    addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
//                                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword);
                                    Log.e("Update:", "Password Update process completed");
                                    prefEditor.putString(AppConstants.INTENT_ACTION_KEY,AppConstants.DEPARTMENT_SETUP_KEY);
                                    prefEditor.apply();

                                    if (activity instanceof DashBoard){
                                        ((DashBoard)activity).show_department_setup_fragment();
                                    }

                                }
                            });
//                        toDepartmentSetup();

                        }
                    });

        }


    }

    public static void hide_Dashboard_fragments(Activity activity){
            ((DashBoard)activity).dismissAll_fragments();
    }


}
