package com.example.igorg.woof;



        import android.content.Context;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.example.igorg.peteazyv20.R;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.model.Marker;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class infoview extends AppCompatActivity implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private String ID,NOME,LATITUDE,LONGITUDE;
    private String url;
    public static final String TITLE = "Nome";
    String[] nome;
    String j;

    public infoview() {
    }

    private String url_mapa = "http://192.168.0.32/peteasy/markers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getMarkers();

    }



    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.activity_infoview, null);
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome[i] = jsonObject.getString("Nome");


                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(infoview.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }



    public infoview(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_infoview, null);


        TextView tvNome = (TextView) v.findViewById(R.id.nome);
        TextView tvEndereco = (TextView) v.findViewById(R.id.endereco);
        TextView tvTelefone = (TextView) v.findViewById(R.id.telefone);
        TextView tvProduto = (TextView) v.findViewById(R.id.produto);
        TextView tvServicos = (TextView) v.findViewById(R.id.servicos);




        tvEndereco.setText("" +nome);



        return v;


    }




}

