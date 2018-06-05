package com.example.dima.bsofttask.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.common.ItemClickListener;
import com.example.dima.bsofttask.mvp.model.Comment;
import com.example.dima.bsofttask.remote.Service;
import com.example.dima.bsofttask.ui.MainActivity;
import com.google.gson.JsonObject;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    TextView comment,date;
    ItemClickListener itemClickListener;

    public CommentViewHolder(View itemView) {
        super(itemView);
        comment = itemView.findViewById(R.id.title_comment);
        date = itemView.findViewById(R.id.date_comment);

        itemView.setOnLongClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class CommentsAdapter  extends RecyclerView.Adapter<CommentViewHolder>{
    private Context context;
    private List<Comment> commentList;
    private int imageId;
    public CommentsAdapter(Context context, List<Comment> commentList,int imageId) {
        this.commentList = commentList;
        this.context = context;
        this.imageId = imageId;
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

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                // ругается на тему
                if(isLongClick) {
//                    int idComment = commentList.get(holder.getAdapterPosition()).getId();
//                    deletePosition(imageId, idComment);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle(R.string.sure_delete)
//                            .setCancelable(true)
//                            .setPositiveButton(R.string.ok,
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            int idComment = commentList.get(holder.getAdapterPosition()).getId();
//                                            //deletePosition(imageId, idComment); ругается на
//
//                                            commentList.remove(holder.getAdapterPosition());
//                                            notifyItemRemoved(holder.getAdapterPosition());
//                                            notifyItemRangeChanged(holder.getAdapterPosition(), commentList.size());
//
//                                            dialog.cancel();
//                                        }
//                                    })
//                            .setNegativeButton(R.string.return_dialog,
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//
//                    AlertDialog alert = builder.create();
//                    alert.show();
                }
            }
        });
    }
    private void deletePosition(int imageId, int commentId)
    {
        Service service = Common.getRetrofitService();
        service.deleteComment(Common.token,imageId,commentId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Toasty.info(context,"Delete").show();
                }
                else
                {
                    Toasty.error(context,response.errorBody().toString()).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toasty.error(context,t.getMessage()).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
