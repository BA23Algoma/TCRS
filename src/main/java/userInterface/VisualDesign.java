package userInterface;

import java.util.List;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.shape.Rectangle;

public class VisualDesign {
	
//***Building Components***

//Buttons	
	
	//Basic buttons
	Button btClear = new Button ("Clear");
	Button btSubmit = new Button ("Submit");
	Button btSearch = new Button ("Search");
	Button btLogout = new Button ("Logout");
	Button btExit = new Button ("Exit");
	Button btBack = new Button ("Back");
	Button btLogin = new Button("Login");
	Button btDelete = new Button("Delete");
	
	//Record management buttons
	
	Button btEnterAccount = new Button("Enter an Account");
	Button btViewEditAccount = new Button("View/Edit an Account");
	Button btDeleteAccount = new Button("Delete an Account");
	
		//Enter
	
	Button btEnterVehRecord = new Button("Enter a Record");
	Button btEnterDriRecord = new Button("Enter a Record");
	Button btEnterOffRecord = new Button("Enter a Record");
	Button btEnterDriCitRecord = new Button("Enter a Record");
	Button btEnterVehCitRecord = new Button("Enter a Record");
	Button btEnterDriWarRecord = new Button("Enter a Record");
	Button btEnterVehWarRecord = new Button("Enter a Record");
	Button btEnterTrafficSchoolRecord = new Button("Enter a Record");
	
		//View/Edit
	
	Button btViewEditVehRecord = new Button("View/Edit a Record");
	Button btViewEditDriRecord = new Button("View/Edit a Record");
	Button btViewEditOffRecord = new Button("View/Edit a Record");
	Button btViewEditDriCitRecord = new Button("View/Edit a Record");
	Button btViewEditVehCitRecord = new Button("View/Edit a Record");
	Button btViewEditDriWarRecord = new Button("View/Edit a Record");
	Button btViewEditVehWarRecord = new Button("View/Edit a Record");
	Button btViewEditTrafficSchoolRecord = new Button("View/Edit a Record");
	
		//Delete
	
	Button btDeleteVehRecord = new Button("Delete a Record");
	Button btDeleteDriRecord = new Button("Delete a Record");
	Button btDeleteOffRecord = new Button("Delete a Record");
	Button btDeleteDriCitRecord = new Button("Delete a Record");
	Button btDeleteVehCitRecord = new Button("Delete a Record");
	Button btDeleteDriWarRecord = new Button("Delete a Record");
	Button btDeleteVehWarRecord = new Button("Delete a Record");
	Button btDeleteTrafficSchoolRecord = new Button("Delete a Record");
	
	//Record navigation buttons
	
	Button btManageRecordsProvincial = new Button("Manage Records");
	Button btManageRecordsMunicipal = new Button("Manage Records");
	Button btVehicleRecords = new Button("Vehicle Records");
	Button btDriverRecords = new Button("Driver Records");
	Button btWarrants = new Button("Warrants");
	Button btVehicleWarrants = new Button("Vehicle Warrants");
	Button btDriverWarrants = new Button("Driver Warrants");
	Button btCitations = new Button("Citations");
	Button btVehicleCitations = new Button("Vehicle Citations");
	Button btDriverCitations = new Button("Driver Citations");
	Button btTrafficSchool = new Button("Traffic School");
	Button btOfficers = new Button("Officers");
	
	//Report navigation/management buttons
	
	Button btGenerateReportProvincial = new Button("Generate a Report");
	Button btGenerateReportMunicipal = new Button("Generate a Report");
	Button btVehicleInfo = new Button("Vehicle Information");
	Button btDriverInfo = new Button("Driver Information");
	Button btDrivingRecord = new Button("Driving Record");
	Button btCitationSummary = new Button("Citation Summary");
	Button btOutstandingWarrants = new Button("Outstanding Warrants");
	
//Labels
	
	//Login page labels
	
	Label lbTrafficWatch = new Label ("Traffic Watch: ");
	Label lbAppDescription = new Label ("The provincial and municipal system for traffic citations and reporting");
	
	//Feedback labels
	
	Label lbEmptyFields = new Label ("Please fill in all fields.");
	Label lbWrongFormat = new Label ("Incorrect format. Please try again.");
	Label lbNoRecord = new Label ("No record found.");
	Label lbLoginError = new Label("Invalid login. Please try again.");
	Label lbSuccessText = new Label("Successful submission.");
	
