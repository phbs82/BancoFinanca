package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class ProdutoViewModel extends AndroidViewModel {

    private ProdutoRepository produtoRepository;
    private int codigo;

    private LiveData<List<String>> ProdutoAll;

    public ProdutoViewModel(Application application) {
        super(application);
        produtoRepository = new ProdutoRepository(application);
        ProdutoAll = produtoRepository.getProdutoAll();
    //    codigo = produtoRepository.getCodigo();

    }


    public int getCodigo(String descricao) {

        return produtoRepository.getcodigoProduto(descricao);

    }


    public LiveData<List<String>> getProdutoAll() {

        return ProdutoAll;

    }


    public Long insert(Produto produto) {
        return produtoRepository.insert(produto);
    }


}