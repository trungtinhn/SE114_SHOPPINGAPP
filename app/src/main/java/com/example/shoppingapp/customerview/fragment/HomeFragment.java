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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter();

        //Lay Du Lieu San Pham
        firebaseFirestore.collection("SANPHAM")
                .whereEqualTo("Trending", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                String masp = documentSnapshot.getString("MaSP");
                                String name = documentSnapshot.getString("TenSP");
                                List<String> Anh = (List<String>) documentSnapshot.get("HinhAnhSP");
                                listProduct.add(new Product(Anh.get(0), name, masp));
                            }
                            productAdapter.setData(listProduct, new IClickItemProductListener() {
                                @Override
                                public void onClickItemProduct(Product product) {
                                    onClickGoToDetailProduct(product);
                                }
                            });
                            rcvProduct.setAdapter(productAdapter);
                        }
                        else {
                            Log.d("Error", "KHong lay duoc ten san pham", task.getException());
                        }
                    }
                });

    }

    private void onClickGoToDetailProduct(Product product) {
        bottomNavigationCustomActivity.gotoDetailProduct(product);
    }


    private void setDataRcvCategories() {


        listCategories = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter();

        firebaseFirestore.collection("DANHMUC")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    String name = document.getString("TenDM");
                                    String anh = document.getString("AnhDM");
                                    listCategories.add(new Categories(name,anh));
                                } else {
                                    Log.d("Error", "No such document");
                                }
                            }

                            categoriesAdapter.setData(listCategories);
                            rcvCategories.setAdapter(categoriesAdapter);
                        } else {
                            Log.w("Error", "Error getting documents.", task.getException());
                        }
                    }
                });



    }


}
