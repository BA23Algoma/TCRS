package userInterface;
import databaseManagement.DatabaseManager;
import databaseManagement.RecordValidation;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import CRUD.*;



public class UserInteraction extends SceneNavigation {
	
	private final Stage primaryStage;
	
	DatabaseManager databaseManager = new DatabaseManager();
      
        		
    InputValidation dataValidator = new InputValidation();
    RecordValidation recordValidator = new RecordValidation(databaseManager); 
        Vehicle vehicle = new Vehicle(databaseManager);
        Driver driver = new Driver(databaseManager);
        Officer officer = new Officer(databaseManager);
        Account account = new Account(databaseManager);
        VehicleCitation vehicleCitation = new VehicleCitation(databaseManager);
        DriverCitation driverCitation = new DriverCitation(databaseManager);
        DriverWarrants driverWarrant = new DriverWarrants(databaseManager);
        VehicleWarrant vehicleWarrant = new VehicleWarrant(databaseManager);
        TrafficSchool trafficSchool = new TrafficSchool(databaseManager);
	
        public UserInteraction(Stage primaryStage) {
        
		this.primaryStage = primaryStage;
        
		databaseManager.connectToDatabase();
		
       
     }
	//***Assigning actions to buttons***
	
	public void setButtonActions()  {
		
		//Buttons for basic functions
		 btClear.setOnAction(event -> clearFields(primaryStage.getScene().getRoot()));
		 btExit.setOnAction(event->{
			 exit();
			 sceneStack.clear();});
		 btBack.setOnAction(event -> navigateBack());	 
		 btLogout.setOnAction(event -> {
			 primaryStage.setScene(createLoginScene());
			 sceneStack.clear();});
		 btLogin.setOnAction(event->login());
		 
		 //Buttons to make a data page to enter a record
		 
		 btEnterAccount.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Account Info")));
		 btEnterVehRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Vehicle Info")));
		 btEnterDriRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Driver Info")));
		 btEnterOffRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Officer Info")));
		 btEnterDriCitRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Driver Citation Info")));
		 btEnterVehCitRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Vehicle Citation Info")));
		 btEnterDriWarRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Driver Warrant Info")));
		 btEnterVehWarRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Vehicle Warrant Info")));
		 btEnterTrafficSchoolRecord.setOnAction(event -> primaryStage.setScene(createDataScene("Enter", "Traffic School")));
		 
		 //Buttons to make a search page for viewing/editing records
		 
		 btViewEditAccount.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Account ID", "Edit")));
		 btViewEditVehRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter VIN Vehicle", "Edit")));
		 btViewEditDriRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter License Driver", "Edit")));
		 btViewEditOffRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Badge", "Edit")));
		 btViewEditDriCitRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Driver", "Edit")));
		 btViewEditVehCitRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Vehicle", "Edit")));
		 btViewEditDriWarRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Warrant ID Driver", "Edit")));
		 btViewEditVehWarRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Warrant ID Vehicle", "Edit")));
		 btViewEditTrafficSchoolRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Traffic School", "Edit")));
		
		 
		 //Buttons to make a search page for deleting records
		 
		 btDeleteAccount.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Account ID", "Delete")));
		 btDeleteVehRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter VIN Vehicle", "Delete")));
		 btDeleteDriRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter License Driver", "Delete")));
		 btDeleteOffRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Badge", "Delete")));
		 btDeleteDriCitRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Driver", "Delete")));
		 btDeleteVehCitRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Vehicle", "Delete")));
		 btDeleteDriWarRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Warrant ID Driver", "Delete")));
		 btDeleteVehWarRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Warrant ID Vehicle", "Delete")));
		 btDeleteTrafficSchoolRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Citation ID Traffic School", "Delete")));

		 // Button to make option selection pages 
		 
		 btManageRecordsProvincial.setOnAction(event -> primaryStage.setScene(createOptionScene("Vehicle/Driver")));
		 btManageRecordsMunicipal.setOnAction(event -> primaryStage.setScene(createOptionScene("Citations/Warrants/Officers/Traffic School")));
		 btGenerateReportProvincial.setOnAction(event -> primaryStage.setScene(createOptionScene("Vehicle/Driver/Driving Record Report")));
		 btGenerateReportMunicipal.setOnAction(event -> primaryStage.setScene(createOptionScene("Citation Summary/Outstanding Warrants")));

		 btWarrants.setOnAction(event -> primaryStage.setScene(createOptionScene("Vehicle/Driver Warrants")));
		 btCitations.setOnAction(event -> primaryStage.setScene(createOptionScene("Vehicle/Driver Citations")));
		
		 
		 btVehicleRecords.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Vehicle Record")));
		 btDriverRecords.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Driver Record")));
		 btVehicleWarrants.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Vehicle Warrant Record")));
		 btDriverWarrants.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Driver Warrant Record")));
		 btVehicleCitations.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Vehicle Citation Record")));
		 btDriverCitations.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Driver Citation Record")));
		 btTrafficSchool.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Traffic School Record")));
		 btOfficers.setOnAction(event -> primaryStage.setScene(createOptionScene("Enter/Edit/Delete Officer Record")));

		 //Submit button
		 	//Submit button functioning to enter info into database
		 	
		 	
		 //Delete
		 //Search
		 
		 // Buttons to navigate report section
		 
		 btCitationSummary.setOnAction(event -> primaryStage.setScene(createDataScene("Filter", "Citation Summary")));
		 btVehicleInfo.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter VIN Vehicle Report", "Report")));
		 btDriverInfo.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter License Driver Report", "Report")));
		 btDrivingRecord.setOnAction(event -> primaryStage.setScene(createSearchScene("Enter Driving Record", "Report")));

	
		
		 //btSearch to find a record for either editing or deleting 
		 
		 btDelete.setOnAction(event -> {
			    resetVisualComponentSettings();
			    CreateScene currentScene = sceneStack.peek();

			    switch (currentScene.parameter2) {
			        case "Vehicle Info":
			            deleteVehicle();
			            break;
			        case "Driver Info":
			            deleteDriver();
			            break;
			        case"Officer Info":
			            deleteOfficer();
			            break;
			        case "Account Info":
			            deleteAccount();
			            break;
			        case "Vehicle Citation Info":
			            deleteVehicleCitation();
			            break;
			        case "Driver Citation Info":
			            deleteDriverCitation();
			            break;
			        case "Vehicle Warrant Info":
			            deleteVehicleWarrant();
			            break;
			        case "Driver Warrant Info":
			            deleteDriverWarrant();
			            break;
			        case "Traffic School":
			            deleteTrafficSchool();
			            break;
			    }
			});

		btSearch.setOnAction(event-> {
				
			    resetVisualComponentSettings();
			    CreateScene currentScene = sceneStack.peek();

			    if (currentScene.parameter2.equals("Edit")) {
			        switch (currentScene.parameter1) {
			            case "Enter License Driver":
			                searchLicenseDriverEdit();
			                break;
			            case "Enter VIN Vehicle":
			                searchVinVehicleEdit();
			                break;
			            case "Enter Warrant ID Vehicle":
			                searchWarIDVehicleWarrantEdit();
			                break;
			            case "Enter Warrant ID Driver":
			                searchWarIDDriverWarrantEdit();
			                break;
			            case "Enter Citation ID Vehicle":
			                searchCitIDVehicleCitationEdit();
			                break;
			            case "Enter Citation ID Driver":
			                searchCitIDDriverCitationEdit();
			                break;
			            case "Enter Citation ID Traffic School":
			                searchCitIDTrafficSchoolEdit();
			                break;
			            case "Enter Account ID":
			                searchAccountIDAccountEdit();
			                break;
			            case "Enter Badge":
			                searchBadgeOfficerEdit();
			                break;
			        }
			    } else if (currentScene.parameter2.equals("Delete")) {
			        switch (currentScene.parameter1) {
			            case "Enter License Driver":
			                searchLicenseDriverDelete();
			                break;
			            case "Enter VIN Vehicle":
			                searchVinVehicleDelete();
			                break;
			            case "Enter Warrant ID Vehicle":
			                searchWarIDVehicleWarrantDelete();
			                break;
			            case "Enter Warrant ID Driver":
			                searchWarIDDriverWarrantDelete();
			                break;
			            case "Enter Citation ID Vehicle":
			                searchCitIDVehicleCitationDelete();
			                break;
			            case "Enter Citation ID Driver":
			                searchCitIDDriverCitationDelete();
			                break;
			            case "Enter Citation ID Traffic School":
			                searchCitIDTrafficSchoolDelete();
			                break;
			            case "Enter Account ID":
			                searchAccountIDAccountDelete();
			                break;
			            case "Enter Badge":
			                searchBadgeOfficerDelete();
			                break;
			        }
			    } else if (currentScene.parameter2.equals("Report")) {
			        
			    	switch (currentScene.parameter1) {
		            case "Enter License Driver Report":
		            	searchLicenseReport();
		                break;
		            case "Enter VIN Vehicle Report":
		               searchVinReport();
		                break;		         
		            case "Enter Driving Record":
		                searchDrivingRecordReport();
		                break;
		           
		        }
		    }
			});

			btSubmit.setOnAction(event -> {
				
			    resetVisualComponentSettings();
			    CreateScene currentScene = sceneStack.peek();

			    if (currentScene.parameter1.equals("Enter")) {
			        switch (currentScene.parameter2) {
			            case ("Vehicle Info"):
			                submitEnterVehicle();
			                break;
			            case ("Driver Info"):
			                submitEnterDriver();
			                break;
			            case ("Officer Info"):
			                submitEnterOfficer();
			                break;
			            case ("Account Info"):
			                submitEnterAccount();
			                break;
			            case ("Vehicle Citation Info"):
			                submitEnterVehicleCitation();
			                break;
			            case ("Driver Citation Info"):
			                submitEnterDriverCitation();
			                break;
			            case ("Vehicle Warrant Info"):
			                submitEnterVehicleWarrant();
			                break;
			            case ("Driver Warrant Info"):
			                submitEnterDriverWarrant();
			                break;
			            case ("Traffic School"):
			                submitEnterTrafficSchool();
			                break;
			        }
			    } else if (currentScene.parameter1.equals("View/Edit")) {
			        switch (currentScene.parameter2) {
			            case ("Vehicle Info"):
			                submitEditVehicle();
			                break;
			            case ("Driver Info"):
			                submitEditDriver();
			                break;
			            case ("Officer Info"):
			                submitEditOfficer();
			                break;
			            case ("Account Info"):
			                submitEditAccount();
			                break;
			            case ("Vehicle Citation Info"):
			                submitEditVehicleCitation();
			                break;
			            case ("Driver Citation Info"):
			                submitEditDriverCitation();
			                break;
			            case ("Vehicle Warrant Info"):
			                submitEditVehicleWarrant();
			                break;
			            case ("Driver Warrant Info"):
			                submitEditDriverWarrant();
			                break;
			            case ("Traffic School"):
			                submitEditTrafficSchool();
			                break;
			        }
			    }
			});

	
	}
//***Inserting records into the database***	
		 
//Function on the submit button to enter new vehicle record	
	
