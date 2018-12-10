/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.ScheduleItem;
import csg.workspace.Schedule;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Schedule_AddScheduleItems_Transaction implements jTPS_Transaction{
    AppData data;
    ScheduleItem newItem;
    TableView<ScheduleItem> table;
    Schedule sch;
    
    public Schedule_AddScheduleItems_Transaction(Schedule sch,AppData data, ScheduleItem newItem, TableView<ScheduleItem> table){
        this.data = data;
        this.newItem = newItem;
        this.table = table;
        this.sch = sch;
    }
    
    @Override
    public void doTransaction() {
        data.addScheduleItem(newItem);
        data.getScheduleItemBackup().add(newItem);                              //Adding and removing items will always be in range.
        sch.updateScheduleItems();                                              //UPDATE to sort         
    }

    @Override
    public void undoTransaction() {
        data.removeScheduleItem(newItem);
        data.getScheduleItemBackup().remove(newItem);
        table.getSelectionModel().clearSelection();
        sch.updateScheduleItems();
    }
    
}
