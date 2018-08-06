package com.example.pedrobraga.bancofinanca;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
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
import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;
import com.example.pedrobraga.bancofinanca.Repository.LocalRepository;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;
import com.example.pedrobraga.bancofinanca.Utils.ItemListAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ItemViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.LocalViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ProdutoViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        final ProdutoViewModel mModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);
        final CompraViewModel compraModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        final int codigocompra = compraModel.getCodigo();
  //      final int codigoproduto = compraModel.getCodigo();

        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.txtproduto);

         final ArrayAdapter<List<String>> produtosadapter = new ArrayAdapter<List<String>>(this,
                android.R.layout.simple_expandable_list_item_1);


        final List<Item> itens = new ArrayList<Item>(0);
        final List<Produto> produtos = new ArrayList<Produto>(0);



        final float[] total = {0};



        mModel.getProdutoAll().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> listaprodutos) {

                produtosadapter.add(listaprodutos);
                textproduto.setAdapter(produtosadapter);

            }
        });


        final LocalViewModel localViewModel = ViewModelProviders.of(this).get(LocalViewModel.class);

        final AutoCompleteTextView txtlocal = (AutoCompleteTextView)
                findViewById(R.id.txtlocal);


        localViewModel.getLocalAll().observe(this, new Observer<List<Local>>() {

            @Override
            public void onChanged(@Nullable List<Local> locals) {

               ArrayAdapter<List<String>> localadapter = new ArrayAdapter<List<String>>(getApplication(),
              android.R.layout.simple_expandable_list_item_1);

              List<String> listalocal = new ArrayList<>(0);
                for(int i =0; i < locals.size(); i++ ) {

                    listalocal.add(locals.get(i).getDesclocal().toString());

                }

                localadapter.add(listalocal);
                txtlocal.setAdapter(localadapter);

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editDescricao = (EditText) findViewById(R.id.txtproduto);
                EditText editValor = (EditText) findViewById(R.id.txtValor);
                EditText editQuantidade = (EditText) findViewById(R.id.txtquantidade);
                Item item = new Item();
                Integer codigoproduto = null;

                if       (mModel.getCodigo(editDescricao.getText().toString()) ==  0) {

                    Produto produto = new Produto();
                    produto.setDescproduto(editDescricao.getText().toString());
                    codigoproduto = (int) (long) mModel.insert(produto);

                     item.setCodigoproduto(codigoproduto);

                }

                else {

                        item.setCodigoproduto(mModel.getProdutoAll().getValue().indexOf(editDescricao.getText().toString()));
                }


                item.setDescricao(String.valueOf(editDescricao.getText()));
                item.setValor(Float.parseFloat(String.valueOf(editValor.getText())));
                item.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));

                itens.add(item);
                adapter.insertItem(item);

                total[0] = total[0] + Float.parseFloat(String.valueOf(editValor.getText()));

                TextView txttotal = (TextView) findViewById(R.id.txtTotal);

                txttotal.setText("Total R$: " + total[0]);

            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adaptersp = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);

        adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptersp);


        Button btncadastra = (Button) findViewById(R.id.btnCadastra);

       final ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
       final CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        final  Map locais = new HashMap();
        if (!(localViewModel.getMapLocais().getValue() == null)) {

            locais.putAll(localViewModel.getMapLocais().getValue());
        }

        btncadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               EditText edtlocal = (EditText) findViewById(R.id.txtlocal);
                try {


                    if (!locais.containsValue(edtlocal.getText().toString())) {


                        Local local2 = new Local();
                        local2.setDesclocal(edtlocal.getText().toString());

                        localViewModel.insert(local2);

                        String teste = String.valueOf(locais.size());
                        Toast toast = Toast.makeText(getApplicationContext(), teste, Toast.LENGTH_LONG);
                        toast.show();

                    }

                    Compra compra = new Compra();

                    EditText edtdata = (EditText) findViewById(R.id.txtdata);
                    compra.setData(DateTypeConverter.toDate(edtdata.getText().toString()));

                    Integer codigolocal = localViewModel.getCodigo(edtlocal.getText().toString());

                    compra.setCodigolocal(codigolocal);

                    //  CompraRepository compraRepository = new CompraRepository(getApplication());
                    Long codcompra = compraViewModel.insert(compra);

                    //    Long codigocompra = compraRepository.insert(compra);
                    Integer codigocompra = (int) (long) codcompra;


                    for (int i = 0; i < itens.size(); i++) {

                        itens.get(i).setCodigocompra(codigocompra);

                        itemViewModel.insert(itens.get(i));

                    }
                }

                catch (Exception e) {

                    System.out.println(e.getMessage());
                }


            }
        });


    } // fim do OnCreate

    public void LimpaCampos() {



    }


}
