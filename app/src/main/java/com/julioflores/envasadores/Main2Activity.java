package com.julioflores.envasadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {

    AsyncHttpClient cliente;
    Calendar calendariocompleto;
    Button botonter, botonpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cliente = new AsyncHttpClient();
        botonter = (Button) findViewById(R.id.terminadodia);
        botonpro = (Button) findViewById(R.id.problemadia);

        final String ids = getIntent().getExtras().getString("idenv");
        final String cantid1 = getIntent().getExtras().getString("cantenv");
        final String cantid2 = getIntent().getExtras().getString("cantenv2");
        final String cantid3 = getIntent().getExtras().getString("cantenv3");
        final String lotes1 = getIntent().getExtras().getString("lotenv1");
        final String lotes2 = getIntent().getExtras().getString("lotenv2");
        final String lotes3 = getIntent().getExtras().getString("lotenv3");

        botonter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date fechahora = calendariocompleto.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                final String dias = dateFormat.format(fechahora);
                ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                if(lanet != null && lanet.isConnected()){
                    final AlertDialog.Builder mibuild1 = new AlertDialog.Builder(Main2Activity.this);
                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
                    mibuild1.setTitle("Terminado");
                    final int c11 = Integer.parseInt(cantid1);
                    final int l11 = Integer.parseInt(lotes1);
                    final int c22 = Integer.parseInt(cantid2);
                    final int l22 = Integer.parseInt(lotes2);
                    final int c33 = Integer.parseInt(cantid3);
                    final int l33 = Integer.parseInt(lotes3);
                    if(c11 == 0 || l11 == 0) {
                        final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
                        t1.setText(cantid1);
                        final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
                        t2.setText(lotes1);
                        mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                if(lanet1 != null && lanet1.isConnected()){
                                    String vacio = "";
                                    String lt1 = t1.getText().toString();
                                    t1.setText(lt1);
                                    String rlots = t2.getText().toString();
                                    t2.setText(rlots);
                                    int cant1 = Integer.parseInt(lt1);
                                    int lote1 = Integer.parseInt(rlots);
                                    if(cant1 != 0 && lote1 != 0){
                                        if(c11 == cant1){
                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                    "&Etapa1=Terminado&Cantidad=" + lt1 +
                                                    "&DetalleProblema=" + vacio.replaceAll("", "%20") +
                                                    "&Lote=" + rlots + "&Cantidad2=0&Lote2=0&Cantidad3=0&Lote3=0" +
                                                    "&ID=" + ids;
                                            Toast.makeText(Main2Activity.this, "Terminado", Toast.LENGTH_SHORT).show();
                                            cliente.post(url, new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if(statusCode == 200){} }
                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                });
                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else if(cant1 < c11){
                                            Toast.makeText(Main2Activity.this, "Ingrese una Cantidad menor", Toast.LENGTH_SHORT).show();
                                        }else{
                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                    "&Etapa1=Pendiente&Cantidad=" + lt1 +
                                                    "&DetalleProblema=" + vacio.replaceAll("", "%20") +
                                                    "&Lote=" + rlots + "&Cantidad2=0&Lote2=0&Cantidad3=0&Lote3=0" +
                                                    "&ID=" + ids;
                                            Toast.makeText(Main2Activity.this, "Pendiente", Toast.LENGTH_SHORT).show();
                                            cliente.post(url, new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                });
                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }else{
                                        Toast.makeText(Main2Activity.this, "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                    botonter.setVisibility(View.INVISIBLE);
                                    botonpro.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                        mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                if(lanet1 != null && lanet1.isConnected()) {
                                    dialog.cancel();
                                }else{
                                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                    botonter.setVisibility(View.INVISIBLE);
                                    botonpro.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                        mibuild1.setView(mview1);
                        AlertDialog dialog = mibuild1.create();
                        dialog.show();
                    }//else if (c22 == 0 || l22 == 0){
//                        View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
//                        mibuild1.setTitle("Terminado");
//                        final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
//                        t1.setText(cantid1);
//                        final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
//                        t2.setText(lotes1);
//                        mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                                NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                if(lanet1 != null && lanet1.isConnected()){
//                                    String vacio = "";
//                                    String lt2 = t1.getText().toString();
//                                    t1.setText(lt2);
//                                    String rlots2 = t2.getText().toString();
//                                    t2.setText(rlots2);
//                                    int cant1 = Integer.parseInt(lt2);
//                                    int lote1 = Integer.parseInt(rlots2);
//                                    if(cant1 != 0 && lote1 != 0){
//                                        if(c11 == cant1){
//                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                    "&Etapa1=Terminado&Cantidad="+ c11 +
//                                                    "&DetalleProblema=" + vacio.replaceAll("","%20") +
//                                                    "&Lote=" + l11 + "&Cantidad2="+ lt2 + "&Lote2="+ rlots2 +
//                                                    "&Cantidad3=0&Lote3=0"+
//                                                    "&ID=" + ids;
//                                            Toast.makeText(Main2Activity.this, "Terminado", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            cliente.post(url, new AsyncHttpResponseHandler() {
//                                                @Override
//                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                @Override
//                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                });
//                                        }else if(c11 < cant1){
//                                            Toast.makeText(Main2Activity.this, "Ingrese una Cantidad menor", Toast.LENGTH_SHORT).show();
//                                        }else{
//                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                    "&Etapa1=Pendiente&Cantidad="+ c11 +
//                                                    "&DetalleProblema=" + vacio.replaceAll("","%20") +
//                                                    "&Lote=" + l11 + "&Cantidad2="+ lt2 + "&Lote2="+ rlots2 +
//                                                    "&Cantidad3=0&Lote3=0"+
//                                                    "&ID=" + ids;
//                                            Toast.makeText(Main2Activity.this, "Pendiente", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            cliente.post(url, new AsyncHttpResponseHandler() {
//                                                @Override
//                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                @Override
//                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                            });
//                                        }
//                                    }else{
//                                        Toast.makeText(Main2Activity.this, "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
//                                    }
//                                }else{
//                                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                    botonter.setVisibility(View.INVISIBLE);
//                                    botonpro.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        });
//                        mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                }
//                                });
//                        mibuild1.setView(mview1);
//                        AlertDialog dialog = mibuild1.create();
//                        dialog.show();
//                        }else if (c33 == 0 || l33 == 0){
//                        View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
//                        mibuild1.setTitle("Terminado");
//                        final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
//                        final int ccccc = c11 - c22;
//                        String c1c2c3 = String.valueOf(ccccc);
//                        t1.setText(c1c2c3);
//                        final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
//                        t2.setText(lotes2);
//                        mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                                NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                if(lanet1 != null && lanet1.isConnected()){
//                                    String lt3 = t1.getText().toString();
//                                    t1.setText(lt3);
//                                    String rlots3 = t2.getText().toString();
//                                    t2.setText(rlots3);
//                                    int cant3 = Integer.parseInt(lt3);
//                                    int lote1 = Integer.parseInt(rlots3);
//                                    if(cant3 != 0 && lote1 != 0){
//                                        if(ccccc == cant3) {
//                                            String vaci = "";
//                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                    "&Etapa1=Terminado&Cantidad="+ c11 + "&DetalleProblema=" + vaci.replaceAll("","%20") +
//                                                    "&Lote=" + l11 + "&Cantidad2="+ c22 + "&Lote2="+ l22 + "&Cantidad3="+ lt3 +"&Lote3="+ rlots3 +
//                                                    "&ID=" + ids;
//                                            Toast.makeText(Main2Activity.this, "Terminado", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            cliente.post(url, new AsyncHttpResponseHandler() {
//                                                @Override
//                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                @Override
//                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                });
//                                            }else if(cant3 < ccccc) {
//                                            String vacio = "No coincidio sobre el valor de cantidad";
//                                            String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                    "&Etapa1=Problema&Cantidad="+ c11 + "&DetalleProblema=" + vacio.replaceAll(" ","%20") +
//                                                    "&Lote=" + l11 + "&Cantidad2="+ c22 + "&Lote2="+ l22 + "&Cantidad3="+ lt3 +"&Lote3="+ rlots3 +
//                                                    "&ID=" + ids;
//                                            Toast.makeText(Main2Activity.this, "Enviado", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            cliente.post(url, new AsyncHttpResponseHandler() {
//                                                @Override
//                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                @Override
//                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                });
//                                        } else{
//                                            Toast.makeText(Main2Activity.this,"Ingrese menor cantidad", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }else{
//                                        Toast.makeText(Main2Activity.this, "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
//                                    }
//                                }else{
//                                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                    botonter.setVisibility(View.INVISIBLE);
//                                    botonpro.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        });
//                        mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                                NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                if(lanet1 != null && lanet1.isConnected()){
//                                    dialog.cancel();
//                                }else{
//                                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                    botonter.setVisibility(View.INVISIBLE);
//                                    botonpro.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        });
//                        mibuild1.setView(mview1);
//                        AlertDialog dialog = mibuild1.create();
//                        dialog.show();
//                    }else if(c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0){
//                        Toast.makeText(Main2Activity.this, "Ha llego al limite de Intentos de Cantidad/Lote", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                        startActivity(intent);
//                    } else { }
                    mibuild1.setView(mview1).create().show();
                } else{
                    Toast.makeText(Main2Activity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                    botonter.setVisibility(View.INVISIBLE);
                    botonpro.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
