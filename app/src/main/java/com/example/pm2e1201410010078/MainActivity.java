package com.example.pm2e1201410010078;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm2e1201410010078.clases.SQLiteConexion;
import com.example.pm2e1201410010078.clases.dataBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nombre,telefono,nota;
    Spinner sItems;
    List<String> spinnerArray =  new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sItems = (Spinner) findViewById(R.id.spinner);
        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtNumero);
        nota = (EditText) findViewById(R.id.textNota);
        llenarComboListaPaises();
    }

    private void ClearScreen() {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }

    private void llenarComboListaPaises(){

        spinnerArray.add("HONDURAS ( 504 )");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems.setAdapter(adapter);

    }

    private void mostrarAlerta(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msj);
        builder.setTitle("Falta informacion para en el registro");
        builder.show();

    }

    public void registrarContac(View view) {
        if (nombre.getText().toString().isEmpty()){
            mostrarAlerta("Debe ingresar un nombre.");
            return;
        }
        if (telefono.getText().toString().isEmpty()){
            mostrarAlerta("Debe ingresar un telefono.");
            return;
        }
        if (nota.getText().toString().isEmpty()){
            mostrarAlerta("Debe ingresar una nota de contacto.");
            return;
        }

        String paisSeleccionado = sItems.getSelectedItem().toString();
        SQLiteConexion conexion = new SQLiteConexion(this, dataBase.NAME_DATABASE, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(dataBase.nombre, nombre.getText().toString());
        valores.put(dataBase.telefono, telefono.getText().toString());
        valores.put(dataBase.nota, nota.getText().toString());
        valores.put(dataBase.codigo_pais, paisSeleccionado);

        Long resultado = db.insert(dataBase.TABLA_CONTACTOS, dataBase.id, valores);
        Toast.makeText(getApplicationContext(), "Registro Ingresado: " + resultado.toString(), Toast.LENGTH_LONG).show();

        db.close();

        ClearScreen();
    }


    public void verContact(View view) {
        Intent intent = new Intent(getApplicationContext() , ActivityListContactView.class);
        startActivity(intent);

    }

    public void agregarPais(View view) {
        mostrarDialog();

    }



    public void mostrarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.dialog_pais, null);

        builder.setView(v);
        TextView pais = (TextView) v.findViewById(R.id.dltxtPais);
        TextView codigo_area = (TextView) v.findViewById(R.id.dltxtCodigo);

        builder.setPositiveButton("AGREGAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (!pais.getText().toString().isEmpty()  && !pais.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Se agregó un nuevo país", Toast.LENGTH_SHORT).show();
                            spinnerArray.add(pais.getText().toString() + " " + "( " + codigo_area.getText().toString() + ")");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sItems.setAdapter(adapter);
                        }

                    }
                });
        builder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the
                        // user
                        dialog.cancel();

                    }
                });


        builder.show();
    }
}