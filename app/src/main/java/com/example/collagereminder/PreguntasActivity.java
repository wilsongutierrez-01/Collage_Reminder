package com.example.collagereminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PreguntasActivity extends AppCompatActivity {
    EditText txtPregunta, txtRespuesta;
    Button btnGuardar;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.preguntas_activity);
        cargarElementos();

        btnGuardar.setOnClickListener(view -> {
            guardarFirebase();
        });

    }

    private void cargarElementos(){
        txtPregunta = findViewById(R.id.txtPregunta);
        txtRespuesta = findViewById(R.id.txtRespuesta);
        btnGuardar = findViewById(R.id.btnGuardarTarjeta);

    }
    private void guardarFirebase(){
        String pregunta = txtPregunta.getText().toString();
        String respuesta = txtRespuesta.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        try {
            DatabaseReference userPreguntasReference = databaseReference.child("estudios").child(userName);
            String preguntaKey = userPreguntasReference.push().getKey();
            if (preguntaKey != null){
                if (!pregunta.isEmpty() && !respuesta.isEmpty()){
                    Map<String,String> preguntas = new HashMap<>();
                    preguntas.put("key", preguntaKey);
                    preguntas.put("pregunta", pregunta);
                    preguntas.put("respuesta", respuesta);

                    userPreguntasReference.child(preguntaKey).setValue(preguntas);

                    finish();

                }else {
                    mostrarMsgToast("Por favor ingrese datos");
                }
                            }

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}
