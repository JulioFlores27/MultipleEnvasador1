package com.julioflores.envasadores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    AdaptadorEnvase adaptadores;
    ListView listanombres1, listanombres2;
    AsyncHttpClient cliente;
    Calendar calendariocompleto;
    Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listanombres1 = (ListView) findViewById(R.id.listan1);
        listanombres2 = (ListView) findViewById(R.id.listan2);
        cliente = new AsyncHttpClient();
        ObtenerEnvases1();
        ObtenerEnvases2();
    }
    private void ObtenerEnvases1(){
        String eduardo = "Eduardo";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ eduardo;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){ listarEnvases1(new String(responseBody)); }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases1(String respuesta){
        final ArrayList<Envases> lista = new ArrayList<Envases>();

        try{
            final JSONArray jsonarreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonarreglo.length(); i++){
                Envases t = new Envases();
                t.setIds(jsonarreglo.getJSONObject(i).getInt("ID"));
                t.setNopedidos(jsonarreglo.getJSONObject(i).getInt("NoPedidos"));
                t.setProductos(jsonarreglo.getJSONObject(i).getString("Producto"));
                t.setCantidades(jsonarreglo.getJSONObject(i).getInt("Cantidad"));
                t.setEtapa1s(jsonarreglo.getJSONObject(i).getString("Etapa1"));
                t.setFechaCapturas(jsonarreglo.getJSONObject(i).getString("FechaCaptura"));
                t.setFechaaprobaciones(jsonarreglo.getJSONObject(i).getString("FechaAprobacion"));
                t.setFechaasignadas(jsonarreglo.getJSONObject(i).getString("FechaAsignacion"));
                t.setFechaenvases(jsonarreglo.getJSONObject(i).getString("FechaEnvase"));
                t.setPersonaasignadas(jsonarreglo.getJSONObject(i).getString("PersonaAsignada"));
                t.setTipoenvases(jsonarreglo.getJSONObject(i).getString("TipoEnvase"));
                t.setLote(jsonarreglo.getJSONObject(i).getInt("Lote"));
                t.setCantidades2(jsonarreglo.getJSONObject(i).getInt("Cantidad2"));
                t.setLote2(jsonarreglo.getJSONObject(i).getInt("Lote2"));
                t.setCantidades3(jsonarreglo.getJSONObject(i).getInt("Cantidad3"));
                t.setLote3(jsonarreglo.getJSONObject(i).getInt("Lote3"));
                lista.add(t);
            }
            adaptadores = new AdaptadorEnvase(MainActivity.this, lista);
            listanombres1.setAdapter(adaptadores);
            listanombres1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                    if(lanet != null && lanet.isConnected()){
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        Envases esteenvase = (Envases) listanombres1.getItemAtPosition(position);
                        String iden = String.valueOf(esteenvase.getIds());
                        intent.putExtra("idenv", iden);
                        String cant = String.valueOf(esteenvase.getCantidades());
                        intent.putExtra("cantenv", cant);
                        String cant2 = String.valueOf(esteenvase.getCantidades2());
                        intent.putExtra("cantenv2", cant2);
                        String cant3 = String.valueOf(esteenvase.getCantidades3());
                        intent.putExtra("cantenv3", cant3);
                        String lote1 = String.valueOf(esteenvase.getLote());
                        intent.putExtra("lotenv1", lote1);
                        String lote2 = String.valueOf(esteenvase.getLote2());
                        intent.putExtra("lotenv2", lote2);
                        String lote3 = String.valueOf(esteenvase.getLote3());
                        intent.putExtra("lotenv2", lote3);
                        startActivity(intent);
                    }else {

                    }
                }
            });
        }catch (Exception e1){ e1.printStackTrace(); }
    }





    private void ObtenerEnvases2(){
        String eduardo = "Eduardo";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ eduardo;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases2(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases2(String respuesta){
        final ArrayList<Envases> lista = new ArrayList<Envases>();

        try{
            final JSONArray jsonarreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonarreglo.length(); i++){
                Envases t = new Envases();
                t.setIds(jsonarreglo.getJSONObject(i).getInt("ID"));
                t.setNopedidos(jsonarreglo.getJSONObject(i).getInt("NoPedidos"));
                t.setProductos(jsonarreglo.getJSONObject(i).getString("Producto"));
                t.setCantidades(jsonarreglo.getJSONObject(i).getInt("Cantidad"));
                t.setEtapa1s(jsonarreglo.getJSONObject(i).getString("Etapa1"));
                t.setFechaCapturas(jsonarreglo.getJSONObject(i).getString("FechaCaptura"));
                t.setFechaaprobaciones(jsonarreglo.getJSONObject(i).getString("FechaAprobacion"));
                t.setFechaasignadas(jsonarreglo.getJSONObject(i).getString("FechaAsignacion"));
                t.setFechaenvases(jsonarreglo.getJSONObject(i).getString("FechaEnvase"));
                t.setPersonaasignadas(jsonarreglo.getJSONObject(i).getString("PersonaAsignada"));
                t.setTipoenvases(jsonarreglo.getJSONObject(i).getString("TipoEnvase"));
                t.setLote(jsonarreglo.getJSONObject(i).getInt("Lote"));
                t.setCantidades2(jsonarreglo.getJSONObject(i).getInt("Cantidad2"));
                t.setLote2(jsonarreglo.getJSONObject(i).getInt("Lote2"));
                t.setCantidades3(jsonarreglo.getJSONObject(i).getInt("Cantidad3"));
                t.setLote3(jsonarreglo.getJSONObject(i).getInt("Lote3"));
                lista.add(t);
            }
            adaptadores = new AdaptadorEnvase(MainActivity.this, lista);
            listanombres2.setAdapter(adaptadores);
//            listanombres.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                    Date fechahora = calendariocompleto.getInstance().getTime();
//                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//                    final String dias = dateFormat.format(fechahora);
//                    final Envases esteenvase = (Envases) listanombres.getItemAtPosition(position);
//                    final AlertDialog.Builder mibuild1 = new AlertDialog.Builder(MainActivity.this);
//                    final View mviewd = getLayoutInflater().inflate(R.layout.opcion_dialogo, null);
//                    final Button botonter = (Button) mviewd.findViewById(R.id.terminadodia);
//                    Button botonpro = (Button) mviewd.findViewById(R.id.problemadia);
//                    mibuild1.setTitle("Seleccione Opción:");
//                    botonter.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final String ids = String.valueOf(esteenvase.getIds());
//                            ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                            NetworkInfo lanet = conectividad.getActiveNetworkInfo();
//                            if(lanet != null && lanet.isConnected()){
//                                final String cant = String.valueOf(esteenvase.getCantidades());
//                                final String cant2 = String.valueOf(esteenvase.getCantidades2());
//                                String cant3 = String.valueOf(esteenvase.getCantidades3());
//                                String lots = String.valueOf(esteenvase.getLote());
//                                String lots2 = String.valueOf(esteenvase.getLote2());
//                                String lots3 = String.valueOf(esteenvase.getLote3());
//                                final int c11 = Integer.parseInt(cant);
//                                final int l11 = Integer.parseInt(lots);
//                                final int c22 = Integer.parseInt(cant2);
//                                final int l22 = Integer.parseInt(lots2);
//                                final int c33 = Integer.parseInt(cant3);
//                                int l33 = Integer.parseInt(lots3);
//                                if(c11 == 0 || l11 == 0) {
//                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
//                                    mibuild1.setTitle("Terminado");
//                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
//                                    t1.setText(cant);
//                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
//                                    t2.setText(lots);
//                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            ConnectivityManager conectividad1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                            if(lanet1 != null && lanet1.isConnected()){
//                                                String vacio = "";
//                                                String lt1 = t1.getText().toString();
//                                                t1.setText(lt1);
//                                                String rlots = t2.getText().toString();
//                                                t2.setText(rlots);
//                                                int cant1 = Integer.parseInt(lt1);
//                                                int lote1 = Integer.parseInt(rlots);
//                                                if(cant1 != 0 && lote1 != 0){
//                                                    if(c11 == cant1){
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Terminado&Cantidad=" + lt1 +
//                                                                "&DetalleProblema=" + vacio.replaceAll("", "%20") +
//                                                                "&Lote=" + rlots + "&Cantidad2=0&Lote2=0&Cantidad3=0&Lote3=0" +
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }else if(cant1 < c11){
//                                                        Toast.makeText(getActivity(), "Ingrese una Cantidad menor", Toast.LENGTH_SHORT).show();
//                                                    }else{
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Pendiente&Cantidad=" + lt1 +
//                                                                "&DetalleProblema=" + vacio.replaceAll("", "%20") +
//                                                                "&Lote=" + rlots + "&Cantidad2=0&Lote2=0&Cantidad3=0&Lote3=0" +
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Pendiente", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }
//                                                }else{
//                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
//                                                }
//                                            }else{
//                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                                listanombres.setVisibility(View.INVISIBLE);
//                                            }
//                                        }
//                                    });
//                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                            if(lanet1 != null && lanet1.isConnected()) {
//                                                dialog.cancel();
//                                            }else{
//                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                                listanombres.setVisibility(View.INVISIBLE);
//                                            }
//                                        }
//                                    });
//                                    mibuild1.setView(mview1);
//                                    AlertDialog dialog = mibuild1.create();
//                                    dialog.show();
//                                } else if (c22 == 0 || l22 == 0){
//                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
//                                    mibuild1.setTitle("Terminado");
//                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
//                                    t1.setText(cant);
//                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
//                                    t2.setText(lots);
//                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                            if(lanet1 != null && lanet1.isConnected()){
//                                                String vacio = "";
//                                                String lt2 = t1.getText().toString();
//                                                t1.setText(lt2);
//                                                String rlots2 = t2.getText().toString();
//                                                t2.setText(rlots2);
//                                                int cant1 = Integer.parseInt(lt2);
//                                                int lote1 = Integer.parseInt(rlots2);
//                                                if(cant1 != 0 && lote1 != 0){
//                                                    if(c11 == cant1){
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Terminado&Cantidad="+ c11 +
//                                                                "&DetalleProblema=" + vacio.replaceAll("","%20") +
//                                                                "&Lote=" + l11 + "&Cantidad2="+ lt2 + "&Lote2="+ rlots2 +
//                                                                "&Cantidad3=0&Lote3=0"+
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }else if(c11 < cant1){
//                                                        Toast.makeText(getActivity(), "Ingrese una Cantidad menor", Toast.LENGTH_SHORT).show();
//                                                    }else{
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Pendiente&Cantidad="+ c11 +
//                                                                "&DetalleProblema=" + vacio.replaceAll("","%20") +
//                                                                "&Lote=" + l11 + "&Cantidad2="+ lt2 + "&Lote2="+ rlots2 +
//                                                                "&Cantidad3=0&Lote3=0"+
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Pendiente", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }
//                                                }else{
//                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
//                                                }
//                                            }else{
//                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                                listanombres.setVisibility(View.INVISIBLE);
//                                            }
//                                        }
//                                    });
//                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                                    mibuild1.setView(mview1);
//                                    AlertDialog dialog = mibuild1.create();
//                                    dialog.show();
//                                }else if (c33 == 0 || l33 == 0){
//                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
//                                    mibuild1.setTitle("Terminado");
//                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
//                                    final int ccccc = c11 - c22;
//                                    String c1c2c3 = String.valueOf(ccccc);
//                                    t1.setText(c1c2c3);
//                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
//                                    t2.setText(lots2);
//                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                            if(lanet1 != null && lanet1.isConnected()){
//                                                String lt3 = t1.getText().toString();
//                                                t1.setText(lt3);
//                                                String rlots3 = t2.getText().toString();
//                                                t2.setText(rlots3);
//                                                int cant3 = Integer.parseInt(lt3);
//                                                int lote1 = Integer.parseInt(rlots3);
//                                                if(cant3 != 0 && lote1 != 0){
//                                                    if(ccccc == cant3) {
//                                                        String vaci = "";
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Terminado&Cantidad="+ c11 + "&DetalleProblema=" + vaci.replaceAll("","%20") +
//                                                                "&Lote=" + l11 + "&Cantidad2="+ c22 + "&Lote2="+ l22 + "&Cantidad3="+ lt3 +"&Lote3="+ rlots3 +
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }else if(cant3 < ccccc) {
//                                                        String vacio = "No coincidio sobre el valor de cantidad";
//                                                        String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                                "&Etapa1=Problema&Cantidad="+ c11 + "&DetalleProblema=" + vacio.replaceAll(" ","%20") +
//                                                                "&Lote=" + l11 + "&Cantidad2="+ c22 + "&Lote2="+ l22 + "&Cantidad3="+ lt3 +"&Lote3="+ rlots3 +
//                                                                "&ID=" + ids;
//                                                        Toast.makeText(getActivity(), "Enviado", Toast.LENGTH_SHORT).show();
//                                                        cliente.post(url, new AsyncHttpResponseHandler() {
//                                                            @Override
//                                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                            @Override
//                                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                        });
//                                                    }
//                                                    else{
//                                                        Toast.makeText(getActivity(),"Ingrese menor cantidad", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }else{
//                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
//                                                }
//                                            }else{
//                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                                listanombres.setVisibility(View.INVISIBLE);
//                                            }
//                                        }
//                                    });
//                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                            if(lanet1 != null && lanet1.isConnected()){
//                                                dialog.cancel();
//                                            }else{
//                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                                listanombres.setVisibility(View.INVISIBLE);
//                                            }
//                                        }
//                                    });
//                                    mibuild1.setView(mview1);
//                                    AlertDialog dialog = mibuild1.create();
//                                    dialog.show();
//                                }else if(c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0){
//                                    Toast.makeText(getActivity(), "Ha llego al limite de Intentos de Cantidad/Lote", Toast.LENGTH_SHORT).show();
//                                } else { }
//                            }else{
//                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                listanombres.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    });
//                    botonpro.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final String ids = String.valueOf(esteenvase.getIds());
//                            ConnectivityManager conectividad = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                            NetworkInfo lanet = conectividad.getActiveNetworkInfo();
//                            if(lanet != null && lanet.isConnected()){
//                                final String cant1 = String.valueOf(esteenvase.getCantidades());
//                                final String cant21 = String.valueOf(esteenvase.getCantidades2());
//                                final String cant31 = String.valueOf(esteenvase.getCantidades3());
//                                final String lots1 = String.valueOf(esteenvase.getLote());
//                                final String lots21 = String.valueOf(esteenvase.getLote2());
//                                final String lots31 = String.valueOf(esteenvase.getLote3());
//                                //Toast.makeText(getActivity(), cant1+cant21+cant31,Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder mibuild = new AlertDialog.Builder(getActivity());
//                                final View mview = getLayoutInflater().inflate(R.layout.problema_dialogo, null);
//                                mibuild.setTitle("Problema");
//                                mibuild.setMessage("Anota su problema:");
//                                final AlertDialog.Builder builder = mibuild.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                        NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                        if(lanet1 != null && lanet1.isConnected()) {
//                                            EditText p1 = (EditText) mview.findViewById(R.id.problemas);
//                                            Date fechahora = Calendar.getInstance().getTime();
//                                            String pr1 = p1.getText().toString();
//                                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//                                            if(pr1.isEmpty()){
//                                                Toast.makeText(getActivity(), "No se guardo, favor de escribir sus problema", Toast.LENGTH_SHORT).show();
//                                            }else {
//                                                String url2 = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
//                                                        "&Etapa1=Terminado&Cantidad="+ cant1 + "&DetalleProblema=" + pr1.replaceAll(" ","%20") +
//                                                        "&Lote=" + lots1 + "&Cantidad2="+ cant21 + "&Lote2="+ lots21 + "&Cantidad3="+ cant31 +"&Lote3="+ lots31 +
//                                                        "&ID=" + ids;
//                                                Toast.makeText(getActivity(), "Problema Enviada", Toast.LENGTH_SHORT).show();
//                                                cliente.post(url2, new AsyncHttpResponseHandler() {
//                                                    @Override
//                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
//                                                    @Override
//                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
//                                                });
//                                            }
//                                        }else {
//                                            Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                            listanombres.setVisibility(View.INVISIBLE);
//                                        }
//                                    }
//                                });
//                                mibuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                                        NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
//                                        if(lanet1 != null && lanet1.isConnected()) {
//                                            dialog.cancel();
//                                        } else {
//                                            Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                            listanombres.setVisibility(View.INVISIBLE);
//                                        }
//                                    }
//                                });
//                                mibuild.setView(mview);
//                                AlertDialog dialog1 = mibuild.create();
//                                dialog1.show();
//                            } else{
//                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
//                                listanombres.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    });
//                    mibuild1.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
//                    });
//                    mibuild1.setView(mviewd).create().show();
//                }
//            });
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }
}
