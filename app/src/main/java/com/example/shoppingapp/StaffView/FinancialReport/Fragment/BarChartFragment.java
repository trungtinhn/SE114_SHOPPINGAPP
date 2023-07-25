package com.example.shoppingapp.StaffView.FinancialReport.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shoppingapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartFragment extends Fragment {
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_financial_report, container, false);
        barChart = view.findViewById(R.id.bc_act);

        // Lấy dữ liệu từ Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference donhangRef = db.collection("DONHANG");
        donhangRef.whereEqualTo("TrangThai", "Delivered").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<BarEntry> entries = new ArrayList<>();
                    Map<String, Float> dataMap = new HashMap<>();

                    // Duyệt qua các tài liệu trả về từ Firestore
                    for (DocumentSnapshot document : task.getResult()) {
                        // Lấy ngày giao hàng và tổng tiền từ tài liệu
                        String ngayGiaoHang = document.getString("NgayGiaoHang");
                        float tongTien = document.getLong("TongTien").floatValue();

                        // Tính tổng tiền của các đơn hàng cùng ngày giao hàng
                        if (dataMap.containsKey(ngayGiaoHang)) {
                            dataMap.put(ngayGiaoHang, dataMap.get(ngayGiaoHang) + tongTien);
                        } else {
                            dataMap.put(ngayGiaoHang, tongTien);
                        }
                    }

                    // Chuyển dữ liệu sang BarEntry
                    int index = 0;
                    for (Map.Entry<String, Float> entry : dataMap.entrySet()) {
                        entries.add(new BarEntry(index, entry.getValue()));
                        index++;
                    }

                    // Tạo một BarDataSet từ dữ liệu và đặt tên cho DataSet này (ví dụ: "Tổng tiền")
                    BarDataSet barDataSet = new BarDataSet(entries, "Tổng tiền");

                    // Thiết lập màu sắc cho biểu đồ cột
                    barDataSet.setColor(Color.BLUE);

                    // Gom nhóm các cột lại thành một nhóm (nếu cần)
                    BarData barData = new BarData(barDataSet);
                    barData.setBarWidth(0.9f); // Đặt khoảng cách giữa các nhóm cột

                    // Xoá các khoảng trắng phía dưới và phía trên của biểu đồ
                    barChart.setFitBars(true);

                    // Thiết lập dữ liệu vào biểu đồ
                    barChart.setData(barData);

                    // Cấu hình trục X (ngày giao hàng)
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(dataMap.keySet()));
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục X ở phía dưới biểu đồ

                    // Cấu hình trục Y (tổng tiền)
                    YAxis yAxis = barChart.getAxisLeft(); // Trục Y ở bên trái biểu đồ

                    // Hiển thị biểu đồ
                    barChart.invalidate();
                } else {
                    // Xử lý lỗi nếu cần thiết
                }
            }
        });

        return view;
    }
}