	public void deleteVehicle() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfVin) {
                   vehicle.deleteVehicle(textField.getText());
                }
            }
        }
	}

	public void deleteDriver() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfLic) {
                   driver.deleteDriver(textField.getText());
                }
            }
        }
	}

	public void deleteOfficer() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfBadge) {
                   officer.deleteOfficer(textField.getText());
                }
            }
        }
	}

	public void deleteAccount() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfAccID) {
                   account.deleteAccount(textField.getText());
                }
            }
        }
	}

	public void deleteVehicleCitation() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfCitID) {
                   vehicleCitation.deleteVehicleCitation(textField.getText());
                }
            }
        }
	}

	public void deleteDriverCitation() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfCitID) {
                   driverCitation.deleteDriverCitation(textField.getText());
                }
            }
        }
	}

	public void deleteVehicleWarrant() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfWarID) {
                  vehicleWarrant.deleteVehicleWarrant(textField.getText());
                }
            }
        }
	}

	public void deleteDriverWarrant() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfWarID) {
                  driverWarrant.deleteDriverWarrant(textField.getText());
                }
            }
        }
	}

	public void deleteTrafficSchool() {
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

        for (Node node : FieldsVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfCitID) {
                  trafficSchool.deleteEnrollment(Integer.parseInt(textField.getText()));
                }
            }
        }
	}
	

