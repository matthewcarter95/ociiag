package iag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oracle.bmc.identity.model.Compartment;
import com.oracle.bmc.identity.model.Policy;
import com.oracle.bmc.identity.responses.ListPoliciesResponse;


public class WhatUserHasAccessTo {

	public static void main(String[] args) throws Exception {
		//ListCompartmentsResponse lcResponse = OCICompartment.list();
		List<String> compartmentList = OCICompartment.listCompartmentOcids();
		List<String> groupList = new ArrayList<String>();
		List<String> statementList = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();

		/*
		 * Get all groups that the user belongs to
		 */
		groupList = OCIMembership.list(args[0]);
		

		/*
		 * Get all policy statements from every compartment and flatten and dedup
		 */

		for(String compartmentOcid : compartmentList) {
            //System.out.println(compartment.getName());
			
			ListPoliciesResponse lpResponse = OCIPolicy.list(compartmentOcid);
			
			for (Policy policy : lpResponse.getItems()) {
				List<String> statements = policy.getStatements();
				//System.out.println("----All policy statements-----");
				for (String statement : statements) {
					//System.out.println(statement);
					statementList.add(statement);
				}
				
			}
        }
		
		Iterator<String> statementIter = statementList.iterator();
		while (statementIter.hasNext()) {
			String statement = statementIter.next().toString();
			System.out.println(statement);
			if (statement.toLowerCase().startsWith("allow any-user")) {
				permissions.add(statement.substring(18));
			}
			for (String group : groupList) {
				
				if (statement.contains(group)) {
					permissions.add(statement.substring(16 + group.length()));
				}
			}
			
		}
		
		ArrayList<String> dedupPermisssions = removeDuplicates(permissions);
		System.out.println("----All permissions for user -----");
		for (int i = 0; i<dedupPermisssions.size(); i++) {
			System.out.println(dedupPermisssions.get(i));
		}
		
		
		
	}
	
	// Function to remove duplicates from an ArrayList 
    public static <String> ArrayList<String> removeDuplicates(List<String> permissions) 
    { 
  
        // Create a new ArrayList 
        ArrayList<String> newList = new ArrayList<String>(); 
  
        // Traverse through the first list 
        for (String element : permissions) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 
  
	


}
