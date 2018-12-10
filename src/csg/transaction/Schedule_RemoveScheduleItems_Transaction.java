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
public class Schedule_RemoveScheduleItems_Transaction implements jTPS_Transaction{
    AppData data;
    ScheduleItem selected;
    TableView<ScheduleItem> table;
    Schedule sch;
    
    public Schedule_RemoveScheduleItems_Transaction(AppData data, ScheduleItem selected, TableView<ScheduleItem> table, Schedule sch){
        this.data = data;
        this.selected = selected;
        this.table = table;
        this.sch = sch;
    }
    @Override
    public void doTransaction() {
        data.removeScheduleItem(selected);
        data.getScheduleItemBackup().remove(selected);
        table.getSelectionModel().clearSelection();
        sch.updateScheduleItems();
    }

    @Override
    public void undoTransaction() {
        data.addScheduleItem(selected);
        data.getScheduleItemBackup().add(selected);
        sch.updateScheduleItems();
    }
    
}
