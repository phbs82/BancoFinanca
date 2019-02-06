package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.Utils.ExpandableListViewAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ListaCompras extends AppCompatActivity {

    private ExpandableListView expandableListView;

    private  ExpandableListViewAdapter expandableListViewAdapter;

    private List<String> listDataGroup;

    private HashMap<String, List<String>> listDataChild;

    private ComprasItems comprasitems;
    private CompraViewModel compraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);


        // initializing the list of groups
        listDataGroup = new ArrayList<>();

        // initializing the list of child
        listDataChild = new HashMap<>();

        // initializing the adapter object
        expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);
        expandableListView = findViewById(R.id.expandableListView);

        expandableListView.setAdapter(expandableListViewAdapter);
        expandableListViewAdapter.notifyDataSetChanged();

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

            @Override
            public void onChanged(@Nullable final List<ComprasItems> compras) {

                List<ComprasItems> comprasitenstotal = new ArrayList<>(0);

                Set<String> mesano = new HashSet<>();

                comprasitenstotal.addAll(compras);

                final Spinner SpinnerMesAno = (Spinner) findViewById(R.id.spinner);

                for (int i = 0; i < comprasitenstotal.size(); i++) {



                    String pattern = "dd-MMM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


                    String datacompra = simpleDateFormat.format(comprasitenstotal.get(i).compra.getData());


                    listDataGroup.add(String.valueOf(datacompra) + "  " +
                            comprasitenstotal.get(i).local.get(0).getDesclocal()


                    );

                    mesano.add(datacompra.subSequence(3,6) + "/" + datacompra.substring(datacompra.length()-4,datacompra.length()));



                    List<String> itens  = new ArrayList<>(0);
                    float total = 0;


                    for (int j = 0; j < comprasitenstotal.get(i).itens.size(); j++) {

                        itens.add(comprasitenstotal.get(i).itens.get(j).getDescricao() + " " +
                                String.valueOf(comprasitenstotal.get(i).itens.get(j).getValor()
                                * comprasitenstotal.get(i).itens.get(j).getQuantidade())

                        );
                        total += comprasitenstotal.get(i).itens.get(j).getValor()  * comprasitenstotal.get(i).itens.get(j).getQuantidade() ;

                    }
                    itens.add("Total: R$" + String.valueOf(total));
                    listDataChild.put(listDataGroup.get(i),itens);

                }
                expandableListViewAdapter.notifyDataSetChanged();

                CharSequence[] cs = mesano.toArray(new CharSequence[mesano.size()]);

                ArrayAdapter<String> adaptersp = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item);

                adaptersp.addAll(mesano);

                adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerMesAno.setAdapter(adaptersp);

            }
        });


        Spinner SpinnerMesAno = (Spinner) findViewById(R.id.spinner);


        SpinnerMesAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                expandableListViewAdapter.getFilter()
                        .filter(SpinnerMesAno.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                return false;
            }
        });

    }





}

