package connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC Homework
 * Definiti o baza de date pentru aplicatia de "booking" care sa contina urmatoarele tabele:
 * - accomodation: cu urmatoarele  coloane:
 * -- id: int (primary key)
 * -- type: varchar (32)
 * -- bed_type: varchar (32)
 * -- max_guests: int
 * -- description: varchar(512)
 * - room_fair: cu urmatoarele coloane
 * -- id: int (primary key)
 * -- value: double
 * -- season: varchar(32)
 * - accomodation_fair_relation: cu urmatoarele coloane:
 * -- id: int (primary key)
 * -- id_accomodation: int (foreign key of accomodation)
 * -- id_room_fair: int (foreign key of room fair)
 * 2. Scrieti un unit test care sa insereze (INSERT INTO ... ) date in tabele folosindu-va de prepared statement si tineti cont de relationarea de date din accomodation_fair_relation.
 * 3. Scrieti un unit test care sa interogheze baza de date (SELECT * FROM ...) si tipariti la consola preturile pe fiecare camera din DB.
 * Mentiuni:
 * - respectati intocmai numele tabelelor, al coloanelor si tipurile de date specificate mai sus
 * - tabela "accomodation_fair_relation" este o tabela de legatura intre camere si preturi si va trebui sa va folositi de JOIN in query-ul de interogare
 * - pentru insert in DB din java, recomand sa va folositi de: PreparedStatement.executeUpdate()
 */

public class ConnectionToDB {
    public static java.sql.Connection returnConnection() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println("Can’t load driver");
            System.err.println(e.getMessage());
        }
        java.sql.Connection connection = null;
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
        return connection;
    }
}