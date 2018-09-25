package com.example.pedrobraga.bancofinanca;

import android.accessibilityservice.AccessibilityButtonController;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton b = (ImageButton) findViewById(R.id.btncompra);


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



    }
}
