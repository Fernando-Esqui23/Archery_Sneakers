package com.ugb.archery_sneakers;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class lista_adidas extends AppCompatActivity {

    Bundle parametros = new Bundle();
    BD db_adidas;
    ListView lts;
    Cursor cAdidas;
    FloatingActionButton btn;
    final ArrayList<String> alAdidas = new ArrayList<String>();
    final ArrayList<String> alAdidasCopy = new ArrayList<String>();


    FloatingActionButton fabBtn;
    final ArrayList<String> Adidaslist = new ArrayList<String>();
    final ArrayList<String> alAmigosCopy = new ArrayList<String>();
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.lista_adidas);
        obtenerDatosAdidas();
        Buscarcalzado();
        fabBtn = findViewById(R.id.btnAgregarcalzado);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirAgregarcalzado(parametros);
            }
        });
    }

    public void menu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void abrirAgregarcalzado (Bundle parametros){
        Intent iAgregarcalzado = new Intent(lista_adidas.this, Ingreso_Datos.class);
        iAgregarcalzado.putExtras(parametros);
        startActivity(iAgregarcalzado);
    }
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuadidas, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cAdidas.moveToPosition(info.position);
        menu.setHeaderTitle(cAdidas.getString(1)); //1=> Nombre del amigo...
    }

    @Override
    public boolean onContextItemSelected (@NonNull MenuItem item){
        try {
            switch (item.getItemId()) {
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirAgregarcalzado(parametros);
                    return true;

                case R.id.mnxModificar:
                    String adidas[] = {
                            cAdidas.getString(0), //idAmigo
                            cAdidas.getString(1), //nombre
                            cAdidas.getString(2), //direccion
                            cAdidas.getString(3), //telefono
                            cAdidas.getString(4), //email
                    };
                    parametros.putString("accion", "modificar");
                    parametros.putStringArray("adidas", adidas);
                    abrirAgregarcalzado(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatosAdidas();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
    }
    void eliminarDatosAdidas() {
        try {
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_adidas.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cAdidas.getString(1));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_adidas.administrar_adidas(cAdidas.getString(0), "", "", "", "", "eliminar");
                    obtenerDatosAdidas();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerDatosAdidas () {
        try {
            db_adidas = new BD(lista_adidas.this, "", null, 1);
            cAdidas = db_adidas.consultar_adidas();
            if (cAdidas.moveToFirst()) {
                lts = findViewById(R.id.ltszapatos);
                final ArrayAdapter<String> adAmigos = new ArrayAdapter<String>(lista_adidas.this,
                        android.R.layout.simple_expandable_list_item_1, alAdidas);
                lts.setAdapter(adAmigos);
                do {
                    alAdidas.add(cAdidas.getString(1));//1 es el nombre del calzado, pues 0 es el idAdidas.
                } while (cAdidas.moveToNext());
                adAmigos.notifyDataSetChanged();
                registerForContextMenu(lts);
            } else {
                Toast.makeText(this, "NO HAY datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al obtener amigos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void Buscarcalzado() {
        TextView temp = findViewById(R.id.txtBuscarcalzado);
        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    alAdidas.clear();
                    String valor = temp.getText().toString().trim().toLowerCase();
                    if (valor.length() <= 0) {//es porque no esta escribiendo mostramos
                        // la lista completa de calzado
                        alAdidas.addAll(alAdidasCopy);
                    } else { //si esta buscando calzado...
                        for (String item : alAdidasCopy) {
                            if (item.toLowerCase().contains(valor)) {
                                alAdidas.add(item);
                            }
                        }
                    }
                    // Update the ListView with the filtered results
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(lista_adidas.this, android.R.layout.simple_list_item_1, alAdidas);
                    lts.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(lista_adidas.this, "Error al buscar : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}