package com.example.collagereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PreguntaAdapter extends ArrayAdapter<Pregunta> {
    private Context context;
    private List<Pregunta> preguntas;

    public PreguntaAdapter(Context context, List<Pregunta> preguntas) {
        super(context, 0, preguntas);
        this.context = context;
        this.preguntas = preguntas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotaAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_preguntas, parent, false);
            viewHolder = new NotaAdapter.ViewHolder();
            viewHolder.textViewTitle = convertView.findViewById(R.id.txtPreguntaView);
            viewHolder.textViewContent = convertView.findViewById(R.id.txtRespuestaView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NotaAdapter.ViewHolder) convertView.getTag();
        }

        Pregunta pregunta = getItem(position);

        if (pregunta != null) {
            viewHolder.textViewTitle.setText(pregunta.getPregunta());
            viewHolder.textViewContent.setText(pregunta.getRespuesta());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;
    }

}
