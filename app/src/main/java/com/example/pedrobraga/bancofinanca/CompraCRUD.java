package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import java.util.List;

public class CompraCRUD extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_crud);


        final List<String> PRODUTOS = new ArrayList<String>();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fbuttonItem);


        final ArrayAdapter<String> adaptertxtproduto = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PRODUTOS);
        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.edtDescricao);
        textproduto.setAdapter(adaptertxtproduto);



        // Get the ViewModel.
        final ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);


        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                textproduto.setAdapter(adaptertxtproduto);
            }
        };



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(mModel.toString());



             /*   EditText editDescricao = (EditText) findViewById(R.id.edtDescricao);
                EditText editValor = (EditText) findViewById(R.id.edtValor);
                EditText editQuantidade = (EditText) findViewById(R.id.edtQuantidade);

                CompraRepository compraRepository = new CompraRepository(getApplication());
                ProdutoRepository produtoRepository = new ProdutoRepository(getApplication());


                int codigocompra = compraRepository.getcodigoCompra();
                int codigoproduto = produtoRepository.getcodigoProduto();


                Item item = new Item(codigocompra,codigoproduto);

                item.setDescricao(String.valueOf(editDescricao.getText()));
                item.setValor(Float.parseFloat(String.valueOf(editValor.getText())));
                item.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));


                adapter.insertItem(item);*/


            }
        });






    }

}
