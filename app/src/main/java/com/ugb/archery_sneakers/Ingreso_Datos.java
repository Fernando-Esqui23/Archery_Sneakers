package com.ugb.archery_sneakers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Ingreso_Datos extends AppCompatActivity {
    BD db_adidas;
    String accion="nuevo";
    String id="";
    Button btn;
    TextView temp;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_datos);

        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_lista();
            }
        });
        fab = findViewById(R.id.fabRegresarListaAdidas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaAdidas();
            }
        });
        mostrar_datos_adidas();
    }

    void mostrar_datos_adidas(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if(accion.equals("modificar")){
            String adidas[] = parametros.getStringArray("adidas");
            id = adidas[0];

            temp = findViewById(R.id.txtnombre);
            temp.setText(adidas[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(adidas[2]);

            temp = findViewById(R.id.txtTalla);
            temp.setText(adidas[3]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(adidas[4]);
        }
    }
    void guardar_lista(){
        try {
            temp = (TextView) findViewById(R.id.txtnombre);
            String nombre = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtTalla);
            String talla = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();

            db_adidas = new BD(Ingreso_Datos.this, "",null,1);
            String result = db_adidas.administrar_adidas(id, nombre, descripcion, talla, precio, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListaAdidas();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListaAdidas(){
        Intent iListaAdidas = new Intent(Ingreso_Datos.this, lista_adidas.class);
        startActivity(iListaAdidas);
    }
}