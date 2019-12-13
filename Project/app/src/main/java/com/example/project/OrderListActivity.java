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

import java.util.HashMap;
import java.util.List;

public class OrderListActivity extends ListActivity {

    private static final String t = "[LIST]";

    private ArrayAdapter<String> resultAdapter;
    private ArrayList<String> displayList;
    private HashMap<Integer, Boolean> orderStatuses;

    private int selectedRow;
    private int selectedOrder = 0;
    private int REQUEST_STATUS = 1;

    private boolean upd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        orderStatuses = new HashMap<Integer, Boolean>();
        displayList = new ArrayList<String>();

        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("fetch");

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        selectedRow = position;
        int orderId = Integer.parseInt(l.getItemAtPosition(position).toString().replaceAll("\\D+", ""));
        boolean orderStatus = orderStatuses.get(orderId);
        openOrder(orderId, orderStatus);
    }

    public void openOrder(int orderId, boolean orderStatus){
        selectedOrder = orderId;
        Intent intent2 = new Intent(this, OrderActivity.class);
        intent2.putExtra("ORDER_ID", orderId);
        intent2.putExtra("ORDER_STATUS", orderStatus);
        startActivityForResult(intent2, REQUEST_STATUS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_STATUS && resultCode == RESULT_OK) {
            Log.i(t, "Got result");

            boolean status = data.getBooleanExtra("ORDER_STATUS_AFTER", false);
            if (status != orderStatuses.get(selectedOrder)) {
                if (status) {
                    resultAdapter.remove(getListView().getItemAtPosition(selectedRow).toString());
                    resultAdapter.notifyDataSetChanged();
                }

                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("update", Boolean.toString(status));
            }

        }

    }



    protected class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        boolean fetching = true;

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

                if (params[0].equals("fetch")) {
                    fetching = true;
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT DISTINCT package.id, package.status FROM package_products\n" +
                            "JOIN product\n" +
                            "ON package_products.product = product.id\n" +
                            "JOIN package\n" +
                            "ON package_products.package = package.id\n" +
                            "WHERE product.manufacturer=" + Manufacturer.id + " AND package.sent = true;");
                    ResultSetMetaData rsmd = rs.getMetaData();


                    while (rs.next()) {
                        String status;
                        if (!(rs.getBoolean("status"))) {
                            orderStatuses.put(rs.getInt("id"), rs.getBoolean("status"));
                            displayList.add(rs.getString("id"));
                        }

                    }

                    Log.i(t, displayList.toString());
                } else if (params[0].equals("update")) {
                    fetching = false;
                    Statement st = con.createStatement();
                    st.executeUpdate("UPDATE package SET status=true WHERE id=" + params[1] + ";");
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
                resultAdapter = new ArrayAdapter<String>(OrderListActivity.this, android.R.layout.simple_list_item_1, displayList);
                setListAdapter(resultAdapter);
            }
        }
    }

}