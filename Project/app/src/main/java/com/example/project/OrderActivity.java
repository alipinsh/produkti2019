package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OrderActivity extends AppCompatActivity {
    private static final String t = "[ORDER]";
    private Order order;

    private ArrayList<String> displayList;
    private ArrayAdapter<String> productAdapter;

    private Button scanButton;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        order = new Order();

        scanButton = findViewById(R.id.scanbutton);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        Log.i(t, "Found views");

        order.setId(getIntent().getIntExtra("ORDER_ID", 0));
        order.setStatus(getIntent().getBooleanExtra("ORDER_STATUS", false));
        Log.i(t, "Found order id " + order.getId());

        if (order.getId() != 0) {
            OrderActivity.ConnectMySql connectMySql = new OrderActivity.ConnectMySql();
            connectMySql.execute("");
        }
    }



    protected class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(OrderActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

        }

        @Override
        public String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DBConnection.url, DBConnection.user, DBConnection.pass);
                Log.i(t, "Database connection success");

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT package_products.product, product.name, product.prodcode, package_products.quantity, package_products.scanned FROM package_products\n" +
                        "JOIN product\n" +
                        "ON package_products.product = product.id\n" +
                        "WHERE package=" + order.getId() + ";");
                ResultSetMetaData rsmd = rs.getMetaData();

                displayList = new ArrayList<String>();

                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("product"));
                    product.setName(rs.getString("name"));
                    product.setProdcode(rs.getString("prodcode"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setScanned(rs.getBoolean("scanned"));
                    order.addProduct(product);

                    String status;
                    if (product.getScanned()) {
                        status = "(scanned)";
                    } else {
                        status = "(not scanned)";
                    }

                    displayList.add(product.getId() + " " + status);
                }

                Log.i(t, displayList.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(t, e.toString());
            }

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            productAdapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_list_item_1, displayList);
            listView.setAdapter(productAdapter);
        }
    }
}

