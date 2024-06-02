package net.ddns.djpinxo.warecontrol;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;


import android.widget.Button;

import net.ddns.djpinxo.warecontrol.ui.user.ViewUserFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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

            transaction.add(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
            transaction.commit();
        }



    }

    public void changeFragment (int LayoutContenedorDeFragment, Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(LayoutContenedorDeFragment, fragment);
        transaction.commit();
    }


}