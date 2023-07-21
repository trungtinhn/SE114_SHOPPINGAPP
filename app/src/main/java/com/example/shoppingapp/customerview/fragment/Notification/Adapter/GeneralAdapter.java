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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ProductsViewHolder> {
    private List<Notification> notificationList;
    private Context context;

    public GeneralAdapter(List<Notification> notificationList, Context context) {
        this.context = context;
        this.notificationList = notificationList;
        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        CollectionReference thongbaoRef = FirebaseFirestore.getInstance().collection("THONGBAO");

        thongbaoRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                notificationList.clear();

                for (DocumentSnapshot document : task.getResult()) {
                    // Lấy các trường dữ liệu từ document
                    // Lấy mảng địa chỉ ảnh
                    String MaKM = document.getString("MaKM");
                    String MaTB = document.getString("MaTB");
                    String TB = document.getString("TB");
                    CollectionReference mathongbaoRef = FirebaseFirestore.getInstance().collection("MATHONGBAO");
                    mathongbaoRef.whereEqualTo("MaTB", MaTB).get().addOnSuccessListener(querySnapshot ->{
                       for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments())
                       {
                           String Noidung = documentSnapshot.getString("NoiDung");
                           String LoaiTB = documentSnapshot.getString("LoaiTB");
                           CollectionReference khuyenmaiRef = FirebaseFirestore.getInstance().collection("KHUYENMAI");
                           khuyenmaiRef.whereEqualTo("MaKM", MaKM).get().addOnSuccessListener(KMquerySnapshot ->{
                              for(DocumentSnapshot kmdocumentSnapshot : KMquerySnapshot.getDocuments())
                              {
                                  String HinhAnhTB = kmdocumentSnapshot.getString("HinhAnhTB");
                                  String HinhAnhKM = kmdocumentSnapshot.getString("HinhAnhKM");

                                  String LoaiKhuyenMai = kmdocumentSnapshot.getString("LoaiKhuyenMai");
                                  String TenKM = kmdocumentSnapshot.getString("ChiTietKM");
                                  Notification notification = new Notification(HinhAnhKM, HinhAnhTB, MaTB, MaKM, Noidung, TB, LoaiTB, TenKM);
                                  notificationList.add(notification);
                              }
                               notifyDataSetChanged();
                           });
                           notifyDataSetChanged();
                       }
                       notifyDataSetChanged();
                    });
                    notifyDataSetChanged();
                    // Thêm đối tượng Product vào danh sách
                }

                notifyDataSetChanged();
            } else {
                // Xử lý khi không thành công
                Exception exception = task.getException();
                // ...
            }
        });
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.name.setText(notification.getTenKM());
        holder.detail.setText(notification.getNoiDung());
        Picasso.get().load(notification.getHinhAnhTB()).into(holder.pic_no);
        Picasso.get().load(notification.getHinhAnhKM()).into(holder.ava);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView ava, pic_no;
        private TextView detail;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtNameNotification);
            detail = itemView.findViewById(R.id.txtContentNotification);
            ava = itemView.findViewById(R.id.imgAvt);
            pic_no = itemView.findViewById(R.id.imgNoti);


        }
    }
}
