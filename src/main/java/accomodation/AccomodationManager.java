package accomodation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccomodationManager {

    public void insertInAccomodation(Accomodation accomodation) {

        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println("Can’t load driver");
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        DriverManager.setLoginTimeout(60);
        try {
            String url = new StringBuilder()
                    .append("jdbc:")
                    .append("postgresql")
                    .append("://")
                    .append("127.0.0.1")
                    .append(":")
                    .append(5432)
                    .append("/")
                    .append("booking")
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("pass").toString();
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (connection == null) {
            System.out.println("no connection");
        }

        try (PreparedStatement ps = connection
                .prepareStatement("insert into accomodation (id, type, bed_type, max_guests, description)"
                        + "values (nextval('accomodation_id_seq'), ?, ?, ?, ?)")) {
            ps.setString(1, accomodation.type);
            ps.setString(2, accomodation.bed_type);
            ps.setInt(3, accomodation.max_guests);
            ps.setString(4, accomodation.description);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}