package com.goyito.escom.verimovil01.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.goyito.escom.verimovil01.R;

/**
 * Created by goyito on 14/02/17.
 */

public class ActividadPrincipal extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Bundle bundle = new Bundle();
    String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        // Parte para recoger los datos de login
        //savedInstanceState = getIntent().getExtras();
        //texto = savedInstanceState.getString("Texto"); //recogo el dato
        //saludo(texto);
        //mando el dato al faragmento_inicio
        //datos.putString("texto",texto);
        //recogerExtras();
        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //TextView textView = (TextView) findViewById(R.id.textUsuarioInicio);
        //textView.setText("Hola mundo");

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        //saludo();
    }

    public void recogerExtras() {
        Bundle extras = getIntent().getExtras();
        texto = extras.getString("texto");
        //agregarFragment(texto);
    }

    /*public void agregarFragment(String texto){
        //TextView textView = (TextView) findViewById(R.id.textUsuarioInicio2);
        //textView.setText(s);
        Bundle bundle = new Bundle();
        bundle.putString("texto",texto);
    }*/

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                //fragmentoGenerico = new FragmentoInicio();
                //Bundle bundle = new Bundle();
                //bundle.putString("texto", texto);
                fragmentoGenerico = FragmentoInicio.newInstance(bundle);
                break;
            case R.id.item_verificar:
                fragmentoGenerico = FragmentoVerificar.newInstance(bundle);
                break;
            case R.id.item_catalogo:
                //bundle.putString("id","FragmentoCatalogo");
                fragmentoGenerico = FragmentoCatalogo.newInstance(bundle);
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                break;
            /*case R.id.item_misautos:
                fragmentoGenerico = new
                break;*/
            /*case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;*/
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