	//Instruction labels
	
	Label lbChoose = new Label ("Would you like to?");
	Label lbManage = new Label ("Which information would you like to manage?");
	Label lbReport = new Label ("Which information would you like to report?");
	Label lbEnter = new Label ("Enter information:");
	Label lbEdit = new Label ("View/Edit information:");
	Label lbFilter = new Label ("Filter information:");
	Label lbDelete = new Label ("Delete?");
	
	//Labels for text fields 
	
	Label lbEnterVin = new Label ("Enter the VIN:");
	Label lbEnterLic = new Label ("Enter the license number:");
	Label lbStartDate = new Label ("Enter the start date (YYYY-MM-DD):");
	Label lbEndDate = new Label ("Enter the end date (YYYY-MM-DD):");
	Label lbEnterCitID = new Label ("Enter the citation ID:");
	Label lbEnterWarID = new Label ("Enter the warrant ID:");
	Label lbEnterBadge = new Label ("Enter the badge number:");
	Label lbEnterAcc = new Label ("Enter the account ID:");
	Label lbVin = new Label ("VIN:");
	Label lbPlate = new Label ("License Plate:");
	Label lbMake = new Label ("Make:");
	Label lbModel = new Label ("Model:");
	Label lbYear = new Label ("Year:");
	Label lbRegStat = new Label ("Registration Status:");
	Label lbLic = new Label ("License Number:");
	Label lbFirst = new Label ("First Name:");
	Label lbLast = new Label ("Last Name:");
	Label lbPoints = new Label ("Demerit Points:");
	Label lbLicStat = new Label ("License Status:");
	Label lbDate = new Label ("Date (YYYY-MM-DD):");
	Label lbReason = new Label ("Reason:");
	Label lbOutstanding = new Label ("Outstanding:");
	Label lbCitID = new Label ("Citation ID:");
	Label lbWarID = new Label("Warrant ID:");
	Label lbAccID = new Label("Account ID");
	Label lbFine = new Label ("Fine Amount (CAD):");
	Label lbIssuingOff = new Label ("Issuing Officer Badge Number:");
	Label lbPaid = new Label ("Payment Status:");
	Label lbReportable = new Label ("Reportable to Provincial Agency:");
	Label lbSess1 = new Label ("Session 1 (2h) date (YYYY-MM-DD):");
	Label lbSess2 = new Label ("Session 2 (2h) date (YYYY-MM-DD):");
	Label lbSess3 = new Label ("Session 3 (2h) date (YYYY-MM-DD):");
	Label lbSess4 = new Label ("Session 4 (2h) date (YYYY-MM-DD):");
	Label lbSess1Att = new Label ("Session 1 :");
	Label lbSess2Att = new Label ("Session 2 :");
	Label lbSess3Att = new Label ("Session 3 :");
	Label lbSess4Att = new Label ("Session 4 :");
	Label lbBadge = new Label ("Badge number:");
	Label lbUsername = new Label ("Username:");
	Label lbPassword = new Label ("Password:");
	Label lbAgency = new Label ("Agency:");	
	
//Text fields
	
	TextField tfVin = new TextField();
    TextField tfStartDate = new TextField();
    TextField tfEndDate = new TextField();
    TextField tfEnterCitID = new TextField();
    TextField tfEnterWarID = new TextField();
    TextField tfEnterAcc = new TextField();
    TextField tfPlate = new TextField();
    TextField tfMake = new TextField();
    TextField tfModel = new TextField();
    TextField tfYear = new TextField();
    TextField tfLic = new TextField();
    TextField tfFirst = new TextField();
    TextField tfLast = new TextField();
    TextField tfPoints = new TextField();
    TextField tfDate = new TextField();
    TextField tfReason = new TextField();
    TextField tfOutstanding = new TextField();
    TextField tfCitID = new TextField();
    TextField tfWarID = new TextField();
    TextField tfAccID = new TextField();
    TextField tfFine = new TextField();
    TextField tfIssuingOff = new TextField();
    TextField tfSess1 = new TextField();
    TextField tfSess2 = new TextField();
    TextField tfSess3 = new TextField();
    TextField tfSess4 = new TextField();
    TextField tfBadge = new TextField();
    TextField tfUsername = new TextField();
    TextField tfPassword = new TextField(); 
    
//Password Field
    
