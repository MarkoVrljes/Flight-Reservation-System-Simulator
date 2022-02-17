import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * @author         Marko Vrljes 
 * student number:   501032932
 */

public class FlightManager
{
  // flight map that maps a flightNum to a Flight object
  Map<String, Flight> flights = new TreeMap<String, Flight>();
  
  String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  int[] flightTimes = { 3, // Dallas
  						1, // New York
  						7, // London
  						8, // Paris
  					    16,// Tokyo
  };
  
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
  ArrayList<String> flightNumbers = new ArrayList<String>();
  
  String errMsg = null;
  Random random = new Random();
  
  // constructor that adds flights from a flights.txt file into the map
  public FlightManager() throws FileNotFoundException                                
  {
	  	  // reads file and makes scanner of the whole thing
		  File inputFile = new File("flights.txt");
		  Scanner in = new Scanner(inputFile);
		  // sets variables
		  String airline = "";
		  String dest = "";
		  String departure = "";
		  int capacity = 0; 
		  int destination = 0;
		  // loops through the number of lines in the file
		  for(int i = 0; i < 8; i++) {
			  // makes scanner of the specific line
			  String line = in.nextLine();  
			  Scanner ln = new Scanner(line);
			  // sets the first word to be the airline, removing the _ in the process
			  if(ln.hasNext()) {
				  String word = ln.next();
				
				  if(word.contains("_")) {
					  String space = word.replace("_", " ");
				      airline = space;   
				  }    
	 
			  }
			  // sets the next word to destination, removing the underscore in the process
			  if(ln.hasNext()) {
				  dest = ln.next();
				  if(dest.contains("_")) {
					  String space = dest.replace('_', ' ');
				      dest = space;
				  } 
				  // sets the destination variable to the correct number as show above the constructor
				  if(dest.equals("Dallas")) destination = 0;
			      else if(dest.equals("London")) destination = 2;
			      else if(dest.equals("Paris")) destination = 3;
			      else if(dest.equals("New York")) destination = 1;
			      else if(dest.equals("Tokyo")) destination = 4;
			  }
			  // sets departure to next string
			  if(ln.hasNext()) {
				  departure = ln.next();
			  }
			  // sets the capacity to the next integer
			  if(ln.hasNextInt()) {
				  capacity = ln.nextInt();
				  
				  // sets the aircraft and adds them to the airplanes list according to the size of their capacity
				  if(capacity >= 100) airplanes.add(new Aircraft(capacity, 12,  "Boeing 800")); // LongHaul flight
				  else if(100 > capacity && capacity >= 70) airplanes.add(new Aircraft(capacity, "Airbus 320"));
				  else if(70 > capacity && capacity >= 30) airplanes.add(new Aircraft(capacity, "Sparks 456"));
				  else if(30 > capacity && capacity >= 5) airplanes.add(new Aircraft(capacity, "Dash-8 100")); 
				  else if(5 > capacity) airplanes.add(new Aircraft(capacity, "Bombardier 5000"));
				  
				  // if capacity is greater than 100 than it is a LongHaul flight
				  if(capacity >= 100) {
					  String flightNum = generateFlightNumber(airline);
					  Flight flight = new LongHaulFlight(flightNum, airline, dest, departure, flightTimes[destination], airplanes.get(airplanes.size() -1)); 
					  flights.put(flightNum, flight);
				  }
				  // if capacity is less than 100 than it is a Economy flight
				  else if(capacity < 100) {
					  String flightNum = generateFlightNumber(airline);
					  Flight flight = new Flight(flightNum, airline, dest, departure, flightTimes[destination], airplanes.get(airplanes.size() -1)); 
					  flights.put(flightNum, flight);
				  }
				 
			  }
		  }
			 
  }
  /*
   * This method generates and returns a flight number string from the airline name parameter
   * get a range from 101 - 300, splits the airline String and get the first letter if each word
   * returns first letter of first word + second letter of second word + 3 digit number
   */
  private String generateFlightNumber(String airline) {
	String word1, word2;
	Scanner scanner = new Scanner(airline);
	word1 = scanner.next();
	word2 = scanner.next();
	String letter1 = word1.substring(0, 1);
	String letter2 = word2.substring(0, 1);
	letter1.toUpperCase(); letter2.toUpperCase();
			  	
	// Generate random number between 101 and 300
	boolean duplicate = false;
	int flight = random.nextInt(200) + 101;
	String flightNum = letter1 + letter2 + flight;
	return flightNum;
  }
  
  //Prints all flights in flights treeMap  
  public void printAllFlights()
  {
	for(String key : flights.keySet()) {
		
		Flight value = flights.get(key);
		
		System.out.println(value);
	}
  }
  
  // uses method from flight.java to print out the passengers in manifest
  public void printManifest(String flightNum) { 
	  Flight f = flights.get(flightNum);
	  f.printPassengerManifest();
  }
  
  // checks if there are seats available by using the flightNum
  public void seatsAvailable(String flightNum) { 
	  
	  Flight flight = null;
	  // throws exception if flightNum not in flights TreeMapp
	  if(!flights.containsKey(flightNum)) {
		  throw new FlightNotFoundException("Flight " + flightNum + " not found");
	  }
	  // gets the flight and prints out the seats 
	  flight = flights.get(flightNum);
	  flight.printSeats();
	  
  }
  

  // reserves a seat on a flight 
  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat)
  {
  	Flight flight = null;
    // throws exception if flightNum not in flights TreeMapp
  	if(!flights.containsKey(flightNum)) {
		  throw new FlightNotFoundException("Flight " + flightNum + " not found");
	  }
  	
  	// checks if there is a + indicating a first class reservation or not
  	flight = flights.get(flightNum);
  	String seatType = "";
  	if(seat.length() == 3 && seat.charAt(2) == '+') {
  		seatType = "First Class";
  	}
  	else {
  		seatType = "Economy";
  	}
  	// creates a new passengers using variables
  	Passenger p = new Passenger(name, passport, seat, seatType);
  	
  	// adds the passenger and chosen seat to flights treeMap and return the reservation made by the passenger
  	flight.reserveSeat(p, seat);
  	Reservation res = new Reservation(flight.getFlightNum(), flight.toString(), name, passport, seat);
  	return res;
  }
  
  // cancels a seat by passenger
  public void cancelReservation(Reservation res)  
  {
	  Flight flight = null;
	    // throws exception if flightNum not in flights TreeMapp
	  	if(!flights.containsKey(res.getFlightNum())){
			  throw new FlightNotFoundException("Flight " + res.getFlightNum() + " not found");
		  }
	  	
	  	flight = flights.get(res.getFlightNum());
	  	
	  	Passenger p = new Passenger(res.getName(), res.getPassport(), res.getseat(), res.getseatType());
	  		  	
	  	flight.cancelSeat(p);
  	
  }

}
 // class for FlightNotFoundException Exception
 class FlightNotFoundException extends RuntimeException {  
		
		public FlightNotFoundException() {
		}
		public FlightNotFoundException(String msg) {
			super(msg);
		} 
 }

