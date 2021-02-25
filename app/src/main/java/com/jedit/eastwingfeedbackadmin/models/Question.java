package com.jedit.eastwingfeedbackadmin.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

@Entity(tableName = AppConstants.TABLE_QUESTIONS)
public class Question {

    @PrimaryKey()
    @ColumnInfo(name = AppConstants.COLUMN_ID)
    @NonNull
    private String id = "";

    @ColumnInfo(name = AppConstants.COLUMN_TIME_STAMP)
    private long time_Stamp_Millis;

    @ColumnInfo(name = AppConstants.COLUMN_DETAILS)
    private String details;

    @ColumnInfo(name = AppConstants.COLUMN_DEPARTMENT_ID)
    private String department_id;

    @Ignore
    public Question() {
    }

    public Question(@NonNull String id, String department_id, long time_Stamp_Millis,
                    String details) {
        this.id = id;
        this.time_Stamp_Millis = time_Stamp_Millis;
        this.details = details;
        this.department_id = department_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getTime_Stamp_Millis() {
        return time_Stamp_Millis;
    }

    public void setTime_Stamp_Millis(long time_Stamp_Millis) {
        this.time_Stamp_Millis = time_Stamp_Millis;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
}
