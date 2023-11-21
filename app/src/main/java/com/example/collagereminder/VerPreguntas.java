package com.example.collagereminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerPreguntas extends AppCompatActivity {
    Button btnTarjeta;

    private ListView listViewPreguntas;
    private ArrayAdapter<Pregunta> adapter;
    private DatabaseReference userPreguntasReference;
    private List<Pregunta> listaDePreguntas;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_estudio);

        cargarElementos();
        cargarDatos();

        listViewPreguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener el objeto Nota correspondiente a la posición i
                Pregunta tarjetaSeleccionada = listaDePreguntas.get(i);

                // Aquí puedes acceder a los datos de la Nota seleccionada
                String id = tarjetaSeleccionada.getId();
                String pregunta = tarjetaSeleccionada.getPregunta();
                String respuesta = tarjetaSeleccionada.getRespuesta();

                // Puedes hacer lo que desees con estos datos, por ejemplo, pasarlos a otra actividad
                // o mostrarlos en un Toast
                //Toast.makeText(getApplicationContext(), "ID: " + id + "\nTítulo: " + titulo + "\nContenido: " + contenido, Toast.LENGTH_SHORT).show();
                Intent modificar = new Intent(getApplicationContext(), ModificarEliminarTarjeta.class);
                modificar.putExtra("id",id);
                modificar.putExtra("pregunta",pregunta);
                modificar.putExtra("contenido", respuesta);
                startActivity(modificar);
            }
        });
        btnTarjeta.setOnClickListener(view -> {
            Intent agregar = new Intent(getApplicationContext(), PreguntasActivity.class);
            startActivity(agregar);
        });

    }
    private void cargarElementos(){
        btnTarjeta = findViewById(R.id.btnAddTarjeta);
    }

    private void cargarDatos (){
        //userId = mAuth.getUid();

        // Inicializa la lista de notas
        listaDePreguntas = new ArrayList<>();

        // Obtiene una referencia al ListView
        listViewPreguntas = findViewById(R.id.listViewPreguntas);

        // Obtiene una referencia a la base de datos de Firebase

        // Obtiene el nombre de usuario
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        // Obtiene una referencia a la ubicación de las notas del usuario en Firebase
        userPreguntasReference = databaseReference.child("estudios").child(userName);

        try {
            // Agrega un escuchador de eventos para cargar las notas desde Firebase
            userPreguntasReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaDePreguntas.clear(); // Limpia la lista antes de cargar las notas
                    for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                        Map<String, String> preguntaData = (Map<String, String>) noteSnapshot.getValue();



                        if (preguntaData != null) {
                            String id = preguntaData.get("key");
                            String preguntas = preguntaData.get("pregunta");
                            String respuestas = preguntaData.get("respuesta");

                            Pregunta pregunta = new Pregunta();
                            pregunta.setId(id);
                            pregunta.setPregunta(preguntas);
                            pregunta.setRespuesta(respuestas);
                            listaDePreguntas.add(pregunta);

                        }

                    }
                    adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mostrarMsgToast("Error al cargar notas desde Firebase");
                }
            });


        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

        try{
            // Inicializa el adaptador personalizado
            adapter = new PreguntaAdapter(this, listaDePreguntas);

            // Asocia el adaptador con el ListView
            listViewPreguntas.setAdapter(adapter);

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}
