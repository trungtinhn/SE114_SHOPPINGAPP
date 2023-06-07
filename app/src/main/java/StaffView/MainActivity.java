package StaffView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.shoppingapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffview_page09);

        // Assign variable
        View inflatedView = getLayoutInflater().inflate(R.layout.staffview_page08, null);
        barChart = inflatedView.findViewById(R.id.bc_act);
        pieChart = inflatedView.findViewById(R.id.pc_act);
        // array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(int i =1; i<10; i++)
        {
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry((float)(i*1.0), value);
            PieEntry pieEntry = new PieEntry((float)(i*1.0), value);

            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }
        // bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "1");
        // set colors
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // hide draw value
        barDataSet.setDrawValues(false);
        // Set database
        barChart.setData(new BarData(barDataSet));
        // Set animation
        barChart.animateY(1000);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"1");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(new PieData(pieDataSet));
        barChart.animateXY(1000, 1000);


    }
    void setData(BarChart bc, PieChart pc)
    {


    }
}