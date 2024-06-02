package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

public class ModalAdapterContenedorList extends RecyclerView.Adapter<ModalAdapterContenedorList.ViewHolder> implements FragmentCallback<List<Contenedor>> {
    private List<Contenedor> contenedores;
    private Activity activity;
    private DialogFragment dialog;

    public ModalAdapterContenedorList(List<Contenedor> contenedores, Activity activity, DialogFragment dialog) {
        this.contenedores = contenedores;
        this.activity = activity;
        this.dialog = dialog;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Contenedor> contenedores) {
        this.contenedores = contenedores;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_list_contenedor, parent, false);
        return new ViewHolder(view, activity, dialog);
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
        private Activity activity;
        private DialogFragment dialog;

        public ViewHolder(View itemView, Activity activity, DialogFragment dialog) {
            super(itemView);
            this.activity = activity;
            this.dialog = dialog;
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);


            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activity.findViewById(R.id.editTextContenedor)!=null){
                        ((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedor)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    }
                    if(activity.findViewById(R.id.editTextContenedorPadre)!=null){
                        ((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedorPadre)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    }
                    //((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedor)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    dialog.dismiss();

                }

            });

            textViewNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activity.findViewById(R.id.editTextContenedor)!=null){
                        ((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedor)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    }
                    if(activity.findViewById(R.id.editTextContenedorPadre)!=null){
                        ((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedorPadre)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    }
                    //((TextView)((MainActivity)activity).findViewById(R.id.editTextContenedor)).setText(Long.valueOf(textViewId.getText().toString()).toString());
                    dialog.dismiss();
                }

            });

        }
    }

    @Override
    public void callbackDataAcessSuccess(List<Contenedor> contenedores) {
        updateData(contenedores);
    }

    @Override
    public void callbackDataAcessError(List<Contenedor> contenedores) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
