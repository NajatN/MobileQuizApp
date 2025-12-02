package com.example.quizapp;

import android.app.Application;

import com.example.quizapp.ViewModels.QuizViewModel;

public class MyApp extends Application {
    public QuizViewModel quizViewModel;

    public QuizViewModel getQuizViewModel() {
        return quizViewModel;
    }
}