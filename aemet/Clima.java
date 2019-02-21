package com.example.maanas.aemet;

public class Clima {
    private String fecha, t_minima,t_maxima,municipio,provincia;

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Clima(String fecha, String t_minima, String t_maxima) {
        this.fecha = fecha;
        this.t_minima = t_minima;
        this.t_maxima = t_maxima;
    }

    public Clima() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getT_minima() {
        return t_minima;
    }

    public void setT_minima(String t_minima) {
        this.t_minima = t_minima;
    }

    public String getT_maxima() {
        return t_maxima;
    }

    public void setT_maxima(String t_maxima) {
        this.t_maxima = t_maxima;
    }
}
