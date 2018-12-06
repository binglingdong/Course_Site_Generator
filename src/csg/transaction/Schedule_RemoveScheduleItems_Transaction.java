/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.ScheduleItem;
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
    
    public Schedule_RemoveScheduleItems_Transaction(AppData data, ScheduleItem selected, TableView<ScheduleItem> table){
        this.data = data;
        this.selected = selected;
        this.table = table;
    }
    @Override
    public void doTransaction() {
        data.removeScheduleItem(selected);
        table.getSelectionModel().clearSelection();
    }

    @Override
    public void undoTransaction() {
        data.addScheduleItem(selected);
    }
    
}
