package interrogation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PricesForEveryRoom {
    private String typeOfAccomodation;
    private double valueOfRoomFair;

    public PricesForEveryRoom(String typeOfAccomodation, double valueOfRoomFair) {
        this.typeOfAccomodation = typeOfAccomodation;
        this.valueOfRoomFair = valueOfRoomFair;
    }

    public String getTypeOfAccomodation() {
        return typeOfAccomodation;
    }

    public void setTypeOfAccomodation(String typeOfAccomodation) {
        this.typeOfAccomodation = typeOfAccomodation;
    }

    public double getValueOfRoomFair() {
        return valueOfRoomFair;
    }

    public void setValueOfRoomFair(double valueOfRoomFair) {
        this.valueOfRoomFair = valueOfRoomFair;
    }

    public List<PricesForEveryRoom> getPricesForEveryRoom(Connection connection) {

        List<PricesForEveryRoom> pricesForEveryRooms = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT accomodation.type, room_fair.value FROM accomodation_fair_relation " +
                     "JOIN accomodation ON accomodation_fair_relation.id_accomodation = accomodation.id " +
                     "JOIN room_fair ON accomodation_fair_relation.id_room_fair = room_fair.id;")) {
            while (rs.next()) {
                String typeOfAccomodation = rs.getString("type");
                double valueOfRoomFair = rs.getDouble("value");

                PricesForEveryRoom pricesForEveryRoom = new PricesForEveryRoom(typeOfAccomodation, valueOfRoomFair);
                pricesForEveryRooms.add(pricesForEveryRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pricesForEveryRooms;
    }
}