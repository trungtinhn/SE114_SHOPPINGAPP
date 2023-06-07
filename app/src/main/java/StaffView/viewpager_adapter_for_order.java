package StaffView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpager_adapter_for_order extends FragmentStateAdapter {

    public viewpager_adapter_for_order(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new confirm_fragment();
            case 1:
                return new wait_fragment();
            case 2:
                return new delivered_fragment();
            case 3:
                return new delivering_fragment();
            default:
                return new confirm_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
