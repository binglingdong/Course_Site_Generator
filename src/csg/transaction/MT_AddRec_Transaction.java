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
public class MT_AddRec_Transaction implements jTPS_Transaction{
    AppData data;
    Recitation newRec;
    
    public MT_AddRec_Transaction(AppData data, Recitation newRec){
        this.data = data;
        this.newRec= newRec;
    }
    
    @Override
    public void doTransaction() {
        data.addRec(newRec);
    }

    @Override
    public void undoTransaction() {
        data.removeRec(newRec);
    }
    
}
