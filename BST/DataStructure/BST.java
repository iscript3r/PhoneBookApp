/****************************
CLASS: BST.java
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

class BooleanWrapper {
	boolean bool;

	public BooleanWrapper(boolean bool) {
		this.bool = bool;
	}

	public boolean get() {
		return bool;
	}

	public void set(boolean Bool) {
		bool = Bool;
	}
}

class BSTNode<T> {
	public String key; 
	public T data;
	public BSTNode<T> left, right;

	public BSTNode(String k, T val) { 
		key = k;
		data = val;
		left = right = null;
	}

	public BSTNode(String k, T val, BSTNode<T> l, BSTNode<T> r) { 
		key = k;
		data = val;
		left = l;
		right = r;
	}
}

public class BST<T> {
	BSTNode<T> root, current;

	public BST() {
		root = current = null;
	}

	public boolean empty() {
		return root == null;
	}

	public boolean full() {
		return false;
	}

	public T retrieve() {
		return current.data;
	}

	public boolean update(String key, T data) { 
		remove_key(current.key);
		return insert(key, data);
	}

	public boolean findkey(String tkey) {
		BSTNode<T> p = root, q = root;

		if (empty())
			return false;

		while (p != null) {
			q = p;
			if (p.key.equals(tkey)) { 
				current = p;
				return true;
			} else if (tkey.compareTo(p.key) < 0) 
				p = p.left;
			else
				p = p.right;
		}

		current = q;
		return false;
	}

	public boolean insert(String k, T val) { 
		BSTNode<T> p, q = current;

		if (findkey(k)) {
			current = q; // findkey() modified current
			return false; // key already in the BST
		}

		p = new BSTNode<>(k, val); 
		if (empty()) {
			root = current = p;
			return true;
		} else {
			if (k.compareTo(current.key) < 0) 
				current.left = p;
			else
				current.right = p;
			current = p;
			return true;
		}
	}


	private BSTNode<T> find_min(BSTNode<T> p) {
		if (p == null)
			return null;

		while (p.left != null) {
			p = p.left;
		}

		return p;
	}

	public boolean remove_key(String tkey) { 
		BooleanWrapper removed = new BooleanWrapper(false);
		BSTNode<T> p;
		p = remove_aux(tkey, root, removed);
		current = root = p;
		return removed.get();
	}

	private BSTNode<T> remove_aux(String key, BSTNode<T> p, BooleanWrapper flag) { 
		BSTNode<T> q, child = null;
		if (p == null)
			return null;
		if (key.compareTo(p.key) < 0) { 
			p.left = remove_aux(key, p.left, flag); // go left
		} else if (key.compareTo(p.key) > 0) { 
			p.right = remove_aux(key, p.right, flag); // go right
		} else { // key is found
			flag.set(true);
			if (p.left != null && p.right != null) { // two children
				q = find_min(p.right);
				p.key = q.key;
				p.data = q.data;
				p.right = remove_aux(q.key, p.right, flag);
			} else {
				if (p.right == null) // one child
					child = p.left;
				else if (p.left == null) // one child
					child = p.right;
				return child;
			}
		}
		return p;
	}

	public void printInorder() {
		printRecursive(root); 
	}
	
	private void printRecursive(BSTNode<T> p) {
		if (p == null)
			return;

		printRecursive(p.left);
		System.out.print(p.data.toString() + "\n");
		printRecursive(p.right);
	}	
	public T searchInorder(String S) {
		return searchRecursive(root , S) ; 
	}
	
	private T searchRecursive(BSTNode<T> p , String S) {
		
        if (p == null) {
            return null; 
        }
        
	      T left = searchRecursive(p.left, S);
	        if (left != null) {
	            return left; 
	        }

	        if (p.data.toString().contains(S)) {
	            return p.data; 
	        }

	        // Now recur on right child
	        return searchRecursive(p.right, S);
	}
}
