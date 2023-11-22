package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Recetas.RecetasGSON;
import org.example.chiste.Chistes;
import org.example.chiste.ListaChistes;
import org.example.entidades.Envio;
import org.example.entidades.Peticion;
import org.example.entidades.Respuesta;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase con métodos para utilizar el bot de Telegram
 *
 * @author Simon Souto Dopazo
 * @version 1.2, 10/10/2023
 */
public class BotUtils {

    static final String BOT_TOKEN = "6502763626:AAF5qusuAgoabtOO1Z60qyfa-4KZok45sqs";
    static final String API_TELEGRAM = "https://api.telegram.org/bot";
    static final String METHOD_GET = "GET";
    static final String METHOD_POST = "POST";
    static final String PROPERTY_ACCEPT = "Accept";
    static final String PROPERTY_APPLICATION_JSON = "application/json";
    static final String PROPERTY_CONTENT_TYPE = "Content-Type";
    static final String LOG_ERROR_SOLICITUD = "Error en la solicitud. Código de respuesta: ";
    static final String LOG_ENVIO_CORRECTO = "Envío correcto";
    static final int HTTP_CODE_OK = 200;
    static long offset = 0;

    /**
     * Método que invocará al API de Telegram para ver si hay peticiones de usuarios pendientesd de procesar
     */
    public static void procesarPeticiones() {

        try {
            // Crear petición HTTP
            String apiUrl = API_TELEGRAM + BOT_TOKEN + "/getUpdates?offset=" + offset;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(METHOD_GET);
            conn.setRequestProperty(PROPERTY_ACCEPT, PROPERTY_APPLICATION_JSON);

            // Obtiene el código de respuesta
            int responseCode = conn.getResponseCode();
            // Si es un 200 la petición se ha realizado con éxito
            if (responseCode == HTTP_CODE_OK) {

                // Lee la respuesta JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parsea la respuesta
                Gson gson = new Gson();
                Respuesta respuesta = gson.fromJson(response.toString(), Respuesta.class);

                // Recorremos todas las peticiones y las procesamos
                for (Peticion peticion : respuesta.getResult()) {
                    procesarPeticion(peticion);
                }
            } else {
                System.out.println(LOG_ERROR_SOLICITUD + responseCode);
            }

            // Cierra la conexión
            conn.disconnect();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Método que evaluará una petición enviada por un usuario al bot
     *
     * @param peticion Datos de la petición del usuario
     * @throws IOException Excepción en envío de mensaje
     */
    public static void procesarPeticion(Peticion peticion) throws JAXBException, IOException {

        // Obtener datos del mensaje recibido

        long destinatario = 0;
        String texto = " ";
        try {

            destinatario = peticion.getMessage().getFrom().getId();
            texto = peticion.getMessage().getText();

        } catch (Exception exception) {

            System.out.println("ERROR. Comando no reconocido");
        }
        
        ArrayList<RecetasGSON> recetas = new ArrayList<RecetasGSON>();
        String mitos = " ";
        Envio envio = new Envio();
        if (texto != null) {
            //Creamos mensaje ayuda

            if (texto.equals("/ayuda")) {

                envio.setChat_id(destinatario);
                envio.setText("Las funcionalidades de las que dispongo son:" +
                        "\n /chiste --> El bot busca en un fichero de chistes y envía uno aleatorio cuando se le solicita." +
                        "\n /addChiste --> cuentale un chiste al bot y lo añadirá a su lista de chistes\n" +
                        "\n /receta --> El bot te muestra multiples recetas para crear un menú con sus tres platos. " +
                        "\n1. /primerPlato --> muestra una receta para elavorar un primer plato." +
                        "\n2. /segundoPlato --> muestra una receta para elavorar un segundo plato." +
                        "\n3. /postre --> muestra una receta para elavorar un postre.\n" +
                        "\n /mitosGriegos --> El bot lee un fichero y envía un mito Griego cuando se le solicita." +
                        "\n1. /mitoSisifo --> muestra el relato del mito del rey Sísifo" +
                        "\n2. /mitoPrometeo --> muestra el relato del mito del gigante Prometeo" +
                        "\n3. /mitoMedusa --> muestra el relato del mito de Medusa");
                enviarMensaje(envio);
            } else if (texto.equals("/start")) {
                envio.setChat_id(destinatario);
                envio.setText("Escribe /ayuda para ver las funcionalidades del bot.");
                enviarMensaje(envio);

            } else if (texto.equals("/chiste")) {

                ArrayList<Chistes> listaFinalChistes = new ArrayList<Chistes>();

                listaFinalChistes = contarChiste("C:\\Users\\Simon\\OneDrive\\Documentos\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\chistes.xml");

                int numeroChiste = (int) (Math.floor(Math.random() * (listaFinalChistes.size() - 0 + 1) + 0));

                String chiste = listaFinalChistes.get(numeroChiste).getTexto();


                envio.setChat_id(destinatario);
                envio.setText("Atento a este chiste:\n" + chiste);
                enviarMensaje(envio);

            } else if (texto.equals("/addChiste")) {

                envio.setChat_id(destinatario);
                envio.setText("Para introducir un chiste nuevo escribe /addChiste y a continuación el chiste.");
                enviarMensaje(envio);

            } else if (texto.matches("^/addChiste .*$")) {

                ArrayList<Chistes> listaFinalChistes = new ArrayList<Chistes>();
                String[] chiste = texto.split("/addChiste");


                listaFinalChistes = contarChiste("C:\\Users\\Simon\\OneDrive\\Documentos\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\chistes.xml");
                listaFinalChistes.add(new Chistes(chiste[1]));

                guardarChiste("C:\\Users\\Simon\\OneDrive\\Documentos\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram", listaFinalChistes);

                envio.setChat_id(destinatario);
                envio.setText("El chiste se guardo correctamente");
                enviarMensaje(envio);

            } else if (texto.equals("/receta")) {

                envio.setChat_id(destinatario);
                envio.setText("¿Sobre que quieres la receta?" +
                        "\n/primerPlato --> muestra una receta para elavorar un primer plato" +
                        "\n/segundoPlato --> muestra una receta para elavorar un segundo plato" +
                        "\n/postre --> muestra una receta para elavorar un postre");
                enviarMensaje(envio);

            } else if (texto.equals("/primerPlato")) {


                recetas = mostrarReceta("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\recetas.json");

                int numeroPlato = (int) (Math.floor(Math.random() * (recetas.size() - 0 + 1) + 0));
                String primerPLato = recetas.get(numeroPlato).getPrimerPlato();

                envio.setChat_id(destinatario);
                envio.setText("Para un buen primer plato tenemos: \n" + primerPLato);
                enviarMensaje(envio);

            } else if (texto.equals("/segundoPlato")) {

                recetas = mostrarReceta("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\recetas.json");

                int numeroPlato = (int) (Math.floor(Math.random() * (recetas.size() - 0 + 1) + 0));
                String segundoPlato = recetas.get(numeroPlato).getSegundoPlato();

                envio.setChat_id(destinatario);
                envio.setText("Para un buen segundo plato tenemos: \n" + segundoPlato);
                enviarMensaje(envio);


            } else if (texto.equals("/postre")) {

                recetas = mostrarReceta("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\recetas.json");

                int numeroPlato = (int) (Math.floor(Math.random() * (recetas.size() - 0 + 1) + 0));
                String postre = recetas.get(numeroPlato).getPostre();

                envio.setChat_id(destinatario);
                envio.setText("Para un buen postre tenemos: \n" + postre);
                enviarMensaje(envio);

            } else if (texto.equals("/mitosGriegos")) {

                envio.setChat_id(destinatario);
                envio.setText("¿Que mito quieres que te cuente?" +
                        "\n/mitoSisifo --> muestra el relato del mito del rey Sísifo" +
                        "\n/mitoPrometeo --> muestra el relato del mito del gigante Prometeo" +
                        "\n/mitoMedusa --> muestra el relato del mito de Medusa");
                enviarMensaje(envio);

            } else if (texto.equals("/mitoSisifo")) {

                mitos = mostrarMito("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\mitosGriegos\\mitoSisifo.txt");

                envio.setChat_id(destinatario);
                envio.setText(mitos);
                enviarMensaje(envio);

            } else if (texto.equals("/mitoPrometeo")) {

                mitos = mostrarMito("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\mitosGriegos\\mitoPrometeo.txt");

                envio.setChat_id(destinatario);
                envio.setText(mitos);
                enviarMensaje(envio);

            } else if (texto.equals("/mitoMedusa")) {

                mitos = mostrarMito("C:\\Users\\FP\\Documents\\GitHub\\SimonSoutoAN\\Acceso a Datos\\1ºEvaluación\\Practicas\\BotTelegram\\mitosGriegos\\mitoMedusa.txt");

                envio.setChat_id(destinatario);
                envio.setText(mitos);
                enviarMensaje(envio);
            } else {

                envio.setChat_id(destinatario);
                envio.setText("Este mensaje no se detecta como comando. Para ver los comandos introduce /ayuda");
                enviarMensaje(envio);
            }

        } else {

            envio.setChat_id(destinatario);
            envio.setText("ERROR. Formato de mensaje no valido. Introduce /ayuda");
            enviarMensaje(envio);
        }

        // Nos quedamos con el último mensaje procesado
        offset = peticion.getUpdate_id() + 1;
    }

    /**
     * Método que enviará un mensaje a un usuario
     *
     * @param envio Datos de destinatario del mensaje y mensaje a enviar
     * @throws IOException Excepción en envío de mensaje
     */
    public static void enviarMensaje(Envio envio) throws IOException {

        // Crear petición HTTP
        String apiUrl = API_TELEGRAM + BOT_TOKEN + "/sendMessage";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(METHOD_POST);
        conn.setRequestProperty(PROPERTY_CONTENT_TYPE, PROPERTY_APPLICATION_JSON);
        conn.setDoOutput(true);

        // Utilizar Gson para convertir el objeto JSON en una cadena
        String jsonMessage = new Gson().toJson(envio);

        // Crear un OutputStreamWriter para escribir en el OutputStream que se ligará a la conexión
        try (OutputStream os = conn.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            osw.write(jsonMessage);
            osw.flush();
        }

        // Enviar peticion HTTP
        int responseCode = conn.getResponseCode();

        // Evaluar respuesta petición HTTP
        if (responseCode == HTTP_CODE_OK) {
            System.out.println(LOG_ENVIO_CORRECTO);
        } else {
            System.out.println(LOG_ERROR_SOLICITUD + responseCode);
        }
    }

    /**
     * Método que enviará un chiste a un usuario
     * <p>
     *
     * @return chistes, Devolverá un ArrayList de objetos donde están todos los chistes
     * @throws JAXBException correlaciona clases Java y esquema XML
     */
    public static ArrayList<Chistes> contarChiste(String ruta) throws JAXBException {
        //Crear ruta del fihcero
        File file = new File(ruta);
        //Crear ArrayList para almacenar objetos
        ArrayList<Chistes> chistes = new ArrayList<Chistes>();
        //Leemos el fichero de chistes
        try {

            if (file.exists()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(ListaChistes.class);
                Unmarshaller jaxbUmarshaller = jaxbContext.createUnmarshaller();

                ListaChistes listaChistes = (ListaChistes) jaxbUmarshaller.unmarshal(file);
                if (listaChistes != null) {

                    chistes = listaChistes.getListaChistes();
                }

            } else {

                System.out.println("No se ha podido encontrar el fichero indicado");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return chistes;
    }

    /**
     * Método que guardará un chiste nuevo en el fichero XML
     * <p>
     *
     * @throws JAXBException correlaciona clases Java y esquema XML
     */
    public static void guardarChiste(String ruta, ArrayList<Chistes> arrayChistes) throws JAXBException, IOException {
        //Crear ruta del fihcero
        File file = new File(ruta);

        if (!file.exists()) {

            file.createNewFile();
        }

        JAXBContext context = JAXBContext.newInstance(ListaChistes.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ListaChistes chiste1 = new ListaChistes();
        chiste1.setListaChistes(arrayChistes);
        marshaller.marshal(chiste1, file);

    }

    /**
     * Método que enviará una receta a un usuario
     * <p>
     *
     * @return lista, Devolverá un ArrayList de objetos "Lista" edonde están todas las recetas. Las recetas varían entre Primer,Sgeundo plato y Postre
     * @throws FileNotFoundException xxcepción que se produce cuando hay un error al intentar acceder a un archivo que no existe en el disco.
     */

    public static ArrayList<RecetasGSON> mostrarReceta(String ruta) throws FileNotFoundException {
        //Crear ruta del fihcero
        FileReader fic = null;
        //Crear ArrayList para almacenar objetos
        ArrayList<RecetasGSON> lista = null;
        //Leemos el fichero de recetas
        try {

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            fic = new FileReader(ruta);
            RecetasGSON[] receta = gson.fromJson(fic, RecetasGSON[].class);
            lista = new ArrayList<>(Arrays.asList(receta));


        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (fic != null) fic.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return lista;
    }

    /**
     * Método que enviará un mito Griego a un usuario
     * <p>
     *
     * @return emviarMito, Devolverá un String con el contenido de uno de los archivos TXT que le pasemos
     */

    public static String mostrarMito(String ruta) {

        FileReader mitos = null;
        BufferedReader leerMitos = null;
        String enviarMito = " ";

        try {

            mitos = new FileReader(ruta);
            leerMitos = new BufferedReader(mitos);

            StringBuilder mostrarMitoGriego = new StringBuilder();
            enviarMito = leerMitos.readLine();


        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (mitos != null) mitos.close();
                if (leerMitos != null) leerMitos.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return enviarMito;
    }


}