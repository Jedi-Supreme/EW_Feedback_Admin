package com.jedit.eastwingfeedbackadmin.utilities;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.jedit.eastwingfeedbackadmin.activities.DashBoard;
import com.jedit.eastwingfeedbackadmin.activities.LoginActivity;

public class intentUtil {

    public static void toDashboard(Activity activity, String intentAction) {
        Log.e("Intent:","going to dashboard");
        Intent dash_intent = new Intent(activity, DashBoard.class);
        dash_intent.putExtra(AppConstants.INTENT_ACTION_KEY,intentAction);
        dash_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(dash_intent);
        activity.finish();
    }

    public static void toLogin(Activity activity) {
        Log.e("Intent:","going to Login");
        Intent login_intent = new Intent(activity, LoginActivity.class);
        login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(login_intent);
        activity.finish();
    }

}
