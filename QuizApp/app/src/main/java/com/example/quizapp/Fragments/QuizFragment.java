package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.Adapters.QuestionPagerAdapter;
import com.example.quizapp.Models.Question;
import com.example.quizapp.MyApp;
import com.example.quizapp.databinding.FragmentQuizBinding;
import com.example.quizapp.ViewModels.QuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    private QuestionPagerAdapter adapter;
    private QuizViewModel quizViewModel;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static final long COUNTDOWN_INTERVAL = 1000;
    private String level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        if (getArguments() != null) {
            level = getArguments().getString("level", "");
        }

        setupViewPager();
        setupViewModel();

        if (savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("TIME_LEFT_IN_MILLIS", 1200000); // Default time
            final int currentPage = savedInstanceState.getInt("CURRENT_PAGE", 0);
            binding.viewPager.post(() -> binding.viewPager.setCurrentItem(currentPage));
        } else {
            timeLeftInMillis = 1200000; // 20 minutes in milliseconds
        }


        startTimer();

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.submitButton.setEnabled(position == adapter.getItemCount() - 1);
            }
        });

        binding.submitButton.setOnClickListener(v -> submitQuiz());

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                submitQuiz();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        binding.timerTextView.setText("Time: " + timeLeftFormatted);
    }

    private void submitQuiz() {

        LoadingResultsFragment loadingResultsFragment = new LoadingResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("level", level);
        loadingResultsFragment.setArguments(bundle);
        ((MainActivity)getActivity()).showFragment(loadingResultsFragment);


    }

    private void setupViewPager() {
        adapter = new QuestionPagerAdapter(getChildFragmentManager(), getLifecycle(), new ArrayList<>());
        binding.viewPager.setAdapter(adapter);
    }

    private void setupViewModel() {
        quizViewModel.getQuestionsByLevel().observe(getViewLifecycleOwner(), questionsByLevel -> {
            if (questionsByLevel != null && questionsByLevel.containsKey(level)) {
                List<Question> questions = questionsByLevel.get(level);
                populateViewPagerWithQuestions(questions);
            }
        });
    }

    private void populateViewPagerWithQuestions(List<Question> questions) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < questions.size(); i += 2) {
            Question question1 = questions.get(i);
            Question question2 = (i + 1 < questions.size()) ? questions.get(i + 1) : null;
            fragments.add(DoubleQuestionFragment.newInstance(question1, question2));
        }
        adapter.setFragments(fragments);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("TIME_LEFT_IN_MILLIS", timeLeftInMillis);
//        outState.putInt("CURRENT_PAGE", binding.viewPager.getCurrentItem());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

}
