package com.example.collagereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class AgregarTareaActivity extends AppCompatActivity {
    public static final String MY_CHANNEL_ID = "myChannel";
    public static final int NOTIFICATION_ID = 1;

    public static final String NOTIFICATION_IDS = "";
    private static final String ONESIGNAL_APP_ID = "b3837e85-2f5c-4930-9baa-6759b9367a0e";

    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;
    private Button pickTimeBtn, pickDateBtn;
    private TextView selectedTimeTV, selectedDateTv;
    private EditText tareaEditText ,nivelEditTxt;
    private Spinner nivelColor;
    private CalendarView calendarView;
    private Button guardarButton;

    private long timestamp, currentTimesTamp;


    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;
    // En lugar de esta URL, utiliza la dirección de tu servidor

    private int NOTIFICATION_IDR;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        NOTIFICATION_IDR = generateRandomIntId();

        Button myNotificationButton = findViewById(R.id.btnNotification);
        createChannel();
        myNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification();
                mostrarMsgToast(String.valueOf(timestamp - Calendar.getInstance().getTimeInMillis()));
            }
        });
        // Inicializa OneSignal
        Calendar time = Calendar.getInstance();
        currentTimesTamp = time.getTimeInMillis()/1000;

        // Inicializa OneSignal (reemplaza 'YOUR_ONESIGNAL_APP_ID' con tu ID de aplicación real)
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        selectedTimeTV = findViewById(R.id.idTVSelectedTime);
        selectedDateTv = findViewById(R.id.idTvSelectedDate);
        pickTimeBtn = findViewById(R.id.idBtnPickTime);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        tareaEditText = findViewById(R.id.editTextTarea);
        nivelColor = findViewById(R.id.spinnerColors);
        guardarButton = findViewById(R.id.buttonGuardar);

        // Inicializa la referencia a la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://collage-reminder-32e34-default-rtdb.firebaseio.com/");



        try {
            pickDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarTareaActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    Calendar calendar = Calendar.getInstance();
                                    selectedYear = year;
                                    selectedMonth = month;
                                    selectedDay = day;
                                    calendar.set(Calendar.YEAR, year);
                                    calendar.set(Calendar.MONTH, month);
                                    calendar.set(Calendar.DAY_OF_MONTH, day);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
                                    String formattedDate = dateFormat.format(calendar.getTime());
                                    selectedDateTv.setText(formattedDate);
                                }
                            },year,month,day);
                    datePickerDialog.show();
                }
            });
        }catch (Exception e){
            mostrarMsgToast(e.getMessage());
        }
        try {
            pickTimeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // on below line we are getting the
                    // instance of our calendar.
                    final Calendar c = Calendar.getInstance();

                    // on below line we are getting our hour, minute.
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    // on below line we are initializing our Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AgregarTareaActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // on below line we are setting selected time
                                    // in our text view.
                                    selectedTimeTV.setText(hourOfDay + ":" + minute);
                                    selectedHour = hourOfDay;
                                    selectedMinute = minute;
                                    if (selectedYear !=0 && selectedMonth !=0 && selectedDay !=0){
                                        c.set(Calendar.DAY_OF_YEAR,selectedYear);
                                        c.set(Calendar.MONTH,selectedMonth);
                                        c.set(Calendar.DAY_OF_MONTH,selectedDay);
                                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                        c.set(Calendar.MINUTE, minute);

                                        timestamp = c.getTimeInMillis();

                                    }else{
                                        mostrarMsgToast("Seleccione Fecha");
                                    }

                                }
                            }, hour, minute, false);
                    // at last we are calling show to
                    // display our time picker dialog.
                    timePickerDialog.show();
                }
            });
        }catch (Exception e){
            mostrarMsgToast("Aqui esa el error principal"+e.getMessage());

        }

        guardarButton.setOnClickListener(v -> {
            String tarea = tareaEditText.getText().toString();
            int selectedIndex = nivelColor.getSelectedItemPosition();
            String nivel = (String) nivelColor.getItemAtPosition(selectedIndex);

            if (tarea.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa una tarea", Toast.LENGTH_SHORT).show();
            } else {

                try {


                        Map<String, String> tareas = new HashMap<>();
                        tareas.put("tarea", tarea);
                        tareas.put("nivel", nivel);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        final String userName = sharedPreferences.getString("userName", "");
                        String fechaSeleccionada = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        String uid = userName;
                        String ruta = "Tareas/" + uid + "/" + fechaSeleccionada;

                        // Guardar la tarea en Firebase
                        DatabaseReference nuevaTareaRef = databaseReference.child(ruta).push();

                        nuevaTareaRef.setValue(tareas);
                        scheduleNotification();

                        finish();
                    }catch (Exception e){
                        mostrarMsgToast(e.getMessage());
                    }


                    /*try {

                        // Guardar la tarea en la fecha seleccionada (selectedYear, selectedMonth, selectedDay)

                        // Marcar el día seleccionado en el CalendarView
                        calendarView.setDate(getDateInMillisC(selectedYear, selectedMonth, selectedDay), true, true);
                        long dateTimeInMillis = getDateInMillis(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);
                        // long timeStap = getTimestamp(selectedYear,selectedMonth,selectedDay,selectedHour,selectedMinute);
                        // Guardar la tarea en la fecha seleccionada en Firebase

                        try {
                            long xd = timestamp - currentTimesTamp;
                            Map<String, String> data = new HashMap<>();
                            data.put("tarea", "ok");
                            OneSignal.getUser().addTags(data);
                            mostrarMsgToast(String.valueOf(xd));
                    }catch (Exception e){
                        mostrarMsgToast(e.getMessage());
                    }

                    // Opcionalmente, puedes regresar a la actividad anterior
                    finish();

                }catch (Exception e){
                    mostrarMsgToast(e.getMessage());
                }*/

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
    private void guardarTareaEnFirebase(HashMap<String, String> tarea) {
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

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification() {
        Intent intent = new Intent(getApplicationContext(), AlarmNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                NOTIFICATION_IDR,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,  Calendar.getInstance().getTimeInMillis() + (timestamp - Calendar.getInstance().getTimeInMillis()) , pendingIntent);
        }
    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    MY_CHANNEL_ID,
                    "MySuperChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("COLLEGEREMINDER");

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static int generateRandomIntId() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }

}