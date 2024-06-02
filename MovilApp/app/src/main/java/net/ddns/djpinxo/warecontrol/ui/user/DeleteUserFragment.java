package net.ddns.djpinxo.warecontrol.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

public class DeleteUserFragment implements FragmentCallback<Boolean> {

    //creado por mantener una estructura aun que no sea fragment

    Activity activity;

    private User userModel;

    public DeleteUserFragment(){
        super();
    }
    public DeleteUserFragment (User userModel){
        this();
        this.userModel=userModel;

    }

    public void showConfirmationDialog(Activity activity) {
        this.activity=activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
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

    private void deleteUser(){
        if(validateUserForm()){
            MainActivity.userDao.deleteUser(this, userModel.getEmail());
        }
        else {
            Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUserForm() {
        return !userModel.getEmail().trim().isEmpty();
    }

    @Override
    public void callbackDataAcessSuccess(Boolean object) {
        Toast.makeText(activity, R.string.user_deleted_dialog, Toast.LENGTH_LONG).show();
        ViewUserFragment viewUserFragment = new ViewUserFragment();
        ((MainActivity) activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
    }

    @Override
    public void callbackDataAcessError(Boolean object) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
