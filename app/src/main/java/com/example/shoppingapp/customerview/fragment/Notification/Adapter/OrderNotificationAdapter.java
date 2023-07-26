package com.example.shoppingapp.customerview.fragment.Notification.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.fragment.Notification.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderNotificationAdapter extends RecyclerView.Adapter<OrderNotificationAdapter.ProductsViewHolder> {
    private List<Notification> notificationList;
    private Context context;

    public OrderNotificationAdapter(List<Notification> notificationList, Context context) {
        this.context = context;
        this.notificationList = notificationList;
        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        String UserId = FirebaseAuth.getInstance().getUid();
        CollectionReference thongbaoRef = FirebaseFirestore.getInstance().collection("THONGBAODONHANG");
        thongbaoRef.whereEqualTo("MaND", UserId).addSnapshotListener((querySnapshot, error) -> {
            if (error != null) {
                // Xử lý khi có lỗi xảy ra
                return;
            }

            if (querySnapshot != null) {
                notificationList.clear();
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    String MaDH = document.getString("MaDH");
                    String MaND = document.getString("MaND");
                    String MaTB = document.getString("MaTB");
                    String TBO = document.getString("TBO");
                    CollectionReference mathongbaoRef = FirebaseFirestore.getInstance().collection("MATHONGBAO");
                    mathongbaoRef.whereEqualTo("MaTB", MaTB).addSnapshotListener((KMquerySnapshot, error1) -> {
                        for (DocumentSnapshot documentSnapshot : KMquerySnapshot.getDocuments()) {
                            String Noidung = documentSnapshot.getString("NoiDung");
                            String LoaiTB = documentSnapshot.getString("LoaiTB");
                            String AnhTB = documentSnapshot.getString("AnhTB");
                            Notification notification = new Notification(AnhTB, LoaiTB, Noidung, MaTB, TBO, MaDH, MaND);
                            notificationList.add(notification);
                        }
                        notifyDataSetChanged();
                    });
                }
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_order, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.id.setText(notification.getMaDH());
        holder.detail.setText(notification.getNoiDung());
        Picasso.get().load(notification.getAnhTB()).into(holder.ava);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private ImageView ava;
        private TextView detail;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.ID_order);
            detail = itemView.findViewById(R.id.txtContentNotification_order);
            ava = itemView.findViewById(R.id.imgAvt_order);
        }
    }
}
