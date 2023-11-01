package com.example.collagereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Registro extends AppCompatActivity {
    FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    private EditText userEmail;
    private EditText userPassword;
    private String userEmailString = " ";
    private String userPasswordString = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        final Button btnRegister = findViewById(R.id.btnRegister);



        //Connecting firebase Auth
        /*Botones y acciones*/
        btnRegister.setOnClickListener(view -> {
           registrar();
        });/*Creamos el registro del usuario y logueamos*/


    }

    //Messages Toast
    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
    //////////////////////////////////////////////////


    private void registrar (){
        final EditText name = findViewById(R.id.txtName);
        final EditText email = findViewById(R.id.txtEmail);
        final EditText password = findViewById(R.id.txtPassword);
        final EditText conPassword = findViewById(R.id.txtconPassword);

        final Button btnRegister = findViewById(R.id.btnRegister);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nameTxt = name.getText().toString();
                final String emailTxt = email.getText().toString();
                final String emailPath = emailTxt.replace(".", "_");
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                if (nameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Registro.this, "Por favor ingresa tus datos", Toast.LENGTH_SHORT).show();
                }

                else if (!passwordTxt.equals((conPasswordTxt))){
                    Toast.makeText(Registro.this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(emailPath)){
                                Toast.makeText(Registro.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                try{
                                    databaseReference.child("users").child(emailPath).child("email").setValue(emailTxt);
                                    databaseReference.child("users").child(emailPath).child("name").setValue(nameTxt);
                                    databaseReference.child("users").child(emailPath).child("password").setValue(passwordTxt);

                                    Toast.makeText(Registro.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                                    finish();
                                }catch (Exception e){
                                    mostrarMsgToast(e.getMessage());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*Creamos al usuario y lo registramos*/


    /* generar el mensaje Toast*/
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

}