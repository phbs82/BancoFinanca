package com.example.pedrobraga.bancofinanca.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pedro.braga on 05/08/2018.
 */
import com.example.pedrobraga.bancofinanca.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ExpandableListViewAdapter extends BaseExpandableListAdapter
         implements Filterable    {

    private Context context;

    // group titles
    private List<String> listDataGroup;
    private List<String> listDataGroupOriginal;


                 // child data
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<String>> listDataChildOriginal;
    private FiltroCompras filtroCompras;


    public ExpandableListViewAdapter(Context context, List<String> listDataGroup,
                                     HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataGroupOriginal = listDataGroup;
        this.listDataChild = listChildData;
        this.listDataChildOriginal = listChildData;


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

        if (convertView == null) {
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
        return this.listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_lista_compras_grupo, null);
        }

        TextView textViewGroup = convertView
                .findViewById(R.id.textViewGroup);
        textViewGroup.setTypeface(null, Typeface.BOLD);
        textViewGroup.setText(headerTitle);

        return convertView;
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


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();


            if (constraint.toString()!="Todos") {

                String mes = constraint.toString().split("/")[0];
                String ano = constraint.toString().split("/")[1];


                List<String> comprasitenstotal;

                comprasitenstotal = listDataGroupOriginal.stream()
                        .filter(c -> c.toString().contains(mes))
                        .filter(c -> c.toString().contains(ano))
                        .collect(Collectors.toList());

                results.values = comprasitenstotal;
                results.count = comprasitenstotal.size();


            } else {

                results.count = listDataGroup.size();
                results.values = listDataGroup;

            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listDataGroup = (List<String>)results.values;
            notifyDataSetChanged();

        }
    }

}