public void submitEditVehicle() {
			
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

		    String vin=null;
		    String plate=null;
		    String make=null;
		    String model=null;
		    String year=null;
		    String regStat=null;

		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfVin) {
		                        vin = textField.getText();
		                    } else if (textField == tfPlate) {
		                        plate = textField.getText();
		                    } else if (textField == tfMake) {
		                        make = textField.getText();
		                    } else if (textField == tfModel) {
		                        model = textField.getText();
		                    } else if (textField == tfYear) {
		                        year = textField.getText();
		                       
		                    }
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbRegStat) {
		                        regStat = comboBox.getValue();
		                    }
		                }
		            }
		            // Call the method to insert the vehicle
		            vehicle.editVehicle(vin, plate, make, model, year, regStat);
		            clearFields(currentPane);
		            showSuccessMessage();
		        } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }
		}
	
public void submitEditDriver() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
    String license=null;
    String plate = null;
    String first=null;
    String last=null;
    String licStat=null;
    String demerit=null;

    if (dataValidator.emptyFieldsTest(currentPane)) {
        if (dataValidator.fieldFormatTest(currentPane)) {
            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfLic) {
                        license = textField.getText();
                    } else if (textField == tfFirst) {
                        first = textField.getText();
                    } else if (textField == tfLast) {
                        last = textField.getText();
                    } else if (textField == tfPlate) {
                        plate = textField.getText();
                    } else if (textField == tfPoints) {
                        demerit = textField.getText();
                        
                    }
                } else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbLicStat) {
                        licStat = comboBox.getValue();
                    }
                }
            }
            // Call the method to insert the vehicle
            driver.editDriver(license, plate, first, last, licStat, demerit);
            clearFields(currentPane);
            showSuccessMessage();
        } else {
            System.out.println("Field format test failed");
            showWrongFormatMessage();
        }
    } else {
        System.out.println("Empty fields test failed");
        showEmptyFieldsMessage();
    }		 
		 }
public void submitEditOfficer() {

    
    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

    String first=null;
    String last=null;
    String badge=null;

    if (dataValidator.emptyFieldsTest(currentPane)) {
        if (dataValidator.fieldFormatTest(currentPane)) {
            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfBadge) {
                       badge = textField.getText();
                      
                    } else if (textField == tfFirst) {
                        first = textField.getText();
                    } else if (textField == tfLast) {
                        last = textField.getText();
                    } 
                }
            }
            // Call the method to insert the vehicle
            officer.editOfficer(badge,first,last);
            clearFields(currentPane);
            showSuccessMessage();
        } else {
            System.out.println("Field format test failed");
            showWrongFormatMessage();
        }
    } else {
        System.out.println("Empty fields test failed");
        showEmptyFieldsMessage();
    }
}		
		
		public void submitEditAccount() {
			BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

		    String first=null;
		    String last=null;
		    String username=null;
		    String password=null;
		    String agency=null;
		    String accountID = null;
		  

		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfUsername) {
		                        username = textField.getText();
		                    } else if (textField == tfPassword) {
		                    	password = textField.getText();
		                    } else if (textField == tfFirst) {
		                        first = textField.getText();
		                    } else if (textField == tfLast) {
		                        last = textField.getText();
		                    } else if (textField == tfAccID) {
		                        accountID = textField.getText();
		                    } 
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbAgency) {
		                        agency = comboBox.getValue();
		                    }
		                }
		            }
		            // Call the method to insert the vehicle
		            account.editAccount(accountID, agency, first, last, username, password);
		            clearFields(currentPane);
		            showSuccessMessage();
		        } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }			
			
			
		}
		public void submitEditVehicleCitation() {
			BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
		String citID=null;
		    String vin=null;
		    String badge=null;
		    String dateIssued=null;
		    String reason=null;
		    String fine=null;
		    String paid=null;

		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfVin) {
		                        vin = textField.getText();
		                    } else if (textField == tfBadge) {
		                        badge = textField.getText();
		                 
		                    } else if (textField == tfDate) {
		                        dateIssued = textField.getText();
		                    } else if (textField == tfFine) {
		                        fine  = textField.getText();
		                     
		                    } else if (textField == tfCitID) {
		                        citID  = textField.getText();
		                     
		                    } 
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbReasonVeh) {
		                        reason = comboBox.getValue();
		                    } else if (comboBox == cbPaid) {
		                    	paid = comboBox.getValue();
		                    }
		                }
		            }
		            // Call the method to insert the vehicle
		            vehicleCitation.editVehicleCitation (citID, vin, badge, dateIssued, reason, fine, paid);
		            clearFields(currentPane);
		            showSuccessMessage();
		        } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }
		}
		public void submitEditDriverCitation() {
			BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

		    String license=null;
		    String badge=null;
		    String dateIssued=null;
		    String reason=null;
		    String  fine=null;
		    String paid=null;
		    String reportable = null;
		    String citID=null;
		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfLic) {
		                        license = textField.getText();
		                    } else if (textField == tfBadge) {
		                        badge = textField.getText();
		                        
		                    } else if (textField == tfDate) {
		                        dateIssued = textField.getText();
		                    } else if (textField == tfFine) {
		                        fine  = textField.getText();
		                    
		                    }  else if (textField == tfCitID) {
		                        citID  = textField.getText();
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbReasonDriv) {
		                        reason = comboBox.getValue();
		                    } else if (comboBox == cbPaid) {
		                    	paid = comboBox.getValue();
		                    } else if (comboBox==cbReportable) {
		                    	reportable = comboBox.getValue();
		                    }
		                }
		            }
		            // Call the method to insert the vehicle
		            driverCitation.editDriverCitation (citID, license, badge, dateIssued, reason, fine, paid, reportable);
		            clearFields(currentPane);
		            showSuccessMessage();
		            }
		            } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }
		    
		}
		public void submitEditVehicleWarrant() {
		    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

		    String vin=null;
		    String dateIssued=null;
		    String reason=null;
		    String outstanding = null;
		    String warID=null;

		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfVin) {
		                        vin = textField.getText();
		                    } else if (textField == tfDate) {
		                        dateIssued = textField.getText();
		                    } else if (textField == tfReason) {
		                        reason = textField.getText();	                    
		                    } else if (textField == tfWarID) {
		                        warID = textField.getText();	                    
		                    } 
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbOutstanding) {
		                    	outstanding = comboBox.getValue();	                        
		                    } 
		                }
		            }
		            // Call the method to insert the vehicle
		            vehicleWarrant.editVehicleWarrant(warID, vin, dateIssued, reason, outstanding);
		            clearFields(currentPane);
		            showSuccessMessage();
		        } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }
		}
		public void submitEditDriverWarrant() {
			BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

		    String license=null;
		    String dateIssued=null;
		    String reason=null;
		    String outstanding = null;
		    String warID = null;
		   

		    if (dataValidator.emptyFieldsTest(currentPane)) {
		        if (dataValidator.fieldFormatTest(currentPane)) {
		            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

		            for (Node node : FieldsVBox.getChildren()) {
		                if (node instanceof TextField) {
		                    TextField textField = (TextField) node;
		                    if (textField == tfLic) {
		                        license = textField.getText();
		                    } else if (textField == tfDate) {
		                        dateIssued = textField.getText();
		                    } else if (textField == tfReason) {
		                        reason = textField.getText();	                    
		                    } else if (textField == tfWarID) {
		                        warID = textField.getText();	                    
		                    } 
		                } else if (node instanceof ComboBox) {
		                    ComboBox<String> comboBox = (ComboBox<String>) node;
		                    if (comboBox == cbOutstanding) {
		                        outstanding = comboBox.getValue();	                     
		                    } 
		                }
		            }
		            // Call the method to insert the vehicle
		            driverWarrant.editDriverWarrant(warID, license, dateIssued,reason,outstanding);
		            clearFields(currentPane);
		            showSuccessMessage();
		        } else {
		            System.out.println("Field format test failed");
		            showWrongFormatMessage();
		        }
		    } else {
		        System.out.println("Empty fields test failed");
		        showEmptyFieldsMessage();
		    }
		}
		public void submitEditTrafficSchool() {
			 BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

			    int citationID=0;
			    String session1Date=null;
			    String session2Date=null;
			    String session3Date=null;
			    String session4Date=null;
			    String session1Attendance=null;
			    String session2Attendance=null;
			    String session3Attendance=null;
			    String session4Attendance=null;


			    if (dataValidator.emptyFieldsTest(currentPane)) {
			        if (dataValidator.fieldFormatTest(currentPane)) {
			            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
			            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

			            for (Node node : FieldsVBox.getChildren()) {
			                if (node instanceof TextField) {
			                    TextField textField = (TextField) node;
			                    if (textField == tfEnterCitID) {
			                        String citationIDString = textField.getText();
			                        citationID = Integer.parseInt(citationIDString);
			                    } else if (textField == tfSess1) {
			                        session1Date = textField.getText();
			                    } else if (textField == tfSess2) {
			                    	session2Date = textField.getText();
			                    } else if (textField == tfSess3) {
			                    	session3Date = textField.getText();
			                    } else if (textField == tfSess4) {
			                    	session4Date = textField.getText();	                        
			                    } 
			                }  else if (node instanceof ComboBox) {
			                    ComboBox<String> comboBox = (ComboBox<String>) node;
			                    if (comboBox == cbSessAtt1) {
			                        session1Attendance = comboBox.getValue();
			                    } else if (comboBox == cbSessAtt2) {
			                        session2Attendance = comboBox.getValue();
			                    }else if (comboBox == cbSessAtt3) {
			                        session3Attendance = comboBox.getValue();
			                    }else if (comboBox == cbSessAtt4) {
			                        session4Attendance = comboBox.getValue();
			                    }
			                }
			            }
			            // Call the method to insert the vehicle
			            //trafficSchool.editEnrollment (citationID, session1Date, session2Date, session3Date, session4Date, 
			            //session1Attendance, session2Attendance, session3Attendance, session4Attendance);
			            clearFields(currentPane);
			            showSuccessMessage();
			            
			        } else {
			            System.out.println("Field format test failed");
			            showWrongFormatMessage();
			        }
			    } else {
			        System.out.println("Empty fields test failed");
			        showEmptyFieldsMessage();
			    }
		}
		
		
