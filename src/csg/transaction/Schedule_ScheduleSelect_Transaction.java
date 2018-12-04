/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import static csg.SchedulePropertyType.CALENDAR_ADD_EDIT_DATE_DATEPICKER;
import static csg.SchedulePropertyType.CALENDAR_ADD_EDIT_LINK_TEXTFIELD;
import static csg.SchedulePropertyType.CALENDAR_ADD_EDIT_TITLE_TEXTFIELD;
import static csg.SchedulePropertyType.CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD;
import static csg.SchedulePropertyType.CALENDAR_ADD_EDIT_TYPE_COMBO;
import csg.data.ScheduleItem;
import djf.modules.AppGUIModule;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Schedule_ScheduleSelect_Transaction implements jTPS_Transaction {
    ScheduleItem oldItem;
    ScheduleItem newItem;
    TableView<ScheduleItem> scheudleItemsTableView;
    AppGUIModule gui;
    
    public Schedule_ScheduleSelect_Transaction(ScheduleItem oldItem, ScheduleItem newItem,
                                        TableView<ScheduleItem> table, AppGUIModule gui){
        this.oldItem = oldItem;
        this.newItem = newItem;
        scheudleItemsTableView = table;
        this.gui = gui;
    }
    
    @Override
    public void doTransaction() {
        if(newItem!= null){
            ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select(newItem.getType());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).setText(newItem.getTitle());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).setText(newItem.getTopic());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).setText(newItem.getLink());
            ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(newItem.getDate());
            scheudleItemsTableView.getSelectionModel().select(newItem);
        }
        else{
            ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select("Lecture");
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).clear();
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).clear();
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).clear();
            ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(null);
            scheudleItemsTableView.getSelectionModel().clearSelection();
        }
    }

    @Override
    public void undoTransaction() {
        if(oldItem!= null){
            ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select(oldItem.getType());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).setText(oldItem.getTitle());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).setText(oldItem.getTopic());
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).setText(oldItem.getLink());
            ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(oldItem.getDate());
            scheudleItemsTableView.getSelectionModel().select(oldItem);

        }
        else{
            ((ComboBox)gui.getGUINode(CALENDAR_ADD_EDIT_TYPE_COMBO)).getSelectionModel().select("Lecture");
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TITLE_TEXTFIELD)).clear();
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_TOPIC_TEXTFIELD)).clear();
            ((TextField)gui.getGUINode(CALENDAR_ADD_EDIT_LINK_TEXTFIELD)).clear();
            ((DatePicker)gui.getGUINode(CALENDAR_ADD_EDIT_DATE_DATEPICKER)).setValue(null);
            scheudleItemsTableView.getSelectionModel().clearSelection();
        }
    }
    
}
