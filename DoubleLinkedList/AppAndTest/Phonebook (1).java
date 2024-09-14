

import java.util.Scanner;
import java.util.InputMismatchException;


public class Phonebook {
	//These are the 4 main that we will use through out the methods
	private LinkedListADT<Contact> ContactList;
	private DoubleLinkedList<Event> EventList;
	private int ContactSize, EventSize;

	public Phonebook() {
		//A constructor that will make a new Contact List & new Event List
		ContactList = new LinkedListADT<Contact>();
		EventList = new DoubleLinkedList<Event>();
		ContactSize = 0;
		EventSize = 0;
	}

	/*					We will have a certain way of organizing the methods : 
	 * 
	 *							we have two main kinds of methods : 
	 *	1#Methods that deals with contacts. 					2#Methods that deals with events .
	 * ---------------------------------------------------------------------------------------------
	 * 									First : Contact methods  	          *note : "all these methods will be 
	 * 					                                                               described specifically later on. "
	 *                     Sections:
	 *                        A - Search methods (By Contact's Name ,Phone Number ,EmailAdress ,address ,Birthday)
	 *                              {From Line 93 To 233 } 
	 *                    
	 *                        B - Add method  (Takes a new contact as a parameter and adds it)
	 *                              {From Line 235 To 278 } 
	 *                              
	 *                        C - Delete methods (By Contact's Name ,Phone Number)
	 *                              {From Line 280 To 338 } 
	 *                              
	 *                        C - Display methods : *A methods that can display multiple contacts that shares the first name .
	 *                        						*A methods that can display all contacts that have been added . 	
	 *                                              *A methods that can display an array of contacts .
	 *                                                   (it's used in the main for the search methods).
     *                                      {From Line 340 To 402 }
     *                                      
	 * ---------------------------------------------------------------------------------------------
	 * 									Secondly : Event methods  	          *note : "all these methods will be 
	 * 					                                                               described specifically later on. "                         										 
                           Sections:
	 *                        A - Search methods (By Events's Title ,Contact's Name) 
	 *                              {From Line 430 To 490 } 
	 *                    
	 *                        B - Add method  (Takes a new Event as a parameter and adds it )
	 *                              {From Line 492 To 526 } 
	 *                              
	 *                        C - Delete methods (By Events's Title)
	 *                              {From Line 528 To 543 } 
	 *                              
	 *                        C - Display methods :*A methods that can display all Events that have been added Alphabetically . 	
	 *                                             *A methods that can display an array of Events .
	 *                                                   (it's used in the main for the search methods).
     *                              {From Line 546 To 583 }
     *                                      
     *                               *An additional helper methods that checks if there is a conflict in an event
	 * 
	 * ---------------------------------------------------------------------------------------------
	 * 									Lastly : The phone book menu 
	 * 	                            {From Line 585 To 808 }
	 *           This will work as the menu that will use all the implemented methods . 
	 *           And make it as a user friendly interface . 
	 * 
	 * 
	 */
	
