package accomodationfairrelation;

import accomodation.Accomodation;
import accomodation.AccomodationManager;
import connection.ConnectionToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roomfair.RoomFair;
import roomfair.RoomFairManager;

import java.sql.*;

public class AccomodationFairRelationManagerTest {

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
    public void testInsertInAccomodationFairRelation() {
        Connection connection = ConnectionToDB.returnConnection();

        Accomodation accomodation = new Accomodation(1, "hotel", "single", 1, "close to the beach");
        AccomodationManager accomodationManager = new AccomodationManager();
        accomodationManager.insertInAccomodation(accomodation);

        RoomFair roomFair = new RoomFair(1, 75.0, "spring");
        RoomFairManager roomFairManager = new RoomFairManager();
        roomFairManager.insertInRoomFair(roomFair);

        AccomodationFairRelation accomodationFairRelation = new AccomodationFairRelation(1, 1, 1);
        AccomodationFairRelationManager accomodationFairRelationManager = new AccomodationFairRelationManager();

        accomodationFairRelationManager.insertInAccomodationFairRelation(accomodation, roomFair);

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.accomodation_fair_relation;");
            rs.next();
            Assert.assertEquals(1, rs.getInt("id"));
            Assert.assertEquals(1, rs.getInt("id_accomodation"));
            Assert.assertEquals(1, rs.getInt("id_room_fair"));
            Assert.assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}