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
public class MT_AddLectures_Transaction implements jTPS_Transaction{
    AppData data;
    Lecture newLec;
    
    public MT_AddLectures_Transaction(AppData data, Lecture newLec){
        this.data = data;
        this.newLec= newLec;
    }
    
    @Override
    public void doTransaction() {
        data.addLecture(newLec);
    }

    @Override
    public void undoTransaction() {
        data.removeLecture(newLec);
    }
    
}
