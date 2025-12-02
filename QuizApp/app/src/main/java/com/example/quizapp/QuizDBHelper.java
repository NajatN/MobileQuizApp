package com.example.quizapp;

import com.example.quizapp.Models.Question;
import com.example.quizapp.Models.QuizHistoryItem;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class QuizDBHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getQuestionsByLevel(String level, Consumer<List<Question>> onSuccess, Consumer<Exception> onFailure) {
        db.collection("Questions")
                .whereEqualTo("Level", level)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Question> fetchedQuestions = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        fetchedQuestions.add(document.toObject(Question.class));
                    }
                    onSuccess.accept(fetchedQuestions);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public void fetchHistory(Consumer<List<QuizHistoryItem>> onSuccess, Consumer<Exception> onFailure) {
        db.collection("History")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<QuizHistoryItem> historyItems = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String dateTaken = document.getString("Date");
                        Long scoreLong = document.getLong("Score");
                        int score = scoreLong != null ? scoreLong.intValue() : 0;
                        String quizTitle = document.getString("Name");
                        String level = document.getString("Level");
                        QuizHistoryItem historyItem = new QuizHistoryItem(quizTitle, score, dateTaken,level);
                        historyItems.add(historyItem);
                    }
                    onSuccess.accept(historyItems);
                })
                .addOnFailureListener(onFailure::accept);
    }

    public void deleteAllHistory(Consumer<Void> onSuccess, Consumer<Exception> onFailure) {
        db.collection("History")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        db.collection("History").document(document.getId())
                                .delete()
                                .addOnSuccessListener(aVoid -> onSuccess.accept(null))
                                .addOnFailureListener(onFailure::accept);
                    }
                })
                .addOnFailureListener(onFailure::accept);
    }

    public void addToHistory(Map<String, Object> historyData, Consumer<DocumentReference> onSuccess, Consumer<Exception> onFailure) {
        db.collection("History")
                .add(historyData)
                .addOnSuccessListener(onSuccess::accept)
                .addOnFailureListener(onFailure::accept);
    }

}
