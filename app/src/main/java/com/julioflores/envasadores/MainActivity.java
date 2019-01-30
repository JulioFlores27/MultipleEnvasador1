package com.julioflores.envasadores;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    AdaptadorEnvase adaptadores;
    ListView listanombres1, listanombres2, listanombres3, listanombres4, listanombres5, listanombres6, listanombres7;
    AsyncHttpClient cliente;
    Handler customHandler = new Handler();

    public class contar extends CountDownTimer {
        public contar(long milienfuturo, long countdowninterval){
            super(milienfuturo,countdowninterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            ObtenerEnvases1();
            ObtenerEnvases2();
            ObtenerEnvases3();
            ObtenerEnvases4();
            ObtenerEnvases5();
            ObtenerEnvases6();
            ObtenerEnvases7();
        }
        public void onFinish(){
            ObtenerEnvases1();
            ObtenerEnvases2();
            ObtenerEnvases3();
            ObtenerEnvases4();
            ObtenerEnvases5();
            ObtenerEnvases6();
            ObtenerEnvases7();
            //Toast.makeText(MainActivity.this, "Actualizado",Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable actualizartimer = new Runnable() {
        @Override
        public void run() {
            contar tiempo = new contar(5000, 5000);
            tiempo.start();
            customHandler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listanombres1 = (ListView) findViewById(R.id.listan1);
        listanombres2 = (ListView) findViewById(R.id.listan2);
        listanombres3 = (ListView) findViewById(R.id.listan3);
        listanombres4 = (ListView) findViewById(R.id.listan4);
        listanombres5 = (ListView) findViewById(R.id.listan5);
        listanombres6 = (ListView) findViewById(R.id.listan6);
        listanombres7 = (ListView) findViewById(R.id.listan7);
        customHandler = new android.os.Handler();
        customHandler.postDelayed(actualizartimer, 1000);
        cliente = new AsyncHttpClient();
        ConnectivityManager conectividad = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lanet = conectividad.getActiveNetworkInfo();
        if(lanet != null && lanet.isConnected()){
            ObtenerEnvases1();
            ObtenerEnvases2();
            ObtenerEnvases3();
            ObtenerEnvases4();
            ObtenerEnvases5();
            ObtenerEnvases6();
            ObtenerEnvases7();
            listanombres1.setVisibility(View.VISIBLE);
            listanombres2.setVisibility(View.VISIBLE);
            listanombres3.setVisibility(View.VISIBLE);
            listanombres4.setVisibility(View.VISIBLE);
            listanombres5.setVisibility(View.VISIBLE);
            listanombres6.setVisibility(View.VISIBLE);
            listanombres7.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(MainActivity.this, "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
            listanombres1.setVisibility(View.INVISIBLE);
            listanombres2.setVisibility(View.INVISIBLE);
            listanombres3.setVisibility(View.INVISIBLE);
            listanombres4.setVisibility(View.INVISIBLE);
            listanombres5.setVisibility(View.INVISIBLE);
            listanombres6.setVisibility(View.INVISIBLE);
            listanombres7.setVisibility(View.INVISIBLE);
        }
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
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases2(){
        String cesar = "Cesar";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ cesar;
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
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases3(){
        String francisco = "Francisco";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ francisco;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases3(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases3(String respuesta){
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
            listanombres3.setAdapter(adaptadores);
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases4(){
        String joseluis = "Jose Luis";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ joseluis.replaceAll(" ","%20");
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases4(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases4(String respuesta){
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
            listanombres4.setAdapter(adaptadores);
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases5(){
        String leonardo = "Leonardo";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ leonardo;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases5(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases5(String respuesta){
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
            listanombres5.setAdapter(adaptadores);
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases6(){
        String miguel = "Miguel";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ miguel;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases6(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases6(String respuesta){
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
            listanombres6.setAdapter(adaptadores);
        }catch (Exception e1){ e1.printStackTrace(); }
    }
    private void ObtenerEnvases7(){
        String silver = "Silver";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ silver;
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases7(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
        });
    }
    public void listarEnvases7(String respuesta){
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
            listanombres7.setAdapter(adaptadores);
        }catch (Exception e1){ e1.printStackTrace(); }
    }
}
