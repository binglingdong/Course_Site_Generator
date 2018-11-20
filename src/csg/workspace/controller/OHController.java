/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.*;
import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.transaction.OH_AddTA_Transaction;
import csg.transaction.OH_ChangeTimeRange_Transaction;
import csg.transaction.OH_RemoveTA_Transaction;
import csg.workspace.OfficeHours;
import djf.modules.AppGUIModule;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author bingling.dong
 */
public class OHController {
    CourseSiteGeneratorApp app;
    public OHController(CourseSiteGeneratorApp App){
        this.app= App;
    }
    
    public void processAddTA(OfficeHours ohws) {
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        TextField nameTF = (TextField) gui.getGUINode(OH_TA_ADD_NAME_TEXT_FIELD);
        String name = nameTF.getText();
        TextField emailTF = (TextField) gui.getGUINode(OH_TA_ADD_EMAIL_TEXT_FIELD);
        String email= emailTF.getText();
        RadioButton graduate= ((RadioButton)gui.getGUINode(OH_TA_RADIO_TYPE_GRADUATE));
        TeachingAssistantPrototype ta;
        
        if(graduate.isSelected()){
            ta = new TeachingAssistantPrototype(name,email,0,"Graduate");
        }
        else{
            ta = new TeachingAssistantPrototype(name,email,0,"Undergraduate");
        }

        OH_AddTA_Transaction addTATransaction = new OH_AddTA_Transaction(data, ta, data.getTABackup(),ohws);
        app.processTransaction(addTATransaction);
        
        // NOW CLEAR THE TEXT FIELDS
        nameTF.setText("");
        emailTF.setText("");
        nameTF.requestFocus();
        emailTF.requestFocus();
        
        app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
    }
    
    public void processRemoveTA(OfficeHours ohws){
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        TableView taTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype selectedTA= (TeachingAssistantPrototype)taTable.getSelectionModel().getSelectedItem();
        OH_RemoveTA_Transaction removeTATransaction = new OH_RemoveTA_Transaction(data, selectedTA, ohws);
        app.processTransaction(removeTATransaction);
        app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
    }   
    
    public void processTimeChange(ComboBox<String> thisBox, ComboBox<String> otherBox, String newValue,String oldValue, OfficeHours ohws){
        OH_ChangeTimeRange_Transaction transaction = new OH_ChangeTimeRange_Transaction(thisBox, otherBox, app, oldValue, newValue, ohws);
        app.processTransaction(transaction);
    }
}


