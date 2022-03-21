package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class PrietenieService {
    private final Repository<Tuple<Long, Long>, Prietenie> repo;

    public PrietenieService(Repository<Tuple<Long, Long>, Prietenie> repo) {
        this.repo = repo;
    }

    public void AdaugaPrietenie(Prietenie messageTask) {
        repo.save(messageTask);
    }

    public void StergePrietenie(long l, long l1) {
        this.repo.delete(new Tuple<>(l, l1));
    }

    public void updatePrietenie(Prietenie prietenie) {
        repo.update(prietenie);
    }

    public Iterable<Prietenie> getAll() {
        return repo.findAll();
    }

    public Prietenie findOne(Long id1, Long id2) {return repo.findOne(new Tuple<>(id1, id2));}

    public int numarComponenteConexe() {
        return dprietenii().getL();
    }

    public List<Prietenie> celMaiLungDrum() {
        return dprietenii().getR();
    }

    public void removePreteniiIfUserIsDeleted(Long id){
        List<Prietenie> prietenieList = new ArrayList<>();
        for (Prietenie prietenie: this.repo.findAll()) {
            if(Objects.equals(prietenie.getId().getL(), id) || Objects.equals(prietenie.getId().getR(), id)){
                prietenieList.add(prietenie);
            }
        }
        for (Prietenie prietenie: prietenieList) {
            this.repo.delete(prietenie.getId());
        }
    }

    private Tuple<Integer, List<Prietenie>> dprietenii() {

        //recursiv, in f pune toate prieteniile in repo,
        //se verif fiecare iterator pe rand-verif daca exista sau nu
        //      nr comp conexe=setat la 1
        // avem 3 liste
        //se ia 1 prietenie in iterator se fac componentele conexe
        //verif-toate comp conexe care s legate de first,le pun intr un sir
        //lverif=pun verif
        Iterable<Prietenie> prietenii = repo.findAll();
        boolean isEmpty = !prietenii.iterator().hasNext();
        if (isEmpty) {

            return new Tuple<>(0, new ArrayList<>());
        }
        int nrCompConexe = 1;
        List<Prietenie> verificate = dprieteniiwrec(prietenii.iterator().next(), prietenii);
        List<Prietenie> longverif = verificate;
        List<Prietenie> globalVisited = new ArrayList<>(verificate);
        for (Prietenie prietenie : prietenii) {
            if (!globalVisited.contains(prietenie)) {
                verificate = dprieteniiwrec(prietenie, prietenii);
                if (verificate.size() > longverif.size()) {
                    longverif = verificate;
                }
                globalVisited.addAll(verificate);
                nrCompConexe++;
            }
        }
        return new Tuple<>(nrCompConexe, longverif);
    }

    private List<Prietenie> dprieteniiwrec(Prietenie first, Iterable<Prietenie> prietenii) {
        Stack<Prietenie> stack = new Stack<>();
        List<Prietenie> visited = new ArrayList<>();
        stack.push(first);
        while (!stack.isEmpty()) {
            Prietenie current = stack.pop();
            Long left = current.getId().getL();
            Long right = current.getId().getR();
            if (!visited.contains(current)) {
                visited.add(current);
                for (Prietenie prietenie : prietenii) {
                    Long left1 = prietenie.getId().getL();
                    if (!visited.contains(prietenie) && (Objects.equals(left1, right) || Objects.equals(left1, left))) {
                        stack.push(prietenie);
                    }
                }
            }
        }
        return visited;
    }
}