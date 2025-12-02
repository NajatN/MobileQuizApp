package com.example.quizapp.Fragments;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp.Models.Question;
import com.example.quizapp.MyApp;
import com.example.quizapp.R;
import com.example.quizapp.ViewModels.QuizViewModel;
import com.example.quizapp.databinding.FragmentQuestionBinding;

import java.util.List;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION = "question";
    private Question question;

    FragmentQuestionBinding binding;
    private QuizViewModel quizViewModel;


    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;
        }
        if (getArguments() != null) {
            question = getArguments().getParcelable(ARG_QUESTION);
        }
    }
    private String selectedAnswer = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);

        if (question != null) {
            if (getQuestionPosition(question)!=-1) {
                binding.questionText.setText(getQuestionPosition(question) + ". " + question.getQuestion());
            }else{
                binding.questionText.setText(question.getQuestion());
            }


            binding.optionA.setText(question.getA1());
            binding.optionB.setText(question.getA2());
            binding.optionC.setText(question.getA3());
            binding.optionD.setText(question.getA4());

            // Restore the selected answer
            restoreSelectedAnswer();
        }

        binding.answersGroupQuestion2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleAnswerSelection(checkedId);
            }
        });

        return binding.getRoot();
    }

    private void restoreSelectedAnswer() {
        String savedAnswer = quizViewModel.getAnswerForQuestion(question.getQuestion());
        if (savedAnswer != null) {
            switch (savedAnswer) {
                case "Option A":
                    binding.optionA.setChecked(true);
                    break;
                case "Option B":
                    binding.optionB.setChecked(true);
                    break;
                case "Option C":
                    binding.optionC.setChecked(true);
                    break;
                case "Option D":
                    binding.optionD.setChecked(true);
                    break;
            }
        }
    }

    private void handleAnswerSelection(int checkedId) {
        switch (checkedId) {
            case R.id.optionA:
                selectedAnswer = "Option A";
                break;
            case R.id.optionB:
                selectedAnswer = "Option B";
                break;
            case R.id.optionC:
                selectedAnswer = "Option C";
                break;
            case R.id.optionD:
                selectedAnswer = "Option D";
                break;
        }
        quizViewModel.addAnswer(question.getQuestion(), selectedAnswer);
    }

    private int getQuestionPosition(Question question) {
        List<Question> questions = quizViewModel.getLevelQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestion().equals(question.getQuestion())) {
                return i + 1; // Adding 1 because list indexes start at 0
            }
        }
        return -1; // Return -1 or some error value if the question is not found
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("selectedAnswer", selectedAnswer);
//    }
//
//    @Override
//    public void onViewStateRestored(Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null && savedInstanceState.containsKey("selectedAnswer")) {
//            selectedAnswer = savedInstanceState.getString("selectedAnswer");
//        }
//    }

}