/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.*;
import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.data.TimeSlot.DayOfWeek;
import csg.transaction.AddOH_Transaction;
import csg.workspace.controller.OHController;
import csg.workspace.dialogs.OfficeHoursDialogs;
import csg.workspace.foolproof.OfficeHoursFoolproofDesign;
import static csg.workspace.style.Style.*;
import static djf.AppPropertyType.APP_CLIPBOARD_FOOLPROOF_SETTINGS;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import djf.ui.dialogs.AppDialogsFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class OfficeHours {
    private Tab OHTab; 
    CourseSiteGeneratorApp app;
    private ToggleGroup taTypes= new ToggleGroup();
    private ArrayList <TableColumn> tableColumns= new ArrayList<>();
    
    public OfficeHours(Tab tab, CourseSiteGeneratorApp app){
        OHTab= tab;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();    
        ScrollPane mainPane = csgBuilder.buildScrollPane(OH_SCROLLPANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        VBox mainVBox = csgBuilder.buildVBox("", null, CLASS_PANES_FOREGROUND, ENABLED);
        mainVBox.setPadding(new Insets(5,5,5,5));
        mainVBox.setSpacing(10);
        
        initTAs(mainVBox);
        initOHs(mainVBox);
        mainPane.setContent(mainVBox);
        OHTab.setContent(mainPane);
        mainPane.setFitToHeight(true);
        mainPane.setFitToWidth(true);
        
        
        // INIT THE EVENT HANDLERS
        initControllers();
        // SETUP FOOLPROOF DESIGN FOR THIS APP
        initFoolproofDesign();
    }
    
    public void initTAs(VBox parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();    
        HBox hbox1 = csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hbox1.setPadding(new Insets(5,5,0,5));
        hbox1.setSpacing(20);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        Button removeTAButton = csgBuilder.buildTextButton(OH_TA_REMOVE_BUTTON, hbox1, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        csgBuilder.buildLabel(OH_TAS_LABEL, hbox1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildRadioButton(OH_TA_RADIO_TYPE_ALL, hbox1, CLASS_INPUT_CONTROL, ENABLED, taTypes,true);
        csgBuilder.buildRadioButton(OH_TA_RADIO_TYPE_UNDERGRADUATE, hbox1, CLASS_INPUT_CONTROL, ENABLED, taTypes,false);
        csgBuilder.buildRadioButton(OH_TA_RADIO_TYPE_GRADUATE, hbox1, CLASS_INPUT_CONTROL, ENABLED, taTypes,false);
        
        // MAKE THE TABLE AND SETUP THE DATA MODEL
        TableView<TeachingAssistantPrototype> taTable = csgBuilder.buildTableView(OH_TAS_TABLE_VIEW, parentPane, CLASS_TABLEVIEW, ENABLED);
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        TableColumn nameColumn = csgBuilder.buildTableColumn(OH_TA_NAME_TABLE_COLUMN, taTable, CLASS_TABLE_COLUMNS);
        nameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));
        
        TableColumn emailColumn= csgBuilder.buildTableColumn(OH_TA_EMAIL_TABLE_COLUMN, taTable, CLASS_TABLE_COLUMNS);
        emailColumn.setCellValueFactory(new PropertyValueFactory<String, String>("email"));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/3.0));
        
        //COUNT THE TIME SLOT OF A TA
        TableColumn timeSlotColumn = csgBuilder.buildTableColumn(OH_TA_SLOTS_TABLE_COLUMN, taTable, CLASS_TABLE_COLUMNS);
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<String, Integer>("slots"));
        timeSlotColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/5.0));
        
        TableColumn typeColumn = csgBuilder.buildTableColumn(OH_TA_TYPE_TABLE_COLUMN, taTable, CLASS_TABLE_COLUMNS);
        typeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        typeColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));

        //INIT THE TEXTFIELDS AND BUTTON TO ADD A TA.
        HBox taBox = csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        taBox.setSpacing(10);
        csgBuilder.buildTextField(OH_TA_ADD_NAME_TEXT_FIELD, taBox, CLASS_INPUT_CONTROL, ENABLED);
        csgBuilder.buildTextField(OH_TA_ADD_EMAIL_TEXT_FIELD, taBox, CLASS_INPUT_CONTROL, ENABLED);
        csgBuilder.buildTextButton(OH_TA_ADD_BUTTON, taBox, CLASS_OH_BUTTONS, ENABLED);
        
        //Set the grow of the table to true. Set the sortable to false;
        VBox.setVgrow(taTable, Priority.ALWAYS);
        for (int i = 0; i < taTable.getColumns().size(); i++) {
             ((TableColumn)taTable.getColumns().get(i)).setSortable(false);
        }
        
        taTable.setOnMouseClicked(e ->{
            AppData data=(AppData)app.getDataComponent();
            if(data.isTASelected()){
                updateBgColorForCell();
            }
            
            if(e.getClickCount()==2){
                if(data.isTASelected()){
                    OfficeHoursDialogs.editTADialog(app.getGUIModule().getWindow(), OH_EDIT_TITLE, OH_EDIT_HEADER, taTable, this,app);
                }
            }
            app.getFoolproofModule().updateControls(APP_CLIPBOARD_FOOLPROOF_SETTINGS);
        });
        
    }
    
    public void initOHs(VBox parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();    
        HBox hbox1 = csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hbox1.setPadding(new Insets(5,5,5,5));
        hbox1.setSpacing(10);
        
        csgBuilder.buildLabel(OH_OFFICE_HOURS_LABEL, hbox1, CLASS_MINOR_LABELS, ENABLED);
        Region r = new Region();
        hbox1.getChildren().add(r);
        HBox.setHgrow(r, Priority.ALWAYS);
        csgBuilder.buildLabel(OH_OFFICE_HOURS_START_TIME_LABEL, hbox1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> startTimeList = new ArrayList<>();
        startTimeList.add("9:00am");
        ComboBox startTimeCombo= csgBuilder.buildComboBox(OH_OFFICE_HOURS_START_TIME_COMBO, startTimeList, "9:00am", hbox1, CLASS_INPUT_CONTROL, ENABLED);
        csgBuilder.buildLabel(OH_OFFICE_HOURS_END_TIME_LABEL, hbox1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> endTimeList = new ArrayList<>();
        startTimeList.add("10:00pm");
        ComboBox endTimeCombo= csgBuilder.buildComboBox(OH_OFFICE_HOURS_END_TIME_COMBO, endTimeList, "10:00pm", hbox1, CLASS_INPUT_CONTROL, ENABLED);
        
        // SETUP THE OFFICE HOURS TABLE
        TableView<TimeSlot> officeHoursTable = csgBuilder.buildTableView(OH_OFFICE_HOURS_TABLE_VIEW, parentPane, CLASS_TABLEVIEW, ENABLED);
        TableColumn startTimeColumn = csgBuilder.buildTableColumn(OH_START_TIME_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn endTimeColumn = csgBuilder.buildTableColumn(OH_END_TIME_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn mondayColumn = csgBuilder.buildTableColumn(OH_MONDAY_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn tuesdayColumn = csgBuilder.buildTableColumn(OH_TUESDAY_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn wednesdayColumn = csgBuilder.buildTableColumn(OH_WEDNESDAY_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn thursdayColumn = csgBuilder.buildTableColumn(OH_THURSDAY_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        TableColumn fridayColumn = csgBuilder.buildTableColumn(OH_FRIDAY_TABLE_COLUMN, officeHoursTable, CLASS_TABLE_COLUMNS);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("endTime"));
        mondayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("monday"));
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("tuesday"));
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("wednesday"));
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("thursday"));
        fridayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("friday"));
        for (int i = 0; i < officeHoursTable.getColumns().size(); i++) {
            ((TableColumn)officeHoursTable.getColumns().get(i)).prefWidthProperty().bind(officeHoursTable.widthProperty().multiply(1.0/7.0));
        }
        
        //Add monday through friday to tableColumn, so it can be formated later. 
        tableColumns.add(mondayColumn);
        tableColumns.add(tuesdayColumn);
        tableColumns.add(wednesdayColumn);
        tableColumns.add(thursdayColumn);
        tableColumns.add(fridayColumn);
        
        // MAKE SURE IT'S THE TABLE THAT ALWAYS GROWS IN THE LEFT PANE
        VBox.setVgrow(officeHoursTable, Priority.ALWAYS);
        
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////// GENERATES DATA FOR THE OH/////////////////////////////////////
        TableView TATableView = (TableView)app.getGUIModule().getGUINode(OH_TAS_TABLE_VIEW);
        
        officeHoursTable.setOnMouseClicked(e->{
            ObservableList list= officeHoursTable.getSelectionModel().getSelectedCells();
            if(!list.isEmpty()){
                TablePosition tp = (TablePosition) list.get(0);
                int cellCol= tp.getColumn();
                int cellRow= tp.getRow();
                AppData data=(AppData)app.getDataComponent();
                ArrayList <TimeSlot> OHBackup= data.getOHBackup();
                boolean validCol= data.isDayOfWeekColumn(cellCol);

                // SETUP THE COPYOH IF IS EMPTY (FIRST TIME)
                if(OHBackup.isEmpty()){
                     initCopyOH(data.getStartHour(),data.getEndHour());
                }
                
                //If the ta is selected
                if(validCol&& data.isTASelected()){
                    TeachingAssistantPrototype selectedTA= (TeachingAssistantPrototype)TATableView.getSelectionModel().getSelectedItem();
                    DayOfWeek day= data.getColumnDayOfWeek(cellCol);
                    TimeSlot timeSlot = officeHoursTable.getSelectionModel().getSelectedItem();
                    TimeSlot copy_timeSlot= getTimeSlotInCopyOH(timeSlot);
                    //Create a transaction for adding timeslots, and process the transaction
                    AddOH_Transaction addOH_transaction = new AddOH_Transaction(data,timeSlot,selectedTA ,day, copy_timeSlot,OHBackup, officeHoursTable.getItems(), this);
                    app.processTransaction(addOH_transaction);
                }
                else{
                    //no TA is selected
                    AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(),INVALID_COMMAND_TITLE, DIDNT_CHOOSE_TA_INVALID_CLICK_CONTENT);
                }
                ((TableView)(app.getGUIModule().getGUINode(OH_OFFICE_HOURS_TABLE_VIEW))).refresh();
            }
            app.getFoolproofModule().updateControls(APP_CLIPBOARD_FOOLPROOF_SETTINGS);
            updateBgColorForCell();
        }); 
        
        /////////////////AFTER SETTING UP THE TABLE, WE NEED TO LISTEN FOR THE CHANGE IN THE RADIO BUTTON TO CHANGE////////////
        ////////////////////////////////////////////THE TABLEVIEWS ACCORDINGLY/////////////////////////////////////////////////
        ObservableList<TeachingAssistantPrototype>  allTAs= TATableView.getItems();
        ObservableList<TimeSlot> originalOH= officeHoursTable.getItems();
        

        //add a listener for toggle group      
        taTypes.selectedToggleProperty().addListener(e->{
            AppData data=(AppData)app.getDataComponent();
            app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            updateTaTableForRadio(allTAs);
            
            //After they updated the taTable on the left
            //first reset the originalOH to copyOH
            resetOHToMatchTA(data,originalOH);
            //remove the ones that are not in the ta list. 
            removeOHToMatchTA(data,allTAs,originalOH);
            app.getFoolproofModule().updateControls(APP_CLIPBOARD_FOOLPROOF_SETTINGS);
            
            officeHoursTable.refresh();
            updateBgColorForCell();
        });
    }
    
    private void initControllers() {
        OHController controller = new OHController((CourseSiteGeneratorApp) app);
        AppGUIModule gui = app.getGUIModule();
        
        TextField nameTextField= (TextField)gui.getGUINode(OH_TA_ADD_NAME_TEXT_FIELD);
        TextField emailTextField= (TextField)gui.getGUINode(OH_TA_ADD_EMAIL_TEXT_FIELD);
        Button addTAButton= (Button) gui.getGUINode(OH_TA_ADD_BUTTON);
        
        //Listen to the change made in the text fields. 
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)){
                app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            }           
        });
       
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)){
                app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            }           
        });
        
        AppData data= (AppData)app.getDataComponent();
        //update the button after every action. 
        //update the copylist also
        (nameTextField).setOnAction(e -> {
            app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            if(!addTAButton.isDisabled()){
                controller.processAddTA(this);
            }
        });
        
        (emailTextField).setOnAction(e -> {
            app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            if(!addTAButton.isDisabled()){
                controller.processAddTA(this);
            }
        });

        (addTAButton).setOnAction(e -> {
            app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
            controller.processAddTA(this);
        });
        
        
        TableView officeHoursTableView = (TableView) gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        // DON'T LET ANYONE SORT THE TABLES
        for (int i = 0; i < officeHoursTableView.getColumns().size(); i++) {
            ((TableColumn)officeHoursTableView.getColumns().get(i)).setSortable(false);
        }
        officeHoursTableView.refresh();
    }
    
    
    //This initiazes the fool proof for the workplace pane.
    private void initFoolproofDesign() {
        AppFoolproofModule foolproofSettings = app.getFoolproofModule(); //has a hashmap of all the settings
        foolproofSettings.registerModeSettings(OH_FOOLPROOF_SETTINGS,
                new OfficeHoursFoolproofDesign((CourseSiteGeneratorApp)app));
    }
    
    //UPDATE THE TA TABLE ACCORDING TO RADIO BUTTON
    public void updateTaTableForRadio(ObservableList<TeachingAssistantPrototype> allTAs){
        AppData data= (AppData) app.getDataComponent();
        ArrayList <TeachingAssistantPrototype> TABackup= data.getTABackup();
        int size= TABackup.size();
        allTAs.clear();
        if(taTypes.getSelectedToggle()== app.getGUIModule().getGUINode(OH_TA_RADIO_TYPE_UNDERGRADUATE)){
            for(int i=0; i<size; i++){
                TeachingAssistantPrototype a= TABackup.get(i);
                allTAs.add(a);
            }
            for(int i=0; i<size; i++){
                TeachingAssistantPrototype a= TABackup.get(i);
                if(a.getType().equals("Graduate")){
                    allTAs.remove(a);
                }
            }
        }
        else if(taTypes.getSelectedToggle()== app.getGUIModule().getGUINode(OH_TA_RADIO_TYPE_GRADUATE)){
            for(int i=0; i<size; i++){
                TeachingAssistantPrototype a= TABackup.get(i);
                allTAs.add(a);
            }
            for(int i=0; i<size; i++){
                TeachingAssistantPrototype a= TABackup.get(i);
                if(a.getType().equals("Undergraduate")){
                    allTAs.remove(a);
                }
            }
        }
        else{
            for(int i=0; i<size; i++){
                TeachingAssistantPrototype a= TABackup.get(i);
                allTAs.add(a);
            }
        }
        
        Comparator<TeachingAssistantPrototype> comparator = (TeachingAssistantPrototype o1, TeachingAssistantPrototype o2) -> {
                return o1.getName().compareTo(o2.getName());
        };
        Collections.sort(allTAs, comparator);
    }
    
    //INITIALING THE 24 TIMESLOTS FOR COPYOH SO THERE'S NO NULL POINTER
    public void initCopyOH(int startHour, int endHour){
        AppData data= (AppData) app.getDataComponent();
        ArrayList <TimeSlot> OHBackup= data.getOHBackup();
        
        for (int i = startHour; i <= endHour; i++) {
            TimeSlot timeSlot = new TimeSlot(   this.getTimeString(i, true),
                                                this.getTimeString(i, false));
            OHBackup.add(timeSlot);
            
            TimeSlot halfTimeSlot = new TimeSlot(   this.getTimeString(i, false),
                                                    this.getTimeString(i+1, true));
            OHBackup.add(halfTimeSlot);
        }
    }
    
    private String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public TimeSlot getTimeSlotInCopyOH(TimeSlot originalTime){
        AppData data= (AppData) app.getDataComponent();
        ArrayList <TimeSlot> OHBackup= data.getOHBackup();
        for(TimeSlot ts: OHBackup){
            if(ts.getStartTime().equals(originalTime.getStartTime())){
                return ts;
            }
        }
        return null;
    }
    
    //CLEAR THE OH TABLE, AND REEST IT TO "ALL" MODE
    public void resetOHToMatchTA(AppData data, ObservableList<TimeSlot> originalOH){
        data.resetOfficeHours();
        ObservableList<TeachingAssistantPrototype> allTheTA= data.getTeachingAssistants();
        ArrayList <TimeSlot> OHBackup= data.getOHBackup();
        
        for(TeachingAssistantPrototype t: allTheTA){
            t.setSlots(0);
        }
        for(int i= 0; i<OHBackup.size(); i++){   
            TimeSlot copyTime= OHBackup.get(i);
            TimeSlot originalTime= originalOH.get(i);
            HashMap<DayOfWeek, ArrayList<TeachingAssistantPrototype>> copyTas= copyTime.getTas();

            for(DayOfWeek d: DayOfWeek.values()){
                ArrayList<TeachingAssistantPrototype> copyList= copyTas.get(d);
                for(TeachingAssistantPrototype ta: copyList){
                    data.addOH(originalTime, ta, d);
                }
            }
        }
    }
    
    //REMOVE THE ITEMS ON THE OH TABLE ACCORDINGLY
    public void removeOHToMatchTA(AppData data, ObservableList<TeachingAssistantPrototype>  currentTAs
                                  ,ObservableList<TimeSlot> originalOH){
        ArrayList <TimeSlot> OHBackup= data.getOHBackup();
        
        for(int i= 0; i<OHBackup.size(); i++){
            TimeSlot originalTime= originalOH.get(i);
            TimeSlot copyTime= OHBackup.get(i);

            HashMap<DayOfWeek, ArrayList<TeachingAssistantPrototype>> copyTas= copyTime.getTas();
            for(DayOfWeek d: DayOfWeek.values()){
                ArrayList<TeachingAssistantPrototype> copyList= copyTas.get(d);
                for(TeachingAssistantPrototype ta: copyList){
                    if(!currentTAs.contains(ta)){
                        data.removeOH(originalTime, ta, d);
                    }
                }
            }
        }
    }
    
    public void updateBgColorForCell(){
        TableView<TeachingAssistantPrototype> taTable = (TableView) app.getGUIModule().getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype selectedTA= taTable.getSelectionModel().getSelectedItem();
        for(TableColumn tc :tableColumns){
            tc.setCellFactory(e->new TableCell<ObservableList<String>, String>(){
                @Override
                public void updateItem(String item, boolean empty) {
                // Always invoke super constructor.
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        
                    } else {
                        if(selectedTA==null){
                            setText(item);
                            this.setStyle("");
                        }
                        else{
                            setText(item);
                            if(getText().equals("")) this.setStyle("");
                            else{
                                String[] arr= getText().split("\n");
                                for(String s: arr){
                                    if(s.equals(selectedTA.getName())){
                                        this.setStyle("-fx-background-color: #fcdbde;");
                                        break;
                                    }
                                    else this.setStyle(""); 
                                }
                            }
                        }
                    }
                }
            } );
        }
    }
    
}
