/****************************
CLASS: Contact.java
CSC212 Data structures - Project phase II
Fall 2023

EDIT DATE:
12-3-2023

TEAM:
Fahad AlJadaan's Group . 

AUTHORS:
Fahad Adel AlJadaan (443105685)
Nawaf Mohammed AlSaeed (443102065)
Mishari Khalid AlBuhairi (443102188)

***********************************/

public class Contact implements Comparable<String> {
	// Contact details
	private String name;
	private String phoneNumber;
	private String emailAddress;
	private String address;
	private String birthday;
	private String notes;

//Generating the Constructor  
	public Contact(String name, String phoneNumber, String emailAddress, String address, String birthday,
			String notes) {

		this.name = name;
		// we had to use a set method so it can validate the phone number upon adding
		setPhoneNumber(phoneNumber);
		this.emailAddress = emailAddress;
		this.address = address;
		this.birthday = birthday;
		this.notes = notes;
	}

	
	
	public Contact() {
		name = null ; 
		phoneNumber = null ; 
		emailAddress = null ; 
		address = null ; 
		birthday = null ; 
		notes = null ; 
}



	public Contact(Contact newContant) {

		this.name = newContant.name;
		this.phoneNumber = newContant.phoneNumber;
		this.emailAddress = newContant.emailAddress;
		this.address = newContant.address;
		this.birthday = newContant.birthday;
		this.notes = newContant.notes;
	}

//Setters & Getters  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
       // this setter works normally except it checks if the number has an any value other than digits then it throws and 
	   //then it throws and IllegalArgumentException  . 
	public void setPhoneNumber(String phoneNumber) {
		
		if (ValidPhoneNumber(phoneNumber)) {
			this.phoneNumber = phoneNumber;
		} else {
			throw new IllegalArgumentException("Invalid phone number. Please enter only digits.");
		}
	}
             
	// this method is used in the set phone number that checks that the given string contains
	//digits from 0-9 only hence calling it a phone number .
	public static boolean ValidPhoneNumber(String phoneNumber) {
		String pattern = "^[0-9]+$"; // to check if a string contains only digits.
		return phoneNumber.matches(pattern);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//Method compareTo that was implemented by the interface Comparable
	@Override
	public int compareTo(String SecondContactName) {
		return this.name.compareTo(SecondContactName);
	}

	@Override
//return the details of contact in string
	public String toString() {
		return "Name:" + name + "\n" + "Phone Number:" + phoneNumber + "\n" + "Email Address:" + emailAddress + "\n"
				+ "Address:" + address + "\n" + "Birthday:" + birthday + "\n" + "Notes:" + notes + "\n";
	}

}
