/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import static csg.SchedulePropertyType.*;
import csg.data.AppData;
import csg.data.ScheduleItem;
import csg.transaction.Schedule_AddScheduleItems_Transaction;
import csg.transaction.Schedule_DateChange_Transaction;
import csg.transaction.Schedule_EditScheduleItems_Transaction;
import csg.transaction.Schedule_RemoveScheduleItems_Transaction;
import djf.modules.AppGUIModule;
import djf.modules.AppLanguageModule;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author bingling.dong
 */
public class ScheduleController {
    CourseSiteGeneratorApp app;
    public ScheduleController(CourseSiteGeneratorApp App){
        this.app= App;
    }
    public void processAdd(TableView<ScheduleItem> table){
        AppData data = (AppData)app.getDataComponent();
        AppGUIModule gui = app.getGUIModule();
        ComboBox<String> typeCombo =(ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO);
        DatePicker dp= (DatePicker) gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER);
        TextField titleTF= (TextField) gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD);
        TextField topicTF= (TextField) gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD);
        TextField linkTF= (TextField) gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD);
       
        String type = typeCombo.getSelectionModel().getSelectedItem();
        LocalDate date = dp.getValue();
        String title = titleTF.getText();
        String topic = topicTF.getText();
        String link = linkTF.getText();
        ScheduleItem newItem = new ScheduleItem(type, date, title, topic, link);

        Schedule_AddScheduleItems_Transaction addTATransaction = new Schedule_AddScheduleItems_Transaction(data,newItem, table);
        app.processTransaction(addTATransaction);
    }
    
    public void processEdit(ScheduleItem selectedItem){
        String newType = (String)((ComboBox)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().getSelectedItem();
        String newTitle = ((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).getText();
        String newTopic =((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).getText();
        String newLink = ((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).getText();
        LocalDate newDate= ((DatePicker)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).getValue();
        
        if(!selectedItem.getType().equals(newType)||selectedItem.getDate()!=newDate||!selectedItem.getLink().equals(newLink)
                    ||!selectedItem.getTopic().equals(newTopic)||!selectedItem.getTitle().equals(newTitle)){
            Schedule_EditScheduleItems_Transaction tran = new Schedule_EditScheduleItems_Transaction(
                                                    selectedItem, newType, newTitle, newTopic, newLink, newDate);
            app.processTransaction(tran);
        }
    }
    
    public void processRemove(ScheduleItem selected, TableView<ScheduleItem> table){
        Schedule_RemoveScheduleItems_Transaction tran = new Schedule_RemoveScheduleItems_Transaction((AppData)app.getDataComponent(), selected, table);
        app.processTransaction(tran);
    }
    
    public void processPickDate(LocalDate oldValue, LocalDate newValue, DatePicker dp){
        Schedule_DateChange_Transaction tran = new Schedule_DateChange_Transaction(oldValue,newValue, dp);
        app.processTransaction(tran);
    }
    
    public void processSelectingItems(ScheduleItem newItem, TableView<ScheduleItem> table){
        ((ComboBox)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select(newItem.getType());
        ((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).setText(newItem.getTitle());
        ((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).setText(newItem.getTopic());
        ((TextField)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).setText(newItem.getLink());
        ((DatePicker)app.getGUIModule().getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(newItem.getDate());
    }
    
    public void processClear(TableView<ScheduleItem> table){
        AppGUIModule gui= app.getGUIModule();
        ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select("Lecture");
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).clear();
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).clear();
        ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).clear();
        ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(null);
        String newKey =CALENDAR_ADD_BUTTON.toString()+"_TEXT";
        String oldKey = CALENDAR_EDIT_BUTTON.toString()+"_TEXT";
        AppLanguageModule languageSettings = app.getLanguageModule();
        Button button = (Button)app.getGUIModule().getGUINode(CALENDAR_ADD_BUTTON);
        languageSettings.replaceLabeledControlProperty(oldKey, newKey, button.textProperty());
        table.getSelectionModel().clearSelection();
    }
}
