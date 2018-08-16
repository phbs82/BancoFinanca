package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Repository.CompraRepository;

import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class CompraViewModel  extends AndroidViewModel {

    private CompraRepository CompraRepository;
    private int codigo;
    private LiveData<List<Compra>> CompraAll;

    public CompraViewModel(Application application) {
        super(application);
        CompraRepository = new CompraRepository(application);
        this.CompraAll =  new MutableLiveData<List<Compra>>();
     //   codigo = CompraRepository.getcodigoCompra();

    }


    public int getCodigo() {

        return this.codigo;


    }

    public LiveData<List<Compra>> getCompraAll() {

        return CompraRepository.getCompraAll();

    }

    public Long insert(Compra compra) {
        Long codigocompra;
        return CompraRepository.insert(compra);
    }

    public void delete(Compra compra) {

        CompraRepository.delete(compra);

    }


}