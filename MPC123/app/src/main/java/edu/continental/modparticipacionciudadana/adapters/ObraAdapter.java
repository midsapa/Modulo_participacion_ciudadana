package edu.continental.modparticipacionciudadana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.continental.modparticipacionciudadana.R;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.ViewHolderDatos> {
    private ArrayList<String> datos1,datos2,datos3,datos4,datos5,datos6,datos7,datos8,datos9,datos10;
    Context context;

    public ObraAdapter(ArrayList<String> adatos1, ArrayList<String> adatos2, ArrayList<String> adatos3, ArrayList<String> adatos4, ArrayList<String> adatos5, ArrayList<String> adatos6, ArrayList<String> adatos7, ArrayList<String> adatos8, ArrayList<String> adatos9, ArrayList<String> adatos10, MyAdapterListener listener, MyAdapterListener2 listener2, Context context){
        this.datos1 = adatos1;
        this.datos2 = adatos2;
        this.datos3 = adatos3;
        this.datos4 = adatos4;
        this.datos5 = adatos5;
        this.datos6 = adatos6;
        this.datos7 = adatos7;
        this.datos8 = adatos8;
        this.datos9 = adatos9;
        this.datos10= adatos10;

        onClickListener = listener;
        onClickListener2 = listener2;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_obras,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObraAdapter.ViewHolderDatos holder, int position) {
        holder.ponerDatos(datos1.get(position),datos2.get(position),datos3.get(position),datos4.get(position),datos5.get(position),datos6.get(position),datos7.get(position),datos8.get(position),datos9.get(position),datos10.get(position));
    }

    @Override
    public int getItemCount() {
        return datos1.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView ivListObra;
        TextView txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10;
        Button btnlistarcomentarios, btnvermas;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            ivListObra = itemView.findViewById(R.id.ivListObra);
            txt2 = itemView.findViewById(R.id.txtListNombre);
            txt3=itemView.findViewById(R.id.txtListDescription);
            txt4=itemView.findViewById(R.id.txtListFechaInicio);
            txt5=itemView.findViewById(R.id.txtListFechaFin);
            txt6=itemView.findViewById(R.id.txtListMonto);
            txt7=itemView.findViewById(R.id.txtListCronograma);
            txt8 = itemView.findViewById(R.id.txtListTipo);
            txt10=itemView.findViewById(R.id.txtListPromedioObra2);

            btnlistarcomentarios = itemView.findViewById(R.id.btnListcomentarios);
            btnvermas = itemView.findViewById(R.id.btnListVerMas);

            btnlistarcomentarios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener2.listar(v,getAdapterPosition());
                }
            });
            btnvermas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.vermas(v,getAdapterPosition());
                }
            });
        }
        public void ponerDatos(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10){
            //txt1.setText(a1);
            txt2.setText(a2);
            txt3.setText(a3);

            String[] parts = a4.split(" ");
            String part1 = parts[0]; // 123
            String part2 = parts[1]; // 654321

            txt4.setText(part1);
            String[] part = a5.split(" ");
            String part13 = parts[0]; // 123
            String part5 = parts[1]; // 654321
            txt5.setText(part13);
            txt6.setText(a6);
            txt7.setText(a7);
            txt8.setText(a8);
            txt10.setText(a10);


            String imageUri = a9;
            Picasso.with(context).load(imageUri).into(ivListObra);
        }
    }
    public MyAdapterListener onClickListener;
    public MyAdapterListener2 onClickListener2;

    public interface MyAdapterListener {
        void vermas(View v, int position);
    }

    public interface MyAdapterListener2 {
        void listar(View v, int position);
    }
}

