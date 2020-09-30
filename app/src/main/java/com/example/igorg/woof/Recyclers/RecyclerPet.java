package com.example.igorg.woof.Recyclers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Pet;

public class RecyclerPet extends RecyclerView.Adapter<RecyclerPet.ViewHolder> {

    Context context;

    List<Pet> dataAdapters;

    ImageView petPhot;


    public RecyclerPet(List<Pet> getDataAdapter, Context context) {

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {


        final Pet dataAdapter = dataAdapters.get(position);


        viewHolder.TextViewNome.setText(dataAdapter.getPet_nome());

/*
            Glide.with(context)
                    .load(Pet.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.pets);

*/
    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNome;
        public TextView TextViewID;
        public ImageView pets;

        public ViewHolder(View itemView) {

            super(itemView);

            pets = itemView.findViewById(R.id.pet_photo);
            TextViewNome = (TextView) itemView.findViewById(R.id.lista_nome);
            TextViewID = (TextView) itemView.findViewById(R.id.lista_id);


        }
    }
}
