package com.example.pedrobraga.bancofinanca.Entity;

/**
 * Created by pedro.braga on 19/04/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.pedrobraga.bancofinanca.Entity.Compra;

@Entity(tableName = "user_repo_join",
        primaryKeys = { "userId", "repoId" },
        foreignKeys = {
                @ForeignKey(entity = Compra.class,
                        parentColumns = "codigocompra",
                        childColumns = "codigocompra"),
                @ForeignKey(entity = Produto.class,
                        parentColumns = "codigoproduto",
                        childColumns = "codigoproduto")

        } )

public class Item {



    private int codigocompra;

    private int codigoproduto;

    @PrimaryKey(autoGenerate = true)
    private int codigoitem;

    private float valor;


    public Item(final int codigocompra, final int codigoproduto) {

        this.codigocompra = codigocompra;
        this.codigoproduto = codigoproduto;


    }


    public int getCodigoitem() {
        return codigoitem;
    }

    public void setCodigoitem(int codigoitem) {
        this.codigoitem = codigoitem;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }







}
