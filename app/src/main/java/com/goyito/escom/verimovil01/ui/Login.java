package com.goyito.escom.verimovil01.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goyito.escom.verimovil01.R;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import java.io.File;

public class Login extends AppCompatActivity implements Validator.ValidationListener {
    private static final int SOLICITUD_PERMISO_ALMACENAMIENTO = 0;
    private View vista;
    private Validator validator;

    @Required(order = 1, message = "Debe introducir el nombre de usuario")
    private EditText Usuario;
    static String Susuario; //string para usuario

    @Required(order = 2, message = "La password es obligatoria")
    @TextRule(order = 3, minLength = 8, maxLength = 12, message = "Introduce entre 6 a 12 caracteres")
    @Regex(order = 4, pattern = "[A-Za-z0-9]+", message = "Solo caracteres Alfanumericos")
    private EditText Password;
    static String Spassword;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView(R.layout.actividad_login);
        vista = findViewById(R.id.content_main);

        validator = new Validator(this);
        validator.setValidationListener(this);

        TextView olvidado = (TextView) findViewById(R.id.recuperar);
        olvidado.setOnClickListener(OlvidadoClickListener);

        Button botonLogin = (Button) findViewById(R.id.ingresar);
        botonLogin.setOnClickListener(LoginClickListener);

        TextView registrar = (TextView) findViewById(R.id.registrar);
        registrar.setOnClickListener(RegistrarClickListener);

        Snackbar.make(vista, "Bienvenido!", Snackbar.LENGTH_SHORT).show();
        crearDirectorio();
    }

    private View.OnClickListener LoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Usuario = (EditText) findViewById(R.id.txtusuario);
            Password = (EditText) findViewById(R.id.txtpassword);

            Susuario = (String) Usuario.getText().toString();
            Spassword = (String) Password.getText().toString();

            validator.validate();
        }
    };

    private View.OnClickListener OlvidadoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent recuperar = new Intent(Login.this, ActividadRecuperar.class);
            startActivity(recuperar);
        }
    };

    private View.OnClickListener RegistrarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent registro = new Intent(Login.this, ActividadRegistro.class);
            startActivity(registro);
        }
    };

    public void crearDirectorio() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            File f = new File(Environment.getExternalStorageDirectory() + "/VeriMovil");
            // Comprobamos si la carpeta está ya creada
            // Si la carpeta no está creada, la creamos.
            if (!f.isDirectory()) {
                String newFolder = "/VeriMovil"; //cualquierCarpeta es el nombre de la Carpeta que vamos a crear
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File myNewFolder = new File(extStorageDirectory + newFolder);
                myNewFolder.mkdir(); //creamos la carpeta
            }
        } else {
            solicitarPermisoAlmacenamiento();
        }
    }

    public void solicitarPermisoAlmacenamiento() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(vista, "Sin el permiso administrar llamadas no puedo"
                    +" borrar llamadas del registro.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    SOLICITUD_PERMISO_ALMACENAMIENTO);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    SOLICITUD_PERMISO_ALMACENAMIENTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_ALMACENAMIENTO) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                crearDirectorio();
            } else {
                Snackbar.make(vista, "Sin el permiso, no puedo realizar la" +
                        "acción", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void iniciarLogin() {
            if (Susuario.equals("admin") && Spassword.equals("password")) {
                Intent principal = new Intent(Login.this, ActividadPrincipal.class);
                //principal.putExtra("texto",usuario);
                startActivity(principal);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void preValidation() {

    }

    @Override
    public void onSuccess() {
        //Toast.makeText(this, "Validado correctamente", Toast.LENGTH_LONG).show();
        iniciarLogin();
        //if(estadoValidador == 0){ //boton Login
          //  iniciarLogin();
        //}
    }

    @Override
    public void onFailure(View failedView, Rule failedRule) {
        Log.w("onFailure", "Mensage error " + failedRule.getFailureMessage());
        Log.w("onFailure", "failedView ID " + failedView.getId());
        Toast.makeText(this, failedRule.getFailureMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationCancelled() { }
}

