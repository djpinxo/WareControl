/*package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

public class ContenedorModalSelect implements FragmentCallback<Contenedor> {
    private static Contenedor contenedorModel;
    private Activity activity;
    private AlertDialog dialog;

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedorPadre;

    public ContenedorModalSelect(){
        super();
    }

    public ContenedorModalSelect(Contenedor contenedorModel, Activity activity, AlertDialog dialog) {
        this();
        this.contenedorModel=contenedorModel;
        MainActivity.contenedorDao.getContenedor(this, contenedorModel.getId());
        this.activity = activity;
        this.dialog = dialog;

        editTextId = dialog.findViewById(R.id.editTextId);
        editTextNombre = dialog.findViewById(R.id.editTextNombre);
        editTextDescripcion = dialog.findViewById(R.id.editTextDescripcion);
        editTextContenedorPadre = dialog.findViewById(R.id.editTextContenedorPadre);
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
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
*/