package jdbc.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Letter implements Serializable {
    private Integer id;
    private User sender;
    private User receiver;
    private String text;
    private String theme;
    private Timestamp sendDate;

    public Letter() {
    }

    public Letter(int id) {
        this.id = id;
    }

    public Letter(User sender, User receiver, String text, String theme, Timestamp sendDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.theme = theme;
        this.sendDate = sendDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter = (Letter) o;
        return Objects.equals(id, letter.id) && Objects.equals(sender, letter.sender) && Objects.equals(receiver, letter.receiver) && Objects.equals(text, letter.text) && Objects.equals(theme, letter.theme) && Objects.equals(sendDate, letter.sendDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, text, theme, sendDate);
    }

    @Override
    public String toString() {
        return "Letter{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", text='" + text + '\'' +
                ", theme='" + theme + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}