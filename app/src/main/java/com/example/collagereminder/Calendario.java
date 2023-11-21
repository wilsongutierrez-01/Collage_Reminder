package com.example.collagereminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Calendario extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;
    private int year, month, day;
    private ListView listView;
    private ArrayAdapter<Tarea> adapter;

    private List<Tarea> listaDeTareas;

    Button btnNuevatarea;


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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        cargarTareasDesdeFirebase(y,m,d);
    }

    public void cargarTareasDesdeFirebase(int year, int month, int day) {
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

                            Map<String, String> taskData = (Map<String, String>)dateSnapshot.getValue();
                            //String taskContent = taskSnapshot.getValue(String.class);

                            try {
                                if (taskData != null) {
                                    String hora = taskData.get("hora");
                                    String nivel = taskData.get("nivel");
                                    String tarea = taskData.get("tarea");

                                    Tarea tareas = new Tarea();
                                    tareas.setHora(hora);
                                    tareas.setContenido(tarea);
                                    tareas.setNivel(nivel);
                                    listaDeTareas.add(tareas);

                                }
                            }catch (Exception e){
                                mostrarMsgToast(e.getMessage());
                            }


                    }

                    if ( adapter != null){
                        adapter.notifyDataSetChanged();

                    }

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }

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
