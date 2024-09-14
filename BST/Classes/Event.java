
public class Event implements Comparable<String> {
	private String title;
	private String location;
	private Contact[] Contacts;
	private int ContactNumber ; 
	private String dateAndTime;
	private boolean IsAppointment ; 

	public Event(String title, String location, Contact[] Contacts, String dateAndTime ,boolean IsAppointment ) {
		this.title = title;
		this.location = location;
		this.Contacts = Contacts;
		this.dateAndTime = dateAndTime;
		this.IsAppointment = IsAppointment ; 
		this.ContactNumber = Contacts.length;
	}

	public Event(Event event) {
		this.title = event.title;
		this.location = event.location;
		this.Contacts = event.Contacts;
		this.dateAndTime = event.dateAndTime;
		this.IsAppointment = event.IsAppointment ;
		this.ContactNumber = event.Contacts.length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



	public Contact[] getContacts() {
		return Contacts;
	}

	public boolean getIsAppointment() {
		return IsAppointment;
	}

	public void setIsAppointment(boolean isAppointment) {
		IsAppointment = isAppointment;
	}

	public int getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.ContactNumber = contactNumber;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int compareTo(String SecondEventName) {
		return this.title.compareTo(SecondEventName);
	}

	public String toString() {
		//we check if its an appointment or event
		if (!IsAppointment ) {
			//Event : we use the get contact names method
			return "Event tilte: " + title + "\n"
					+ "Contact names: " + getContactNames() + "\n"
					+ "Event date and time (MM/DD/YYYY HH:MM): " + dateAndTime + "\n"
					+ "Event location: " + location + "\n";
		}else {
			//Appointment : we take the name directly 
			return "Appointment tilte: " + title + "\n"
					+ "Contact name: " + Contacts[0].getName() + "\n"
					+ "Appointment date and time (MM/DD/YYYY HH:MM): " + dateAndTime + "\n"
					+ "Appointment location: " + location + "\n";
		}
	}
	//method that gets all the cotnact's names and add them to a string and return it . 
	public String getContactNames() {
		//if there are no contacts we return an empty string
		if(Contacts[0] == null)
			return "";
		String ContactNames = "" ;
		String separator = "";
		for (int i = 0 ;i < Contacts.length ; i ++) {
			if (Contacts[i] != null) {
			ContactNames += separator+Contacts[i].getName() ; 
			separator= " , " ; 
			}
			
		}
		return ContactNames ; 
	}
	
	
	
	
	
	
	
}