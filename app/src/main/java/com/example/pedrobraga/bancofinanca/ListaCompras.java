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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

        Button btn = (Button) findViewById(R.id.button);

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
                mesano.add("Todos");

                for (int i = 0; i < comprasitenstotal.size(); i++) {

                    String datacompra = DateFormat.getDateInstance().format(
                            comprasitenstotal.get(i).compra.getData());


                    listDataGroup.add(String.valueOf(datacompra) + "  " +
                            comprasitenstotal.get(i).local.get(0).getDesclocal()
                    );

                   // mesano.add(comprasitenstotal.get(i).compra.getMesAno());

                    mesano.add(datacompra.substring(0,3) + "/" + datacompra.substring(8,12));



                    List<String> itens  = new ArrayList<>(0);
                    float total = 0;


                    for (int j = 0; j < comprasitenstotal.get(i).itens.size(); j++) {

                        itens.add(comprasitenstotal.get(i).itens.get(j).getDescricao() + " " +
                                String.valueOf(comprasitenstotal.get(i).itens.get(j).getValor()));
                        total += comprasitenstotal.get(i).itens.get(j).getValor();

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
/*
                if (SpinnerMesAno.getSelectedItem().toString()!="Todos") {

//                    int mes = Integer.valueOf(SpinnerMesAno.getSelectedItem().toString().trim().substring(0, 2));


                    String month = SpinnerMesAno.getSelectedItem().toString().trim().substring(0, 2);
                    String year = SpinnerMesAno.getSelectedItem().toString().trim().substring(4, 7);



                int mes = Integer.valueOf(SpinnerMesAno.getSelectedItem().toString().substring(0,1));


                String month = "";
                String year = SpinnerMesAno.getSelectedItem().toString().substring(4,7);


                switch(mes) {

                    case 1 :  month ="Jan";
                    case 2 :  month ="Feb";
                    case 3 :  month ="Mar";
                    case 4 :  month ="Apr";
                    case 5 :  month ="May";
                    case 6 :  month ="Jun";
                    case 7 :  month ="Jul";
                    case 8 :  month ="Aug";
                    case 9 :  month ="Sep";
                    case 10 :  month ="Oct";
                    case 11 :  month ="Nov";
                    default :  month ="Dec";

                }



                    expandableListViewAdapter.filterData(month, year);

                }
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


/*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               expandableListViewAdapter.ClearFilter();


            }
        });
*/

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

              //  expandableListViewAdapter.getFilter().filter("Sep");
              /*  expandableListViewAdapter.filterData("Sep");

                expandableListView.setAdapter(expandableListViewAdapter);
                expandableListViewAdapter.notifyDataSetChanged();

                */
                
                return false;
            }
        });

    }


}

