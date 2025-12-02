package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.MyApp;
import com.example.quizapp.databinding.FragmentChooseLevelBinding;
import com.example.quizapp.ViewModels.QuizViewModel;

public class ChooseLevelFragment extends Fragment {

    private FragmentChooseLevelBinding binding;
    private QuizViewModel quizViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseLevelBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        binding.chooseLevelTextView.setText("Hi " + quizViewModel.getCurrentUserName() + "! Choose a Level");

        setupLevelButtons();

        return view;
    }

    private void setupLevelButtons() {
        binding.easyButton.setOnClickListener(view -> startQuiz("Easy"));
        binding.mediumButton.setOnClickListener(view -> startQuiz("Medium"));
        binding.hardButton.setOnClickListener(view -> startQuiz("Hard"));
    }

    private void startQuiz(String level) {
        LoadingFragment loadingFragment = new LoadingFragment();
        Bundle args = new Bundle();
        args.putString("level", level);
        loadingFragment.setArguments(args);
        ((MainActivity)getActivity()).showFragment(loadingFragment);
    }

}
