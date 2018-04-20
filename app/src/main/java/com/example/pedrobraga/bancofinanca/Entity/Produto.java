package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pedro.braga on 19/04/2018.
 */

@Entity
public class Produto {

    @PrimaryKey(autoGenerate = true)
    private int codigoproduto;


    private String descproduto;

    public int getCodigoproduto() {
        return codigoproduto;
    }

    public void setCodigoproduto(int codigoproduto) {
        this.codigoproduto = codigoproduto;
    }

    public String getDescproduto() {
        return descproduto;
    }

    public void setDescproduto(String descproduto) {
        this.descproduto = descproduto;
    }
}
