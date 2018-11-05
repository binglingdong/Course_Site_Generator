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
import csg.transaction.AddTA_Transaction;
import csg.transaction.RemoveTA_Transaction;
import csg.workspace.OfficeHours;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
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

        AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta, data.getTABackup(),ohws);
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
        RemoveTA_Transaction removeTATransaction = new RemoveTA_Transaction(data, selectedTA, ohws);
        app.processTransaction(removeTATransaction);
        app.getFoolproofModule().updateControls(OH_FOOLPROOF_SETTINGS);
    }   
}


