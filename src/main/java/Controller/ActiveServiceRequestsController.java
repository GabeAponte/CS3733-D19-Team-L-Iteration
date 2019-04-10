package Controller;

import Access.EmployeeAccess;
import Access.InternalTransportAccess;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import Object.ServiceRequestTable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import Object.*;


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

    Timeline timeout;

    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        timeout.stop();
        if(single.isIsAdmin()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
            Parent roots = loader.load();

            Scene scene = new Scene(roots);
            Stage thestage = (Stage) activeRequests.getScene().getWindow();

            //Show scene 2 in new window
            thestage.setScene(scene);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
            Parent roots = loader.load();

            Scene scene = new Scene(roots);
            Stage thestage = (Stage) activeRequests.getScene().getWindow();

            //Show scene 2 in new window
            thestage.setScene(scene);
        }
    }

    //Gabe - Populates table
    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        single.setLastTime();
                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) activeRequests.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();

        filter.getItems().addAll(
                "Religious", "Internal Transportation", "Audio/Visual",
                "External Transportation", "Florist Delivery", "IT",
                "Language Assistance", "Maintenance", "Prescriptions", "Sanitation", "Security");
        activeRequests.getColumns().clear();
    }

    /**@author Gabe
     * Populates the table on the screen with any active service rquests in the database
     */
    @FXML
    private void filterTable(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();

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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countAudioRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getAudioVisualRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDestination());
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

            field1.setText("Name");
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
        if (filter.getValue() == "External Transportation") {
            root.getChildren().clear();
            activeRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countExternalRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getExternalRequests(count);
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


            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDestination());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countFloristRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getFloristRequests(count);
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
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getReceiverName());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getFlowerName());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countITRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getITRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDevice());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getProblem());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countLanguageRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getLanguageRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLanguage());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLevel());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field3.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getInterpreters());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countMaintenanceRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getMaintenanceRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getIsHazard());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countPrescriptionRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getPrescriptionRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getMedicineType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getAmmount());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field3.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDeliveryTime());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countSanitationRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getSanitationRequests(count);
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


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getUrgenecyLevel());
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

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countSecurityRecords()-1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> rrt = sra.getSecurityRequests(count);
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
                    System.out.println("Setcell factory date set");
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getCreationDate());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });


            hi.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLocation());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            type.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field1.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getName());
                }
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
            });

            field2.setCellValueFactory(cellData -> {
                if (cellData.getValue().getValue() instanceof ServiceRequestTable) {
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getThreatLevel());
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

            field1.setText("Identifier");
            field2.setText("ThreatLevel");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
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
        timeout.stop();
        activeRequests.setOnMouseClicked(event -> {
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            setNext(activeRequests.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                EmployeeAccess ea = new EmployeeAccess();
                if(single.isIsAdmin()) {
                    yooo();
                }else if(single.getUsername().equals(ea.getEmployeeUsername(activeRequests.getSelectionModel().getSelectedItem().getValue().getAssignedEmployee()))){
                    yooo();
                }
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


