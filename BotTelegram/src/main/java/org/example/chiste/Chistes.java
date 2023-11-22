package org.example.chiste;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Clase Chistes donde se definen todos sus atributos
 * Se usará para leer fichero XML
 *
 * @author Simon Souto Dopazo
 * @version 1.0, 04/10/2023
 */
public class Chistes {

    //Atributos
    private String texto;

    //Constructores
    public Chistes(String texto) {

        this.texto = texto;
    }

    public Chistes() {
    }

    //Getters y Setters
    @XmlElement(name = "texto")
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return "Chistes{" +
                "texto='" + texto + '\'' +
                '}';
    }


}
