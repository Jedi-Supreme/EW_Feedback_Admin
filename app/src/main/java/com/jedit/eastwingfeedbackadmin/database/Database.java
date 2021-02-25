package com.jedit.eastwingfeedbackadmin.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jedit.eastwingfeedbackadmin.interfaces.Feedback_Access_Obj;
import com.jedit.eastwingfeedbackadmin.models.*;

@androidx.room.Database(entities = {Department.class, Question.class, Feedback.class}, exportSchema = false, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database appDB_instance;

    public static Database getInstance(Context context){

        if (appDB_instance == null){

            String DB_NAME = "Admin_DB";
            appDB_instance = Room.databaseBuilder(context.getApplicationContext(),Database.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return appDB_instance;
    }

    public abstract Feedback_Access_Obj feedbackDAO();

}
