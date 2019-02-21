package com.example.maanas.aemet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static com.example.maanas.aemet.ParseoXML.tratarXML;

public class MainActivity extends AppCompatActivity {
    ListView lv_datos;
    TextView tv_codigo, tv_provincia, tv_municipio;
    EditText et_codigo;
    Spinner spn_municipio;
    Button btn_ver;
    String codigo;
    Clima c;
    HashMap mapa_ciudades=new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarVistas();
        mapa_ciudades.put("Madrid","28079");
        mapa_ciudades.put("Madrigal de la Vera","10111");
        mapa_ciudades.put("Móstoles","28092");
        Set conjunto_llaves=mapa_ciudades.keySet();
        Iterator it=conjunto_llaves.iterator();
        ArrayList<String> lista_ciudades=new ArrayList<>();
        while(it.hasNext())
        {
            String key=(String)it.next();
            lista_ciudades.add(key);
        }
        ArrayAdapter adaptador=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,lista_ciudades);
        spn_municipio.setAdapter(adaptador);
        accederXML();
    }

    private void cargarVistas() {
        tv_codigo = findViewById(R.id.tv_codigo);
        //et_codigo = findViewById(R.id.et_codigo);
        btn_ver = findViewById(R.id.btn_ver);
        tv_municipio=findViewById(R.id.tv_nombre_municipio);
        tv_provincia=findViewById(R.id.tv_nombre_provincia);
        spn_municipio=findViewById(R.id.spn_municipio);
    }

    public void accederXML() {
        final RequestQueue cola=Volley.newRequestQueue(this);
        btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String ciudad_seleccionada = (String) spn_municipio.getSelectedItem();
                    String codigo = (String) mapa_ciudades.get(ciudad_seleccionada);
                    String url = "http://www.aemet.es/xml/municipios/localidad_" + codigo + ".xml";
                    Response.Listener oyente_ok = new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            ArrayList<Clima> lista_dias = ParseoXML.tratarXML((String) response);
                            Lugar l = new Lugar();
                            String municipio = l.getMunicipio();
                            tv_municipio.setText(municipio);
                            String provincia = l.getProvincia();
                            tv_provincia.setText(provincia);
                            poblarListView(lista_dias);
                        }
                    };
                    Response.ErrorListener oyente_error = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    };
                    StringRequest stringRequest = new StringRequest(url, oyente_ok, oyente_error);
                    cola.add(stringRequest);
                }
        });

    }

    private void poblarListView(ArrayList<Clima> lista_dias) {
        lv_datos=findViewById(R.id.lv_datos);
        MiAdaptador adaptador=new MiAdaptador(lista_dias,this);
        lv_datos.setAdapter(adaptador);
    }



}
