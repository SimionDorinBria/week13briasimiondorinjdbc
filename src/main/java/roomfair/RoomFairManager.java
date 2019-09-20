package roomfair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomFairManager {
    public void insertInRoomFair(RoomFair roomFair) {
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
                .prepareStatement("insert into room_fair (id,value, season)"
                        + "values (nextval('room_fair_id_seq'),?, ?)")) {
            ps.setDouble(1, roomFair.value);
            ps.setString(2, roomFair.season);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}