public void submitEnterVehicle() {
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String vin=null;
	    String plate=null;
	    String make=null;
	    String model=null;
	    String year=null;
	    String regStat=null;

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfVin) {
	                        vin = textField.getText();
	                    } else if (textField == tfPlate) {
	                        plate = textField.getText();
	                    } else if (textField == tfMake) {
	                        make = textField.getText();
	                    } else if (textField == tfModel) {
	                        model = textField.getText();
	                    } else if (textField == tfYear) {
	                        year = textField.getText();
	                     
	                    }
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbRegStat) {
	                        regStat = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            vehicle.insertVehicle(vin, plate, make, model, year, regStat);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
	
//Function on the submit button to enter new driver record
public void submitEnterDriver() {
	    
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	    String license=null;
	    String plate = null;
	    String first=null;
	    String last=null;
	    String licStat=null;
	    String demerit=null;

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfLic) {
	                        license = textField.getText();
	                    } else if (textField == tfFirst) {
	                        first = textField.getText();
	                    } else if (textField == tfLast) {
	                        last = textField.getText();
	                    } else if (textField == tfPlate) {
	                        plate = textField.getText();
	                    } else if (textField == tfPoints) {
	                        demerit = textField.getText();
	                      
	                    }
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbLicStat) {
	                        licStat = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            driver.insertDriver(license, plate, first, last, licStat, demerit);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new officer record
public void submitEnterOfficer() {
	

    
    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

    String first=null;
    String last=null;
    String badge=null;

    if (dataValidator.emptyFieldsTest(currentPane)) {
        if (dataValidator.fieldFormatTest(currentPane)) {
            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfBadge) {
                       badge = textField.getText();
                      
                    } else if (textField == tfFirst) {
                        first = textField.getText();
                    } else if (textField == tfLast) {
                        last = textField.getText();
                    } 
                }
            }
            // Call the method to insert the vehicle
            officer.insertOfficer(badge,first,last);
            clearFields(currentPane);
            showSuccessMessage();
        } else {
            System.out.println("Field format test failed");
            showWrongFormatMessage();
        }
    } else {
        System.out.println("Empty fields test failed");
        showEmptyFieldsMessage();
    }
}
//Function on the submit button to enter new account	
public void submitEnterAccount() {
		
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String first=null;
	    String last=null;
	    String username=null;
	    String password=null;
	    String agency=null;
	  

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfUsername) {
	                        username = textField.getText();
	                    } else if (textField == tfPassword) {
	                    	password = textField.getText();
	                    } else if (textField == tfFirst) {
	                        first = textField.getText();
	                    } else if (textField == tfLast) {
	                        last = textField.getText();
	                    } 
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbAgency) {
	                        agency = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            account.insertAccount(agency, first, last, username, password);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new vehicle citation		
	public void submitEnterVehicleCitation() {
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String vin=null;
	    String badge=null;
	    String dateIssued=null;
	    String reason=null;
	    String fine=null;
	    String paid=null;

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfVin) {
	                        vin = textField.getText();
	                    } else if (textField == tfBadge) {
	                        badge = textField.getText();
	                 
	                    } else if (textField == tfDate) {
	                        dateIssued = textField.getText();
	                    } else if (textField == tfFine) {
	                        fine  = textField.getText();
	                     
	                    } 
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbReasonVeh) {
	                        reason = comboBox.getValue();
	                    } else if (comboBox == cbPaid) {
	                    	paid = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            vehicleCitation.insertVehicleCitation (vin, badge, dateIssued, reason, fine, paid);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new driver citation		
	public void submitEnterDriverCitation() {
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String license=null;
	    String badge=null;
	    String dateIssued=null;
	    String reason=null;
	    String  fine=null;
	    String paid=null;
	    String reportable = null;

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfLic) {
	                        license = textField.getText();
	                    } else if (textField == tfBadge) {
	                        badge = textField.getText();
	                        
	                    } else if (textField == tfDate) {
	                        dateIssued = textField.getText();
	                    } else if (textField == tfFine) {
	                        fine  = textField.getText();
	                    
	                    } 
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbReasonDriv) {
	                        reason = comboBox.getValue();
	                    } else if (comboBox == cbPaid) {
	                    	paid = comboBox.getValue();
	                    } else if (comboBox==cbReportable) {
	                    	reportable = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            driverCitation.insertDriverCitation (license, badge, dateIssued, reason, fine, paid, reportable);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new driver warrant		
	public void submitEnterDriverWarrant() {
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String license=null;
	    String dateIssued=null;
	    String reason=null;
	    String outstanding = null;
	   

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfLic) {
	                        license = textField.getText();
	                    } else if (textField == tfDate) {
	                        dateIssued = textField.getText();
	                    } else if (textField == tfReason) {
	                        reason = textField.getText();	                    
	                    } 
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbOutstanding) {
	                        outstanding = comboBox.getValue();	                     
	                    } 
	                }
	            }
	            // Call the method to insert the vehicle
	            driverWarrant.insertDriverWarrant(license, dateIssued,reason,outstanding);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new vehicle warrant	
