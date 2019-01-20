package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.LocalViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;




public class Grafico extends AppCompatActivity {

    private CompraViewModel compraViewModel;
    private Spinner SpinnerMesAno;
    private PieChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {


                Set<String> mesano = new HashSet<>();


                String pattern = "dd-MMM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


              //  String datacompra = simpleDateFormat.format(comprasitenstotal.get(i).compra.getData());


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



    private void geraGrafico(String year, String month) {

       chart = (PieChart) findViewById(R.id.chart1);


        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {


                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                for (int i=0; i < compras.size(); i++) {

                    if (compras.get(i).compra.getData().toString().contains(year.trim())
                            && compras.get(i).compra.getData().toString().contains(year.trim())) {

                        comprasitens.add(compras.get(i));

                    }

                }


                List<PieEntry> entries = new ArrayList<>();


                for (int i = 0; i < comprasitens.size(); i++) {

                    if (i == 10)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor()
                                * comprasitens.get(i).itens.get(j).getQuantidade();
                    }

                    entries.add(new PieEntry(total,local));

                }



                PieDataSet set = new PieDataSet(entries,"");
                set.setColors(ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData(set);


                data.setValueTextColor(Color.MAGENTA);
                data.setValueTextSize(20);

                data.setValueFormatter(new MyValueFormatter());

                data.setHighlightEnabled(true);

                Legend legend = chart.getLegend();
                legend.setTextColor(Color.BLACK);
                legend.setTextSize(16);



                chart.setData(data);


                chart.invalidate(); // refresh



            }
        });



    }


    public class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal

        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return  "R$ " + mFormat.format(value); // e.g. append a dollar-sign
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listagem_compras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.cadastro:
                Intent intent = new Intent(getApplicationContext(),CompraCRUD.class);
                startActivity(intent);
                return true;
            case R.id.listagem:
                Intent intent2 = new Intent(getApplicationContext(),ListaCompras.class);
                startActivity(intent2);
                return true;
            default:
                Intent intent3 = new Intent(getApplicationContext(),Grafico.class);
                startActivity(intent3);
                return true;
        }

    }




}
