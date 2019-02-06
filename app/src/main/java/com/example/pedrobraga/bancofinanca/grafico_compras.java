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
import android.widget.Spinner;
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.github.mikephil.charting.charts.PieChart;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.LineView;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;


public class grafico_compras extends AppCompatActivity {


    private BarView barView;
    private Spinner SpinnerMesAno;
    private CompraViewModel compraViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_compras);

        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);

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

                geraGrafico(SpinnerMesAno.getSelectedItem().toString().substring(0,3),
                        SpinnerMesAno.getSelectedItem().toString().substring(4,8));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    public void geraGrafico(String year, String month) {

        barView = (BarView) findViewById(R.id.barview);
        barView.setBottom(10);

        ArrayList<String> barBottomList = new ArrayList<String>();

        ArrayList<Integer> barDataList = new ArrayList<Integer>();

        //Pega dados do banco
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
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

                    barBottomList.add(i," R$:" + local + ": " +String.valueOf(total));
                    barDataList.add(i,(int)total);

                }

                barView.setBottomTextList(barBottomList);
                barView.setDataList(barDataList,100);


            }// FIM DE ONCHANGED

        });    // FIM DE OBSERVER

   //     barView.setMinimumWidth(10);
        barView.setFitsSystemWindows(true);
        barView.setContentDescription("10 maiores copras no mês");


    }


}


