package com.example.quizapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp.Activities.MainActivity;
import com.example.quizapp.Models.Question;
import com.example.quizapp.MyApp;
import com.example.quizapp.QuizDBHelper;
import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentResultsBinding;
import com.example.quizapp.ViewModels.QuizViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsFragment extends Fragment {
    private FragmentResultsBinding binding;
    private QuizViewModel quizViewModel;
    private QuizDBHelper dbHelper;
    private String level;
    private float score;

    private String user_input;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        dbHelper = new QuizDBHelper();
        quizViewModel = ((MyApp) getActivity().getApplication()).quizViewModel;

        if (getArguments() != null) {
            level = getArguments().getString("level", "");
            score = getArguments().getFloat("percentage", 0);
        }

//        ProgressBar progress = binding.progressBar;
//        progress.setProgress((int) score);

        binding.progressBar.setProgress((int) score);

        binding.textViewProgress.setText((int) score+ "%");

        //set image icon based on score
        if(score < 60){
            binding.imageView2.setImageResource(R.drawable.bad);
        }else if(score >= 60 && score < 90){
            binding.imageView2.setImageResource(R.drawable.good);
        }
        else if(score >= 90){
            binding.imageView2.setImageResource(R.drawable.great);
        }

        List<Question> questionList = quizViewModel.getLevelQuestions();
        displayQuestionsWithAnswers(questionList);

        binding.backButton.setOnClickListener(v -> navigateBackToStart());
        binding.shareButton.setOnClickListener(v -> shareScore());
        binding.inputButton.setOnClickListener(v -> openInputDialog());

        return view;
    }

    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Enter Your Feedback");

        // Set up the input
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user_input = input.getText().toString();
                Log.d("User Input", "User Input: " + user_input);

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

    private void displayQuestionsWithAnswers(List<Question> questionList) {
        Map<String, String> answersWithQuestions = quizViewModel.getAnswersWithQuestions();
        int questionNumber = 1;
        for (Question question : questionList) {
            View questionView = getLayoutInflater().inflate(R.layout.question_item, null);

            TextView questionNumberTextView = questionView.findViewById(R.id.question_number);
            TextView questionTextView = questionView.findViewById(R.id.question);
            questionNumberTextView.setText(questionNumber + ".");
            questionTextView.setText(question.getQuestion());

            ImageView correctImageView = questionView.findViewById(R.id.correct);
            ImageView wrongImageView = questionView.findViewById(R.id.wrong);

            if((question.getCorrectAnswer().equals("A1") && "Option A".equals(answersWithQuestions.get(question.getQuestion())))|| (question.getCorrectAnswer().equals("A2") && "Option B".equals(answersWithQuestions.get(question.getQuestion())))|| (question.getCorrectAnswer().equals("A3") && "Option C".equals(answersWithQuestions.get(question.getQuestion())))|| (question.getCorrectAnswer().equals("A4") && "Option D".equals(answersWithQuestions.get(question.getQuestion())))){

                questionTextView.setText(question.getQuestion());

                correctImageView.setVisibility(View.VISIBLE);
            }
            else {
                questionTextView.setText(question.getQuestion());
                wrongImageView.setVisibility(View.VISIBLE);

                RadioGroup answersGroup = questionView.findViewById(R.id.answersGroup);
                List<String> options = new ArrayList<>();
                options.add(question.getA1());
                options.add(question.getA2());
                options.add(question.getA3());
                options.add(question.getA4());

                for (int i = 0; i < options.size(); i++) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(options.get(i));
                    radioButton.setEnabled(false);
                    if("A1".equals(question.getCorrectAnswer()) && i==0){
                        radioButton.setTextColor(Color.GREEN);
                    }else if("A2".equals(question.getCorrectAnswer()) && i==1) {
                        radioButton.setTextColor(Color.GREEN);
                    }else if("A3".equals(question.getCorrectAnswer()) && i==2){
                        radioButton.setTextColor(Color.GREEN);
                    }else if("A4".equals(question.getCorrectAnswer()) && i==3){
                        radioButton.setTextColor(Color.GREEN);
                    }

                    if("Option A".equals(answersWithQuestions.get(question.getQuestion())) && i==0){
                        radioButton.setTextColor(Color.RED);
                        radioButton.setChecked(true);
                    }else if("Option B".equals(answersWithQuestions.get(question.getQuestion())) && i==1) {
                        radioButton.setTextColor(Color.RED);
                        radioButton.setChecked(true);
                    }else if("Option C".equals(answersWithQuestions.get(question.getQuestion())) && i==2){
                        radioButton.setTextColor(Color.RED);
                        radioButton.setChecked(true);
                    }else if("Option D".equals(answersWithQuestions.get(question.getQuestion())) && i==3){
                        radioButton.setTextColor(Color.RED);
                        radioButton.setChecked(true);
                    }
                    answersGroup.addView(radioButton);
                }
            }

            binding.questionsLayout.addView(questionView);
            questionNumber++;
        }
    }
    public void AddtoDbResult(int score){
        Map<String, Object> Result1 = new HashMap<>();
        Result1.put("Name", quizViewModel.getCurrentUserName());
        Result1.put("Score", score);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
        String dateString = dateFormat.format(currentDate);
        Result1.put("Date", dateString);
        Result1.put("Level",level);
        Result1.put("Feedback",user_input);


        dbHelper.addToHistory(
                Result1,
                documentReference -> Log.d("Store", "DocumentSnapshot added with ID: " + documentReference.getId()),
                e -> Log.w("Store", "Error adding document", e)
        );
    }

    private void navigateBackToStart() {
        // Navigate back to StartFragment or relevant fragment
        AddtoDbResult((int) score);
        ((MainActivity)getActivity()).showFragment(new StartFragment());
    }

    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "I scored " + (int) score + "% on the "+level+ " quiz! Can you beat me?");
        startActivity(Intent.createChooser(intent, "Share using"));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ResultsFragment", "onDestroyView");
        binding.progressBar.setProgress(0);
    }

}
