package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Repository.LocalRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pedro.braga on 23/04/2018.
 */

public class LocalViewModel     extends AndroidViewModel {

    private LocalRepository localRepository;

    private MutableLiveData<List<Local>> localAll;

    public LocalViewModel (Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        localAll = localRepository.getLocalAll();
    }


    private LiveData<Map> locais =
            Transformations.map(localAll, new Function<List<Local>, Map>() {
                        @Override
                        public Map apply(List<Local> input) {

                            Map mapLocais = new HashMap();

                            for(int i =0; i < input.size(); i++) {

                                mapLocais.put(input.get(i).getCodigolocal(),input.get(i).getDesclocal());

                            }


                            return mapLocais;
                        }
                    });
                    // (locals) -> {   locals.get(0).getDesclocal().toString() });



    public LiveData<Map> getMapLocais() {


        return this.locais;

    }

    public int getCodigo(String local) {


        Integer codigo = localRepository.getCodigo(local);

        return codigo;

    }

    public MutableLiveData<List<Local>> getLocalAll() {

        if (localAll==null) {

            localAll =  new MutableLiveData<List<Local>>();
     }

        return  localAll;
    }

    public void insert(Local local) {
        localRepository.insert(local);
    }


}
