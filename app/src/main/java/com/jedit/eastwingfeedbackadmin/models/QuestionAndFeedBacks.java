package com.jedit.eastwingfeedbackadmin.models;

import androidx.room.Embedded;
import androidx.room.Relation;
import androidx.room.Transaction;

import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

import java.util.List;

public class QuestionAndFeedBacks {

    @Embedded
    public Question question;

    @Relation(parentColumn = AppConstants.COLUMN_ID, entityColumn = AppConstants.COLUMN_QUESTION_ID, entity = Feedback.class)
    public List<Feedback> feedBacks;

}
