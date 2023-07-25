package com.example.shoppingapp.StaffView.MyProduct.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Color.Color;
import com.example.shoppingapp.StaffView.Color.adapter_color;
import com.example.shoppingapp.StaffView.MyProduct.Adapter.ImageAdapter;
import com.example.shoppingapp.StaffView.Product;
import com.example.shoppingapp.StaffView.Size.Size;
import com.example.shoppingapp.StaffView.Size.adapter_size;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class activity_edit_product extends AppCompatActivity {
    private ImageAdapter imageAdapter;
    private List<String>imageUrls = new ArrayList<>();
    private List<Color> allColors = new ArrayList<>();
    private List<String> selectedColors = new ArrayList<>();
    private List<Size> allSize = new ArrayList<>();
    private List<String> selectedSizes = new ArrayList<>();
    private EditText name, description, price, amount;
    private String MaSP;
    private RecyclerView recyclerView_color, recyclerView_size, recyclerView_image;
    private ImageView img_add;
    private ImageButton btn_back;
    private LinearLayoutManager layoutManager;
    private Button btn_update, btn_delete;
    private List<String> updatedImageUrls;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        name = findViewById(R.id.edt_name);
        description = findViewById(R.id.edit_decription);
        price = findViewById(R.id.edit_price);
        amount = findViewById(R.id.edit_amount);
        Intent intent = getIntent();

        MaSP = intent.getStringExtra("MaSP");
        recyclerView_color = findViewById(R.id.RCV_color_edit);
        recyclerView_size = findViewById(R.id.RCV_size_edit);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_color.setLayoutManager(layoutManager);
        recyclerView_size.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView_image = findViewById(R.id.recycler_view_images);
        recyclerView_image.setLayoutManager(new GridLayoutManager(this, 3));
        img_add = findViewById(R.id.imgView_add_new_picture);
        btn_back = findViewById(R.id.imgbtn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(activity_edit_product.this, activity_MyProduct.class);
                startActivity(intent_back);
            }
        });
        // Step 1: Lấy danh sách các sản phẩm (SANPHAM) từ Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("SANPHAM");
        productsRef.whereEqualTo("MaSP", MaSP).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot productSnapshot) {
                List<Product> productList = productSnapshot.toObjects(Product.class);
                CollectionReference colorsRef = db.collection("MAUSAC");
                colorsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot colorSnapshot) {
                        allColors.clear();
                        // Duyệt qua danh sách sản phẩm
                        for (Product product : productList) {
                            List<String> currentColors = product.getMauSac();

                            // Duyệt qua danh sách màu sắc
                            for (DocumentSnapshot colorDoc : colorSnapshot) {
                                String colorId = colorDoc.getString("MaMauSac");
                                String colorCode = colorDoc.getString("MaMau");
                                String colorName = colorDoc.getString("TenMau");
                                boolean check = currentColors.contains(colorId);

                                Color color = new Color(colorName, colorCode, colorId, check);

                                allColors.add(color);
                            }
                        }

                        // Step 4: Hiển thị danh sách sản phẩm kết quả lên RecyclerView
                        adapter_color colorAdapter = new adapter_color(allColors);
                        recyclerView_color.setAdapter(colorAdapter);
                    }
                });
                CollectionReference sizeRef = db.collection("SIZE");
                sizeRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot colorSnapshot) {
                        allSize.clear();
                        // Duyệt qua danh sách sản phẩm
                        for (Product product : productList) {
                            List<String> currentSize = product.getSize();

                            // Duyệt qua danh sách màu sắc
                            for (DocumentSnapshot colorDoc : colorSnapshot) {
                                String sizeId = colorDoc.getString("MaSize");

                                String sizeName = colorDoc.getString("Size");
                                boolean check = currentSize.contains(sizeId);

                                Size size = new Size(sizeName, sizeId, check);
                                allSize.add(size);
                            }
                        }

                        // Step 4: Hiển thị danh sách sản phẩm kết quả lên RecyclerView
                        adapter_size sizeAdapter = new adapter_size(allSize);
                        recyclerView_size.setAdapter(sizeAdapter);
                    }
                });
            }
        });

        FirebaseFirestore db_sanpham = FirebaseFirestore.getInstance();
        CollectionReference sanphamRef = db_sanpham.collection("SANPHAM");
        sanphamRef.whereEqualTo("MaSP", MaSP)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        // Xử lý lỗi nếu cần thiết
                        return;
                    }

                    for (DocumentSnapshot document : queryDocumentSnapshots) {

                        imageUrls = (List<String>) queryDocumentSnapshots.getDocuments().get(0).get("HinhAnhSP");
                        imageAdapter = new ImageAdapter(imageUrls);

                        imageAdapter.setOnImageClickListener(new ImageAdapter.OnImageClickListener() {
                            @Override
                            public void onImageClick(int clickedPosition) {
                                // Xóa ảnh tại vị trí đã chọn khi nhấp vào ImageButton
                                if (clickedPosition >= 0 && clickedPosition < imageUrls.size()) {
                                    imageUrls.remove(clickedPosition);
                                    imageAdapter.notifyDataSetChanged();
                                }
                            }

                        });
                        img_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Gọi hộp thoại chọn hình ảnh từ thư viện hoặc camera
                                ImagePicker.Companion.with(activity_edit_product.this)
                                        .cropSquare()
                                        .compress(1700)
                                        .start();

                            }
                        });


                        String tenSP = document.getString("TenSP");
                        name.setText(tenSP);
                        String mota = document.getString("MoTaSP");
                        description.setText(mota);
                        String giaSP = String.valueOf(document.getLong("GiaSP"));
                        price.setText(giaSP);
                        String soluong = String.valueOf(document.getLong("SoLuongSP"));
                        amount.setText(soluong);
                    }
                    recyclerView_image.setAdapter(imageAdapter);
                });


        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageUrls.add(imageUri.toString());
                imageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to get image URI.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateProduct() {
        // Tải ảnh lên Firebase Storage và sau khi hoàn thành, tiến hành cập nhật thông tin sản phẩm
        if (imageUrls.isEmpty() || imageUrls.size() > 3) {
            Toast.makeText(this, "Vui lòng thêm ít nhất một hình ảnh và không được thêm hơn 3 hình ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadImagesAndSaveProduct();
    }
    private void uploadImagesAndSaveProduct() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("ImageSanPham");
        updatedImageUrls = new ArrayList<>();
        List<Task<Uri>> imageUploadTasks = new ArrayList<>();
        List<String> localImageUrls = new ArrayList<>();
        List<String> storageImageUrls = new ArrayList<>();

        for (String imageUrl : imageUrls) {
            if (imageUrl.contains("firebasestorage.googleapis.com")) {
                // Ảnh đã là URL Storage Firebase
                storageImageUrls.add(imageUrl);
            }
            else {
                // Ảnh là URL tệp cục bộ, vì vậy tải lên Firebase Storage
                localImageUrls.add(imageUrl);
                Uri imageUri = Uri.parse(imageUrl); // Thay đổi ở đây
                StorageReference imageRef = storageRef.child(imageUri.getLastPathSegment());
                UploadTask uploadTask = imageRef.putFile(imageUri);
                Task<Uri> downloadUrlTask = uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                });

                imageUploadTasks.add(downloadUrlTask);
            }
        }

        Tasks.whenAllComplete(imageUploadTasks)
                .addOnSuccessListener(taskSnapshots -> {
                    for (Task<Uri> downloadUrlTask : imageUploadTasks) {
                        if (downloadUrlTask.isSuccessful()) {
                            String imageUrlpiu = downloadUrlTask.getResult().toString();
                            updatedImageUrls.add(imageUrlpiu);
                            // Tùy chọn: Bạn cũng có thể loại bỏ URL ảnh cục bộ đã tải lên khỏi danh sách
                            localImageUrls.remove(imageUrlpiu);
                        }
                    }

                    // Gộp hai danh sách (URL Storage Firebase đã tải lên + URL Storage Firebase ban đầu)
                    updatedImageUrls.addAll(storageImageUrls);
                    // Bây giờ bạn có một danh sách hoàn chỉnh của URL ảnh sẵn sàng để thêm vào tài liệu

                    // Gọi hàm để cập nhật thông tin sản phẩm
                    updateProductInfo(updatedImageUrls);

                    Toast.makeText(getApplicationContext(), "Lấy URL ảnh thành công!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Lỗi khi tải lên ảnh!", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateProductInfo(List<String> updatedImageUrls) {
        // Lấy thông tin sản phẩm từ giao diện người dùng
        String tenSP = name.getText().toString();
        String mota = description.getText().toString();
        long giaSP;
        int soluong;
        try {
            giaSP = Long.parseLong(price.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập giá hợp lệ hoặc không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            soluong = Integer.parseInt(amount.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số lượng hợp lệ hoặc không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra các thông tin cần thiết không được để trống
        if (tenSP.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mota.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy danh sách màu sắc mới từ adapter_color
        selectedColors.clear();
        for (Color color : allColors) {
            if (color.getChecked()) {
                selectedColors.add(color.getMaMau());
            }
        }
        selectedSizes.clear();
        for(Size size : allSize)
        {
            if(size.isChecked())
            {
                selectedSizes.add(size.getMaSize());
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("SANPHAM");
        productsRef.document(MaSP).update(
                "TenSP", tenSP,
                "MoTaSP", mota,
                "GiaSP", giaSP,
                "SoLuongSP", soluong,
                "MauSac", selectedColors,
                "Size", selectedSizes,
                "HinhAnhSP", updatedImageUrls
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent_done = new Intent(activity_edit_product.this, activity_MyProduct.class);
                Toast.makeText(activity_edit_product.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                startActivity(intent_done);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_edit_product.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
