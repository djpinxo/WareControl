package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;

public class UpdateContenedorFragment extends Fragment implements FragmentCallback<Contenedor> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedorPadre;
    private Button buttonUpdate;
    private Button buttonCancel;
    private static Contenedor contenedorModel;
    private boolean isUpdating=false;

    public UpdateContenedorFragment(){
        super();
    }
    public UpdateContenedorFragment (Contenedor contenedorModel){
        this();
        this.contenedorModel=contenedorModel;
        isUpdating=false;
        MainActivity.contenedorDao.getContenedor(this, contenedorModel.getId());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_contenedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.contenedor_update_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedorPadre = view.findViewById(R.id.editTextContenedorPadre);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateContenedor();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(contenedorModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);
            }

        });



        ImageButton buttonInfo = view.findViewById(R.id.buttonInfo);
        ImageButton buttonSearch = view.findViewById(R.id.buttonSearch);
        ImageButton buttonScan = view.findViewById(R.id.buttonScan);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextContenedorPadre.getText().toString().trim());
                    showSelectModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.container)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListModal();
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    showScanModal();
                } else {
                    requestCameraPermission();
                }
            }
        });

    }

    //crear modal de informacion de contenedor
    private void showSelectModal() {
        ModalSelectContenedor modalSelectContenedor = new ModalSelectContenedor(new Contenedor(Long.valueOf(editTextContenedorPadre.getText().toString().trim()),null,null,null,null,null));
        modalSelectContenedor.show(getParentFragmentManager(), "ContenedorSelect");
    }
    //finish crear modal

    //crear modal de seleccion de contenedor
    private void showListModal() {
        ModalViewContenedor modalViewContenedor = new ModalViewContenedor();
        modalViewContenedor.show(getParentFragmentManager(), "ContenedorView");
    }
    //finish crear modal

    //crear modal de scan qa de contenedor
    private void showScanModal() {
        ModalScanContenedor modalScanContenedor = new ModalScanContenedor();
        modalScanContenedor.show(getParentFragmentManager(), "ContenedorScan");
    }
    private boolean checkCameraPermission() {
        return getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
    }
    //finish crear modal

    private void updateContenedor(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdPadre = editTextContenedorPadre.getText().toString().trim();

        if(validateContenedorForm()){
            Contenedor contenedorPadre = (sIdPadre.isEmpty())?null:new Contenedor(Long.valueOf(sIdPadre), null, null, null, null, null);
            //contenedorModel=new Contenedor(0l,name,descripcion, contenedorPadre, null, null);
            contenedorModel.setNombre(name);
            contenedorModel.setDescripcion(descripcion);
            contenedorModel.setContenedorPadre(contenedorPadre);
            isUpdating=true;
            MainActivity.contenedorDao.updateContenedor(this, contenedorModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateContenedorForm() {
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdPadre = editTextContenedorPadre.getText().toString().trim();

        boolean result = true;
        if(sId.isEmpty()) {
            editTextId.setError(getString(R.string.id) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(!sId.isEmpty()){
            try{
                Long.valueOf(sId);
            }catch (NumberFormatException e) {
                editTextId.setError(getString(R.string.dadcontainer)+ " " + getString(R.string.notnumeric_dialog));
                result = false;
            }
        }
        if(!sId.equals(contenedorModel.getId().toString())) {
            editTextId.setError(getString(R.string.id) + " " + "introducido no concuerda con el original");
            result = false;
        }
        if(name.isEmpty()) {
            editTextNombre.setError(getString(R.string.name) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(!sIdPadre.isEmpty()){
            try{
                Long.valueOf(sIdPadre);
            }catch (NumberFormatException e) {
                editTextContenedorPadre.setError(getString(R.string.dadcontainer)+ " " + getString(R.string.notnumeric_dialog));
                result = false;
            }
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(Contenedor contenedor) {
        contenedorModel = contenedor;
        editTextId.setText(contenedorModel.getId().toString());
        editTextNombre.setText(contenedorModel.getNombre());
        editTextDescripcion.setText(contenedorModel.getDescripcion());
        if(contenedorModel.getContenedorPadre()!=null) {
            editTextContenedorPadre.setText(contenedorModel.getContenedorPadre().getId().toString());
        }
        if(isUpdating) {
            Toast.makeText(getContext(), R.string.contenedor_updated_dialog, Toast.LENGTH_LONG).show();
            SelectContenedorFragment selectContenedorFragment = new SelectContenedorFragment(contenedorModel);
            ((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);
        }
        else if (!isUpdating) {
            ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
        }
        isUpdating=false;
    }

    @Override
    public void callbackDataAcessError(Contenedor contenedor) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}