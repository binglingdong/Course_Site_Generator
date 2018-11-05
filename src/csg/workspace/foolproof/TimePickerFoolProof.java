/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.foolproof;

import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.OH_OFFICE_HOURS_END_TIME_COMBO;
import static csg.OfficeHoursPropertyType.OH_OFFICE_HOURS_START_TIME_COMBO;
import static csg.OfficeHoursPropertyType.OH_OFFICE_HOURS_TABLE_VIEW;
import csg.data.AppData;
import csg.data.TimeSlot;
import csg.workspace.OfficeHours;
import djf.modules.AppGUIModule;
import djf.ui.foolproof.FoolproofDesign;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

/**
 *
 * @author bingling.dong
 */
public class TimePickerFoolProof implements FoolproofDesign{
    CourseSiteGeneratorApp app;
    OfficeHours ohws;
    
    public TimePickerFoolProof(CourseSiteGeneratorApp app, OfficeHours ohws){
        this.app= app;
        this.ohws= ohws;
    }
    
    @Override
    public void updateControls() {
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        ComboBox startTime = (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO);
        ComboBox endTime= (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_END_TIME_COMBO);
        TableView OHTable = (TableView)gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        ObservableList<TimeSlot> officeHours = data.getOfficeHours();
        
        
        if(endTime.isDisabled()){       //if start value has not been picked yet. 
                                        //PICKING START VALUE RIGHT NOW.
            ohws.resetOHToMatchTA(data, officeHours);                               //RESETS THE OH SHEET
            ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), officeHours);
            String selectedStartTime = (String)startTime.getSelectionModel().getSelectedItem();
            if(selectedStartTime!=null){
                TimeSlot selectedStartTimeSlot = data.getStartTimeSlotUsingRegularTime(selectedStartTime);
                int startIndex = officeHours.indexOf(selectedStartTimeSlot);
                for(int i=0; i<startIndex;i=i+1){
                    officeHours.remove(0);
                }
                
                endTime.getItems().clear();             //RESET THE SELECTABLE ITEMS ON THE ENDTIME LIST    
                if(endTime.getItems().isEmpty()){
                    for(TimeSlot time: officeHours){
                        String endTimeString = time.getEndTime();
                        endTime.getItems().add(endTimeString);
                    }
                }
            OHTable.refresh();
            }
        }
        else{                           //start value picked, deciding endvalue.
            
            String selectedEndTime = (String)endTime.getSelectionModel().getSelectedItem();
            int size= officeHours.size();
            if(selectedEndTime!= null){
                TimeSlot selectedEndTimeSlot = data.getEndTimeSlotUsingRegularTime(selectedEndTime);
                int endIndex = officeHours.indexOf(selectedEndTimeSlot)+1;
                for(int i=endIndex; i<size;i++){
                    officeHours.remove(endIndex);
                }
            }
            OHTable.refresh();
        }
        
        
        
        
        
    }
    
}
