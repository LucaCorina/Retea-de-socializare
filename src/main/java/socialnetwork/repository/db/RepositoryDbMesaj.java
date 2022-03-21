package socialnetwork.repository.db;

import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositoryDbMesaj implements Repository<Long, Mesaj> {
    private String url;
    private String username;
    private String password;
    private Validator<Mesaj> validator;
    private final Repository<Long, Utilizator> repoUtilizator;

    public RepositoryDbMesaj(String url, String username, String password, Validator<Mesaj> validatorMesaj, Repository<Long, Utilizator> repo) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        this.repoUtilizator = repo;
    }

    @Override
    public Mesaj findOne(Long id) {
        String sql = "select * from mesaje where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
            Long id1 = resultSet.getLong("id");
            Utilizator sender = repoUtilizator.findOne(resultSet.getLong("id_sender"));
            List<Utilizator> reciever = new ArrayList<>();
            reciever.add(repoUtilizator.findOne(resultSet.getLong("id_reciever")));
            String mesajul = resultSet.getString("mesaj");
            Timestamp data = resultSet.getTimestamp("data");
            Long isReply = resultSet.getLong("isreply");

            Mesaj mesaj = new Mesaj(sender, reciever, mesajul, data, isReply);
            mesaj.setId(id1);
            return mesaj;}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Mesaj> findAll() {
        Set<Mesaj> mesaje = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Utilizator sender = repoUtilizator.findOne(resultSet.getLong("id_sender"));
                List<Utilizator> reciever = new ArrayList<>();
                reciever.add(repoUtilizator.findOne(resultSet.getLong("id_reciever")));
                String mesajul = resultSet.getString("mesaj");
                Timestamp data = resultSet.getTimestamp("data");
                Long isReply = resultSet.getLong("isreply");

                Mesaj mesaj = new Mesaj(sender, reciever, mesajul, data, isReply);
                mesaj.setId(id);
                mesaje.add(mesaj);
            }
            return mesaje;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesaje;
    }

    @Override
    public Mesaj save(Mesaj entity) {

        String sql = "insert into mesaje (id_sender, id_reciever, mesaj, data, isreply) values (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            for (Utilizator u : entity.getRecieveri()) {
                //ps.setLong(1, entity.getId());
                ps.setLong(1, entity.getSender().getId());
                ps.setLong(2, u.getId());
                ps.setString(3, entity.getMesaj());
                ps.setTimestamp(4, entity.getData());
                ps.setLong(5, entity.isReply());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from users where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Mesaj entity) {
        String sql = "update mesaje set id_sender=?, id_reciever=?, mesaj=?, data=?, isreply=? where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId());
            ps.setLong(2, entity.getSender().getId());
            ps.setLong(3, entity.getRecieveri().get(0).getId());
            ps.setString(4, entity.getMesaj());
            ps.setTimestamp(5, entity.getData());
            ps.setLong(6, entity.isReply());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(Mesaj entity, String u, String p, String s) {

    }

    @Override
    public int validateLogin(String u, String p) {
        return 0;
    }

    @Override
    public Mesaj findByUser_Name(String u) {
        return null;
    }

}

