package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    Button Adicionar, Escala, Deletar, Editar;
    ListView userlist;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
        listItem = new ArrayList<>();

        Adicionar = findViewById(R.id.Adicionar);
        Escala = findViewById(R.id.Escala);
        Deletar = findViewById(R.id.Deletar);
        Editar = findViewById(R.id.Editar);
        userlist = findViewById(R.id.listView);

        telaEditar();
        telaDeletar();
        telaAdicionar();
        viewALL();
        viewData();
    }

    private void viewData() {
        Cursor cursor = myDb.viewData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nada encontrado", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listItem.add("Nome : " + cursor.getString(1) + "\n"
                        + "Horario :" + cursor.getString(2) + "\n"
                        + "Status :" + cursor.getString(3) + "\n"
                        + "Valor :" + cursor.getString(4) + "\n"
                        + "Data :" + cursor.getString(5));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }
    }


    public void viewALL() {
        Escala.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            // MENSAGEM
                            showMessage("Error", "Nada encontrado");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id : " + res.getString(0) + "\n");
                            buffer.append("Nome : " + res.getString(1) + "\n");
                            buffer.append("Horário : " + res.getString(2) + "\n");
                            buffer.append("Data : " + res.getString(5) + "\n");
                            buffer.append("Status : " + res.getString(3) + "\n");
                            buffer.append("Valor : R$ " + res.getString(4) + "\n");
                            buffer.append("Bônus : R$ " + res.getFloat(4) / 10 + "\n\n");
                        }
                        showMessage("Escala : " + getDate(), buffer.toString());
                    }
                }
        );
    }

    public void telaEditar() {
        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Editar.class));
            }
        });
    }

    public void telaDeletar() {
        Deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Deletar.class));
            }
        });
    }

    public void telaAdicionar() {
        Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Adicionar.class));
            }
        });
    }

    //Helper functions
    public String getDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
