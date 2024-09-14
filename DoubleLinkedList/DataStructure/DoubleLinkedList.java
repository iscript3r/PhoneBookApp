 
class DLLNode<T> {
	public T data;
	public DLLNode<T> next;
	public DLLNode<T> previous;

	public DLLNode() {
		data = null;
		next = null;
		previous = null;
	}

	public DLLNode(T value) {
		data = value;
		next = null;
		previous = null;
	}
}

public class DoubleLinkedList<T> {
	private DLLNode<T> head;
	private DLLNode<T> current;

	public DoubleLinkedList() {
		head = null;
		current = null;
	}


	public boolean empty() {
		return head == null;
	}

	public boolean last() {
		return current.next == null;
	}

	public boolean first() {
		return current.previous == null;
	}

	public boolean full() {
		return false;
	}

	public void findFirst() {
		current = head;
	}

	public void findNext() {
		current = current.next;
	}

	public void findPrevious() {
		current = current.previous;
	}

	public T retrieve() {
		return current.data;
	}

	public void update(T value) {
		current.data = value;
	}

	public void insert(T value) {
		DLLNode<T> NewNode = new DLLNode<T>(value);
		if (empty()) {
			head = current = NewNode;
		}else {
		NewNode.next = current.next;
		NewNode.previous = current;
		if (current.next != null)
			current.next.previous = NewNode;
		current.next = NewNode;
		current = NewNode;
		}

	}
	
	// This method bellow adds the Contact BEFORE the current(Even if the current
	// was the HEAD ) ,
	public void insertBefore(T value) {
		DLLNode<T> NewNode = new DLLNode<T>(value);
		if (empty()) {
			head = current = NewNode;
		}else {
		NewNode.next = current ; 
		NewNode.previous = current.previous ; 
		if(current != head) { 
			current.previous.next = NewNode ; 
		}
		current.previous = NewNode ;
		if(current == head) { 
			current = head = NewNode ; 
		}else {
			current = NewNode; 
		}
		}
	}

	public void Delete() {
		if (current == head) {
			head = head.next;
			if (head != null)
				head.previous = null;
		} else {
			current.previous.next = current.next;
			if (current.next != null)
				current.next.previous = current.previous;
		}
		if (current.next == null)
			current = head;
		else
			current = current.next;
	}
	
	
	
}
