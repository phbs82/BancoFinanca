package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Repository.LocalRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by pedro.braga on 23/04/2018.
 */

public class LocalViewModel     extends AndroidViewModel {

    private LocalRepository localRepository;

    private LiveData<List<Local>> localAll;

    public LocalViewModel (Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        localAll = new MutableLiveData<List<Local>>();

    }


    public LiveData<Map> getMapLocais() {

        return localRepository.getMapLocais();

    }

    public int getCodigo(String local) {

        Integer codigo = localRepository.getCodigo(local);

        return codigo;

    }

    public LiveData<List<Local>> getLocalAll() {


            this.localAll = localRepository.getLocalAll();

            return  this.localAll;
    }

    public Long insert(Local local) {

       return localRepository.insert(local);

    }


    /*  private LiveData<Map> locais =
            Transformations.map(localAll, new Function<List<Local>, Map>() {
                        @Override
                        public Map apply(List<Local> input) {

                            Map mapLocais = new HashMap();
                            System.out.println("*****************************");
                            System.out.println(String.valueOf(localAll.getValue().size()));

                            for(int i =0; i < input.size(); i++) {

                                mapLocais.put(input.get(i).getCodigolocal(),input.get(i).getDesclocal());

                            }


                            return mapLocais;
                        }
                    });
                    // (locals) -> {   locals.get(0).getDesclocal().toString() });
*/



}
