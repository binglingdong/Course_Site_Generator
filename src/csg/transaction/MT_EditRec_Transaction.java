/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.Recitation;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_EditRec_Transaction implements jTPS_Transaction{

    String oldValue;
    String newValue;
    Recitation rec;
    String col;
    
    public MT_EditRec_Transaction(String oldValue, String newValue,Recitation lec, String col){
        this.oldValue= oldValue;
        this.newValue= newValue;
        this.rec = lec;
        this.col = col;
    }
    
    @Override
    public void doTransaction() {
        switch(col){
            case "section":rec.setSection(newValue);
                break;
            case "day":rec.setDayAndTime(newValue);
                break;
            case "room":rec.setRoom(newValue);
                break;
            case "ta1":rec.setTA1(newValue);
                break;
            case "ta2":rec.setTA2(newValue);
                break;
        }
    }

    @Override
    public void undoTransaction() {
        switch(col){
            case "section":rec.setSection(oldValue);
                break;
            case "day":rec.setDayAndTime(oldValue);
                break;
            case "room":rec.setRoom(oldValue);
                break;
            case "ta1":rec.setTA1(oldValue);
                break;
            case "ta2":rec.setTA2(oldValue);
                break;
        }
    }
    
}


