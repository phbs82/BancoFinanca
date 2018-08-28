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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.Dao.CompraItensDao;
import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;
import com.example.pedrobraga.bancofinanca.Utils.ExpandableListViewAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ItemViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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



        private void initViews() {

            expandableListView = findViewById(R.id.expandableListView);

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
            if (!expandableListViewAdapter.isEmpty()) {
                expandableListView.setAdapter(expandableListViewAdapter);
                expandableListViewAdapter.notifyDataSetChanged();


            }

        }

        private void initListData() {

           // final ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
            final CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

            List<ComprasItems> compras = new ArrayList<ComprasItems>();

            compras.addAll(compraViewModel.getComprasAll());

            for (int i=0; i < compras.size(); i++ ) {

                Date datacompra = compras.get(i).compra.getData();

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


 }


//  LiveData<List<Compra>> comp = new MutableLiveData<List<Compra>>();

          /*  compraViewModel.getCompraAll().observe(this, new Observer<List<Compra>>() {
                @Override
                public void onChanged(@Nullable final List<Compra> listacompras) {

                    for (int i=0; i < listacompras.size(); i++) {

                        listDataGroup.add(listacompras.get(i).)



                    }
                    listDataGroup.add()

                    produtosadapter.add(listaprodutos);
                    textproduto.setAdapter(produtosadapter);

                }
            });


            comp = compraViewModel.getCompraAll();

            List<Compra> compras = new ArrayList<Compra>();
            compras.addAll(comp.getValue());

            if (compraViewModel.getCompraAll().getValue() != null ) {
                compras.addAll(compraViewModel.getCompraAll().getValue());


            }*/



          /*  List<Item> itens = new ArrayList<Item>();
            if (itemViewModel.getItemAll().getValue()!= null) {

                itens.addAll(itemViewModel.getItemAll().getValue());


            }


            if (compras.size()> 0 ) {

                for (int i=0;i< compras.size();i++) {

                    String descricao =
                            String.valueOf(compras.get(i).getCodigocompra()) +
                                    " " +
                                     toStringFDate(compras.get(i).getData());

                    listDataGroup.add(descricao);

                    List<String> itemlista = new ArrayList<>(0);

                    for (Item item: itens) {

                        if (item.getCodigocompra()==compras.get(i).getCodigocompra()) {

                            itemlista.add("Produto: " + item.getDescricao() +
                            " Quantidade: " + String.valueOf(item.getQuantidade()) + " Valor: " +
                            String.valueOf(item.getValor()));

                        }

                    }

                    listDataChild.put(descricao,itemlista);
                }


                // notify the adapter
                expandableListViewAdapter.notifyDataSetChanged();*/





/*
            // Adding group data
            listDataGroup.add(getString(R.string.text_alcohol));
            listDataGroup.add(getString(R.string.text_coffee));
            listDataGroup.add(getString(R.string.text_pasta));
            listDataGroup.add(getString(R.string.text_cold_drinks));

            // array of strings
            String[] array;

            // list of alcohol
            List<String> alcoholList = new ArrayList<>();
            array = getResources().getStringArray(R.array.string_array_alcohol);
            for (String item : array) {
                alcoholList.add(item);
            }

            // list of coffee
            List<String> coffeeList = new ArrayList<>();
            array = getResources().getStringArray(R.array.string_array_coffee);
            for (String item : array) {
                coffeeList.add(item);
            }

            // list of pasta
            List<String> pastaList = new ArrayList<>();
            array = getResources().getStringArray(R.array.string_array_pasta);
            for (String item : array) {
                pastaList.add(item);
            }

            // list of cold drinks
            List<String> coldDrinkList = new ArrayList<>();
            array = getResources().getStringArray(R.array.string_array_cold_drinks);
            for (String item : array) {
                coldDrinkList.add(item);
            }

            // Adding child data
            listDataChild.put(listDataGroup.get(0), alcoholList);
            listDataChild.put(listDataGroup.get(1), coffeeList);
            listDataChild.put(listDataGroup.get(2), pastaList);
            listDataChild.put(listDataGroup.get(3), coldDrinkList);
*/












