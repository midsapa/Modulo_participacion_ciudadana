package edu.continental.modparticipacionciudadana;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText edtnombre,edtapellidos,edtdirecion,edtfechanaci,edtnacionalidad,edttelefono,edtemail,edtpassword;
    Button btnregidtrar,brnfechareg;
    Calendar c=Calendar.getInstance();
    int yy=c.get(Calendar.YEAR);
    int mm=c.get(Calendar.MONTH);
    int dd=c.get(Calendar.DAY_OF_MONTH);
    int hor=c.get(Calendar.HOUR_OF_DAY);
    int min=c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtnombre=findViewById(R.id.edtnombreregidtro);
        edtapellidos=findViewById(R.id.edtapellidoregis);
        edtdirecion=findViewById(R.id.edtdireccionreg);
        edtfechanaci=findViewById(R.id.edtfechanaciminetoreg);
        edtnacionalidad=findViewById(R.id.edtnacionalidadreg);
        edttelefono=findViewById(R.id.edttelefonoreg);
        edtemail=findViewById(R.id.edtcorreoregitro);
        edtpassword=findViewById(R.id.edtpasswordregidtro);
        btnregidtrar=findViewById(R.id.btnregitro);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        brnfechareg=findViewById(R.id.btnregistrofecha);
        brnfechareg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenerfecha();
            }
        });
        btnregidtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validarEmail(edtemail.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Correo inválido.",Toast.LENGTH_SHORT).show();
                }else{
                    //ejecutar("http://smartcityhuancayo.herokuapp.com/Usuario/Insert_Usuario.php");
                    Toast.makeText(getApplicationContext(),"Verificando...",Toast.LENGTH_SHORT).show();
                    enviarDatos();
                    Toast.makeText(getApplicationContext(),"Registro exitoso!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registro.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void tenerfecha(){
        DatePickerDialog obtenerfecha=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mesdehoy=month +1;
                String dianuevo=(dayOfMonth <10)?"0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesnuevo=(mesdehoy <10)?"0" + String.valueOf(mesdehoy):String.valueOf(mesdehoy);
                edtfechanaci.setText(year + "-" + mesnuevo + "-" + dianuevo);
            }

        },yy,mm,dd);
        obtenerfecha.show();
    }
    /*private void ejecutar(String URL){
        StringRequest StringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    if (response.length()==2)
                    {
                        Toast.makeText(getApplicationContext(),"Usuario registrado!",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }

                    else {
                        Toast.makeText(getApplicationContext(),"error!:"+ response.toString(),Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Error al registrar!",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("US_Nombres",edtnombre.getText().toString());
                parametros.put("US_Apellidos",edtapellidos.getText().toString());
                parametros.put("US_Direcccion",edtdirecion.getText().toString());
                parametros.put("US_Fecha_Nacimiento",edtfechanaci.getText().toString());
                parametros.put("US_Nacionalidad",edtnacionalidad.getText().toString());
                parametros.put("US_Telefono",edttelefono.getText().toString());
                parametros.put("US_Email",edtemail.getText().toString());
                parametros.put("US_Contrasena",edtpassword.getText().toString());
                parametros.put("US_Tipo","Cliente");
                return parametros;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }*/

    public void enviarDatos() {
        String url ="http://smartcityhyo.tk/api/Usuario/Insert_Usuario.php";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            final org.json.JSONObject jsonBody = new org.json.JSONObject();

            jsonBody.put("US_Nombres",edtnombre.getText().toString());
            jsonBody.put("US_Apellidos",edtapellidos.getText().toString());
            jsonBody.put("US_Direccion",edtdirecion.getText().toString());
            jsonBody.put("US_Fecha_Nacimiento",edtfechanaci.getText().toString());
            jsonBody.put("US_Nacionalidad",edtnacionalidad.getText().toString());
            jsonBody.put("US_Telefono",edttelefono.getText().toString());
            jsonBody.put("US_Email",edtemail.getText().toString());
            jsonBody.put("US_Contrasena",edtpassword.getText().toString());
            jsonBody.put("US_Tipo","Cliente");

            final String requestBody = jsonBody.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ver 1","satisfactorio:"+response);
                    Log.d("ver 2","satisfactorio:"+jsonBody.toString());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    Log.d("ver error","fallo update"+error);


                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}