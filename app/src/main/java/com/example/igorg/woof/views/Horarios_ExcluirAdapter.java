package com.example.igorg.woof.views;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Horarios;
import com.example.igorg.woof.modelo.Servidor;

import static com.example.igorg.woof.modelo.Servidor.usuario_rede;
import static com.facebook.FacebookSdk.getApplicationContext;



public class Horarios_ExcluirAdapter extends ArrayAdapter<Horarios> {
    Context context;
    int layoutResourceId;
    ProgressDialog progressDialog ;
    ArrayList<Horarios> data = new ArrayList<Horarios>();


    public Horarios_ExcluirAdapter(Context context, int layoutResourceId,
                                   ArrayList<Horarios> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.textName = (TextView) row.findViewById(R.id.tipo);
            holder.textAddress = (TextView) row.findViewById(R.id.horario);
            holder.btnDelete = (Button) row.findViewById(R.id.excluir);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final Horarios horarios = data.get(position);
        holder.textName.setText(horarios.getTipo());
        holder.textAddress.setText(horarios.getHorario());


        holder.btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Toast.makeText(context, horarios.getID(), Toast.LENGTH_LONG).show();
                check(horarios.getID());
            }
        });
        return row;

    }

    static class UserHolder {
        TextView textName;
        TextView textAddress;
        TextView textLocation;
        Button btnEdit;
        Button btnDelete;
    }

    public void check(final String id_hora){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Deseja realmente excluir esse horário?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Excluir(id_hora);

            }
        });

        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                       }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void Excluir(final String id_hora) {

        progressDialog = ProgressDialog.show(context, "Excluindo horário", "Por favor aguarde", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ Servidor.getIp()+"/woof/excluir_horario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Response_pet", response);

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Horario excluido com sucesso! ", Toast.LENGTH_SHORT).show();

            }

        }, new Response.ErrorListener() {
            @Override            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("h",id_hora);


                return params;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

    }

}