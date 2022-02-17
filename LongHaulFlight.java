/**
 * @author         Marko Vrljes 
 * student number:   501032932
 */
/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
	// constructor initializing variables from class flight	
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		// using constructor of class flight
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{

	}
	
	// new reserveSeat for passengers of first class 
	public void reserveSeat(Passenger p, String seat)
	{
		// if seatType is first class
		if (p.getSeatType().equals("First Class"))
		{
			// if there is no room for first class passengers
			if (firstClassPassengers >= aircraft.getNumFirstClassSeats()){
				throw new FirstClassFullException("Flight " + flightNum + " first class is full");
			}
			super.reserveSeat(p, seat);
			firstClassPassengers++;
		}	
		else {
			super.reserveSeat(p, seat);
		}
	}
	
	// new cancelSeat method for passengers of first class
	public void cancelSeat(Passenger p)
	{
		// if seatType is first class
		if (p.getSeatType().equals("First Class"))
		{
			super.cancelSeat(p);;
			firstClassPassengers--;
		}
		else {
			super.cancelSeat(p);
		}	
	}
	// checks if there is less first class passengers then number of first class seats
	public boolean seatsAvailable() {
		if(firstClassPassengers < aircraft.getNumFirstClassSeats()) {
			return true;
		}
		return false;
	}

	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
}
