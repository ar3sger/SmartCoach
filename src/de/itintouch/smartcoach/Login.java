package de.itintouch.smartcoach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {

    private Button btn_register, btn_login;
    private EditText et_login, et_password;

    private String login_name = "Admin";
    public String getLogin_name() {
		return login_name;
	}

	private String login_password = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login = (EditText) findViewById(R.id.et_Login);

        et_password = (EditText) findViewById(R.id.et_password);

        btn_register = (Button) findViewById(R.id.btn_abbrechen);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login = (Button) findViewById(R.id.btn_hinzufuegen);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_login.getText().toString().equals(login_name) && et_password.getText().toString().equals(login_password)) {
                    Toast.makeText(getBaseContext(), "Logged in as " + et_login.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), MainWindow.class);
                    startActivity(intent);
                    //finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "Password or Username mismatch!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}