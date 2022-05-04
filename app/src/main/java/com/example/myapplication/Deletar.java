package com.example.myapplication;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Deletar extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText edit_ID_id;
    Button btnVoltar,btndelete,btnviewALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_deletar);

        myDb = new DataBaseHelper(this);
        edit_ID_id = (EditText) findViewById(R.id.editText_ID_id);
        btnVoltar = (Button) findViewById(R.id.bntVoltar);
        btndelete = (Button) findViewById(R.id.button_delet);
        btnviewALL = (Button) findViewById(R.id.button_view);

        DeleteData();
        VoltarHome();
        viewALL();

    }

    public void viewALL() {
        btnviewALL.setOnClickListener(
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

    public void DeleteData() {
        btndelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(edit_ID_id.getText().toString());
                        if (deleteRows > 0)
                            Toast.makeText(Deletar.this, "Deletado", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Deletar.this, "Erro ao Deletar", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void VoltarHome(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Deletar.this,MainActivity.class));
            }
        }
      );
    }

    public String getDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Helper Funtions

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}
