package socialnetwork.service;

import socialnetwork.domain.Cerere;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CererePrietenieService {
    private final Repository<Tuple<Long, Long>, Cerere> repo;
    private final PrietenieService prietenieService;


    public CererePrietenieService(Repository<Tuple<Long, Long>, Cerere> repo, PrietenieService prietenieService) {
        this.repo = repo;
        this.prietenieService = prietenieService;
    }

    public void AdaugaCerere(Cerere cerere) {
        repo.save(cerere);
    }

    public void StergeCerere(Tuple<Long, Long> id) {
        repo.delete(id);
    }

    public Iterable<Cerere> getAll() {
        return repo.findAll();
    }

    public Cerere findOne(Long id1, Long id2) {return repo.findOne(new Tuple<>(id1, id2));}

    public void AcceptDecline(Cerere cerere, String status) {
        if (status.equals("Accept")) {
            prietenieService.AdaugaPrietenie(new Prietenie(cerere.getId_sender(), cerere.getId_reciever(), LocalDateTime.now()));
            repo.delete(new Tuple<>(cerere.getId_sender(), cerere.getId_reciever()));
        }
        if (status.equals("Decline")) {
            repo.delete(new Tuple<>(cerere.getId_sender(), cerere.getId_reciever()));
        }
    }

    public Iterable<Cerere> getAllSentByUser(Long id_user) {
        Set<Cerere> cereri = new HashSet<>();
        for(Cerere cerere : repo.findAll())
        {if(cerere.getId_sender().equals(id_user)) {cereri.add(cerere);}}

        return cereri;
    }

    public Iterable<Cerere> getAllRecievedByUser(Long id_user) {
        Set<Cerere> cereri = new HashSet<>();
        for(Cerere cerere : repo.findAll())
        {if(cerere.getId_reciever().equals(id_user)) {cereri.add(cerere);}}

        return cereri;
    }


}
