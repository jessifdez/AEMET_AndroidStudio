package com.example.maanas.aemet;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseoXML {
    public static ArrayList<Clima> tratarXML(String response) {
        ArrayList<Clima> clima = new ArrayList<>();
        ArrayList<Lugar> lugar=new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        /*String fecha = null;
        String t_minima = null;
        String t_maxima = null;*/
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(response)));
            Lugar l=new Lugar();
            NodeList nodo_nombre=doc.getElementsByTagName("nombre");
            for(int x=0;x<nodo_nombre.getLength();x++)
            {
                Node nodo_municipio=nodo_nombre.item(x);
                String municipio=nodo_municipio.getTextContent();
                Log.d("etiqueta",municipio);
                l.setMunicipio(municipio);
            }
            NodeList nodo_provincia=doc.getElementsByTagName("provincia");
            for(int v=0;v<nodo_provincia.getLength();v++)
            {
                Node nodo_prov=nodo_provincia.item(v);
                String provincia=nodo_prov.getTextContent();
                Log.d("etiqueta",provincia);
                l.setProvincia(provincia);
            }
            lugar.add(l);
            NodeList lista_predicciones = doc.getElementsByTagName("prediccion");
            for (int i = 0; i < lista_predicciones.getLength(); i++) {
                Node nodo_prediccion = lista_predicciones.item(i);
                NodeList hijos_prediccion = nodo_prediccion.getChildNodes();
                for (int j = 0; j < hijos_prediccion.getLength(); j++) {
                    Node nodo_hijo_prediccion = hijos_prediccion.item(j);
                    if (nodo_hijo_prediccion.getNodeName().equals("dia")) {
                        Clima c=new Clima();
                        //Para que te lea la fecha(es un atributo)
                        Node nodo_atributo = nodo_hijo_prediccion.getAttributes().getNamedItem("fecha");
                        String fecha = nodo_atributo.getTextContent();
                        c.setFecha(fecha);
                        Log.d("etiqueta", "FECHA: " + fecha);
                        NodeList hijos_dia = nodo_hijo_prediccion.getChildNodes();
                        for (int z = 0; z < hijos_dia.getLength(); z++) {
                            Node nodo_dia = hijos_dia.item(z);
                            if (nodo_dia.getNodeName().equals("temperatura")) {
                                NodeList hijos_temp = nodo_dia.getChildNodes();
                                for (int y = 0; y < hijos_temp.getLength(); y++) {
                                    Node nodo_hijo_temp = hijos_temp.item(y);
                                    if (nodo_hijo_temp.getNodeName().equals("maxima")) {
                                        String t_maxima = nodo_hijo_temp.getTextContent();
                                        c.setT_maxima(t_maxima);
                                        Log.d("etiqueta", "MAXIMA: " + t_maxima);
                                    }
                                    if (nodo_hijo_temp.getNodeName().equals("minima")) {
                                        String t_minima = nodo_hijo_temp.getTextContent();
                                        c.setT_minima(t_minima);
                                        Log.d("etiqueta", "MINIMA: " + t_minima);
                                    }
                                }
                            }
                        }
                        clima.add(c);
                    }

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clima;
    }
}
