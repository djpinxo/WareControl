package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;

import java.util.List;

public class AdapterContenedorList extends RecyclerView.Adapter<AdapterContenedorList.ViewHolder> {
    private List<Contenedor> contenedores;
    private Activity activity;

    public AdapterContenedorList(List<Contenedor> contenedores, Activity activity) {
        this.contenedores = contenedores;
        this.activity = activity;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Contenedor> contenedores) {
        this.contenedores = contenedores;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenedor_detail_list, parent, false);
        return new ViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contenedor contenedor = contenedores.get(position);
        holder.textViewId.setText(contenedor.getId().toString());
        holder.textViewNombre.setText(contenedor.getNombre().toString());

    }

    @Override
    public int getItemCount() {
        return contenedores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId;
        private TextView textViewNombre;
        private ImageButton buttonUpdate;
        private ImageButton buttonDelete;
        private Activity activity;
        private Contenedor contenedorModel;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            this.activity = activity;
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);


            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(new Contenedor(Long.valueOf(textViewId.getText().toString()), null, null, null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);

                }

            });

            textViewNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(new Contenedor(Long.valueOf(textViewId.getText().toString()), null, null, null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);

                }

            });
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateContenedorFragment updateContenedorFragment=new UpdateContenedorFragment(new Contenedor(Long.valueOf(textViewId.getText().toString()), null, null, null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateContenedorFragment);
                }

            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteContenedorFragment(new Contenedor(Long.valueOf(textViewId.getText().toString()), null, null, null, null, null)).showConfirmationDialog(activity);
                }

            });

        }
    }
}
