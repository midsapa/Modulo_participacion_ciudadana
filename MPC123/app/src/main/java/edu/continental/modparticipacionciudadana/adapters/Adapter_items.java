package edu.continental.modparticipacionciudadana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.continental.modparticipacionciudadana.R;
import edu.continental.modparticipacionciudadana.Registro_Comentario;

public class Adapter_items extends RecyclerView.Adapter<Adapter_items.ViewHolderDatos>{
    private ArrayList<String> datos1,datos2,datos3,datos4,datos5;
    Context context;

    public Adapter_items(ArrayList<String> adatos1, ArrayList<String> adatos2, ArrayList<String> adatos3, ArrayList<String> adatos4, ArrayList<String> adatos5, Context context){
        this.datos1 = adatos1;
        this.datos2 = adatos2;
        this.datos3 = adatos3;
        this.datos4 = adatos4;
        this.datos5 = adatos5;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listacomentarios,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_items.ViewHolderDatos holder, int position) {
        holder.ponerDatos(datos1.get(position),datos2.get(position),datos3.get(position),datos4.get(position),datos5.get(position));
       // holder.ratingBar.setRating(Float.parseFloat(datos4.get(position)));
    }

    @Override
    public int getItemCount() {
        return datos1.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView ivListObracoment;
        TextView txt1,txt2,txt3,txt4,txt5;
        RatingBar ratingBar;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            ivListObracoment = itemView.findViewById(R.id.ivListObraComent);
            txt1 = itemView.findViewById(R.id.txtListNombreUsuarioComent);
            txt2=itemView.findViewById(R.id.txtListfechacoment);
            txt3=itemView.findViewById(R.id.txtListcomentarioUsuarioComent);
            txt4=itemView.findViewById(R.id.txtListPuntajeComent);
           // ratingBar=itemView.findViewById(R.id.txtListPuntajeComent);

        }
        public void ponerDatos(String a1, String a2, String a3, String a4, String a5){
            txt1.setText(a1);
            txt2.setText(a2);
            txt3.setText(a3);
            txt4.setText(a4);

           //ratingBar.setRating();
            /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Toast.makeText(context, "Valor:"+rating, Toast.LENGTH_SHORT).show();
                }
            });*/

            if(a5.toString().equals("")){
                String imageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ5aP696Mr-1GGDI1LjKGzcYdeHQUyNz4AGjw&usqp=CAU";
                Picasso.with(context).load(imageUri).into(ivListObracoment);
            }
               else
            {
                String imageUri =a5;
                Picasso.with(context).load(imageUri).into(ivListObracoment);

            }

        }
    }

}
