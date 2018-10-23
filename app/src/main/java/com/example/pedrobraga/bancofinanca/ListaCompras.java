package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.Dao.CompraItensDao;
import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;
import com.example.pedrobraga.bancofinanca.Utils.ExpandableListViewAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ItemViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter.toStringFDate;

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

        initObjects();


        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<ComprasItems> comprasitensfiltro = new ArrayList<ComprasItems>(0);

                expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), listDataGroup, listDataChild);
                expandableListView = findViewById(R.id.expandableListView);

                expandableListView.setFilterText("Sep");

//                    expandableListView.setAdapter(expandableListViewAdapter);
//                    expandableListViewAdapter.notifyDataSetChanged();


                }



        });



    }


        private void initObjects() {

            // initializing the list of groups
            listDataGroup = new ArrayList<>();

            // initializing the list of child
            listDataChild = new HashMap<>();

            initListData("Todos");
            // initializing the adapter object
            expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);
            expandableListView = findViewById(R.id.expandableListView);

            expandableListView.setAdapter(expandableListViewAdapter);
            expandableListViewAdapter.notifyDataSetChanged();


        }

    private void initListData(String filtro) {


            compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

            compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {

                @Override
                public void onChanged(@Nullable final List<ComprasItems> compras) {


                    List<ComprasItems> comprasitenstotal = compras.stream()
                            .filter(c -> c.compra.getData().toString().contains("Sep"))
                            .collect(Collectors.toList());


                    for (int i = 0; i < comprasitenstotal.size(); i++) {

                        String datacompra = DateFormat.getDateInstance().format(comprasitenstotal.get(i).compra.getData());
                        listDataGroup.add(String.valueOf(datacompra) + "  " +
                                comprasitenstotal.get(i).local.get(0).getDesclocal()
                        );

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


                }
            });

        }


 }