public void submitEnterVehicleWarrant() {
		
	    
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    String vin=null;
	    String dateIssued=null;
	    String reason=null;
	    String outstanding = null;
	   

	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfVin) {
	                        vin = textField.getText();
	                    } else if (textField == tfDate) {
	                        dateIssued = textField.getText();
	                    } else if (textField == tfReason) {
	                        reason = textField.getText();	                    
	                    } 
	                } else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbOutstanding) {
	                    	outstanding = comboBox.getValue();	                        
	                    } 
	                }
	            }
	            // Call the method to insert the vehicle
	            vehicleWarrant.insertVehicleWarrant(vin, dateIssued, reason, outstanding);
	            clearFields(currentPane);
	            showSuccessMessage();
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
//Function on the submit button to enter new traffic school 
	public void submitEnterTrafficSchool() {
		   
	    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

	    int citationID=0;
	    String session1Date=null;
	    String session2Date=null;
	    String session3Date=null;
	    String session4Date=null;
	    String session1Attendance=null;
	    String session2Attendance=null;
	    String session3Attendance=null;
	    String session4Attendance=null;


	    if (dataValidator.emptyFieldsTest(currentPane)) {
	        if (dataValidator.fieldFormatTest(currentPane)) {
	            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfEnterCitID) {
	                        String citationIDString = textField.getText();
	                        citationID = Integer.parseInt(citationIDString);
	                    } else if (textField == tfSess1) {
	                        session1Date = textField.getText();
	                    } else if (textField == tfSess2) {
	                    	session2Date = textField.getText();
	                    } else if (textField == tfSess3) {
	                    	session3Date = textField.getText();
	                    } else if (textField == tfSess4) {
	                    	session4Date = textField.getText();	                        
	                    } 
	                }  else if (node instanceof ComboBox) {
	                    ComboBox<String> comboBox = (ComboBox<String>) node;
	                    if (comboBox == cbSessAtt1) {
	                        session1Attendance = comboBox.getValue();
	                    } else if (comboBox == cbSessAtt2) {
	                        session2Attendance = comboBox.getValue();
	                    }else if (comboBox == cbSessAtt3) {
	                        session3Attendance = comboBox.getValue();
	                    }else if (comboBox == cbSessAtt4) {
	                        session4Attendance = comboBox.getValue();
	                    }
	                }
	            }
	            // Call the method to insert the vehicle
	            trafficSchool.insertEnrollment (citationID, session1Date, session2Date, session3Date, session4Date, 
	            session1Attendance, session2Attendance, session3Attendance, session4Attendance);
	            clearFields(currentPane);
	            showSuccessMessage();
	            
	        } else {
	            System.out.println("Field format test failed");
	            showWrongFormatMessage();
	        }
	    } else {
	        System.out.println("Empty fields test failed");
	        showEmptyFieldsMessage();
	    }
	}
	

//***Searching records to make reports

	public void searchLicenseReport() {
		
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
		String license = null;

		if (dataValidator.emptyFieldsTest(currentPane)) {
		    if (dataValidator.fieldFormatTest(currentPane)) {
		        
		    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
		        for (Node node : centerContentVBox.getChildren()) {
		            if (node instanceof TextField) {
		                TextField textField = (TextField) node;
		                if (textField == tfLic) {
		                    license = textField.getText();
		                }
		            }
		        }
		        
		        if (recordValidator.checkVehicleRecordExistence(license)) {
		        	createDataScene("View/Edit","Vehicle Info");
		        	driver = driver.findDriver(license);
		        	
		        	//handle
		        } else {
		        	System.out.println("No record found");
		        	showNoRecordMessage();
		        }
		        
		    } else {
		        System.out.println("Field format test failed");
		        showWrongFormatMessage();
		    }
		    
		} else {
		    System.out.println("Empty fields test failed");
		    showEmptyFieldsMessage();
		}
	}
 

   public void searchVinReport() {
	   
   }
    	         

    public void searchDrivingRecordReport() {
    	
    }
   
	//***Searching records in the database for Viewing/Editing***	

