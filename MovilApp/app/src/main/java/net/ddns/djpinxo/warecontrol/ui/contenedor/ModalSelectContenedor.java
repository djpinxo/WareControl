package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.ui.item.SelectItemFragment;

public class ModalSelectContenedor extends DialogFragment implements FragmentCallback<Contenedor> {
    private static Contenedor contenedorModel;
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedorPadre;

    public ModalSelectContenedor(){
        super();
    }

    public ModalSelectContenedor(Contenedor contenedorModel) {
        super();
        this.contenedorModel=contenedorModel;
    }

    public ModalSelectContenedor(Long idContenedor) {
        super();
        this.contenedorModel= new Contenedor(idContenedor, null, null, null, null, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_select_contenedor, container, false);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedorPadre = view.findViewById(R.id.editTextContenedorPadre);

        MainActivity.contenedorDao.getContenedor(this, contenedorModel.getId());

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> dismiss());

        Button buttonSelect = view.findViewById(R.id.buttonSelect);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(contenedorModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);
            }
        });

        return view;
    }


    public void callbackDataAcessSuccess(Contenedor contenedor){
        contenedorModel = contenedor;
        editTextId.setText(contenedorModel.getId().toString());
        editTextNombre.setText(contenedorModel.getNombre());
        editTextDescripcion.setText(contenedorModel.getDescripcion());
        if(contenedorModel.getContenedorPadre()!=null) {
            editTextContenedorPadre.setText(contenedorModel.getContenedorPadre().getId().toString());
        }

    }

    public void callbackDataAcessError(Contenedor contenedor){
        Toast.makeText(getActivity(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
