package com.example.quizapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private String Question;
    private String A1, A2, A3, A4;
    private String CorrectAnswer;
    private String Level;

    // Constructors, getters, setters
    public Question() {
    }

    public Question(String Question, String A1, String A2, String A3, String A4, String CorrectAnswer, String Level) {
        this.Question = Question;
        this.A1 = A1;
        this.A2 = A2;
        this.A3 = A3;
        this.A4 = A4;
        this.CorrectAnswer = CorrectAnswer;
        this.Level = Level;
    }

    protected Question(Parcel in) {
        Question = in.readString();
        A1 = in.readString();
        A2 = in.readString();
        A3 = in.readString();
        A4 = in.readString();
        CorrectAnswer = in.readString();
        Level = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Question);
        parcel.writeString(A1);
        parcel.writeString(A2);
        parcel.writeString(A3);
        parcel.writeString(A4);
        parcel.writeString(CorrectAnswer);
        parcel.writeString(Level);
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }

    public String getA4() {
        return A4;
    }

    public void setA4(String a4) {
        A4 = a4;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String CorrectAnswer) {
        this.CorrectAnswer = CorrectAnswer;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }
}