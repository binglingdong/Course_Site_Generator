/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.ScheduleItem;
import csg.workspace.Schedule;
import java.time.LocalDate;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Schedule_EditScheduleItems_Transaction implements jTPS_Transaction{
    ScheduleItem item;
    String newType;
    String newTitle;
    String newTopic;
    String newLink;
    LocalDate newDate;
    String oldType;
    String oldTitle;
    String oldTopic;
    String oldLink;
    LocalDate oldDate;
    Schedule sch;
    
    public Schedule_EditScheduleItems_Transaction(ScheduleItem item, String newType, String newTitle, String newTopic,
                                                String newLink, LocalDate newDate, Schedule sch){
        this.newLink = newLink;
        this.newDate = newDate;
        this.newTitle= newTitle;
        this.newTopic = newTopic;
        this.newType = newType;
        this.item = item;
        oldType = item.getType();
        oldTitle = item.getTitle();
        oldTopic = item.getTopic();
        oldLink = item.getLink();
        oldDate = item.getDate();
        this.sch = sch;
    }
    
    @Override
    public void doTransaction() {
        item.setDate(newDate);
        item.setLink(newLink);
        item.setType(newType);
        item.setTitle(newTitle);
        item.setTopic(newTopic);
        sch.updateScheduleItems();
    }

    @Override
    public void undoTransaction() {
        item.setDate(oldDate);
        item.setLink(oldLink);
        item.setType(oldType);
        item.setTitle(oldTitle);
        item.setTopic(oldTopic);
        sch.updateScheduleItems();
    }
    
}
