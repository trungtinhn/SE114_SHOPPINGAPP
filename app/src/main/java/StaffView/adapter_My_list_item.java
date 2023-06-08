package StaffView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.itf_RCV_list_item;

import java.util.ArrayList;
import java.util.List;

public class adapter_My_list_item extends RecyclerView.Adapter<adapter_My_list_item.list_item_holder> {

    private Activity activity;
    private Context context;
    private itf_RCV_list_item listener;
    private List<item_object> arrayList;

    public adapter_My_list_item(Activity activity, List<item_object> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;


    }

    @NonNull
    @Override
    public list_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listitem,parent,false);
        return new list_item_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_item_holder holder, int position) {
        item_object product = arrayList.get(position);
        if (product==null)
            return;
        holder.name.setText(product.getName());
        holder.ava.setImageResource(product.getImage());
        holder.quanity.setText(String.valueOf(product.getQuanity()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity act = (AppCompatActivity) view.getContext();
                fragment_detail_list_item fragment = new fragment_detail_list_item(product);
                act.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_item,
                        fragment).addToBackStack(null).commit();

               // listener.onClick(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return arrayList.size();
        return 0;
    }
    public class list_item_holder extends RecyclerView.ViewHolder
    {
        private TextView name,quanity;
        private ImageView ava;
        public CardView cardView;

        public list_item_holder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_item_name);
            quanity = (TextView) itemView.findViewById(R.id.txt_item_quanity);
            ava = itemView.findViewById(R.id.img_item_img);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }
    public void setData(ArrayList<item_object> arrayList)
    {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
}
