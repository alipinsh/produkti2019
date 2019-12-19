package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;
import android.widget.Switch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class OrderActivity extends AppCompatActivity {
    private static final String t = "[ORDER]";
    private Order order;

    private ArrayList<String> displayList;
    private ArrayAdapter<String> productAdapter;

    private Button scanButton;
    private ListView listView;
    private TextView textView;
  //  switch1
    private Switch scan_swicth;
    private int REQUEST_CAMERA = 1;
    private HashMap<String, Boolean> cameraResult;
    private int upd;

/*
*            scan_swicth = (Switch) findViewById(R.id.switch1);
            scan_swicth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        } else {

                        }


                    }
                }});
*
* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        order = new Order();
        displayList = new ArrayList<String>();
        cameraResult = new HashMap<String, Boolean>();

        scanButton = findViewById(R.id.scanbutton);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        Log.i(t, "Found views");


        order.setId(getIntent().getIntExtra("ORDER_ID", 0));
        order.setStatus(getIntent().getBooleanExtra("ORDER_STATUS", false));
        Log.i(t, "Found order id " + order.getId());


        scanButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  openCamera();
              }
        }
        );


        if (order.getId() != 0) {
            ((TextView)findViewById(R.id.textView)).setText(Integer.toString(order.getId()));
            ConnectMySql connectMySql = new ConnectMySql();
            connectMySql.execute("fetch");
        }
    }

    public void openCamera() {
        Intent intent3 = new Intent(this, BarcodeActivity.class);
        ArrayList<Product> products = order.getProducts();
        HashMap<String, Boolean> prodcodes = new HashMap<String, Boolean>();
        for (Product product : products) {
            prodcodes.put(product.getProdcode(), product.getScanned());
        }
        intent3.putExtra("PRODCODES", prodcodes);
        startActivityForResult(intent3, REQUEST_CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Log.i(t, "Got result");
            HashMap<String, Boolean> result = (HashMap<String, Boolean>) data.getSerializableExtra("CAMERA_RESULT");
            Log.i(t, result.toString());
            ArrayList<Product> products = order.getProducts();

            listView.setAdapter(null);
            displayList.clear();

            for (Product product : products) {
                for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                    if (product.getProdcode().equals(entry.getKey())) {
                        if (product.getScanned() != entry.getValue()) {
                            product.setScanned(entry.getValue());
                            ConnectMySql connectMySql = new OrderActivity.ConnectMySql();
                            connectMySql.execute("update", Integer.toString(product.getId()));
                        }
                        String status = product.getScanned() ? "(scanned)" : "(not scanned)";
                        displayList.add(product.getName() + " " + product.getQuantity() + " " + status);
                    }
                }
            }

            //productAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_list_item_1, displayList);
            listView.setAdapter(productAdapter);

            int scannedn = 0;
            for (Product product : products) {
                if (product.getScanned()) {
                    scannedn++;
                }
            }
            if (scannedn == products.size()) {
                order.setStatus(true);
            }

        }
    }

    @Override
    public void onBackPressed() {
        Log.i(t, "Pressed back");

        Intent resultIntent = new Intent();
        resultIntent.putExtra("ORDER_STATUS_AFTER", order.getStatus());
        setResult(RESULT_OK, resultIntent);
        finish();
    }



    protected class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        boolean fetching = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(OrderActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
            internetCheck();
        }

        @Override
        public String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DBConnection.url, DBConnection.user, DBConnection.pass);
                Log.i(t, "Database connection success");

                if (params[0].equals("fetch")) {
                    fetching = true;

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT package_products.product, product.name, product.prodcode, package_products.quantity, package_products.scanned FROM package_products\n" +
                            "JOIN product\n" +
                            "ON package_products.product = product.id\n" +
                            "WHERE package=" + order.getId() + ";");
                    ResultSetMetaData rsmd = rs.getMetaData();


                    while (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getInt("product"));
                        product.setName(rs.getString("name"));
                        product.setProdcode(rs.getString("prodcode"));
                        product.setQuantity(rs.getInt("quantity"));
                        product.setScanned(rs.getBoolean("scanned"));
                        order.addProduct(product);

                        Log.i(t, "Added product to order");

                        String status;
                        if (product.getScanned()) {
                            status = "(scanned)";
                        } else {
                            status = "(not scanned)";
                        }
                        displayList.add(product.getName() + " " + product.getQuantity() + " " + status);
                        Log.i(t, "Added display string");
                    }

                    Log.i(t, displayList.toString());

                } else if (params[0].equals("update")) {
                    fetching = false;

                    Statement st = con.createStatement();
                    st.executeUpdate("UPDATE package_products SET scanned=true " +
                            "WHERE product=" + params[1] + " AND package=" + order.getId() + ";");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(t, e.toString());
            }

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if (fetching) {
                productAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_list_item_1, displayList);
                listView.setAdapter(productAdapter);
            }

    }
        public void internetCheck(){
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
              //  textView.setText("");
            }
            else{
                Toast.makeText(OrderActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
              //  textView.setText("Please turn on the Internet!");
            }
        }
}}

