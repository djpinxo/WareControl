package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

public class InsertContenedorFragment extends Fragment implements FragmentCallback <Contenedor> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedorPadre;
    private Button buttonInsert;
    private Button buttonCancel;
    private Contenedor contenedorModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_contenedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedorPadre = view.findViewById(R.id.editTextContenedorPadre);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerContenedor();
                /*((MainActivity)getActivity()).showConfirmationDialog();
                contenedorModel=insertContenedor();
                if(contenedorModel != null){
                    Toast.makeText(getContext(), R.string.contenedor_inserted_dialog, Toast.LENGTH_LONG).show();
                    SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(contenedorModel);
                    ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);
                }
                else {
                    Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
                }*/

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertContenedor();
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
                ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
            }

        });
    }



    private void insertContenedor(){

        //long id = Long.valueOf(editTextId.getText().toString().trim());
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        long idPadre = Long.valueOf(editTextContenedorPadre.getText().toString().trim());

        if(validateContenedorForm()){
            contenedorModel=new Contenedor(0l,name,descripcion,new Contenedor(idPadre, null, null, null, null, null), null, null);
            MainActivity.contenedorDao.insertContenedor(this, contenedorModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateContenedorForm(){
        //long id = Long.valueOf(editTextId.getText().toString().trim());
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        long idPadre = Long.valueOf(editTextContenedorPadre.getText().toString().trim());

        boolean result=true;


        if(name.isEmpty()) {
            editTextNombre.setError(R.string.name + " " + R.string.required_dialog);
            result = false;
        }

        return result;
    }


    @Override
    public void callbackDataAcessSuccess(Contenedor contenedor) {
        Toast.makeText(getContext(), R.string.contenedor_inserted_dialog, Toast.LENGTH_LONG).show();
        contenedorModel=contenedor;
        SelectContenedorFragment selectContenedorFragment=new SelectContenedorFragment(contenedorModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectContenedorFragment);
    }

    @Override
    public void callbackDataAcessError(Contenedor contenedor) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}