/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.OH_OFFICE_HOURS_START_TIME_COMBO;
import csg.data.AppData;
import csg.workspace.OfficeHours;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_ChangeTimeRange_Transaction implements jTPS_Transaction {
    ComboBox<String> currentBox;
    ComboBox<String> theOtherBox;
    AppData data;
    ObservableList<String> theOtherOldList;
    ObservableList<String> currentOldList; 
    String oldItem;
    String newItem;
    CourseSiteGeneratorApp app;
    OfficeHours ohws;
    
    public OH_ChangeTimeRange_Transaction(ComboBox<String> box1, ComboBox<String> box2, 
            CourseSiteGeneratorApp app, String oldValue, String newValue, OfficeHours ohws){
        currentBox = box1;
        theOtherBox = box2;
        this.data = (AppData)app.getDataComponent();
        this.app= app;
        ArrayList<String> currentArray = new ArrayList<>();
        ArrayList<String> otherArray = new ArrayList<>();
        for(String s: box1.getItems()){
            currentArray.add(s);
        }
        for(String s: box2.getItems()){
            otherArray.add(s);
        }
        oldItem = oldValue;
        newItem = newValue;
        currentOldList = FXCollections.observableArrayList(currentArray);
        theOtherOldList = FXCollections.observableArrayList(otherArray);
        this.ohws= ohws;
    }
    
    @Override
    public void doTransaction() {
        ArrayList<String> defaultList = data.getDefaultTimeRangeBackup();
        ArrayList<String> newList = new ArrayList<>();
        int index = defaultList.indexOf(newItem);
        if(currentBox==app.getGUIModule().getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO)){//IF this box is the starttime
            for(int i=index+1; i<defaultList.size(); i++){
                newList.add(defaultList.get(i));
            }
        }
        else{       //if it's endtime
            for(int i=0; i<index; i++){
                newList.add(defaultList.get(i));
            }
        }
        
        if(currentBox==app.getGUIModule().getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO)){
            theOtherBox.setItems(FXCollections.observableArrayList(newList));
        }   ///////TWO CASES
        else{                                              
            theOtherBox.setItems(FXCollections.observableArrayList(newList));
        }
        
        for(String s: theOtherBox.getItems()){          //remove all non-in-range items
            if(!newList.contains(s)){
               theOtherBox.getItems().remove(s);
            }
        }
        
        currentBox.getSelectionModel().select(newItem);
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
    }

    @Override
    public void undoTransaction() {
        currentBox.setItems(currentOldList);
        theOtherBox.setItems(theOtherOldList);
        currentBox.getSelectionModel().select(oldItem);
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
    }
    
}
