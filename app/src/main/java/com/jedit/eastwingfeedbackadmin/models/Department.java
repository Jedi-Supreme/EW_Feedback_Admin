package com.jedit.eastwingfeedbackadmin.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

import java.util.List;

@Entity(tableName = AppConstants.TABLE_DEPARTMENTS, indices = {@Index(value = AppConstants.COLUMN_NAME, unique = true)})
public class Department {

    @PrimaryKey
    @ColumnInfo(name = AppConstants.COLUMN_ID)
    @NonNull
    private String id = "";

    @ColumnInfo(name = AppConstants.COLUMN_NAME)
    private String name;

    @Ignore
    private List<Question> questions;

    @Ignore
    public Department() {
    }

    public Department(@NonNull String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Department(@NonNull String id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
