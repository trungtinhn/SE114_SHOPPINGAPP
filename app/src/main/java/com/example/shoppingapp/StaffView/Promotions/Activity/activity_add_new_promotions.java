package com.example.shoppingapp.StaffView.Promotions.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_add_new_promotions extends AppCompatActivity {
    private ImageButton btn_back;
    private EditText name, detail, minimum, kind, rate;
    private EditText start_day, end_day;
    private Timestamp start, end;
    private FirebaseFirestore db_khuyenmai;
    private Button btn_add_new;
    private ImageView image_promotion, image_tb;
    private Uri imageUri; // Để lưu trữ đường dẫn hình ảnh đã chọn
    private String imageUrl;
    private Uri image_tb_uri;
    private String hinhanhTB;
    private static final int REQUEST_CODE_PROMOTION_IMAGE = 1;
    private static final int REQUEST_CODE_THONG_BAO_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotions);
        btn_back = findViewById(R.id.btn_back);
        image_promotion = findViewById(R.id.imageView);
        name = findViewById(R.id.tenKM);
        detail = findViewById(R.id.chitiet_km);
        minimum = findViewById(R.id.dontoi_thieu);
        kind = findViewById(R.id.loaikhuyenmai);
        rate = findViewById(R.id.tile);
        db_khuyenmai = FirebaseFirestore.getInstance();
        image_tb = findViewById(R.id.image_thongbao);
        start_day = findViewById(R.id.start);
        end_day = findViewById(R.id.end);
        image_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(activity_add_new_promotions.this).crop().compress(1024).start(REQUEST_CODE_THONG_BAO_IMAGE);
            }
        });
        image_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng ImagePicker để chọn hình ảnh
                ImagePicker.with(activity_add_new_promotions.this)
                        .crop()
                        .compress(1024) // Kích thước tối đa cho hình ảnh đã chọn (byte)
                        .start(REQUEST_CODE_PROMOTION_IMAGE);
            }
        });

        start_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        end_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_new_promotions.this, activity_promotions.class);
                startActivity(intent);
            }
        });

        btn_add_new = findViewById(R.id.btn_add_new);
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageThongBaoToFirebase();
                uploadImageToFirebase();
                savePromotionDataToFirestore();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });

    }
    private void showDatePickerDialog(final boolean isStart) {
        // Lấy ngày hiện tại để hiển thị trên DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Khi người dùng chọn ngày tháng năm, lưu giá trị vào biến tương ứng
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);
                        int selectedHour = selectedCalendar.get(Calendar.HOUR_OF_DAY);
                        int selectedMinute = selectedCalendar.get(Calendar.MINUTE);

                        // Đặt giờ và phút cho biến start hoặc end tùy thuộc vào trường hợp
                        if (isStart) {
                            selectedCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            selectedCalendar.set(Calendar.MINUTE, selectedMinute);
                            start = new Timestamp(selectedCalendar.getTime());
                            start_day.setText(formatTimestamp(start));
                        } else {
                            selectedCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            selectedCalendar.set(Calendar.MINUTE, selectedMinute);
                            end = new Timestamp(selectedCalendar.getTime());
                            end_day.setText(formatTimestamp(end));
                        }
                    }
                },
                year, month, day
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private String formatTimestamp(Timestamp timestamp) {
        // Phương thức này để định dạng Timestamp thành chuỗi ngày tháng năm giờ phút
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(timestamp.toDate());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PROMOTION_IMAGE) {
                imageUri = data.getData();
                Glide.with(this)
                        .load(imageUri)
                        .into(image_promotion);

                // Gọi hàm uploadImageToFirebase() để tải ảnh khuyến mãi lên Firebase Storage
                uploadImageToFirebase();
            } else if (requestCode == REQUEST_CODE_THONG_BAO_IMAGE) {
                image_tb_uri = data.getData();
                Glide.with(this)
                        .load(image_tb_uri)
                        .into(image_tb);

                // Gọi hàm uploadImageThongBaoToFirebase() để tải ảnh thông báo lên Firebase Storage
                uploadImageThongBaoToFirebase();
            }
        }
    }



    private void uploadImageThongBaoToFirebase() {
        if (image_tb_uri != null) {
            // Tạo tên duy nhất cho hình ảnh thông báo (ví dụ: "thongbao_image_2023_07_20_15_30_45.jpg")
            String fileName = "thongbao_image_" + System.currentTimeMillis() + ".jpg";

            // Tham chiếu đến Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("ImageThongBao").child(fileName);

            // Chuyển đổi hình ảnh thành mảng byte để tải lên
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_tb_uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageData = baos.toByteArray();

                // Tải lên hình ảnh thông báo lên Firebase Storage
                UploadTask uploadTask = storageRef.putBytes(imageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Lấy đường dẫn tới hình ảnh thông báo đã tải lên
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                // Khi tải lên thành công, bạn có thể lưu đường dẫn downloadUrl vào biến hinhanhTB
                                hinhanhTB = downloadUrl.toString();
                                Log.d("UploadImage", "Image URL (ThongBao): " + hinhanhTB);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi nếu tải lên thất bại
                        Log.e("UploadImage", "Upload failed: " + e.getMessage()); // Log the upload failure for debugging
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Hiển thị thông báo nếu chưa chọn ảnh thông báo
            Toast.makeText(activity_add_new_promotions.this, "Vui lòng chọn ảnh thông báo", Toast.LENGTH_SHORT).show();
        }
    }


    // Phương thức để tải lên hình ảnh lên Firebase Storage
    private void uploadImageToFirebase() {
        if (imageUri != null) {
            // Tạo tên duy nhất cho hình ảnh (ví dụ: "promotion_image_2023_07_20_15_30_45.jpg")
            String fileName = "promotion_image_" + System.currentTimeMillis() + ".jpg";

            // Tham chiếu đến Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("ImagePromotion").child(fileName);

            // Chuyển đổi hình ảnh thành mảng byte để tải lên
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageData = baos.toByteArray();

                // Tải lên hình ảnh lên Firebase Storage
                UploadTask uploadTask = storageRef.putBytes(imageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Lấy đường dẫn tới hình ảnh đã tải lên
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                // Khi tải lên thành công, bạn có thể sử dụng đường dẫn downloadUrl để lưu trữ trong imageUrl
                                imageUrl = downloadUrl.toString();
                                Log.d("UploadImage", "Image URL: " + imageUrl);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi nếu tải lên thất bại
                        Log.e("UploadImage", "Upload failed: " + e.getMessage()); // Log the upload failure for debugging
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Hiển thị thông báo nếu chưa chọn hình ảnh
            Toast.makeText(activity_add_new_promotions.this, "Vui lòng chọn hình ảnh khuyến mãi", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePromotionDataToFirestore() {
        // Lấy thông tin khuyến mãi từ các EditText (name, detail, minimum, kind, rate, start, end)
        String promotionName = name.getText().toString();
        String promotionDetail = detail.getText().toString();
        String kindValue = kind.getText().toString();

        // Kiểm tra xem trường minimum và rate có chưa dữ liệu và có chứa giá trị số hợp lệ không
        String minimumStr = minimum.getText().toString();
        String rateStr = rate.getText().toString();

        if (minimumStr.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(activity_add_new_promotions.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int minimumValue;
        double rateValue;
        try {
            minimumValue = Integer.parseInt(minimumStr);
            rateValue = Double.parseDouble(rateStr);
        } catch (NumberFormatException e) {
            Toast.makeText(activity_add_new_promotions.this, "Vui lòng nhập giá trị số hợp lệ cho Đơn tối thiểu và Tỉ lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        // Tạo một Map chứa thông tin khuyến mãi để thê


        // Kiểm tra xem ngày bắt đầu và ngày kết thúc đã được chọn hay chưa
        if (start == null || end == null) {
            // Hiển thị thông báo nếu ngày bắt đầu hoặc kết thúc chưa được chọn
            Toast.makeText(activity_add_new_promotions.this, "Vui lòng chọn ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra ràng buộc ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu
        if (end.compareTo(start) < 0) {
            // Hiển thị thông báo nếu ngày kết thúc nhỏ hơn ngày bắt đầu
            Toast.makeText(activity_add_new_promotions.this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu", Toast.LENGTH_SHORT).show();
            return;
        }
        if(imageUrl == null)
        {
            Toast.makeText(activity_add_new_promotions.this, "Bạn chưa thêm hình ảnh cho chương trình giảm giá", Toast.LENGTH_SHORT).show();
            return;
        }
        if(hinhanhTB == null)
        {
            Toast.makeText(activity_add_new_promotions.this, "Bạn chưa thêm hình ảnh để thông báo khi hiển thị giảm giá cho sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        // Tạo một Map chứa thông tin khuyến mãi để thêm vào Firestore
        Map<String, Object> promotionData = new HashMap<>();
        promotionData.put("TenKM", promotionName);
        promotionData.put("ChiTietKM", promotionDetail);
        promotionData.put("DonToiThieu", minimumValue);
        promotionData.put("LoaiKhuyenMai", kindValue);
        promotionData.put("TiLe", rateValue);
        promotionData.put("NgayBatDau", start);
        promotionData.put("NgayKetThuc", end);
        promotionData.put("HinhAnhKM", imageUrl); // Lưu đường dẫn hình ảnh vào Firestore
        promotionData.put("HinhAnhTB", hinhanhTB);


        // Thực hiện thêm dữ liệu vào collection "KHUYENMAI" trong Firestore
        db_khuyenmai.collection("KHUYENMAI")
                .add(promotionData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Lấy ID của document sau khi thêm dữ liệu thành công
                        String maKM = documentReference.getId();
                        // Thêm ID của document vào trường "MaKM"
                        db_khuyenmai.collection("KHUYENMAI").document(maKM)
                                .update("MaKM", maKM) // Cập nhật trường "MaKM" với giá trị ID
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Xử lý thành công
                                        Toast.makeText(activity_add_new_promotions.this, "Thêm khuyến mãi thành công", Toast.LENGTH_SHORT).show();

                                        // Chuyển về màn hình danh sách khuyến mãi sau khi thêm thành công
                                        Intent intent = new Intent(activity_add_new_promotions.this, activity_promotions.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Xử lý lỗi nếu có khi cập nhật trường "MaKM"
                                        Toast.makeText(activity_add_new_promotions.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        db_khuyenmai.collection("MATHONGBAO")
                                .whereEqualTo("LoaiTB", kindValue) // Compare with "kindValue"
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                        String maTB = document.getId(); // MaTB from the matching document
                                        // Create notification data
                                        Map<String, Object> thongBao = new HashMap<>();
                                        thongBao.put("MaTB", maTB);
                                        thongBao.put("MaKM", maKM);
                                        // Add the notification to "THONGBAO" collection
                                        db_khuyenmai.collection("THONGBAO")
                                                .add(thongBao)
                                                .addOnSuccessListener(documentSetTB -> {
                                                    String thongBaoId= documentSetTB.getId();
                                                    thongBao.put("TB", thongBaoId);
                                                    documentSetTB.set(thongBao)
                                                            .addOnSuccessListener(aVoid -> {
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                // Xử lý lỗi khi cập nhật tài liệu "THONGBAODONHANG"
                                                            });
                                                    // Notification added successfully
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Handle the failure if adding notification fails
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Handle the failure if querying "MATHONGBAO" fails
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi nếu có khi thêm dữ liệu vào Firestore
                        Toast.makeText(activity_add_new_promotions.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
