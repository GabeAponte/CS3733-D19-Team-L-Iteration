package edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest;

import com.jfoenix.controls.JFXTabPane;
import edu.wpi.cs3733.d19.teamL.Account.CreateEditAccountController;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Account.employeeID;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.InternalTransportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.LanguageAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ReligiousRequestAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;

import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Locale;


public class ActiveServiceRequestsController {

    private Stage thestage;
    private TreeItem<ServiceRequestTable> selectedRequest;

    TreeItem root = new TreeItem<>("rootxxx");

    @FXML
    private Button back;

    @FXML
    private Tab tab;

    @FXML
    private String filter;

    @FXML
    JFXTabPane requestType;

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
    private TreeTableView<ServiceRequestTable> ReligiousRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> AVRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> ExternalRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> FloristRequest;

    @FXML
    private TreeTableView<ServiceRequestTable> InternalRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> ITRequest;

    @FXML
    private TreeTableView<ServiceRequestTable> PrescriptionsRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> SecurityRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> SanitationRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> LanguageRequests;

    @FXML
    private TreeTableView<ServiceRequestTable> MaintenanceRequests;

    Timeline timeout;

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
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

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
    }

    /**
     * @author Gabe
     * Populates the table on the screen with any active service rquests in the database
     */
    @FXML
    private void filterTable() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        if (requestType.getSelectionModel().getSelectedItem().getText().equals("Internal Transportation")) {
            InternalRequests.setEditable(false);
            System.out.println(requestType.getSelectionModel().getSelectedItem().getText());
            root.getChildren().clear();
            InternalRequests.setRoot(null);
            InternalTransportAccess it = new InternalTransportAccess();

            int count;
            count = it.countRecords() - 1;
            while (count >= 0) {
                TreeItem<ServiceRequestTable> itt = it.getRequests(count);
                root.getChildren().add(itt);
                count--;
            }

            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            InternalRequests.getColumns().clear();
            InternalRequests.getColumns().add(timeRequested);
            InternalRequests.getColumns().add(dateRequested);
            InternalRequests.getColumns().add(field1);
            InternalRequests.getColumns().add(field2);
            InternalRequests.getColumns().add(type);
            InternalRequests.getColumns().add(field3);
            InternalRequests.getColumns().add(comment);
            InternalRequests.getColumns().add(assignedEmployee);
            InternalRequests.setRoot(root);
            InternalRequests.setShowRoot(false);

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

        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Religious")) {
            root.getChildren().clear();
            ReligiousRequests.setRoot(null);

            ReligiousRequestAccess rr = new ReligiousRequestAccess();

            int count;
            count = rr.countRecords() - 1;
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
            ReligiousRequests.getColumns().clear();
            ReligiousRequests.getColumns().add(timeRequested);
            ReligiousRequests.getColumns().add(dateRequested);
            ReligiousRequests.getColumns().add(name);
            ReligiousRequests.getColumns().add(field1);
            ReligiousRequests.getColumns().add(type);
            ReligiousRequests.getColumns().add(hi);
            ReligiousRequests.getColumns().add(comment);
            ReligiousRequests.getColumns().add(assignedEmployee);
            ReligiousRequests.setRoot(root);
            ReligiousRequests.setShowRoot(false);

        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Audio/Visual")) {
            root.getChildren().clear();
            AVRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countAudioRecords() - 1;
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
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getName());
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
            AVRequests.getColumns().clear();
            AVRequests.getColumns().add(timeRequested);
            AVRequests.getColumns().add(dateRequested);
            AVRequests.getColumns().add(field1);
            AVRequests.getColumns().add(hi);
            AVRequests.getColumns().add(type);
            AVRequests.getColumns().add(comment);
            AVRequests.getColumns().add(assignedEmployee);
            AVRequests.setRoot(root);
            AVRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("External Transportation")) {
            root.getChildren().clear();
            ExternalRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countExternalRecords() - 1;
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

            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            ExternalRequests.getColumns().clear();
            ExternalRequests.getColumns().add(timeRequested);
            ExternalRequests.getColumns().add(dateRequested);
            ExternalRequests.getColumns().add(field1);
            ExternalRequests.getColumns().add(field2);
            ExternalRequests.getColumns().add(type);
            ExternalRequests.getColumns().add(field3);
            ExternalRequests.getColumns().add(comment);
            ExternalRequests.getColumns().add(assignedEmployee);
            ExternalRequests.setRoot(root);
            ExternalRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Florist")) {
            root.getChildren().clear();
            FloristRequest.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countFloristRecords() - 1;
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
            FloristRequest.getColumns().clear();
            FloristRequest.getColumns().add(timeRequested);
            FloristRequest.getColumns().add(dateRequested);
            FloristRequest.getColumns().add(name);
            FloristRequest.getColumns().add(field1);
            FloristRequest.getColumns().add(hi);
            FloristRequest.getColumns().add(comment);
            FloristRequest.getColumns().add(assignedEmployee);
            FloristRequest.setRoot(root);
            FloristRequest.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("IT")) {
            root.getChildren().clear();
            ITRequest.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countITRecords() - 1;
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
            ITRequest.getColumns().clear();
            ITRequest.getColumns().add(timeRequested);
            ITRequest.getColumns().add(dateRequested);
            ITRequest.getColumns().add(hi);
            ITRequest.getColumns().add(field1);
            ITRequest.getColumns().add(field2);
            ITRequest.getColumns().add(comment);
            ITRequest.getColumns().add(assignedEmployee);
            ITRequest.setRoot(root);
            ITRequest.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Language Assistance")) {
            root.getChildren().clear();
            LanguageRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countLanguageRecords() - 1;
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
            LanguageRequests.getColumns().clear();
            LanguageRequests.getColumns().add(timeRequested);
            LanguageRequests.getColumns().add(dateRequested);
            LanguageRequests.getColumns().add(hi);
            LanguageRequests.getColumns().add(field1);
            LanguageRequests.getColumns().add(field2);
            LanguageRequests.getColumns().add(field3);
            LanguageRequests.getColumns().add(comment);
            LanguageRequests.getColumns().add(assignedEmployee);
            LanguageRequests.setRoot(root);
            LanguageRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Maintenance")) {
            root.getChildren().clear();
            MaintenanceRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countMaintenanceRecords() - 1;
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
            MaintenanceRequests.getColumns().clear();
            MaintenanceRequests.getColumns().add(timeRequested);
            MaintenanceRequests.getColumns().add(dateRequested);
            MaintenanceRequests.getColumns().add(hi);
            MaintenanceRequests.getColumns().add(type);
            MaintenanceRequests.getColumns().add(field1);
            MaintenanceRequests.getColumns().add(comment);
            MaintenanceRequests.getColumns().add(assignedEmployee);
            MaintenanceRequests.setRoot(root);
            MaintenanceRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Prescriptions")) {
            root.getChildren().clear();
            PrescriptionsRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countPrescriptionRecords() - 1;
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
                    return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDestination());
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
            PrescriptionsRequests.getColumns().clear();
            PrescriptionsRequests.getColumns().add(timeRequested);
            PrescriptionsRequests.getColumns().add(dateRequested);
            PrescriptionsRequests.getColumns().add(hi);
            PrescriptionsRequests.getColumns().add(field1);
            PrescriptionsRequests.getColumns().add(field2);
            PrescriptionsRequests.getColumns().add(field3);
            PrescriptionsRequests.getColumns().add(comment);
            PrescriptionsRequests.getColumns().add(assignedEmployee);
            PrescriptionsRequests.setRoot(root);
            PrescriptionsRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Sanitation")) {
            root.getChildren().clear();
            SanitationRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countSanitationRecords() - 1;
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
            SanitationRequests.getColumns().clear();
            SanitationRequests.getColumns().add(timeRequested);
            SanitationRequests.getColumns().add(dateRequested);
            SanitationRequests.getColumns().add(hi);
            SanitationRequests.getColumns().add(type);
            SanitationRequests.getColumns().add(field1);
            SanitationRequests.getColumns().add(comment);
            SanitationRequests.getColumns().add(assignedEmployee);
            SanitationRequests.setRoot(root);
            SanitationRequests.setShowRoot(false);
        } else if (requestType.getSelectionModel().getSelectedItem().getText().equals("Security")) {
            root.getChildren().clear();
            SecurityRequests.setRoot(null);

            ServiceRequestAccess sra = new ServiceRequestAccess();

            int count;
            count = sra.countSecurityRecords() - 1;
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
            SecurityRequests.getColumns().clear();
            SecurityRequests.getColumns().add(timeRequested);
            SecurityRequests.getColumns().add(dateRequested);
            SecurityRequests.getColumns().add(hi);
            SecurityRequests.getColumns().add(type);
            SecurityRequests.getColumns().add(field1);
            SecurityRequests.getColumns().add(field2);
            SecurityRequests.getColumns().add(comment);
            SecurityRequests.getColumns().add(assignedEmployee);
            SecurityRequests.setRoot(root);
            SecurityRequests.setShowRoot(false);
        }

    }


    public void setNext(TreeItem<ServiceRequestTable> request) {
        this.selectedRequest = request;
    }

    //Gabe - Switches to fulfill screen when mouse double clicks a row in the table
    //TODO: Bring over request information so that fulfill page can update a request
    //@FXML

    /**
     * @author Gabe, DJ
     * When double clicking on a row in the table, user is sent to the fulfill request screen
     * and all the information from that row is passed along so that a user can update it
     */

    @FXML
    private void SwitchToFulfillRequestScreen() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();

        AVRequests.setOnMouseClicked(event -> {
            filter = "Audio/Visual";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(AVRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        ExternalRequests.setOnMouseClicked(event -> {
            filter = "External Transportation";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(ExternalRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        InternalRequests.setOnMouseClicked(event -> {
            filter = "Internal Transportation";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(InternalRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        ITRequest.setOnMouseClicked(event -> {
            filter = "IT";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(ITRequest.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        MaintenanceRequests.setOnMouseClicked(event -> {
            filter = "Maintenance";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(MaintenanceRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        PrescriptionsRequests.setOnMouseClicked(event -> {
            filter = "Prescriptions";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(PrescriptionsRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        ReligiousRequests.setOnMouseClicked(event -> {
            filter = "Religious";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(ReligiousRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        SanitationRequests.setOnMouseClicked(event -> {
            filter = "Sanitations";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(SanitationRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        SecurityRequests.setOnMouseClicked(event -> {
            filter = "Security";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(SecurityRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

        LanguageRequests.setOnMouseClicked(event -> {
            filter = "Language Assistance";
            single.setLastTime();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                setNext(LanguageRequests.getSelectionModel().getSelectedItem());
                switchScreen();
            }
        });

    }

    private void switchScreen() {
        Singleton single = Singleton.getInstance();
        if (single.isIsAdmin()) {
            yooo();
        } else if (selectedRequest.getValue().getAssignedEmployee() != null && selectedRequest.getValue().getAssignedEmployee().contains(single.getUsername())) {
            yooo();
        }
    }

    private void yooo() {
        try {
            timeout.pause();
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            Stage stage;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FulfillRequest.fxml"));

            Parent sceneMain = loader.load();

            FulfillRequestController controller = loader.<FulfillRequestController>getController();
            controller.setRid(selectedRequest.getValue(), filter);
            stage = new Stage();
            stage.setScene(new Scene(sceneMain));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(requestType.getScene().getWindow());
            stage.showAndWait();
            filterTable();

        } catch (IOException ex) {

        }
    }

    /**@author Nathan
     * Restores previous screen
     * @throws IOException
     */
    @FXML
    private void backPressed() throws IOException{
        Singleton single = Singleton.getInstance();
        timeout.stop();
        single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);

        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
        Parent sceneMain = loader.load();
        if(m.getFxml().contains("HospitalPathFinding")){
            PathFindingController pfc = loader.getController();
            pfc.initWithMeme(m.getPathPref(), m.getTypeFilter(), m.getFloorFilter(), m.getStart(), m.getEnd());
        }

        Stage theStage = (Stage) requestType.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("ActiveServiceRequests.fxml");
    }

    @FXML
    private void logOut() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void goHome() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }
}


