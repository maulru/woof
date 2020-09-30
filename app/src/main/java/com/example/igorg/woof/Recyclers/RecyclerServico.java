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
import com.example.igorg.woof.modelo.Servicos;

import java.util.List;

public class RecyclerServico extends RecyclerView.Adapter<RecyclerServico.ViewHolder> {

    Context context;

    List<Servicos> dataAdapters;

    ImageView btnButton1;

    public RecyclerServico(List<Servicos> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public RecyclerServico.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_row, parent, false);

        RecyclerServico.ViewHolder viewHolder = new RecyclerServico.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        Servicos dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNome.setText(dataAdapter.getNome());

        viewHolder.TextViewID.setText(String.valueOf(dataAdapter.getId()));

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNome;
        public TextView TextViewID;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNome = (TextView) itemView.findViewById(R.id.lista_nome);
            TextViewID = (TextView) itemView.findViewById(R.id.lista_id);


        }
    }


}
