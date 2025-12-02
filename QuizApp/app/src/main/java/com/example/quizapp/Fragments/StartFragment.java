package com.example.quizapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.MyApp;
import com.example.quizapp.ViewModels.QuizViewModel;
import com.example.quizapp.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private QuizViewModel quizViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        binding.start.setOnClickListener(v -> showNameDialog());
        binding.history.setOnClickListener(v -> viewHistory());

        return view;
    }

    private void startQuiz() {
        ((MainActivity)getActivity()).showFragment(new ChooseLevelFragment());
    }

    private void viewHistory() {
        ((MainActivity)getActivity()).showFragment(new HistoryFragment());
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Enter Your Name");

        // Set up the input
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userName = input.getText().toString();

                // if the user name is empty, use "Anonymous" as the user name
                if (userName.isEmpty()) {
                    userName = "Anon";
                }

                quizViewModel.clearAnswers();
                quizViewModel.setCurrentUserName(userName);
                startQuiz();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
