package com.example.pedrobraga.bancofinanca.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class ItemListAdapter  extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView descricao;
        private final TextView quantidade;
        private final TextView valor;


        private ItemViewHolder(View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.txtDescricao);
            quantidade = itemView.findViewById(R.id.txtQuantidade);
            valor = itemView.findViewById(R.id.txtValor);

        }
    }

    private final LayoutInflater mInflater;
    private List<Item> itens = new ArrayList<Item>(0); // Cached copy of words

    public ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_recyclerview_items, parent, false);



        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        if (this.itens != null) {


            Item current = this.itens.get(position);
            holder.descricao.setText(current.getDescricao());
            holder.quantidade.setText(String.valueOf(current.getQuantidade()));
           // holder.valor.setText(String.valueOf((int) current.getValor()));

            holder.valor.setText(String.valueOf(current.getValor()));


        } else {
            // Covers the case of data not being ready yet.
            holder.descricao.setText("Vazio");
            holder.quantidade.setText(0);
            holder.valor.setText(0);

        }
    }

    public void insertItem(Item item) {
        this.itens.add(item);
        notifyItemInserted(getItemCount());





    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (itens != null)
            return itens.size();
        else return 0;


    }


}