package com.example.pedrobraga.bancofinanca;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Utils.ItemListAdapter;
import com.example.pedrobraga.bancofinanca.ViewModel.CompraViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ItemViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.LocalViewModel;
import com.example.pedrobraga.bancofinanca.ViewModel.ProdutoViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        final AutoCompleteTextView textproduto = (AutoCompleteTextView)
                findViewById(R.id.txtproduto);

         final ArrayAdapter<String> produtosadapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item);


        final List<Item> itens = new ArrayList<Item>(0);
        final List<Produto> produtos = new ArrayList<Produto>(0);

        final float[] total = {0};

        mModel.getProdutoAll().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> listaprodutos) {

//                produtosadapter.add(listaprodutos);
                produtosadapter.addAll(listaprodutos);
                textproduto.setAdapter(produtosadapter);

            }
        });


        final LocalViewModel localViewModel = ViewModelProviders.of(this).get(LocalViewModel.class);

        final AutoCompleteTextView txtlocal = (AutoCompleteTextView)
                findViewById(R.id.txtlocal);


        localViewModel.getLocalAll().observe(this, new Observer<List<Local>>() {

            @Override
            public void onChanged(@Nullable List<Local> locals) {

               ArrayAdapter<String> localadapter = new ArrayAdapter<String>(getApplication(),
              android.R.layout.simple_expandable_list_item_1);

              List<String> listalocal = new ArrayList<>(0);
                for(int i =0; i < locals.size(); i++ ) {

                    listalocal.add(locals.get(i).getDesclocal().toString());

                }

                localadapter.addAll(listalocal);
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
                     item.setDescricao(editDescricao.getText().toString());

                }

                else {

                        item.setCodigoproduto(mModel.getProdutoAll().getValue().indexOf(editDescricao.getText().toString()));
                }


                item.setDescricao(String.valueOf(editDescricao.getText()));
                item.setValor(Float.parseFloat(String.valueOf(editValor.getText())));
                item.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));

                itens.add(item);
                adapter.insertItem(item);

                total[0] = total[0] +  Integer.parseInt(editQuantidade.getText().toString()) *
                        Float.parseFloat(String.valueOf(editValor.getText()));

                TextView txttotal = (TextView) findViewById(R.id.txtTotal);

                txttotal.setText("Total R$: " + total[0]);

            }
        });



        Button btncadastra = (Button) findViewById(R.id.btnCadastra);

       final ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
       final CompraViewModel compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);

        final  Map locais = new HashMap();
        if (!(localViewModel.getMapLocais().getValue() == null)) {

            locais.putAll(localViewModel.getMapLocais().getValue());
        }


        final EditText edtdata = (EditText) findViewById(R.id.txtdata);
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                edtdata.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edtdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  new DatePickerDialog(CompraCRUD.this, datePickerListener, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });



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
                        Toast toast = Toast.makeText(getApplicationContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG);
                        toast.show();

                    }

                    Compra compra = new Compra();

                    EditText edtdata = (EditText) findViewById(R.id.txtdata);

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    compra.setData(df.parse(edtdata.getText().toString()));

                    Integer codigolocal = localViewModel.getCodigo(edtlocal.getText().toString());

                    compra.setCodigolocal(codigolocal);

                    Long codcompra = compraViewModel.insert(compra);

                    Integer codigocompra = (int) (long) codcompra;


                    for (int i = 0; i < itens.size(); i++) {

                        itens.get(i).setCodigocompra(codigocompra);


                        itemViewModel.insert(itens.get(i));

                    }


                    edtdata.setText("");
                    edtlocal.setText("");

                    EditText edtproduto = (EditText) findViewById(R.id.txtproduto);
                    edtproduto.setText("");

                    EditText edtquantidade = (EditText) findViewById(R.id.txtquantidade);
                    edtquantidade.setText("");


                    EditText edtvalor = (EditText) findViewById(R.id.txtValor);
                    edtvalor.setText("");

                    itens.clear();





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
