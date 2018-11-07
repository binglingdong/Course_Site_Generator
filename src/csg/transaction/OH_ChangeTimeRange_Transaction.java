/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.OH_OFFICE_HOURS_START_TIME_COMBO;
import csg.data.AppData;
import csg.data.TimeSlot;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_ChangeTimeRange_Transaction implements jTPS_Transaction {
    ComboBox currentBox;
    ComboBox theOtherBox;
    AppData data;
    ArrayList<String> otherOldList;
    ArrayList<String> thisOldList;
    ArrayList<String> newList;
    CourseSiteGeneratorApp app;
    
    public OH_ChangeTimeRange_Transaction(ComboBox box1, ComboBox box2, ArrayList newList, CourseSiteGeneratorApp app){
        currentBox = box1;
        theOtherBox = box2;
        this.data = (AppData)app.getDataComponent();
        this.newList= newList;
        this.app= app;
    }
    
    @Override
    public void doTransaction() {
        ArrayList <String> bad= new ArrayList<>();
        
        if(currentBox==app.getGUIModule().getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO)){
            int i=0;                                        // If we are editing the endtime, we add items to the beginning.
            for(String s: newList){                         //Add the new items to the list.  
                if(!theOtherBox.getItems().contains(s)){
                    theOtherBox.getItems().add(i,s);
                    i++;
                }
            }
            String selectedStartingTime = (String)currentBox.getSelectionModel().getSelectedItem();
            if(selectedStartingTime!=null){
                TimeSlot selectedStartTimeSlot = data.getStartTimeSlotUsingRegularTime(selectedStartingTime);
                int startIndex =  data.getOfficeHours().indexOf(selectedStartTimeSlot);
                for(int j=0; j<startIndex;j=j+1){
                    data.getOfficeHours().remove(0);
                }
            }
            String selectedEndingTime = (String)theOtherBox.getSelectionModel().getSelectedItem();
            if(selectedEndingTime!= null){
                int size= data.getOfficeHours().size();
                TimeSlot selectedEndTimeSlot = data.getEndTimeSlotUsingRegularTime(selectedEndingTime);
                int endIndex =  data.getOfficeHours().indexOf(selectedEndTimeSlot)+1;
                for(int j=endIndex; j<size;j++){
                    data.getOfficeHours().remove(endIndex);
                }
            }
        }
        else{                                              // If we are editing the startime, we add items to the end. 
            int i=theOtherBox.getItems().size();            
            for(String s: newList){                         //Add the new items to the list.  
                if(!theOtherBox.getItems().contains(s)){
                    theOtherBox.getItems().add(i,s);
                    i++;
                }
            }
            String selectedStartingTime = (String)theOtherBox.getSelectionModel().getSelectedItem();
            if(selectedStartingTime!=null){
                TimeSlot selectedStartTimeSlot = data.getStartTimeSlotUsingRegularTime(selectedStartingTime);
                int startIndex =  data.getOfficeHours().indexOf(selectedStartTimeSlot);
                for(int j=0; j<startIndex;j=j+1){
                    data.getOfficeHours().remove(0);
                }
            }
            String selectedEndingTime = (String)currentBox.getSelectionModel().getSelectedItem();
            if(selectedEndingTime!= null){
                int size= data.getOfficeHours().size();
                TimeSlot selectedEndTimeSlot = data.getEndTimeSlotUsingRegularTime(selectedEndingTime);
                int endIndex =  data.getOfficeHours().indexOf(selectedEndTimeSlot)+1;
                for(int j=endIndex; j<size;j++){
                    data.getOfficeHours().remove(endIndex);
                }
            }
        }
        for(Object o: theOtherBox.getItems()){          //remove all non-in-range items
            String s= (String)o;
            if(!newList.contains(s)){
                bad.add(s);
            }
        }
        for(String s: bad){
            theOtherBox.getItems().remove(s);
        }
        
        
    }

    @Override
    public void undoTransaction() {
        
//        ArrayList <String> bad= new ArrayList<>();
//        
//        if(currentBox==app.getGUIModule().getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO)){
//            int i=theOtherBox.getItems().size();                                   // If we are editing the endtime, we add items to the beginning.
//            for(String s: oldList){                         //Add the new items to the list.  
//                if(!theOtherBox.getItems().contains(s)){
//                    theOtherBox.getItems().add(i,s);
//                    i++;
//                }
//            }
//        }else{                                              // If we are editing the startime, we add items to the end. 
//            int i=0;              
//            for(String s: oldList){                         //Add the new items to the list.  
//                if(!theOtherBox.getItems().contains(s)){
//                    theOtherBox.getItems().add(i,s);
//                    i++;
//                }
//            }
//        }
//        for(Object o: theOtherBox.getItems()){          //remove all non-in-range items
//            String s= (String)o;
//            if(!oldList.contains(s)){
//                bad.add(s);
//            }
//        }
//        for(String s: bad){
//            theOtherBox.getItems().remove(s);
//        }
    }
    
}
