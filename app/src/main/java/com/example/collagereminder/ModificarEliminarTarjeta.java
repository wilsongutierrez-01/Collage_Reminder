package com.example.collagereminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarEliminarTarjeta extends AppCompatActivity {
    public String userName, userId;
    private ListView listViewTasks;
    private ArrayAdapter<Nota> adapter;
    private DatabaseReference userNotesReference;
    private List<Nota> listaDeNotas;

    private FirebaseAuth mAuth;
    Cursor datosProductosCursos = null;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");
    EditText preguntaT, contenidoT;
    String keyPregunta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_modficar_borrar);

        cargarDatosIntent();

        final Button btnModficar = findViewById(R.id.btnModificarTarjeta);
        final Button btnBorrar = findViewById(R.id.btnEliminarTarjeta);

        btnModficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modificaTarjeta();

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elimimnarPregunta();

            }
        });

    }
        private void mostrarMsgToast(String msg){
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        }
        private void modificaTarjeta(){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            userName = sharedPreferences.getString("userName", "");
            final String pregunta = preguntaT.getText().toString();
            final String contenido = contenidoT.getText().toString();

            databaseReference.child("estudios").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try {

                        DatabaseReference userPreguntasReference = databaseReference.child("estudios").child(userName);



                        if (keyPregunta != null) {

                            if(!pregunta.isEmpty() && !contenido.isEmpty()){
                                // Guarda la nueva nota con la clave generada en el nodo "notas"
                                Map<String,String> data = new HashMap<>();
                                data.put("key", keyPregunta);
                                data.put("pregunta", pregunta);
                                data.put("respuesta", contenido);

                                userPreguntasReference.child(keyPregunta).setValue(data);
                                finish();
                            }else {
                                mostrarMsgToast("Por favor ingrese datos");
                            }

                        } else {
                            mostrarMsgToast("Error al agregar la nota");
                        }

                    }catch (Exception e){
                        mostrarMsgToast(e.getMessage());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private void elimimnarPregunta (){
            databaseReference.child("nuevasNotas").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    userName = sharedPreferences.getString("userName", "");
                    DatabaseReference userNotesReference = databaseReference.child("estudios").child(userName);
                    userNotesReference.child(keyPregunta).removeValue();
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        private void cargarDatosIntent (){
            // Recuperar los datos del Intent
            Intent intent = getIntent();
            if (intent != null) {
                keyPregunta = intent.getStringExtra("id");
                String titulo = intent.getStringExtra("pregunta");
                String contenido = intent.getStringExtra("contenido");

                // Ahora puedes usar estos valores como desees, por ejemplo, mostrarlos en TextViews

                preguntaT = findViewById(R.id.txtPreguntaModificar);
                contenidoT = findViewById(R.id.txtRespuestaModificar);

                preguntaT.setText(titulo);
                contenidoT.setText(contenido);
            }

        }



}




