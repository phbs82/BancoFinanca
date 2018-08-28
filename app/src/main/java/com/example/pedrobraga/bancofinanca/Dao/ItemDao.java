package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Local;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */
@Dao
public interface ItemDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Item... item);


    @Update
    public void update(Item... item);

    @Delete
    public void delete(Item... item);



    @Query("SELECT * FROM Item order by codigocompra")
    public LiveData<List<Item>> loadallItem();


    @Query("Select descproduto d from produto d, item i where d.codigoproduto = i.codigoproduto")
    public String getDescProduto();




}
