package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.MyApp;
import com.example.quizapp.ViewModels.QuizViewModel;
import com.example.quizapp.databinding.FragmentLoadingBinding;

public class LoadingFragment extends Fragment {

    private FragmentLoadingBinding binding;
    private QuizViewModel quizViewModel;
    private String level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        // Retrieve the level argument
        if (getArguments() != null) {
            level = getArguments().getString("level", "");
        }

        // Simulate loading process
        simulateLoading();

        return view;
    }


    private void simulateLoading() {
        quizViewModel.fetchQuestionsByLevel(level);

        // Simulate loading (e.g., fetch questions for the selected level)
        new Handler().postDelayed(() -> {
            // Navigate to QuizFragment
            QuizFragment quizFragment = new QuizFragment();
            Bundle args = new Bundle();
            args.putString("level", level);
            quizFragment.setArguments(args);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity)getActivity()).showFragment(quizFragment);
            }
        }, 2000); // 2000 milliseconds delay for loading
    }
}
