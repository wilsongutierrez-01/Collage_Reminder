package com.example.collagereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class Calendario extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendarView;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView=(CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0]="Agregar tarea";
        items[1]="Ver evento";
        items[2]="Cancelar";

        //final int dia, mes, anio;
        //dia = i;
        //mes = i1+1;
        //anio = i2;




        AlertDialog dialog = builder.create();
        dialog.show();

    }
}