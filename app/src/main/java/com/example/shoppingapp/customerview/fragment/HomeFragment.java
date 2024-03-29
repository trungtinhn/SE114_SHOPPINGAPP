package com.example.shoppingapp.customerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.activity.CategoriesDetails;
import com.example.shoppingapp.customerview.categories.Categories;
import com.example.shoppingapp.customerview.categories.CategoriesAdapter;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.product.ProductAdapter;
import com.example.shoppingapp.customerview.product.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.customerview.viewpagerimage.AutoScrollTask;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@OptIn(markerClass = com.google.android.material.badge.ExperimentalBadgeUtils.class)
public class HomeFragment extends Fragment {
    private List<String> dataGiohang;
    private ViewPager2 viewPager2;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    private BottomNavigationCustomActivity bottomNavigationCustomActivity;

    private RecyclerView rcvProduct;
    private List<Product> listProduct;
    private ProductAdapter productAdapter;

    private RecyclerView rcvCategories;
    private List<Categories> listCategories;
    private CategoriesAdapter categoriesAdapter;
    private TextView txtSeeall, txtEx;
    private Button editSearch;
    private ImageView chatBtn;
    private  ImageView shoppingCart;
    private List<String> imagePro;

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

        viewPager2 = view.findViewById(R.id.viewpagerImage);
        rcvProduct = view.findViewById(R.id.rcvProduct);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        txtSeeall = view.findViewById(R.id.txtSeeall);
        editSearch = view.findViewById(R.id.editSearch);
        chatBtn = view.findViewById(R.id.chatBtn);
        shoppingCart = view.findViewById(R.id.ShoppingCart);
        txtEx = view.findViewById(R.id.txtExploreNow);

        setDataRcvProduct();
        setDataRcvCategories();
        setOnClicktxtSeeall();
        setOnClickEditSearch();
        setOnCLickChatbtn();
        getDataPromotion();
        SoLuongShoppingCart();


        txtEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoSearchingActivity();
            }
        });
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcvProduct.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter();
        firebaseFirestore.collection("SANPHAM")
                        .whereEqualTo("Trending", true)
                .whereEqualTo("TrangThai", "Inventory")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (error != null) {
                                            Log.w("Error", "listen:error", error);
                                            return;
                                        }
                                        listProduct = new ArrayList<>();
                                        for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                                            String masp = documentSnapshot.getString("MaSP");
                                            String name = documentSnapshot.getString("TenSP");
                                            Long giaSP = documentSnapshot.getLong("GiaSP");
                                            List<String> Anh = (List<String>) documentSnapshot.get("HinhAnhSP");
                                            listProduct.add(new Product(Anh.get(0), name, masp, giaSP));
                                        }
                                        productAdapter.setData(listProduct, new IClickItemProductListener() {
                                            @Override
                                            public void onClickItemProduct(Product product) {
                                                onClickGoToDetailProduct(product);
                                            }
                                        });
                                        rcvProduct.setAdapter(productAdapter);
                                    }
                                });
        //Lay Du Lieu San Pham
    }
    private void onClickGoToDetailProduct(Product product) {
        bottomNavigationCustomActivity.gotoDetailProduct(product);
    }

    private void getDataPromotion(){
        firebaseFirestore.collection("KHUYENMAI")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("Error", "listen:error", error);
                            return;
                        }
                        imagePro = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()){
                            if(doc.exists()){
                                String image = doc.getString("HinhAnhTB");
                                Log.d("Ma Khuyen Mai", image);
                                imagePro.add(image);
                            }
                        }

                        ViewPagerPromotionAdapter imageAdapter = new ViewPagerPromotionAdapter(getContext());
                        imageAdapter.setData(imagePro);
                        viewPager2.setAdapter(imageAdapter);

                        TimerTask autoScrollTask = new AutoScrollTask(viewPager2);
                        Timer timer = new Timer();
                        timer.schedule(autoScrollTask, 2000, 2000);
                    }
                });
    }
    private void setDataRcvCategories() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter();
        try {
            firebaseFirestore.collection("DANHMUC")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.w("Error", "listen:error", error);
                                return;
                            }
                            listCategories = new ArrayList<>();
                            for (DocumentSnapshot document : value.getDocuments()) {
                                if (document.exists()) {
                                    String name = document.getString("TenDM");
                                    String anh = document.getString("AnhDM");
                                    String maDM = document.getString("MaDM");
                                    listCategories.add(new Categories(name,anh, maDM));
                                } else {
                                    Log.d("Error", "No such document");
                                }
                            }
                            categoriesAdapter.setData(listCategories);
                            categoriesAdapter.setOnItemClick(new CategoriesAdapter.OnItemClick() {
                                @Override
                                public void onButtonItemClick(int position) {
                                    Intent t = new Intent(bottomNavigationCustomActivity, CategoriesDetails.class);
                                    t.putExtra("MaDM", listCategories.get(position).getMaDM());
                                    t.putExtra("TenDM", listCategories.get(position).getName());
                                    startActivity(t);
                                }
                            });
                            rcvCategories.setAdapter(categoriesAdapter);
                        }
                    });
        }catch (Exception ex){

        }

    }
    private void SoLuongShoppingCart(){
        try {
            firebaseFirestore.collection("GIOHANG")
                    .whereEqualTo("MaND", firebaseAuth.getCurrentUser().getUid())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
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
                            if(isAdded()){
                                BadgeDrawable badgeDrawable = BadgeDrawable.create(requireContext());
                                badgeDrawable.setNumber(dataGiohang.size());

                                BadgeUtils.attachBadgeDrawable(badgeDrawable, shoppingCart, null);
                            }
                        }
                    });
        }catch (Exception ex){

        }

    }
}
