package com.jedit.eastwingfeedbackadmin.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jedit.eastwingfeedbackadmin.models.Department;
import com.jedit.eastwingfeedbackadmin.models.Feedback;
import com.jedit.eastwingfeedbackadmin.models.Question;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;

import java.util.List;

@Dao
public interface Feedback_Access_Obj {

    //===============================================DEPARTMENT=====================================
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_AllDepartment(List<Department> department);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_single_Department(Department department);

    @Query("SELECT * FROM " + AppConstants.TABLE_DEPARTMENTS)
    List<Department> getAllDepartments();
    //===============================================DEPARTMENT=====================================

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-QUESTIONS=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_AllQuestions(List<Question> questions);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_Single_question(Question questions);

    @Query("SELECT * FROM " + AppConstants.TABLE_QUESTIONS + " WHERE " + AppConstants.DEPARTMENT_ID_KEY + " = :departmentId")
    List<Question> getDepartment_questions(String departmentId);

    @Query("SELECT * FROM " + AppConstants.TABLE_QUESTIONS)
    List<Question> getAllQuestions();
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-QUESTIONS=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    //-------------------------------------------FEEDBACK-------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert_feedback(Feedback feedback);

    //Count all feed backs
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS)
    int total_sum_count();

    //Count all good feed backs
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE " + AppConstants.COLUMN_FEEDBACK
            + " == '" + AppConstants.FEEDBACK_GOOD + "'")
    int total_good_sum_count();

    // Count all bad feed backs
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE " + AppConstants.COLUMN_FEEDBACK
            + " == '" + AppConstants.FEEDBACK_BAD + "'")
    int total_bad_sum_count();

    // Count all good feed backs for specific department
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_FEEDBACK + " == '" + AppConstants.FEEDBACK_GOOD + "' AND "
            + AppConstants.COLUMN_DEPARTMENT_ID + " = :departmentID" )
    int total_good_department_count(String departmentID);

    // Count all bad feed backs for specific department
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_FEEDBACK + " == '" + AppConstants.FEEDBACK_BAD + "' AND "
            + AppConstants.COLUMN_DEPARTMENT_ID + " = :departmentID" )
    int total_bad_department_count(String departmentID);

    // Count all feed backs for specific department
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_DEPARTMENT_ID + " = :departmentID" )
    int total_department_count(String departmentID);

    // Count all good feed backs for specific question
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_FEEDBACK + " == '" + AppConstants.FEEDBACK_GOOD + "' AND "
            + AppConstants.COLUMN_QUESTION_ID + " = :questionID" )
    int total_good_question_count(String questionID);

    // Count all bad feed backs for specific question
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_FEEDBACK + " == '" + AppConstants.FEEDBACK_BAD + "' AND "
            + AppConstants.COLUMN_QUESTION_ID + " = :questionID" )
    int total_bad_question_count(String questionID);

    // Count all feed backs for specific question
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_QUESTION_ID + " = :questionID" )
    int total_question_count(String questionID);

    //todo Count by feed back, department, question, date
    // Count by feed back, department, date
    // Count by feed back, question, date

    // Count by department, date
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_DATE + " BETWEEN :startDate AND :endDate AND "
            + AppConstants.COLUMN_DEPARTMENT_ID + " = :departmentID" )
    int total_department_date_count(String departmentID, String startDate, String endDate);


    // Count by question, date
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_DATE + " BETWEEN :startDate AND :endDate AND "
            + AppConstants.COLUMN_QUESTION_ID + " = :questionID" )
    int total_question_date_count(String questionID, String startDate, String endDate);

    // Count by date
    @Query("SELECT COUNT(*) FROM " + AppConstants.TABLE_FEEDBACKS + " WHERE "
            + AppConstants.COLUMN_DATE + " BETWEEN :startDate AND :endDate")
    int total_date_count(String startDate, String endDate);
    //-------------------------------------------FEEDBACK-------------------------------------------


}
