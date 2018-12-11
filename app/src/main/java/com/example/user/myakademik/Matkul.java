package com.example.user.myakademik;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.user.myakademik.adapter.AdapterDosen;
import com.example.user.myakademik.adapter.AdapterMatkul;
import com.example.user.myakademik.model.Mahasiswa;
import com.example.user.myakademik.util.AppController;
import com.example.user.myakademik.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matkul extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Mahasiswa> itemList = new ArrayList<Mahasiswa>();
    AdapterMatkul adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_nim, txt_nama, txt_alamat;
    String nim, nama, alamat;

    private static final String TAG = Matkul.class.getSimpleName();
    private static String url_select = Server.URLMatkul + "select.php";
    private static String url_insert = Server.URLMatkul + "insert.php";
    private static String url_edit = Server.URLMatkul + "edit.php";
    private static String url_update = Server.URLMatkul + "update.php";
    private static String url_delete = Server.URLMatkul + "delete.php";
    public static final String TAG_KODE_MK = "kode_mk";
    public static final String TAG_NAMA_MATKUL = "nama_matkul";
    public static final String TAG_SKS = "sks";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul);
        // menghubungkan variablel pada layout dan pada java
        //fab = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);
        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterMatkul(Matkul.this, itemList);
        list.setAdapter(adapter);
        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
    }
    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }


    // untuk mengosongi edittext pada form
    private void kosong() {
        txt_nim.setText(null);
        txt_nama.setText(null);
        txt_alamat.setText(null);
    }


    // untuk menampilkan dialog from biodata
    private void DialogForm(String nimx, String namax, String alamatx, String button) {
        dialog = new AlertDialog.Builder(Matkul.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_biodata, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Biodata");
        txt_nim = (EditText) dialogView.findViewById(R.id.txt_nim);
        txt_nama = (EditText) dialogView.findViewById(R.id.txt_nama);
        txt_alamat = (EditText) dialogView.findViewById(R.id.txt_alamat);
        if (!nimx.isEmpty()) {
            txt_nim.setText(nimx);
            txt_nama.setText(namax);
            txt_alamat.setText(alamatx);
        } else {
            kosong();
        }
        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nim = txt_nim.getText().toString();
                nama = txt_nama.getText().toString();
                alamat = txt_alamat.getText().toString();
                simpan_update();
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });
        dialog.show();
    }


    // untuk menampilkan semua data pada listview
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Mahasiswa item = new Mahasiswa();
                                item.setKode_mk(obj.getString(TAG_KODE_MK));
                                item.setNama_matkul(obj.getString(TAG_NAMA_MATKUL));
                                item.setSks(obj.getString(TAG_SKS));
                                // menambah item ke array
                                itemList.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // notifikasi adanya perubahan data pada adapter
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });
        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (nim.isEmpty()) {
            url = url_insert;
        } else {
            url = url_update;
        }
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error node pada json
                            if (success == 1) {
                                Log.d("Add/update", jObj.toString());
                                callVolley();
                                kosong();
                                Toast.makeText(Matkul.this, jObj.getString(TAG_MESSAGE),
                                        Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(Matkul.this, jObj.getString(TAG_MESSAGE),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Matkul.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (nim.isEmpty()) {
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                } else {
                    params.put("nim", nim);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String nimx) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error node pada json
                            if (success == 1) {
                                Log.d("get edit data", jObj.toString());
                                String nimx = jObj.getString(TAG_KODE_MK);
                                String namax = jObj.getString(TAG_NAMA_MATKUL);
                                String alamatx = jObj.getString(TAG_SKS);
                                DialogForm(nimx, namax, alamatx, "UPDATE");
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(Matkul.this, jObj.getString(TAG_MESSAGE),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Matkul.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", nimx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String nimx) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            // Cek error node pada json
                            if (success == 1) {
                                Log.d("delete", jObj.toString());
                                callVolley();
                                Toast.makeText(Matkul.this, jObj.getString(TAG_MESSAGE),
                                        Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(Matkul.this, jObj.getString(TAG_MESSAGE),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(Matkul.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nim", nimx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}