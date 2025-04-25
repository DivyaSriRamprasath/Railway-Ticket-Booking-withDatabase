import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainDAO {
    static int availableLower;
    static int availableMiddle;
    static int availableUpper;
    static int availableRAC;
    static int availableWaitingList;

    public static void getAvailable() throws SQLException {
        String lowerQuery = "select availableLower from trainCapacity";
        String upperQuery = "select availableUpper from trainCapacity";
        String middleQuery = "select availableMiddle from trainCapacity";
        String racQuery = "select availableRac from trainCapacity";
        String wlQuery = "select availableWL from trainCapacity";

        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        ResultSet l = st.executeQuery(lowerQuery);
        l.next();
        availableLower = l.getInt(1);

        ResultSet m = st.executeQuery(middleQuery);
        m.next();
        availableMiddle = m.getInt(1);

        ResultSet u = st.executeQuery(upperQuery);
        u.next();
        availableUpper = u.getInt(1);

        ResultSet r = st.executeQuery(racQuery);
        r.next();
        availableRAC = r.getInt(1);

        ResultSet w = st.executeQuery(wlQuery);
        w.next();
        availableWaitingList = w.getInt(1);
    }

    public static void decreaseAvailableLower() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableLower = availableLower-1;");
        availableLower--;
        System.out.println(row + "rows affected");
    }

    public static void decreaseAvailableUpper() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableUpper = availableUpper-1;");
        availableUpper--;
        System.out.println(row + "rows affected");
    }

    public static void decreaseAvailableMiddle() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableMiddle = availableMiddle-1;");
        availableMiddle--;
        System.out.println(row + "rows affected");
    }

    public static void decreaseAvailableRac() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableRac = availableRac-1;");
        availableRAC--;
        System.out.println(row + "rows affected");
    }

    public static void decreaseAvailableWL() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableWL = availableWL-1;");
        availableWaitingList--;
        System.out.println(row + "rows affected");
    }

    public static void increaseAvailableLower() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableLower = availableLower+1;");
        availableLower++;
        System.out.println(row + "rows affected");
    }

    public static void increaseAvailableUpper() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableUpper = availableUpper+1;");
        availableUpper++;
        System.out.println(row + "rows affected");
    }

    public static void increaseAvailableMiddle() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableMiddle = availableMiddle+1;");
        availableMiddle++;
        System.out.println(row + "rows affected");
    }

    public static void increaseAvailableRac() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableRac = availableRac+1;");
        availableRAC++;
        System.out.println(row + "rows affected");
    }

    public static void increaseAvailableWL() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();

        int row = st.executeUpdate("update TrainCapacity set availableWL = availableWL+1;");
        availableWaitingList++;
        System.out.println(row + "rows affected");
    }
}
