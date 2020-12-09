package edu.continental.modparticipacionciudadana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

public class Registro_Comentario extends AppCompatActivity {

    EditText comentario;
    Button enviar;
    RatingBar ratingBar;
    float valor=0;
    ImageView imagen;
    Button adjuntar;
    Bitmap bitmap;
    String KEY_IMAGE="foto";
    int PICK_IMAGE_REQUEST = 1;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__comentario);

        comentario=findViewById(R.id.txtcomentario);
        adjuntar = findViewById(R.id.btnadjuntar);

        adjuntar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

                 if(ActivityCompat.checkSelfPermission(Registro_Comentario.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                     showFileChooser();
                 }
                 else{
                    ActivityCompat.requestPermissions(Registro_Comentario.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                 }

                }
                else {
                    showFileChooser();
                }
            }
        });
        imagen = findViewById(R.id.imgadjunto);
        enviar=findViewById(R.id.btnenviardecomentario);


        ratingBar=findViewById(R.id.rtPuntajeComent);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String comentario_validar = comentario.getText().toString();
                float rating = valor;
                if(comentario_validar.isEmpty()){
                    comentario.setError("Ingrese el comentario!!");
                } else if(rating<0 || rating>5){
                    Toast.makeText(Registro_Comentario.this, "Debe marcar su calificación", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage("http://smartcityhyo.tk/api/ComentarioObra/insertar_comentario.php");
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(Registro_Comentario.this, "Valor:"+rating, Toast.LENGTH_SHORT).show();
                valor= rating;
            }
        });

    }

    public void uploadImage(String UPLOAD_URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registro_Comentario.this, "Comentario enviado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registro_Comentario.this,Listar_comentario.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro_Comentario.this, "Seleccione la foto a enviar!!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = getStringImagen(bitmap);

                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

                String obra_id_ver = preferences.getString("obra_id","");
                String user_id_ver = preferences.getString("user_id","");

                Map<String, String> params = new Hashtable<String, String>();
                params.put("ID_Obra", obra_id_ver);
                params.put("COM_Obra", comentario.getText().toString());
                params.put("COM_Calificacion", String.valueOf(valor));
                params.put("ID_Usuario", user_id_ver);
                params.put("COM_Foto", imagen);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CAMERA){
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showFileChooser();
            }
            else{
                Toast.makeText(this,"Necesitas habilitar los permisos",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}