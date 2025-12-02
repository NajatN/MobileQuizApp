package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import com.example.quizapp.Models.Question;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentDoubleQuestionBinding;

public class DoubleQuestionFragment extends Fragment {

    private static final String ARG_QUESTION1 = "question1";
    private static final String ARG_QUESTION2 = "question2";
    private Question question1, question2;
    RadioGroup radioGroupQuestion1;
    RadioGroup radioGroupQuestion2;


    FragmentDoubleQuestionBinding binding;

    public DoubleQuestionFragment() {
        // Required empty public constructor
    }

    public static DoubleQuestionFragment newInstance(Question question1, Question question2) {
        DoubleQuestionFragment fragment = new DoubleQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION1, question1);
        args.putParcelable(ARG_QUESTION2, question2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question1 = getArguments().getParcelable(ARG_QUESTION1);
            question2 = getArguments().getParcelable(ARG_QUESTION2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoubleQuestionBinding.inflate(inflater, container, false);

        if (question1 != null) {
            QuestionFragment questionFragment1 = QuestionFragment.newInstance(question1);
            radioGroupQuestion1= binding.question1.findViewById(R.id.answersGroupQuestion2);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.question1, questionFragment1)
                    .commit();
        }

        if (question2 != null) {
            QuestionFragment questionFragment2 = QuestionFragment.newInstance(question2);
            radioGroupQuestion2= binding.question2.findViewById(R.id.answersGroupQuestion2);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.question2, questionFragment2)
                    .commit();

        }

        return binding.getRoot();
    }


}