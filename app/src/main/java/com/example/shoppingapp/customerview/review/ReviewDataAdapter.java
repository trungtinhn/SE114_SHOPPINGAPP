package com.example.shoppingapp.customerview.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

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
        if(reViewData != null) return;

        holder.avatar.setImageResource(reViewData.getIavartar());
        holder.name.setText(reViewData.getName());
        holder.time.setText(reViewData.getTime());
        holder.tb.setText(reViewData.getDanhgiatb());
        holder.stardanhgia.setNumStars(reViewData.getIdanhgiatb());
        holder.noidung.setText(reViewData.getDescription());
        holder.product.setImageResource(reViewData.getImagecmt());

    }

    @Override
    public int getItemCount() {
        if(data != null) return data.size();
        return 0;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar, product;
        private TextView name, time, tb, noidung;
        private RatingBar stardanhgia;

        public ReviewHolder( @NonNull View item){
            super(item);
            avatar = item.findViewById(R.id.image);
            name = item.findViewById(R.id.txt_name);
            time = item.findViewById(R.id.txt_ngay);
            tb = item.findViewById(R.id.txt_trungbinh);
            noidung = item.findViewById(R.id.text_noidung);
            stardanhgia = item.findViewById(R.id.ratingBar);
            product = item.findViewById(R.id.image_product);
        }
    }
}
