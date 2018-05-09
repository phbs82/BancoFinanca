package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Repository.ProdutoRepository;

import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class ProdutoViewModel extends AndroidViewModel {

    private ProdutoRepository produtoRepository;

    private LiveData<List<Produto>> ProdutoAll;

    public ProdutoViewModel(Application application) {
        super(application);
        produtoRepository = new ProdutoRepository(application);
        ProdutoAll = produtoRepository.getProdutoAll();
    }

    LiveData<List<Produto>> getProdutoAll() {
        return ProdutoAll;
    }

    public void insert(Produto produto) {
        produtoRepository.insert(produto);
    }


}