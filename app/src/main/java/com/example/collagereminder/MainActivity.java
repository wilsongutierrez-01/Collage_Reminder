package com.example.collagereminder;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.tasks.OnSuccessListener;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class MainActivity extends AppCompatActivity {

    ImageButton btnCalendario, NuevaNota, btnBienestar, CerrarSesion ;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView saludo, CorreoPrincipal,tokenTextView;


    DatabaseReference Usuarios;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saludo = findViewById(R.id.lblSaludo);
        //tokenTextView = findViewById(R.id.token);


        Usuarios = FirebaseDatabase.getInstance().getReference("users");
        CerrarSesion = findViewById(R.id.btnSalir);
        NuevaNota = findViewById(R.id.btnAddnote);
        btnCalendario = findViewById(R.id.btnCalendario);
        btnBienestar = findViewById(R.id.btnBienestar);
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

        btnBienestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirBienestar();

            }
        });


    }
    private void RegistrarDispositivo(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        // Actualiza el TextView con el token
                        tokenTextView.setText(token);
                        databaseReference.child("token").setValue(token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

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
    private void abrirBienestar(){
        Intent abrirCalendario = new Intent(getApplicationContext(), Estres.class);
        startActivity(abrirCalendario);

    }



}
