package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.Models.Question;
import com.example.quizapp.MyApp;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentLoadingResultsBinding;
import com.example.quizapp.ViewModels.QuizViewModel;

import java.util.List;

public class LoadingResultsFragment extends Fragment {
    private FragmentLoadingResultsBinding binding;
    private QuizViewModel quizViewModel;
    private static final int LOADING_TIME_MS = 2000;
    private float score;
    private String level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoadingResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        if (getArguments() != null) {
            level = getArguments().getString("level", "");
        }

        score = calculateUserScore();

        // Apply animation to the ImageView
        ImageView drumRollImage = binding.drumRollImage;
        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        drumRollImage.startAnimation(rotateAnimation);

        simulateLoadingResults();

        return view;
    }

    private float calculateUserScore() {
        List<String> userAnswers = getFragmentUserAnswer();
        Log.d("QuizResult", "test size2: " + userAnswers.get(0));
        List<Question> questions = quizViewModel.getLevelQuestions();

        int userScore = calculateScore(questions, userAnswers);
        float percentage = ((float) userScore / (questions.size())) * 100;
        Log.d("QuizResult", "test size3: " + userAnswers.get(0));
        Log.d("QuizResult", "User Score: " + userScore);
        Log.d("QuizResult", "Percentage: " + percentage + "%");
        return percentage;
    }
    private List<String> getFragmentUserAnswer() {
        return quizViewModel.getAnswers();
    }
    private int calculateScore(List<Question> questions, List<String> userAnswers) {
        int score = 0;

        for (int i = 0; i < userAnswers.size(); i++) {
            if ((questions.get(i).getCorrectAnswer().equals("A1") && userAnswers.get(i).equals("Option A"))||
                    (questions.get(i).getCorrectAnswer().equals("A2") && userAnswers.get(i).equals("Option B"))||
                    (questions.get(i).getCorrectAnswer().equals("A3") && userAnswers.get(i).equals("Option C"))||
                    (questions.get(i).getCorrectAnswer().equals("A4") && userAnswers.get(i).equals("Option D"))) {
                score++;
            }
            Log.d("loop 1", "loop: " + userAnswers.get(i));
            Log.d("loop 1", "loop: " + questions.get(i).getCorrectAnswer());
        }

        return score;
    }

    private void simulateLoadingResults() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToResultsScreen()     ;
            }
        }, LOADING_TIME_MS);
    }


    private void navigateToResultsScreen() {
        ResultsFragment resultsFragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putFloat("percentage", score);
        args.putString("level", level);
        resultsFragment.setArguments(args);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).showFragment(resultsFragment);
        }
    }

}
