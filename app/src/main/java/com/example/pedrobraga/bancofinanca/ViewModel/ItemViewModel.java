package com.example.pedrobraga.bancofinanca.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Repository.ItemRepository;

import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class ItemViewModel extends AndroidViewModel {

    private com.example.pedrobraga.bancofinanca.Repository.ItemRepository ItemRepository;

    private LiveData<List<Item>> ItemAll;

    public ItemViewModel(Application application) {
        super(application);
        ItemRepository = new ItemRepository(application);
        ItemAll = ItemRepository.getItemAll();
    }

    LiveData<List<Item>> getItemAll() {
        return ItemAll;
    }

    public void insert(Item Item) {
        ItemRepository.insert(Item);
    }


}