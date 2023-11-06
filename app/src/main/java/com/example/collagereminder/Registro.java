package com.example.collagereminder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.StartupTime;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.spec.ECField;

public class Registro extends AppCompatActivity {
    //***********************************************************************
    private EditText txtName, txtEmail, txtPassword, txtconPassword;
    private Button btnRegister;
    private TextView loginNowBtn;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    //************************************************************************
   /* FirebaseAuth userAuth;
    DatabaseReference userDataBase;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");

    private EditText userEmail;
    private EditText userPassword;
    private String userEmailString = " ";
    private String userPasswordString = " ";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //***********************************************************************************

        mAuth = FirebaseAuth.getInstance();

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtconPassword = findViewById(R.id.txtconPassword);
        btnRegister = findViewById(R.id.btnRegister);
        loginNowBtn = findViewById(R.id.loginNowBtn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();


            }
        });


        //***************************************************************************************
        /*final Button btnRegister = findViewById(R.id.btnRegister);



        //Connecting firebase Auth

        btnRegister.setOnClickListener(view -> {
           registrar();
        });*/


    }
    private void registrarUsuario() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String psswd = txtPassword.getText().toString();
                                final String emailp = txtEmail.getText().toString();
                                final String name = txtName.getText().toString();
                                final String emailPath = emailp.replace(".", "_");

                                try{
                                    databaseReference.child("users").child(emailPath).child("email").setValue(emailp);
                                    databaseReference.child("users").child(emailPath).child("name").setValue(name);
                                    databaseReference.child("users").child(emailPath).child("password").setValue(psswd);

                                    Toast.makeText(Registro.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                                    finish();
                                }catch (Exception e){
                                    msgToast(e.getMessage());
                                }
                                Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                // Implementa la lógica para redirigir al usuario a la actividad principal
                            } else {
                                Toast.makeText(Registro.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }catch (Exception e){
            msgToast(e.getMessage());
        }

    }






    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}
/*
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

    //Creamos al usuario y lo registramos


    // generar el mensaje Toast
    private void msgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    } */

