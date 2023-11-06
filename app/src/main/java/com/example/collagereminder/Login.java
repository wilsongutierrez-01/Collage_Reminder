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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //obtenemos el usuario desde Firebase
        mAuth = FirebaseAuth.getInstance();



        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtuserName = findViewById(R.id.txtNombre);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();





               /* mailUser = email.getText().toString();
                final String emailPath = mailUser.replace(".", "_");
                final String userName = user.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (mailUser.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Por favor ingresa tus datos", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Verificar si el usuario existe en la base de datos
                            if (snapshot.hasChild(emailPath)){

                                // Usuario existe
                                // Obtnemos la contraseña del usuario de la base de datos y la combinamos con la contraseña ingresada por el usuario
                                final String getPassword = snapshot.child(emailPath).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userMail", mailUser);
                                    editor.putString("userName", userName);
                                    editor.apply();

                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Usuario no existente", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }*/
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