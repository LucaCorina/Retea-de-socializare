package socialnetwork;

import javafx.application.Application;
import javafx.stage.Stage;
import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.Repository;
import socialnetwork.repository.db.RepositoryDbCerere;
import socialnetwork.repository.db.RepositoryDbMesaj;
import socialnetwork.repository.db.RepositoryDbPrietenie;
import socialnetwork.repository.db.RepositoryDbUtilizator;
import socialnetwork.service.*;

public class Main extends Application{

    public void start(Stage stage) throws Exception {
        loginController lC = new loginController();
        lC.start(new Stage());
    }

    public static void main(String[] args){
        final String url = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.url");
        final String username = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.username");
        final String password = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.password");


        Validator<Utilizator> validatorUtilizator = new UtilizatorValidator();
        Validator<Prietenie> validatorPrietenie = new PrietenieValidator();
        Validator<Mesaj> mesajValidator = new MesajValidator();
        Validator<Cerere> cerereValidator = new CerereValidator();


        Repository<Long, Utilizator> userDbRepository = new RepositoryDbUtilizator(url, username, password, validatorUtilizator);
        Repository<Tuple<Long, Long>, Prietenie> prietenieDbRepository = new RepositoryDbPrietenie(url, username, password, validatorPrietenie);
        RepositoryDbMesaj mesajDbRepository = new RepositoryDbMesaj(url, username, password, mesajValidator, userDbRepository);
        RepositoryDbCerere cerereDbRepository = new RepositoryDbCerere(url, username, password, cerereValidator);

        UtilizatorService utilizatorService = new UtilizatorService(userDbRepository);
        PrietenieService prietenieService = new PrietenieService(prietenieDbRepository);
        UtilizatoriPrieteniiService utilizatoriPrieteniiService = new UtilizatoriPrieteniiService(utilizatorService, prietenieService);
        MesajService mesajService = new MesajService(mesajDbRepository);
        CererePrietenieService cererePrietenieService = new CererePrietenieService(cerereDbRepository, prietenieService);

        //Console console = new Console(utilizatorService, prietenieService, utilizatoriPrieteniiService, mesajService, cererePrietenieService);
        //console.run_utilizator_menu();
        loginController.utilizatorService=utilizatorService;
        loginController.utilizatoriPrieteniiService=utilizatoriPrieteniiService;
        loginController.cererePrietenieService=cererePrietenieService;
        loginController.mesajService=mesajService;
        loginController.prietenieService=prietenieService;
        launch();

    }
}