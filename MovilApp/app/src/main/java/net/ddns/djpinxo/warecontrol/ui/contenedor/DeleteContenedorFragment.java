package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;

public class DeleteContenedorFragment implements FragmentCallback<Boolean> {

    //creado por mantener una estructura aun que no sea fragment

    private Activity activity;

    private Contenedor contenedorModel;

    public DeleteContenedorFragment(){
        super();
    }
    public DeleteContenedorFragment (Contenedor contenedorModel){
        this();
        this.contenedorModel=contenedorModel;

    }

    public void showConfirmationDialog(Activity activity) {
        this.activity=activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteContenedor();
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

    private void deleteContenedor(){
        if(validateContenedorForm()){
            MainActivity.contenedorDao.deleteContenedor(this, contenedorModel.getId());
        }
        else {
            Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateContenedorForm() {
        //TODO validar que no tenga hijos o almenos advertir
        //return !contenedorModel.getEmail().trim().isEmpty();
        return true;
    }

    @Override
    public void callbackDataAcessSuccess(Boolean object) {
        Toast.makeText(activity, R.string.contenedor_deleted_dialog, Toast.LENGTH_LONG).show();
        ViewContenedorFragment viewContenedorFragment = new ViewContenedorFragment();
        ((MainActivity) activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
    }

    @Override
    public void callbackDataAcessError(Boolean object) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
