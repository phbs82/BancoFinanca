package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.pedrobraga.bancofinanca.Utils.ExpandableListViewAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ItemViewModel;

import java.util.ArrayList;
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

        //CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        //CompraRepository compra = new CompraRepository(getApplication());
        //List<Compra> compras = new ArrayList<Compra>();
        //compras.addAll(compra.getCompraAll().getValue());
        //compras.addAll(compra.getComprasAll());



    }



        private void initViews() {

            expandableListView = findViewById(R.id.expandableListView);

        }

        /**
         * method to initialize the listeners
         */
      /*  private void initListeners() {

            // ExpandableListView on child click listener
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Toast.makeText(
                            getApplicationContext(),
                            listDataGroup.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataGroup.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });}

            // ExpandableListView Group expanded listener
           expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                            Toast.LENGTH_SHORT).show();
                }
            });

            // ExpandableListView Group collapsed listener
            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                            Toast.LENGTH_SHORT).show();

                }
            });

        }*/

        /**
         * method to initialize the objects
         */
        private void initObjects() {

            // initializing the list of groups
            listDataGroup = new ArrayList<>();

            // initializing the list of child
            listDataChild = new HashMap<>();


            initListData();
            // initializing the adapter object
            expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);

            // setting list adapter
            if (!expandableListViewAdapter.isEmpty())
                expandableListView.setAdapter(expandableListViewAdapter);

        }

    /*
     * Preparing the list data
     *
     * Dummy Items
     */
        private void initListData() {



           // final ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
            final CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

            List<ComprasItems> compras = new ArrayList<ComprasItems>();

            compras.addAll(compraViewModel.getComprasAll());

            for (int i=0; i < compras.size(); i++ ) {

                listDataGroup.add(compras.get(i).compra.getData())


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

            }


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


        }















