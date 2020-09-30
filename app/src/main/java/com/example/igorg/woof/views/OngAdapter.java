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
import com.example.igorg.woof.modelo.ONG;

import java.util.ArrayList;
import java.util.List;



public class OngAdapter extends RecyclerView.Adapter<OngAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<ONG> contactList;
    private List<ONG> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, telefone,endereco,id,instagram;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.nome);
            id = view.findViewById(R.id.idt);
            telefone = view.findViewById(R.id.telefone);
            endereco = view.findViewById(R.id.endereco);
            instagram = view.findViewById(R.id.insta);
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

    public OngAdapter(Context context, List<ONG> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ong_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ONG contact = contactListFiltered.get(position);
        holder.nome.setText(contact.getNome());
        holder.id.setText(contact.getId());
        holder.telefone.setText(contact.getTelefone());
        holder.endereco.setText(contact.getEndereco());
        holder.instagram.setText(contact.getInstagram());



        Glide.with(context)
                .load(contact.getImagem())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);


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
                    List<ONG> filteredList = new ArrayList<>();
                    for (ONG row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNome().toLowerCase().contains(charString.toLowerCase()) || row.getTelefone().contains(charSequence) || row.getEndereco().toLowerCase().contains(charString.toLowerCase())) {
                            //if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
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
                contactListFiltered = (ArrayList<ONG>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(ONG contact);
    }
}
