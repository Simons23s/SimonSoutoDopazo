package org.example.entidades;

/**
 * Clase Mensaje donde se definen todos sus atributos
 * Se usará para los id de cada mensaje y saber remitente y el chat
 *
 * @author Hugo Díaz
 * @version 1.0, 04/10/2023
 */
public class Mensaje {

    private int message_id;
    private Remitente from;
    private Chat chat;
    private long date;
    private String text;

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public Remitente getFrom() {
        return from;
    }

    public void setFrom(Remitente from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "message_id=" + message_id +
                ", from=" + from +
                ", chat=" + chat +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
