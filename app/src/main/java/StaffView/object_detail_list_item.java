package StaffView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;

import java.util.ArrayList;


public class object_detail_list_item extends AppCompatActivity {
    private RecyclerView RCV;
    private TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_my_list_item);

        RCV = findViewById(R.id.RCV_detail_listItems);
        name = findViewById(R.id.txt_list_item_detail);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        ArrayList<product_object> products = (ArrayList<product_object>) getIntent().getSerializableExtra("product");

        adapter_My_list_product subjectAdapter = new adapter_My_list_product(this, this, products);
        RCV.setAdapter(subjectAdapter);

    }
}
