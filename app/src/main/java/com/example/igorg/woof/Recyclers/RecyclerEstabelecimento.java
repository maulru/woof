package com.example.igorg.woof.Recyclers;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.DataEstabelecimento;

public class RecyclerEstabelecimento extends RecyclerView.Adapter<RecyclerEstabelecimento.ViewHolder> {

    Context context;

    List<DataEstabelecimento> dataAdapters;

    ImageView btnButton1;

    public RecyclerEstabelecimento(List<DataEstabelecimento> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        DataEstabelecimento dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewID.setText(dataAdapter.getName());

        viewHolder.TextViewNome.setText(String.valueOf(dataAdapter.getId()));

        viewHolder.TextViewTelefone.setText(dataAdapter.getTelefone());

        viewHolder.TextViewEndereco.setText(dataAdapter.getEndereco());


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNome;
        public TextView TextViewID;
        public TextView TextViewTelefone;
        public TextView TextViewEndereco;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNome = (TextView) itemView.findViewById(R.id.txt_nome_estabelecimento);
            TextViewID = (TextView) itemView.findViewById(R.id.textView4);
            TextViewTelefone = (TextView) itemView.findViewById(R.id.textView6);
            TextViewEndereco = (TextView) itemView.findViewById(R.id.textView8);


        }
    }
}
