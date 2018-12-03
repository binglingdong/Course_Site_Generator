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
import djf.modules.AppGUIModule;
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    public void processAdd(){
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
        
        Schedule_AddScheduleItems_Transaction addTATransaction = new Schedule_AddScheduleItems_Transaction(data,newItem);
        app.processTransaction(addTATransaction);
    }
    
    public void processPickDate(LocalDate oldValue, LocalDate newValue, DatePicker dp){
        Schedule_DateChange_Transaction tran = new Schedule_DateChange_Transaction(oldValue,newValue, dp);
        app.processTransaction(tran);
    }
}
