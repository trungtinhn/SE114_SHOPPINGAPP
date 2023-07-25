package com.example.shoppingapp.StaffView.FinancialReport.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Home.home_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_financial extends AppCompatActivity {
    private EditText startDay, endDay;
    private Button view_report;
    private TextView doanhthu, sodonhang;
    private FirebaseFirestore firestore;
    private ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_financial);

        firestore = FirebaseFirestore.getInstance();

        startDay = findViewById(R.id.edtStartDate);
        endDay = findViewById(R.id.edtEndDate);
        view_report = findViewById(R.id.view_report);
        doanhthu = findViewById(R.id.doanhthu);
        sodonhang = findViewById(R.id.sodonhang);
        doanhthu.setText("0");
        sodonhang.setText("0");
        ic_back = findViewById(R.id.imgbtn_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_financial.this, home_page.class);
                startActivity(intent);
            }
        });

        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDay);
            }
        });

        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDay);
            }
        });
        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDateStr = startDay.getText().toString();
                String endDateStr = endDay.getText().toString();

                if (!startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    try {
                        Date startDate = dateFormat.parse(startDateStr);
                        Date endDate = dateFormat.parse(endDateStr);

                        getDeliveredOrdersAndCalculateRevenue(startDate, endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity_financial.this, "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn ngày tháng năm
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        String selectedDate = dateFormat.format(calendar.getTime());

                        // Hiển thị ngày đã chọn lên trường EditText
                        editText.setText(selectedDate);

                        // Kiểm tra ngày kết thúc phải lớn hơn ngày bắt đầu
                        if (editText == endDay) {
                            if (calendar.getTimeInMillis() <= Calendar.getInstance().getTimeInMillis()) {
                                Toast.makeText(activity_financial.this, "Ngày kết thúc phải lớn hơn ngày bắt đầu.", Toast.LENGTH_SHORT).show();
                                editText.setText("");
                            }
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void getDeliveredOrdersAndCalculateRevenue(Date startDate, Date endDate) {
        firestore.collection("DONHANG")
                .whereEqualTo("TrangThai", "Delivered")
                .whereGreaterThanOrEqualTo("NgayDatHang", startDate)
                .whereLessThanOrEqualTo("NgayDatHang", endDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            long totalRevenue = 0;
                            int orderCount = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Assume you have a field "TongTien" for the total amount in each order
                                Long orderRevenue = document.getLong("TongTien");
                                if (orderRevenue != null) {
                                    totalRevenue += orderRevenue;
                                    orderCount++;
                                }
                            }

                            // Display the total revenue and order count in respective TextViews
                            doanhthu.setText(String.valueOf(totalRevenue));
                            sodonhang.setText(String.valueOf(orderCount));
                        } else {
                            Toast.makeText(activity_financial.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
