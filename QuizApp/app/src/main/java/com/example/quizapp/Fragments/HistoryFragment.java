package com.example.quizapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Adapters.QuizHistoryAdapter;
import com.example.quizapp.Models.QuizHistoryItem;
import com.example.quizapp.QuizDBHelper;
import com.example.quizapp.databinding.FragmentHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private QuizHistoryAdapter adapter;
    private List<QuizHistoryItem> historyItems;
    private QuizDBHelper dbHelper;
    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        dbHelper = new QuizDBHelper();
        setupRecyclerView();
        fetchHistory();

        binding.clearHistoryButton.setOnClickListener(v -> {
            historyItems.clear();
            adapter.notifyDataSetChanged();
            deleteAllDataFromCollection();
        });

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        historyItems = new ArrayList<>();
        adapter = new QuizHistoryAdapter(historyItems);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.historyRecyclerView.setAdapter(adapter);
    }

    private void fetchHistory() {
        dbHelper.fetchHistory(
                fetchedItems -> {
                    historyItems.clear();
                    historyItems.addAll(fetchedItems);
                    adapter.notifyDataSetChanged();
                },
                e -> Log.d("Firestore", "Error getting documents: ", e)
        );
    }

    private void deleteAllDataFromCollection() {
        dbHelper.deleteAllHistory(
                aVoid -> {
                    // Handle success, e.g., show a message

                },
                e -> Log.d("DELETE", "Error deleting documents: ", e)
        );
    }
}
