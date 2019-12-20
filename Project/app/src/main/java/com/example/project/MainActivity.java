package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainActivity extends AppCompatActivity {
    private String t = "[MAIN]";
    private boolean isRightCode = false;
    private Button button;
    private EditText editText;
    //private Context context;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.txtData);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
            }
        });
    }

    protected class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            internetCheck();
            Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

        }

        @Override
        public String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DBConnection.url, DBConnection.user, DBConnection.pass);
                Log.i(t, "Database connection success");

                String result;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT manufacturer, code FROM packer;");
                ResultSetMetaData rsmd = rs.getMetaData();


                while (rs.next()) {
                    result = rs.getString("code");
                    if(result.equals(editText.getText().toString())){
                        Manufacturer.id = Integer.parseInt(rs.getString("manufacturer"));
                        isRightCode = true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(t, e.toString());
            }

            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (isRightCode) {
                openMenu();
            }
            else{
                Toast.makeText(MainActivity.this, "Wrong code!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openMenu(){

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void internetCheck(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            textView.setText("");
        }
        else{
            Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
            textView.setText("Please turn on the Internet!");
        }
    }
}
