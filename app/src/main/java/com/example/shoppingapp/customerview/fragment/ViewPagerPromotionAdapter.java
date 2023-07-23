package com.example.shoppingapp.customerview.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.categories.Categories;
import com.example.shoppingapp.customerview.message.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerPromotionAdapter extends RecyclerView.Adapter<ViewPagerPromotionAdapter.ImageViewHolder> {

    private List<String> imageList;
    private Context context;

    // Constructor và các phương thức khác...
    public ViewPagerPromotionAdapter(Context context)
    {
        this.context = context;
    }

    public void setData(List<String> messageList)
    {
        this.imageList = messageList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_image_promotion, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String currentImage = imageList.get(position);
         Picasso.get().load(currentImage).into(holder.imageView);
        // Glide.with(holder.itemView.getContext()).load(currentImage.getImageUrl()).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
