package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */


@Dao
public interface CompraDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insert(Compra compra);


    @Update
    public void update(Compra... compra);

    @Delete
    public void delete(Compra... compra);



    @Query("SELECT * FROM Compra")
    public LiveData<List<Compra>> loadAllCompra();


    @Query("SELECT * FROM Compra")
    public List<Compra> getComprasAll();

    @Transaction
    @Query("SELECT * from Compra")
    public LiveData<List<ComprasItems>> ComprasItens();






}
