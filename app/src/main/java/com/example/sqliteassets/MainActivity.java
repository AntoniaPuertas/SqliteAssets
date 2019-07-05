package com.example.sqliteassets;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BaseHelper baseHelper;
    private static String DB_NAME = "notas_bd";
    Cursor cursor;

    TextView txtNotas;
    String notas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNotas = findViewById(R.id.txtNotas);

        //inicializamos la base de datos
        baseHelper = new BaseHelper(this, DB_NAME, null, 1);
        try{
            baseHelper.createDataBase();
            baseHelper.openDataBase();
        }catch (IOException e){
            e.printStackTrace();
        }

        //obtenemos todas las filas de la tabla notas
        cursor = baseHelper.getReadableDatabase().rawQuery("select * from  notas", null);
        //guardamos los datos en un String
        notas = "";
        //recorremos el cursor
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String nota = "Nombre: ";
                nota+= cursor.getString(cursor.getColumnIndex("nombre"));
                nota+= "\nDescripción: ";
                nota+= cursor.getString(cursor.getColumnIndex("descripcion"));
                nota+= "\nImportancia: ";
                nota+= cursor.getString(cursor.getColumnIndex("importancia"));

                notas+=nota + "\n\n";
                cursor.moveToNext();
            }
        }

        txtNotas.setText(notas);
    }
}
