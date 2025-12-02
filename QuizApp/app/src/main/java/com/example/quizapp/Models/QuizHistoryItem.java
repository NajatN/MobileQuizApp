package com.example.quizapp.Models;

public class QuizHistoryItem {
    private String quizTitle;
    private int score;
    private String dateTaken;
    private String level;

    // Constructor
    public QuizHistoryItem(String quizTitle, int score, String dateTaken,String level) {
        this.quizTitle = quizTitle;
        this.score = score;
        this.dateTaken = dateTaken;
        this.level=level;
    }

    // Getters and setters
    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
