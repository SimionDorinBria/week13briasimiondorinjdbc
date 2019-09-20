package accomodationfairrelation;

import accomodation.Accomodation;
import roomfair.RoomFair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccomodationFairRelationManager {
    public void insertInAccomodationFairRelation(Accomodation accomodation, RoomFair roomFair) {

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
                .prepareStatement("INSERT INTO public.accomodation_fair_relation(id, id_accomodation, id_room_fair) " +
                        "VALUES (nextval('accomodation_fair_relation_id_seq'), ?, ?);")) {
            ps.setInt(1, accomodation.id);
            ps.setInt(2, roomFair.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}