package com.example.shoppingapp.customerview.fragment;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.categories.Categories;
import com.example.shoppingapp.customerview.categories.CategoriesAdapter;

import com.example.shoppingapp.customerview.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.product.ProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    private BottomNavigationCustomActivity bottomNavigationCustomActivity;

    private RecyclerView rcvProduct;
    private List<Product> listProduct;
    private ProductAdapter productAdapter;

    private RecyclerView rcvCategories;
    private List<Categories> listCategories;
    private CategoriesAdapter categoriesAdapter;
    private TextView txtSeeall;
    private EditText editSearch;
    private ImageView chatBtn;
    private  ImageView shoppingCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        rcvProduct = view.findViewById(R.id.rcvProduct);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        txtSeeall = view.findViewById(R.id.txtSeeall);
        editSearch = view.findViewById(R.id.editSearch);
        chatBtn = view.findViewById(R.id.chatBtn);
        shoppingCart = view.findViewById(R.id.ShoppingCart);


        setDataRcvProduct();
        setDataRcvCategories();
        setOnClicktxtSeeall();
        setOnClickEditSearch();
        setOnCLickChatbtn();

        setOnClickShoppingCart();
        // Inflate the layout for this fragment
        return view;
    }

    private void setOnClickShoppingCart() {
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoDetail();
            }
        });
    }

    private void setOnCLickChatbtn() {
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoMessageActivity();
            }
        });
    }

    private void setOnClickEditSearch() {
        listProduct = new ArrayList<>();
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoSearchingActivity();
            }
        });
    }

    private void setOnClicktxtSeeall() {
        txtSeeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoTrendingActivity();
            }
        });
    }

    private void setDataRcvProduct() {
        listProduct = new ArrayList<>();
        List<String> listsp = new ArrayList<>();
        List<String> listhinhanh = new ArrayList<>();
        //Lay Du Lieu San Pham
        firebaseFirestore.collection("SANPHAM")
                .whereEqualTo("Trending", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                listsp.add(documentSnapshot.getString("TenSP"));
                                Log.d("hkggggggg", String.valueOf(listsp.size()));

                            }
                        }
                        else {
                            Log.d("Error", "KHong lay duoc ten san pham", task.getException());
                        }
                    }
                });
        //Lay Du Lieu Hinh Anh

        firebaseFirestore.collection("SANPHAM")
                .whereEqualTo("Trending", true)
                        .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                            // Lấy thuộc tính của mảng có tên là "myArray" từ tài liệu phù hợp với điều kiện
                                            List<String> myArray = (List<String>) documentSnapshot.get("HinhAnhSP");
                                            // Thực hiện các hành động khác với các thuộc tính của mảng
                                            listhinhanh.add(myArray.get(0));
                                            Log.d("hkggggggg",myArray.get(0));

                                        }
                                    }
                                });
        //
        //Log.d("hkggggggg", String.valueOf(listsp.size()));
        for( int i = 0; i < listsp.size(); i++){

            listProduct.add(new Product("https://firebasestorage.googleapis.com/v0/b/se114-df58a.appspot.com/o/ImageTest%2Fantibody.png?alt=media&token=d0f35056-b3c7-405e-9105-d0775a5eb5a5&_gl=1*1bu10vj*_ga*MTcwMzc1MjQzNC4xNjgxMjE5MDE0*_ga_CW55HF8NVT*MTY4NjA3NjQzMi42LjEuMTY4NjA3NzQ5My4wLjAuMA.. ",listsp.get(i)));
        }
        if(listProduct.size() == 0){
            listProduct.add(new Product("https://firebasestorage.googleapis.com/v0/b/se114-df58a.appspot.com/o/ImageTest%2Fantibody.png?alt=media&token=d0f35056-b3c7-405e-9105-d0775a5eb5a5&_gl=1*1bu10vj*_ga*MTcwMzc1MjQzNC4xNjgxMjE5MDE0*_ga_CW55HF8NVT*MTY4NjA3NjQzMi42LjEuMTY4NjA3NzQ5My4wLjAuMA.. ","Ao len nau"));
        }

//        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
//        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
//        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
//        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
//        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter();

        productAdapter.setData(listProduct, new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickGoToDetailProduct(product);
            }
        });

        rcvProduct.setAdapter(productAdapter);
    }

    private void onClickGoToDetailProduct(Product product) {
        bottomNavigationCustomActivity.gotoDetailProduct(product);
    }


    private void setDataRcvCategories() {


        listCategories = new ArrayList<>();

        listCategories.add(new Categories("Thời trang nam"));
        listCategories.add(new Categories("Thời trang nữ"));
        listCategories.add(new Categories("Phụ kiện"));
        listCategories.add(new Categories("Giày nam"));
        listCategories.add(new Categories("Giày nữ"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.setData(listCategories);
        rcvCategories.setAdapter(categoriesAdapter);
    }


}
