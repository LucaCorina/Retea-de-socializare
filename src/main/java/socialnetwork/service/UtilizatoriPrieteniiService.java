package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class UtilizatoriPrieteniiService {
    private final UtilizatorService utilizatorService;
    private final PrietenieService prietenieService;

    public UtilizatoriPrieteniiService(UtilizatorService utilizatorService, PrietenieService prietenieService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
    }

    public void removeUtilizatorAndPrieteniiUtilizator(Long id){
        this.utilizatorService.removeUtilizator(id);
        this.prietenieService.removePreteniiIfUserIsDeleted(id);
    }

    public List<Tuple<Long, LocalDateTime>> prieteniiUtilizator(String firstName, String lastName){
        Utilizator utilizator = utilizatorService.findByName(firstName, lastName);
        Iterable<Prietenie> prietenii = prietenieService.getAll();
        return StreamSupport.stream(prietenii.spliterator(), false)
                .filter(prietenie -> (Objects.equals(prietenie.getId().getL(), utilizator.getId())
                        || Objects.equals(prietenie.getId().getR(), utilizator.getId())))
                .map(prietenie -> {
                    Long left = prietenie.getId().getL();
                    Long right = prietenie.getId().getR();
                    Long idUser = utilizator.getId();
                    Tuple<Long, LocalDateTime> tuple;
                    if (!Objects.equals(left, idUser))
                        tuple = new Tuple<>(left, prietenie.getDate());
                    else
                        tuple = new Tuple<>(right, prietenie.getDate());
                    return tuple;}
                )
                .collect(toList());
    }

    public List<Tuple<Long, LocalDateTime>> prieteniiUtilizatorDinLuna(String firstName, String lastName, String luna){
        Utilizator utilizator = utilizatorService.findByName(firstName, lastName);
        Iterable<Prietenie> prietenii = prietenieService.getAll();

        return StreamSupport.stream(prietenii.spliterator(), false)
                .filter(prietenie -> (Objects.equals(prietenie.getId().getL(), utilizator.getId())
                        || Objects.equals(prietenie.getId().getR(), utilizator.getId()))
                        && prietenie.getDate().getMonth() == Month.valueOf(luna.toUpperCase()))
                .map(prietenie -> {
                    Long left = prietenie.getId().getL();
                    Long right = prietenie.getId().getR();
                    Long idUser = utilizator.getId();
                    Tuple<Long, LocalDateTime> tuple;
                    if (!Objects.equals(left, idUser))
                        tuple = new Tuple<>(left, prietenie.getDate());
                    else
                        tuple = new Tuple<>(right, prietenie.getDate());
                    return tuple;
                })
                .collect(toList());
    }

    public List<Tuple<Utilizator, String>> seeNewPeople(Long id){
        Iterable<Prietenie> prietenii = prietenieService.getAll();
        Iterable<Utilizator> utilizatori = utilizatorService.getAll();

        return StreamSupport.stream(utilizatori.spliterator(), false).filter(utilizator -> (!Objects.equals(utilizator.getId(), id)))
                .map(utilizator -> {
                    Tuple<Utilizator, String> tuple;
                    if(prietenieService.findOne(utilizator.getId(), id)!=null || prietenieService.findOne(id, utilizator.getId())!=null){
                    tuple = new Tuple<>(utilizator, " You are already friend with this user");}
                    else{tuple = new Tuple<>(utilizator, " You are not friend with this user");}

                    return tuple;
                })
                .collect(toList());
    }
}
