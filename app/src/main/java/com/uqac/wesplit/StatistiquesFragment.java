package com.uqac.wesplit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.uqac.wesplit.enums.CategoriesEnum;
import com.uqac.wesplit.enums.PeriodesEnum;

import java.util.ArrayList;
import java.util.List;

public class StatistiquesFragment extends Fragment {

    private Spinner spinnerPeriode;
    private TextView depensesTotales, depensesPeriode;

    public StatistiquesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistiques, container, false);

        TextView depensesTotales = (TextView) view.findViewById(R.id.depenses_totales_text);
        TextView depensesPeriode = (TextView) view.findViewById(R.id.depenses_periode_text);
        Spinner spinnerPeriode = (Spinner) view.findViewById(R.id.spinner_periode);
        PieChart categoriesChart = (PieChart) view.findViewById(R.id.categories_chart);

        spinnerPeriode.setAdapter(new ArrayAdapter<PeriodesEnum>(getActivity(), android.R.layout.simple_spinner_dropdown_item, PeriodesEnum.values()));

        categoriesChart.setHoleRadius(33);
        categoriesChart.setTransparentCircleRadius(37);
        categoriesChart.setEntryLabelColor(Color.parseColor("#ffffff"));
        categoriesChart.setUsePercentValues(true);

        List<PieEntry> entries = new ArrayList<>();
        for (CategoriesEnum categorie : CategoriesEnum.values()) {
            entries.add(new PieEntry(16.65f, categorie.toString()));
        }
        PieDataSet set = new PieDataSet(entries, "Répartition des dépenses");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(set);
        categoriesChart.setData(data);
        categoriesChart.invalidate();

        return view;
    }
}
