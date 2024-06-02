package net.ddns.djpinxo.warecontrol.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolder> {
    private List<User> users;

    public AdapterUserList(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewEmail.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEmail;
        public ImageButton buttonModificar;
        public ImageButton buttonBorrar;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonModificar = itemView.findViewById(R.id.buttonModificar);
            buttonBorrar = itemView.findViewById(R.id.buttonBorrar);

            textViewEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext().getApplicationContext(), "evento consulta pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                }

            });
            buttonModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext().getApplicationContext(), "evento modificar pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                }

            });
            buttonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext().getApplicationContext(), "evento borrar pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                }

            });

        }
    }
}
