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

public class Editar extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editNome, editHorario, editStatus, editValor, editData, edit_ID_id;
    Button bntVoltar, btnviewALL, btnviewUpdate, btnviewUpdateValue, btnviewUpdateHorario, btnviewUpdateStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_editar);

        myDb = new DataBaseHelper(this);

        edit_ID_id = (EditText) findViewById(R.id.editText_ID_id);
        editNome = (EditText) findViewById(R.id.editText_nome_id);
        editHorario = (EditText) findViewById(R.id.editText_horario_id);
        editData = (EditText) findViewById(R.id.data_id);
        editStatus = (EditText) findViewById(R.id.editText_Status_id);
        editValor = (EditText) findViewById(R.id.editText_Valor_id);


        btnviewALL = (Button) findViewById(R.id.button_view);
        btnviewUpdate = (Button) findViewById(R.id.button_edit);
        btnviewUpdateValue = (Button) findViewById(R.id.button_edit_value);
        btnviewUpdateHorario = (Button) findViewById(R.id.button_edit_horario);
        btnviewUpdateStatus = (Button) findViewById(R.id.button_edit_status);
        bntVoltar = (Button) findViewById(R.id.bntVoltar);

        VoltarHome();
        UpdateData();
        UpdateDataValue();
        UpdateDataStatus();
        UpdateDataHorario();
        viewALL();
        getDate();
    }

    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.UpdateData(edit_ID_id.getText().toString(),
                                editNome.getText().toString(), editHorario.getText().toString(),
                                editStatus.getText().toString(), editValor.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(Editar.this, "Editado", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Editar.this, "Erro ao Editar", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void UpdateDataValue() {
        btnviewUpdateValue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdateValue = myDb.UpdateDataValue(edit_ID_id.getText().toString(), editValor.getText().toString());
                        if (isUpdateValue == true)
                            Toast.makeText(Editar.this, "Valor editado", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Editar.this, "Erro ao Editar", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void UpdateDataStatus() {
        btnviewUpdateStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdateStatus = myDb.UpdateDataStatus(edit_ID_id.getText().toString(), editStatus.getText().toString());
                        if (isUpdateStatus == true)
                            Toast.makeText(Editar.this, "Status editado", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Editar.this, "Erro ao Editar", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void UpdateDataHorario() {
        btnviewUpdateHorario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdateHorario = myDb.UpdateDataHorario(edit_ID_id.getText().toString(), editHorario.getText().toString());
                        if (isUpdateHorario == true)
                            Toast.makeText(Editar.this, "Horário editado", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Editar.this, "Erro ao Editar", Toast.LENGTH_LONG).show();

                    }
                }
        );
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

    public void VoltarHome() {
        bntVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Editar.this, MainActivity.class));
            }
        });
    }

    //Helper functions

    public String getDate() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.data_id);
        textViewDate.setText(currentDate);

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


