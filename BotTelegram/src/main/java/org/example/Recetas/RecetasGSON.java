package org.example.Recetas;

import com.google.gson.annotations.Expose;

/**
 * Clase RecetasGSON donde se todos sus atributos
 * Se usará para leer fichero GSON
 *
 * @author Simon Souto Dopazo
 * @version 2.0, 10/10/2023
 */

public class RecetasGSON {

    //Atributos
    @Expose
    private String primerPlato;
    @Expose
    private String segundoPlato;
    @Expose
    private String postre;

    //Constructores
    public RecetasGSON(String primerPlato, String segundoPlato, String postre) {
        this.primerPlato = primerPlato;
        this.segundoPlato = segundoPlato;
        this.postre = postre;
    }

    //Getters y Setters
    public String getPrimerPlato() {
        return primerPlato;
    }

    public void setPrimerPlato(String primerPlato) {
        this.primerPlato = primerPlato;
    }

    public String getSegundoPlato() {
        return segundoPlato;
    }

    public void setSegundoPlato(String segundoPlato) {
        this.segundoPlato = segundoPlato;
    }

    public String getPostre() {
        return postre;
    }

    public void setPostre(String postre) {
        this.postre = postre;
    }

    @Override
    public String toString() {
        return "RecetasGSON{" +
                "primerPlato='" + primerPlato + '\'' +
                ", segundoPlato='" + segundoPlato + '\'' +
                ", postre='" + postre + '\'' +
                '}';
    }
}
