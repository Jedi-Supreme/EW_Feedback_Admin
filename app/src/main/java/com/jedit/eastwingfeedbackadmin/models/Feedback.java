package com.jedit.eastwingfeedbackadmin.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

@Entity(tableName = AppConstants.TABLE_FEEDBACKS)
public class Feedback {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppConstants.COLUMN_ID)
    private int id;

    @ColumnInfo(name = AppConstants.COLUMN_TIME_STAMP)
    private long timeStamp;

    @ColumnInfo(name = AppConstants.COLUMN_DEPARTMENT_ID)
    private String department_id;

    @ColumnInfo(name = AppConstants.COLUMN_QUESTION_ID)
    private String question_id;

    @ColumnInfo(name = AppConstants.COLUMN_FEEDBACK)
    private String feedback;

    @ColumnInfo(name = AppConstants.COLUMN_DATE)
    private String date; //yyyy-MM-dd HH:mm:ss

    @Ignore
    public Feedback() {
    }

    public Feedback(String department_id, String question_id, String feedback, String date, long timeStamp) {
        this.timeStamp = timeStamp;
        this.department_id = department_id;
        this.question_id = question_id;
        this.feedback = feedback;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
