
public class Event implements Comparable<String> {
	private String title;
	private String location;
	private Contact WithContact;
	private String dateAndTime;

	public Event(String title, String location, Contact withContact, String dateAndTime) {
		this.title = title;
		this.location = location;
		WithContact = withContact;
		this.dateAndTime = dateAndTime;
	}

	public Event(Event event) {
		event.title = title;
		event.location = location;
		event.WithContact = WithContact;
		event.dateAndTime = dateAndTime;
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

	public Contact getWithContact() {
		return WithContact;
	}

	public void setWithContact(Contact withContact) {
		WithContact = withContact;
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
return "Event tilte: " + title + "\n"
	+ "Contact name: " + WithContact.getName() + "\n"
	+ "Event date and time (MM/DD/YYYY HH:MM): " + dateAndTime + "\n" 
	+ "Event location: " + location + "\n";
	}

}
