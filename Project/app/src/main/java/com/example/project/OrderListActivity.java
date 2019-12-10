package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.app.Activity;
import android.app.ListActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class OrderListActivity extends ListActivity {

    private static final String t = "[LIST]";

    private ArrayAdapter<String> resultAdapter;
    private ArrayList<String> result_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(), "You chose order nr " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    protected class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(OrderListActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

        }

        @Override
        public String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DBConnection.url, DBConnection.user, DBConnection.pass);
                Log.i(t, "Database connection success");

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT DISTINCT package.id, package.status FROM package_products\n" +
                        "JOIN product\n" +
                        "ON package_products.product = product.id\n" +
                        "JOIN package\n" +
                        "ON package_products.package = package.id\n" +
                        "WHERE product.manufacturer=" + Manufacturer.id + " AND package.sent = true;");
                ResultSetMetaData rsmd = rs.getMetaData();

                result_list = new ArrayList<String>();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String status;
                    if (rs.getBoolean("status")) {
                        status = "(completed)";
                    } else {
                        status = "(not completed)";
                    }
                    result_list.add(id + " " + status);
                }

                Log.i(t, result_list.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(t, e.toString());
            }

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            resultAdapter = new ArrayAdapter<String>(OrderListActivity.this, android.R.layout.simple_list_item_1, result_list);
            setListAdapter(resultAdapter);
        }
    }

}