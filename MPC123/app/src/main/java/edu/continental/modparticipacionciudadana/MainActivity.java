package edu.continental.modparticipacionciudadana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import edu.continental.modparticipacionciudadana.clases.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtcorreo,txtcontraseña;
    Button btnentrar,btncrear;
    RequestQueue queue2;
    ArrayList<String> dato2,dato3,dato4,dato5;
    String id,nombre,correo,password;

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtcorreo=findViewById(R.id.edtcorreologin);
        txtcontraseña=findViewById(R.id.edtpasswordlogin);
        btncrear=findViewById(R.id.btncrearlogin);
        btnentrar=findViewById(R.id.btnentrarlogin);
        queue2 = Volley.newRequestQueue(this);

        btncrear.setOnClickListener(this);
        btnentrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnentrarlogin:
                if (txtcontraseña.getText().toString().length()<1 || txtcorreo.getText().toString().length()<1){
                    Toast.makeText(MainActivity.this, "Campo contraseña o correo vacio", Toast.LENGTH_SHORT).show();
                }else{
                    String gmail = txtcorreo.getText().toString();
                    //validarUsuario("http://192.168.1.89:80/participacionciudadana/login.php");
                    validarUsuario("http://smartcityhyo.tk/api/buscar_Email.php?US_Email="+gmail);

                }
                break;
            case R.id.btncrearlogin:
                Intent j= new Intent(this,Registro.class);
                startActivity(j);
                break;
        }
    }
    private void validarUsuario(String URL){

        dato2 = new ArrayList<String>();
        dato3 = new ArrayList<String>();
        dato4 = new ArrayList<String>();
        dato5 = new ArrayList<String>();

        //SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

        //String usr_name = preferences.getString("usr_nombre","");

        //String url = "https://smartcityhuancayo.herokuapp.com/buscar_Email.php?US_Email="+;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("usuario");

                    for (int i = 0 ; i < array.length() ; i++) {
                        JSONObject a = array.getJSONObject(i);
                        //ID = a.getString("id");
                        id = a.getString("ID_Usuario");
                        nombre = a.getString("US_Nombres");
                        correo = a.getString("US_Email");
                        password = a.getString("US_Contrasena");

                        String contra = txtcontraseña.getText().toString();
                        if(password.toString().equals(contra.toString())){
                            guardarPreferencias(id,nombre);
                            Toast.makeText(MainActivity.this,"Bienvenido: "+nombre,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Mostrar_Obras.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Credenciales inválidas"+contra+"-"+password, Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error de credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue2.add(request);

    }
    private void guardarPreferencias(String user_id, String usr_gmail){
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id",user_id);
        editor.putString("US_Email",usr_gmail);
        editor.commit();
        Toast.makeText(MainActivity.this,"Credenciales guardadas!",Toast.LENGTH_SHORT).show();
    }


}