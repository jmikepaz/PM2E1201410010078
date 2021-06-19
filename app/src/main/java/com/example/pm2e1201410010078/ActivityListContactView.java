package com.example.pm2e1201410010078;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pm2e1201410010078.clases.Contactos;
import com.example.pm2e1201410010078.clases.SQLiteConexion;
import com.example.pm2e1201410010078.clases.dataBase;

import java.util.ArrayList;
import android.widget.Toolbar;

public class ActivityListContactView extends AppCompatActivity {
    /* Variables globales de la Actividad */
    SQLiteConexion conexion;
    ListView vistaListaPersonas;
    ArrayList<Contactos> lista;
    ArrayList<String> arregloPersonas;
    public String id_contacto="";
    public String telefono_contacto="";
    TextView contactoSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contactos);
        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_contacto="";
        telefono_contacto = "";
        contactoSeleccionado = (TextView) findViewById(R.id.txtContactoSeleccionado) ;

        conexion = new SQLiteConexion(this, dataBase.NAME_DATABASE, null, 1);
        vistaListaPersonas = (ListView) findViewById(R.id.lv_listado_contactos);

        obtenerListaPersonas();

        ArrayAdapter adaptadorArreglo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPersonas);
        vistaListaPersonas.setAdapter(adaptadorArreglo);

        vistaListaPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactoSeleccionado.setText("ID: "+lista.get(position).getId() + " NOMBRE: "+lista.get(position).getNombre() +" TELEFONO: " +lista.get(position).getTelefono());
                id_contacto = ""+lista.get(position).getId();
                telefono_contacto = lista.get(position).getTelefono();
            }
        });
    }

    private void obtenerListaPersonas(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos listaPersonas;
        lista = new ArrayList<>();

        Cursor cursorConsulta = db.rawQuery("SELECT * FROM " + dataBase.TABLA_CONTACTOS, null);

        while (cursorConsulta.moveToNext()){
            listaPersonas = new Contactos();
            listaPersonas.setId(cursorConsulta.getInt(0));
            listaPersonas.setNombre(cursorConsulta.getString(1));
            listaPersonas.setTelefono(cursorConsulta.getString(2));
            listaPersonas.setNota(cursorConsulta.getString(3));
            listaPersonas.setCodigo_pais(cursorConsulta.getString(4));

            lista.add(listaPersonas);
        }

        cursorConsulta.close();
        fillList();

    }

    private void fillList() {
        arregloPersonas = new ArrayList<>();
        for(int i = 0; i < lista.size(); i++){
            arregloPersonas.add(lista.get(i).getId()+" | " +lista.get(i).getNombre() + " | "
                                +lista.get(i).getTelefono() + " | " +lista.get(i).getCodigo_pais());
        }
    }

    public void eliminarContacto(View view) {
        if (!id_contacto.toString().isEmpty()){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {id_contacto.toString()};

        db.delete(dataBase.TABLA_CONTACTOS, dataBase.id + "=?", params);
        obtenerListaPersonas();
        ArrayAdapter adaptadorArreglo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPersonas);
        vistaListaPersonas.setAdapter(adaptadorArreglo);
        contactoSeleccionado.setText("SELECCIONE UN CONTACTO");
        Toast.makeText(getApplicationContext(), "Registro Eliminado " + id_contacto, Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar un contacto antes de eliminar.", Toast.LENGTH_LONG).show();
        }
    }

    public void llamarContacto(View view) {
        if (telefono_contacto.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe seleccionar un contacto.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" +telefono_contacto));
        startActivity(intent);
    }
}