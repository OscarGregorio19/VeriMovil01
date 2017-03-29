package com.goyito.escom.verimovil01.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goyito.escom.verimovil01.R;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;

/**
 * Created by goyito on 11/03/17.
 */

public class ActividadRegistro extends AppCompatActivity implements Validator.ValidationListener {
    private View vista;
    private Validator validator;

    @Required(order = 1, message = "Debe escribir su nombre")
    @TextRule(order = 2, maxLength =16, message = "Tu nombre puede llevar un maximo de 16 caracteres")
    @Regex(order = 3, pattern = "[A-Za-z]+")
    private EditText Nombre;
    String Snombre;

    @Required(order = 4, message = "Debe escribir su apellido")
    @TextRule(order = 5, maxLength =16, message = "Tu apellido puede llevar un maximo de 16 caracteres")
    @Regex(order = 6, pattern = "[A-Za-z]+")
    private EditText Apellido;
    String Sapellido;

    @Required(order = 7, message = "Es necesario su numero de licencia")
    @NumberRule(order = 8, type = NumberRule.NumberType.LONG, message = "La licencia solo puede tener numeros")
    @TextRule(order = 9,minLength = 16, maxLength = 16, message = "La licencia tiene 16 numeros")
    private EditText Licencia;
    String Slicencia;

    @Required(order = 10, message = "Su email es necesario")
    @Email(order = 11, message = "Debe introducir un email valido")
    private EditText Email;
    String Semail;

    @Required(order = 12, message = "Nombre usuario en necesario")
    @TextRule(order = 13, minLength = 4, maxLength = 12, message = "Usuario debe tener de 4 a 12 caracteres alfanumericos")
    @Regex(order = 14, pattern = "[A-Za-z0-9]+")
    private EditText RegUsuario;
    String SregUsuario = "";

        @Required(order = 15, message = "Debe escribir una contraseña")
    @Password(order = 16)
    @TextRule(order = 17, minLength = 8, maxLength = 12, message = "La contraseña puede ser de 6 a 12 caracteres Alfanumericos")
    @Regex(order = 18, pattern = "[A-Za-z0-9]+")
    private EditText Regpassword;
    String Sregpassword;

    @ConfirmPassword(order = 19, message = "Las contraseñas deben coincidir")
    private EditText RegConfirmarPassword;
    String SregConfirmarPassword;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro);
        vista = findViewById(R.id.actividad_registrar);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Button botonRegistro = (Button) findViewById(R.id.registro);
        botonRegistro.setOnClickListener(RegistroClickListener);
    }

    private View.OnClickListener RegistroClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Nombre = (EditText) findViewById(R.id.nombre);
            Apellido = (EditText) findViewById(R.id.apellido);
            Licencia = (EditText) findViewById(R.id.numlicencia);
            Email = (EditText) findViewById(R.id.email);
            RegUsuario = (EditText) findViewById(R.id.regUsuario);
            Regpassword = (EditText) findViewById(R.id.regPassword);
            RegConfirmarPassword = (EditText) findViewById(R.id.confirmarRegPassword);

            Snombre = (String) Nombre.getText().toString();
            Sapellido = (String) Apellido.getText().toString();
            Slicencia = (String) Licencia.getText().toString();
            Semail = (String) Email.getText().toString();
            SregUsuario = (String) RegUsuario.getText().toString();
            Sregpassword = (String) Regpassword.getText().toString();
            SregConfirmarPassword = (String) RegConfirmarPassword.getText().toString();

            validator.validate();
        }
    };

    @Override
    public void preValidation() {

    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Ahora puede iniciar sesion", Toast.LENGTH_LONG).show();
        finish();
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