	  //                                       1#CONTACT METHODS
	
	
	//Contact's Search methods Section .
	private Contact SearchByName(String name) {
		if (ContactList.empty()) { // it return null if the list is empty
			return null;
		}
		ContactList.findfirst();
		while (!ContactList.last()) {          // it continues through the List till it reaches the end
			// we used the trim() method to make sure that there is no blank spaces at the end and beginning of the name
			if (ContactList.retrieve().getName().trim().equalsIgnoreCase(name.trim())) { 
				return ContactList.retrieve();      // if we find a name that is equal to the input we return it directly,
			}                                       // (since the name is always unique)
			ContactList.findNext();
		}
		if (ContactList.retrieve().getName().trim().equalsIgnoreCase(name.trim())) { // We check the last contact since it doesn't get
			return ContactList.retrieve();                                           // checked in the while loop(it stops in the last contact).
		}
		return null;
	}

	
	private Contact SearchByPhoneNumber(String PhoneNumber) {
		/* it goes through the same procedure as the SearchByName method (its explained by details there) ,
		but instead of checking the name it checks the phone number*/
		if (ContactList.empty()) { 
			return null;
		}
		ContactList.findfirst();
		while (!ContactList.last()) {
			if (ContactList.retrieve().getPhoneNumber().equals(PhoneNumber)) { // here it checks if its equal to the phone number
				return ContactList.retrieve();
			}
			ContactList.findNext();
		}
		if (ContactList.retrieve().getPhoneNumber().equals(PhoneNumber)) {
			return ContactList.retrieve();
		}
		return null;
	}
	
	
	private Contact[] SearchByEmailAddress(String EmailAddress) {
		
		/* this method has a similar procedure as the last search methods ,
		 but instead of returning the contact directly it adds it to an array of contact then it returns the whole array .
		 That it because the email address may be equal for different contacts so we want to return them all . */
		
		Contact[] ContactsFound = new Contact[ContactSize] ;  // first it creates a new array by the max size (number of all contacts)
		int FoundCount = 0 ; 
		if (ContactList.empty()) {     // if its empty it returns the empty array
			return ContactsFound;
		}
		ContactList.findfirst();
		while (!ContactList.last()) {
			if (ContactList.retrieve().getEmailAddress().equals(EmailAddress)) {
				ContactsFound[FoundCount] = ContactList.retrieve() ;  // it adds every contact it finds 
				FoundCount++ ;      							      // that have the same email address given .
			}		
			ContactList.findNext();
		}
		if (ContactList.retrieve().getEmailAddress().equals(EmailAddress)) { // checking the last contact since its not included in the loop .
			ContactsFound[FoundCount] = ContactList.retrieve() ; 
			FoundCount++ ; 
		}
		
		// we make a new array fixed to the contacts that have been found only 
		// so it doesn't have a null values in it 
		Contact[] ContactsFoundOnly = new Contact[FoundCount];
		
		//we go through every element in the old array and add it to the fixed array 
		for (int i = 0; i < FoundCount; i++)
			ContactsFoundOnly[i] = ContactsFound[i];
		
		//returning the fixed array
		return ContactsFoundOnly;
	
	}
	
	
	private Contact[] SearchByAddress(String Address) {
		
		/* it goes through the same procedure of the SearchByEmailAddress method (its explained by details there) ,
		but it checks the Contact's Address instead .
		*/
		
		Contact[] ContactsFound = new Contact[ContactSize] ; 
		int FoundCount = 0 ; 
		if (ContactList.empty()) {
			return ContactsFound;
		}
		ContactList.findfirst();
		while (!ContactList.last()) {
			if (ContactList.retrieve().getAddress().equals(Address)) { // here it checks the contact's address
				ContactsFound[FoundCount] = ContactList.retrieve() ; 
				FoundCount++ ; 
			}		
			ContactList.findNext();
		}
		if (ContactList.retrieve().getAddress().equals(Address)) {
			ContactsFound[FoundCount] = ContactList.retrieve() ; 
			FoundCount++ ; 
		}
		Contact[] ContactsFoundOnly = new Contact[FoundCount];
		
		for (int i = 0; i < FoundCount; i++)
			ContactsFoundOnly[i] = ContactsFound[i];
		
		return ContactsFoundOnly;
	}
	
	
	private Contact[] SearchByBirthday(String Birthday) {
		
		/* it goes through the same procedure of the SearchByEmailAddress method(its explained by details there) ,
		 but it checks the Contact's Birthday instead .
		*/
		
		Contact[] ContactsFound = new Contact[ContactSize] ; 
		int FoundCount = 0 ; 
		if (ContactList.empty()) {
			return ContactsFound;
		}
		ContactList.findfirst();
		while (!ContactList.last()) {
			if (ContactList.retrieve().getBirthday().equals(Birthday)) { // here it checks the contac's Birthday
				ContactsFound[FoundCount] = ContactList.retrieve() ; 
				FoundCount++ ; 
			}		
			ContactList.findNext();
		}
		if (ContactList.retrieve().getBirthday().equals(Birthday)) {
			ContactsFound[FoundCount] = ContactList.retrieve() ;
			FoundCount++ ; 
		}
		Contact[] ContactsFoundOnly = new Contact[FoundCount];
		
		for (int i = 0; i < FoundCount; i++)
			ContactsFoundOnly[i] = ContactsFound[i];
		
		return ContactsFoundOnly;
	}
	
	
	//Contact Add method . 
	private void AddContactByOrder(Contact NewContact) {
		if (ContactSize == 0) { // if the there is no contacts in the list its added directly
			ContactList.insertAfter(NewContact);
			ContactSize++;
			System.out.println("\nContact added successfully ! ");
		} 
		
		/*else if there are contacts , we have to check if the given contact is already added 
		 we need to use the search methods to check if we find any contact that hold the same name of phone number .*/
		else if (SearchByName(NewContact.getName()) != null     
				|| SearchByPhoneNumber(NewContact.getPhoneNumber()) != null) { 
			System.out.println("\nThis contact already exists"); // we print this message if its already exist .
		} 
		
		//else if its not duplicate nor the list is empty . 
		else { 
			/*it finds the first contact and it goes in a loop 
			   the loop ends in 2 conditions  
			   #1 the list reach the last contact 
			   #2 the contact name is smaller than the new Contact (we check this using the compareto interface)*/
			ContactList.findfirst();
			while (!ContactList.last() && ContactList.retrieve().compareTo(NewContact.getName()) < 0) { 
				ContactList.findNext();
			}

			//Then if it exit the loop because of the #1 condition(reaching the last contact) .
			//We check if the new contact name is smaller than the last's name 
			if (ContactList.last() && ContactList.retrieve().compareTo(NewContact.getName()) < 0) {
				ContactList.insertAfter(NewContact); // it adds it as the last contact (after the current last)
			}
			
			// else it adds it before the contact that its stopped the loop(whether its the last or not) 
			// hence the current (stopped at) contact's name is smaller than the new contact 
			else {
				ContactList.insertBefore(NewContact);  // we add it before the current contact
				
			}
			System.out.println("\nContact added successfully ! ");
			ContactSize++;

		}
	}

	
	//Contact Delete methods Section . 
	private void DeleteByName(String Name) {
		if (ContactSize == 0) { // checking if the list is empty . 
			System.out.println("\nThere are no contacts in your PhoneBook ."); 
		} else {
			Contact ContactToDelete = SearchByName(Name); 
			//if we find the contact we are looking for we delete the  events related to it
			if (ContactToDelete != null) {
				if (!EventList.empty()) { // we check if there were scheduled events to prevent errors. 
					
					// Delete all events that are related to the deleted contact
					Event[] RelatedEvents = EventSearchByContactName(ContactToDelete.getName());// we get all events related to contact
					int i = 0;
					
					// we loop through the array and delete all the related events 
					while (i < RelatedEvents.length) {
						EventDelete(RelatedEvents[i]);
						i++;
					}
				}
				ContactList.Delete(); // Finally we delete the contact
				System.out.println("\nContact deleted successfully !");
				ContactSize--;
			} else {
				System.out.println("\nContact couldn't be found .");
			}
		}
	}

	
	private void DeleteByPhoneNumber(String PhoneNumber) {
		/* it goes through the same procedure of the DeleteByName method (its explained by details there) ,
		but it deletes by the contact's phone number instead .
		*/
		if (ContactSize == 0) {
			System.out.println("\nThere are no contacts in your PhoneBook .");
		} else {
			Contact ContactToDelete = SearchByPhoneNumber(PhoneNumber);// we search by the phone number
			if (ContactToDelete != null) {

				if (!EventList.empty()) { 	 /* here we used the name to search for the related events,
				 								Why didn't we use the phone number?
									            it doesn't make a difference since the name and phone number
												are always unique for every contact. */
					Event[] RelatedEvents = EventSearchByContactName(ContactToDelete.getName());
					int i = 0;
					while (i < RelatedEvents.length) {
						EventDelete(RelatedEvents[i]);
						i++;
					}
				}
				ContactList.Delete();
				System.out.println("\nContact deleted successfully !");
				ContactSize--;
			} else {
				System.out.println("\nContact couldn't be found.");
			}
		}
	}

