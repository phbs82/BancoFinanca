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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;
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




        final RecyclerView recyclerView = findViewById(R.id.rvItens);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fbaddItem);

        final CompraRepository compraRepository = new CompraRepository(getApplication());
        final ProdutoRepository produtoRepository = new ProdutoRepository(getApplication());

        final int codigocompra = compraRepository.getcodigoCompra();
        final int codigoproduto = produtoRepository.getcodigoProduto();


        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.txtproduto);

        List<String> teste = new  ArrayList<String>(0);

        final ArrayAdapter<List<String>> produtosadapter = new ArrayAdapter<List<String>>(this,
                android.R.layout.simple_expandable_list_item_1);

        ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);

        final List<Item> itens = new ArrayList<Item>(0);


        final float[] total = {0};

        mModel.getProdutoAll().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> listaprodutos) {

                produtosadapter.add(listaprodutos);
                textproduto.setAdapter(produtosadapter);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editDescricao = (EditText) findViewById(R.id.txtproduto);
                EditText editValor = (EditText) findViewById(R.id.txtValor);
                EditText editQuantidade = (EditText) findViewById(R.id.txtquantidade);


                Item item = new Item(codigocompra,codigoproduto);

                item.setDescricao(String.valueOf(editDescricao.getText()));
                item.setValor(Float.parseFloat(String.valueOf(editValor.getText())));
                item.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));

                itens.add(item);
                adapter.insertItem(item);

                total[0] = total[0] + Float.parseFloat(String.valueOf(editValor.getText()));


                TextView txttotal = (TextView) findViewById(R.id.txtTotal);

                txttotal.setText("Total R$: " + txttotal);


            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adaptersp = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);

        adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptersp);


        Button btncadastra = (Button) findViewById(R.id.btnCadastra);

        btncadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Compra compra = new Compra();

                compra.setCodigocompra(codigocompra);
                Toast.makeText(getApplication(),String.valueOf(codigocompra),Toast.LENGTH_LONG).show();

                EditText edtdata = (EditText) findViewById(R.id.txtdata);
                compra.setData(DateTypeConverter.toDate(edtdata.getText().toString()));

                EditText edtlocal = (EditText) findViewById(R.id.txtlocal);
             //   compra.setCodigolocal();



            }
        });


    } // fim do OnCreate

    public void LimpaCampos() {



    }


}
