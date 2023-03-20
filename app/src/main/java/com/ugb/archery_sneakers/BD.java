package com.ugb.archery_sneakers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends  SQLiteOpenHelper {
    public static final String dbname = "db_adidas";
    public static final int v = 1;
    static final String sqlDb = "CREATE TABLE adidas(idAdidas integer primary key autoincrement, nombre text, descripcion text, talla integer, precio text)";

    public BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlDb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public String administrar_adidas(String id, String nom, String des, String tall, String pre, String accion){
        try{
            SQLiteDatabase db = getWritableDatabase();
            if(accion.equals("nuevo")){
                db.execSQL("INSERT INTO adidas(nombre,descripcion,talla,precio) VALUES('"+nom+"','"+des+"','"+tall+"','"+pre+"')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE adidas SET nombre='"+nom+"', descripcion='"+des+"', talla='"+tall+"', precio='"+pre+"' WHERE idAdidas='"+id+"'");
            }else if(accion.equals("eliminar")){
                db.execSQL("DELETE FROM adidas WHERE idAdidas='"+id+"'");
            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }


    public Cursor consultar_adidas(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM adidas ORDER BY nombre";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

}
