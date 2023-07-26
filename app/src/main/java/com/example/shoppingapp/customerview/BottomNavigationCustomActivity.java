package com.example.shoppingapp.customerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingapp.Login.LoginActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.activity.ChangePassWordActivity;
import com.example.shoppingapp.customerview.activity.ChatActivity;
import com.example.shoppingapp.customerview.activity.DetailProductActivity;
import com.example.shoppingapp.customerview.activity.Notification;
import com.example.shoppingapp.customerview.activity.OrderActivity;
import com.example.shoppingapp.customerview.activity.ProfileActivity;
import com.example.shoppingapp.customerview.activity.SearchingActivity;
import com.example.shoppingapp.customerview.activity.ShoppingCart;
import com.example.shoppingapp.customerview.activity.TrendingActivity;
import com.example.shoppingapp.customerview.fragment.AccountFragment;
import com.example.shoppingapp.customerview.fragment.HomeFragment;
import com.example.shoppingapp.customerview.fragment.ViewPagerAdapter;
import com.example.shoppingapp.customerview.product.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BottomNavigationCustomActivity extends AppCompatActivity{


    private ViewPager view_pager;
    private BottomNavigationView BottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_custom);

        view_pager = findViewById(R.id.view_pager);
        BottomNavigationView = findViewById(R.id.bottomNavigationView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        BottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;

                    case 1:
                        BottomNavigationView.getMenu().findItem(R.id.menu_notification).setChecked(true);
                        break;

                    case 2:
                        BottomNavigationView.getMenu().findItem(R.id.menu_follow).setChecked(true);
                        break;

                    case 3:
                        BottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        view_pager.setCurrentItem(0);
                        break;

                    case R.id.menu_notification:
                        view_pager.setCurrentItem(1);
                        break;

                    case R.id.menu_follow:
                        view_pager.setCurrentItem(2);
                        break;

                    case R.id.menu_account:
                        view_pager.setCurrentItem(3);
                        break;
                }

                return true;
            }
        });

    }

    public void gotoDetailProduct(Product product)
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, DetailProductActivity.class);
        intent.putExtra("MaSP", product.getMasp());
        startActivity(intent);

    }

    public void gotoHomeFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framehomepragment, new HomeFragment());
        fragmentTransaction.commit();
    }

    public void gotoProfileFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framehomepragment, new AccountFragment());
        fragmentTransaction.commit();
    }

    public void gotoSearchingActivity()
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, SearchingActivity.class);
        startActivity(intent);
    }

    public void gotoMessageActivity()
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, ChatActivity.class);
        startActivity(intent);
    }
    public void gotoDetail()
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, ShoppingCart.class);
        startActivity(intent);
    }

    public void gotoTrendingActivity()
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, TrendingActivity.class);
        startActivity(intent);
    }
    public void gotoNotificationActivity()
    {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, Notification.class);
        startActivity(intent);
    }

    public void gotoOrderActivity() {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, OrderActivity.class);
      //  intent.putExtra("MaSP", product.getMasp());
        startActivity(intent);
    }

    public void gotoProfile() {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void gotoCLickHelp() {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void gotoChangePass() {
        Intent intent = new Intent(BottomNavigationCustomActivity.this, ChangePassWordActivity.class);
        startActivity(intent);
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Are you sure you want to sign out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
    private void performLogout() {

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(BottomNavigationCustomActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void gotoLogOut() {
        showLogoutConfirmationDialog();
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
}
