package socialnetwork.repository.db;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class RepositoryDbPrietenie implements Repository<Tuple<Long, Long>, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public RepositoryDbPrietenie(String url, String username, String password, Validator<Prietenie> validatorPrietenie) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Prietenie findOne(Tuple<Long, Long> id) {
        String sql = "select * from friendships where id_friend1=? and id_friend2=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id.getL());
            statement.setLong(2, id.getR());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {Long id1 = resultSet.getLong("id_friend1");
            Long id2 = resultSet.getLong("id_friend2");
            String date = resultSet.getString("creation_date");
            return new Prietenie(id1, id2, LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME));}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id_friend1");
                Long id2 = resultSet.getLong("id_friend2");
                String date = resultSet.getString("creation_date");
                prietenii.add(new Prietenie(id1, id2, LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)));
            }
            return prietenii;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public Prietenie save(Prietenie entity) {

        String sql = "insert into friendships (id_friend1, id_friend2, creation_date) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId().getL());
            ps.setLong(2, entity.getId().getR());
            String dateTime = entity.getDate().format(DateTimeFormatter.ISO_DATE_TIME);
            ps.setString(3, dateTime);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Tuple<Long, Long> id) {
        String sql = "delete from friendships where id_friend1=? and id_friend2=?";

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
    public void update(Prietenie entity) {
        String sql = "update friendships set id_friend1=?, id_friend2=?, creation_date=? where id_friend1=? and id_friend2=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId().getL());
            ps.setLong(2, entity.getId().getR());
            String dateTime = entity.getDate().format(DateTimeFormatter.ISO_DATE_TIME);
            ps.setString(3, dateTime);
            ps.setLong(4, entity.getId().getL());
            ps.setLong(5, entity.getId().getR());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(Prietenie entity, String u, String p, String s) {

    }

    @Override
    public int validateLogin(String u, String p) {
        return 0;
    }

    @Override
    public Prietenie findByUser_Name(String u) {
        return null;
    }

}
