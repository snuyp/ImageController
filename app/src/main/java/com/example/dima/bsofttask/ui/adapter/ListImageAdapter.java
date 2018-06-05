package com.example.dima.bsofttask.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.common.ItemClickListener;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.remote.Service;
import com.example.dima.bsofttask.ui.InfoActivity;
import com.example.dima.bsofttask.ui.fragment.PhotoFragment;
import com.google.gson.JsonObject;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    ItemClickListener itemClickListener;
    TextView title;
    ImageView image;
    public ItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_photo_name);
        image = itemView.findViewById(R.id.image);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
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
                if(!isLongClick) {
                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra("image", listImage.get(position));
                    context.startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.sure_delete)
                            .setCancelable(true)
                            .setPositiveButton(R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int idImage = listImage.get(holder.getAdapterPosition()).getId();
                                            deletePosition(idImage, 0);//потом подправить
                                            listImage.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            notifyItemRangeChanged(holder.getAdapterPosition(), listImage.size());

                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton(R.string.return_dialog,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    private void deletePosition(int id, int page)
    {
        Service service = Common.getRetrofitService();
        service.deleteImage(Common.token,id,page).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                     Toasty.success(context,"").show();
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
}