/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.Recitation;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_RemoveRec_Transaction implements jTPS_Transaction{
    AppData data;
    Recitation selectedRec;
    
    public MT_RemoveRec_Transaction(AppData data, Recitation Rec){
        this.data = data;
        this.selectedRec= Rec;
    }

    @Override
    public void doTransaction() {
        data.removeRec(selectedRec);
    }

    @Override
    public void undoTransaction() {
        data.addRec(selectedRec);
    }
    
}
