package edu.continental.modparticipacionciudadana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mapa_Obras extends AppCompatActivity implements OnMapReadyCallback {

    LatLng pos1 = null;
    LatLng pos2 = null;
    private int contmarker=0;
    private String id;
    private String nombre;
    private String lat_s;
    private String long_s;
    public GoogleMap mMap;
    RequestQueue queue2;
    public LatLng plm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa__obras);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        queue2 = Volley.newRequestQueue(this);
    }
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        String url = "http://smartcityhyo.tk/api/Obra/Listar_Obra.php";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("records");

                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject a = array.getJSONObject(i);
                        //ID = a.getString("id");
                        lat_s = a.getString("OBR_Coordenada_X");
                        long_s = a.getString("OBR_Coordenada_Y");
                        nombre = a.getString("OBR_Nombre");
                        id = a.getString("ID_Obra");

                        String lat_replace = lat_s.replaceAll(" ","");
                        String long_replace = long_s.replaceAll(" ","");

                        double lat_v = Double.parseDouble(lat_replace);
                        double long_v = Double.parseDouble(long_replace);

                        //lat_v = lat_v*-10.0456;
                        //long_v = long_v*-10.0456;

                        plm = new LatLng( lat_v,long_v);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.addMarker(new MarkerOptions().position(plm).title(id).snippet(nombre));
                        CameraPosition cameraPosition = CameraPosition.builder().target(plm).zoom(17).build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Toast.makeText(Mapa_Obras.this, "Prueba"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                                guardarPreferencias(marker.getTitle().toString(), marker.getSnippet().toString());
                                Intent i = new Intent(getApplicationContext(), Listar_comentario.class);
                                startActivity(i);
                                return false;
                            }
                        });

                    }

                } catch (JSONException e) {
                    Toast.makeText(Mapa_Obras.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mapa_Obras.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(Mapa_Obras.this,"Ver comentarios de la obra!",Toast.LENGTH_SHORT).show();
    }

}