	//Contact Display methods Section . 
	private void DisplayByFirstName(String FirstName2) {
		if (ContactSize == 0) { //Checks if the list is empty
			System.out.println("\nThere are no contacts in your PhoneBook .");
		} else {
			ContactList.findfirst();
			while (!ContactList.last()) { // loop till the last contact
				
				// Here we used the split() method to split the first Name from the last Name (or middle name)
				// it splits when it find the space character and adds them in an array ,the 0 index is the FirstName.
				
				String[] PartName = ContactList.retrieve().getName().split(" ");
				String FirstName1 = PartName[0];
				if (FirstName1.equals(FirstName2)) { 
					//if the first name of the current contact is equal to the given name: the contact gets printed .
					System.out.println("\n" + ContactList.retrieve().toString());
				}
				ContactList.findNext();
			}
			String[] PartName = ContactList.retrieve().getName().split(" "); // we do the same for the last contact
			String FirstName1 = PartName[0];
			if (FirstName1.equals(FirstName2)) {
				System.out.println("\n" + ContactList.retrieve().toString());
			}

		}

	}

	
	private void DisplayContactsAlphabetically() { 
		/*this method is not used in the main phone book app , but we implemented it so we can test our adding methods . 
		 * And maybe it could be implemented later as a new feature in the app . 
		 */
		
		if (ContactSize == 0) { // it checks if the list is empty
			System.out.println("\n There are no contacts in your PhoneBook .");
		} else {
			ContactList.findfirst();
			while (!ContactList.last()) {  // it goes through the whole list and retrieve then prints the contact
				System.out.println("\n" + ContactList.retrieve().toString());
				ContactList.findNext();
			}
			System.out.println("\n" + ContactList.retrieve().toString()); //we do the same for the last contact
		}
	}
	
	
	private void DisplayArrayOfContacts(Contact[] ContactsFound) {
		// this method prints an array of contacts
		
		if(ContactsFound.length == 0) {  // it checks the length of the given contact.
			System.out.println("\nWe Didnt find any Contact! ");
		}else {
			System.out.println("\nContacts Are Found !!");
			int Count = 0 ; 
			while (Count < ContactsFound.length ) {
				//it goes through the array and prints the contacts info .
				System.out.println("\n" + ContactsFound[Count].toString()); 
				Count++ ; 
			}
		}
	}

	
	  //                                       2#EVENT METHODS
	
