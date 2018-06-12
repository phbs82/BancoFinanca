package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;
import com.example.pedrobraga.bancofinanca.Utils.ItemListAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.ProdutoViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.R.layout.simple_dropdown_item_1line;

public class CompraCRUD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_crud);

      //  final List<String> PRODUTOS = new ArrayList<String>();

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fbuttonItem);

       //  ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);

        final CompraRepository compraRepository = new CompraRepository(getApplication());
        final ProdutoRepository produtoRepository = new ProdutoRepository(getApplication());

        final int codigocompra = compraRepository.getcodigoCompra();
        final int codigoproduto = produtoRepository.getcodigoProduto();


        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.edtDescricao);

        List<String> teste = new  ArrayList<String>(0);




      // final List<String> produtos = new ArrayList<String>(0);


        final ArrayAdapter<List<String>> produtosadapter = new ArrayAdapter<List<String>>(this,
                android.R.layout.simple_expandable_list_item_1);

        ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);



        mModel.getProdutoAll().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> listaprodutos) {
                // Update the cached copy of the words in the adapter.

              /*  Iterator<String> s = listaprodutos.iterator();
                for (int i=0; i < listaprodutos.size(); i++) {

                    System.out.println("_________________________________________");
                    System.out.println(listaprodutos.get(i).toString());
                    System.out.println("_________________________________________");
                }*/


                produtosadapter.add(listaprodutos);
                textproduto.setAdapter(produtosadapter);

            }
        });


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
     //   mModel.getProdutoAll().observe(this, nameObserver);


      // produtos =  mModel.getProdutoAll().getValue();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editDescricao = (EditText) findViewById(R.id.edtDescricao);
                EditText editValor = (EditText) findViewById(R.id.edtValor);
                EditText editQuantidade = (EditText) findViewById(R.id.edtQuantidade);


                Item item = new Item(codigocompra,codigoproduto);

                item.setDescricao(String.valueOf(editDescricao.getText()));
                item.setValor(Float.parseFloat(String.valueOf(editValor.getText())));
                item.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));


                adapter.insertItem(item);




            }
        });






    }

}