    PasswordField pfPassword = new PasswordField();
    
//Combo boxes
    
    ComboBox<String> cbAgencyLogin = new ComboBox<>(FXCollections.observableArrayList(
            "Provincial", "Municipal", "Administration"));
    ComboBox<String> cbAgency = new ComboBox<>(FXCollections.observableArrayList(
            "Provincial", "Municipal", "Administration"));
    ComboBox<String> cbRegStat = new ComboBox<>(FXCollections.observableArrayList(
            "Valid", "Stolen", "Wanted", "Expired"));
    ComboBox<String> cbLicStat = new ComboBox<>(FXCollections.observableArrayList(
            "Valid", "Suspended", "Revoked", "Expired"));
    ComboBox<String> cbOutstanding = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No"));
    ComboBox<String> cbReasonDriv = new ComboBox<>(FXCollections.observableArrayList(
            "Speeding", "Reckless Driving", "Distracted Driving", "DUI"));
    ComboBox<String> cbReasonVeh = new ComboBox<>(FXCollections.observableArrayList(
            "Paking Violation", "Fix-It Ticket"));
    ComboBox<String> cbReasonDrivVeh = new ComboBox<>(FXCollections.observableArrayList(
            "Speeding", "Reckless Driving", "Distracted Driving", "DUI", "Parking Violation", "Fix-It Ticket"));
    ComboBox<String> cbPaid = new ComboBox<>(FXCollections.observableArrayList(
            "Paid", "Unpaid"));
    ComboBox<String> cbReportable = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No", "TBD"));
    ComboBox<String> cbSessAtt1 = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No", "TBD"));
    ComboBox<String> cbSessAtt2 = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No", "TBD"));
    ComboBox<String> cbSessAtt3 = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No", "TBD"));
    ComboBox<String> cbSessAtt4 = new ComboBox<>(FXCollections.observableArrayList(
            "Yes", "No", "TBD"));  
    
//Report text 
    
    TextArea taReport = new TextArea();
    
//Bottom rectangle  
    
    Rectangle bottomRectangle = new Rectangle(100,100);
    
//***Assigning styles to components***
    
