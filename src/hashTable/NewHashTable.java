package hashTable;
public class NewHashTable<K, V> {

	/**
	 * @author Mitch Aman
	 * Due: December 2, 2013
	 * Data Structures - Clark
	 * 
	 * 
	 */
	
	/*
	 * Hash table implements a single array holding
	 * LinkedLists of type EntrySet<K,V>.  Uses chaining
	 * for collision resolution, meaning elements are 
	 * hashed to index key.hashCode() % index,
	 * at the next available position in the LinkedList.
	 */
	
	private int capacity; // The total number of buckets.
	private int size; // The number of keys in this hashtable.
	private double loadFactor; // The load factor of the hashtable
	
	private Object[] data;
	
	
	
	/**
	 * Constructs a new, empty hashtable with the specified initial capacity
	 * and the specified load factor.
	 * @param initialCapacity - The initial capacity of the hashtable.
	 * @param loadFactor - The load factor of the hashtable.
	 */
	public NewHashTable(int initialCapacity, double loadFactor){
		setInitialCapacity(initialCapacity);
		setLoadFactor(loadFactor);
		size = 0;
		data = new Object[capacity];
	}
	
	
	/**
	 *  Constructs a new, empty hashtable with the specified initial capacity
	 *  and default load factor (0.75).
	 * @param initialCapacity - The initial capacity of the hashtable.
	 */
	public NewHashTable(int initialCapacity){
		setInitialCapacity(initialCapacity);
		setLoadFactor(0.75);
		size = 0;
		data = new Object[capacity];
	}
	
