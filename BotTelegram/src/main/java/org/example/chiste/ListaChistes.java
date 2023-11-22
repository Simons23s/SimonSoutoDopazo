package org.example.chiste;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Clase ListaChistes donde se definen un ArrayListe de objetos
 * Se usará para almacenar contenido de un fichero XML
 *
 * @author Simon Souto Dopazo
 * @version 1.3, 05/10/2023
 */

//Nombramos el elemento raíz
@XmlRootElement(name = "chistes")
public class ListaChistes {

    //Atributos
    private ArrayList<Chistes> listaChistes;

    @XmlElement(name = "chiste")
    public ArrayList<Chistes> getListaChistes() {

        return listaChistes;
    }



    public void setListaChistes(ArrayList<Chistes> listaChistes) {

        this.listaChistes = listaChistes;
    }

}
