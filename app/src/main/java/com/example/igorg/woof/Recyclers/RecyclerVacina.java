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
import com.example.igorg.woof.modelo.Vacina;

import java.util.List;

public class RecyclerVacina extends RecyclerView.Adapter<RecyclerVacina.ViewHolder> {

        Context context;

        List<Vacina> dataAdapters;

        ImageView btnButton1;

public RecyclerVacina(List<Vacina> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
        }

@SuppressLint("WrongViewCast")
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vacina, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
        }

@Override
public void onBindViewHolder(ViewHolder viewHolder, int position) {


        Vacina dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNome.setText(dataAdapter.getTipo());
        viewHolder.TextViewQuantidade.setText(dataAdapter.getQuantidade());
        viewHolder.TextViewData.setText(dataAdapter.getData_Vacinacao());


        }

@Override
public int getItemCount() {

        return dataAdapters.size();
        }

static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView TextViewNome;
    public TextView TextViewQuantidade;
    public TextView TextViewData;


    public ViewHolder(View itemView) {

        super(itemView);

        TextViewNome = (TextView) itemView.findViewById(R.id.txt_nome_estabelecimento);
        TextViewQuantidade = (TextView) itemView.findViewById(R.id.textView4);
        TextViewData = (TextView) itemView.findViewById(R.id.textView6);


    }
}
}
