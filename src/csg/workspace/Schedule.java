/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.SchedulePropertyType.*;
import csg.data.AppData;
import csg.data.ScheduleItem;
import csg.workspace.controller.ScheduleController;
import static csg.workspace.style.Style.*;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.modules.AppLanguageModule;
import djf.ui.AppNodesBuilder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class Schedule {
    private Tab ScheduleTab; 
    CourseSiteGeneratorApp app;
    
    public Schedule(Tab tab, CourseSiteGeneratorApp app){
        ScheduleTab= tab;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        ScrollPane mainPane = csgBuilder.buildScrollPane(SCHEDULE_PANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        VBox foregroundPane = csgBuilder.buildVBox("", null, CLASS_PANES_BACKGROUND, ENABLED);
        foregroundPane.setSpacing(8);
        foregroundPane.setPadding(new Insets(10,8,10,8));
        
        GridPane calendarPane = csgBuilder.buildGridPane("", foregroundPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox scheduleItemsPane = csgBuilder.buildVBox("", foregroundPane, CLASS_PANES_FOREGROUND, ENABLED);
        GridPane addAndEditPane = csgBuilder.buildGridPane("", foregroundPane, CLASS_PANES_FOREGROUND, ENABLED);
        
        calendarPane.setPadding(new Insets(10,20,10,20));
        calendarPane.setHgap(8);
        calendarPane.setVgap(8);
        scheduleItemsPane.setPadding(new Insets(10,20,10,20));
        scheduleItemsPane.setSpacing(8);
        addAndEditPane.setPadding(new Insets(10,20,10,20));
        addAndEditPane.setHgap(8);
        addAndEditPane.setVgap(8);
        
        initCalendarPane(calendarPane);
        initScheduleItemsPane(scheduleItemsPane);
        initAddAndEditPane(addAndEditPane);
        
        mainPane.setFitToHeight(true);
        mainPane.setFitToWidth(true);
        mainPane.setContent(foregroundPane);
        ScheduleTab.setContent(mainPane);
        
    }
    
    public void initCalendarPane(GridPane parentPane){
        ScheduleController controller = new ScheduleController(app);
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        csgBuilder.buildLabel(CALENDAR_BOUNDARIES_LABEL,parentPane, 0, 0, 2, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_BOUNDARIES_STARTING_LABEL, parentPane, 0, 1, 1, 1, CLASS_LABEL_BACKGROUND, ENABLED);
        csgBuilder.buildLabel(CALENDAR_BOUNDARIES_ENDING_LABEL, parentPane, 3, 1, 1, 1, CLASS_LABEL_BACKGROUND, ENABLED);
        DatePicker startingDate = csgBuilder.buildDatePicker(CALENDAR_BOUNDARIES_STARTING_DATEPICKER, parentPane, 1, 1, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        DatePicker endingDate = csgBuilder.buildDatePicker(CALENDAR_BOUNDARIES_ENDING_DATEPICKER, parentPane, 4, 1, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        startingDate.setEditable(false);
        endingDate.setEditable(false);
        
        startingDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if(startingDate.isFocused()){
                controller.processPickDate(oldValue, newValue, startingDate);
            }
        });
        
        endingDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if(endingDate.isFocused()){
                controller.processPickDate(oldValue, newValue, endingDate); 
            }
        });
        
        startingDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(endingDate.getValue()!=null){
                    setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY        // THIs is for updating 
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.FRIDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY
                        ||date.compareTo(endingDate.getValue()) > 0);
                }  
                else{
                     setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY            //THIS is for initialize
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.FRIDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY);
                }
                updateScheduleItems();
            }
        });
        
        endingDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(startingDate.getValue()!=null){
                    setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY                //for= updating
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY  
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.MONDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY
                        ||date.compareTo(startingDate.getValue()) < 0);
                }
                else{
                    setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY                    //For initailizing
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.MONDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY); 
                }
                updateScheduleItems();
            }
            
        });
        
    }
    
    public void initScheduleItemsPane(VBox parentPane){
        
        ScheduleController controller = new ScheduleController(app);
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        HBox hb1 = csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hb1.setSpacing(10);
        Button removeButton = csgBuilder.buildTextButton(CALENDAR_SCHEDULE_ITEMS_REMOVE_BUTTON, hb1, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        csgBuilder.buildLabel(CALENDAR_SCHEDULE_ITMES_LABEL, hb1, CLASS_MINOR_LABELS, ENABLED);
        TableView<ScheduleItem> scheudleItemsTableView = csgBuilder.buildTableView(CALENDAR_SCHEDULE_ITEMS_TABLEVIEW, parentPane, CLASS_TABLEVIEW, ENABLED);
        VBox.setVgrow(scheudleItemsTableView, Priority.ALWAYS);
        
        TableColumn typeTableColumn = csgBuilder.buildTableColumn(CALENDAR_SCHEUDLE_ITMES_TYPE_COLUMN, scheudleItemsTableView, CLASS_TABLE_COLUMNS);
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        typeTableColumn.prefWidthProperty().bind(scheudleItemsTableView.widthProperty().multiply(1.0/5.0));
        
        TableColumn dateTableColumn = csgBuilder.buildTableColumn(CALENDAR_SCHEUDLE_ITMES_DATE_COLUMN, scheudleItemsTableView, CLASS_TABLE_COLUMNS);
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dateString"));
        dateTableColumn.prefWidthProperty().bind(scheudleItemsTableView.widthProperty().multiply(1.0/5.0));
        
        TableColumn titleTableColumn = csgBuilder.buildTableColumn(CALENDAR_SCHEUDLE_ITMES_TITLE_COLUMN, scheudleItemsTableView, CLASS_TABLE_COLUMNS);
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<String, String>("title"));
        titleTableColumn.prefWidthProperty().bind(scheudleItemsTableView.widthProperty().multiply(1.0/5.0));
        
        TableColumn topicTableColumn = csgBuilder.buildTableColumn(CALENDAR_SCHEUDLE_ITMES_TOPIC_COLUMN, scheudleItemsTableView, CLASS_TABLE_COLUMNS);
        topicTableColumn.setCellValueFactory(new PropertyValueFactory<String, String>("topic"));
        topicTableColumn.prefWidthProperty().bind(scheudleItemsTableView.widthProperty().multiply(2.0/5.0));
        
        removeButton.setOnAction(e->{
            ScheduleItem selected = scheudleItemsTableView.getSelectionModel().getSelectedItem();
            controller.processRemove(selected, scheudleItemsTableView,this);
        });
        
        scheudleItemsTableView.setOnMouseClicked(e->{
            if(scheudleItemsTableView.getSelectionModel().getSelectedItem()!=null){
                controller.processSelectingItems(scheudleItemsTableView.getSelectionModel().getSelectedItem(), scheudleItemsTableView);
                String oldKey =CALENDAR_ADD_BUTTON.toString()+"_TEXT";
                String newKey = CALENDAR_EDIT_BUTTON.toString()+"_TEXT";
                AppLanguageModule languageSettings = app.getLanguageModule();
                Button button = (Button)app.getGUIModule().getGUINode(CALENDAR_ADD_BUTTON);
                languageSettings.replaceLabeledControlProperty(oldKey, newKey, button.textProperty());
            }
        });
        scheudleItemsTableView.getSelectionModel().selectedItemProperty().addListener((e, oldValue, newValue)->{
            if(newValue==null){
                 String newKey =CALENDAR_ADD_BUTTON.toString()+"_TEXT";
                String oldKey = CALENDAR_EDIT_BUTTON.toString()+"_TEXT";
                AppLanguageModule languageSettings = app.getLanguageModule();
                Button button = (Button)app.getGUIModule().getGUINode(CALENDAR_ADD_BUTTON);
                languageSettings.replaceLabeledControlProperty(oldKey, newKey, button.textProperty());
            }
        });
    }
    
    public void initAddAndEditPane(GridPane parentPane){
        ScheduleController controller = new ScheduleController(app);
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_LABEL, parentPane, 0, 0, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_TYPE_LABEL, parentPane, 0, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_DATE_LABEL, parentPane, 0, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_TITLE_LABEL, parentPane, 0, 3, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_TOPIC_LABEL, parentPane, 0, 4, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(CALENDAR_ADD_EDIT_LINK_LABEL, parentPane, 0, 5, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        
        ArrayList<String> list= new ArrayList<>();
        list.add("Holiday");
        list.add("Lecture");
        list.add("HW");
        list.add("Recitation");
        list.add("Reference");
        ComboBox typeCombo = csgBuilder.buildComboBox(CALENDAR_ADD_EDIT_TYPE_COMBO, list, "Lecture", null, CLASS_INPUT_CONTROL, ENABLED);
        parentPane.add(typeCombo, 1, 1, 2, 1);
        DatePicker dp= csgBuilder.buildDatePicker(CALENDAR_ADD_EDIT_DATE_DATEPICKER, parentPane, 1, 2, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField titleTF= csgBuilder.buildTextField(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD, parentPane, 1, 3, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField topicTF= csgBuilder.buildTextField(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD, parentPane, 1, 4, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField linkTF= csgBuilder.buildTextField(CALENDAR_ADD_EDIT_LINK_TEXTFIELD, parentPane, 1, 5, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        
        Button addOrUpdateButton = csgBuilder.buildTextButton(CALENDAR_ADD_BUTTON, parentPane, 0, 6, 2, 1, CLASS_SCHEDULE_BUTTONS, ENABLED);
        Button clearButton = csgBuilder.buildTextButton(CALENDAR_CLEAR_BUTTON, parentPane, 2, 6, 2, 1, CLASS_SCHEDULE_BUTTONS, ENABLED);
        addOrUpdateButton.prefWidthProperty().bind(parentPane.widthProperty().multiply(0.15));
        clearButton.prefWidthProperty().bind(parentPane.widthProperty().multiply(0.13));
        TableView<ScheduleItem> scheudleItemsTableView = (TableView<ScheduleItem>) app.getGUIModule().getGUINode(CALENDAR_SCHEDULE_ITEMS_TABLEVIEW);
        
        addOrUpdateButton.setOnAction(e->{
            if(scheudleItemsTableView.getSelectionModel().getSelectedItem()==null){
                controller.processAdd(scheudleItemsTableView,this); 
            }
            else{
                controller.processEdit(scheudleItemsTableView.getSelectionModel().getSelectedItem(), this);
            }
        });
        clearButton.setOnAction(e->{
            controller.processClear(scheudleItemsTableView);
        });
        
        DatePicker startingDate = (DatePicker) app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER);
        DatePicker endingDate = (DatePicker) app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER);
        dp.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(endingDate.getValue()!=null&& startingDate.getValue()!=null){
                    setDisable(empty||date.compareTo(endingDate.getValue())>0||
                    date.compareTo(startingDate.getValue())<0);
                }
                else if(startingDate.getValue()!=null){
                    setDisable(empty||date.compareTo(startingDate.getValue())<0);
                }
                else if(endingDate.getValue()!=null){
                    setDisable(empty||date.compareTo(endingDate.getValue())<0);
                }
            }
        });
    }
    
    public void updateScheduleItems(){
        DatePicker start= ((DatePicker)app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER));
        DatePicker end= ((DatePicker)app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER));
        AppData data= (AppData)app.getDataComponent();
        TableView<ScheduleItem> table= ((TableView)app.getGUIModule().getGUINode(CALENDAR_SCHEDULE_ITEMS_TABLEVIEW));
        table.getItems().clear();
        for(ScheduleItem item: data.getScheduleItemBackup()){
            table.getItems().add(item);
        }
        if(start.getValue()!=null){
            LocalDate startDate = start.getValue();
            for(ScheduleItem item: data.getScheduleItemBackup()){
                if(item.getDate()!=null){
                    if(item.getDate().compareTo(startDate)<0){
                        if(table.getItems().contains(item)){
                            table.getItems().remove(item);
                        }
                    }
                }
                
            }
        }
        if(end.getValue()!=null){
            LocalDate endDate = end.getValue();
            for(ScheduleItem item: data.getScheduleItemBackup()){
                if(item.getDate()!=null){
                    if(item.getDate().compareTo(endDate)>0){
                      if(table.getItems().contains(item)){
                          table.getItems().remove(item);
                      }
                    }  
                }
            }
        }
        
        Comparator<ScheduleItem> comparator = (ScheduleItem o1, ScheduleItem o2) -> {
            if(o1.getDate()!=null&&o2.getDate()!=null){
                return o1.getDate().compareTo(o2.getDate());
            }
            else if(o1.getDate()==null){
                return 1;
            }else{
                return -1;
            }
        };
        Collections.sort(table.getItems(), comparator);
    }
    
    ///////////////////////////// STILL NEED MORE IMPLEMENTATION/////////////////////////////
    public void reset(){
        AppGUIModule gui= app.getGUIModule();
        ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select("Lecture");
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).clear();
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).clear();
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).clear();
        ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER)).setValue(null);
        ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER)).setValue(null);
        ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(null);
        String newKey =CALENDAR_ADD_BUTTON.toString()+"_TEXT";
        String oldKey = CALENDAR_EDIT_BUTTON.toString()+"_TEXT";
        AppLanguageModule languageSettings = app.getLanguageModule();
        Button button = (Button)app.getGUIModule().getGUINode(CALENDAR_ADD_BUTTON);
        languageSettings.replaceLabeledControlProperty(oldKey, newKey, button.textProperty());
    }
}
