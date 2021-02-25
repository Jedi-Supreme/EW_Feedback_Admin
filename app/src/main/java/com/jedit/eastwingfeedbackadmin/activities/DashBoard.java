package com.jedit.eastwingfeedbackadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jedit.eastwingfeedbackadmin.R;
import com.jedit.eastwingfeedbackadmin.fragments.UpdatePasswordFragment;
import com.jedit.eastwingfeedbackadmin.fragments.DepartmentSetupFragment;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;
import com.jedit.eastwingfeedbackadmin.utilities.intentUtil;

public class DashBoard extends AppCompatActivity {

    Bundle bundle;
    FrameLayout frame_dashboard;
    SharedPreferences appPref;

    TextView tv_sum_total_count;
    TextView tv_sum_total_good, tv_sum_total_good_percentage;
    TextView tv_sum_total_bad, tv_sum_total_bad_percentage;
    CardView card_company_summary;
    RecyclerView recycler_feedback_summary;

    //==============================================ON CREATE=======================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bundle = getIntent().getExtras();
        appPref = commonUtils.app_pref(this);

        frame_dashboard = findViewById(R.id.frame_dashboard);
        tv_sum_total_count = findViewById(R.id.tv_sum_total_count);
        tv_sum_total_good = findViewById(R.id.tv_sum_total_good);
        tv_sum_total_good_percentage = findViewById(R.id.tv_sum_total_good_percentage);
        tv_sum_total_bad = findViewById(R.id.tv_sum_total_bad);
        tv_sum_total_bad_percentage = findViewById(R.id.tv_sum_total_bad_percentage);
        card_company_summary = findViewById(R.id.card_company_summary);
        recycler_feedback_summary = findViewById(R.id.recycler_feedback_summary);

        String intentAction = bundle.getString(AppConstants.INTENT_ACTION_KEY,null);

        switch (intentAction) {
            case AppConstants.COMPANY_SETUP_KEY:
                show_setup_fragment();
                break;
            case AppConstants.DEPARTMENT_SETUP_KEY:
                show_department_setup_fragment();
                break;
            case AppConstants.COMPANY_LOAD_KEY:
                frame_dashboard.setVisibility(View.GONE);
                break;
        }

    }
    //==============================================ON CREATE=======================================

    //-----------------------------------------------DEFINED METHOD---------------------------------
    void show_setup_fragment(){
        Log.e("Dashboard:", "Show company fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_dashboard,new UpdatePasswordFragment());
        transaction.addToBackStack(AppConstants.COMPANY_SETUP_KEY);
        transaction.commit();
    }

    public void show_department_setup_fragment(){
        Log.e("Dashboard:", "Show department fragment");
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_dashboard,new DepartmentSetupFragment());
        transaction.addToBackStack(AppConstants.DEPARTMENT_SETUP_KEY);
        transaction.commit();
    }

    public void dismissAll_fragments(){
        frame_dashboard.setVisibility(View.GONE);
    }
    //-----------------------------------------------DEFINED METHOD---------------------------------


    //-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-OVERRIDE METHOD-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            intentUtil.toLogin(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1 || getSupportFragmentManager() == null){
            super.onBackPressed();
        }

//        for (Fragment fragment : getSupportFragmentManager().getFragments()){
//            if (fragment != null && fragment.getTag() != null){
//
//                if (!fragment.getTag().equals(AppConstants.COMPANY_SETUP_KEY)){
////                getSupportFragmentManager().beginTransaction().remove(fragment);
//                    super.onBackPressed();
//                }
//            }
//        }

    }
    //-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-OVERRIDE METHOD-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
}