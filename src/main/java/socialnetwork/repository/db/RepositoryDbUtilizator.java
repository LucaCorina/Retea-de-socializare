package socialnetwork.repository.db;


import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.Hashed;

import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RepositoryDbUtilizator implements Repository<Long, Utilizator> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public RepositoryDbUtilizator(String url, String username, String password, Validator<Utilizator> validatorUtilizator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Utilizator findOne(Long id) {
        String sql = "select * from users where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id1 = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id1);
                return utilizator;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator save(Utilizator entity) {

        String sql = "insert into users (first_name, last_name) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getPrenume());

            ps.executeUpdate();
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
    public void update(Utilizator entity) {
        String sql = "update users set first_name=?, last_name=? where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getPrenume());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(Utilizator entity, String user_name, String parola, String salt) {
        Hashed hashed = new Hashed();
        String passwd = hashed.get_SHA_256_SecurePasswordWithParameters(parola, salt);
        String sql = "insert into registrations (id_user, user_name, parola, salt) values (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId());
            ps.setString(2, user_name);
            ps.setString(3, passwd);
            ps.setString(4, salt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int validateLogin(String user_name, String parola) {
        String sql = "select * from registrations where user_name=?";
        Hashed hashed = new Hashed();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user_name);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return -1;  //nu exista inregistrarea asta
            } else if (!Objects.equals(hashed.get_SHA_256_SecurePasswordWithParameters(parola, resultSet.getString("salt")), resultSet.getString("parola"))) {
                return 0;  // datele introduse la login nu-s bune
            } else {
                return 1;   // e ok
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Utilizator findByUser_Name(String user_name) {
        String sql = "select * from registrations where user_name=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user_name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long userid = resultSet.getLong("id_user");
                return findOne(userid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}