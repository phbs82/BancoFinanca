package com.example.pedrobraga.bancofinanca;

import android.Manifest;
import android.accessibilityservice.AccessibilityButtonController;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pedrobraga.bancofinanca.Utils.CustomGridViewActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Intent intent, intent2, intent3;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestStoragePermission();

        GridView androidGridView;

        String[] gridViewString = {
                "Cadastro Compras", "Lista Compras", "Gráfico Compras",


        } ;
        int[] gridViewImageId = {
                R.drawable.ic_icone_shop, R.drawable.ic_listagem, R.drawable.ic_grafico,

        };

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);



        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent;

                switch (((int) position)) {

                    case 0:
                        intent = new Intent(getApplicationContext(),CompraCRUD.class);
                        startActivity(intent);
                        break;

                    case 1:

                       intent2 = new Intent(getApplicationContext(),ListaCompras.class);
                       startActivity(intent2);
                       break;

                    default:
                        intent3 = new Intent(getApplicationContext(),Grafico.class);
                        startActivity(intent3);
                        break;

                }
               }

        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listagem_compras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.cadastro:
                intent = new Intent(getApplicationContext(),CompraCRUD.class);
                startActivity(intent);
                return true;
            case R.id.listagem:
                intent = new Intent(getApplicationContext(),ListaCompras.class);
                startActivity(intent);
                return true;
            default:
                intent = new Intent(getApplicationContext(),Grafico.class);
                startActivity(intent);
                return true;
        }

    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                           // showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);

    }


}
