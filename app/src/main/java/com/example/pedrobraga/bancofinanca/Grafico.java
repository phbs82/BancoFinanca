package com.example.pedrobraga.bancofinanca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;




public class Grafico extends AppCompatActivity {

  /*  private CompraViewModel compraViewModel;
    private Spinner SpinnerMesAno;
    private BarChart chart;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*


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





        GraphView graph = (GraphView) findViewById(R.id.chart1);



        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 5.5),
                new DataPoint(2, -3.4),
                new DataPoint(3, 2)
        });

        series.setSpacing(50); // 50% spacing between bars
        series.setAnimated(true);
        graph.addSeries(series);

        // set the viewport wider than the data, to have a nice view
        graph.getViewport().setMinX(10000d);
        graph.getViewport().setMaxX(10d);
        graph.getViewport().setXAxisBoundsManual(true);
*/






    }

/*
    private void geraGrafico(String year, String month) {

        chart = (BarChart) findViewById(R.id.chart1);

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                chart.clear();
                List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);

                for (int i = 0; i < compras.size(); i++) {

                    if (compras.get(i).compra.getData().toString().contains(year.trim())
                            && compras.get(i).compra.getData().toString().contains(month.trim())) {

                        comprasitens.add(compras.get(i));

                    }
                }

                List<BarEntry> entries = new ArrayList<>();
                for (int i = 0; i < comprasitens.size(); i++) {

                    if (i == 5)
                        break;

                    String local = comprasitens.get(i).local.get(0).getDesclocal();

                    float total = 0;

                    for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                        total += comprasitens.get(i).itens.get(j).getValor()
                                * comprasitens.get(i).itens.get(j).getQuantidade();
                    }

                    entries.add(new BarEntry(total, i));

                }

                BarDataSet set = new BarDataSet(entries, "");
                set.setBarBorderColor(Color.MAGENTA);
                set.setBarShadowColor(Color.BLUE);
                set.setDrawValues(true);
                set.notifyDataSetChanged();
                set.setColors(ColorTemplate.MATERIAL_COLORS);
                BarData data = new BarData(set);
                data.setValueTextColor(Color.BLACK);
                data.setValueTextSize(8);
                data.setHighlightEnabled(true);
                Legend legend = chart.getLegend();
                legend.setTextColor(Color.BLACK);
                legend.setTextSize(12);
                chart.enableScroll();
                chart.isScaleYEnabled();
                chart.isScaleXEnabled();
                chart.setAutoScaleMinMaxEnabled(true);
                chart.bringToFront();
                chart.setData(data);
                chart.invalidate(); // refresh

            }
            });
    }*/







}