	//Checking event conflict .
	private Event CheckEventConflict(String DateAndTime) { 
		/* We can make this method return a boolean , but we implemented it this way to return an event
		 * so that we can use it in future methods as a (Search by Date And Time ) 
		 * which can be used to improve the program .
		 */
		
		if (EventList.empty()) { //if the event list is empty we return null
			return null;
		}
		EventList.findFirst();
		while (!EventList.last()) { // we go through the list till the last element
			if (EventList.retrieve().getDateAndTime().equalsIgnoreCase(DateAndTime)) {
				return EventList.retrieve(); // if we find an Event that have the same Date and Time as the new Event , 
			}                                // we return that event . 
			EventList.findNext();
		}
		if (EventList.retrieve().getDateAndTime().equalsIgnoreCase(DateAndTime)) { // we check the last event too . 
			return EventList.retrieve();
		}
		return null; // if we didnt find any that have a conflict we return a null 
	} 
 	
	//Event's Search methods Section .
	private Event[] EventSearchByTitle(String Title ) {
		/*this method works exactly like the SearchByEmailAddress method 
		 * but instead it goes through the EVENT list and add the event that is equal to the 
		 * input title ,  then it fixes the array to match the new size , then it returns it . 
		 * 
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
		if (EventList.retrieve().getTitle().equals(Title)) { // checks last 
			AllEventsFound[count] = EventList.retrieve();
			count++;
		}
		Event[] EventsFoundOnly = new Event[count];
		for (int i = 0; i < count; i++)
			EventsFoundOnly[i] = AllEventsFound[i];
		
		return EventsFoundOnly; // return the fixed array
	}
	
	private Event[] EventSearchByContactName(String ContactName  ) {
		/*this method works exactly like the SearchByEmailAddress method 
		 * but instead it goes through the EVENT list and add the event that have the same contact name
		 * as the input name ,  then it fixes the array to match the new size , then it returns it . 
		 * 
		 */
		Event[] AllEventsFound = new Event[EventSize];
		int count = 0;
		if (EventList.empty()) { 
			return AllEventsFound;
		}
		EventList.findFirst();
		while (!EventList.last()) {
			// here it checks the name of the contact with the current contact's bane that the scheduled event is with .
			if (EventList.retrieve().getWithContact().getName().equals(ContactName)) { 
				AllEventsFound[count] = EventList.retrieve();
				count++;
			}
			EventList.findNext();
		}
		if (EventList.retrieve().getWithContact().getName().equals(ContactName)) { // checks last 
			AllEventsFound[count] = EventList.retrieve();
			count++;
		}
		Event[] EventsFoundOnly = new Event[count];
		for (int i = 0; i < count; i++)
			EventsFoundOnly[i] = AllEventsFound[i];
		
		return EventsFoundOnly;
	}

