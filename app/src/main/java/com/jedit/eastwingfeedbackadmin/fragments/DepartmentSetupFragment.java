package com.jedit.eastwingfeedbackadmin.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.jedit.eastwingfeedbackadmin.R;
import com.jedit.eastwingfeedbackadmin.adapters.DepartmentsAdapter;
import com.jedit.eastwingfeedbackadmin.adapters.QuestionsAdapter;
import com.jedit.eastwingfeedbackadmin.crud.ReadData;
import com.jedit.eastwingfeedbackadmin.crud.WriteData;
import com.jedit.eastwingfeedbackadmin.models.Company;
import com.jedit.eastwingfeedbackadmin.models.Department;
import com.jedit.eastwingfeedbackadmin.models.Question;
import com.jedit.eastwingfeedbackadmin.utilities.AppConstants;
import com.jedit.eastwingfeedbackadmin.utilities.commonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DepartmentSetupFragment extends Fragment implements View.OnClickListener {

    TextInputEditText et_setup_department_company_name;
    Button bt_department_setup_add, bt_department_setup_next;
    RecyclerView recycler_departments_setup;
    ViewGroup frag_View_group;
    List<Department> departments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_company_setup,container,false);

        et_setup_department_company_name = view.findViewById(R.id.et_setup_department_company_name);
        bt_department_setup_add = view.findViewById(R.id.bt_department_setup_add);
        bt_department_setup_next = view.findViewById(R.id.bt_department_setup_next);
        recycler_departments_setup = view.findViewById(R.id.recycler_departments_setup);

        frag_View_group = container;

        bt_department_setup_add.setOnClickListener(this);
        bt_department_setup_next.setOnClickListener(this);

        return view;
    }

    //Will be tested later if necessary, use dialog instead
    public void toQuestionFragment(Department department){

        FragmentTransaction transaction;
        Bundle question_bundle = new Bundle();

        question_bundle.putString(AppConstants.DEPARTMENT_ID_KEY,department.getId());
        question_bundle.putString(AppConstants.DEPARTMENT_NAME_KEY,department.getName());

        QuestionsSetupFragment question_fragment = new QuestionsSetupFragment();
        question_fragment.setArguments(question_bundle);

        if (getActivity() != null){
            transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.frame_dashboard,question_fragment);
            transaction.addToBackStack(AppConstants.QUESTION_SETUP_KEY);
            transaction.commit();
        }

    }

    public void showQuestion_Dialog(Department department){

        if (getContext() != null){
            AlertDialog questionDialog = new AlertDialog.Builder(getContext()).create();

            View qnView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_question_setup, frag_View_group,false);
            questionDialog.setView(qnView);

            TextInputEditText et_question_setup = qnView.findViewById(R.id.et_question_setup);
            Button bt_question_setup_save = qnView.findViewById(R.id.bt_question_setup_save);
            Button bt_question_setup_close = qnView.findViewById(R.id.bt_question_setup_close);

            bt_question_setup_save.setOnClickListener(view -> {
                
                if (!Objects.requireNonNull(et_question_setup.getText()).toString().isEmpty()){

                    String question_uuid = UUID.fromString(Objects.requireNonNull(et_question_setup.getText()).toString()).toString();
                    Question question = new Question(question_uuid,department.getId(),System.currentTimeMillis(),et_question_setup.getText().toString());
                    commonUtils.getDBExecutor().execute(() -> WriteData.saveQuestion_local(getActivity(), question));

                }else {
                    commonUtils.MySnackBar(frag_View_group,"Enter a valid question", Snackbar.LENGTH_SHORT).show();
                }
            });

            //Close dialog
            bt_question_setup_close.setOnClickListener(view -> questionDialog.dismiss());

            questionDialog.show();
        }
    }

    //Method to show dialog for adding departments
    public void showDepartment_Dialog(){

        if (getContext() != null){
            AlertDialog questionDialog = new AlertDialog.Builder(getContext()).create();
            departments = new ArrayList<>();

            View qnView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_department_setup, frag_View_group,false);
            questionDialog.setView(qnView);

            TextInputEditText et_department_setup = qnView.findViewById(R.id.et_department_setup);
            Button bt_department_setup_save = qnView.findViewById(R.id.bt_department_setup_save);
            Button bt_department_setup_close = qnView.findViewById(R.id.bt_department_setup_close);

            //Save button for adding department
            bt_department_setup_save.setOnClickListener(view -> {

                if (!Objects.requireNonNull(et_department_setup.getText()).toString().isEmpty()){

                    String department_uuid = UUID.fromString(Objects.requireNonNull(et_setup_department_company_name.getText()).toString()).toString();
                    Department department = new Department(department_uuid,et_department_setup.getText().toString().toUpperCase());
                    departments.add(department);

                    try {
                        WriteData.saveDepartment_local(getActivity(),department);
                        //Todo refresh department list after saving
                        populate_department_list(departments);
                    }catch (Exception e){
                        Log.e("Save Department", "Department saving failed with error: " + e.toString());
                        commonUtils.MySnackBar(frag_View_group,"Department name already exists.", Snackbar.LENGTH_SHORT).show();
                    }

                }else {
                    commonUtils.MySnackBar(frag_View_group,"Enter a valid department name.", Snackbar.LENGTH_SHORT).show();
                }
            });

            //Close dialog
            bt_department_setup_close.setOnClickListener(view -> questionDialog.dismiss());

            questionDialog.show();
        }
    }

    public void showQuestions_list(String department_id){

        if (getContext() != null){
            AlertDialog questionDialog = new AlertDialog.Builder(getContext()).create();

            View qnView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_question_listview, frag_View_group,false);

            RecyclerView recycler_question_list = qnView.findViewById(R.id.recycler_question_list);
            Button bt_question_list_close = qnView.findViewById(R.id.bt_question_list_close);

            List<Question> dept_questions = ReadData.loadQuestions_local(getActivity(),department_id);

            populate_question_list(recycler_question_list,dept_questions);

            bt_question_list_close.setOnClickListener(view -> questionDialog.dismiss());
            questionDialog.setView(qnView);
        }

    }

    void testInputs(){

        if (Objects.requireNonNull(et_setup_department_company_name.getText()).toString().isEmpty()){
            commonUtils.showError_Input(et_setup_department_company_name);
            commonUtils.MySnackBar(et_setup_department_company_name,"Please enter Company name.", Snackbar.LENGTH_SHORT).show();

        }else if (recycler_departments_setup.getAdapter() == null || recycler_departments_setup.getAdapter().getItemCount() <= 0){

            //Dialog prompt to remind user about empty departments
            showReminderDialog();

        }else {
            finalise_saving();
        }
    }

    void showReminderDialog(){

        if (getContext() != null){
            AlertDialog remindDialog = new AlertDialog.Builder(getContext()).create();

            remindDialog.setTitle("Department");
            remindDialog.setMessage("Proceed without creating departments?");
            remindDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Proceed", (dialogInterface, i) -> finalise_saving());
            remindDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialogInterface, i) -> remindDialog.dismiss());

            remindDialog.show();
        }
    }

    void finalise_saving(){

        Log.e("Saving","Saving company with department and questions");

        Company company = new Company();
        company.setName(Objects.requireNonNull(et_setup_department_company_name.getText()).toString());
        company.setUuid(FirebaseAuth.getInstance().getUid());

        List<Department> departments = ReadData.loadDepartments_local(getActivity());

        if (departments.size() > 0){

            for (Department department : departments){
                List<Question> questions = ReadData.loadQuestions_local(getActivity(),department.getId());

                if (questions.size() > 0){
                    department.setQuestions(questions);
                }
            }

            company.setDepartments(departments);
            Log.e("Save Data","Departments total: " + company.getDepartments().size());
            WriteData.saveCompanyOnline(getActivity(),company);
        }


    }

    void populate_department_list(List<Department> departments){

        if (departments != null && departments.size() > 0){

            if (getActivity() != null){
                DepartmentsAdapter departmentsAdapter = new DepartmentsAdapter(departments,new DepartmentSetupFragment());
                recycler_departments_setup.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_departments_setup.setAdapter(departmentsAdapter);
            }
        }

    }

    void populate_question_list(RecyclerView recyclerView , List<Question> questions){

        if (questions != null && questions.size() > 0){

            if (getActivity() != null){
                QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,new DepartmentSetupFragment());
                recycler_departments_setup.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_departments_setup.setAdapter(questionsAdapter);
            }
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.bt_department_setup_add){
            //show department add dialog
            showDepartment_Dialog();
        }else if (view.getId() == R.id.bt_department_setup_next){
            //check inputs and departments list before returning to dashboard
            testInputs();
        }

    }
}
