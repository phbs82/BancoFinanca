package com.example.pedrobraga.bancofinanca.Entity;

/**
 * Created by pedro.braga on 19/04/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.widget.EditText;

import com.example.pedrobraga.bancofinanca.Entity.Compra;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Compra.class,
                        parentColumns = "codigocompra",
                        childColumns = "codigocompra"),
                @ForeignKey(entity = Produto.class,
                        parentColumns = "codigoproduto",
                        childColumns = "codigoproduto")

        },
        indices = { @Index("codigocompra"),
                    @Index("codigoproduto")}


        )
public class Item {



    private int codigocompra;

    private int codigoproduto;

    @PrimaryKey(autoGenerate = true)
    private int codigoitem;


    private int quantidade;

    private float valor;


    @Ignore
    private String descricao;





    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCodigocompra() {
        return codigocompra;
    }

    public void setCodigocompra(int codigocompra) {
        this.codigocompra = codigocompra;
    }

    public int getCodigoproduto() {
        return codigoproduto;
    }

    public void setCodigoproduto(int codigoproduto) {
        this.codigoproduto = codigoproduto;
    }

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
