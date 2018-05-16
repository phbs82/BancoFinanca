package com.example.pedrobraga.bancofinanca;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;
import com.example.pedrobraga.bancofinanca.Utils.ItemListAdapter;

public class CompraCRUD extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_crud);




        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CompraRepository compraRepository = new CompraRepository(getApplication());
        ProdutoRepository produtoRepository = new ProdutoRepository(getApplication());


        final int codigocompra = compraRepository.getcodigoCompra();
        final int codigoproduto = produtoRepository.getcodigoProduto();

        Button btnAdd = (Button) findViewById(R.id.button5);

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
