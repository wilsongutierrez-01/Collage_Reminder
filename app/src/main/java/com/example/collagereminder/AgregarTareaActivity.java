package com.example.collagereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.onesignal.OneSignal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgregarTareaActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "b3837e85-2f5c-4930-9baa-6759b9367a0e";

    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;


    private EditText tareaEditText;
    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button guardarButton;


    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);
        // Inicializa OneSignal


        // Inicializa OneSignal (reemplaza 'YOUR_ONESIGNAL_APP_ID' con tu ID de aplicación real)
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);


        tareaEditText = findViewById(R.id.editTextTarea);
        calendarView = findViewById(R.id.calendarView);
        timePicker = findViewById(R.id.timePicker);
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
        try {
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    selectedHour = hourOfDay;
                    selectedMinute = minute;
                }
            });
        }catch (Exception e){
            mostrarMsgToast("Aqui esa el error principal"+e.getMessage());

        }

        guardarButton.setOnClickListener(v -> {
            String tarea = tareaEditText.getText().toString();

            if (tarea.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa una tarea", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    // Guardar la tarea en la fecha seleccionada (selectedYear, selectedMonth, selectedDay)

                    // Marcar el día seleccionado en el CalendarView
                    calendarView.setDate(getDateInMillisC(selectedYear, selectedMonth, selectedDay), true, true);
                    long dateTimeInMillis = getDateInMillis(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);
                    long timeStap = getTimestamp(selectedYear,selectedMonth,selectedDay,selectedHour,selectedMinute);
                    // Guardar la tarea en la fecha seleccionada en Firebase
                    guardarTareaEnFirebase(tarea);

                    try {
                        Map<String, String> data = new HashMap<>();
                        data.put("tarea", String.valueOf(timeStap));
                        OneSignal.getUser().addTags(data);
                        mostrarMsgToast(String.valueOf(timeStap));
                    }catch (Exception e){
                        mostrarMsgToast(e.getMessage());
                    }

                    // Opcionalmente, puedes regresar a la actividad anterior
                    finish();

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }

            }
        });
    }
    // Método para obtener la fecha en milisegundos a partir del año, mes y día
    private long getDateInMillis(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
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
    public long getTimestamp(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis() / 1000L; // Dividir por 1000 para obtener el timestamp en segundos
    }


    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
    // Método para obtener la fecha en milisegundos a partir del año, mes y día
    private long getDateInMillisC(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
}