/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.workspace.OfficeHours;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class EditTA_Transaction implements jTPS_Transaction{
    String newName;
    String newEmail;
    String newType;
    OfficeHours ohws;
    TeachingAssistantPrototype selectedTA;
    TableView<TeachingAssistantPrototype> taTable;
    AppData data;
    String oriName;
    String oriEmail;
    String oriType;
    
    public EditTA_Transaction(String newName, String newEmail, String newType, 
                              TeachingAssistantPrototype selectedTA,TableView<TeachingAssistantPrototype> taTable,
                              OfficeHours ohws, AppData data){
        this.newEmail= newEmail;
        this.newName= newName;
        this.newType= newType;
        this.selectedTA= selectedTA;
        this.taTable=taTable;
        this.ohws=ohws;
        this.data= data;
        oriName= selectedTA.getName();
        oriEmail= selectedTA.getEmail();
        oriType= selectedTA.getType();
    }
    
    @Override
    public void doTransaction() {
        selectedTA.setName(newName);
        selectedTA.setEmail(newEmail);
        selectedTA.setType(newType);
        ohws.updateTaTableForRadio(taTable.getItems());
        ohws.resetOHToMatchTA(data,data.getOfficeHours());
        ohws.removeOHToMatchTA(data, taTable.getItems(), data.getOfficeHours());
    }

    @Override
    public void undoTransaction() {
        selectedTA.setType(oriType);
        selectedTA.setEmail(oriEmail);
        selectedTA.setName(oriName);
        ohws.updateTaTableForRadio(taTable.getItems());
        ohws.resetOHToMatchTA(data,data.getOfficeHours());
        ohws.removeOHToMatchTA(data, taTable.getItems(), data.getOfficeHours());
    }
}