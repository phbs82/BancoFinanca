package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Entity.Produto;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */
@Dao
public interface LocalDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Local... local);


    @Update
    public void update(Local... local);

    @Delete
    public void delete(Local... local);



    @Query("SELECT * FROM Local")
    public LiveData<List<Local>> loadAllLocal();


    @Query("SELECT * FROM Local")
    public LiveData<Local> loadLocal();

}
