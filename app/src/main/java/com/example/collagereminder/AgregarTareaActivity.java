package com.example.collagereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AgregarTareaActivity extends AppCompatActivity {

    private EditText tareaEditText;
    private CalendarView calendarView;
    private Button guardarButton;

    private int selectedYear, selectedMonth, selectedDay;

    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        tareaEditText = findViewById(R.id.editTextTarea);
        calendarView = findViewById(R.id.calendarView);
        guardarButton = findViewById(R.id.buttonGuardar);

        // Inicializa la referencia a la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;
            }
        });
        guardarButton.setOnClickListener(v -> {
            String tarea = tareaEditText.getText().toString();

            if (tarea.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa una tarea", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    // Guardar la tarea en la fecha seleccionada (selectedYear, selectedMonth, selectedDay)
                    // Puedes usar un servicio o base de datos para guardar la tarea

                    // Marcar el día seleccionado en el CalendarView
                    calendarView.setDate(getDateInMillis(selectedYear, selectedMonth, selectedDay), true, true);

                    // Guardar la tarea en la fecha seleccionada en Firebase
                    guardarTareaEnFirebase(tarea);

                    // Opcionalmente, puedes regresar a la actividad anterior
                    finish();

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }

            }
        });
    }
    // Método para obtener la fecha en milisegundos a partir del año, mes y día
    private long getDateInMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
    // Método para guardar la tarea en Firebase Realtime Database
    private void guardarTareaEnFirebase1(String tarea) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String userName = sharedPreferences.getString("userName", "");
        // Construye la ruta en la base de datos de Firebase
        String fechaSeleccionada = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
        String uid = userName;
        String ruta = "tareas/" + uid + "/" + fechaSeleccionada;

        // Guarda la tarea
        databaseReference.child(ruta).push().setValue(tarea);
    }

    // Método para guardar la tarea en Firebase
    private void guardarTareaEnFirebase(String tarea) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String userName = sharedPreferences.getString("userName", "");
        String fechaSeleccionada = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
        String uid = userName;
        String ruta = "Tareas/" + uid + "/" + fechaSeleccionada;

        // Guardar la tarea en Firebase
        DatabaseReference nuevaTareaRef = databaseReference.child(ruta).push();
        nuevaTareaRef.child("contenido").setValue(tarea);
    }

    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}