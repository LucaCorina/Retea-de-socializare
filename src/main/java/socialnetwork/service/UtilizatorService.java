package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;

    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public void addUtilizator(Utilizator utilizator) {
        repo.save(utilizator);
    }

    public void removeUtilizator(Long id) {
        repo.delete(id);

    }

    public void registerUser(Utilizator entity, String user_name, String parola, String salt) {repo.registerUser(entity, user_name, parola, salt);}

    public int validateLogin(String user_name, String parola) {return repo.validateLogin(user_name, parola);}

    public Utilizator findByUser_Name(String user_name) {return repo.findByUser_Name(user_name);}

    public void updateUtilizator(Utilizator utilizator) {
        repo.update(utilizator);
    }

    public Utilizator findOne(Long id) {
        return repo.findOne(id);
    }

    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    public Utilizator findByName(String firstName, String lastName) {
        for(Utilizator utilizator : repo.findAll())
        {
            if(utilizator.getNume().equals(firstName) && utilizator.getPrenume().equals(lastName))
                return utilizator;
        }
        return null;
    }
}
