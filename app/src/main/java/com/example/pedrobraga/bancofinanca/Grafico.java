package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.LocalViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;



public class Grafico extends AppCompatActivity {


    private CompraViewModel compraViewModel;
//    private LocalViewModel localViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final PieChartView pieChartView = findViewById(R.id.chart);
        final List<SliceValue> pieData = new ArrayList<>();

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                for (int i = 0; i < compras.size(); i++) {

                    if (i == 10)
                        break;

                    String local = compras.get(i).local.get(0).getDesclocal();

                    int color = 0;

                    switch (i) {

                        case 0:
                            color = Color.BLUE;
                        case 1:
                            color = Color.RED;
                        case 2:
                            color = Color.BLUE;
                        case 3:
                            color = Color.CYAN;
                        case 4:
                            color = Color.DKGRAY;
                        case 5:
                            color = Color.MAGENTA;
                        case 6:
                            color = Color.CYAN;
                        case 7:
                            color = Color.YELLOW;
                        case 8:
                            color = Color.GRAY;
                        default:
                            color = Color.GREEN;

                    }

                    float total = 0;

                    for (int j = 0; j < compras.get(i).itens.size(); j++) {
                        total += compras.get(i).itens.get(j).getValor();
                    }

                    pieData.add(new SliceValue(total, color).setLabel(local));

                }
            }


        });

        PieChartData pieChartData = new PieChartData(pieData);

        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Top 10 Compras no mês").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);


    } // fim do método Create





}