	//Event Schedule method .
	private void EventSchedule(Event NewEvent) {
		if (EventSize == 0) {
			EventList.insert(NewEvent); // if the event list is empty it adds it directly . 
			EventSize++;
			System.out.println("\nEvent scheduled successfully!\n"); 
			// here it checks if the new even have a conflict of time using CheckEventConflict method .
		} else if (CheckEventConflict(NewEvent.getDateAndTime()) == null) {
			EventList.findFirst();
			// it goes through the loop till it finds an event that have a smaller name than the new event and it stops .
			//or it stops if it reaches the last event
			while (!EventList.last() && EventList.retrieve().compareTo(NewEvent.getTitle()) < 0) {
				EventList.findNext();
			}
			//here if it stopped at the last event we check its name is bigger than the new event 
			// then we add the new even at last of the list , or if its smaller then we add it before the last event. 
			if (EventList.last()) {
				if (EventList.retrieve().compareTo(NewEvent.getTitle()) < 0) {
					EventList.insert(NewEvent);
				} else {
					EventList.insertBefore(NewEvent);
				}
			}
			//if it reached here that means it stopped at somewhere in the middle of the list, hence the current 
			//event's name is smaller than the new event's name , then we add it before it(closer to the head) .
			else {
				EventList.insertBefore(NewEvent);
			}
			System.out.println("\nEvent Scheduled successfully!");
			EventSize++;
		} else {
			// we print this message if there was a conflict . 
			System.out.println("\nThere is a conflict in time with other Event !"); 
		}
	}

	//Event Delete method .
	private void EventDelete(Event event) {

		EventList.findFirst();
		//it goes through the list till it finds an event that have the same contact's name as the given event's contact's name . 
		// or it stops if it reaches the end  .
		while (!EventList.last() && EventList.retrieve().getWithContact().getName().equals(event.getWithContact().getName()) != true  ) {
			EventList.findNext();
		}
		//here we check the last element (and it also checks again if the loop stopped cause of the first condition ).
		if (EventList.retrieve().getWithContact().getName().equals(event.getWithContact().getName())) {
			EventList.Delete();
			EventSize--;
		} else {
			System.out.println("\nEvent couldn't be found .");// we print this if we didn't find the required event
		}
	}
	
	//Event's Display methods Section .
	private void DisplayEventAlphabetically() {
		// the events are added Alphabetically already (so we can just display it directly ) 
		// and since there is  only one loop that goes from the first to the last of the list 
		// it is considered to be big O(n) . 
		
		if (EventSize == 0) { // if there is no events it prints this message . 
			System.out.println("\nThere are no events Scheduled .");
		} else {
			EventList.findFirst();
			while (!EventList.last()) { // it goes through out the loop and prints the events details
				System.out.println("\n" + EventList.retrieve().toString());
				EventList.findNext();
			}
			// this prints the last event since its not included in the top loop 
			System.out.println("\n" + EventList.retrieve().toString());
		}
	}

	
	private void DisplayArrayOfEvents(Event[] EventsFound) { 
		// method  that takes an array of events and displays its details . 
		//it is used in main method combined with the search of events methods .
		
		if(EventsFound.length == 0) { // it prints this message if there are no events found .
			System.out.println("\nWe Didnt find any Event !");
		}else {
			System.out.println("\nEvents Are Found !");
			int Count = 0 ; // it goes through a loop to print the events details .
			while (Count < EventsFound.length ) {
				System.out.println("\n" + EventsFound[Count].toString());
				Count++ ; 
			}
		}

	}

	
	  //                                       3#PHONE BOOK APP
	
	public void PhoneBookApp() {
		int UserInput = -1;
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Linked Tree Phonebook! ");
		do {

			try {
				// we have 8 options to choose from  : 
			System.out.println("\n*******************************");
			System.out.println("\nPlease choose an option: ");
			System.out.println("1. Add a contact ");
			System.out.println("2. Search for a contact  ");
			System.out.println("3. Delete a contact  ");
			System.out.println("4. Schedule an event  ");
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
				//if the user input is 4 that means he wants to schedule an event 
				//we will ask him for the event details and add it to to the list.
				System.out.print("\nEnter event title: ");
				String title = input.nextLine().trim();
                //we ask the user for the contact related to the event . 
				System.out.print("Enter contact name:");
				Contact WithContact = SearchByName(input.nextLine().trim()) ;
				//The contact should EXIST so we can schedule an event with him . 
				if(WithContact != null ) {

				System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
				String DateAndTime = input.nextLine().trim();
                //Asking for the event details . 
				System.out.print("Enter event location:");
				String Location = input.nextLine().trim();
				Event NewEvent = new Event(title , Location , WithContact , DateAndTime ) ;
				EventSchedule(NewEvent) ;
				}else {
					// we print this if the contact didn't exist .
					System.out.println("We Couldnt find this contact!");
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
					System.out.print("Enter contact's name : ");
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
