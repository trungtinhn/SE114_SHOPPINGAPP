package com.example.shoppingapp.customerview.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.product.ProductAdapter;
import com.example.shoppingapp.customerview.product.customer_interface.IClickItemProductListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

@OptIn(markerClass = com.google.android.material.badge.ExperimentalBadgeUtils.class)
public class FollowFragment extends Fragment {
    private List<String> dataGiohang;
    private ImageView shoppingCart;

    private RecyclerView rcvFollow;
    private TextView btnEmpty;
    private RelativeLayout layoutEmpty;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProductAdapter productAdapter;

    private BottomNavigationCustomActivity bottomNavigationCustomActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        rcvFollow = view.findViewById(R.id.rcvFollow);
        btnEmpty = view.findViewById(R.id.btnEmpty);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        shoppingCart = view.findViewById(R.id.ShoppingCart);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvFollow.setLayoutManager(gridLayoutManager);

        getFirebase();
        setFollow();
        SoLuongShoppingCart();
        setOnClickShoppingCart();


        return view;
    }

    private void getFirebase() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

    }

    private void onClickGoToDetailProduct(Product product) {
        bottomNavigationCustomActivity.gotoDetailProduct(product);
    }
    private void setOnClickShoppingCart() {
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoDetail();
            }
        });
    }
    private void setFollow() {
        productAdapter = new ProductAdapter();

        List<Product> products = new ArrayList<>();

        firebaseFirestore.collection("YEUTHICH")
                .whereEqualTo("MaND", firebaseUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (value.size() == 0) {
                            layoutEmpty.setVisibility(View.VISIBLE);
                        }else {
                            layoutEmpty.setVisibility(View.GONE);


                            for (QueryDocumentSnapshot doc : value) {
                                String maSP = doc.getString("MaSP");


                                final DocumentReference docRef = firebaseFirestore.collection("SANPHAM").document(maSP);
                                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                        @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.w(TAG, "Listen failed.", e);
                                            return;
                                        }

                                        if (snapshot != null && snapshot.exists()) {
                                            Log.d(TAG, "Current data: " + snapshot.getData());
                                            String masp = snapshot.getString("MaSP");
                                            String name = snapshot.getString("TenSP");
                                            Long giaSP = snapshot.getLong("GiaSP");
                                            List<String> Anh = (List<String>) snapshot.get("HinhAnhSP");
                                            products.add(new Product(Anh.get(0), name, masp, giaSP));

                                            productAdapter.setData(products, new IClickItemProductListener() {
                                                @Override
                                                public void onClickItemProduct(Product product) {
                                                    onClickGoToDetailProduct(product);
                                                }
                                            });
                                            rcvFollow.setAdapter(productAdapter);
                                        } else {
                                            Log.d(TAG, "Current data: null");
                                        }
                                    }
                                });
                            }

                        }


                    }
                });
    }
    private void SoLuongShoppingCart(){
        firebaseFirestore.collection("GIOHANG")
                .whereEqualTo("MaND", firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        dataGiohang = new ArrayList<>();
                        for(DocumentSnapshot doc: value.getDocuments()){
                            if(doc.exists()){
                                String ma = doc.getString("MaGH");
                                dataGiohang.add(ma);
                            }
                        }
                        BadgeDrawable badgeDrawable = BadgeDrawable.create(getContext());
                        badgeDrawable.setNumber(dataGiohang.size());

                        BadgeUtils.attachBadgeDrawable(badgeDrawable, shoppingCart, null);
                    }
                });

    }
}