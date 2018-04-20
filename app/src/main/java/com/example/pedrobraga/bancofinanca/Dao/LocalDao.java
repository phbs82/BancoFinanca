package com.example.pedrobraga.bancofinanca.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Entity.Produto;

/**
 * Created by pedro.braga on 19/04/2018.
 */
@Dao
public interface LocalDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCompra(Local... local);


    @Update
    public void updateUsers(Local... local);

    @Delete
    public void deleteUsers(Local... local);



    @Query("SELECT * FROM Local")
    public Local[] loadAllLocal();


}
