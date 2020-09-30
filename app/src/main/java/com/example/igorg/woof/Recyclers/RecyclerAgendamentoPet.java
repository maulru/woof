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
import com.example.igorg.woof.modelo.Agendamento;

import java.util.List;

public class RecyclerAgendamentoPet extends RecyclerView.Adapter<RecyclerAgendamentoPet.ViewHolder> {

    Context context;

    List<Agendamento> dataAdapters;

    ImageView btnButton1;

    public RecyclerAgendamentoPet(List<Agendamento> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public RecyclerAgendamentoPet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pet_agendamento, parent, false);

        RecyclerAgendamentoPet.ViewHolder viewHolder = new RecyclerAgendamentoPet.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAgendamentoPet.ViewHolder viewHolder, int position) {


        Agendamento dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNome.setText(dataAdapter.getNome_estabelecimento());
        viewHolder.TextViewHorario.setText(dataAdapter.getHorario());
        viewHolder.TextViewEndereco.setText(dataAdapter.getEndereco());
        viewHolder.TextViewStatus.setText(dataAdapter.getStatus());


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNome;
        public TextView TextViewHorario;
        public TextView TextViewEndereco;
        public TextView TextViewStatus;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNome = (TextView) itemView.findViewById(R.id.txt_nome_estabelecimento);
            TextViewHorario = (TextView) itemView.findViewById(R.id.txt_horario_estabelecimento);
            TextViewEndereco = (TextView) itemView.findViewById(R.id.txt_endereco_estabelecimento);
            TextViewStatus = (TextView) itemView.findViewById(R.id.txt_status_estabelecimento);



        }
    }
}
