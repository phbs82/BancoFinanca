package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Compra;

/**
 * Created by pedro.braga on 19/04/2018.
 */


@Dao
public interface CompraDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCompra(Compra... compra);


    @Update
    public void updateUsers(Compra... compra);

    @Delete
    public void deleteUsers(Compra... compra);



    @Query("SELECT * FROM Compra")
    public Compra[] loadAllCompra();



}
