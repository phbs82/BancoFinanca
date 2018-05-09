package com.example.pedrobraga.bancofinanca.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.R;

import java.util.List;

/**
 * Created by pedro.braga on 08/05/2018.
 */

public class ItemListAdapter  extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView descricao;
        private final TextView quantidade;
        private final TextView valor;


        private ItemViewHolder(View itemView, TextView descricao, TextView quantidade, TextView valor) {
            super(itemView);
            this.descricao = descricao;
            this.quantidade = quantidade;
            this.valor = valor;
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Item> itens; // Cached copy of words

    public ItemListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_recyclerview_items, parent, false);
        return new ItemViewHolder(itemView, descricao, quantidade, valor);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }