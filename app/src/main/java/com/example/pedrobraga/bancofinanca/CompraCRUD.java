package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;
import com.example.pedrobraga.bancofinanca.Utils.ItemListAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.ProdutoViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompraCRUD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_crud);

        final List<String> PRODUTOS = new ArrayList<String>();

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fbuttonItem);

//        final ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);
//
//        PRODUTOS.addAll((Collection<? extends String>) mModel.getProdutoAll());





        final CompraRepository compraRepository = new CompraRepository(getApplication());
        final ProdutoRepository produtoRepository = new ProdutoRepository(getApplication());

        final int codigocompra; //= compraRepository.getcodigoCompra();
        final int codigoproduto; //= produtoRepository.getcodigoProduto();


          class getProdutos extends  AsyncTask<Void,Void,Void> {


              @Override
              protected Void doInBackground(Void... voids) {
                  codigocompra = compraRepository.getcodigoCompra();
                  codigoproduto = = produtoRepository.getcodigoProduto();
              }
          }







    //    PRODUTOS.addAll(produtoRepository.getProdutos());


        final ArrayAdapter<String> adaptertxtproduto = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PRODUTOS);

        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.edtDescricao);

        textproduto.setAdapter(adaptertxtproduto);




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
