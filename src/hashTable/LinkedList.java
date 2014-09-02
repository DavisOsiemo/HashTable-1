package hashTable;

public class LinkedList<E> {
	/*
	 *  Head points to the beginning node of the list.
	 *  Tail points to the ending node of the list.
	 *  Size will keep track of the total number of nodes in the list.
	 */
	private Node<E> head, tail;
	private int size;
	
	/*
	 * Generic Linked List Class to be
	 * implemented in NewHashTable<K,V>.
	 * Only necessary methods have been included.
	 * @author Mitch Aman
	 * 
	 */
	
	
	/**
	 * Constructor method
	 * Initialize an empty linked list
	 */
	public LinkedList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * 
	 * @param element - The element to be added to the end
	 * of the linked list.
	 */
	public void add(E element){
		// Condition 1: element to add is null - cannot be null.
		if(element == null){
			throw new IllegalArgumentException("Data cannot be null.");
		}
		
		// Condition 2: the list is empty - head and tail will both be new node.
		else if(isEmpty()){
			head = new Node<E>(element, null);
			tail = head;
		}
		
		// Condition 3: the list is not empty - new node will become the tail.
		else{
			tail.addNodeAfter(element);
			tail = tail.getLink();
		}
		
		// Increment the size by one.
		size++;
	}

	/**
	 * 
	 * @param element - The element to be removed from the list
	 * @return - True if the element has been found & removed.  False
	 * if the element was not found in the list.
	 */
	public boolean remove(E target){
		/*
		 *  A cursor node to traverse the Linked List.
		 *  A precursor node to traverse one node behind the cursor.
		 */
		Node<E> cursor = head, precursor = null;
		
		// If the list is empty, target cannot be in it.
		if(size == 0)
			return false;
		
		// Search for the target to remove
		while(cursor != null && !cursor.getData().equals(target)){
			precursor = cursor;
			cursor = cursor.getLink();
		}
		
		// If the target is not found, return false.
		if(cursor == null){
			return false;
		}
		
		// Target has been found
		
		// Condition A: Target is Head
		if(cursor == head)
			head = head.getLink();
			
		// Condition B: Target is Tail
		else if(cursor == tail){
			tail = precursor;
			tail.removeNodeAfter();
		}
		
		// Condition C: Target is neither head nor tail
		else{
			precursor.removeNodeAfter();
		}
		
		// Decrement the size, and return true
		size--;
		return true;

		
	}
	
	/**
	 * 
	 * @param target - the target to return
	 * @return the target if found, otherwise null.
	 */
	public E get(E target){
		/*
		 *  A cursor node to traverse the Linked List.
		 *  A precursor node to traverse one node behind the cursor.
		 */
		Node<E> cursor = head, precursor = null;
		
		// If the list is empty, target cannot be in it.
		if(size == 0)
			return null;
		
		// Search for the target to remove
		while(cursor != null && !cursor.getData().equals(target)){
			precursor = cursor;
			cursor = cursor.getLink();
		}
		
		// If the target is not found, return null.
		if(cursor == null){
			return null;
		}
		
		// Target has been found at cursor
		return cursor.getData();
		
		
	}
	
	
	/**
	 * 
	 * @param index - the index to obtain, where the head is index 0.
	 * @return the target if found, otherwise null.
	 */
	public E get(int index){
		/*
		 *  A cursor node to traverse the Linked List.
		 *  A precursor node to traverse one node behind the cursor.
		 */
		Node<E> cursor = head;
		if(index >= size)
			return null;
		else{
			// Find position index in the the linked list
			for(int i = 0; i < index; i++)
				cursor = cursor.getLink();
		}
		return cursor.getData();
	}
	
	
	/**
	 * 
	 * @param target - The element whose existence is in question.
	 * @return true if the linked list contains the target; otherwise false.
	 */
	public boolean contains(E target){
		Node<E> cursor = head;
		// Traverse the linked list
		while(cursor != null){
			E data = cursor.getData();
			if(data.equals(target))
				return true;
			else
				cursor = cursor.getLink();
		}
		// Not found
		return false;
	}
	
	
	/**
	 * @return - The number of nodes in the linked list.
	 */
	public int getSize(){
		return size;
	}
	
	
	/**
	 * 
	 * @return True if the list is empty, otherwise false.
	 */
	public boolean isEmpty(){
		return (size == 0);
	}	
	

	/**
	 * @return - A string representation of the list
	 */
	public String toString(){
		return toString(head);
	}
	
	/**
	 * An auxilary method for the toString() method to allow recursion.
	 * Traverses through the linked list, printing each node's data's toString()
	 * @param head - the head of the linked list
	 * @return A string representation of the list.
	 */
	private String toString(Node<E> head){
		if(size>0){
			//Not last item
			if(head.getLink()!=null)
				return "[" + head.getData().toString() + "]" + ", " + toString(head.getLink());
			//last item, no comma
			else
				return "[" + head.getData().toString() + "]";
		}
		return "";			
		
	}
	
}
