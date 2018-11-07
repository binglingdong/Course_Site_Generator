/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.data.TimeSlot.DayOfWeek;
import csg.workspace.OfficeHours;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_AddOH_Transaction implements jTPS_Transaction {
    AppData data;
    TimeSlot timeSlot;
    DayOfWeek theDay;
    TeachingAssistantPrototype TA;
    TimeSlot copyTimeSlot;
    ArrayList <TimeSlot> copyOH;
    ObservableList<TimeSlot> originalOH;
    OfficeHours ohws;
    
    public OH_AddOH_Transaction(AppData initData, TimeSlot initTime, TeachingAssistantPrototype TA,
            DayOfWeek theDay, TimeSlot copyTime, ArrayList <TimeSlot> copyOH,  ObservableList<TimeSlot> originalOH
            ,OfficeHours ohws) {
        data = initData;
        timeSlot = initTime;
        this.theDay=theDay;
        this.TA= TA;
        this.copyTimeSlot= copyTime;
        this.copyOH= copyOH;
        this.originalOH= originalOH;
        this.ohws= ohws;
    }

    @Override
    public void doTransaction() {
        //getting everysingle ta on that timeslot, then get the array for the tas that are on a certain day. 
        if(!timeSlot.getTas().get(theDay).contains(TA)){//if the TA is not in the cell yet.
            data.addOH(timeSlot,TA,theDay);  //then add TA
            
            //update the copy list
            ArrayList<TeachingAssistantPrototype> copy_list= copyTimeSlot.getTas().get(theDay);
            copy_list.add(TA);
        }
        else{
            data.removeOH(timeSlot, TA, theDay);//else, meaning the TA already exists in the cell, then remove it. 
            ArrayList<TeachingAssistantPrototype> copy_list= copyTimeSlot.getTas().get(theDay);
            copy_list.remove(TA);
        }
        ohws.resetOHToMatchTA(data, originalOH);
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), originalOH);
        ohws.updateBgColorForCell();
    }

    @Override
    public void undoTransaction() {
        //In terms of the UNDO, if the TA currently exists in the cell. Then remove it.
        if(timeSlot.getTas().get(theDay).contains(TA)){
            data.removeOH(timeSlot,TA,theDay);
            ArrayList<TeachingAssistantPrototype> copy_list= copyTimeSlot.getTas().get(theDay);
            copy_list.remove(TA);
        }
        
        //else, meaning that if the TA is not currently in the cell. Which means, we just removed it.
        else{
            data.addOH(timeSlot,TA,theDay);  //Now we add it back. 
            ArrayList<TeachingAssistantPrototype> copy_list= copyTimeSlot.getTas().get(theDay);
            copy_list.add(TA);
        }        
        ohws.resetOHToMatchTA(data, originalOH);
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), originalOH);
        ohws.updateBgColorForCell();
    }
}

