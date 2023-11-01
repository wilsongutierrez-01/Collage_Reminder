package com.example.collagereminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collagereminder.AgregarTareaActivity;
import com.example.collagereminder.R;
import com.example.collagereminder.VerEventosActivity;

public class Calendario extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[]{"Agregar tarea", "Ver evento", "Cancelar"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
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

        builder.create().show();
    }
}
