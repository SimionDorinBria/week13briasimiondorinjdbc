package interrogation;

import accomodation.Accomodation;
import accomodation.AccomodationManager;
import accomodationfairrelation.AccomodationFairRelation;
import accomodationfairrelation.AccomodationFairRelationManager;
import connection.ConnectionToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roomfair.RoomFair;
import roomfair.RoomFairManager;

import java.sql.*;

public class PricesForEveryRoomTest {

    @Before
    public void setUp() {
        try {
            Connection connection = ConnectionToDB.returnConnection();
            PreparedStatement ps = connection
                    .prepareStatement("TRUNCATE TABLE public.accomodation_fair_relation CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_fair_relation_id_seq START WITH 1 RESTART;");
            ps2.executeUpdate();
            PreparedStatement ps3 = connection
                    .prepareStatement("TRUNCATE TABLE public.accomodation CASCADE;");
            ps3.executeUpdate();
            PreparedStatement ps4 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_id_seq START WITH 1 RESTART;");
            ps4.executeUpdate();
            PreparedStatement ps5 = connection
                    .prepareStatement("TRUNCATE TABLE public.room_fair CASCADE;");
            ps5.executeUpdate();
            PreparedStatement ps6 = connection
                    .prepareStatement("ALTER SEQUENCE public.room_fair_id_seq START WITH 1 RESTART;");
            ps6.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = ConnectionToDB.returnConnection();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("TRUNCATE TABLE public.accomodation_fair_relation CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_fair_relation_id_seq START WITH 1 RESTART;");
            ps2.executeUpdate();
            PreparedStatement ps3 = connection
                    .prepareStatement("TRUNCATE TABLE public.accomodation CASCADE;");
            ps3.executeUpdate();
            PreparedStatement ps4 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_id_seq START WITH 1 RESTART;");
            ps4.executeUpdate();
            PreparedStatement ps5 = connection
                    .prepareStatement("TRUNCATE TABLE public.room_fair CASCADE;");
            ps5.executeUpdate();
            PreparedStatement ps6 = connection
                    .prepareStatement("ALTER SEQUENCE public.room_fair_id_seq START WITH 1 RESTART;");
            ps6.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPricesForEveryRoom() {
        Connection connection = ConnectionToDB.returnConnection();

        Accomodation accomodation1 = new Accomodation(1, "hotel", "single", 1, "close to the beach");
        Accomodation accomodation2 = new Accomodation(2, "guesthouse", "double", 7, "cozy");
        AccomodationManager accomodationManager = new AccomodationManager();
        accomodationManager.insertInAccomodation(accomodation1);
        accomodationManager.insertInAccomodation(accomodation2);

        RoomFair roomFair1 = new RoomFair(1, 75.0, "spring");
        RoomFair roomFair2 = new RoomFair(2, 100.0, "winter");
        RoomFairManager roomFairManager = new RoomFairManager();
        roomFairManager.insertInRoomFair(roomFair1);
        roomFairManager.insertInRoomFair(roomFair2);

        AccomodationFairRelation accomodationFairRelation1 = new AccomodationFairRelation(1, 1, 2);
        AccomodationFairRelation accomodationFairRelation2 = new AccomodationFairRelation(2, 2, 1);
        AccomodationFairRelationManager accomodationFairRelationManager = new AccomodationFairRelationManager();
        accomodationFairRelationManager.insertInAccomodationFairRelation(accomodation1, roomFair2);
        accomodationFairRelationManager.insertInAccomodationFairRelation(accomodation2, roomFair1);

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT accomodation.type, room_fair.value FROM accomodation_fair_relation " +
                    "JOIN accomodation ON accomodation_fair_relation.id_accomodation = accomodation.id " +
                    "JOIN room_fair ON accomodation_fair_relation.id_room_fair = room_fair.id;");
            rs.next();
            Assert.assertEquals("hotel", rs.getString("type"));
            Assert.assertEquals(100, rs.getDouble("value"), 0.01);
            rs.next();
            Assert.assertEquals("guesthouse", rs.getString("type"));
            Assert.assertEquals(75, rs.getDouble("value"), 0.01);
            Assert.assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}