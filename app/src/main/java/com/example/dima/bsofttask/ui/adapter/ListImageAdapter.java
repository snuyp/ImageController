package com.example.dima.bsofttask.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.ItemClickListener;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.ui.InfoActivity;

import java.util.List;

class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ItemClickListener itemClickListener;
    TextView title;
    ImageView image;

    public ItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_photo_name);
        image = itemView.findViewById(R.id.image);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}


public class ListImageAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private Context context;
    private List<Image> listImage;

    public ListImageAdapter(Context context, List<Image> listImage) {
        this.listImage = listImage;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.photo_card_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        holder.title.setText(listImage.get(position).getDateFormat());
        Glide.with(holder.itemView)
                .load(listImage.get(position).getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_terrain_black_24dp))
                .into(holder.image);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("image",listImage.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }
}