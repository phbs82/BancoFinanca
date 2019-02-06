package com.example.pedrobraga.bancofinanca.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;

import java.util.Date;
import java.util.List;

/**
 * Created by pedro.braga on 20/08/2018.
 */

public class ComprasItems {



    @Embedded
    public Compra compra;

    @Relation(parentColumn = "codigocompra", entityColumn = "codigocompra", entity = Item.class)
    public List<Item> itens;

    @Relation(parentColumn = "codigolocal", entityColumn = "codigolocal", entity = Local.class)
    public List<Local> local;







}


