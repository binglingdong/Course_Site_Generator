/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import java.util.EventListener;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Site_changeComboBox_Transaction implements jTPS_Transaction{
    Object oldValue;
    Object newValue;
    ComboBox cb;
    EventListener e;

    public Site_changeComboBox_Transaction(Object oldValue, Object newValue, ComboBox cb){
        this.oldValue= oldValue;
        this.newValue= newValue;
        this.cb = cb;
        this.e = e;
    }
    @Override
    public void doTransaction() {
        if(newValue==null){
            cb.getSelectionModel().clearSelection();
        }
        else{
            cb.getSelectionModel().select(newValue);}
    }

    @Override
    public void undoTransaction() {
        if(oldValue ==null){
            cb.getSelectionModel().clearSelection();
        }else{
            cb.getSelectionModel().select(oldValue);
        }
    }
}
