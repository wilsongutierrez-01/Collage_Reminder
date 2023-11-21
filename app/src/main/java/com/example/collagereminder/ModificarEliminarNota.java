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
import android.widget.TextView;
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

public class ModificarEliminarNota extends AppCompatActivity {
    public String userName, userId;
    private ListView listViewTasks;
    private ArrayAdapter<Nota> adapter;
    private DatabaseReference userNotesReference;
    private List<Nota> listaDeNotas;

    private FirebaseAuth mAuth;
    Cursor datosProductosCursos = null;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");
    EditText tituloT, contenidoT;
    String keyNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_modificar_borrar);

        cargarDatosIntent();

        final Button btnNota = findViewById(R.id.btnModificar);
        final Button btnBorrar = findViewById(R.id.btnEliminar);

        btnNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modificarNota();

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elimimnarNota();
            }
        });

    }
        private void mostrarMsgToast(String msg){
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        }
        private void modificarNota(){
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            userName = sharedPreferences.getString("userName", "");
            final String tituloNota = tituloT.getText().toString();
            final String nuevaNota = contenidoT.getText().toString();

            databaseReference.child("nuevasNotas").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try {

                        DatabaseReference userNotesReference = databaseReference.child("nuevasNotas").child(userName);


                        if (keyNota != null) {

                            if(!tituloNota.isEmpty() && !nuevaNota.isEmpty()){
                                // Guarda la nueva nota con la clave generada en el nodo "notas"
                                Map<String,String> data = new HashMap<>();
                                data.put("key", keyNota);
                                data.put("titulo", tituloNota);
                                data.put("tarea", nuevaNota);

                                userNotesReference.child(keyNota).setValue(data);
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

        private void elimimnarNota (){
            databaseReference.child("nuevasNotas").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    userName = sharedPreferences.getString("userName", "");
                    DatabaseReference userNotesReference = databaseReference.child("nuevasNotas").child(userName);
                    userNotesReference.child(keyNota).removeValue();
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
                keyNota = intent.getStringExtra("id");
                String titulo = intent.getStringExtra("titulo");
                String contenido = intent.getStringExtra("contenido");

                // Ahora puedes usar estos valores como desees, por ejemplo, mostrarlos en TextViews

                tituloT = findViewById(R.id.editTextTitleM);
                contenidoT = findViewById(R.id.editTextTareaM);

                tituloT.setText(titulo);
                contenidoT.setText(contenido);
            }

        }

}




