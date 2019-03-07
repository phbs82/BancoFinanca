package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




public class grafico_compras extends AppCompatActivity {


    private BarChart chart;
    private Spinner SpinnerMesAno;
    private CompraViewModel compraViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_compras);

        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);


        ToggleButton tb1 = findViewById(R.id.tb2);
        ViewSwitcher vs1 = findViewById(R.id.vs1);


        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    vs1.showNext();
                }

                else {


                    vs1.showPrevious();

                }


            }
        });


        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                Set<String> mesano = new HashSet<>();
                String pattern = "dd-MMM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                for (int i = 0; compras.size() > i; i++) {

                    String datacompra = simpleDateFormat.format(compras.get(i).compra.getData());
                    mesano.add(datacompra.subSequence(3,6) + "/" + datacompra.substring(datacompra.length()-4,datacompra.length()));
                }

                ArrayAdapter<String> adaptersp = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item);

                adaptersp.addAll(mesano);
                adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerMesAno.setAdapter(adaptersp);
            }

        });




        SpinnerMesAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             /*   geraGrafico(SpinnerMesAno.getSelectedItem().toString().substring(0,3),
                        SpinnerMesAno.getSelectedItem().toString().substring(4,8));*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        ArrayList<String> barBottomList = new ArrayList<String>();
        ArrayList<Integer> barDataList = new ArrayList<Integer>();
        List<BarEntry> entries = new ArrayList<>();

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                for (int i=0; i < compras.size(); i++) {

                    if (compras.get(i).compra.getData().toString().contains("Jan")) {
                        comprasitens.add(compras.get(i));
                    }
                }

                for (int i = 0; i < comprasitens.size(); i++) {
                    if (i == 10)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor()
                                * comprasitens.get(i).itens.get(j).getQuantidade();
                    }

                    //    barBottomList.add(i," R$:" + local + ": " +String.valueOf(total));
                    //  entries.add(new BarEntry(2f,total));
                    BarEntry entrie = new BarEntry(i,total);
                    entries.add(entrie);


                }

                BarDataSet set = new BarDataSet(entries, "BarDataSet");

                BarData data = new BarData(set);
                data.setBarWidth(0.9f); // set custom bar width

                chart = findViewById(R.id.barview);

                chart.setData(data);
                chart.setFitBars(true); // make the x-axis fit exactly all bars

                chart.invalidate(); // refresh


            }// FIM DE ONCHANGED

        });    // FIM DE OBSERVER*/





    }

    public void geraGrafico(String year, String month) {


        ArrayList<String> barBottomList = new ArrayList<String>();
        ArrayList<Integer> barDataList = new ArrayList<Integer>();
        List<BarEntry> entries = new ArrayList<>();

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {



                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);


            }





        });




    /*   compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                for (int i=0; i < compras.size(); i++) {

                    if (compras.get(i).compra.getData().toString().contains(year.trim())
                            && compras.get(i).compra.getData().toString().contains(month.trim())) {
                        comprasitens.add(compras.get(i));
                    }
                }

                for (int i = 0; i < comprasitens.size(); i++) {
                    if (i == 10)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor()
                                * comprasitens.get(i).itens.get(j).getQuantidade();
                    }

                //    barBottomList.add(i," R$:" + local + ": " +String.valueOf(total));
                  //  entries.add(new BarEntry(2f,total));
                    BarEntry entrie = new BarEntry(i,total);
                    entries.add(entrie);


                }

                BarDataSet set = new BarDataSet(entries, "BarDataSet");

                BarData data = new BarData(set);
                data.setBarWidth(0.9f); // set custom bar width

                chart = findViewById(R.id.barview);

                chart.setData(data);
                chart.setFitBars(true); // make the x-axis fit exactly all bars

                chart.invalidate(); // refresh


            }// FIM DE ONCHANGED

        });    // FIM DE OBSERVER*/


        //        barView.setContentDescription("10 maiores copras no mês");


    }


}


