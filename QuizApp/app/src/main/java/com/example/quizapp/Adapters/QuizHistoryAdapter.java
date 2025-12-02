package com.example.quizapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Models.QuizHistoryItem;
import com.example.quizapp.R;

import java.util.List;

public class QuizHistoryAdapter extends RecyclerView.Adapter<QuizHistoryAdapter.QuizHistoryViewHolder> {

    private List<QuizHistoryItem> historyItems;

    public QuizHistoryAdapter(List<QuizHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public QuizHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_history_item, parent, false);
        return new QuizHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHistoryViewHolder holder, int position) {
        QuizHistoryItem item = historyItems.get(position);
        holder.quizTitleTextView.setText((position+1)+". "+item.getQuizTitle());
        holder.scoreTextView.setText("Score: " + item.getScore());
        holder.dateTextView.setText("Date: " + item.getDateTaken());
        if (item.getLevel().equals("Easy")){
            holder.level.setImageResource(R.drawable.easy);

        }else if(item.getLevel().equals("Medium")) {
            holder.level.setImageResource(R.drawable.medium);
        }else{
            holder.level.setImageResource(R.drawable.hard);

        }

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    static class QuizHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView quizTitleTextView;
        TextView scoreTextView;
        TextView dateTextView;
        ImageView level;

        QuizHistoryViewHolder(View itemView) {
            super(itemView);
            quizTitleTextView = itemView.findViewById(R.id.quizTitle);
            scoreTextView = itemView.findViewById(R.id.score);
            dateTextView = itemView.findViewById(R.id.dateTaken);
            level = itemView.findViewById(R.id.level);

        }
    }
}