	public void styleVisualComponents () {	
		
	//Adding warning icon to warning messages
		
		ImageView warningIcon = new ImageView(new Image(getClass().getResourceAsStream("/Resources/icons8-warning-32.png")));
		lbEmptyFields.setGraphic(new ImageView(warningIcon.getImage()));
		lbWrongFormat.setGraphic(new ImageView(warningIcon.getImage()));
        lbNoRecord.setGraphic(new ImageView(warningIcon.getImage()));
        lbLoginError.setGraphic(new ImageView(warningIcon.getImage()));
        
    //Styling buttons
        
        //Basic buttons
        Button[] basicButtons = { btClear, btSearch, btSubmit, btLogout, btExit, btBack, btLogin, btDelete };

        for (Button basicButton : basicButtons) {
            basicButton.getStyleClass().add("small-buttons");
        }
        
        //Record management buttons
        
        Button[] recordManagementButtons = { btEnterAccount, btViewEditAccount, btDeleteAccount, 
        	    btEnterVehRecord, btEnterDriRecord, btEnterOffRecord, btEnterDriCitRecord,
        	    btEnterVehCitRecord, btEnterDriWarRecord, btEnterVehWarRecord, btEnterTrafficSchoolRecord,
        	    btViewEditVehRecord, btViewEditDriRecord, btViewEditOffRecord, btViewEditDriCitRecord,
        	    btViewEditVehCitRecord, btViewEditDriWarRecord, btViewEditVehWarRecord, btViewEditTrafficSchoolRecord,
        	    btDeleteVehRecord, btDeleteDriRecord, btDeleteOffRecord, btDeleteDriCitRecord,
        	    btDeleteVehCitRecord, btDeleteDriWarRecord, btDeleteVehWarRecord, btDeleteTrafficSchoolRecord };


        for (Button button : recordManagementButtons) {
            button.getStyleClass().add("big-buttons"); 
        }
        
        //Record navigation buttons
        
        Button[] navigationButtons = { 
        	    btManageRecordsProvincial, btManageRecordsMunicipal, btVehicleRecords, btDriverRecords, btWarrants, btVehicleWarrants,
        	    btDriverWarrants, btCitations, btVehicleCitations, btDriverCitations,
        	    btTrafficSchool, btOfficers
        	};

        for (Button button : navigationButtons) {
        	    button.getStyleClass().add("big-buttons"); 
        	}
        
        //Report navigation/management buttons
        
        Button[] reportManagementButtons = { 
        	    btGenerateReportProvincial, btGenerateReportMunicipal, btVehicleInfo, btDriverInfo, btDrivingRecord, 
        	    btCitationSummary, btOutstandingWarrants
        	};

        	for (Button button : reportManagementButtons) {
        	    button.getStyleClass().add("big-buttons");
        	}
        	
    //Styling labels
        	
    	//Login page labels
        	
        lbTrafficWatch.getStyleClass().add("title");
        lbAppDescription.getStyleClass().add("app-description");
        
        //Feedback labels
        
        lbEmptyFields.getStyleClass().add("warning-label");
        lbWrongFormat.getStyleClass().add("warning-label");
        lbNoRecord.getStyleClass().add("warning-label");
        lbLoginError.getStyleClass().add("warning-label");
        lbSuccessText.getStyleClass().add("success-label");

        //Big labels
        Label [] bigLabels = { lbChoose, lbManage, lbReport, lbEnterVin,lbEnterLic,lbEnterCitID, lbEnterWarID, lbEnterBadge, lbEnterAcc};
        
        for(Label label: bigLabels){
        	label.getStyleClass().add("big-label");
        }
        
        //Medium labels
        
        Label[] mediumLabels = {lbEnter,lbEdit,lbFilter,lbDelete};
        
        for (Label label:mediumLabels) {
        	label.getStyleClass().add("medium-label");
        }
        
        //Small labels
        
        Label[] smallLabels = { 
        	    lbVin, lbPlate, lbMake, lbModel, lbYear, lbRegStat, lbLic, lbFirst, lbLast, lbPoints, lbLicStat, lbDate,
        	    lbReason, lbOutstanding, lbCitID, lbWarID, lbAccID, lbFine, lbIssuingOff, lbPaid, lbReportable, lbSess1, lbSess2,
        	    lbSess3, lbSess4, lbSess1Att, lbSess2Att, lbSess3Att, lbSess4Att, lbBadge, lbUsername, lbPassword, lbAgency,
        	    lbStartDate, lbEndDate
        	};

        	for (Label label : smallLabels) {
        	    label.getStyleClass().add("small-label");
        	}

    //Styling text fields 
        	
        TextField[] textFields = {
        	    tfStartDate, tfEndDate, tfEnterCitID, tfEnterWarID, tfEnterAcc,
        	    tfVin, tfPlate, tfMake, tfModel, tfYear, tfLic, tfFirst, tfLast,
        	    tfPoints, tfDate, tfReason, tfOutstanding, tfCitID, tfWarID, tfAccID,
        	    tfFine, tfIssuingOff, tfSess1, tfSess2, tfSess3, tfSess4, tfBadge,
        	    tfUsername, tfPassword
        	};

        	for (TextField textField : textFields) {
        	    textField.getStyleClass().add("text-fields"); // Add style class
        	}
        	
        pfPassword.getStyleClass().add("big-text-fields");
        pfPassword.setPromptText("Password"); 
        
    //Styling combo boxes
   
        cbAgencyLogin.getStyleClass().add("big-combo-boxes");
        cbAgencyLogin.setPromptText("Agency");
     
        List<ComboBox<String>> stringComboBoxes = Arrays.asList(
        	    cbAgency, cbRegStat, cbLicStat, cbOutstanding, cbReasonDriv, cbReasonDrivVeh,
        	    cbReasonVeh, cbPaid, cbReportable, cbSessAtt1, cbSessAtt2,
        	    cbSessAtt3, cbSessAtt4
        	);

        	for (ComboBox<String> comboBox : stringComboBoxes) {
        	    comboBox.getStyleClass().add("combo-boxes"); // Add style class
        	}
        	
    //Styling report text
        	
        taReport.getStyleClass().add("report-text");
        
    //Styling bottom rectangle 
        
        bottomRectangle.getStyleClass().add("bottom-rectangle");
    
	}
	
//***Setting default settings for visual components***
	
