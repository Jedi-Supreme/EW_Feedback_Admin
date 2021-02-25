package com.jedit.eastwingfeedbackadmin.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

import java.util.List;

public class DepartmentAndQuestions {

    @Embedded
    public Department department;

    @Relation(parentColumn = AppConstants.COLUMN_QUESTION_ID, entityColumn = AppConstants.COLUMN_DEPARTMENT_ID,
            entity = Question.class)
    public List<Question> questions;
}
