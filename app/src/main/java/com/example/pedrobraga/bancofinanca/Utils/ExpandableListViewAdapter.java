package com.example.pedrobraga.bancofinanca.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pedro.braga on 05/08/2018.
 */


public class ExpandableListViewAdapter extends BaseExpandableListAdapter
         implements Filterable , ExpandableListAdapter {

    private Context context;
    private List<ComprasItems> listDataGroup ;

    private HashMap<ComprasItems, List<String>> listDataChild;
    private FiltroCompras filtroCompras;

    private List<ComprasItems> listDataGroupOriginal  ;

    private HashMap<ComprasItems, List<String>> listDataChildOriginal;

    private ExpandableListView expandableListView;

    public ExpandableListViewAdapter(Context context,   List<ComprasItems>  listDataGroup,
                                     HashMap<ComprasItems, List<String>> listChildData,ExpandableListView expandableListView) {



        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataGroupOriginal = listDataGroup;
        this.listDataChild = listChildData;
        this.listDataChildOriginal = listChildData;
        this.expandableListView = expandableListView;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .get(childPosititon);
    }




    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_lista_compras_itens, null);
        }

        TextView textViewChild = convertView
                .findViewById(R.id.textViewChild);

        textViewChild.setText(childText);



        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        if (listDataGroup != null) {

            return listDataGroup.size();
        }
        else {

            return 0;
        }

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {



        String pattern = "dd-MMM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String datacompra = simpleDateFormat.format(listDataGroup.get(groupPosition).compra.getData());

        String headerTitle = listDataGroup.get(groupPosition).local.get(0).getDesclocal().toString()
                + datacompra ;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_lista_compras_grupo, null);
        }

        TextView textViewGroup = convertView
                .findViewById(R.id.textViewGroup);
        textViewGroup.setTypeface(null, Typeface.BOLD);
        textViewGroup.setText(headerTitle);


        ImageView btndel = convertView.findViewById(R.id.imgdelcompra);


        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialogo(groupPosition);

            }
        });

        return convertView;
    }


    public void Dialogo(int posicao) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja deletar o item selecionado");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Registro apagado", Toast.LENGTH_SHORT).show();
                listDataGroup.remove(posicao);
                notifyDataSetChanged();


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();



    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public Filter getFilter() {

        if (filtroCompras == null) {
            filtroCompras = new FiltroCompras();
        }
        return filtroCompras;

    }



    public class FiltroCompras extends Filter {


        private FilterResults results;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

             results = new FilterResults();

            if (constraint.toString()!="Todos") {

                String mes = constraint.toString().split("/")[0];
                String ano = constraint.toString().split("/")[1];

                List<ComprasItems> itens = new ArrayList<ComprasItems>(0);

                for (int i=0; i < listDataGroupOriginal.size(); i++ ) {

                    String pattern = "dd-MMM-yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String data = simpleDateFormat.format(listDataGroupOriginal.get(i).compra.getData());

//                    String data;
//                    data = listDataGroupOriginal.get(i).compra.getData().toString();

                    if (data.toLowerCase().contains(mes) &&
                            data.toLowerCase().contains(ano)   ) {

                        itens.add(listDataGroupOriginal.get(i));
                    }

                }

                results.values = itens;
                results.count = itens.size();


            } else {

                results.count = listDataGroup.size();
                results.values = listDataGroup;

            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listDataGroup = (List<ComprasItems>) results.values;
            notifyDataSetChanged();

        }
    }

}

