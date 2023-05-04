package com.example.shoppingapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppingapp.BottomNavigationCustomActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.categories.Categories;
import com.example.shoppingapp.categories.CategoriesAdapter;

import com.example.shoppingapp.customer_interface.IClickItemProductListener;
import com.example.shoppingapp.product.Product;
import com.example.shoppingapp.product.ProductAdapter;

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
        setDataRcvProduct();
        setDataRcvCategories();
        // Inflate the layout for this fragment
        return view;
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
