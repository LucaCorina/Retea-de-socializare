package socialnetwork.domain;

import java.util.Objects;

public class Cerere extends Entitate<Tuple<Long, Long>>{
    private Long id_sender;
    private Long id_reciever;
    private String status;

    public Cerere() {}

    public Cerere(Long id_sender, Long id_reciever, String status) {
        this.id_sender = id_sender;
        this.id_reciever = id_reciever;
        this.status = status;
    }

    public Long getId_sender() {
        return id_sender;
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public Long getId_reciever() {
        return id_reciever;
    }

    public void setId_reciever(Long id_reciever) {
        this.id_reciever = id_reciever;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cerere cerere = (Cerere) o;
        return Objects.equals(id_sender, cerere.id_sender) && Objects.equals(id_reciever, cerere.id_reciever) && Objects.equals(status, cerere.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_sender, id_reciever, status);
    }

    @Override
    public String toString() {
        return "Cerere{" +
                "id_sender= " + id_sender +
                ", id_reciever= " + id_reciever +
                ", status= '" + status + '\'' +
                '}';
    }
}
