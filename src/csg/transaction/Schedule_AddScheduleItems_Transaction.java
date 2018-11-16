/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.ScheduleItem;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Schedule_AddScheduleItems_Transaction implements jTPS_Transaction{
    AppData data;
    ScheduleItem newItem;
    
    public Schedule_AddScheduleItems_Transaction(AppData data, ScheduleItem newItem){
        this.data = data;
        this.newItem = newItem;
    }
    
    @Override
    public void doTransaction() {
        data.addScheduleItem(newItem);
    }

    @Override
    public void undoTransaction() {
        data.removeScheduleItem(newItem);
    }
    
}
