package com.example.dima.bsofttask.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.mvp.model.Comment;

import java.util.List;

class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView comment,date;

    public CommentViewHolder(View itemView) {
        super(itemView);
        comment = itemView.findViewById(R.id.title_comment);
        date = itemView.findViewById(R.id.date_comment);
    }
}

public class CommentsAdapter  extends RecyclerView.Adapter<CommentViewHolder>{
    private Context context;
    private List<Comment> commentList;

    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.comment_card_layout, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {
        holder.comment.setText(commentList.get(position).getText());
        holder.date.setText(commentList.get(position).getDateFormat());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
