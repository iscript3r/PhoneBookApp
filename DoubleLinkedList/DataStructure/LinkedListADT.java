

class Node<T> {
	public T data;
	public Node<T> next;

	public Node() {
		data = null;
		next = null;
	}

	public Node(T value) {
		data = value;
		next = null;
	}
}

public class LinkedListADT<T> {
	private Node<T> head, current;

	public LinkedListADT() {
		head = null;
		current = null;
	}
	
	public boolean empty() {
		return head == null;
	}
	
	public boolean last() {
		return current.next == null;
	}
	
	public boolean full () {
		return false;
	}
	
	public void findfirst() {
		current = head;
	}

	public void findNext() {
		current = current.next;
	}

	public T retrieve() {
		return current.data;
	}

	// this is the same as update
	public void update(T val) {
		current.data = val;
	}

	// This method (Works just like the regular insert ), it adds the Contact AFTER
	// the current .
	public void insertAfter (T value) {
		Node<T> tmp;
		if (empty()) {
			current = head = new Node<T> (value);
		}
		else {
			tmp = current.next;
			current.next = new Node<T> (value);
			current = current.next;
			current.next = tmp;
		}
	}


	// This method bellow adds the Contact BEFORE the current(Even if the current
	// was the HEAD ) .
	public void insertBefore(T val) {
		Node<T> NewNode = new Node<T>(val);
		if (empty()) {
			head = current = NewNode;
		} else {
			NewNode.next = current;
			Node<T> Temp = head;
			if (current != head) {
				while (Temp.next != current)
					Temp = Temp.next;
				Temp.next = NewNode;
				current = NewNode;
			} else {
				head = current = NewNode;
			}
		}
	}

	public void Delete() {
		if (current == head) {
			head = head.next;
			current = head;
		} else {
			Node<T> Temp = head;
			while (Temp.next != current)
				Temp = Temp.next;
			Temp.next = current.next;

			if (current.next == null) {
				current = head;
			} else {
				current = current.next;
			}
		}
	}


}
