import java.sql.SQLException;

public class Passenger {
    public int id = BookingDAO.getLastPassengerId();
    public String name;
    public int age;
    String berthPreference;
    String allotted;
    public int passengerId;
    public int number;

    public Passenger(String name, int age, String berthPreference) throws SQLException {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.passengerId = id++;
        number = -1;
    }
}
