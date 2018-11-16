/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.Lab;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_RemoveLab_Transaction implements jTPS_Transaction {
    AppData data;
    Lab selectedLab;
    
    public MT_RemoveLab_Transaction(AppData data, Lab selectedLab){
        this.data = data;
        this.selectedLab= selectedLab;
    }
    
    @Override
    public void doTransaction() {
        data.removeLab(selectedLab);
    }

    @Override
    public void undoTransaction() {
        data.addLab(selectedLab);
    }
}
