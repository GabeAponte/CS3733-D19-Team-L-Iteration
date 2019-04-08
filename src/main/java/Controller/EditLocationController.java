package Controller;

import Access.EdgesAccess;
import Access.NodesAccess;
import Object.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class EditLocationController {

    @FXML
    Button backButton;

    @FXML
    Button downloadNode;

    @FXML
    Button downloadEdge;

    @FXML
    Button makeEditableNode;

    @FXML
    Button makeEditableEdge;

    @FXML
    Button addNode;

    @FXML
    Button addEdge;

    @FXML
    Button deleteNode;

    @FXML
    Button deleteEdge;

    @FXML
    private Button G;

    @FXML
    private Button Up;

    @FXML
    private Button Down;

    @FXML
    private Button L1;

    @FXML
    private Button L2;

    @FXML
    private Button F1;

    @FXML
    private Button F2;

    @FXML
    private Button F3;

    @FXML
    private JFXButton nodeDisplay; //nodeDisplayPress

    @FXML
    private JFXTextField nodeInfoID;
    @FXML
    private JFXTextField nodeInfoX;
    @FXML
    private JFXTextField nodeInfoY;
    @FXML
    private JFXTextField nodeInfoType;
    @FXML
    private JFXTextField nodeInfoBuilding;
    @FXML
    private JFXTextField nodeInfoFloor;
    @FXML
    private JFXTextField nodeInfoLong;
    @FXML
    private JFXTextField nodeInfoShort;



    @FXML
    private ImageView Map;


    @SuppressWarnings("unchecked")
    private ArrayList<String> mapURLs = new ArrayList<String>();

    private HashMap<String, Location> lookup = new HashMap<String, Location>();
    private final ObservableList<Location> nodeData = FXCollections.observableArrayList();
    private final ObservableList<Edge> edgeData = FXCollections.observableArrayList();

    private Location focusNode;
    private Edge focusEdge;

    @FXML
    private void clickedG(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
    }
    @FXML
    private void clickedL1(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
    }

    @FXML public void clickedL2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
    }
    @FXML
    private void clicked1(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
    }
    @FXML
    private void clicked2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
    }
    @FXML
    private void clicked3(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
    }

    @FXML
    /**
     * Grace made this, but its from gabe's code
     * adds all the URLs to the list, starts with 3rd floor since that is the next floor to come
     */
    public void map(){
        mapURLs.add("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png");
    }

    ListIterator<String> listIterator = null;
    boolean upclickedLast = false;
    boolean downclickedLast = false;

    @FXML
    /**
     * Grace made this, but its from gabe's code
     * Replaces image URL with the next floor up when the UP button is pressed
     */
    private void upClicked() {
        if (mapURLs.isEmpty()) {
            map();
            listIterator = mapURLs.listIterator();
        }
        if (listIterator.hasNext() == false && downclickedLast == true) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
        else if (listIterator.hasNext() == false) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }

        else if (downclickedLast == true){
            String next = listIterator.next();
            Map.setImage(new Image(next));
            upAgain();
            // Map.setImage(new Image(next));
        }

        else if (downclickedLast == false){
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
        upclickedLast = true;
        downclickedLast = false;
    }
    @FXML
    /**
     * Grace made this, but its from gabe's code
     * allows the map to change when UP is pressed and the last button clicked was DOWN by calling next one more time.
     */
    private void upAgain() {
        if (listIterator.hasNext() == false) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        } else {
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
    }

    @FXML
    /**
     * Grace made this, but its from gabe's code
     * allows the map to change when down is pressed and the last button clicked was UP by calling previous one more time.
     */
    private void downAgain() {
        if (listIterator.hasPrevious() == false) {
            listIterator = mapURLs.listIterator(mapURLs.size() - 1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        } else {
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
    }

    @FXML
    /**
     * Grace made this, but its from gabe's code
     * Replaces image URL with the next floor down when the DOWN button is pressed
     */
    private void downClicked(){
        if (mapURLs.isEmpty()) {
            map();
            listIterator = mapURLs.listIterator();
        }
        if (listIterator.hasPrevious() == false && upclickedLast == true) {
            listIterator = mapURLs.listIterator(mapURLs.size()-1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        else if (listIterator.hasPrevious() == false) {
            listIterator = mapURLs.listIterator(mapURLs.size()-1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        else if (upclickedLast == true){
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
            downAgain();//Due to the nature of listIterator, previous needs to be called twice inorder for the image to switch
        }
        else if (upclickedLast == false){
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        upclickedLast = false;
        downclickedLast = true;
    }



    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){

    }

    public void setNextNode(Location proto) {

        this.focusNode = proto;
        this.deleteNode.setDisable(false);
    }

    public void setNextEdge(Edge proto) {

        this.focusEdge = proto;
        this.deleteEdge.setDisable(false);
    }

    @FXML
    private void deleteEdgePress() {
        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected edge?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            EdgesAccess ea = new EdgesAccess();

            deleteEdge.setDisable(true);
        }
        else if (alert.getResult() == ButtonType.NO) {
        }
        */
    }

    @FXML
    private void modifyEdgePress() {
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());

        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.setInitialValues(focusEdge.getStartID(), focusEdge.getEndID());
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
        */
    }

    @FXML
    private void addEdgePress() {
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
        */
    }

    @FXML
    private void deleteNodePress() {
        /*
        //this is the dialogue popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected node?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        Singleton single = Singleton.getInstance();
        if (alert.getResult() == ButtonType.YES) {
            single.lookup.get(focusNode.getLocID()).restitch();
            //delete the node here
            NodesAccess na = new NodesAccess();
            na.deleteNode(focusNode.getLocID());


            EdgesAccess ea = new EdgesAccess();
            ArrayList<Edge> edgeList = new ArrayList<Edge>();

            for (Edge e: edgeData) {
                if (!(ea.containsEdge(e.getEdgeID()))) {
                    edgeList.add(e);
                }
            }
        }
        else if (alert.getResult() == ButtonType.NO) {
            //do nothing
        }

*/
    }

    @FXML
    private void modifyNodePress() {
        /*
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.fillFields(this.focusNode);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
        */
    }

    @FXML
    private void addNodePress() {
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);
        } catch (Exception e){
        }
        */
    }

    @FXML
    private void downloadNodes() {
        NodesAccess na = new NodesAccess();
        na.writeTableIntoCSV("");
    }

    @FXML
    private void downloadEdges() {
        EdgesAccess ea = new EdgesAccess();
        ea.writeTableIntoCSV("");
    }

    private void initializeTable(NodesAccess na, EdgesAccess ea) {
        /*
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), (arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0)))) {
                lookup.put((arr.get(0)), testx);
                nodeData.add(testx);
            }
            count++;
        }
        count = 0;
        while (count < ea.countRecords()) {
            edgeList = ea.getEdges(count);
            if (!edgeList.get(0).equals("edgeID")) {
                Edge testy = new Edge(edgeList.get(0), lookup.get(edgeList.get(1)), lookup.get(edgeList.get(2)));
                edgeData.add(testy);
            }
            count++;
        }
        */
    }

    @FXML
    private void nodeDisplayPress(){
        //display all nodes on that floor!!!
    }

    @FXML
    private void nodeInfoIDPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoFloorPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoXPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoYPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoTypePress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoBuildingPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoLongPress(){
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoShortPress(){
        //be able to modify the selected nodeID
    }

    @FXML
    private void backPressed() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        Stage theStage = (Stage) addEdge.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }




}