	/**
	 * Constructs a new, empty hashtable with a default initial capacity (11)
	 * and load factor (0.75).
	 */
	public NewHashTable(){
		setInitialCapacity(11);
		setLoadFactor(0.75);
		size = 0;
		data = new Object[capacity];
	}
	
	
	/**
	 *  Returns the number of keys in this hashtable.
	 * @return - The number of keys in this hashtable
	 */
	public int size(){
		return size;
	}
	
	
	/**
	 * Tests if this hashtable maps no keys to values.
	 * @return - True if this hashtable maps no keys to values; false otherwise.
	 */
	public boolean isEmpty(){
		return (size == 0);
	}
	
	
	/**
	 * Tests if the specified object is a key in this hashtable.
	 * @param key - possible key.
	 * @return true if and only if the specified object is a key in this hashtable,
	 * as determined by the equals method; false otherwise.
	 * @throws NullPointerException if the key is null
	 */
	public boolean containsKey(K key){
		// The index to where the key would be hashed
		int index = Math.abs(key.hashCode()%capacity);
		
		// If there is no LinkedList at data[index], the key is not mapped.
		if(data[index] == null)
			return false;
		
		else{
			// The LinkedList at data[index]
			LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[index];
			// If the list contains the EntrySet with key, the hashTable contains key.
			if(list.contains(new EntrySet<K,V>(key)))
				return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * Returns true if this hashtable maps one or more keys to this value.
	 * @param value - value whose presence in this hashtable is to be tested
	 * @return true if this map maps one or more keys to the specified value.
	 * @throws NullPointerException - if the value is null.
	 */
	public boolean containsValue(V value){
		if(value == null)
			throw new NullPointerException();
		
		//Loop through array
		for(int i = 0; i < data.length; i++){
			//If data[i] is null, value cannot be in there
			if(data[i] != null){
				LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[i];
				//loop through the LinkedList at index i
				for(int j = 0; j < list.getSize(); j++){
					// Found
					if(list.get(j).value == value){
						return true;
					}
					
				}
			}
		}
		
		
		return false;
	}
	
	
	/**
	 * Increases the capacity of and internally organizes this hashtable, in order to accommodate
	 * and access its entries more efficiently.  This method is called automatically when the 
	 * number of keys in the hashtable exceeds this hashtable's capacity and load factor.
	 */
	private void rehash(){
		//Double the size
		int newCapacity = this.capacity*2;
		
		// Create new array
		Object[] newData = new Object[newCapacity];
		
		// Traverse through data[] and hash all EntrySets to newData[]
		for(int i = 0; i < data.length; i++){
			//If data[i] is null, there's no list at that index
			if(data[i] != null){
				//Loop through the linked list at data[i]
				LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[i];
				EntrySet<K,V> currentSet;
				int newIndex;
				for(int j = 0; j < list.getSize(); j++){
					currentSet = list.get(j);
					//find new index
					newIndex = Math.abs(currentSet.key.hashCode()%newCapacity);
					//If there is no list at new index, initialize it.
					if(newData[newIndex] == null){
						newData[newIndex] = new LinkedList<EntrySet<K,V>>();
					}
					// add currentSet to the list at newData[newIndex]
					LinkedList<EntrySet<K,V>> newList = (LinkedList<EntrySet<K,V>>) newData[newIndex];
					newList.add(currentSet);
				}
			}
		}
		
		data = newData;
		capacity = newCapacity;
		System.out.println("New capacity: " + capacity);
			
		
	}
	
	
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no
	 * mapping for the key.
	 * 
	 * More formally, if this map contains a mapping from a key k to a value v such
	 * that (key.equals(k)), then this method return v; otherwise it returns null (there can
	 * be at most one such mapping.)
	 * 
	 * @param key - the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or null if this map contains no
	 * mapping for the key.
	 * 
	 * @throws NullPointerException - if the specified key is null.
	 */
	public V get(K key){
		if(key == null)
			throw new NullPointerException();
		int index = Math.abs(key.hashCode() % capacity);
		
		// If data[index] does not contain a LinkedList, then the key does not map the value.
		if(data[index] == null)
			return null;
		
		//Obtain the LinkedList at data[index].
		LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[index];
		
		// Obtain the corresponding value for the key, or null if the list does not contain it.
		V val = list.get(new EntrySet<K,V>(key)).value;
		return val;
	}
	
	
	
	/**
	 * Maps the specified key to the specified value in this hashtable  Neither the key nor the value
	 * can be null.
	 * 
	 * The value can be retrieved by calling the get method with a key that is equal to the original
	 * key.
	 * @param key - the hashtable key
	 * @param value - the value
	 * @return the previous value of the specified key in this hashtable, or null if it did not have one.
	 * 
	 * @throws NullPointerException if the key or value is null
	 */
	public V put(K key, V value){
		if(key == null||value == null)
			throw new NullPointerException();
		
		//obtain old value to mapped key, or null if it did not have one, then remove the key.
		V oldVal = null;
		if(containsKey(key)){
			oldVal=get(key);
			remove(key);
		}
			
		// Create new EntrySet with provided key and value
		EntrySet<K,V> set = new EntrySet<K,V>(key,value);
		// Find index to be stored at
		int index = Math.abs(key.hashCode()%capacity);
		
		// If the index's LinkedList has not been initialized yet, initialize it.
		if(data[index] == null){
			data[index] = new LinkedList<EntrySet<K,V>>();
		}
		
		// The Linked List at data[index]
		LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[index];
		
		// Add the set to the list
		list.add(set);
		
		//Increment size
		size++;
		
		//If hashtable is now over the load factor, rehash.
		if((double)size/capacity >= loadFactor){
			System.out.println("rehashing..");
			rehash();
		}
			
		
		return oldVal;
	}
	
	
	/**
	 * Removes the key (and its corresponding value) from this hashtable.  This method does nothing
	 * if the key is not in the hashtable.
	 * 
	 * @param key - the key that needs to be removed.
	 * @return the value to which the key has been mapped in this hashtable, or null if the key did
	 * not have a mapping.
	 * @throws NullPointerException - if the key is null.
	 */
	public V remove(K key){
		if(key == null)
			throw new NullPointerException();
		
		//Obtain the value, or null if it doesn't exist.
		V val = get(key);
		
		//The key is in the hashtable
		if(val != null){
			//find index where key is hashed
			int index = Math.abs(key.hashCode()%capacity);
			//obtain the list at data[index]
			LinkedList<EntrySet<K,V>> list = (LinkedList<EntrySet<K,V>>) data[index];
			
			//remove the EntrySet containing the key and value from the list at data[index].
			list.remove(new EntrySet<K,V>(key));
			size--;	
		}
		return val;			
	}
	
	
	/**
	 * Clears this hashtable so that it contains no keys.
	 */
	public void clear(){
		data = new Object[capacity];
		size = 0;
	}
	
	
	/**
	 * Returns a string representation of this hashtable object in the form of a set of entries,
	 * enclosed in braces and separated by the ASCII characters ", " (comma and space).  Each
	 * entry is rendered as the key, and equals sign =, and the associated element, where
	 * the toString method is used to convert the key and element to strings.
	 * 
	 * @overrides toString in class Object
	 * @returns a string representation of this hashtable.
	 */
	public String toString(){
		String str = "";
		LinkedList<EntrySet<K,V>> list;
		int numLists = 0;
		//Loop through data array
		for(int index = 0; index < capacity; index++){
			//If there is a list at data[index], call its toString.
			if(data[index] != null){
				list = (LinkedList<EntrySet<K,V>>) data[index];
				str += list.toString();
				if(list.getSize() != 0)
					str += ", ";
				numLists++;
			}
		}
		//Take substring to remove last comma and space.
		return str.substring(0,str.length()-2);
	}
	
	
	/**
	 * Auxilary method for constructor's use to error check loadFactor.
	 * @param loadFactor - The load factor of the hashtable.
	 */
	private void setLoadFactor(double loadFactor){
		if(loadFactor < 0.01)
			throw new IllegalArgumentException("Load factor may not be less than 1%.");
		else
			this.loadFactor = loadFactor;
	}
	
	
	/**
	 * Auxilary method for constructor's use to error check intialCapacity.
	 * @param initialCapacity - The initial capacity of the hashtable.
	 */
	private void setInitialCapacity(int initialCapacity){
		if(initialCapacity < 1)
			throw new IllegalArgumentException("Intial Capacity cannot be less than 1.");
		else
			capacity = initialCapacity;
	}
	
}
