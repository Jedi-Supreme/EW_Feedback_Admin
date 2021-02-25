package com.jedit.eastwingfeedbackadmin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jedit.eastwingfeedbackadmin.R;
import com.jedit.eastwingfeedbackadmin.crud.WriteData;
import com.jedit.eastwingfeedbackadmin.models.Company;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;

import java.util.Objects;

import static com.jedit.eastwingfeedbackadmin.utilities.commonUtils.*;

public class UpdatePasswordFragment extends Fragment implements View.OnFocusChangeListener {

    TextInputLayout input_setup_old_password, input_setup_new_password,input_setup_confirm_password;
    TextInputEditText et_setup_company_name, et_setup_old_password, et_setup_new_password, et_setup_confirm_password;
    ProgressBar probar_company_setup;
    ConstraintLayout const_frag_comp_setup;
    Button bt_company_setup_next;

    SharedPreferences appPref;
    Company company;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_password_setup,container,false);
        appPref = commonUtils.app_pref(view.getContext());
        company = new Company();

        input_setup_old_password = view.findViewById(R.id.input_setup_old_password);
        input_setup_new_password = view.findViewById(R.id.input_setup_new_password);
        input_setup_confirm_password = view.findViewById(R.id.input_setup_confirm_password);

        et_setup_company_name = view.findViewById(R.id.et_setup_department_company_name);
        et_setup_old_password = view.findViewById(R.id.et_setup_old_password);
        et_setup_new_password = view.findViewById(R.id.et_setup_new_password);
        et_setup_confirm_password = view.findViewById(R.id.et_setup_confirm_password);

        probar_company_setup = view.findViewById(R.id.probar_company_setup);
        const_frag_comp_setup = view.findViewById(R.id.const_frag_comp_setup);

        bt_company_setup_next = view.findViewById(R.id.bt_company_setup_next);

        et_setup_old_password.setOnFocusChangeListener(this);
        et_setup_new_password.setOnFocusChangeListener(this);
        et_setup_confirm_password.setOnFocusChangeListener(this);

        bt_company_setup_next.setOnClickListener(view1 -> {

            //test inputs and go to department adding page
            testInputs();

        });

        return view;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        switch (view.getId()){

            case R.id.et_setup_old_password:
                togglePasswordIcon(b,input_setup_old_password);
                break;
            case R.id.et_setup_new_password:
                togglePasswordIcon(b,input_setup_new_password);
                break;
            case R.id.et_setup_confirm_password:
                togglePasswordIcon(b,input_setup_confirm_password);
                break;

        }

    }


    void testInputs(){

        Log.e("Test:", "Testing run");

        if (Objects.requireNonNull(et_setup_old_password.getText()).toString().isEmpty()){
            commonUtils.showError_Input(et_setup_old_password);
            commonUtils.MySnackBar(const_frag_comp_setup,"Please enter Old Password", Snackbar.LENGTH_SHORT).show();

        }else if (Objects.requireNonNull(et_setup_new_password.getText()).toString().isEmpty()){
            commonUtils.showError_Input(et_setup_new_password);
            commonUtils.MySnackBar(const_frag_comp_setup,"Please enter New Password", Snackbar.LENGTH_SHORT).show();

        }else if (Objects.requireNonNull(et_setup_confirm_password.getText()).toString().isEmpty()){
            commonUtils.showError_Input(et_setup_confirm_password);
            commonUtils.MySnackBar(const_frag_comp_setup,"Please Confirm New password", Snackbar.LENGTH_SHORT).show();

        }else if (!et_setup_new_password.getText().toString().equals(et_setup_confirm_password.getText().toString())){
            commonUtils.MySnackBar(const_frag_comp_setup,"Passwords do not match, please try again", Snackbar.LENGTH_SHORT).show();

        }else {

            Log.e("Comp test:", "Beginning writing process");

            //go to department adding page
//            company.setName(et_setup_company_name.getText().toString());//save to preference
//            company.setUuid(FirebaseAuth.getInstance().getUid());
//
//            SharedPreferences.Editor prefEditor = appPref.edit();
//            prefEditor.putString(AppConstants.COMPANY_NAME_KEY,company.getName());
//            prefEditor.putString(AppConstants.INTENT_ACTION_KEY,AppConstants.DEPARTMENT_SETUP_KEY);
//            prefEditor.apply();

            WriteData.updatePassword(getActivity(),et_setup_old_password.getText().toString(),et_setup_confirm_password.getText().toString());

            //toDepartmentSetup();

//            WriteData.saveCompanyOnline(const_frag_comp_setup,getActivity(),company,
//                    et_setup_old_password.getText().toString(),et_setup_confirm_password.getText().toString());
        }

    }


    void toDepartmentSetup(){

        FragmentTransaction transaction;

        if (getActivity() != null){
               transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_dashboard,new DepartmentSetupFragment());
            transaction.addToBackStack(AppConstants.DEPARTMENT_SETUP_KEY);
            transaction.commit();
        }

    }

}
