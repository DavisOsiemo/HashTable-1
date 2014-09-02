package hashTable;
/*
 * A simple class to hold the key and value together.
 * EntrySets are equal if they have the same key.
 * (Cannot have multiple keys in a HashTable)
 */

/**
 * @author Mitch
 *
 * @param <K> - The data type of the keys.
 * @param <V> - The data type of the values.
 */
public class EntrySet<K,V> {
	public K key;
	public V value;
	
	/**
	 * Create a new EntrySet with specified key
	 * and value.
	 * 
	 * @param key - the key to be set
	 * @param value - the value to be set
	 */
	public EntrySet(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Create a new EntrySet with specified key
	 * (Because only key is needed for comparison)
	 * @param key - the key to be added
	 */
	public EntrySet(K key){
		this.key=key;
	}
	
	
	/**
	 * @param key - The key to be compared to
	 * @return true if this EntrySet has the same key as provided.
	 */
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		else{
			EntrySet<K,V> obj = (EntrySet<K,V>) o;
			return (key.equals(obj.key));
		}
		
	}
	
	
	
	public String toString(){
		return key.toString() + "=" + value.toString();
	}
}
