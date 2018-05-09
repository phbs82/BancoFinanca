package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Repository.LocalRepository;

import java.util.List;

/**
 * Created by pedro.braga on 23/04/2018.
 */

public class LocalViewModel     extends AndroidViewModel {

    private LocalRepository localRepository;

    private LiveData<List<Local>> LocalAll;

    public LocalViewModel (Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        LocalAll = localRepository.getLocalAll();
    }

    LiveData<List<Local>> getLocalAll() {
        return LocalAll;
    }

    public void insert(Local local) {
        localRepository.insert(local);
    }


}
