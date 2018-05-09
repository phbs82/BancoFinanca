package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;

import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class CompraViewModel  extends AndroidViewModel {

    private CompraRepository CompraRepository;

    private LiveData<List<Compra>> CompraAll;

    public CompraViewModel(Application application) {
        super(application);
        CompraRepository = new CompraRepository(application);
        CompraAll = CompraRepository.getCompraAll();
    }

    LiveData<List<Compra>> getCompraAll() {
        return CompraAll;
    }

    public void insert(Compra Compra) {
        CompraRepository.insert(Compra);
    }


}