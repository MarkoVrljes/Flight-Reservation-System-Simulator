/**
 * @author         Marko Vrljes 
 * student number:   501032932
 */
public class Reservation
{
	String flightNum;
	String flightInfo;
	String name;
	String passport;
	String seat;
	String seatType;
	boolean firstClass; // added for a2
	
	public Reservation(String flightNum, String info)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
	}
	
	public Reservation(String flightNum, String name, String passport)
	{
		this.flightNum = flightNum;
		this.name = name;
		this.passport = passport;
	}
	
	public Reservation(String flightNum, String info, String name, String passport, String seat)// took out string seatType
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.name = name;
		this.passport = passport;
		this.seat = seat;
	}
	
	// new constructor with String flightNum; String flightInfo; boolean firstClass;
		//                  String passengerName; String passengerPassport; String seat;
		public Reservation(String flightNum, String flightInfo, boolean firstClass, String passengerName, String passengerPassport, String seat) {
			this.flightNum = flightNum;
			this.flightInfo = flightInfo;
			this.firstClass = firstClass;   // don't think this is right
			this.name = passengerName;
			this.passport = passengerPassport;
			this.seat = seat;
		}
		
	public String getName()
	{
		return name;
	}
			
	public String getPassport()
	{
		return passport;
	}
	
	public String getseat()
	{
		return seat;
	}
	
	public String getseatType()
	{
		return seatType;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}
	
	public boolean equals(Object other)
	{
		Reservation otherRes = (Reservation) other;
		return flightNum.equals(otherRes.flightNum)&&  name.equals(otherRes.name) && passport.equals(otherRes.passport); 
	}

	public void print()
	{
		System.out.println(flightInfo + " " + name + " " + seat);
	}
}
