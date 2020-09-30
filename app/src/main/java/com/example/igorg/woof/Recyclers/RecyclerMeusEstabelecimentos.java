package com.example.igorg.woof.Recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.DataEstabelecimento;
import com.example.igorg.woof.modelo.Estabelecimento;

import java.util.List;

public class RecyclerMeusEstabelecimentos extends RecyclerView.Adapter<RecyclerMeusEstabelecimentos.ViewHolder> {
    Context context;

    List<Estabelecimento> dataAdapters;

    ImageView btnButton1;

    public RecyclerMeusEstabelecimentos(List<Estabelecimento> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public RecyclerMeusEstabelecimentos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empresa_row, parent, false);

        RecyclerMeusEstabelecimentos.ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerMeusEstabelecimentos.ViewHolder viewHolder, int position) {


        Estabelecimento dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewID.setText(dataAdapter.getEstabelecimento_id());

        viewHolder.TextViewNome.setText(dataAdapter.getEstabelecimento_nome());


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

            TextViewNome = (TextView) itemView.findViewById(R.id.lista_empresa_nome);
            TextViewID = (TextView) itemView.findViewById(R.id.lista_empresa_id);



        }
    }
}
