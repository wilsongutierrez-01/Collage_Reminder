package com.example.collagereminder;

import static java.util.logging.Logger.global;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    public String mailUser;
    private EditText txtEmail, txtPassword, txtuserName;
    private Button btnLogin;
    private TextView registerNowBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference, userReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //obtenemos el usuario desde Firebase
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");


        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtuserName = findViewById(R.id.txtNombre);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });


    }


    private void login (){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String userName = txtuserName.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarMsgToast("Por favor completa los campos");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Para guardar el nombre de usuario y usarlo en los nodos de la base de datos
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userMail", email);
                            editor.putString("userName", userName);
                            editor.apply();
                            mostrarMsgToast("Inicio de sesión exitoso");
                            // Implementa la lógica para redirigir al usuario a la actividad principal
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();

                        } else {
                            mostrarMsgToast("Error en el inicio de sesión");
                        }
                    }
                });

    }
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

}