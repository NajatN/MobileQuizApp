package com.example.quizapp.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.quizapp.Models.Question;
import com.example.quizapp.QuizDBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizViewModel extends ViewModel {
    private QuizDBHelper dbHelper = new QuizDBHelper();
    private MutableLiveData<Map<String, List<Question>>> questionsByLevel = new MutableLiveData<>();
    private static Map<String, String> answers = new HashMap<>();
    private static List<Question> levelQuestions = new ArrayList<>();
    private String currentUserName;

    public void fetchQuestionsByLevel(String level) {
        dbHelper.getQuestionsByLevel(level, fetchedQuestions -> {
            processFetchedQuestions(fetchedQuestions, level);
        }, e -> {
            // Handle errors here, e.g., log or show error message
        });
    }

    private void processFetchedQuestions(List<Question> fetchedQuestions, String level) {
        Collections.shuffle(fetchedQuestions); // Randomly shuffle the list
        List<Question> questionsToUse = fetchedQuestions.size() > 10 ? fetchedQuestions.subList(0, 10) : fetchedQuestions;

        Map<String, List<Question>> currentData = questionsByLevel.getValue();
        if (currentData == null) {
            currentData = new HashMap<>();
        }
        currentData.put(level, questionsToUse);
        questionsByLevel.setValue(currentData);
        levelQuestions = questionsToUse;
    }
    public LiveData<Map<String, List<Question>>> getQuestionsByLevel() {
        return questionsByLevel;
    }
    public List<Question> getLevelQuestions(){return levelQuestions;}


    public void addAnswer(String question, String answer) {

        answers.put(question, answer);
        Log.d("AnswersMap", "Answers Map: " + answers.toString());
    }
    public Map<String, String> getAnswersWithQuestions(){
        return answers;
    }
    public List<String> getAnswers() {
        List<String> orderedAnswers = new ArrayList<>();
        for (Question question : levelQuestions) {
            String questionText = question.getQuestion();
            String answer = answers.get(questionText);

            Log.d("QuestionText", "Question Text: " + questionText);
            Log.d("Answer", "Answer: " + answer);
            if (answer != null) {
                orderedAnswers.add(answer);
            } else {
                orderedAnswers.add("");
            }
        }
        Log.d("AnswersMap", "Answers Map: " + answers.toString());
        return orderedAnswers;
    }

    public void clearAnswers() {
        answers.clear();
    }

    public String getAnswerForQuestion(String questionText) {
        return answers.get(questionText);
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }
}