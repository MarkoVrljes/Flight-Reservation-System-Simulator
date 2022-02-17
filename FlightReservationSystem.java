import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author         Marko Vrljes 
 * student number:   501032932
 */
// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		FlightManager manager = null;

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations

		// Try's and catches any exceptions caused from reading the flights.txt file
		try {
			manager = new FlightManager();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
			System.exit(1);
		}
		catch(IOException e){
			System.out.println("File contents contain bad data");
			System.exit(1);        
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);
			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();
			// try block for all methods so that exceptions can be caught at the very end after 
			// being thrown in other classes and this one
			try {
				
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
			
				else if (action.equals("Q") || action.equals("QUIT")) {
					return;
				}
				// uses manager method to print out the flights from the flights.txt file
				else if (action.equalsIgnoreCase("LIST"))
				{
					manager.printAllFlights(); //print like in airport
				}
				// Reserves a flight based on Flight number string 
				else if (action.equalsIgnoreCase("RES"))
				{
					// initialize variable
					String flightNum = null;
					String passengerName = "";
					String passport = "";
					String seat = "";
					// sets the variables to each of the string in the line
					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
					}
					if (commandLine.hasNext())
					{
						passengerName = commandLine.next();
					}
					if (commandLine.hasNext())
					{
						passport = commandLine.next();
					}
					if (commandLine.hasNext())
					{
						seat = commandLine.next();
						// creates a reservation object using previously contracted variables
						Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
							// adds to myReservation and prints
							myReservations.add(res);
							res.print();
					}
				}
				// cancels the seats in the flight by the passenger
				else if (action.equalsIgnoreCase("CANCEL"))
				{
					// initialize variables
					Reservation res = null;
					String flightNum = null;
					String passengerName = "";
					String passport = "";
					// sets the variables to each of the string in the line
					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
					}
					if (commandLine.hasNext())
					{
						passengerName = commandLine.next();
					}
					if (commandLine.hasNext())
					{
						passport = commandLine.next();
						// checks if the reservation object is in myReservations and returns -1 if false
						int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
						if (index >= 0)
						{
							// cancels reservation and removes if true
							manager.cancelReservation(myReservations.get(index));
							myReservations.remove(index);
						}
						else
							// throws exception
							throw new ReservationNotFoundException("Reservation on Flight " + flightNum + " Not Found");
					}
				}
				// prints out the seat layout of a flight
				else if (action.equalsIgnoreCase("SEATS"))
				{
					String flightNum = null;
					
					if (commandLine.hasNext()) {
					
						flightNum = commandLine.next();
						manager.seatsAvailable(flightNum);
						System.out.println("\nXX = Occupied   + = First Class");
					}
				}
				// prints the passenger manifest for this flight i.e information about all passengers
				else if (action.equalsIgnoreCase("PASMAN"))
				{
					String flightNum = "";
					
					if (commandLine.hasNext()) {
						flightNum = commandLine.next();
						manager.printManifest(flightNum);
					}
					
				}
				// Print all the reservations in array list myReservations
				else if (action.equalsIgnoreCase("MYRES"))
				{
					for (Reservation myres : myReservations)
						myres.print();
				}
			}
			// catches all of the exceptions throughout all java files for the program
			
			catch(FlightNotFoundException e) {
				System.out.println(e.getMessage());
			}
			
			catch(FirstClassFullException e) {
				System.out.println(e.getMessage());
			}
			
			catch(ReservationNotFoundException e) {
				System.out.println(e.getMessage());
			}
			catch(FlightFullException e) {
				System.out.println(e.getMessage());
			}
			
			catch(DuplicatePassenger e) {
				System.out.println(e.getMessage());
			}
			
			catch(PassengerNotInManifestException e) {
				System.out.println(e.getMessage());
			}
			
			catch(SeatOccupiedException e) {
				System.out.println(e.getMessage());
			}

			System.out.print("\n>");
		}
	}
}

