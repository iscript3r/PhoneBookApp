/****************************
CLASS: Phonebook.java
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
import java.util.InputMismatchException;
import java.util.Scanner;

public class Phonebook {

	private BST<Contact> ContactTree;
	private DoubleLinkedList<Event> EventList;
	private int ContactSize, EventSize;

	public Phonebook() {
		super();
		ContactTree = new BST<Contact>();
		EventList = new DoubleLinkedList<Event>();
		ContactSize = 0;
		EventSize = 0;
	}

	public Contact SearchByName(String name) {
		if (ContactTree.empty()) { //if the tree is empty there are no names
			return null;
		}
		if (ContactTree.findkey(name)) {  // if found , return the contact 
			return ContactTree.retrieve();
		}
		return null;

	}

	public Contact SearchByPhoneNumber(String PhoneNumber) {
		if (ContactTree.empty()) //if the tree is empty there are no phone numbers
			return null;
		Contact FoundContact = ContactTree.searchInorder(PhoneNumber) ;  //searching for the phone number using in order traversal . 
		if(FoundContact!= null)   //if found return the contact
			return FoundContact ; 
		return null ; 
	}
	
	private Contact[] EmailSearchRecursion(String EmailAddress, Contact[] ContactsFound, int EmailCount) {
		/*In this recursion method we use a certain way with moving the current by deleting 
		 * the current element then checking with the desired condition .
		 * then it calls the method again (with the tree missing the checked item) 
		 * it repeats that till the tree is empty .
		 * The method will then insert all the deleted contacts to insure that the tree doesn't change . 
		 */
		if (ContactTree.empty()) // if empty (base case) return . 
			return null; 
		Contact contact = ContactTree.retrieve(); //it gets the contact at the current
		ContactTree.remove_key(contact.getName()); //deletes the current contact
		if (contact.getEmailAddress().equalsIgnoreCase(EmailAddress)) { // checks if the deleted contact have the same email . 
			ContactsFound[EmailCount] = contact; //it adds it to the array 
			EmailCount++;
		}
		EmailSearchRecursion(EmailAddress, ContactsFound, EmailCount);
		ContactTree.insert(contact.getName(), contact);
		return ContactsFound;
	}

	public Contact[] SearchByEmailAddress(String EmailAddress) {
		if (ContactTree.empty()) { // checking if the tree is empty .
			return null; 
		}
		Contact[] ContactsFound = new Contact[ContactSize]; //it makes an array of contact with the max numebr . 
		EmailSearchRecursion(EmailAddress, ContactsFound, 0); //it calls the recursive search method . 
		return ContactsFound ; //returns the array . 
	}

	private Contact[] AddressSearchRecursion(String Address, Contact[] ContactsFound, int AddressCount) {
		/*This method follows the same process as the previously stated method (EmailSearchRecursion)
		 * but instead of checking the email it checks the Address .
		 */
		if (ContactTree.empty())
			return null;
		Contact contact = ContactTree.retrieve();
		ContactTree.remove_key(contact.getName());
		if (contact.getAddress().equalsIgnoreCase(Address)) {
			ContactsFound[AddressCount] = contact;
			AddressCount++;
		}
		AddressSearchRecursion(Address, ContactsFound, AddressCount);
		ContactTree.insert(contact.getName(), contact);
		return ContactsFound;
	}

	public Contact[] SearchByAddress(String Address) {
		/*This method follows the same process as the previously stated method (SearchByEmailAddress)
		 * but it uses the (AddressSearchRecursion) method instead . 
		 */
		if (ContactTree.empty()) { // checking if the list is empty .
			return null;
		}
		Contact[] ContactsFound = new Contact[ContactSize];
		AddressSearchRecursion(Address, ContactsFound, 0);
		return ContactsFound ; 
	}

	private Contact[] BirthdaySearchRecursion(String Birthday, Contact[] ContactsFound, int BirthdayCount) {
		/*This method follows the same process as the previously stated method (EmailSearchRecursion)
		 * but instead of checking the email it checks the Birthday .
		 */
		if (ContactTree.empty())
			return null;
		Contact contact = ContactTree.retrieve();
		ContactTree.remove_key(contact.getName());
		if (contact.getBirthday().equalsIgnoreCase(Birthday)) {
			ContactsFound[BirthdayCount] = contact;
			BirthdayCount++;
		}
		BirthdaySearchRecursion(Birthday, ContactsFound, BirthdayCount);
		ContactTree.insert(contact.getName(), contact);
		return ContactsFound;
	}

	public Contact[] SearchByBirthday(String Birthday) {
		/*This method follows the same process as the previously stated method (SearchByEmailAddress)
		 * but it uses the (BirthdaySearchRecursion) method instead . 
		 */
		if (ContactTree.empty()) { // checking if the list is empty .
			return null;
		}
		Contact[] ContactsFound = new Contact[ContactSize];
		BirthdaySearchRecursion(Birthday, ContactsFound, 0);
		return ContactsFound ; 
	}

	public void AddContactByOrder(Contact NewContact) {

		//Checks if the contact's name or phone number is duplicate , if not the contact is added using (.insert) . 
		
		if ((SearchByName(NewContact.getName()) != null || SearchByPhoneNumber(NewContact.getPhoneNumber()) != null)) {
			System.out.println("This contact already exists\n"); // we print this message if it already exists .
		} else {
			ContactTree.insert(NewContact.getName(), NewContact);
			System.out.println("Contact added successfully ! \n");
			ContactSize++;
		}
	}


	public void DeleteByName(String Name) {
		if (ContactTree.empty()) { // checking if the list is empty .
			System.out.println("\nThere are no contacts in your PhoneBook .");
		} else if ( ContactTree.remove_key(Name)) { //if the key was removed (hence the contact exists).
			Appointment_And_Event_Delete_Contact(Name) ; //We delete the contact from related events/appointments 
			System.out.println("\nContact deleted successfully !");
		} else {
			System.out.println("Contact couldn't be found . \n"); // if not found display this message.
		}
	}
	public void DeleteByPhoneNumber(String PhoneNumber) {
		//we search for the contact by using SearchByPhoneNumber method
		Contact DeletedContact = SearchByPhoneNumber(PhoneNumber);
		//If the contact is null (it wasn't found ) we print this message . 
		if(DeletedContact == null) {
			System.out.println("The contact was not found !");
		}
		else {
			//if it was found we remove it using his name . 
			ContactTree.remove_key(DeletedContact.getName());
			//we make sure to remove him from the Events/Appointments using this method .
			Appointment_And_Event_Delete_Contact(DeletedContact.getName());
			System.out.println("Contact Deleted !");
		}
	}

	public void DisplayContactsAlphabetically() {
		if (ContactTree.empty()) { // it checks if the list is empty
			System.out.println("\n There are no contacts in your PhoneBook .");
		} else {//we use in in order traversal to print them 
			ContactTree.printInorder();
		}
	}

	public void DisplayArrayOfContacts(Contact[] ContactsFound) {
		// this method prints an array of contacts

		if (ContactsFound[0] == null) { // it checks the length of the given contact.
			System.out.println("\nWe Didnt find any Contact! ");
		} else {
			System.out.println("\nContacts Are Found !!");
			int Count = 0;
			while (Count < ContactsFound.length && ContactsFound[Count] != null ) {
				// it goes through the array and prints the contacts info .
				System.out.println("\n" + ContactsFound[Count].toString());
				Count++;
			}
		}
	}

	private boolean FirstNameRecursion(String FirstName2, boolean flag) {
		//the base case is when the ContactTree is empty
		if (ContactTree.empty())
			return flag;
		//we retrieve the current contact
		Contact contact = ContactTree.retrieve();
		ContactTree.remove_key(contact.getName());  // we remove it
		String[] PartName = contact.getName().split(" "); //splitting the name
		String FirstName1 = PartName[0]; //we take the first part
		if (FirstName1.equals(FirstName2)) { //if its equal to the given name
			System.out.println(contact.toString()); // we print it 
			flag = true; // make the flag true
		}
		flag = FirstNameRecursion(FirstName2, flag); //we go through the recursion again (one contact deleted)
		ContactTree.insert(contact.getName(), contact);//we add the deleted contact .
		return flag;
	}

	public void DisplayByFirstName(String FirstName2) {
		if (ContactSize == 0) { // Checks if the list is empty
			System.out.println("\nThere are no contacts in your PhoneBook .");
			return;
		}
		boolean flag = false; // a flag to check if a contact with this first name is found or not . 
		
		//we use the recursion method . 
		flag = FirstNameRecursion(FirstName2, flag);
		
		//if the contact isn't found means that the flag is still false .
		if (!flag)
			System.out.println("We Didnt find any contact with this first name ! ");

	}

	public Event CheckEventConflict(String DateAndTime) {
		/*
		 * We can make this method return a boolean , but we implemented it this way to
		 * return an event so that we can use it in future methods as a (Search by Date
		 * And Time ) which can be used to improve the program .
		 */
		if (EventList.empty()) { // if the event list is empty we return null
			return null;
		}
		EventList.findFirst();
		while (!EventList.last()) { // we go through the list till the last element
			if (EventList.retrieve().getDateAndTime().equalsIgnoreCase(DateAndTime)) {
				return EventList.retrieve(); // if we find an Event that have the same Date and Time as the new Event ,
			} // we return that event .
			EventList.findNext();
		}
		Event LastEvent = EventList.retrieve();
		if (LastEvent.getDateAndTime().equalsIgnoreCase(DateAndTime)) { // we check the last event too .
			return LastEvent;
		}
		return null; // if we didn't find any that have a conflict we return a null
	}

	public Event[] EventSearchByTitle(String Title ) {
		/*this method works exactly like the SearchByEmailAddress method 
		 * but instead it goes through the EVENT list and add the event that is equal to the 
		 * input title ,  then it fixes the array to match the new size , then it returns it . 
		 */
		Event[] AllEventsFound = new Event[EventSize];
		int count = 0;
		if (EventList.empty()) { 
			return AllEventsFound;
		}
		EventList.findFirst();
		while (!EventList.last()) {
			if (EventList.retrieve().getTitle().equals(Title)) { // checks the title and adds its to the array.
				AllEventsFound[count] = EventList.retrieve();
				count++;
			}
			EventList.findNext();
		}
		Event LastEvent = EventList.retrieve();
		if (LastEvent.getTitle().equals(Title)) { // checks last 
			AllEventsFound[count] =LastEvent;
			count++;
		}
		Event[] EventsFoundOnly = new Event[count];
		for (int i = 0; i < count; i++)
			EventsFoundOnly[i] = AllEventsFound[i];
		
		return EventsFoundOnly; // return the fixed array
	}
	
	public Event[] EventSearchByContactName(String ContactName  ) {
		
		Event[] AllEventsFound = new Event[EventSize]; // we make an array to save the matching events .
		int EventCounter = 0;
		int ContactCounter = 0 ; 
		if (EventList.empty()) {  //if there are not event we return the empty array . 
			return AllEventsFound;
		}
		EventList.findFirst(); 
		while (!EventList.last()) { 
			ContactCounter = 0 ; 
			while(  ContactCounter < EventList.retrieve().getContacts().length) { //we go through the array of contacts in the current event
				
				//we check every contact's name to check if it matches the given name or not
			if (EventList.retrieve().getContacts()[ContactCounter] != null && EventList.retrieve().getContacts()[ContactCounter].getName().equals(ContactName)) { 
				AllEventsFound[EventCounter] = EventList.retrieve(); //we add the the event if we find the given name
				EventCounter++;
				break ; //we go out of the inner loop since the contact was found so we don't have to go through all the contacts . 
			}
			ContactCounter++ ; 
			}
			EventList.findNext();
		}
		ContactCounter = 0 ; // we reset the counter to check for the last element
		Event LastEvent = EventList.retrieve();
		while(  ContactCounter < LastEvent.getContacts().length) { // we do the same process for the last event
			if (LastEvent.getContacts()[ContactCounter] != null && LastEvent.getContacts()[ContactCounter].getName().equals(ContactName)) { 
				AllEventsFound[EventCounter] = EventList.retrieve();
				EventCounter++;
				break ; 
			}
			ContactCounter++ ; 
			}
		// we make a new array with the found elements only (no null spaces , fixed array)
		Event[] EventsFoundOnly = new Event[EventCounter];
		for (int j = 0; j < EventCounter; j++)
			EventsFoundOnly[j] = AllEventsFound[j];
		
		return EventsFoundOnly; // we return the fixed array
	}
	
	public void AppointmentAndEventSchedule(Event NewEvent) {
		if (EventList.empty()) {
			EventList.insert(NewEvent); // if the event list is empty it adds it directly .
			EventSize++;
			System.out.println("\nEvent/Appointment scheduled successfully!\n");
			
			// here it checks if the new even have a conflict of time using CheckEventConflict method .
		} else if (CheckEventConflict(NewEvent.getDateAndTime()) == null) {
			//we make a new DLL to add the events sorted alphabetically 
			DoubleLinkedList<Event> sortedEventList = new DoubleLinkedList<>(); 
			EventList.findFirst();   // we go the first element in the current DLL
			boolean inserted  = false ; 
			while (!EventList.empty()) {  // while the current DLL is not empty
				
				Event event = EventList.retrieve(); 
				EventList.remove(); // we remove the current event after appointing another pointer at it 
				
				//then we compare the deleted event with the new event using (compare_to) method
				//we also make sure that the new event isn't inserted yet using inserted method . 
				if (event.compareTo(NewEvent.getTitle()) > 0 && !inserted) {
					sortedEventList.insert(NewEvent); //we insert the new element to the new DLL
					inserted = true ; 
				}
				sortedEventList.insert(event); // we insert the deleted element to the new DLL
			}
			//this case if all the elements were inserted except the new element(hence the new element should be the last)
			if (!inserted) { 
				//we insert the new event to the new DLL
				sortedEventList.insert(NewEvent);
			}
			EventList = sortedEventList;
			EventSize++;
			System.out.println("Event/Appointment scheduled successfully!\n");
		} else {
			// we print this message if there was a conflict .
			System.out.println("There is a conflict in time with other Event/Appointment !\n");
		}
	}
	
	private void Appointment_And_Event_Delete_Contact(String ContactName ) {
		if(EventList.empty()) 
			return ; 
		else {
			EventList.findFirst();
			while(!EventList.last()) {// while the event is not last
				if(EventList.retrieve().getIsAppointment()) { // if the current event is an appointment
					
					//we check the contact involved in it with the given contact name
					if(EventList.retrieve().getContacts()[0].getName().equals(ContactName)) {
						//if it's the same we delete it directly
						EventList.remove();
					}else {
						EventList.findNext();
					}
					//if its an event
				}else {
					//if we delete a contact from one event it will be deleted from all of the other .
					//if the contact name is within the contacts .
						if(EventList.retrieve().getContactNames().contains(ContactName)) {
						//we call the DeleteContactFromEvent method to delete the contact involved
						DeleteContactFromEvent(EventList.retrieve(), ContactName); 
					}  	
						//we check if the current event have no contacts
						if(EventList.retrieve().getContactNames().equals("")) {
							// if it doesn't we remove it
						EventList.remove();
					}
						//if the contact isn't there we go next .
						else {
						EventList.findNext();
					}
					}
				}
			}
			//we do the same process for the last event . 
			Event LastEvent = EventList.retrieve();
			if(LastEvent.getIsAppointment()) { 
				if(LastEvent.getContacts()[0].getName().equals(ContactName)) {
					EventList.remove();
				}
			}else {
				if(LastEvent.getContactNames().contains(ContactName)) {
					DeleteContactFromEvent(EventList.retrieve(), ContactName);
				}
				if(LastEvent.getContactNames().equals("")) {
				EventList.remove();
			}
			}
		}
		
	
	
	private  void DeleteContactFromEvent(Event Event , String ContactName) {
		//we get the contact's number and the event list
		Contact[] contacts = Event.getContacts();
		int NumOfContacts = Event.getContactNumber();	
		int index = 0 ; 
		
		for (int i = 0 ; i < NumOfContacts ; i++) {
			//we go through the loop to find the index of the contact we want to delete
			if(contacts[i].getName().equals(ContactName)) {
				index = i ; //if its found we assign the index and break out of the loop
				break ; 
			}
		}
		//we shift the contacts over the given index and the contact will be removed . 
		for(int i = index ; i < NumOfContacts-1 ; i++) {
			contacts[i] = contacts[i+1] ;
		}
		contacts[Event.getContactNumber()- 1] = null; // Set the last element to null
	}

	public void DisplayEventAlphabetically() {
	// the events are added Alphabetically already (so we can just display it directly ) 
	// and since there is  only one loop that goes from the first to the last of the list 
	// it is considered to be big O(n) . 
	
	if (EventList.empty()) { // if there is no events it prints this message . 
		System.out.println("\nThere are no events Scheduled .");
	} else {
		EventList.findFirst();
		while (!EventList.last()) { // it goes through out the loop and prints the events details
			
			//if the event have no contacts (hence all the contacts in it are deleted)
			if(EventList.retrieve().getContactNames().equals("")) {
				//it prints out that the event have no contacts .(the user )
				System.out.println("The Event "+ EventList.retrieve().getTitle()+ " Dont have any participants! !");
			}else {
			System.out.println("\n" + EventList.retrieve().toString());
			}
			EventList.findNext();
		}
		// this prints the last event since its not included in the top loop 
		System.out.println("\n" + EventList.retrieve().toString());
					}
			}
	
	public void DisplayArrayOfEvents(Event[] EventsFound) { 
		// method  that takes an array of events and displays its details . 
		//it is used in main method combined with the search of events methods .
		
		if(EventsFound.length == 0 || EventsFound[0] == null ) { // it prints this message if there are no events found .
			System.out.println("\nWe Didnt find any Event !");
		}else {
			System.out.println("\nEvents/Appointments Are Found !");
			int Count = 0 ; // it goes through a loop to print the events details .
			while (Count < EventsFound.length ) {
				if(EventsFound[Count] != null )
				System.out.println("\n" + EventsFound[Count].toString());
				Count++ ; 
			}
		}

	}
	
	public void PhoneBookApp() {
		int UserInput = -1;
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the BST Phonebook! ");
		do {

			try {
				// we have 8 options to choose from  : 
			System.out.println("\n*******************************");
			System.out.println("\nPlease choose an option: ");
			System.out.println("1. Add a contact ");
			System.out.println("2. Search for a contact  ");
			System.out.println("3. Delete a contact  ");
			System.out.println("4. Schedule an Event / Appointment  ");
			System.out.println("5. Print event details ");
			System.out.println("6. Print contacts by first name ");
			System.out.println("7. Print all events alphabetically ");
			System.out.println("8. Exit ");
			System.out.println("\n*******************************");

			System.out.print("Enter your choice: ");

			UserInput = input.nextInt();
			input.nextLine();

			if (UserInput == 1) {
				//if the user input is 1 we begin to ask him for the contact details .
				System.out.print("\nEnter the contact's name: ");
				String name = input.nextLine().trim();

				System.out.print("Enter the contact's phone number: ");
				String phoneNumber = input.nextLine().trim();

				System.out.print("Enter the contact's email address: ");
				String emailAddress = input.nextLine().trim();

				System.out.print("Enter the contact's address: ");
				String address = input.nextLine().trim();

				System.out.print("Enter the contact's birthday: ");
				String birthday = input.nextLine().trim();

				System.out.print("Enter any notes for the contact: ");
				String notes = input.nextLine().trim();
				
				
                //then creating the contact and adding it . 
				Contact NewContact = new Contact(name, phoneNumber, emailAddress, address, birthday, notes);
				AddContactByOrder(NewContact);
			}

			else if (UserInput == 2) {
				//if the user input is 2 we ask him for the criteria of search that he wants .
                // we have 5 options of search and they are :
				System.out.println("\nEnter search criteria: ");
				System.out.println("1. Name ");
				System.out.println("2. Phone Number ");
				System.out.println("3. Email Address ");
				System.out.println("4. Address  ");
				System.out.println("5. Birthday ");
				System.out.println("-----------------------");
				System.out.print("Enter your choice: ");
				UserInput = input.nextInt();
				input.nextLine();
                                                       
			    if (UserInput == 1) {
				            // if the input is 1 , that means the search will be by name .
					        System.out.print("\nEnter the contact's name: ");
					        Contact ContactFound = SearchByName(input.nextLine().trim()) ;
					        if(ContactFound != null ) {
					        System.out.println("Contact found!");
					        System.out.println("\n" + ContactFound.toString());
					        }else {
					        	System.out.println("Contact not found !");
					        }
				          } 
				            
			    else if (UserInput == 2) {
					        // if the input is 2 , that means the search will be by Phone Number .
					        System.out.print("\nEnter the contact's Phone Number: ");
					        Contact ContactFound = SearchByPhoneNumber(input.nextLine().trim()) ;
					        if(ContactFound != null ) {
					        System.out.println("Contact found!");
					        System.out.println("\n" + ContactFound.toString());
					        }else {
					        	System.out.println("Contact not found !");
					        }
					        
				          } 
				            
			                else if (UserInput == 3) {
						        // if the input is 3 , that means the search will be by Email Address .
					        System.out.print("\nEnter the contact's Email Address: ");
					        DisplayArrayOfContacts(SearchByEmailAddress(input.nextLine().trim())) ;
					        //We used both of the array displaying of array of contacts 
					        //and the search by email address that returners an array .
					       
				          } 
				            
				            else if (UserInput == 4) {   
						        // if the input is 4 , that means the search will be by Address .
					        System.out.print("\nEnter the contact's Address: ");
					        DisplayArrayOfContacts(SearchByAddress(input.nextLine().trim())) ;
					        
			              } 
				            
				            else if (UserInput == 5) {
						        // if the input is 5 , that means the search will be by Birthday .
					        System.out.print("\nEnter the contact's Birthday: ");
					        DisplayArrayOfContacts(SearchByBirthday(input.nextLine().trim())) ;
				                 } 
			              }
         
			else if (UserInput == 3) {
				//if the user input is 3 we ask him for the criteria of delete that he wants .
				System.out.println("\nEnter Delete Criteria");
				System.out.println("1. Delete by contact's name");
				System.out.println("2. Delete by contact's phone number");
				System.out.print("Enter your choice: ");
				UserInput= input.nextInt();
				input.nextLine();
				            if(UserInput == 1) {
				            	//Deleting by contact name .
					        System.out.print("\nEnter contact's name : ");
					        DeleteByName(input.nextLine().trim()) ;
				         }
				            else if(UserInput == 2) {
				            	//Deleting by contact's phone number .
					        System.out.print("\nEnter contact's phone number : ");
					        DeleteByPhoneNumber(input.nextLine().trim()) ;
				              }
			            } 
			
			else if (UserInput == 4) {
				System.out.println("\nEnter type:");
				System.out.println("1. Event");
				System.out.println("2. Appointment");
				System.out.print("Enter your choice: ");
				UserInput= input.nextInt();
				input.nextLine();
				if(UserInput == 1) {
					System.out.print("\nEnter event title: ");
					String title = input.nextLine().trim();
					System.out.print("\nEnter contacts name separated by a comma: ");
					String ContactNames = input.nextLine().trim();
					String[] NamesArray = ContactNames.split("\\s*,\\s*");
					//we have to check that every contact exists . 
					Contact[] EventContacts = new Contact[NamesArray.length] ; 
					int Counter = 0 ;
					boolean ContactsExist = true ; 
					for(int i = 0 ; i < NamesArray.length ; i++) {
						Contact SearchedContact = SearchByName(NamesArray[i]) ; 
						if(SearchedContact != null) {
							EventContacts[Counter] = SearchedContact ; 
							Counter++ ; 
						}else {
							System.out.println("The contact by the name '"+NamesArray[i]+"' couldnt be found in your contacts !");
							ContactsExist = false ;
							break ; 
						}
					}
					if(ContactsExist) {
						System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
						String DateAndTime = input.nextLine().trim();
		                //Asking for the event details . 
						System.out.print("Enter event location: ");
						String Location = input.nextLine().trim();
						Event NewEvent = new Event(title , Location , EventContacts , DateAndTime , false ) ;
						AppointmentAndEventSchedule(NewEvent) ;
					}

				}
				else if(UserInput == 2) {
				System.out.print("\nEnter appointment title: ");
				String title = input.nextLine().trim();
                //we ask the user for the contact related to the event . 
				System.out.print("Enter the contact name: ");
				String ContactName = input.nextLine().trim();
				Contact[] AppointmentContact= new Contact[1] ;
				AppointmentContact[0] = SearchByName(ContactName) ;
				//The contact should EXIST so we can schedule an event with him . 
				if(AppointmentContact[0] != null ) {
				System.out.print("Enter appointment date and time (MM/DD/YYYY HH:MM): ");
				String DateAndTime = input.nextLine().trim();
                //Asking for the event details . 
				System.out.print("Enter appointment location: ");
				String Location = input.nextLine().trim();
				Event NewEvent = new Event(title , Location , AppointmentContact , DateAndTime , true ) ;
				AppointmentAndEventSchedule(NewEvent) ;
				}else {
					// we print this if the contact didn't exist .
					System.out.println("We Couldnt find this contact!");
				                 }
			          } 
			}
			
			else if (UserInput == 5) {
				//if the user input is 5 that means searching for an event . 
				//we have 2 choices for the search of events : 
				System.out.println("Enter search criteria: ");
				System.out.println("1. Event title ");
				System.out.println("2. contact name");
				System.out.print("Enter your choice: ");
				UserInput= input.nextInt() ;
				input.nextLine();

				if(UserInput == 1) { 
					//we ask for the title and display the events that have the same title . 
					System.out.print("Enter event title: ");
					DisplayArrayOfEvents(EventSearchByTitle(input.nextLine().trim())) ;
				}
				else if(UserInput == 2) { 
					//we ask for the contact's name and display the events that have the same contact . 
					System.out.print("Enter contact name : ");
					DisplayArrayOfEvents(EventSearchByContactName(input.nextLine().trim())) ;
				                    }

			            } 
			
		    else if (UserInput == 6) {
		    	//option 6 is searching by the first name . 
				System.out.print("Enter first name only : ");
				DisplayByFirstName(input.nextLine().trim()) ;

			} else if (UserInput == 7) {
				//option 7 is displaying all events Alphabetically .   
				 DisplayEventAlphabetically() ; 

			} else if (UserInput == 8) {
				System.out.println("\nThank you for using our app !\nTake Care !\n"
						+ "\nThis app was created by : "
						+ "\nFahad Adel AlJadaan"
						+ "\nNawaf Mohammed AlSaeed "
						+ "\nMishari AlBuhairi ");
			}
			
			else {
				//this case is when the user enter a choice that is not there . Like choosing option 9 .
				System.out.println("\nYou entered a wrong choice");
			}

			} catch (InputMismatchException e) {
				//this handles the case when they enter a valid input in the choices .
				//like the entering string in the choices . 
				
				/*note : we decided to make the try/catch handling 
				  broad since its not our main focus in this project .*/
				
				
				System.out.println("\nInvalid input. Please enter a number.");
				input.nextLine(); // This line consumes the invalid input so we can start again
			} catch (Exception e) {
				System.out.println("\nAn error occurred: " + e.getMessage());
			}

		} while (UserInput != 8);
		input.close();
		//we close the input (since its still open like we used to read files) .
	}
	
	
	
	
	
	
	
	
}
