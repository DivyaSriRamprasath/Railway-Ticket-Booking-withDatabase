import java.sql.*;

public class BookingDAO {

    public static void addPassenger(Passenger p) throws SQLException {

        int pId = p.passengerId;
        String name = p.name;
        int age = p.age;
        String berthPreference = p.berthPreference;
        String allotted = p.allotted;
        int sNo = p.number;

        Connection con = DbConnection.getConnection();

        String query = "insert into passenger values(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pst = con.prepareStatement(query);

        pst.setInt(1, pId);
        pst.setString(2, name);
        pst.setInt(3, age);
        pst.setString(4, berthPreference);
        pst.setString(5, allotted);
        pst.setInt(6, sNo);
        pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

        int rows = pst.executeUpdate();
        System.out.println(rows + "rows inserted");
    }

    public static int getLastPassengerId() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select pId from passenger order by pId desc limit 1;";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int lastPid = rs.getInt(1);
            return ++lastPid;
        }
        return 1;
    }

    public static void viewTicket(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "select * from Passenger where pId = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            System.out.println("Passenger id: " + rs.getInt(1));
            System.out.println("Passenger name: " + rs.getString(2));
            System.out.println("Passenger age: " + rs.getInt(3));
            System.out.println("Passenger seat number: " + rs.getInt(6)+rs.getString(5));
        }
    }

    public static void ListTicket() throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "select * from Passenger;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            System.out.println("Passenger id: " + rs.getInt(1));
            System.out.println("Passenger name: " + rs.getString(2));
            System.out.println("Passenger age: " + rs.getInt(3));
            System.out.println("Passenger seat number: " + rs.getInt(6)+rs.getString(5));
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println();
        }
    }

    public static Passenger getPassenger(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "select * from Passenger where pId = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        int pId = 0;
        String name = "";
        int age = 0;
        String berthPreference= "";
        String allotted = "";
        int number = 0;

        while (rs.next()) {
            pId =  rs.getInt(1);
            name =  rs.getString(2);
            age =  rs.getInt(3);
            berthPreference = rs.getString(4);
            allotted = rs.getString(5);
            number =  rs.getInt(6);
        }

        Passenger p = new Passenger(name, age, berthPreference);
        p.passengerId = pId;
        p.allotted = allotted;
        p.number = number;

        return p;
    }

    public static void cancelTicket(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "delete from passenger where pId = ?;";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);

        int rows = pst.executeUpdate();
        System.out.println(rows + "rows affected");
    }

    public static Passenger getRac() throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "select * from passenger where alloted = 'rac' order by bookingTime asc";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int pId =  rs.getInt(1);
        String name =  rs.getString(2);
        int age =  rs.getInt(3);
        String berthPreference = rs.getString(4);
        String allotted = rs.getString(5);
        int number =  rs.getInt(6);

        Passenger p = new Passenger(name, age, berthPreference);
        p.passengerId = pId;
        p.allotted = allotted;
        p.number = number;

        return p;
    }

    public static Passenger getWL() throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "select * from passenger where alloted = 'W' order by bookingTime asc";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int pId =  rs.getInt(1);
        String name =  rs.getString(2);
        int age =  rs.getInt(3);
        String berthPreference = rs.getString(4);
        String allotted = rs.getString(5);
        int number =  rs.getInt(6);

        Passenger p = new Passenger(name, age, berthPreference);
        p.passengerId = pId;
        p.allotted = allotted;
        p.number = number;

        return p;
    }

    public static boolean isRac() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select count(*) from passenger where alloted = 'rac';";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int rac = rs.getInt(1);
            return rac > 0;
        }
        return false;
    }

    public static boolean isWL() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select count(*) from passenger where alloted = 'W';";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int wl = rs.getInt(1);
            return wl > 0;
        }
        return false;
    }

    public static void updateRac(int passengerId, String allotted, int number) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "update passenger set alloted = ?, sNo = ? where pId = ?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, allotted);
        pst.setInt(2, number);
        pst.setInt(3, passengerId);
        int rows = pst.executeUpdate();
        if (allotted.equals("L"))
            TrainDAO.decreaseAvailableLower();
        else if (allotted.equals("U")) {
            TrainDAO.decreaseAvailableUpper();
        } else if (allotted.equals("M")) {
            TrainDAO.decreaseAvailableMiddle();
        }
        System.out.println(rows + " updated for " + allotted);
    }

    public static void updateWL(int passengerId, int number) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "update passenger set alloted = 'rac', sNo = ? where pId = ?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, number);
        pst.setInt(2, passengerId);
        int rows = pst.executeUpdate();
        System.out.println(rows + " updated for rac");
    }

    public static int getLowerSno() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select sNo from passenger where alloted = 'L' order by pId desc limit 1;";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int lowerNo = rs.getInt(1);
            return ++lowerNo;
        }
        return 1;
    }
    public static int getUpperSno() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select sNo from passenger where alloted = 'U' order by pId desc limit 1;";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int upperNo = rs.getInt(1);
            return ++upperNo;
        }
        return 1;
    }

    public static int getMiddleSno() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select sNo from passenger where alloted = 'M' order by pId desc limit 1;";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int middleNo = rs.getInt(1);
            return ++middleNo;
        }
        return 1;
    }

    public static int getRacSno() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select sNo from passenger where alloted = 'rac' order by pId desc limit 1;";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int racNo = rs.getInt(1);
            return ++racNo;
        }
        return 1;
    }

    public static int getWlSno() throws SQLException {
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        String query = "select sNo from passenger where alloted = 'W' order by pId desc limit 1;";
        String wlCapacity = "select wlCapacity from trainCapacity;";
        ResultSet rs = st.executeQuery(query);
        if (! rs.next())
            return 1;

        int wNo = rs.getInt(1);
        ResultSet wrs = st.executeQuery(wlCapacity);
        wrs.next();
        int waitingCapacity = wrs.getInt(1);
        if (wNo == waitingCapacity)
            return --wNo;

        return ++wNo;
    }
}
