package com.aa.act.interview.org;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.swing.RootPaneContainer;

public abstract class Organization {

	private Position root;
	
	
	//This is a made up code to help fulfill the Employee Identification number which no information was provided
	public static final String ORGANIZATIONAL_CODE = "134";
	
	public static int employeeCount = 100;
	
	public Organization() {
		root = createOrganization();
		size = root.getDirectReports().size();
	
	}
	
	public static int size;
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	
	
	public Optional<Position> hire(Name person, String title) {
		//your code here
		try {
	    Optional<Position> position = findPositionByTitle(root, title); //we find the position if it is present or filled
	    
	    if (position.isPresent()) { //we check if it is present in the optional object
	        
	    	Employee employee = new Employee(createIdentification(), person); //we construct a new employee
	        position.get().setEmployee(Optional.of(employee));//we set the new employee to that position
	        return position; // we then return that position optional object as filled
	        
	    }
	    
		} catch(NoSuchElementException noSuchElementException) {
	    	System.out.println("The Position " + title + " Does not exist");
	    }
	    return Optional.empty();//returns the optional position as empty if the condition above returns false
	}
	
	/**
	 * findPosition is an method that links together the employee's title and the position
	 * first it checks to see if both titles in the Position object and the string value title passed through hire() in the main method
	 * if so it returns the object
	 * if not we loop through direct reports in Position to find if the position is actually present
	 * if it is present then we return that position
	 * else we report that it is empty.
	 * 
	 * @param position
	 * @param titles
	 * @return a position if the title is filled or if it is present else we return empty
	 * '*/
	private Optional<Position> findPositionByTitle(Position position, String title) {
	    if (position.getTitle().equals(title)) {
	        return Optional.of(position);
	    }
	    for (Position directReport : position.getDirectReports()) {
	        Optional<Position> result = findPositionByTitle(directReport, title);
	        if (result.isPresent()) {
	            return result;
	        }
	    }
	    return Optional.empty();
	}
	
	
	/**
	 * I created this as a way of creating some sort of Identification for an employee
	 * as there was no information provided so I created a constant that held the organizational code which
	 * represents the entire organization and concatenated a value that increments once the
	 * identification code is created
	 * @return employee identification number
	 * */
	public static int createIdentification() {
		int id = Integer.parseInt(ORGANIZATIONAL_CODE + employeeCount);
		employeeCount += 1;
		return id;
	}
	
	

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
