package akhaled.shahen.com.exchangeme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText et_fullNAme, et_email, et_passWord, et_phoneNumber, et_con, et_country;
    String S_fullNAme, S_email, S_passWord, S_phoneNumber, S_con, S_country;
    Button btn_signUp;
    Boolean valid = false;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        et_fullNAme = findViewById(R.id.et_fullName);
        et_email = findViewById(R.id.et_Email);
        et_passWord = findViewById(R.id.et_passWord);
        et_con = findViewById(R.id.et_con_passWord);
        et_country = findViewById(R.id.et_country);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        btn_signUp = findViewById(R.id.btn_register);
        btn_signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker (Register.this);
                if (ActivityCompat.checkSelfPermission( Register.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( Register.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }
                latitude = gps.getLatitude();
                longitude= gps.getLongitude();

                S_fullNAme = et_fullNAme.getText().toString();
                S_email = et_email.getText().toString();
                S_passWord = et_passWord.getText().toString();
                S_con = et_con.getText().toString();
                S_country = et_country.getText().toString();
                S_phoneNumber = et_phoneNumber.getText().toString();
                if (et_email.getText().length()<=8){
                    et_email.setError( "Please write a valid Email" );
                    valid = false;
                }
                if (et_fullNAme.getText().length()<=2){
                    et_fullNAme.setError( "Please write a valid Name" );
                    valid = false;
                }

                if (S_passWord.equals( S_con ) && S_passWord.length()>=6) {
                    if (S_email.isEmpty()){
                        et_email.setError( "Please Fill Email" );
                    }else {
                        if (valid){
                            //Toast.makeText(Register.this,"Invalid email address",Toast.LENGTH_SHORT).show();
                            et_email.setError( "Invalid email address" );
                        }else {
                            AddUser( S_fullNAme, S_passWord, S_email, S_country, S_phoneNumber );
                            Intent intent = new Intent(Register.this,Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity( intent );
                        }
                    }
                } else {
                    //Toast.makeText( Register.this, "Password Doesn't Match", Toast.LENGTH_LONG ).show();
                    et_passWord.setError( "Password Doesn't Match or Less than 6 Letters" );
                }
            }
        } );
    }

    private void AddUser(final String fullname, final String pass, final String email, final String country, final String phone) {

        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                "https://programmerx.000webhostapp.com/ExchangeMe/UserRegister.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        String s = URLEncoder.encode( response, "ISO-8859-1" );
                        response = URLDecoder.decode( s, "UTF-8" );
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText( Register.this,response,Toast.LENGTH_SHORT ).show();

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //progressDialog.dismiss();
            Toast.makeText(Register.this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap hashMap = new HashMap();
            hashMap.put("name",fullname);
            hashMap.put("email",email);
            hashMap.put("password",pass);
            hashMap.put("capital",country);
            hashMap.put("phone",phone);
            hashMap.put("lat",String.valueOf(latitude));
            hashMap.put("long",String.valueOf(longitude));
            return hashMap;
        }
    };
        Volley.newRequestQueue(Register.this).add(stringRequest);
}


}
