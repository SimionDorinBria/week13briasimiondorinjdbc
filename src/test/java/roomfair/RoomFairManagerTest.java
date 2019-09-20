package roomfair;

import connection.ConnectionToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class RoomFairManagerTest {

    @Before
    public void setUp() {
        try {
            Connection connection = ConnectionToDB.returnConnection();
            PreparedStatement ps = connection
                    .prepareStatement("TRUNCATE TABLE public.room_fair CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.room_fair_id_seq START WITH 1 RESTART;");
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = ConnectionToDB.returnConnection();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("TRUNCATE TABLE public.room_fair CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.room_fair_id_seq START WITH 1 RESTART;");
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertInRoomFair() {
        Connection connection = ConnectionToDB.returnConnection();

        RoomFair roomFair = new RoomFair(1, 75.0, "spring");
        RoomFairManager roomFairManager = new RoomFairManager();

        roomFairManager.insertInRoomFair(roomFair);

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.room_fair;");
            rs.next();
            Assert.assertEquals(1, rs.getInt("id"));
            Assert.assertEquals(75.0, rs.getDouble("value"), 0.01);
            Assert.assertEquals("spring", rs.getString("season"));
            Assert.assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}