package databaseManagement;

import java.util.Stack;

public class NavigationStack {
	
	    private Stack<String> navStack;

	    public NavigationStack() {
	    	navStack = new Stack<>();
	    }

	    public void navigateTo(String page) {
	        // Push the current page onto the navigation stack
	    	navStack.push(page);
	        
	    }

	    public void navigateBack() {
	        if (!navStack.isEmpty()) {
	            // Pop the top page from the navigation stack
	        	navStack.pop();
	            // If there are remaining pages in the stack, display the top page
	            if (!navStack.isEmpty()) {
	                String previousPage = navStack.peek();
	            } else {
	                System.out.println("You are on the initial page.");
	            }
	        } else {
	            System.out.println("Navigation stack is empty.");
	        }
	    }

	
}
