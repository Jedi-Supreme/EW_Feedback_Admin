package com.jedit.eastwingfeedbackadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jedit.eastwingfeedbackadmin.R;
import com.jedit.eastwingfeedbackadmin.fragments.DepartmentSetupFragment;
import com.jedit.eastwingfeedbackadmin.models.Department;

import java.lang.ref.WeakReference;
import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter {

    private final List<Department> departmentList;
    static Fragment fragment;

    public DepartmentsAdapter(List<Department> departmentList, Fragment fragment) {
        this.departmentList = departmentList;
        DepartmentsAdapter.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_departments, parent, false);
        return new departments_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((departments_list_holder) holder).bind_views(departmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public static class departments_list_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_row_department_name;
        ImageButton ib_row_delete, ib_row_add_question;
        WeakReference<Context> weak_mContext;
        Department globalDepartment;

        departments_list_holder(View itemView) {
            super(itemView);

            weak_mContext = new WeakReference<>(itemView.getContext());
            tv_row_department_name = itemView.findViewById(R.id.tv_row_department_name);
            ib_row_delete = itemView.findViewById(R.id.ib_row_delete);
            ib_row_add_question = itemView.findViewById(R.id.ib_row_add_question);

            ib_row_add_question.setOnClickListener(this);
            ib_row_delete.setOnClickListener(this);
            tv_row_department_name.setOnClickListener(this);
        }

        void bind_views(Department department) {
            globalDepartment = department;
            tv_row_department_name.setText(department.getName());
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.ib_row_delete){
                //Todo delete department from list
            }else if (v.getId() == R.id.ib_row_add_question){

                //Todo show question add dialog
                //Todo Add questions by passing department id to fragment
                if (fragment instanceof DepartmentSetupFragment){
//                ((DepartmentSetupFragment)fragment).toQuestionFragment(globalDepartment);
                    ((DepartmentSetupFragment)fragment).showQuestion_Dialog(globalDepartment);
                }

            }else if (v.getId() == R.id.tv_row_department_name){
                //Todo show dialog list of questions for selected department
                ((DepartmentSetupFragment)fragment).showQuestions_list(globalDepartment.getId());
            }


//            if (weak_mcontext.get() instanceof Duty_RosterActivity){
//                //Toast.makeText(weak_mcontext.get(),"Adapter contect matches Duty activity", Toast.LENGTH_LONG).show();
//                Fragment fragment = new Add_team_fragment();
//                ((Duty_RosterActivity) weak_mcontext.get()).loadFragment(fragment,bname);
//            }
        }

    }
}
