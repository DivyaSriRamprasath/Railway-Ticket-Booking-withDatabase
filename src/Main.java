import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void bookTicket(Passenger p) throws SQLException {
        TicketBooker booker = new TicketBooker();

        if(TrainDAO.availableWaitingList == 0){
            System.out.println("No seats available :(");
        }
        if(p.berthPreference.equals("L") && TrainDAO.availableLower > 0 ||
                p.berthPreference.equals("M") && TrainDAO.availableMiddle > 0 ||
                p.berthPreference.equals("U") && TrainDAO.availableUpper > 0) {
            if (p.berthPreference.equals("L")) {
                System.out.println("Lower berth given");
                booker.bookTicket(p, BookingDAO.getLowerSno(), "L");
                TrainDAO.decreaseAvailableLower();
            } else if (p.berthPreference.equals("M")) {
                System.out.println("Middle berth given");
                booker.bookTicket(p, BookingDAO.getMiddleSno(), "M");
                TrainDAO.decreaseAvailableMiddle();
            } else if (p.berthPreference.equals("U")) {
                System.out.println("Upper berth given");
                booker.bookTicket(p, BookingDAO.getUpperSno(), "U");
                TrainDAO.decreaseAvailableUpper();
            }
        }
        else if (TrainDAO.availableLower > 0) {
            System.out.println("Lower given");
            booker.bookTicket(p, BookingDAO.getLowerSno(), "L");
            TrainDAO.decreaseAvailableLower();
        }
        else if (TrainDAO.availableMiddle > 0) {
            System.out.println("Middle given");
            booker.bookTicket(p, BookingDAO.getMiddleSno(), "M");
            TrainDAO.decreaseAvailableMiddle();
        }
        else if (TrainDAO.availableUpper > 0) {
            System.out.println("Upper given");
            booker.bookTicket(p, BookingDAO.getUpperSno(), "U");
            TrainDAO.decreaseAvailableUpper();
        }
        else if (TrainDAO.availableRAC > 0) {
            System.out.println("rac given");
            booker.addToRac(p, BookingDAO.getRacSno(), "rac");
            TrainDAO.decreaseAvailableRac();

        }
        else if (TrainDAO.availableWaitingList > 0) {
            System.out.println("Waiting list given");
            booker.addToWaitingList(p, BookingDAO.getWlSno(), "W");
            TrainDAO.decreaseAvailableWL();
        }
    }
    public static void cancelTicket(int pId) throws SQLException {
        TicketBooker booker = new TicketBooker();
        booker.cancelTicket(pId);
    }
    public static void main(String[] args) throws SQLException {

        TicketBooker booker = new TicketBooker();
        boolean loop = true;
        while (loop){
            System.out.println("1. Book Ticket");
            System.out.println("2. Show Ticket");
            System.out.println("3. List Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Check availability");
            System.out.println("6. Exit");

            Scanner sc = new Scanner(System.in);
            int userChoice = sc.nextInt();

            switch (userChoice){
                case 1:
                    System.out.println("Enter your name, age and berth preference to book ticket: ");
                    try{
                        String passengerName = sc.next();
                        System.out.println();
                        sc.nextLine();
                        String age = sc.nextLine().trim();

                        String berthPreference = sc.nextLine();

                        int passengerAge = Integer.parseInt(age);

                        Passenger p = new Passenger(passengerName, passengerAge, berthPreference);
                        bookTicket(p);
                    }
                    catch (InputMismatchException e){
                        System.out.println("Please enter your name first followed by your age.");
                    }
                    catch (NumberFormatException e){
                        System.out.println("please enter number as your age.");
                    }
                    catch (Exception e){
                        System.out.println(e + "Please provide valid data");
                    }
                    break;

                case 2:
                    System.out.println("Enter passenger id to view your ticket: ");
                    try {
                        int id = sc.nextInt();
//                        booker.viewTicket(id);
                        BookingDAO.viewTicket(id);
                    }
                    catch (InputMismatchException e){
                        System.out.println("please enter valid data");
                    }
                    break;

                case 3:
//                    booker.ListTicket();
                    BookingDAO.ListTicket();
                    break;

                case 4:
                    System.out.println("Enter passenger id to cancel your ticket: ");
                    try {
                        int pId = sc.nextInt();
                        cancelTicket(pId);
                    }
                    catch (InputMismatchException e){
                        System.out.println("please enter valid data");
                    }
                    break;

                case 5:
                    booker.available();
                    break;

                case 6:
                    loop = false;
            }
        }
    }
}