package com.example.collagereminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Random;

public class Estres extends AppCompatActivity {

    //Agregar todos los consejos deseados
    String[] consejos = {
            //Agregar todos los consejos que quieran
            "Respira profundamente y relájate.",
            "Dedica tiempo para ti mismo todos los días.",
            "Encuentra una actividad que te guste y que te relaje.",
            "Mantén una actitud positiva.",
            "Evita el estrés innecesario."
    };
    //Agregar todas las fraseds deseadas
    String[] frasesInspiradoras = {
            "La vida es corta, sonríe a menudo.",
            "Cree en ti mismo y todo será posible.",
            "El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
            "La paciencia es amarga, pero su fruto es dulce.",
            "El optimismo es la fe que conduce al logro. Nada se puede hacer sin esperanza y confianza.",

    };

    //Agregar todos los enlaces deseados
    //En el metodo de video se agregaran los titulos de videos
    String[] enlacesVideosMeditacion = {
            "https://www.youtube.com/watch?v=Tg6CYjujgKg",
            "https://www.youtube.com/watch?v=6p_yzMzutKk",
            "https://www.youtube.com/watch?v=e9tqC4VCKIY",

    };



    //Creamos los botones
    Button buttonConsejos, buttonFrases, buttonVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estres);

        //Enlazamos nuestros botones
       buttonConsejos = findViewById(R.id.buttonConsejos);
       buttonFrases = findViewById(R.id.buttonFrases);
       buttonVideos = findViewById(R.id.buttonEnlacesavideos);

       //Asociamos nuestro metodo al evento click de nuestro boton
        buttonConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomConsejo();
            }
        });
        buttonFrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomFrases();
            }
        });
        buttonVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videos();
            }
        });
    }

    //Metodos par nuestros mensajes aleatorios
    private void randomConsejo (){
        // Genera un número aleatorio para seleccionar un consejo al azar
        Random random = new Random();
        int consejoIndex = random.nextInt(consejos.length);
        // Muestra el consejo seleccionado en un cuadro de diálogo
        String consejo = consejos[consejoIndex];
        crearDialogo("Recuerda:",consejo);
    }

    private void randomFrases (){
        Random random = new Random();
        int fraseIndex = random.nextInt(frasesInspiradoras.length);
        String frase = frasesInspiradoras[fraseIndex];
        crearDialogo("Tomalo con calma", frase);

    }

    //Metodo para crear nuestro cuadro de dialgo
    private void crearDialogo(String title, String contenido){
        AlertDialog.Builder builder = new AlertDialog.Builder(Estres.this);
        builder.setTitle(title);
        builder.setMessage(contenido);
        builder.setPositiveButton("Aceptar",null);
        builder.show();

    }

    //Metodo para mostrar los videos
    private void videos(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Estres.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_videos, null);
        builder.setView(dialogView);
        ListView listViewVideos = dialogView.findViewById(R.id.listViewVideos);

        // Colocar los titulos de los videos aca
        String[] titulosVideosMeditacion = {
                "Colocar titulo de video",
                "Colocar titulo de video",
                "Colocar titulo de video",

        };

        // Crea un ArrayAdapter para mostrar los títulos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Estres.this, android.R.layout.simple_list_item_1, titulosVideosMeditacion);
        listViewVideos.setAdapter(adapter);

        final AlertDialog dialog = builder.create();

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Cuando se selecciona un título, puedes abrir el enlace correspondiente
                String enlaceSeleccionado = enlacesVideosMeditacion[position];
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlaceSeleccionado));
                startActivity(intent);
                dialog.dismiss(); // Cierra el cuadro de diálogo
            }
        });

        dialog.show();
    }
}