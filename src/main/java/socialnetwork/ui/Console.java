package socialnetwork.ui;
import socialnetwork.domain.*;
import socialnetwork.service.*;

import java.security.KeyException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

//am modificat ceva de test test2
public class Console {
    private final UtilizatorService utilizatorService;
    private final PrietenieService prietenieService;
    private final MesajService mesajService;
    private final UtilizatoriPrieteniiService utilizatoriPrieteniiService;
    private final CererePrietenieService cererePrietenieService;

    public Console(UtilizatorService utilizatorService, PrietenieService prietenieService,
                   UtilizatoriPrieteniiService utilizatoriPrieteniiService, MesajService mesajService, CererePrietenieService cererePrietenieService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.utilizatoriPrieteniiService = utilizatoriPrieteniiService;
        this.mesajService = mesajService;
        this.cererePrietenieService = cererePrietenieService;
    }

    public void run_meniu_CRUD_Util() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Adauga--utilizator");
            System.out.println("2. Sterge--utilizator");
            System.out.println("3. Update--utilizator");
            System.out.println("4. Get--all--utilizatori");
            System.out.println("5. Prietenii utilizator");
            System.out.println("6. Prietenii utilizator luna");
            System.out.println("b. Back");

            System.out.println("Optiune ");
            String optiune;
            optiune = scanner.next();
            if (Objects.equals(optiune, "1")) {
                try {
                    System.out.println("Nume ");
                    String firstName = scanner.next();
                    System.out.println("Prenume ");
                    String lastName = scanner.next();
                    Utilizator utilizator = new Utilizator(firstName, lastName);
                    this.utilizatorService.addUtilizator(utilizator);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "2")) {
                try {
                    System.out.println("Dati id-ul utilizatoruli pe care vrem sa l stergem: ");
                    Long id = scanner.nextLong();
                    //this.utilizatorService.removeUtilizator(id);
                    utilizatoriPrieteniiService.removeUtilizatorAndPrieteniiUtilizator(id);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "3")) {
                try {
                    System.out.println("Id-ul utilizatorului de updatat: ");
                    Long id = scanner.nextLong();
                    System.out.println("Nume ");
                    String firstName = scanner.next();
                    System.out.println("Prenume ");
                    String lastName = scanner.next();
                    Utilizator utilizator = new Utilizator(firstName, lastName);
                    utilizator.setId(id);
                    this.utilizatorService.updateUtilizator(utilizator);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "4")) {
                try {
                    this.utilizatorService.getAll().forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "5")) {
                try {
                    System.out.println("Nume ");
                    String firstName = scanner.next();
                    System.out.println("Prenume ");
                    String lastName = scanner.next();
                    this.utilizatoriPrieteniiService.prieteniiUtilizator(firstName, lastName).forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "6")) {
                try {
                    System.out.println("Nume ");
                    String firstName = scanner.next();
                    System.out.println("Prenume ");
                    String lastName = scanner.next();
                    System.out.println("Luna ");
                    String luna = scanner.next();
                    this.utilizatoriPrieteniiService.prieteniiUtilizatorDinLuna(firstName, lastName, luna).forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "b")) {
                break;
            } else {
                System.out.println("Optiune invalida! Reincercati.");
            }
        }
    }

    public void run_meniu_CRUD_Prietenie() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Adauga--prietenie");
            System.out.println("2. Sterge--prietenie");
            System.out.println("3. Update--prietenie");
            System.out.println("4. Get--all--prietenie");
            System.out.println("5. Afisare--nr--de--comunitati");
            System.out.println("6. Afisare cea mai sociabila comunitate");
            System.out.println("b. Back");


            //sa
            System.out.println("Optiune ");
            String optiune;
            optiune = scanner.next();
            if (Objects.equals(optiune, "1")) {
                try {
                    System.out.println("Id1: ");
                    Long id1 = scanner.nextLong();
                    Utilizator utilizator = utilizatorService.findOne(id1);

                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Id2: ");
                    Long id2 = scanner.nextLong();
                    utilizator = utilizatorService.findOne(id2);
                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");
                    this.prietenieService.AdaugaPrietenie(new Prietenie(id1, id2, LocalDateTime.now()));
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "2")) {
                try {
                    System.out.println("Dati id-ul prieteniei de sters: ");
                    //

                    long id1 = scanner.nextLong();
                    long id2 = scanner.nextLong();
                    this.prietenieService.StergePrietenie(id1, id2);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "3")) {
                try {
                    System.out.println("Id1: ");
                    Long id1 = scanner.nextLong();
                    System.out.println("Id2: ");
                    Long id2 = scanner.nextLong();
                    this.prietenieService.updatePrietenie(new Prietenie(id1, id2, LocalDateTime.now()));
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "4")) {
                try {
                    this.prietenieService.getAll().forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "5")) {
                try {

                    System.out.println("Numarul de componete conexe:  " + prietenieService.numarComponenteConexe());
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "6")) {
                try {
                    System.out.println("Cea mai sociabila comunitate e: ");
                    System.out.println(prietenieService.celMaiLungDrum());
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "b")) {
                break;
            } else {
                System.out.println("Optiune invalida. Reincercati!");
            }
        }
    }

    public void run_meniu_CRD_Mesaje() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Adauga mesaj");
            System.out.println("2. Sterge mesaj");
            System.out.println("3. Get all mesaj");
            System.out.println("4. Afisare mesaje 2 utilizatori");
            System.out.println("b. Back");

            System.out.println("Optiune ");
            String optiune;
            optiune = scanner.next();

            if (Objects.equals(optiune, "1")) {
                try {
                    System.out.println("Id Sender: ");
                    Long idS = scanner.nextLong();
                    Utilizator sender = utilizatorService.findOne(idS);
                    if (sender == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Id Reciever: ");
                    Long idR = scanner.nextLong();
                    Utilizator reciever = utilizatorService.findOne(idR);
                    if (reciever == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");
                    List<Utilizator> listRecieve = new ArrayList<>();
                    listRecieve.add(utilizatorService.findOne(idR));

                    System.out.println("Mesaj de trimis");
                    String mesajul = scanner.next();

                    System.out.println("? Reply to: ");
                    Long idmesajreply = scanner.nextLong();
                    if (idmesajreply != 0L) {
                        if (!Objects.equals(mesajService.findOne(idmesajreply).getSender().getId(), idS))
                            throw new KeyException("Acest mesaj nu a fost trimis de utilizatorul" + idS + " aka " + utilizatorService.findOne(idS).getNume());
                    }

                    Date date = new Date();
                    this.mesajService.addMesaj(new Mesaj(sender, listRecieve, mesajul, new Timestamp(date.getTime()), idmesajreply));
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "2")) {
                try {
                    System.out.println("Dati id-ul mesajului de sters: ");
                    Long id = scanner.nextLong();
                    this.mesajService.removeMesaj(id);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "3")) {
                try {
                    this.mesajService.getAll().forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "4")) {
                try {
                    System.out.println("Id Utilizator 1: ");
                    Long id1 = scanner.nextLong();
                    if (utilizatorService.findOne(id1) == null)
                        throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Id Utilizator 2: ");
                    Long id2 = scanner.nextLong();
                    if (utilizatorService.findOne(id2) == null)
                        throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    this.mesajService.Conversation_History(id1, id2).forEach(System.out::println);


                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "b")) {
                break;
            } else {
                System.out.println("Optiune invalida. Reincercati!");
            }
        }
    }

    public void run_meniu_CRD_Cereri() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Trimite cerere");
            System.out.println("2. Sterge cerere");
            System.out.println("3. Get all cereri");
            System.out.println("4. Get all cereri trimise by user");
            System.out.println("5. Get all cereri primite by user");
            System.out.println("6. Accept / Decline cerere");
            System.out.println("b. Back");

            System.out.println("Optiune ");
            String optiune;
            optiune = scanner.next();

            if (Objects.equals(optiune, "1")) {
                try {
                    System.out.println("Id care trimite: ");
                    Long id1 = scanner.nextLong();
                    Utilizator utilizator = utilizatorService.findOne(id1);

                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Id care primeste: ");
                    Long id2 = scanner.nextLong();
                    Utilizator utilizator2 = utilizatorService.findOne(id2);
                    if (utilizator2 == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    if (prietenieService.findOne(id1, id2) == null) {
                        this.cererePrietenieService.AdaugaCerere(new Cerere(id1, id2, "Pending"));
                    } else throw new KeyException("Prietenia exista deja intre cei 2 utilizatori");
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "2")) {
                try {
                    System.out.println("Dati id-ul cererii de sters (id_sender + pe linie noua, id_reciever): ");
                    Long id1 = scanner.nextLong();
                    Long id2 = scanner.nextLong();
                    this.cererePrietenieService.StergeCerere(new Tuple<>(id1, id2));
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "3")) {
                try {
                    this.cererePrietenieService.getAll().forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "4")) {
                try {
                    System.out.println("Id sender: ");
                    Long id1 = scanner.nextLong();
                    Utilizator utilizator = utilizatorService.findOne(id1);
                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    this.cererePrietenieService.getAllSentByUser(id1).forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "5")) {
                try {
                    System.out.println("Id reciever: ");
                    Long id1 = scanner.nextLong();
                    Utilizator utilizator = utilizatorService.findOne(id1);
                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    this.cererePrietenieService.getAllRecievedByUser(id1).forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "6")) {
                try {
                    cererePrietenieService.getAll().forEach(System.out::println);
                    System.out.println("\n");
                    System.out.println("Id_sender: ");
                    Long id1 = scanner.nextLong();
                    Utilizator utilizator = utilizatorService.findOne(id1);
                    if (utilizator == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Id_reciever: ");
                    Long id2 = scanner.nextLong();
                    Utilizator utilizator2 = utilizatorService.findOne(id2);
                    if (utilizator2 == null) throw new KeyException("Nu exista utilizatorul cu id-ul acesta");

                    System.out.println("Accept / Decline ?");
                    String status = scanner.next();

                    cererePrietenieService.AcceptDecline(new Cerere(id1, id2, "Pending"), status);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(optiune, "b")) {
                break;
            } else {
                System.out.println("Optiune invalida. Reincercati!");
            }
        }
    }


    public void run_console() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. CRUD--Utilizator");
            System.out.println("2. CRUD--Prietenie");
            System.out.println("3. CRD--Mesaje");
            System.out.println("4. CRD--Cereri");

            System.out.println("x. Stop/Exit");

            System.out.println("Optiune  ");
            String optiune;
            optiune = scanner.next();
            if (Objects.equals(optiune, "1")) {
                run_meniu_CRUD_Util();
            } else if (Objects.equals(optiune, "2")) {
                run_meniu_CRUD_Prietenie();
            } else if (Objects.equals(optiune, "3")) {
                run_meniu_CRD_Mesaje();
            } else if (Objects.equals(optiune, "4")) {
                run_meniu_CRD_Cereri();
            } else if (Objects.equals(optiune, "x")) {
                return;
            } else {
                System.out.println("Optiune invalida. Reincercati!");
            }
        }
    }

    public void run_friendship_user_options(Utilizator u){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Search for people :D");
            System.out.println("2. See your friendships");
            System.out.println("3. See your friendships from a certain month");
            System.out.println("4. Delete a friendship");
            System.out.println("5. Send a friend request");
            System.out.println("6. Delete a friend request you sent");
            System.out.println("7. Accept / Decline incoming requests");
            System.out.println("8. See all your sent requests");
            System.out.println("9. See all your recieved requests");//
            System.out.println("b. Back");

            System.out.println("Optiune ");
            String optiune;
            optiune = scanner.next();

            if (Objects.equals(optiune, "1")) {utilizatoriPrieteniiService.seeNewPeople(u.getId()).forEach(System.out::println);}
            else if (Objects.equals(optiune, "2")) {utilizatoriPrieteniiService.prieteniiUtilizator(u.getNume(), u.getPrenume()).forEach(System.out::println);}
            else if (Objects.equals(optiune, "3")) {
                System.out.println("Luna ");
                String luna = scanner.next();
                utilizatoriPrieteniiService.prieteniiUtilizatorDinLuna(u.getNume(), u.getPrenume(), luna).forEach(System.out::println);}
            else if (Objects.equals(optiune, "4")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(prietenieService.findOne(u.getId(), utilizatorService.findByName(nume, prenume).getId())!=null){
                prietenieService.StergePrietenie(u.getId(), utilizatorService.findByName(nume, prenume).getId());System.out.println("\nPrietenie stearsa cu succes\n");}
                else if(prietenieService.findOne(utilizatorService.findByName(nume, prenume).getId(), u.getId())!=null){
                prietenieService.StergePrietenie(utilizatorService.findByName(nume, prenume).getId(), u.getId());System.out.println("\nPrietenie stearsa cu succes\n");}
                else {System.out.println("\nNu sunteti prieten cu acest utilizator\n");}}
            else if (Objects.equals(optiune, "5")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(utilizatorService.findByName(nume, prenume)==null){System.out.println("\nNu exista acest utilizator\n");}
                else if(cererePrietenieService.findOne(utilizatorService.findByName(nume, prenume).getId(), u.getId())!=null
                        || cererePrietenieService.findOne(u.getId(), utilizatorService.findByName(nume, prenume).getId())!=null){
                    System.out.println("\nExista deja o cerere in pending intre tine si acest user\n");}
                else if(prietenieService.findOne(u.getId(), utilizatorService.findByName(nume, prenume).getId())!=null
                || prietenieService.findOne(utilizatorService.findByName(nume, prenume).getId(), u.getId())!=null){System.out.println("\nEsti deja prieten cu acest user\n");}
                else{cererePrietenieService.AdaugaCerere(new Cerere(u.getId(), utilizatorService.findByName(nume, prenume).getId(), "pending"));}}
            else if (Objects.equals(optiune, "6")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(utilizatorService.findByName(nume, prenume)==null){System.out.println("\nNu exista acest utilizator\n");}
                else if(cererePrietenieService.findOne(u.getId(), utilizatorService.findByName(nume, prenume).getId())!=null){cererePrietenieService.StergeCerere(new Tuple<>(u.getId(), utilizatorService.findByName(nume, prenume).getId())); System.out.println("\nCererea trimisa de tine catre acest user a fost stearsa cu succes\n");}
                else{System.out.println("\nNu exista cerere de prietenie trimisa acestui user\n");}}
            else if (Objects.equals(optiune, "7")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(utilizatorService.findByName(nume, prenume)==null){System.out.println("\nNu exista acest utilizator\n");}
                else if(cererePrietenieService.findOne(utilizatorService.findByName(nume, prenume).getId(), u.getId())!=null){
                    System.out.println("Accept / Decline");
                    String status = scanner.next();
                    if(status.equals("Accept") || status.equals("Decline")){
                    cererePrietenieService.AcceptDecline(new Cerere(utilizatorService.findByName(nume, prenume).getId(), u.getId(), "Pending"), status);}
                    else{System.out.println("\nInvalid option. Choose Accept or Decline\n");}}
                else{System.out.println("\nAcest user nu ti-a trimis nicio cerere de prietenie\n");}}
            else if (Objects.equals(optiune, "8")) {cererePrietenieService.getAllSentByUser(u.getId()).forEach(System.out::println);}
            else if (Objects.equals(optiune, "9")) {cererePrietenieService.getAllRecievedByUser(u.getId()).forEach(System.out::println);}
            else if (Objects.equals(optiune, "b")) {break;}
            else{System.out.println("\nInvalid option\n");}
            }
        }

    public  void run_mesaje_user_options(Utilizator u){
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.println("1. Send a message");
            System.out.println("2. See all your messages with another user");
            System.out.println("b. Back");

            System.out.println("Optiune ");
            String optiune;
            Date date = new Date();
            optiune = scanner.next();

            if (Objects.equals(optiune, "1")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(utilizatorService.findByName(nume, prenume)==null){System.out.println("\nNu exista acest utilizator\n");}
                else{List<Utilizator> listRecieve = new ArrayList<>();
                    listRecieve.add(utilizatorService.findByName(nume, prenume));
                    System.out.println("Mesaj de trimis: ");
                    String mesaj = scanner.next();
                    System.out.println("Replying to which message ID? (experimental feature, needs further implementation. INPUT 0 IF NOT REPLYING): ");
                    Long replyTo = scanner.nextLong();
                    mesajService.addMesaj(new Mesaj(u, listRecieve, mesaj, new Timestamp(date.getTime()), replyTo));}
            }

            else if (Objects.equals(optiune, "2")) {
                System.out.println("Numele: ");
                String nume = scanner.next();
                System.out.println("Prenumele: ");
                String prenume = scanner.next();
                if(nume.equals(u.getNume()) && prenume.equals(u.getPrenume())){System.out.println("Userul introdus esti tu xD");}
                else if(utilizatorService.findByName(nume, prenume)==null){System.out.println("\nNu exista acest utilizator\n");}
                else{this.mesajService.Conversation_History(u.getId(), utilizatorService.findByName(nume, prenume).getId()).forEach(System.out::println);}
            }

            else if (Objects.equals(optiune, "b")) {break;}

            else{System.out.println("\nInvalid option\n");}
        }
    }

    public void run_utilizator_menu() {
        Scanner scanner = new Scanner(System.in);
        String username, parola, optiune;
        Utilizator current_user;
        while (true) {
            System.out.println("\nIntrodu username si parola: \n");
            System.out.println("Username: ");
            username = scanner.next();
            System.out.println("\nParola: ");
            parola = scanner.next();

            if (utilizatorService.validateLogin(username, parola)!=1){
                System.out.println("Date Invalide\n");}
            else if(utilizatorService.findByUser_Name(username)==null){
                System.out.println("User nu exista, reintrodu datele de login\n");
            } else {
                current_user = utilizatorService.findByUser_Name(username);
                System.out.println("\n------Welcome, " + current_user.getNume() + " " + current_user.getPrenume() + " ! ------\n");
                while (true) {
                    System.out.println("1. Friendships");
                    System.out.println("2. Messages");
                    System.out.println("x. Logout");
                    optiune = scanner.next();
                    if (Objects.equals(optiune, "1")) {
                    run_friendship_user_options(current_user);
                    } else if (Objects.equals(optiune, "2")) {
                    run_mesaje_user_options(current_user);
                    } else if (Objects.equals(optiune, "x")) {
                        System.out.println("\n------Farewell, " + current_user.getNume() + " " + current_user.getPrenume() + " ! ------\n");
                        break;
                    } else {
                        System.out.println("Optiune invalida. Reincercati!");
                    }
                }
            }
            System.out.println("\nEnter x now to close program. Otherwise return to login\n");
            optiune = scanner.next();
            if(optiune.equals("x")){break;}
        }
    }
}
