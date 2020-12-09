package edu.continental.modparticipacionciudadana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import edu.continental.modparticipacionciudadana.adapters.Adapter_items;

public class Listar_comentario extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<String> dato1,dato2,dato3,dato4,dato5;
    String nombreUsuario,comentarioUsuario,FechaComentario,Puntuacion,id_obra,foto;
    //ImageView foto;
    Button comentar,Volver;
    RequestQueue queue2;
    TextView txtNombreObraComent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_comentario);

        //Toast.makeText(Listar_comentario.this, "LLega a listar comentario", Toast.LENGTH_SHORT).show();
        queue2 = Volley.newRequestQueue(this);
        comentar=findViewById(R.id.btnComentar2);
        Volver=findViewById(R.id.btnvolver);

        txtNombreObraComent=findViewById(R.id.txtNombreObraComent);
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

        String nombre_obra = preferences.getString("nombre_obra","");
        txtNombreObraComent.setText(nombre_obra);
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Listar_comentario.this,Mostrar_Obras.class);
                startActivity(i);
            }
        });

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Listar_comentario.this,Registro_Comentario.class);
                startActivity(i);
            }
        });
        recicler();
    }
    private void recicler(){

        recyclerView  = findViewById(R.id.re2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dato1 = new ArrayList<String>();
        dato2 = new ArrayList<String>();
        dato3 = new ArrayList<String>();
        dato4 = new ArrayList<String>();
        dato5 = new ArrayList<String>();

        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

        String obra_id_ver = preferences.getString("obra_id","");

        String url="http://smartcityhyo.tk/api/ComentarioObra/listar_comentario_por_id_obra.php?id="+obra_id_ver;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("comentarios");

                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject a = array.getJSONObject(i);
                        id_obra = a.getString("ID_Obra");
                        nombreUsuario = a.getString("US_Nombres")+" "+a.getString("US_Apellidos");
                        Puntuacion = a.getString("COM_Calificacion");
                        comentarioUsuario = a.getString("COM_Obra");
                        FechaComentario = a.getString("COM_Fecha");
                        foto = a.getString("COM_Foto");

                        dato1.add(nombreUsuario);
                        dato2.add(FechaComentario);
                        dato3.add(comentarioUsuario);
                        dato4.add(Puntuacion);
                        dato5.add(foto);

                    }
                    Adapter_items adapter_items = new Adapter_items(dato1, dato2, dato3, dato4, dato5,getApplicationContext());
                    recyclerView.setAdapter(adapter_items);
                } catch (JSONException e) {
                    Toast.makeText(Listar_comentario.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Listar_comentario.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);
    }
}