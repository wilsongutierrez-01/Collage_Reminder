package com.example.collagereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TareaAdapter extends ArrayAdapter<Tarea> {
    private Context context;
    private List<Tarea> tareas;

    public TareaAdapter(Context context, List<Tarea> tareas) {
        super(context, 0, tareas);
        this.context = context;
        this.tareas = tareas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nivelView = convertView.findViewById(R.id.btnAlert);
            viewHolder.textViewContenido = convertView.findViewById(R.id.textViewContenido);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tarea tarea = getItem(position);



        if (tarea != null) {
            viewHolder.textViewContenido.setText(tarea.getContenido());
            if ("amarillo".equals(tarea.getNivel())){
                viewHolder.nivelView.setImageResource(R.drawable.amarillo);
            } else if ("verde".equals(tarea.getNivel())) {
                viewHolder.nivelView.setImageResource(R.drawable.verde);

            } else if ("rojo".equals(tarea.getNivel())) {
                viewHolder.nivelView.setImageResource(R.drawable.rojo);

            }else {
                viewHolder.nivelView.setImageResource(R.drawable.azul);
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewContenido;
        ImageView nivelView;
    }
}
