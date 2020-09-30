package com.example.igorg.woof.views;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Estabelecimento;
import com.example.igorg.woof.modelo.Usuarios;

import java.util.ArrayList;
import java.util.List;



public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Usuarios> contactList;
    private List<Usuarios> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, email,telefone,id;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.nome_usuario);
            id = view.findViewById(R.id.idt);
            email = view.findViewById(R.id.email_usuario);
            telefone = view.findViewById(R.id.telefone_usuario);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public UsuarioAdapter(Context context, List<Usuarios> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuarios_row_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Usuarios contact = contactListFiltered.get(position);
        holder.nome.setText(contact.getNome());
        holder.id.setText(contact.getID_Usuario());
        holder.email.setText(contact.getEmail());
/*
        Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);

*/
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Usuarios> filteredList = new ArrayList<>();
                    for (Usuarios row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getNome().toLowerCase().equals(charString.toLowerCase())) || (row.getEmail().equals(charSequence)) ) {
                            //if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }else{
                            filteredList.clear();
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Usuarios>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Usuarios contact);
    }
}
