package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Produto;

/**
 * Created by pedro.braga on 19/04/2018.
 */

@Dao
public interface ProdutoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Produto... produto);


    @Update
    public void update(Produto... produto);

    @Delete
    public void delete(Produto... produto);



    @Query("SELECT * FROM Produto")
    public Produto[] loadallProduto();


}
