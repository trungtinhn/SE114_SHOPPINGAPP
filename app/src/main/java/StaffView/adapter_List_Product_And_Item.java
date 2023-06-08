package StaffView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class adapter_List_Product_And_Item extends FragmentStateAdapter {


    public adapter_List_Product_And_Item( FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new fragment_My_list_product();
            case 1:
                return new fragment_My_list_item();
            default:
                return new fragment_My_list_item();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
