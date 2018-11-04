package fitchat.com.fitchat;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;

public class Message implements Comparable<Message> {
    private String text;
    private Date date;

    public Message(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Message() {}


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + "\n" + text;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Message))
            return false;
        Message that = (Message) other;
        return this.date.equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return date.hashCode() + text.hashCode();
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return this.getDate().compareTo(message.getDate());
    }
}
