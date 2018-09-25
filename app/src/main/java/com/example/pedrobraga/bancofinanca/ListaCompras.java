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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter.toStringFDate;

public class ListaCompras extends AppCompatActivity {

    private ExpandableListView expandableListView;

    private  ExpandableListViewAdapter expandableListViewAdapter;

    private List<String> listDataGroup;

    private HashMap<String, List<String>> listDataChild;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        initObjects();

    }


        private void initObjects() {

            // initializing the list of groups
            listDataGroup = new ArrayList<>();

            // initializing the list of child
            listDataChild = new HashMap<>();

            initListData();
            // initializing the adapter object
            expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);
            expandableListView = findViewById(R.id.expandableListView);

            // setting list adapter
        //    if (!expandableListViewAdapter.isEmpty()) {
                expandableListView.setAdapter(expandableListViewAdapter);
                expandableListViewAdapter.notifyDataSetChanged();


//            int i = expandableListView.getCount();
//            Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_LONG);
//            toast.show();



            ///  }

        }

        private void initListData() {

            final CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);


            compraViewModel.getCompraItens().observe(this, new Observer<List<ComprasItems>>() {
                @Override
                public void onChanged(@Nullable List<ComprasItems> compras) {

                    for (int i=0; i < compras.size(); i++ ) {

                        String datacompra = DateFormat.getDateInstance().format(compras.get(i).compra.getData());
                        listDataGroup.add(String.valueOf(datacompra) + "  " +
                                compras.get(i).local.get(0).getDesclocal()
                        );

                        List<String> itens  = new ArrayList<>(0);
                        for (int j = 0; j < compras.get(i).itens.size(); j++) {

                            itens.add(compras.get(i).itens.get(j).getDescricao() + " " +
                                    String.valueOf(compras.get(i).itens.get(j).getValor()));

                        }
                        listDataChild.put(listDataGroup.get(i),itens);

                    }


                }
            });

//            List<ComprasItems> compras = new ArrayList<ComprasItems>();
//
//            compras.addAll(compraViewModel.getComprasAll());


        }


 }

