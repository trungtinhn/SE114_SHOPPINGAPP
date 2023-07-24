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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
            // Lấy URI của hình ảnh được chọn
            Uri imageUri = data.getData();

            // Lưu URI của hình ảnh vào danh sách imageUrls
            imageAdapter.getImageUrls().add(imageUri.toString());

            // Cập nhật RecyclerView bằng cách thông báo thay đổi dữ liệu
            imageAdapter.notifyDataSetChanged();
        }
    }




    private void updateProduct() {
        // Tải ảnh lên Firebase Storage và sau khi hoàn thành, tiến hành cập nhật thông tin sản phẩm
        List<String> removedImageUrls = imageAdapter.getRemovedImageUrls();
        if (imageUrls.isEmpty() || imageUrls.size() > 3) {
            Toast.makeText(this, "Vui lòng thêm ít nhất một hình ảnh và không được thêm hơn 3 hình ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadImagesAndSaveProduct();
    }

    private void uploadImagesAndSaveProduct() {
        // Tạo thư mục lưu trữ ảnh trong Firebase Storage với tên là "ImageSanPham"
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("ImageSanPham");

        // Tạo danh sách lưu trữ các URL ảnh mới sau khi tải lên thành công
        updatedImageUrls = new ArrayList<>();

        // Lưu số lượng ảnh đã tải lên thành công để xác định khi nào hoàn thành tải lên tất cả
        final int[] uploadedCount = {0};

        // Tải lần lượt các ảnh lên Firebase Storage và cập nhật danh sách URL ảnh trong Firestore
        for (String imageUrl : imageUrls) {
            // Kiểm tra xem URL ảnh đã tồn tại trong danh sách updatedImageUrls chưa
            int existingIndex = updatedImageUrls.indexOf(imageUrl);
            if (existingIndex != -1) {
                // Nếu URL ảnh đã tồn tại trong danh sách updatedImageUrls, thay thế URL cũ bằng URL mới
                updatedImageUrls.set(existingIndex, imageUrl);
            } else {
                // Nếu URL ảnh chưa tồn tại trong danh sách updatedImageUrls, tiến hành upload ảnh và thêm URL mới vào danh sách
                // Thay vì sử dụng imageUrl (từ imageUrls), bạn có thể lưu trữ URI của ảnh đã tải lên trong danh sách updatedImageUrls
                updatedImageUrls.add(imageUrl);

                // Tăng số lượng ảnh đã tải lên thành công
                uploadedCount[0]++;

                // Kiểm tra xem đã tải lên hết các ảnh chưa, nếu đã xong thì tiến hành cập nhật thông tin sản phẩm
                if (uploadedCount[0] == imageUrls.size()) {
                    // Kiểm tra xem có ảnh nào cần xóa không
                    List<String> removedImageUrls = new ArrayList<>(imageUrls); // Tạo một bản sao của danh sách imageUrls
                    removedImageUrls.removeAll(updatedImageUrls); // Loại bỏ các URL hình ảnh đã tải lên khỏi danh sách removedImageUrls

                    // Bây giờ, trong removedImageUrls, chỉ còn những URL hình ảnh cần bị xóa
                    // Bạn có thể xóa các URL này khỏi Firebase Storage hoặc thực hiện các tác vụ khác cần thiết.

                    // Tiếp theo, bạn có thể gọi phương thức updateProductInfo(updatedImageUrls) để cập nhật thông tin sản phẩm
                    // và sau đó thực hiện xóa các URL hình ảnh không cần thiết nếu cần.
                    updateProductInfo(updatedImageUrls);
                }
            }
        }
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
        for(Size size:allSize)
        {
            if(size.isChecked())
            {
                selectedSizes.add(size.getMaSize());
            }
        }

        // Cập nhật thông tin sản phẩm vào Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("SANPHAM");
        productsRef.document(MaSP).update(
                "TenSP", tenSP,
                "MoTaSP", mota,
                "GiaSP", giaSP,
                "SoLuongSP", soluong,
                "MauSac", selectedColors,
                "Size", selectedSizes,
                "HinhAnhSP", updatedImageUrls // Cập nhật danh sách URL ảnh mới vào Firestore
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