public void searchLicenseDriverEdit() {
		
		BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
		String license = null;

		if (dataValidator.emptyFieldsTest(currentPane)) {
		    if (dataValidator.fieldFormatTest(currentPane)) {
		        
		    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
		        for (Node node : centerContentVBox.getChildren()) {
		            if (node instanceof TextField) {
		                TextField textField = (TextField) node;
		                if (textField == tfLic) {
		                    license = textField.getText();
		                }
		            }
		        }
		        
		        if (recordValidator.checkDriverRecordExistence(license)) {
		        	createDataScene("View/Edit","Driver Info");
		        	driver = driver.findDriver(license);
		        	autoFillDriver(driver);
		        } else {
		        	System.out.println("No record found");
		        	showNoRecordMessage();
		        }
		        
		    } else {
		        System.out.println("Field format test failed");
		        showWrongFormatMessage();
		    }
		    
		} else {
		    System.out.println("Empty fields test failed");
		    showEmptyFieldsMessage();
		}
	}
public void searchVinVehicleEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String vin = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfVin) {
	                    vin = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkVehicleRecordExistence(vin)) {
	        	createDataScene("View/Edit","Vehicle Info");
	        	vehicle = vehicle.findVehicle(vin);
	        	autoFillVehicle(vehicle);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchBadgeOfficerEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String badge = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfBadge) {
	                    badge = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkOfficerRecordExistence(badge)) {
	        	createDataScene("View/Edit","Officer Info");
	        	officer = officer.findOfficer(badge);
	        	autoFillVehicle(vehicle);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchAccountIDAccountEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String accountID = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterAcc) {	                    
	                    accountID = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkAccountRecordExistence(accountID)) {
	        	createDataScene("View/Edit","Account Info");
	        	account = account.findAccount(accountID);
	        	autoFillAccount(account);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchCitIDDriverCitationEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String citationID = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterCitID) {	                    
	                    citationID = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkDriCitRecordExistence(citationID)){
	        	createDataScene("View/Edit","Driver Citation Info");
	        	driverCitation = driverCitation.findCitation(citationID);
	        	autoFillDriverCitation(driverCitation);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchCitIDVehicleCitationEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String citationID = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterCitID) {	                    
	                    citationID = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkVehCitRecordExistence(citationID)){
	        	createDataScene("View/Edit","Vehicle Citation Info");
	        	vehicleCitation = vehicleCitation.findCitation(citationID);
	        	autoFillVehicleCitation(vehicleCitation);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchWarIDVehicleWarrantEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String warrantID = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterWarID) {	                    
	                    warrantID = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkDriWarrRecordExistence(warrantID)){
	        	createDataScene("View/Edit","Vehicle Warrant Info");
	        	vehicleWarrant = vehicleWarrant.findVehicleWarrant(warrantID);
	        	autoFillVehicleWarrant(vehicleWarrant);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchWarIDDriverWarrantEdit() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String warrantID = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterWarID) {	                    
	                    warrantID = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkVehWarrRecordExistence(warrantID)){
	        	createDataScene("View/Edit","Driver Warrant Info");
	        	driverWarrant = driverWarrant.findDriverWarrant(warrantID);
	        	autoFillDriverWarrant(driverWarrant);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
	
}
public void searchCitIDTrafficSchoolEdit() {

	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	int citationID = 0;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterCitID) {	                    
	                   citationID = Integer.parseInt(textField.getText());
	                }
	            }
	        }
	        
	        if (recordValidator.checkTrafficSchoolRecordExistence(Integer.toString(citationID))){
	        	createDataScene("View/Edit","Traffic School Info");
	        	trafficSchool = trafficSchool.findEnrollment(citationID);
	        	autoFillTrafficSchool(trafficSchool);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
	
	
}


//***Searching record in the database for Deleting***
public void searchLicenseDriverDelete() {
	
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	String license = null;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfLic) {
	                    license = textField.getText();
	                }
	            }
	        }
	        
	        if (recordValidator.checkDriverRecordExistence(license)) {
	        	createDataScene("Delete","Driver Info");
	        	driver = driver.findDriver(license);
	        	autoFillDriver(driver);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
}
public void searchVinVehicleDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String vin = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfVin) {
                    vin = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkVehicleRecordExistence(vin)) {
        	createDataScene("Delete","Vehicle Info");
        	vehicle = vehicle.findVehicle(vin);
        	autoFillVehicle(vehicle);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}

public void searchBadgeOfficerDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String badge = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfBadge) {
                    badge = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkOfficerRecordExistence(badge)) {
        	createDataScene("Delete","Officer Info");
        	officer = officer.findOfficer(badge);
        	autoFillVehicle(vehicle);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}
public void searchAccountIDAccountDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String accountID = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfEnterAcc) {	                    
                    accountID = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkAccountRecordExistence(accountID)) {
        	createDataScene("Delete","Account Info");
        	account = account.findAccount(accountID);
        	autoFillAccount(account);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}
public void searchCitIDDriverCitationDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String citationID = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfEnterCitID) {	                    
                    citationID = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkDriCitRecordExistence(citationID)){
        	createDataScene("Delete","Driver Citation Info");
        	driverCitation = driverCitation.findCitation(citationID);
        	autoFillDriverCitation(driverCitation);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}
public void searchCitIDVehicleCitationDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String citationID = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfEnterCitID) {	                    
                    citationID = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkVehCitRecordExistence(citationID)){
        	createDataScene("Delete","Vehicle Citation Info");
        	vehicleCitation = vehicleCitation.findCitation(citationID);
        	autoFillVehicleCitation(vehicleCitation);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}
public void searchWarIDVehicleWarrantDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String warrantID = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfEnterWarID) {	                    
                    warrantID = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkDriWarrRecordExistence(warrantID)){
        	createDataScene("Delete","Vehicle Warrant Info");
        	vehicleWarrant = vehicleWarrant.findVehicleWarrant(warrantID);
        	autoFillVehicleWarrant(vehicleWarrant);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}
}
public void searchWarIDDriverWarrantDelete() {
BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
String warrantID = null;

if (dataValidator.emptyFieldsTest(currentPane)) {
    if (dataValidator.fieldFormatTest(currentPane)) {
        
    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
        for (Node node : centerContentVBox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField == tfEnterWarID) {	                    
                    warrantID = textField.getText();
                }
            }
        }
        
        if (recordValidator.checkVehWarrRecordExistence(warrantID)){
        	createDataScene("Delete","Driver Warrant Info");
        	driverWarrant = driverWarrant.findDriverWarrant(warrantID);
        	autoFillDriverWarrant(driverWarrant);
        } else {
        	System.out.println("No record found");
        	showNoRecordMessage();
        }
        
    } else {
        System.out.println("Field format test failed");
        showWrongFormatMessage();
    }
    
} else {
    System.out.println("Empty fields test failed");
    showEmptyFieldsMessage();
}

}
public void searchCitIDTrafficSchoolDelete() {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
	int citationID = 0;

	if (dataValidator.emptyFieldsTest(currentPane)) {
	    if (dataValidator.fieldFormatTest(currentPane)) {
	        
	    	VBox centerContentVBox = (VBox) currentPane.getCenter();		        
	        for (Node node : centerContentVBox.getChildren()) {
	            if (node instanceof TextField) {
	                TextField textField = (TextField) node;
	                if (textField == tfEnterCitID) {	                    
	                    citationID = Integer.parseInt(textField.getText());
	                }
	            }
	        }
	        
	        if (recordValidator.checkTrafficSchoolRecordExistence(Integer.toString(citationID))){
	        	createDataScene("Delete","Traffic School Info");
	        	trafficSchool = trafficSchool.findEnrollment(citationID);
	        	autoFillTrafficSchool(trafficSchool);
	        } else {
	        	System.out.println("No record found");
	        	showNoRecordMessage();
	        }
	        
	    } else {
	        System.out.println("Field format test failed");
	        showWrongFormatMessage();
	    }
	    
	} else {
	    System.out.println("Empty fields test failed");
	    showEmptyFieldsMessage();
	}
	
}
//***AutoFilling data fields for viewing/editing/deleting

public void autoFillDriver(Driver driver) {
    BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
    HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
    VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

    for (Node node : FieldsVBox.getChildren()) {
        if (node instanceof TextField) {
            TextField textField = (TextField) node;
            if (textField == tfLic) {
                textField.setText(driver.getLicenseNumber());
            } else if (textField == tfLast) {
                textField.setText(driver.getLastName());
            } else if (textField == tfFirst) {
                textField.setText(driver.getFirstName());
            } else if (textField == tfPlate) {
                textField.setText(driver.getLicensePlate());
            } else if (textField == tfPoints) {
                textField.setText(driver.getDemeritPoints());
            }
        }

        if (node instanceof ComboBox) {
            ComboBox<String> comboBox = (ComboBox<String>) node;
            if (comboBox == cbLicStat) {
                comboBox.setValue(driver.getLicenseStatus());
            }
        }
    }
} 
public void autoFillVehicle(Vehicle vehicle) {
    
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
    HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
    VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

    for (Node node : FieldsVBox.getChildren()) {
        if (node instanceof TextField) {
            TextField textField = (TextField) node;
            if (textField == tfVin) {
                textField.setText(vehicle.getVin());
            } else if (textField == tfPlate) {
            	textField.setText(vehicle.getLicensePlate());
            } else if (textField == tfMake) {
            	textField.setText(vehicle.getMake());
            } else if (textField == tfModel) {
            	 textField.setText(vehicle.getModel());
            } else if (textField == tfYear) {
            	 textField.setText(vehicle.getYear());           	
            }
        } else if (node instanceof ComboBox) {
            ComboBox<String> comboBox = (ComboBox<String>) node;
            if (comboBox == cbRegStat) {
                comboBox.setValue(vehicle.getRegisteredStatus());
            }
        }
    }
} 
public void autoFillOfficer(Officer officer) {
	 
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();   
	HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
	VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

	            for (Node node : FieldsVBox.getChildren()) {
	                if (node instanceof TextField) {
	                    TextField textField = (TextField) node;
	                    if (textField == tfBadge) {
	                       textField.setText(officer.getBadgeNumber());	                      
	                    } else if (textField == tfFirst) {
	                        textField.setText(officer.getFirstName());
	                    } else if (textField == tfLast) {
	                        textField.setText(officer.getLastName());
	                    } 
	                }
	      
	      
	            }        
}
public void autoFillAccount(Account account) {
	
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfUsername) {
                        textField.setText(account.getUsername());
                    } else if (textField == tfPassword) {
                    	 textField.setText(account.getPassword());
                    } else if (textField == tfFirst) {
                    	 textField.setText(account.getFirstName());
                    } else if (textField == tfLast) {
                    	 textField.setText(account.getLastName());
                    } else if (textField == tfAccID) {
                   	 textField.setText(account.getAccountID());
                   } 
                } else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbAgency) {
                        comboBox.setValue(account.getAgency());
                    }
                }
         
        }
}
public void autoFillVehicleCitation(VehicleCitation vehicleCitation) {
	
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfVin) {
                       	textField.setText(vehicleCitation.getVin());
                    } else if (textField == tfBadge) {
                    	textField.setText(vehicleCitation.getIssuingOfficerBadgeNumber());                  
                    } else if (textField == tfDate) {
                    	textField.setText(vehicleCitation.getdateIssued());
                    } else if (textField == tfFine) {
                    	textField.setText(vehicleCitation.getFineAmount());     
                    } else if (textField == tfCitID) {
                    	textField.setText(vehicleCitation.getcitationId());     
                    } 
                } else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbReasonVeh) {
                        comboBox.setValue(vehicleCitation.getReason());
                    } else if (comboBox == cbPaid) {
                    	comboBox.setValue(vehicleCitation.getPaid());
                    }
                }
            }
      
}
public void autoFillDriverCitation(DriverCitation DriverCitation) {
	
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
    HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
    VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfLic) {
                       	textField.setText(driverCitation.getLicense());
                    } else if (textField == tfBadge) {
                    	textField.setText(driverCitation.getIssuingOfficerBadgeNumber());                  
                    } else if (textField == tfDate) {
                    	textField.setText(driverCitation.getdateIssued());
                    } else if (textField == tfFine) {
                    	textField.setText(driverCitation.getFineAmount());     
                    } else if (textField == tfCitID) {
                    	textField.setText(driverCitation.getcitationId());     
                    } 
                } else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbReasonDriv) {
                        comboBox.setValue(driverCitation.getReason());
                    } else if (comboBox == cbPaid) {
                    	comboBox.setValue(driverCitation.getPaid());
                    }
                }
            }
      
}
public void autoFillVehicleWarrant(VehicleWarrant vehicleWarrant) {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

            HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
            VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfVin) {
                        textField.setText(vehicleWarrant.getVin());
                    } else if (textField == tfDate) {
                    	textField.setText(vehicleWarrant.getdateIssued());
                    } else if (textField == tfReason) {
                    	textField.setText(vehicleWarrant.getReason());                    
                    } else if (textField == tfWarID) {
                    	textField.setText(Integer.toString(vehicleWarrant.getWarrantID()));                   
                    } 
                } else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbOutstanding) {
                        comboBox.setValue(vehicleWarrant.getOutstanding());
                        
                    } 
                }
            }
     
}
public void autoFillDriverWarrant(DriverWarrants driverWarrant) {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();
    HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
    VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

    for (Node node : FieldsVBox.getChildren()) {
        if (node instanceof TextField) {
            TextField textField = (TextField) node;
            if (textField == tfLic) {
                textField.setText(driverWarrant.getLicenseNumber());
            } else if (textField == tfDate) {
            	textField.setText(driverWarrant.getdateIssued());
            } else if (textField == tfReason) {
            	textField.setText(driverWarrant.getReason());                    
            } else if (textField == tfWarID) {
            	textField.setText(driverWarrant.getWarrantId());                    
            } 
        } else if (node instanceof ComboBox) {
            ComboBox<String> comboBox = (ComboBox<String>) node;
            if (comboBox == cbOutstanding) {
                comboBox.setValue(driverWarrant.getOutstanding());
                
            } 
        }
    }
}
public void autoFillTrafficSchool(TrafficSchool trafficSchool) {
	BorderPane currentPane = (BorderPane) primaryStage.getScene().getRoot();

        HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
        VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);

            for (Node node : FieldsVBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    if (textField == tfEnterCitID) {
                        textField.setText(Integer.toString(trafficSchool.getCitationID()));
                    } else if (textField == tfSess1) {
                    	textField.setText(trafficSchool.getSession1Date());
                    } else if (textField == tfSess2) {
                    	textField.setText(trafficSchool.getSession2Date());
                    } else if (textField == tfSess3) {
                    	textField.setText(trafficSchool.getSession3Date());
                    } else if (textField == tfSess4) {
                    	textField.setText(trafficSchool.getSession4Date());                        
                    }
                }  else if (node instanceof ComboBox) {
                    ComboBox<String> comboBox = (ComboBox<String>) node;
                    if (comboBox == cbSessAtt1) {
                         comboBox.setValue(trafficSchool.getSession1Attendance());
                    } else if (comboBox == cbSessAtt2) {
                    	comboBox.setValue(trafficSchool.getSession2Attendance());
                    }else if (comboBox == cbSessAtt3) {
                    	comboBox.setValue(trafficSchool.getSession3Attendance());
                    }else if (comboBox == cbSessAtt4) {
                    	comboBox.setValue(trafficSchool.getSession4Attendance());
                    }
                }    
}

}


