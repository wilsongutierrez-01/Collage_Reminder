package com.example.collagereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button CerrarSesion, NuevaNota, btnCalendario;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView NombrePrincipal, CorreoPrincipal;


    DatabaseReference Usuarios;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NombrePrincipal = findViewById(R.id.NombrePrincipal);


        Usuarios = FirebaseDatabase.getInstance().getReference("users");
        CerrarSesion = findViewById(R.id.CerrarSesion);
        NuevaNota = findViewById(R.id.btnAddnote);
        btnCalendario = findViewById(R.id.btnCalendario);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalirAplicacion();
            }
        });

        NuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCalendario();

            }
        });


    }


    private void CargaDeDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Si el usuario existe
                if (snapshot.exists()){

                    // Los TexView se muestran
                    NombrePrincipal.setVisibility(View.VISIBLE);

                    // Obtener los datos
                    String nombres = "" + snapshot.child("name").getValue();

                    // Setear los datos en los respectivos TexView
                    NombrePrincipal.setText(nombres);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
        Toast.makeText(this, "Cerraste sesión exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void addNote(){
        Intent addNote = new Intent(getApplicationContext(), addNoteActivity.class);
        startActivity(addNote);

    }

    private void abrirCalendario(){
        Intent abrirCalendario = new Intent(getApplicationContext(), Calendario.class);
        startActivity(abrirCalendario);

    }
}
