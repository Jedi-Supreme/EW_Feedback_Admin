package com.jedit.eastwingfeedbackadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.jedit.eastwingfeedbackadmin.R;
import com.jedit.eastwingfeedbackadmin.crud.ReadData;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;
import com.jedit.eastwingfeedbackadmin.utilities.intentUtil;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static com.jedit.eastwingfeedbackadmin.utilities.commonUtils.togglePasswordIcon;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText et_login_password, et_login_email;
    TextInputLayout input_login_password;
    ConstraintLayout const_login;
    ProgressBar pro_bar_login;

    WeakReference<LoginActivity> weak_loginActivity;

    //=============================================ON CREATE========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        weak_loginActivity = new WeakReference<>(this);

        et_login_password = findViewById(R.id.et_login_password);
        et_login_email = findViewById(R.id.et_login_email);
        input_login_password = findViewById(R.id.input_login_password);
        const_login = findViewById(R.id.const_login);
        pro_bar_login = findViewById(R.id.probar_login);

        et_login_password.setOnFocusChangeListener((view, b) -> togglePasswordIcon(b,input_login_password));

    }

    //=============================================ON CREATE========================================

    //--------------------------------------OVERRIDE METHODS----------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){

//            intentUtil.toDashboard(this, AppConstants.DEPARTMENT_SETUP_KEY);
            ReadData.load_online_company_Data(pro_bar_login,weak_loginActivity.get());


        }


    }

    //--------------------------------------OVERRIDE METHODS----------------------------------------

    //---------------------------------------DEFINED METHODS----------------------------------------


    public void LoginAction(View view) {

        testInputs();
//        intentUtil.toDashboard(weak_loginActivity.get(),AppConstants.COMPANY_SETUP_KEY);
    }

    void testInputs() {

        Log.e("Login Test:"," Inputs tested");

        if (Objects.requireNonNull(et_login_email.getText()).toString().isEmpty() || et_login_email.getText().toString().equals("")) {
            commonUtils.showError_Input(et_login_email);
            commonUtils.MySnackBar(const_login, "Enter Valid Email", Snackbar.LENGTH_SHORT).show();

        } else if (Objects.requireNonNull(et_login_password.getText()).toString().isEmpty() || et_login_password.getText().toString().equals("")) {
            commonUtils.showError_Input(et_login_password);
            commonUtils.MySnackBar(const_login, "Enter Password", Snackbar.LENGTH_SHORT).show();

        } else {
            login_with_credentials(et_login_email.getText().toString(), et_login_password.getText().toString());
        }


    }

    void login_with_credentials(String email, String password) {

        pro_bar_login.setVisibility(View.VISIBLE);
        Log.e("Login Cred:",email + " " + password + " Login");

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    Log.e("Sign in:"," Starting sign in");

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Before going to dashboard, fetch company data

                        Log.e("Login:"," Success login");
                        ReadData.load_online_company_Data(pro_bar_login,weak_loginActivity.get());

                    } else {

                        // If sign in fails, display a message to the user.
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {

                            commonUtils.MySnackBar(const_login, "Invalid Email Address",
                                    Snackbar.LENGTH_SHORT).show();

                            pro_bar_login.setVisibility(View.INVISIBLE);
                            pro_bar_login.clearAnimation();

                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            commonUtils.MySnackBar(const_login, "Wrong Password",
                                    Snackbar.LENGTH_SHORT).show();

                            pro_bar_login.setVisibility(View.INVISIBLE);
                            pro_bar_login.clearAnimation();
                        }


                    }

                });

    }


    //---------------------------------------DEFINED METHODS----------------------------------------
}