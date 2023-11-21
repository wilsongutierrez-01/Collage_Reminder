package com.example.collagereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addNoteActivity extends AppCompatActivity {
    public String userName, userId;
    private ListView listViewTasks;
    private ArrayAdapter<Nota> adapter;
    private DatabaseReference userNotesReference;
    private List<Nota> listaDeNotas;

    private FirebaseAuth mAuth;
    Cursor datosProductosCursos = null;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ///Para base de datos
        try {
            cargarDatos();
        }catch (Exception e){
            mostrarMsgToast(e.getMessage()   );
        }
        listViewTasks = findViewById(R.id.listViewTasks);
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener el objeto Nota correspondiente a la posición i
                Nota notaSeleccionada = listaDeNotas.get(i);

                // Aquí puedes acceder a los datos de la Nota seleccionada
                String id = notaSeleccionada.getId();
                String titulo = notaSeleccionada.getTitle();
                String contenido = notaSeleccionada.getContent();

                // Puedes hacer lo que desees con estos datos, por ejemplo, pasarlos a otra actividad
                // o mostrarlos en un Toast
                //Toast.makeText(getApplicationContext(), "ID: " + id + "\nTítulo: " + titulo + "\nContenido: " + contenido, Toast.LENGTH_SHORT).show();
               Intent modificar = new Intent(getApplicationContext(), ModificarEliminarNota.class);
                modificar.putExtra("id",id);
                modificar.putExtra("titulo",titulo);
                modificar.putExtra("contenido", contenido);
                startActivity(modificar);
            }
        });
        try {
            //Variable for user
            final String user;
            //cargar boton para agregar notas
            final Button btnNota = findViewById(R.id.btnAddnote);


            btnNota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  Intent add = new Intent(getApplicationContext(),NuevaNota.class);
                  startActivity(add);



                }
            });


        }catch (Exception e){
            mostrarMsgToast("error en cargar componentes");
        }


    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    //Para cargar los datos desde firebase

    private void cargarDatos (){
        //userId = mAuth.getUid();

        // Inicializa la lista de notas
        listaDeNotas = new ArrayList<>();

        // Obtiene una referencia al ListView
        listViewTasks = findViewById(R.id.listViewTasks);

        // Obtiene una referencia a la base de datos de Firebase

        // Obtiene el nombre de usuario
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        // Obtiene una referencia a la ubicación de las notas del usuario en Firebase
        userNotesReference = databaseReference.child("nuevasNotas").child(userName);

        try {
            // Agrega un escuchador de eventos para cargar las notas desde Firebase
            userNotesReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaDeNotas.clear(); // Limpia la lista antes de cargar las notas
                    for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                        Map<String, String> notaData = (Map<String, String>) noteSnapshot.getValue();



                        if (notaData != null) {
                            String key = notaData.get("key");
                            String titulo = notaData.get("titulo");
                            String notaContenido = notaData.get("tarea");

                            Nota nota = new Nota();
                            nota.setId(key);
                            nota.setTitle(titulo);
                            nota.setContent(notaContenido);
                            listaDeNotas.add(nota);
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
            adapter = new NotaAdapter(this, listaDeNotas);

            // Asocia el adaptador con el ListView
            listViewTasks.setAdapter(adapter);

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
    }




}