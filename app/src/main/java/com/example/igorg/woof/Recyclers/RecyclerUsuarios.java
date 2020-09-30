package com.example.igorg.woof.Recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Usuarios;

import java.util.List;

public class RecyclerUsuarios extends RecyclerView.Adapter<RecyclerUsuarios.ViewHolder> {

    Context context;

    List<Usuarios> dataAdapters;

    ImageView btnButton1;

    public RecyclerUsuarios(List<Usuarios> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_row_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        Usuarios dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNome.setText(dataAdapter.getNome());
        viewHolder.TextViewEmail.setText(dataAdapter.getEmail());
        viewHolder.TextViewTelefone.setText(dataAdapter.getTelefone());
        viewHolder.TextViewEmail.setText(dataAdapter.getEmail());



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNome;
        public TextView TextViewID;
        public TextView TextViewTelefone;
        public TextView TextViewEmail;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNome = (TextView) itemView.findViewById(R.id.nome_usuario);
            TextViewID = (TextView) itemView.findViewById(R.id.idt);
            TextViewTelefone = (TextView) itemView.findViewById(R.id.telefone_usuario);
            TextViewEmail = (TextView) itemView.findViewById(R.id.email_usuario);


        }
    }
}
