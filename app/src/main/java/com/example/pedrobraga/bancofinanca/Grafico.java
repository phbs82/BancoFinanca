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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.LocalViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
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

/*import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;*/



public class Grafico extends AppCompatActivity {

    private CompraViewModel compraViewModel;
    private Spinner SpinnerMesAno;
    //    private GraphView graph;
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


                for (int i = 0; compras.size() > i; i++) {

                    String datacompra = DateFormat.getDateInstance().format(
                            compras.get(i).compra.getData());
                    mesano.add(datacompra.substring(0, 3) + "/" + datacompra.substring(8, 12));


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

//        chart.setDrawBarShadow(false);
      /*  chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);*/

       chart = (PieChart) findViewById(R.id.chart1);


        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {


                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                comprasitens.addAll(compras.stream()
                        .filter(c -> c.compra.getData().toString().contains(year.trim()))
                        .filter(c -> c.compra.getData().toString().contains(month.trim()))
                        .collect(Collectors.toList())
                );

                List<PieEntry> entries = new ArrayList<>();


                for (int i = 0; i < comprasitens.size(); i++) {

                    if (i == 10)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor();
                    }

                    entries.add(new PieEntry(total,local));

                }


                PieDataSet set = new PieDataSet(entries, "Compras no mês");
                set.setColors(ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData(set);
                chart.setData(data);
                chart.invalidate(); // refresh



            }
        });

    }









 /*   private CompraViewModel compraViewModel;
    private Spinner SpinnerMesAno;
    private PieChartView pieChartView;
    private List<SliceValue> pieData = new ArrayList<>();
   private  PieChartData pieChartData = new PieChartData();       //    private LocalViewModel localViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  pieChartData.setHasCenterCircle(true).setCenterText1("Top 10 Compras no mês").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                 Set<String> mesano = new HashSet<>();


                for (int i=0; compras.size() >  i; i++ ) {

                       String datacompra = DateFormat.getDateInstance().format(
                               compras.get(i).compra.getData());
                       mesano.add(datacompra.substring(0,3) + "/" + datacompra.substring(8,12));


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

              geraGrafico(SpinnerMesAno.getSelectedItem().toString().substring(0,3));

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });



    } // fim do método Create


    public void geraGrafico(String filtro) {

         Set<String> mesano = new HashSet<>();

        pieChartData.setHasLabels(true).setValueLabelTextSize(9);

        pieChartView = findViewById(R.id.chart);

        pieChartView.destroyDrawingCache();
        pieChartView.setPieChartData(null);
        pieData.clear();
        pieChartData.setValues(null);

        
        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);
        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {


                  List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                comprasitens.addAll(compras.stream()
                        .filter(c -> c.toString().contains(filtro.trim()))
                        .collect(Collectors.toList())
                );



                for (int i = 0; i < comprasitens.size(); i++) {

                    if (i == 10)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor();
                    }

                    int color = 0;

                    switch (i) {

                        case 0:
                            color = Color.BLUE;
                            break;
                        case 1:
                            color = Color.RED;
                            break;
                        case 2:
                            color = Color.WHITE;
                            break;
                        case 3:
                            color = Color.CYAN;
                            break;
                        case 4:
                            color = Color.DKGRAY;
                            break;
                        case 5:
                            color = Color.MAGENTA;
                            break;
                        case 6:
                            color = Color.CYAN;
                            break;
                        case 7:
                            color = Color.YELLOW;
                            break;
                        case 8:
                            color = Color.GRAY;
                            break;
                        case 9:
                            color = Color.GREEN;
                            break;

                    }

                     pieData.add(new SliceValue(total, color).setLabel(local));

                }


                pieChartData.setValues(pieData);
                pieChartView.setPieChartData(pieChartData);
            }


        });
*/




}
