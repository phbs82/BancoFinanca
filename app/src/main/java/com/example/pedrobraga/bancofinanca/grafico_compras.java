package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.Utils.ValueComparator;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;


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

        ViewSwitcher vs1 = findViewById(R.id.viewSwitcher);

        TextView txtgraficocompra = (TextView) findViewById(R.id.txtvCompra);


        txtgraficocompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtgraficocompra.getText().toString() == "") {
                    vs1.showNext();
                    txtgraficocompra.setText("Gráifco das Compras");


                }
                else {


                    vs1.showPrevious();
                    txtgraficocompra.setText("Gráifco dos Produtos");

                }

            }

        });


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
        ArrayList valoresprodutos = new ArrayList();


        List<Item> itens = new ArrayList<Item>(0);

        HashMap<Float,String> produtos = new HashMap<Float,String>(0);

        for (int i=0; i < listacompras.size(); i++) {

            if (listacompras.get(i).compra.getData().toString().toLowerCase().contains(year.trim())
                    && listacompras.get(i).compra.getData().toString().toLowerCase().contains(month.trim())) {
                comprasitens.add(listacompras.get(i));
                itens.addAll(listacompras.get(i).itens);
            }
        }

        for (int i = 0; i < comprasitens.size(); i++) {
            if (i == 10)
                break;

            String local = comprasitens.get(i).local.get(0).getDesclocal();

            float total =0;

            for (int j = 0; j < comprasitens.get(i).itens.size(); j++) {
                total += comprasitens.get(i).itens.get(j).getValor()
                        * comprasitens.get(i).itens.get(j).getQuantidade();

            }

            valores.add(i,new Entry(total,i));
            localval.add(i,local + " - " + String.valueOf(total));


        }

        for (int i =0; i < itens.size(); i++) {

            produtos.put(itens.get(i).getValor(),itens.get(i).getDescricao());
        }

        PieDataSet dataSet = new PieDataSet(valores, "10 maiores gastos no mês");

        PieData data = new PieData(localval,dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.invalidate(); // refresh

        // Montagem do Gráfico dos Produtos

        Map<Float,String> sortprodutos = new TreeMap<Float,String>(produtos);
        ArrayList descprodutos = new ArrayList();
        int j = 0;
        for (String desc: sortprodutos.values()) {

            descprodutos.add(j,desc);
            j++;

            if (j==10) {
                break;
            }
        }


        ArrayList valprodutos = new ArrayList();
        Set<Float> totalp = sortprodutos.keySet();

        int i =0;
        for (Float t: sortprodutos.keySet()) {

            valprodutos.add(i,new Entry(t,i));

            i++;
            if (i==10)
                break;

        }

        PieChart pieChart2 = findViewById(R.id.barview2);
        PieDataSet dataSet2 = new PieDataSet(valprodutos, "Produtos");
        PieData data2 = new PieData(descprodutos, dataSet2);
        pieChart2.setData(data2);
        dataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart2.animateXY(5000, 5000);
        pieChart2.invalidate();



    }




}
