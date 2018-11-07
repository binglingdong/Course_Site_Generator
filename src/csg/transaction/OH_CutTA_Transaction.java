/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.workspace.OfficeHours;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_CutTA_Transaction implements jTPS_Transaction{
    OfficeHours ohws;
    TeachingAssistantPrototype selectedTA;
    AppData data;
    
    public OH_CutTA_Transaction(OfficeHours ohws, TeachingAssistantPrototype selectedTA, AppData data){
        this.ohws=ohws;
        this.selectedTA= selectedTA;
        this.data=data;
    }

    @Override
    public void doTransaction() {
        //Since when you remove a TA from the backup copy TA list, you are not removing the actual oh from the backup oh
        // list, you are just simply updating the list that's showing to the user, you can just remove the ta and add it back
        // anytime you want. Their oh will always be in the backup oh sheet. 
        data.getTABackup().remove(selectedTA);   
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }

    @Override
    public void undoTransaction() {
        data.getTABackup().add(selectedTA);      //add the ta back to the copy list. 
        ohws.updateTaTableForRadio(data.getTeachingAssistants()); // and update it
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }
    
}