//Method to navigate backwards through scenes 
public void navigateBack() {
		
		 if (!sceneStack.isEmpty()) {
	        sceneStack.pop();
	        if (!sceneStack.isEmpty()) {
	            CreateScene previousCreateScene = sceneStack.pop();
	            
	            switch (previousCreateScene.methodName) {
	            case "CreateLoginScene":
	           	 primaryStage.setScene(createLoginScene());
	           	 break;
	            case "CreateOptionScene":
	           	 primaryStage.setScene(createOptionScene(previousCreateScene.parameter1));
	           	 break;
	            case "CreateSearchScene":
	           	 primaryStage.setScene(createSearchScene(previousCreateScene.parameter1, previousCreateScene.parameter2));
	           	 break;
	            case "CreateDataScene":
	           	 primaryStage.setScene(createDataScene(previousCreateScene.parameter1, previousCreateScene.parameter2));
	           	 break;
	            case "CreateReportScene":
	           	 primaryStage.setScene(createReportScene());
	           	 break;
	            }
	        } else {
	           primaryStage.setScene(createLoginScene());
	        }
	    }
	}
public void clearFields (Node rootNode) {
		
		BorderPane currentPane = (BorderPane) rootNode;
		HBox LabelsAndFieldsHBox = (HBox) currentPane.getRight();
		VBox FieldsVBox = (VBox) LabelsAndFieldsHBox.getChildren().get(1);
		
	    for (Node node : FieldsVBox.getChildren()) {
	        if (node instanceof TextField ) {
	        	TextField textField = (TextField) node;
	        	 if (textField.isEditable()) {
	        		 textField.clear();	
	        	 }
	        }
	        
	        if (node instanceof ComboBox ) {
	        	ComboBox<?> comboBox = (ComboBox<?>) node;
	        	comboBox.setValue(null);
	        }
	    }    
	}      
	
           
            
    
    public void exit() {
    
    	Platform.exit();
    	databaseManager.disconnectFromDatabase();
    }
 
    public void login() {
        DatabaseManager manager = new DatabaseManager();
        
            
            RecordValidation validator = new RecordValidation(manager);

            boolean validLogin = validator.checkLoginInfo(tfUsername.getText(), pfPassword.getText(), cbAgencyLogin.getValue());

            if (validLogin && "Provincial".equals(cbAgencyLogin.getValue()))
                primaryStage.setScene(createOptionScene("Manage/Report Provincial"));
            else if (validLogin && "Municipal".equals(cbAgencyLogin.getValue()))
                primaryStage.setScene(createOptionScene("Manage/Report Municipal"));
            else if (validLogin && "Administration".equals(cbAgencyLogin.getValue()))
                primaryStage.setScene(createOptionScene("Enter/Edit/Delete Account"));
            else
                lbLoginError.setVisible(true);
       
    }
   
 //***Helper methods to show feedback messages***//

   public void showSuccessMessage() {
		lbSuccessText.setVisible(true);
	}
	public void showEmptyFieldsMessage() {
		lbEmptyFields.setVisible(true);
	}
	public void showWrongFormatMessage() {
		lbWrongFormat.setVisible(true);
	}
	public void showNoRecordMessage() {
		lbNoRecord.setVisible(true);
	}
	public void showLoginErrorMessage() {
		lbLoginError.setVisible(true);
	}
    
 
 }
