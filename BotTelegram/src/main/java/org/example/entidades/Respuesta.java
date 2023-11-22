package org.example.entidades;
/**
 * Clase Respuesta donde se definen todos sus atributos
 * Se usará para enviar las respuestas al usuario
 *
 * @author Hugo Díaz
 * @version 1.0, 04/10/2023
 */

import java.util.List;

public class Respuesta {

    private boolean ok;
    private List<Peticion> result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Peticion> getResult() {
        return result;
    }

    public void setResult(List<Peticion> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
