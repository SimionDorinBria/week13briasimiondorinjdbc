package accomodation;

import connection.ConnectionToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class AccomodationManagerTest {
    @Before
    public void setUp() {
        try {
            Connection connection = ConnectionToDB.returnConnection();
            PreparedStatement ps = connection
                    .prepareStatement("TRUNCATE TABLE public.accomodation CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_id_seq START WITH 1 RESTART;");
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
                    .prepareStatement("TRUNCATE TABLE public.accomodation CASCADE;");
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("ALTER SEQUENCE public.accomodation_id_seq START WITH 1 RESTART;");
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertInAccomodation() throws SQLException {
        Connection connection = ConnectionToDB.returnConnection();

        Accomodation accomodation = new Accomodation(1, "hotel", "single", 1, "close to the beach");
        AccomodationManager accomodationManager = new AccomodationManager();

        accomodationManager.insertInAccomodation(accomodation);

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.accomodation;");
            rs.next();
            Assert.assertEquals(1, rs.getInt("id"));
            Assert.assertEquals("hotel", rs.getString("type"));
            Assert.assertEquals("single", rs.getString("bed_type"));
            Assert.assertEquals(1, rs.getInt("max_guests"));
            Assert.assertEquals("close to the beach", rs.getString("description"));
            Assert.assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}