package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ModalViewContenedor extends DialogFragment implements FragmentCallback<List<Contenedor>> {


    private RecyclerView recyclerViewModal;
    private ModalAdapterContenedorList adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_list, container, false);
        recyclerViewModal = view.findViewById(R.id.recyclerViewModal);
        recyclerViewModal.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ModalAdapterContenedorList(new ArrayList<Contenedor>(), getActivity(), this);

        recyclerViewModal.setAdapter(adapter);

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }

        });

        MainActivity.contenedorDao.getContenedores(this);

        return view;
    }


    @Override
    public void callbackDataAcessSuccess(List<Contenedor> contenedores) {
        adapter.updateData(contenedores);
    }

    @Override
    public void callbackDataAcessError(List<Contenedor> contenedores) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }

}