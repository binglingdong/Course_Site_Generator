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
public class OH_PasteTA_Cut_Transaction implements jTPS_Transaction{
    TeachingAssistantPrototype selectedTA;
    OfficeHours ohws;
    AppData data;
    
    public OH_PasteTA_Cut_Transaction(TeachingAssistantPrototype selectedTA, OfficeHours ohws, AppData data){
        this.selectedTA= selectedTA;
        this.ohws= ohws;
        this.data= data;
    }
    
    @Override
    public void doTransaction() {
        data.getTABackup().add(selectedTA);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }

    @Override
    public void undoTransaction() {
        data.getTABackup().remove(selectedTA);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
        ohws.resetOHToMatchTA(data, data.getOfficeHours());
        ohws.removeOHToMatchTA(data, data.getTeachingAssistants(), data.getOfficeHours());
        ohws.updateBgColorForCell();
    }
}