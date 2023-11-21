package com.example.collagereminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class NuevaNota extends AppCompatActivity {
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
        setContentView(R.layout.activity_note_add);


        try {
            //Variable for user
            final String user;
            //cargar boton para agregar notas
            final Button btnNota = findViewById(R.id.btnAddnote);

            //agregar la base de datos

            //Cargamos el text de tarea
            final EditText nota = findViewById(R.id.editTextTask);

            final EditText title = findViewById(R.id.editTextTitle);

            btnNota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    userName = sharedPreferences.getString("userName", "");
                    final String tituloNota = title.getText().toString();
                    final String nuevaNota = nota.getText().toString();

                    databaseReference.child("nuevasNotas").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            try {

                                DatabaseReference userNotesReference = databaseReference.child("nuevasNotas").child(userName);
                                String notaKey = userNotesReference.push().getKey();

                                if (notaKey != null) {

                                    if(!tituloNota.isEmpty() && !nuevaNota.isEmpty()){
                                        // Guarda la nueva nota con la clave generada en el nodo "notas"
                                        Map<String,String> data = new HashMap<>();
                                        data.put("key", notaKey);
                                        data.put("titulo", tituloNota);
                                        data.put("tarea", nuevaNota);

                                        userNotesReference.child(notaKey).setValue(data);
                                        mostrarMsgToast("Nota agregada");
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
            });


        }catch (Exception e){
            mostrarMsgToast("error en cargar componentes");
        }


    }
        private void mostrarMsgToast(String msg){
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        }

}




