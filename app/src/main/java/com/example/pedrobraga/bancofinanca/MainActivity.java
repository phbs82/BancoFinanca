package com.example.pedrobraga.bancofinanca;

import android.accessibilityservice.AccessibilityButtonController;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.pedrobraga.bancofinanca.Utils.CustomGridViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     /*   ImageButton b = (ImageButton) findViewById(R.id.btncompra);

      //  GridInflate();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(getApplicationContext(),CompraCRUD.class);
                startActivity(intent);

            }
        });

        ImageButton  compras = (ImageButton) findViewById(R.id.btnlistaCompras);
        compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(getApplicationContext(),ListaCompras.class);
                startActivity(intent);

            }
        });
*/


        GridView androidGridView;

        String[] gridViewString = {
                "Carrinho", "Dinheiro", "Acao",


        } ;
        int[] gridViewImageId = {
                R.drawable.ic_action_carrinho, R.drawable.ic_action_money, R.drawable.ic_action_name,

        };

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);


        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent;

                switch (((int) id)) {

                    case 0:
                        Intent intent = new Intent(getBaseContext(),CompraCRUD.class);
                        startActivity(intent);

                    case 1:

                       Intent intent2 = new Intent(getBaseContext(),ListaCompras.class);
                        startActivity(intent2);

                    default:
                        Intent intent3 = new Intent(getBaseContext(),Grafico.class);
                        startActivity(intent3);

                }
               }




        });

    }


    public void GridInflate() {

        GridView androidGridView;

        String[] gridViewString = {
                "Carrinho", "Dinheiro", "Acao",


        } ;
        int[] gridViewImageId = {
                R.drawable.ic_action_carrinho, R.drawable.ic_action_money, R.drawable.ic_action_name,

        };

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);







    }



}
