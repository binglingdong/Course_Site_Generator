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
public class MT_AddLab_Transaction implements jTPS_Transaction{
    AppData data;
    Lab newLab;
    
    public MT_AddLab_Transaction(AppData data, Lab newLab){
        this.data = data;
        this.newLab = newLab;
    }
    
    @Override
    public void doTransaction() {
        data.addLab(newLab);
    }

    @Override
    public void undoTransaction() {
        data.removeLab(newLab);
    }
    
}
