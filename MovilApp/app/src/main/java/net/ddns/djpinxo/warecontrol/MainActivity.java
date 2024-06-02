package net.ddns.djpinxo.warecontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;


import android.widget.Button;

import net.ddns.djpinxo.warecontrol.data.daos.ContenedorDao;
import net.ddns.djpinxo.warecontrol.data.daos.ItemDao;
import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ApiClient;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ContenedorDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ItemDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.UserDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.local.UserDaoLocalBBDDImplements;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ViewContenedorFragment;
import net.ddns.djpinxo.warecontrol.ui.user.ViewUserFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    public static UserDao userDao;
    public static ItemDao itemDao;
    public static ContenedorDao contenedorDao;

    public MainActivity(){
        super();
        userDao=new UserDaoApiImplement();
        itemDao=new ItemDaoApiImplement();
        contenedorDao=new ContenedorDaoApiImplement();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ApiClient.getClient(this);

        //TODO revisar si esta logado mandar a un controller o a otro
        if (false){
            setContentView(R.layout.login_screen);

            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.login_button);

                }
            });
            Button regisButton = (Button) findViewById(R.id.registrer_button);
            regisButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.registrer_button);
                }
            });
        }
        else {
            Log.d(this.getPackageName(), "usuario logado");
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            ViewUserFragment viewUserFragment=new ViewUserFragment();
            ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
            transaction.add(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
            transaction.commit();
        }



    }

    public void changeFragment (int LayoutContenedorDeFragment, Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(LayoutContenedorDeFragment, fragment);
        transaction.commit();
    }

    public void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción a realizar si el usuario confirma

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción a realizar si el usuario cancela
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}