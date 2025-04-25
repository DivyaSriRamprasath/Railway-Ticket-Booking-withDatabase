import java.sql.SQLException;

public class TicketBooker {
    TicketBooker() throws SQLException {
        TrainDAO.getAvailable();
    }

    public void bookTicket(Passenger p, int seatInfo, String allotted) throws SQLException {

        p.number = seatInfo;
        p.allotted = allotted;
        BookingDAO.addPassenger(p);
        System.out.println("Ticket booked successfully...");
        System.out.println("your passenger id is: " + p.passengerId + "\n. Please make a note of it to view or cancel ticket later.");
    }

    public void addToRac(Passenger p, int seatInfo, String allotted) throws SQLException {
        p.number = seatInfo;
        p.allotted = allotted;
        BookingDAO.addPassenger(p);
        System.out.println("Ticket booked successfully...");
        System.out.println("your passenger id is: " + p.passengerId + "\n. Please make a note of it to view or cancel ticket later.");

    }

    public void addToWaitingList(Passenger p, int seatInfo, String allotted) throws SQLException {
        p.number = seatInfo;
        p.allotted = allotted;
        BookingDAO.addPassenger(p);
        System.out.println("Ticket booked successfully...");
        System.out.println("your passenger id is: " + p.passengerId + "\n. Please make a note of it to view or cancel ticket later.");
    }

    public void cancelTicket(int id) throws SQLException {

        Passenger p = BookingDAO.getPassenger(id);
        BookingDAO.cancelTicket(id);

        if(p.allotted.equals("L")){
            TrainDAO.increaseAvailableLower();
            System.out.println(TrainDAO.availableLower);
        }
        else if(p.allotted.equals("M")){
            TrainDAO.increaseAvailableMiddle();
            System.out.println(TrainDAO.availableMiddle);
        }
        else if(p.allotted.equals("U")){
            TrainDAO.increaseAvailableUpper();
            System.out.println(TrainDAO.availableUpper);
        }
        else if (p.allotted.equals("rac")) {
            TrainDAO.increaseAvailableRac();
            wlToRac(p.number, p);
            System.out.println(TrainDAO.availableRAC);
        }
        else if(p.allotted.equals("W")){
            TrainDAO.increaseAvailableWL();
            System.out.println(TrainDAO.availableWaitingList);
        }

        if(!(p.allotted.equals("W")) && !(p.allotted.equals("rac"))){
            if(BookingDAO.isRac()){
                Passenger racPassenger = BookingDAO.getRac();
                int racNumber = racPassenger.number;
                BookingDAO.updateRac(racPassenger.passengerId, p.allotted, p.number);
                TrainDAO.increaseAvailableRac();

                if(BookingDAO.isWL()){
                    wlToRac(racNumber, racPassenger);
                }
            }
        }

        System.out.println("Ticket cancelled successfully...");
    }

    public static void wlToRac(int racNumber, Passenger racPassenger) throws SQLException {
        Passenger waitingListPassenger = BookingDAO.getWL();
        BookingDAO.updateWL(waitingListPassenger.passengerId, racNumber);
        TrainDAO.increaseAvailableWL();
        waitingListPassenger.number = racPassenger.number;
        waitingListPassenger.allotted = "rac";
        TrainDAO.decreaseAvailableRac();
    }

    public void available() throws SQLException {
        TrainDAO.getAvailable();
        System.out.println("Lower: " + TrainDAO.availableLower);
        System.out.println("Middle: " + TrainDAO.availableMiddle);
        System.out.println("Upper: " + TrainDAO.availableUpper);
        System.out.println("Rac: " + TrainDAO.availableRAC);
        System.out.println("Waiting list: " + TrainDAO.availableWaitingList);
    }
}
