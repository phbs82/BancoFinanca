package com.example.pedrobraga.bancofinanca.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pedro.braga on 05/08/2018.
 */
import com.example.pedrobraga.bancofinanca.POJO.ComprasItems;
import com.example.pedrobraga.bancofinanca.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

        String headerTitle = listDataGroup.get(groupPosition).local.get(0).getDesclocal().toString();


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

                 CharSequence text = String.valueOf(groupPosition) ;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


            }
        });




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


        private FilterResults results;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

             results = new FilterResults();


            if (constraint.toString()!="Todos") {

                String mes = constraint.toString().split("/")[0];
                String ano = constraint.toString().split("/")[1];

               // List<String> comprasitenstotal = new ArrayList<String>(0);

                List<ComprasItems> itens = new ArrayList<ComprasItems>(0);

                for (int i=0; i < listDataGroupOriginal.size(); i++ ) {

                    String data;
                    data = listDataGroupOriginal.get(i).compra.getData().toString();

/*                    if (data.toLowerCase().contains(mes) &&
                            data.toLowerCase().contains(ano)   ) {

                        comprasitenstotal.add(listDataGroupOriginal.get(i).local.get(0).getDesclocal() +
                       "  " + data  );

                    }*/

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

