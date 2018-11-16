/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.Lecture;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_RemoveLectures_Transaction implements jTPS_Transaction{
    AppData data;
    Lecture selectedLec;
    
    public MT_RemoveLectures_Transaction(AppData data, Lecture selectedLec){
        this.data = data;
        this.selectedLec= selectedLec;
    }
    @Override
    public void doTransaction() {
        data.removeLecture(selectedLec);
    }

    @Override
    public void undoTransaction() {
        data.addLecture(selectedLec);
    }
    
}
