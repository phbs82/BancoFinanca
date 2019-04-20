package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class grafico_compras extends AppCompatActivity {


    private PieChart chart;
    private Spinner SpinnerMesAno;
    private CompraViewModel compraViewModel;
    private List<ComprasItems> listacompras = new ArrayList<ComprasItems>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_compras);

        SpinnerMesAno = (Spinner) findViewById(R.id.spinner2);

      //  ViewSwitcher vs1 = findViewById(R.id.vs1);

      /*  tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    vs1.showNext();
                }
                else {
                    vs1.showPrevious();
                }
            }
        });*/

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                listacompras.addAll(compras);

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


        List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);
        PieChart pieChart = findViewById(R.id.barview);
        ArrayList valores = new ArrayList();
        ArrayList localval = new ArrayList();



        for (int i=0; i < listacompras.size(); i++) {

            if (listacompras.get(i).compra.getData().toString().toLowerCase().contains(year.trim())
                    && listacompras.get(i).compra.getData().toString().toLowerCase().contains(month.trim())) {
                comprasitens.add(listacompras.get(i));
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

            valores.add(i,new Entry(total,i));
            localval.add(i,local + " - " + String.valueOf(total));


        }

        PieDataSet dataSet = new PieDataSet(valores, "10 maiores gastos no mês");

        PieData data = new PieData(localval,dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        pieChart.invalidate(); // refresh




    }


}


/*
    ArrayList<String> barBottomList = new ArrayList<String>();
    ArrayList<Integer> barDataList = new ArrayList<Integer>();
    List<BarEntry> entries = new ArrayList<>();
    //List<String> locais = new ArrayList<String>(0);
    String[] locais = {""};
    String[] vetor = {""};

    List<ComprasItems> comprasitens = new ArrayList<ComprasItems>(0);
    Vector<String> locals= new Vector<String>(0);


        for (int i=0; i < listacompras.size(); i++) {

        if (listacompras.get(i).compra.getData().toString().toLowerCase().contains(year.trim())
        && listacompras.get(i).compra.getData().toString().toLowerCase().contains(month.trim())) {
        comprasitens.add(listacompras.get(i));
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


        BarEntry entrie = new BarEntry(i,total);
        entries.add(entrie);

        locals.add(String.valueOf(i+1) +  " " + local);


        }

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        chart = findViewById(R.id.barview);

        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars


        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
        //   l.setExtra(ColorTemplate.VORDIPLOM_COLORS,vetor.toArray(locals));
        l.setExtra(ColorTemplate.VORDIPLOM_COLORS,locals.toArray(vetor));

        chart.invalidate(); // refresh
*/


