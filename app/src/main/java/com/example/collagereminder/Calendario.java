package com.example.collagereminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Calendario extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;
    private int year, month, day;
    private ListView listView;
    private ArrayAdapter<Tarea> adapter;

    private List<Tarea> listaDeTareas;

     Button btnNuevatarea;


    private int selectedYear, selectedMonth, selectedDay;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        btnNuevatarea = findViewById(R.id.btnAddTarea);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");
        listView = findViewById(R.id.listViewTasks);
        listaDeTareas = new ArrayList<>();




        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        //inicializando adapter
        adapter = new TareaAdapter(this, listaDeTareas);
        // Asocia el adaptador con el ListView
        listView.setAdapter(adapter);

        btnNuevatarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCalendario();

            }
        });

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        try {
            if(year != 0 && month != 0 && dayOfMonth !=0){
                cargarTareasDesdeFirebase(year,month,dayOfMonth);

            }


        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[]{"Agregar tarea", "Ver evento", "Cancelar"};*/

        /*builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Agregar tarea - Puedes lanzar una actividad para agregar una tarea en esta fecha
                        Intent agregarTareaIntent = new Intent(Calendario.this, AgregarTareaActivity.class);
                        agregarTareaIntent.putExtra("year", year);
                        agregarTareaIntent.putExtra("month", month);
                        agregarTareaIntent.putExtra("day", day);
                        startActivity(agregarTareaIntent);
                        break;
                    case 1:
                        // Ver evento - Puedes mostrar los eventos para esta fecha en una actividad
                        Intent verEventosIntent = new Intent(Calendario.this, VerEventosActivity.class);
                        verEventosIntent.putExtra("year", year);
                        verEventosIntent.putExtra("month", month);
                        verEventosIntent.putExtra("day", day);
                        startActivity(verEventosIntent);
                        break;
                    case 2:
                        // Cancelar - No se hace nada
                        break;
                }
            }
        });

        builder.create().show();*/
    }


    private List<Tarea> obtenerTareasDesdeSnapshot(DataSnapshot dataSnapshot) {
        List<Tarea> tareas = new ArrayList<>();

        for (DataSnapshot tareaSnapshot : dataSnapshot.getChildren()) {
            String contenido = tareaSnapshot.child("contenido").getValue(String.class);
            Tarea tarea = new Tarea();
            tareas.add(tarea);
        }

        return tareas;
    }
    private void cargarTareasDesdeFirebase(int year, int month, int day) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String userName = sharedPreferences.getString("userName", "");
        String fechaSeleccionada = year + "-" + (month + 1) + "-" + day;
        String uid = userName;
        String ruta = "Tareas/" + uid + "/" + fechaSeleccionada;

        databaseReference.child(ruta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    listaDeTareas.clear(); // Limpia la lista antes de cargar las notas
                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot taskSnapshot : dateSnapshot.getChildren()){
                            String taskContent = taskSnapshot.getValue(String.class);

                            Tarea tarea = new Tarea();
                            tarea.setContenido(taskContent);
                            listaDeTareas.add(tarea);

                        }

                    }

                    if ( adapter != null){
                        adapter.notifyDataSetChanged();

                    }

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }

                /*adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
                // Crear un adaptador personalizado para el ListView
                TareaAdapter tareaAdapter = new TareaAdapter(getApplicationContext(), obtenerTareasDesdeSnapshot(dataSnapshot));

                // Asociar el adaptador con el ListView
                listView.setAdapter(tareaAdapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mostrarMsgToast("Error al cargar tareas desde Firebase");
            }
        });
    }

    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    private void abrirCalendario(){
        Intent abrirCalendario = new Intent(getApplicationContext(), AgregarTareaActivity.class);
        startActivity(abrirCalendario);

    }
}
