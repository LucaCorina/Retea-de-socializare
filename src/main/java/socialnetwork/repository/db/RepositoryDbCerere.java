package socialnetwork.repository.db;

import socialnetwork.domain.Cerere;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RepositoryDbCerere implements Repository<Tuple<Long, Long>, Cerere> {
    private String url;
    private String username;
    private String password;
    private Validator<Cerere> validator;

    public RepositoryDbCerere(String url, String username, String password, Validator<Cerere> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Cerere findOne(Tuple<Long, Long> id) {
        String sql = "select * from cereri where id_sender=? and id_reciever=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id.getL());
            statement.setLong(2, id.getR());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
            Long ids = resultSet.getLong("id_sender");
            Long idr = resultSet.getLong("id_reciever");
            String status = resultSet.getString("status");
            return new Cerere(ids, idr, status);}

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

        @Override
    public Iterable<Cerere> findAll() {
        Set<Cerere> cereri = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cereri");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long ids = resultSet.getLong("id_sender");
                Long idr = resultSet.getLong("id_reciever");
                String status = resultSet.getString("status");
                cereri.add(new Cerere(ids, idr, status));
            }
            return cereri;

    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cereri;
    }

            @Override
    public Cerere save(Cerere entity) {
        String sql = "insert into cereri (id_sender, id_reciever, status) values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId_sender());
            ps.setLong(2, entity.getId_reciever());
            ps.setString(3, entity.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Tuple<Long, Long> id) {
        String sql = "delete from cereri where id_sender=? and id_reciever=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id.getL());
            ps.setLong(2, id.getR());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Cerere entity) {

    }

    @Override
    public void registerUser(Cerere entity, String u, String p, String s) {

    }

    @Override
    public int validateLogin(String u, String p) {
        return 0;
    }

    @Override
    public Cerere findByUser_Name(String u) {
        return null;
    }
}
