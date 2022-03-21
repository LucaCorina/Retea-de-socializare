package socialnetwork.domain;

import java.util.Objects;

public class Utilizator extends Entitate<Long> {
    private String nume;
    private String prenume;
    //private final List<Utilizator> friends = new ArrayList<>();

    public Utilizator(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public Utilizator() {}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }



    @Override
    public String toString() {


        return "Utilizator{" +
                "id: " + super.getId()  +
                " ,nume: " + nume +
                ", prenume: " + prenume + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getNume().equals(that.getNume()) &&
                getPrenume().equals(that.getPrenume());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getPrenume());
    }
}