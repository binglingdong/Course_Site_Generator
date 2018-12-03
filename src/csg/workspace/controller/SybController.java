/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import csg.transaction.All_EditTextArea_Transaction;
import javafx.scene.control.TextArea;

/**
 *
 * @author bingling.dong
 */
public class SybController {
    CourseSiteGeneratorApp app;
    public SybController(CourseSiteGeneratorApp App){
        this.app= App;
    }
    
    public void textAreaChanged(String oldValue, String newValue, TextArea ta){
        if(app.getMostRecentTransaction()!=null){
            if(app.getMostRecentTransaction() instanceof All_EditTextArea_Transaction){
                All_EditTextArea_Transaction oldTran = (All_EditTextArea_Transaction)app.getMostRecentTransaction();
                if(oldTran.getTA()==ta){
                    oldValue = oldTran.getOldValue();
                    app.moveBackPointer();
                }
            }
        }
        All_EditTextArea_Transaction tran = new All_EditTextArea_Transaction(oldValue, newValue,ta);
        app.processTransaction(tran);
    }
}
