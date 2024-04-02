package userInterface;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.time.Year;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation extends SceneNavigation {

	
	class FormatValidation {

	private Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
	

	public boolean validateVIN(String vin) {
		
		String type = "VIN";
		int vinLength = 17;
		
		// Check the length of the VIN
		if (!isValidLength(vin,vinLength, type)) {
			return false;
		}
		else if (containsSpecialChar(vin, type)) {
			return false;
		}

		return true;
		
	}
	public boolean validateLicensePlate(String plate) {
		
		String type = "License plate";
		int plateLength = 7;
		
		// Check the length of the VIN
		if (!isValidLength(plate, plateLength,type)){
			return false;
		}
		else if (containsSpecialChar(plate, type)) {
			return false;
		}
		else if (!isAlpha(plate, 0, 3) || !isNumber(plate, 4, 6)) {
			System.out.println("Incorrect format for license plate!");
			System.out.println("License Plate Format: XXXXNNN");
			return false;
		}

		return true;
	}
	
	public boolean validateMake(String make) {
		
		// Check make input contains only letters
		return isAlpha(make);
		
	}
	
	public boolean validateModel(String model) {
		
		String type = "model";
		
		// Check no special characters
		return !containsSpecialChar(model, type);
		
	}
	public boolean validateYear(String year) {
		
		if (year.length() != 4) {
			System.out.println("Incorrect year format!");
			return false;
		}
		else if (!isNumber(year)) {
			return false;
		}
		
		int currYear = Integer.valueOf(year);
		
		int latestYear = Year.now().getValue() + 1; 
		
		if (currYear < 1960 || latestYear < currYear) {
			System.out.println("Incorrect year! \nPleae enter correct year of car");
			return false;
		}
		
		return true;
	}
	public boolean validateFirstName(String firstName) {
		
		// Check if only letters entered
		return isAlpha(firstName);
		
	}
	public boolean validateLastName(String lastName) {
		
		// Check if only letters entered
		return isAlpha(lastName);
				
	}
	public boolean validateLicenseNumber(String licenseNumber) {
		
		String type = "LICENSENUMBER";
		int licenseLength = 15;
		
		// Check length
		if (!isValidLength(licenseNumber, licenseLength, type)) {
			return false;
		}
		else if (!isAlpha(licenseNumber, 0, 1)) {
			return false;
		}
		else if (!isNumber(licenseNumber, 1, licenseLength - 1)) {
			return false;
		}
		return true;
		
	}
	public boolean validateDemeritPoints(String demeritPoints) {
		
		if(!isNumber(demeritPoints)) {
			return false;
		}
		
		int dPoint = Integer.valueOf(demeritPoints);
		
		if (dPoint < 0 || 99 < dPoint)
			return false;
		
		return true;
		
	}
	public boolean validateBadgeNumber(String badge) {
		
		if(!isNumber(badge)) {
			return false;
		}
		
		int badgeNumber = Integer.valueOf(badge);
		
		int badgeStart = 10000;
		
		if (badgeNumber < badgeStart )
			return false;
		
		
		return true;
		
	}
	public boolean validateCitationID(String citation) {
		
		if(!isNumber(citation)) {
			return false;
		}
		
		int citationID = Integer.valueOf(citation);
		
		// Ensure number is valid
		if (negativeNum(citationID) || citationID == 0) {
			return false;
		}
		
		return true;
		
	}
	public boolean validateFineAmount(String fine) {
		
		if(!isNumber(fine)) {
			return false;
		}
		
		Double fineAmount = Double.valueOf(fine);
		
		return negativeNum(fineAmount);
	}
	public boolean validateDate(String date) {
		
		String type = "date";
		
		if(!isValidLength(date, 10, type)){
			return false;
		}
		
		return dateFormat(date);
		
	}
	public boolean validateWarrantID(String warrant) {
		
		if(!isNumber(warrant)) {
			return false;
		}
		
		int warrantID = Integer.valueOf(warrant);
		
		// Ensure number is valid
		if (negativeNum(warrantID) || warrantID == 0) {
			return false;
		}
		
		return true;
		
	}
	public boolean validateAccountID(String account) {
		
		if(!isNumber(account)) {
			return false;
		}
		
		int accountID = Integer.valueOf(account);
		
		// Ensure number is valid
		if (negativeNum(accountID) || accountID == 0) {
			return false;
		}
		
		return true;
		
	}
	

	//************************ Helper Methods **********************
	
	// Check if the length is the correct
	private boolean isValidLength(String data, int length, String category) {
		
		if (data.length() == length)
			return true;
		
		System.out.println(String.format("%s is the wrong length!", category));
		return false;
	}
	
	// Check for special characters
	private boolean containsSpecialChar(String str, String category) {
		
		Matcher m = p.matcher(str);
		
		if (m.find()) {
			System.out.println(String.format("Invalid character in %s!", category));
			return true;
		}
		return false;
	}
	
	// Check if only letters
	
	private boolean isAlpha(String str) {
	    char[] chars = str.toCharArray();

	    for (char c: chars) {
	        if(!Character.isLetter(c)) {
	        	System.out.println("Character other than letters was entered!\nPlease review");
	            return false;
	        }
	    }

	    return true;
	}
	
	// Check if only letters, with range
	private boolean isAlpha(String str, int begin, int end) {
	    char[] c = str.toCharArray();

	    for (int i = begin; i < end; i++ ) {
	        if(!Character.isLetter(c[i])) {
	        	System.out.println("Incorrect character was entered!\nPlease review");
	            return false;
	        }
	    }

	    return true;
	}
	
	// Check if only numbers, with range
		private boolean isNumber(String str) {
		    
			return isNumber(str, 0, (str.length() - 1));
		}
		
	
	// Check if only numbers, with range
	private boolean isNumber(String str, int begin, int end) {
	    char[] c = str.toCharArray();

	    for (int i = begin; i < end; i++ ) {
	        if(!Character.isDigit(c[i])) {
	        	System.out.println("Non number was found in incorrect position!");
	            return false;
	        }
	    }

	    return true;
	}
	
	private boolean dateFormat(String date) {
		
		char seperator = '-';
		String monthString = date.substring(0,2);
		String dayString = date.substring(3,5);
		String yearString = date.substring(6);
		
		int month = Integer.valueOf(monthString);
		int day = Integer.valueOf(dayString);
		int year = Integer.valueOf(yearString);
		int numDays = 31;  
				
		int firstYear = 2024;
		
		String invalid = "Invalid date input";
		
		if (month < 1 || month > 12) {
			System.out.println(invalid);
			return false;
		}
		
		numDays = numOfDays(month, year);
		
		if (day < 1 || numDays < day || year < firstYear) {
			System.out.println(invalid);
			return false;
		}
	
		
		return Character.isDigit(date.charAt(0)) &&
        Character.isDigit(date.charAt(1)) &&
        Objects.equals(date.charAt(2), seperator) &&
        Character.isDigit(date.charAt(3)) &&
        Character.isDigit(date.charAt(4)) &&
        Objects.equals(date.charAt(5), seperator) &&
        Character.isDigit(date.charAt(6)) &&
        Character.isDigit(date.charAt(7)) &&
        Character.isDigit(date.charAt(8));
		
	}
	
	private int numOfDays(int month, int year) {
		int days = 31;
		
		switch (month) {
				
				case 1: case 3: case 5: 
				case 7: case 8: case 10: 
				case 12:{
					days = 31;
					break;
				}
				case 4: case 6: case 9:
				case 11:{
					days = 30;
					break;
				}
				case 2:{
					if (Year.of(year).isLeap())
						days = 29;
					else
						days = 28;
					break;
				}
					
				}
		
		return days;
	}
	
	private boolean negativeNum(int value) {
		
		if (value < 0) {
			System.out.println("Amount must be greater than zero!");
			return true;
		}
		
		return false;
	}
	
private boolean negativeNum(double value) {
		
		if (value < 0) {
			System.out.println("Amount must be greater than zero!");
			return true;
		}
		
		return false;
	}


}

public Boolean fieldFormatTest(Node rootNode) {
	
	
	FormatValidation formatValidator = new FormatValidation();
	
	boolean formatTestPassed=true;
	
	BorderPane currentPane = (BorderPane) rootNode;
	
	//Data Scene
	if (currentPane.getCenter()==null) {
		
	HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);
	
    for (Node node : FieldsVBox.getChildren()) {
        if (node instanceof TextField ) {
        	TextField textField = (TextField) node;
        	 if (textField==tfVin && !formatValidator.validateVIN(textField.getText())) {	        		
        		textField.setStyle("-fx-border-color:#FA3E3E;");
        		
        		formatTestPassed=false;
        	 }
        	 if (textField==tfPlate && !formatValidator.validateLicensePlate(textField.getText())) {	        		
	        	textField.setStyle("-fx-border-color:#FA3E3E;");
	        	
	        	formatTestPassed=false;
        	 }
        	 if (textField==tfMake && !formatValidator.validateMake(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfModel && !formatValidator.validateModel(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfFirst && !formatValidator.validateFirstName(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfLast && !formatValidator.validateLastName(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfLic && !formatValidator.validateLicenseNumber(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if ((textField==tfStartDate||textField==tfEndDate||textField==tfSess1|| textField==tfSess2||textField==tfSess3||textField==tfSess4)&& !formatValidator.validateDate(textField.getText())) {	        		
	        		textField.setStyle("-fx-border-color:#FA3E3E;");
	        		
	        		formatTestPassed=false;
	        	 }
        	 if (textField==tfYear && !formatValidator.validateYear(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfPoints && !formatValidator.validateDemeritPoints(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfBadge && !formatValidator.validateBadgeNumber(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfCitID && !formatValidator.validateCitationID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfWarID && !formatValidator.validateWarrantID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfFine && !formatValidator.validateFineAmount(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfAccID && !formatValidator.validateAccountID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
       }
        
       
       }
    }
   
    //Search Scene 
	} else {
		
	VBox centerContentVBox = (VBox) currentPane.getCenter();
	
	for (Node node : centerContentVBox.getChildren()) {
        if (node instanceof TextField ) {
        	TextField textField = (TextField) node;
        	if (textField==tfLic && !formatValidator.validateLicenseNumber(textField.getText())) {	        		
	        	textField.setStyle("-fx-border-color:#FA3E3E;");
	      
	        	formatTestPassed=false;
        	 }
        	 if (textField==tfVin && !formatValidator.validateVIN(textField.getText())) {	        		
	        		textField.setStyle("-fx-border-color:#FA3E3E;");
	        		
	        		formatTestPassed=false;
        	 }
        	 if ((textField==tfStartDate||textField==tfEndDate) && !formatValidator.validateDate(textField.getText())) {	        		
	        		textField.setStyle("-fx-border-color:#FA3E3E;");
	        	
	        		formatTestPassed=false;
	        }
        	 if (textField==tfBadge && !formatValidator.validateBadgeNumber(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }	
        	 if (textField==tfCitID && !formatValidator.validateCitationID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfWarID && !formatValidator.validateWarrantID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
	        	 }
        	 if (textField==tfAccID && !formatValidator.validateAccountID(textField.getText())) {	        		
		        	textField.setStyle("-fx-border-color:#FA3E3E;");
		        	
		        	formatTestPassed=false;
        	 	}
        	}
        	
    	} 
	}
return formatTestPassed;
}

public Boolean emptyFieldsTest(Node rootNode) {
	
	boolean emptyTestPassed=true;
	
	BorderPane currentPane = (BorderPane) rootNode;
	
	//Data Scene
	if (currentPane.getCenter()==null) {
		
	HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);
	
    for (Node node : FieldsVBox.getChildren()) {
        if (node instanceof TextField ) {
        	TextField textField = (TextField) node;
        	 if (textField.getText().trim().isEmpty() && textField.isEditable()) {
        		textField.setStyle("-fx-border-color:#FA3E3E;");
        		
        		emptyTestPassed=false;
        	
        	 }
        }
        
        if (node instanceof ComboBox ) {
        	ComboBox<?> comboBox = (ComboBox<?>) node;
        	if(comboBox.getSelectionModel().isEmpty() && !comboBox.isDisabled()){
        		comboBox.setStyle("-fx-border-color:#FA3E3E;");
        	
        		emptyTestPassed=false;
        	}
        }
    }  //Search Scene 
	} else {
		
	VBox centerContentVBox = (VBox) currentPane.getCenter();
	
	for (Node node : centerContentVBox.getChildren()) {
        if (node instanceof TextField ) {
        	TextField textField = (TextField) node;
        	 if (textField.getText().trim().isEmpty()) {
        		textField.setStyle("-fx-border-color:#FA3E3E;");
        		
        		emptyTestPassed=false;
        	 }
        }
        
        if (node instanceof ComboBox ) {
        	ComboBox<?> comboBox = (ComboBox<?>) node;
        	if(comboBox.getSelectionModel().isEmpty()){
        		comboBox.setStyle("-fx-border-color:#FA3E3E;");
        		
        		emptyTestPassed=false;
        	}
        }
    } 
	}
return emptyTestPassed;
}



}
