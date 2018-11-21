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
import java.util.HashMap;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_RemoveTA_Transaction implements jTPS_Transaction {
    AppData data;
    TeachingAssistantPrototype selectedTA;
    OfficeHours ohws;
    ArrayList<DayOfWeek> removedDays = new ArrayList<>();
    ArrayList<Integer> removedTimeSlotIndex = new ArrayList<>();
    
    public OH_RemoveTA_Transaction(AppData initData, TeachingAssistantPrototype ta, OfficeHours ohws){
        data= initData;
        this.ohws= ohws;
        selectedTA= ta;
    }
    
    @Override
    public void doTransaction() {
        removedDays.clear();
        removedTimeSlotIndex.clear();
        for(int t=0; t<data.getOHBackup().size();t++){
            TimeSlot time = data.getOHBackup().get(t);
            HashMap<DayOfWeek, ArrayList<TeachingAssistantPrototype>> tas= time.getTas();
            for(DayOfWeek dow: DayOfWeek.values()){
                ArrayList<TeachingAssistantPrototype> list= tas.get(dow);
                int size = list.size();
                int j =0;
                for(int i=0;i< size; i++){
                    TeachingAssistantPrototype ta = list.get(j);
                    if(ta.getName().equals(selectedTA.getName())){
                        list.remove(ta);
                        removedTimeSlotIndex.add(t);
                        removedDays.add(dow);
                    }
                    else{
                        j++;
                    }
                }
            }
        }
        data.getTABackup().remove(selectedTA);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }

    @Override
    public void undoTransaction() {
        data.getTABackup().add(selectedTA);
        ArrayList<TimeSlot> ohBackup = data.getOHBackup();
        for(int j=0; j<removedTimeSlotIndex.size(); j++){
            Integer i= removedTimeSlotIndex.get(j);
            TimeSlot time= ohBackup.get(i);
            time.getTas().get(removedDays.get(j)).add(selectedTA);
        }
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }
    
}
