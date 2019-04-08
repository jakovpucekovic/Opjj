package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors; 

public class StudentDatabase {

	private List<StudentRecord> list;
	private Map<String, StudentRecord> index;
	
	public StudentDatabase(List<String> inputList) {
		list = new ArrayList<>();
		index = new HashMap<>();
		
//		index = list.stream()
//			.map(x -> new StudentRecord(x))
//			.collect(Collectors.toMap(StudentRecord::getJmbag, x->x ));
		
		for(var x : inputList) {
			StudentRecord rec = new StudentRecord(x);
			if(index.containsKey(rec.getJmbag())) {
				throw new IllegalArgumentException("Jmbag is already contained.");
			}
			list.add(rec);
			index.put(rec.getJmbag(), rec);
		}	
	}
	
	
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> returnList = new ArrayList<>();
		for(var i : list) {
			if(filter.accepts(i)) {
				returnList.add(i);
			}
		}
		return returnList;
	}

}
