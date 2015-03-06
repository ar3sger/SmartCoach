package de.itintouch.smartcoach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Register extends Activity {

    private EditText username, password1, password2;
    private Button register, cancel;
    private String string_username, string_password1, string_password2;
    private InputStream is = null;
    private Thread add_user, check_user;
    private ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    private boolean exists = true;
    private String line;
    private String result;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.et_username);
        password1 = (EditText) findViewById(R.id.et_password1);
        password2 = (EditText) findViewById(R.id.et_password2);

        register = (Button) findViewById(R.id.btn_abbrechen);
        cancel = (Button) findViewById(R.id.btn_cancel);

//        add_user = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost("http://itintouch.bplaced.net/register.php");
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                    HttpResponse response = httpclient.execute(httppost);
//                    HttpEntity entity = response.getEntity();
//                    is = entity.getContent();
//                } catch (Exception e) {
//                    Log.d("Error", e.toString());
//                }
//            }
//        });
//
//        check_user = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost("http://itintouch.bplaced.net/select.php");
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                    HttpResponse response = httpclient.execute(httppost);
//                    HttpEntity entity = response.getEntity();
//                    is = entity.getContent();
//                } catch (Exception e) {
//                    Log.d("Error", e.toString());
//                }
//
//                try {
//                    BufferedReader reader = new BufferedReader
//                            (new InputStreamReader(is, "iso-8859-1"), 8);
//                    StringBuilder sb = new StringBuilder();
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                    is.close();
//                    result = sb.toString();
//                } catch (Exception e) {
//                    Log.e("Fail 2", e.toString());
//                }
//
//                try {
//                    JSONObject json_data = new JSONObject(result);
//                    name = (json_data.getString("nickname"));
//                    if (name.toString().length() > 5) {
//                        exists = true;
//                    } else {
//                        exists = false;
//                    }
//
//                } catch (Exception e) {
//                    Log.e("Fail 3", e.toString());
//                }
//
//
//            }
//        });
//
//        register.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                string_username = username.getText().toString();
//                string_password1 = password1.getText().toString();
//                string_password2 = password2.getText().toString();
//                if (string_username.length() >= 6) {
//                    if (string_password1.equals(string_password2)) {
//                        register();
//                    } else {
//                        Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//                } else {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Username is too short!", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//
//            }
//        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    private void register() {
//        nameValuePairs.add(new BasicNameValuePair("user_id", "NULL"));
//        nameValuePairs.add(new BasicNameValuePair("nickname", string_username));
//        nameValuePairs.add(new BasicNameValuePair("password", string_password1));
//        Log.d("check", string_username);
//        try {
//            check_user.start();
//            if (!exists) {
//                add_user.start();
//                Toast toast = Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_SHORT);
//                toast.show();
//                Intent intent = new Intent(getBaseContext(), Login.class);
//                startActivity(intent);
//            } else {
//                Toast toast = Toast.makeText(getApplicationContext(), "Username already in use!", Toast.LENGTH_SHORT);
//                toast.show();
//                Intent intent = new Intent(getBaseContext(), Register.class);
//                startActivity(intent);
//            }
//        } catch (Exception e) {
//            Toast toast = Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_SHORT);
//            toast.show();
//        }
//
//    }

}