	public void resetVisualComponentSettings() {
		
		//Setting report text to non-editable and wrap on
		taReport.setWrapText(true);
		taReport.setEditable(false);
		
		//Making text fields for auto-generated fields non-editable
		
		TextField[] NotToBeEditedTextFields={tfCitID,tfWarID,tfAccID};
		
		for (TextField textField: NotToBeEditedTextFields) {
			textField.setEditable(false);
		}

        //Hiding feedback labels
		
        Label[] feedbackLabels = {lbLoginError, lbEmptyFields, lbWrongFormat, lbNoRecord, lbSuccessText};
        
        for (Label feedbackLabel : feedbackLabels) {
            feedbackLabel.setVisible(false);
        }
        //Reversing autoFill set text on fields and combo boxes
        TextField[] textFieldsFilled = { 
			    tfStartDate, tfEndDate, tfEnterCitID, tfEnterWarID, tfEnterAcc, tfVin, tfPlate, tfMake, tfModel, 
			    tfYear, tfLic, tfFirst, tfLast, tfPoints, tfDate, tfReason, tfOutstanding, tfCitID, tfWarID, 
			    tfAccID, tfFine, tfIssuingOff, tfSess1, tfSess2, tfSess3, tfSess4, tfBadge, tfUsername, tfPassword, pfPassword
			};

			for (TextField textField : textFieldsFilled) {
			    textField.setText(""); 
			   
			}
		ComboBox<?>[] comboBoxesFilled = { 
				    cbAgency, cbRegStat, cbLicStat, cbOutstanding, cbReasonDriv, cbReasonVeh, 
				    cbPaid, cbReportable, cbSessAtt1, cbSessAtt2, cbSessAtt3, cbSessAtt4 
				};

				for (ComboBox<?> comboBox : comboBoxesFilled) {
				    comboBox.setValue(null); 
				}
     //Reversing setFieldsUneditable
        
        //Text Fields
        
        TextField[] textFieldsMadeUneditable = { 
			    tfStartDate, tfEndDate, tfEnterCitID, tfEnterWarID, tfEnterAcc, tfVin, tfPlate, tfMake, tfModel, 
			    tfYear, tfLic, tfFirst, tfLast, tfPoints, tfDate, tfReason, tfOutstanding, tfCitID, tfWarID, 
			    tfAccID, tfFine, tfIssuingOff, tfSess1, tfSess2, tfSess3, tfSess4, tfBadge, tfUsername, tfPassword, pfPassword
			};

			for (TextField textField : textFieldsMadeUneditable) {
			    textField.setEditable(true); 
			   
			}

		//Combo Boxes
			
		ComboBox<?>[] comboBoxesMadeUneditable = { 
			    cbAgency, cbRegStat, cbLicStat, cbOutstanding, cbReasonDriv, cbReasonVeh, 
			    cbPaid, cbReportable, cbSessAtt1, cbSessAtt2, cbSessAtt3, cbSessAtt4 
			};

			for (ComboBox<?> comboBox : comboBoxesMadeUneditable) {
			    comboBox.setDisable(false); 
			}
        
	}
	
	//***Setting fields as non-editable for delete pages***
	public void setPrimaryKeyFieldsUneditable () {
			TextField[] textFields = { 
			    tfVin, tfLic, tfCitID, tfWarID, tfAccID, tfBadge
			};

			for (TextField textField : textFields) {
			    textField.setEditable(false); 
			   
			}
	}
	public void setAllFieldsUneditable () {
		
			//Text fields
		
			TextField[] textFields = { 
			    tfStartDate, tfEndDate, tfEnterCitID, tfEnterWarID, tfEnterAcc, tfVin, tfPlate, tfMake, tfModel, 
			    tfYear, tfLic, tfFirst, tfLast, tfPoints, tfDate, tfReason, tfOutstanding, tfCitID, tfWarID, 
			    tfAccID, tfFine, tfIssuingOff, tfSess1, tfSess2, tfSess3, tfSess4, tfBadge, tfUsername, tfPassword, pfPassword
			};

			for (TextField textField : textFields) {
			    textField.setEditable(false); 
			   
			}

			//Combo-boxes
			
			ComboBox<?>[] comboBoxes = { 
			    cbAgency, cbRegStat, cbLicStat, cbOutstanding, cbReasonDriv, cbReasonVeh, 
			    cbPaid, cbReportable, cbSessAtt1, cbSessAtt2, cbSessAtt3, cbSessAtt4 
			};

			for (ComboBox<?> comboBox : comboBoxes) {
			    comboBox.setDisable(true); 
			}
	}
	
  
}
