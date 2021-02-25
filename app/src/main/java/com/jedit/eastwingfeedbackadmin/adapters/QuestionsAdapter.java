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
import com.jedit.eastwingfeedbackadmin.models.Question;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter {

    private final List<Question> questionList;
    static Fragment fragment;

    public QuestionsAdapter(List<Question> questionList, Fragment fragment) {
        this.questionList = questionList;
        QuestionsAdapter.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_questions, parent, false);
        return new departments_list_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((departments_list_holder) holder).bind_views(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class departments_list_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_row_question_name;
        ImageButton ib_qrow_delete;
        WeakReference<Context> weak_mContext;
        Question globalQuestion;

        departments_list_holder(View itemView) {
            super(itemView);

            weak_mContext = new WeakReference<>(itemView.getContext());
            tv_row_question_name = itemView.findViewById(R.id.tv_row_question_name);
            ib_qrow_delete = itemView.findViewById(R.id.ib_qrow_delete);

            ib_qrow_delete.setOnClickListener(this);
            tv_row_question_name.setOnClickListener(this);
        }

        void bind_views(Question question) {
            globalQuestion = question;
            tv_row_question_name.setText(question.getDetails());
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.ib_row_delete) {
                //Todo delete question from list
            }


//            if (weak_mcontext.get() instanceof Duty_RosterActivity){
//                //Toast.makeText(weak_mcontext.get(),"Adapter contect matches Duty activity", Toast.LENGTH_LONG).show();
//                Fragment fragment = new Add_team_fragment();
//                ((Duty_RosterActivity) weak_mcontext.get()).loadFragment(fragment,bname);
//            }
        }

    }
}
