package com.example.collagereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class addNoteActivity extends AppCompatActivity {
    public String userName;
    private ListView listViewTasks;
    private ArrayAdapter<Nota> adapter;
    private DatabaseReference userNotesReference;
    private List<Nota> listaDeNotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Inicializa la lista de notas
        listaDeNotas = new ArrayList<>();

        // Obtiene una referencia al ListView
        listViewTasks = findViewById(R.id.listViewTasks);

        // Obtiene una referencia a la base de datos de Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

        // Obtiene el nombre de usuario
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        // Obtiene una referencia a la ubicación de las notas del usuario en Firebase
        userNotesReference = databaseReference.child("nuevasNotas").child(userName).child("Notas");

        try {
            // Agrega un escuchador de eventos para cargar las notas desde Firebase
            userNotesReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaDeNotas.clear(); // Limpia la lista antes de cargar las notas
                    for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                            String noteText = noteSnapshot.getValue(String.class);

                            Nota nota = new Nota();
                            nota.setContent(noteText);
                            listaDeNotas.add(nota);



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
            mostrarMsgToast("Aca si entra");
            adapter = new NotaAdapter(this, listaDeNotas);

            // Asocia el adaptador con el ListView
            listViewTasks.setAdapter(adapter);

        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }





        //Variable for user
        final String user;
        //cargar boton para agregar notas
        final Button btnNota = findViewById(R.id.btnAddnote);

        //agregar la base de datos

        //Cargamos el text de tarea
        final EditText nota = findViewById(R.id.editTextTask);


        btnNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                userName = sharedPreferences.getString("userName", "");
                final String nuevaNota = nota.getText().toString();

                    databaseReference.child("nuevasNotas").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            try {

                                DatabaseReference userNotesReference = databaseReference.child("nuevasNotas").child(userName).child("Notas");
                                String notaKey = userNotesReference.push().getKey();

                                if (notaKey != null) {
                                    // Guarda la nueva nota con la clave generada en el nodo "notas"
                                    userNotesReference.child(notaKey).setValue(nuevaNota);
                                    mostrarMsgToast("Nota agregada");
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
        });
    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }





}