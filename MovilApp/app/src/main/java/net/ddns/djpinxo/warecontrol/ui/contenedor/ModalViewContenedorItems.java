package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ModalViewContenedorItems extends DialogFragment implements FragmentCallback<List<Item>> {

    private static Contenedor contenedorModel;
    private RecyclerView recyclerViewModal;
    private ModalAdapterContenedorItemsList adapter;

    public ModalViewContenedorItems(Contenedor contenedorModel) {
        super();
        this.contenedorModel=contenedorModel;
    }

    public ModalViewContenedorItems(Long idContenedor) {
        super();
        this.contenedorModel= new Contenedor(idContenedor, null, null, null, null, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_list, container, false);
        recyclerViewModal = view.findViewById(R.id.recyclerViewModal);
        recyclerViewModal.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ModalAdapterContenedorItemsList(new ArrayList<Item>(), getActivity(), this);

        recyclerViewModal.setAdapter(adapter);

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }

        });

        MainActivity.contenedorDao.getContenedorItems(this, contenedorModel.getId());

        return view;
    }


    @Override
    public void callbackDataAcessSuccess(List<Item> items) {
        adapter.updateData(items);
    }

    @Override
    public void callbackDataAcessError(List<Item> items) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }

}