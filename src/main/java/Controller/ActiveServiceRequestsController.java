package Controller;

import Access.InternalTransportAccess;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import Object.ServiceRequestTable;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;


public class ActiveServiceRequestsController {

    private Stage thestage;
    private TreeItem<ServiceRequestTable> selectedRequest;

    TreeItem root = new TreeItem<>("rootxxx");

    @FXML
    private Button back;

    @FXML
    private ComboBox filter;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> dateRequested;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> timeRequested;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> type;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> name;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> assignedEmployee;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> comment;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field1;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field2;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field3;


    @FXML
    TreeTableColumn<ServiceRequestTable, String> hi;

    @FXML
    private TreeTableView<ServiceRequestTable> activeRequests;

    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        LoggedInHomeController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) activeRequests.getScene().getWindow();

        //Show scene 2 in new window
        thestage.setScene(scene);
    }
    @FXML
    public void initialize() {
        filter.getItems().addAll(
                "Religious", "Internal Transportation", "Audio/Visual",
                "External Transportation", "Florist Delivery", "IT",
                "Language Assistance", "Maintenance", "Prescriptions", "Sanitation", "Security");
        //filterTable();
        activeRequests.getColumns().clear();

    }
    public void filter(){

    }


    //Gabe - Populates table
    @FXML
    public void filterTable() {
        /**@author Gabe
         * Populates the table on the screen with any active service rquests in the database
         */
        activeRequests.setEditable(false);

        if (filter.getValue() == "Internal Transportation") {
            root.getChildren().clear();
            activeRequests.setRoot(null);
            InternalTransportAccess it = new InternalTransportAccess();

            int count;
            count = it.countRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> itt = it.getRequests(count);
                root.getChildren().add(itt);
                count--;
            }

            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);

            timeRequested.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getCreationTime());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });


            dateRequested.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getCreationDate());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });


            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getStartLocation());

                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getEndLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field3.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPhoneNumber());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            comment.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getComment());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            assignedEmployee.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getAssignedEmployee());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

        }
        if (filter.getValue() == "Religious") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            ReligiousRequestAccess rr = new ReligiousRequestAccess();

            int count;
            count = rr.countRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = rr.getReligiousRequests(count);
                root.getChildren().add(rrt);
                count--;
            }

            timeRequested.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getCreationTime());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });


            dateRequested.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getCreationDate());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });


            name.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getName());

                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDenomination());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            comment.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getComment());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            assignedEmployee.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getAssignedEmployee());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setText("Denomination");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(name);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setTreeColumn(timeRequested);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);

        }

        if (filter.getValue() == "Audio/Visual") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "External Transportation") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Florist Delivery") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Flower");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(name);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "IT") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Device");
            field2.setText("Problem");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Language Assistance") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Language");
            field2.setText("Their Proficiency");
            field3.setText("Interpreters Needed");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Maintenance") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Hazardous?");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Prescriptions") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Medicine");
            field2.setText("Amount");
            field3.setText("Delivery Time");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Sanitation") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Urgency");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }
        if (filter.getValue() == "Security") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            field1.setText("Identifier");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
            activeRequests.setRoot(root);
            activeRequests.setShowRoot(false);
        }

        }



   public void setNext(TreeItem<ServiceRequestTable> request){
     this.selectedRequest = request;
    }

    //Gabe - Switches to fulfill screen when mouse double clicks a row in the table
    //TODO: Bring over request information so that fulfill page can update a request
    //@FXML
    /**@author Gabe, DJ
     * When double clicking on a row in the table, user is sent to the fulfill request screen
     * and all the information from that row is passed along so that a user can update it
     */

    @FXML
    private void SwitchToFulfillRequestScreen() throws IOException {
        activeRequests.setOnMouseClicked(event -> {
            setNext(activeRequests.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                yooo();
            }
        });
    }

    private void yooo(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FulFillRequest.fxml"));

            Parent sceneMain = loader.load();

            FulfillRequestController controller = loader.<FulfillRequestController>getController();
            controller.setRid(Integer.parseInt(selectedRequest.getValue().getRequestID()), filter.getValue().toString());

            Stage theStage = (Stage) back.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }
}


