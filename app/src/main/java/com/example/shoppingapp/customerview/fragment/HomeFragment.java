package com.example.shoppingapp.customerview.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

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

        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));
        listProduct.add(new Product(R.drawable.mauaodep, "Ao len nau"));

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
