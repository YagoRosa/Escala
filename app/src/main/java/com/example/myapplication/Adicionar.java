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
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Adicionar extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editNome, editHorario, editStatus, editValor,editData;
    Button bntVoltar,btnAddData,btnviewALL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_adicionar);


        myDb = new DataBaseHelper(this);
        editNome = (EditText)findViewById(R.id.editText_nome_id);
        editHorario = (EditText)findViewById(R.id.editText_horario_id);
        editData = (EditText)findViewById(R.id.data_id);
        editStatus = (EditText)findViewById(R.id.editText_Status_id);
        editValor = (EditText)findViewById(R.id.editText_Valor_id);
        bntVoltar = (Button)findViewById(R.id.bntVoltar);
        btnAddData = (Button)findViewById(R.id.btnAddData);
        btnviewALL = (Button)findViewById(R.id.button_view);

        AddData();
        viewALL();
        VoltarHome();
        Data();
        getDate();

    }

    public  void viewALL(){
        btnviewALL.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount()==0){
                            // MENSAGEM
                            showMessage("Error","Nada encontrado");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            buffer.append("id : " + res.getString(0) + "\n");
                            buffer.append("Nome : " + res.getString(1) + "\n");
                            buffer.append("Horário : " + res.getString(2) + "\n");
                            buffer.append("Data : " + res.getString(5) + "\n");
                            buffer.append("Status : " + res.getString(3) + "\n");
                            buffer.append("Valor : R$ " + res.getString(4) + "\n");
                            buffer.append("Bônus : R$ " + res.getFloat(4) / 10 + "\n\n");
                        }
                        showMessage("Escala : " +getDate(), buffer.toString());
                    }
                }
        );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(
                                editNome.getText().toString(),
                                editHorario.getText().toString(),
                                editStatus.getText().toString(),
                                editValor.getText().toString(),
                                editData.getText().toString());

                        if(isInserted == true)
                            Toast.makeText(Adicionar.this,"Adicionado",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Adicionar.this,"Erro ao adicionar",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void VoltarHome(){
        bntVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adicionar.this,MainActivity.class));
            }
        });
    }

    // Helper Funtions

    public void Data(){

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.data_id);
        textViewDate.setText(currentDate);

    }

    public String getDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}