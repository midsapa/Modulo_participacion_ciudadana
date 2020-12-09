package edu.continental.modparticipacionciudadana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.continental.modparticipacionciudadana.adapters.ObraAdapter;

public class Mostrar_Obras extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> dato1,dato2,dato3,dato4,dato5,dato6,dato7,dato8,dato9,dato10;
    String id,nombre,tipo_obra,descripcion,monto,fechainicio,fechafin,cronograma,foto,promedio;
    Button btnBuscar,btnmapaobra;
    EditText etBuscar;


    RequestQueue queue2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__obras);

        //ratingBar = findViewById(R.id.rating);
        btnBuscar= findViewById(R.id.btnBuscar);
        etBuscar= findViewById(R.id.etBuscar);
        btnmapaobra=findViewById(R.id.button3mapa);
        queue2 = Volley.newRequestQueue(this);

        btnmapaobra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j= new Intent(getApplicationContext(),Mapa_Obras.class);
                startActivity(j);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recicler();
            }
        });

    }

    private void recicler()
    {
        recyclerView  = findViewById(R.id.re);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dato1 = new ArrayList<String>();
        dato2 = new ArrayList<String>();
        dato3 = new ArrayList<String>();
        dato4 = new ArrayList<String>();
        dato5 = new ArrayList<String>();
        dato6 = new ArrayList<String>();
        dato7 = new ArrayList<String>();
        dato8 = new ArrayList<String>();
        dato9 = new ArrayList<String>();
        dato10= new ArrayList<String>();



        String url = "http://smartcityhyo.tk/api/Obra/Consult_obra_nombre.php?nombre="+etBuscar.getText().toString();
       // Toast.makeText(Mostrar_Obras.this, "entro hasta aqui", Toast.LENGTH_SHORT).show();

       final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("nickname",response.toString());

                try {
                    JSONArray array = response.getJSONArray("records");
                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject jresponse = array.getJSONObject(i);
                        //JSONObject jresponse = array.getJSONObject(i);
                        id = jresponse.getString("ID_Obra");
                        nombre = jresponse.getString("OBR_Nombre");
                        tipo_obra = jresponse.getString("Tipo_Obra");
                        descripcion = jresponse.getString("OBR_Descripcion");
                        monto = jresponse.getString("OBR_Monto");
                        fechainicio = jresponse.getString("OBR_Fecha_Inicio");
                        fechafin = jresponse.getString("OBR_Fecha_Fin");
                        cronograma = jresponse.getString("OBR_Dias_Calendarios");
                        foto = jresponse.getString("OBR_Foto");
                        promedio=jresponse.getString("calificacion");



                        dato1.add(id);
                        dato2.add(nombre);
                        dato3.add(descripcion);
                        dato4.add(fechainicio);
                        dato5.add(fechafin);
                        dato6.add(monto);
                        dato7.add(cronograma);
                        dato8.add(tipo_obra);
                        dato9.add(foto);
                        dato10.add(promedio);

                    }

                    ObraAdapter adapter_items = new ObraAdapter(dato1, dato2, dato3, dato4,dato5,dato6,dato7,dato8,dato9,dato10,new ObraAdapter.MyAdapterListener() {
                        @Override
                        public void vermas(View v, int position) {
                            String id = dato1.get(position);
                            String nombre = dato2.get(position);
                            guardarPreferencias(id,nombre);
                            Intent intent = new Intent(getApplicationContext(),Registro_Comentario.class);
                            startActivity(intent);

                        }
                    }, new ObraAdapter.MyAdapterListener2() {
                        @Override
                        public void listar(View v, int position) {
                            String id = dato1.get(position);
                            String nombre = dato2.get(position);

                            guardarPreferencias(id,nombre);

                            Intent intent = new Intent(getApplicationContext(),Listar_comentario.class);
                            startActivity(intent);


                        }
                    }, getApplicationContext());
                    recyclerView.setAdapter(adapter_items);
                } catch (JSONException e) {
                    Toast.makeText(Mostrar_Obras.this, "Error"+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mostrar_Obras.this, "No hay coincidencias con el nombre de la obra", Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);

    }


    private void guardarPreferencias(String obra_id,String nombre_obra){
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("obra_id",obra_id);
        editor.putString("nombre_obra",nombre_obra);
        editor.commit();
        Toast.makeText(Mostrar_Obras.this,"Enviar comentario!",Toast.LENGTH_SHORT).show();
    }
}