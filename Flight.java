import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
/**
 * @author         Marko Vrljes 
 * student number:   501032932
 */
public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	protected String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int passengers; // changed
	protected Type type;
	
	// manifest is an array list of Passenger objects
	protected ArrayList<Passenger> manifest = new ArrayList<Passenger>();
	// seatMap maps a seat string to a Passenger object.
	protected Map<String, Passenger> seatMap = new TreeMap<String, Passenger>();
	
	public Flight() {
		
	}

	// Constructor
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
	}
	
	// setters and getters
	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	// gets manifest which is all passengers in flight
	public ArrayList<Passenger> getPassengers(){
		return manifest;
	}
	// returns manifest which is all passengers in flight
	public void printPassengerManifest() {
		for(Passenger p : manifest) {
			System.out.println(p);
		}
	}
	// method for printing the seats using seatMap and methods from aircraft
	public void printSeats() {
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// method getSetLayout from aircraft sets rows and columns sizes
		String[][] seatLayout = aircraft.getseatLayout();
		int row = 0;
		int col = 0;
		System.out.println();
		// looping through the rows and columns checking if seats are occupied or not
		for(int i = 0; i < seatLayout[0].length; i++) {
			for(int j = 0; j < seatLayout.length; j++) {
			  row = i;
			  col = j+1;
			  String seat = "" + col + alpha.charAt(row); 
			// if the seat is being used it is in the seatMap and then get turned to XX
              if(seatMap.containsKey(seat)) { 
            	  System.out.print("XX ");
              }
              else {
            	  System.out.print(seat + " ");
              } 
			}
			if(row == 1)
				System.out.println("");
			System.out.println("");
		}		  
	}
	
	// checks if there are less passengers then available seats and returns true or false
	public boolean seatsAvailable() {
		if(passengers < aircraft.getNumSeats()) {
			return true;
		}
		return false;
	}
	// reserves a seat for a passenger in the desired seat
	public void reserveSeat(Passenger p, String seat){
		// if the seat is an economy and there isn't space throw exception
		if(p.getSeatType().equals("Economy") && passengers >= aircraft.getNumSeats()) {
			throw new FlightFullException("Flight " + flightNum + " is full");
		}
		// searches through the list and find index of matching object, if found throw exception
		int index = manifest.indexOf(p);
		if(index >= 0) {
			throw new DuplicatePassenger("Duplicate Passenger " + p.getName() + " " + p.getPassport());	
		}
		// if seat is already in use throw exception
		if(seatMap.containsKey(seat)) {
			throw new SeatOccupiedException("Seat " + seat + " is already occupied");
		}
		// add the passenger to manifest and to the flights map
		manifest.add(p);
		seatMap.put(seat,  p);
		// if seat is economy add too passenger count
		if(p.getSeatType().equals("Economy")){
			passengers++;
		}
	}
	
	// cancel a seat for the passenger
	public void cancelSeat(Passenger p) {
		int index = manifest.indexOf(p);
		// if passenger not it manifest list throw exception
		if(index == -1) {
			throw new PassengerNotInManifestException(flightNum + " Passenger " + p.getName() + " " + p.getPassport());
		}
		// if seat is economy remove from passenger count
		//if(p.getSeatType().equals("Economy")) {
			passengers--;
		//}
		// remove the passengers seat from the map and from manifest list
		seatMap.remove(p.getSeat());
		manifest.remove(p);
		
	}

	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
	
}

// Where all the exception classes are initialized so they can be thrown and caught in the other files

class FlightFullException extends RuntimeException {
	
	//private static final long serialVersionUID = 1L;
	public FlightFullException() {
		
	}
	// the message is already made when the exceptions are thrown in the code 
	public FlightFullException(String msg) {
		super(msg);
	}
}

class DuplicatePassenger extends RuntimeException {
	
	public DuplicatePassenger() {
		
	}
	public DuplicatePassenger(String msg) {
		super(msg);
	}
}

class ReservationNotFoundException extends RuntimeException {
	
	//private static final long serialVersionUID = 1L;
	public ReservationNotFoundException() {
		
	}
	public ReservationNotFoundException(String msg) {
		super(msg);
	}
}

class PassengerNotInManifestException extends RuntimeException {
	
	public PassengerNotInManifestException() {
		
	}
	public PassengerNotInManifestException(String msg) {
		super(msg);
	}
}

class SeatOccupiedException extends RuntimeException {
	
	public SeatOccupiedException() {
		
	}
	public SeatOccupiedException(String msg) {
		super(msg);
	}
}
class FirstClassFullException extends RuntimeException {
		
	public FirstClassFullException() {
			
	}
	public FirstClassFullException(String msg) {
		super(msg);
	}
}














