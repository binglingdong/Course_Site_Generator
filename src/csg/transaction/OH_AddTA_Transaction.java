/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.workspace.OfficeHours;
import java.util.ArrayList;
import jtps.jTPS_Transaction;

/**
 *
 * @author McKillaGorilla
 */
public class OH_AddTA_Transaction implements jTPS_Transaction {
    AppData data;
    TeachingAssistantPrototype ta;
    ArrayList<TeachingAssistantPrototype> copyTAs;
    OfficeHours ohws;
    
    public OH_AddTA_Transaction(AppData initData, TeachingAssistantPrototype initTA, ArrayList<TeachingAssistantPrototype> copyTAs,
                            OfficeHours ws) {
        data = initData;
        ta = initTA;
        this.copyTAs= copyTAs;
        ohws= ws;
    }

    @Override
    public void doTransaction() {
        data.addTA(ta);
        if (!copyTAs.contains(ta))copyTAs.add(ta);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
    }

    @Override
    public void undoTransaction() {
        data.removeTA(ta);
        copyTAs.remove(ta);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());
    }
}
