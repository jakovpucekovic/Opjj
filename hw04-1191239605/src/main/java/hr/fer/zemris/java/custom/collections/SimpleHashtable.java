package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

public class SimpleHashtable<K,V> {

	public static class TableEntry<K,V>{
		
		private K key;
		private V value;
		TableEntry<K,V> next;
	
		public TableEntry(K key, V value, TableEntry<K,V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
	}
	
	private TableEntry<K,V>[] table;
	private int size;
	private static final int DEFAULT_SIZE = 16;
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K,V>[])new TableEntry[DEFAULT_SIZE];
	}
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be smaller that 1.");
		}
		int actualSize = 2;
		while(actualSize < capacity) {
			actualSize *=2;
		}
		
		table = (TableEntry<K,V>[]) new TableEntry[actualSize];
	}
	
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		int index = hash(key);
		
		if(table[index] == null ) {
			table[index] = new TableEntry<>(key, value, null);
			size++;
		} else {
			TableEntry<K,V> entry = table[index];
			if(entry.key.equals(key)) {
				entry.value = value;
				return;
			}
			while(entry.next != null) {
				if(entry.next.key.equals(key)) {
					entry.value = value;
					return;
				}
				entry = entry.next;
			}
			entry.next = new TableEntry<>(key, value, null);
			size++;
		}
	}
	
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		TableEntry<K,V> temp = findEntryFromKey(key);
		if(temp != null) {
			return temp.value;
		} else {
			return null;
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean containsKey(Object key) {
		return findEntryFromKey(key) != null;
	}

	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length ; i++) {
			TableEntry<K,V> entry = table[i];
			while(entry != null) {
				if(entry.value.equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}
	
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		int index = hash(key);
		if(table[index].key.equals(key)) {
			if(table[index].next == null) {
				table[index] = null;
			}else {
				table[index] = table[index].next;
			}
			size--;
			
		} else {
			TableEntry<K,V> entry = table[index];
			while(entry.next != null && !entry.next.key.equals(key)) {
				entry = entry.next;
			}
			if(entry.next == null) {
				return;
			}
			if(entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				size--;
			}
		}
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder().append('[');
		for(int i = 0; i < table.length ; i++) {
			TableEntry<K,V> entry = table[i];
			while(entry != null) {
				str.append(entry.key).append('=').append(entry.value).append(", ");
				entry = entry.next;
			}
		}
		str.delete(str.length()-2, str.length());
		str.append(']');
		return str.toString();
	}
	
	private int hash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
	private TableEntry<K,V> findEntryFromKey(Object key){
		if(key == null) {
			return null;
		}
		int index = hash(key);
		if(table[index].key.equals(key)) {
			return table[index];
		} else {
			TableEntry<K,V> find = table[index].next;
			while(find != null && !find.key.equals(key)) {
				find = find.next;
			}
			if(find == null) {
				return null;
			} else {
				return find;
			}
		}
	}
	
	
}
