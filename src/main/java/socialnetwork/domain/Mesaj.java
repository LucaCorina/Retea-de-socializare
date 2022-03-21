package socialnetwork.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mesaj extends Entitate<Long>{
    private Utilizator sender;
    private List<Utilizator> recieveri = new ArrayList<>();
    private String mesaj;
    private Timestamp data;
    private Long isReply;

    public Mesaj() {}

    public Mesaj(Utilizator sender, List<Utilizator> recieveri, String mesaj, Timestamp data, Long isReply) {
        this.sender = sender;
        this.recieveri = recieveri;
        this.mesaj = mesaj;
        this.data = data;
        this.isReply = isReply;
    }

    public void setSender(Utilizator sender) {
        this.sender = sender;
    }

    public void setRecieveri(List<Utilizator> recieveri) {
        this.recieveri = recieveri;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public void setBoolean(Long isReply) {
        this.isReply = isReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesaj mesaj1 = (Mesaj) o;
        return Objects.equals(isReply, mesaj1.isReply) && Objects.equals(sender, mesaj1.sender) && Objects.equals(recieveri, mesaj1.recieveri) && Objects.equals(mesaj, mesaj1.mesaj) && Objects.equals(data, mesaj1.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recieveri, mesaj, data, isReply);
    }

    @Override
    public String toString() {
        return "Mesaj{" +
                "id= " + super.getId() +
                "sender= " + sender +
                ", recieveri= " + recieveri +
                ", mesaj= " + mesaj +
                ", data= " + data +
                ", isReply= " + isReply +
                '}';
    }

    public void setReply(Long reply) {
        isReply = reply;
    }

    public Utilizator getSender() {
        return sender;
    }

    public List<Utilizator> getRecieveri() {
        return recieveri;
    }

    public String getMesaj() {
        return mesaj;
    }

    public Timestamp getData() {
        return data;
    }

    public Long isReply() {
        return isReply;
    }
}
