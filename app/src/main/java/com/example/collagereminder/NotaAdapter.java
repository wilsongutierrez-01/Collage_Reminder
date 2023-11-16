package com.example.collagereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.collagereminder.Nota;
import com.example.collagereminder.R;

import java.util.List;

public class NotaAdapter extends ArrayAdapter<Nota> {
    private Context context;
    private List<Nota> notas;

    public NotaAdapter(Context context, List<Nota> notas) {
        super(context, 0, notas);
        this.context = context;
        this.notas = notas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = convertView.findViewById(R.id.textViewTitle);
            viewHolder.textViewContent = convertView.findViewById(R.id.textViewContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Nota nota = getItem(position);
        Nota titulo = getItem(position);

        if (nota != null) {
            viewHolder.textViewTitle.setText(nota.getTitle());
            viewHolder.textViewContent.setText(nota.getContent());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;
    }
}
