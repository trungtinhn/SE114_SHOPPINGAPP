package com.example.shoppingapp.customerview.review;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.message.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewDataAdapter extends RecyclerView.Adapter<ReviewDataAdapter.ReviewHolder> {
    List<ReViewData> data;
    private Context context;
    public void setData(List<ReViewData> data) {
        this.data = data;
        notifyDataSetChanged();

    }
    public ReviewDataAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_review, parent, false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        ReViewData reViewData = data.get(position);
        if(reViewData == null) return;

        Picasso.get().load(reViewData.getAvatar()).into(holder.imageAvatar);
        holder.txtName.setText(reViewData.getName());
        holder.txtTime.setText(reViewData.getTime());
        holder.txtContent.setText(reViewData.getContent());

        holder.rating.setRating(reViewData.getRating());

        if (reViewData.getImage() == ""){
            holder.imageRating.setVisibility(View.GONE);
        } else{
            Picasso.get().load(reViewData.getImage()).into(holder.imageRating);
        }




    }

    @Override
    public int getItemCount() {
        if(data != null) return data.size();
        return 0;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{
        private ImageView imageAvatar, imageRating;
        private TextView txtName, txtTime, txtContent;
        private RatingBar rating;

        public ReviewHolder( @NonNull View item){
            super(item);
            imageAvatar = item.findViewById(R.id.imageAvatarReview);
            txtName = item.findViewById(R.id.txtNameReview);
            txtTime = item.findViewById(R.id.txtTimeReview);

            txtContent = item.findViewById(R.id.txtContentReview);
            rating = item.findViewById(R.id.ratingReview);
            imageRating = item.findViewById(R.id.imageRatingReview);
        }
    }
}
