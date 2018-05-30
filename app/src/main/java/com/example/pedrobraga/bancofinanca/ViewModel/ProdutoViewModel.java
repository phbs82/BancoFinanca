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

    private LiveData<List<String>> ProdutoAll;


    private MutableLiveData<List<String>> userLiveData =
            new MutableLiveData<>();

    public ProdutoViewModel(Application application) {
        super(application);
        produtoRepository = new ProdutoRepository(application);
        ProdutoAll = produtoRepository.getProdutoAll();
    }

    public MutableLiveData<List<String>> getProdutoAll() {

        if(userLiveData==null) {

            userLiveData = new MutableLiveData<List<String>>();
            List<String> produtos = new ArrayList<String>();
            produtos.add("Refrigerante");
            produtos.add("Leite");
            userLiveData.setValue(produtos);

        }
        return userLiveData;
    }

    public void insert(Produto produto) {
        produtoRepository.insert(produto);
    }


}