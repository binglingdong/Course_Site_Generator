/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.foolproof;


import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import djf.ui.foolproof.FoolproofDesign;
import java.util.regex.Pattern;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author McKillaGorilla
 */
public class EditingTAFoolproofDesign implements FoolproofDesign {
    AppData data;
    TextField nameTf;
    TextField emailTf; 
    Dialog dialog;
    TeachingAssistantPrototype selectedTA;
    ButtonType okButton;
    ToggleGroup tg;
    RadioButton under;
    RadioButton gra;
    
    public EditingTAFoolproofDesign(AppData data, TextField nameTf,TextField emailTf, 
                                    Dialog dialog, TeachingAssistantPrototype selectedTA, ButtonType okButton,
                                    ToggleGroup tg, RadioButton under, RadioButton gra) {
        this.data=data;
        this.nameTf= nameTf;
        this.emailTf= emailTf;
        this.dialog= dialog;
        this.selectedTA= selectedTA;
        this.okButton=okButton;
        this.tg= tg;
        this.under= under;
        this.gra= gra;
    }

    @Override
    public void updateControls() {
        enableIfInUse();
    }
    
    private void enableIfInUse() {
        String newName= nameTf.getText();
        String newEmail= emailTf.getText();
        boolean validName;
        boolean validEmail;
        RadioButton current= (RadioButton)tg.getSelectedToggle();
        String newType="";
        if(current.equals(under)){
            newType= "Undergraduate";
        }else newType= "Graduate";
            
            
        if(!newName.equals(selectedTA.getName())){
            validName= checkForRepeatedName(newName);
            if(!validName) nameTf.setStyle("-fx-text-fill: red;");
            else nameTf.setStyle("-fx-text-fill: black;");
        }
        else {//if the name didnt change. 
            nameTf.setStyle("-fx-text-fill: black;");
            validName= true;
        }                   
        
        
        if(!newEmail.equals(selectedTA.getEmail())){
            validEmail= checkForRepeatedAndValidEmail(newEmail);
            if(!validEmail) emailTf.setStyle("-fx-text-fill: red;");
            else  emailTf.setStyle("-fx-text-fill: black;"); 
        }
        else {
            emailTf.setStyle("-fx-text-fill: black;");
            validEmail= true;
        }
        
        if(newName.equals(selectedTA.getName())&& newEmail.equals(selectedTA.getEmail())){
            dialog.getDialogPane().lookupButton(okButton).setDisable(true);
            if(!newType.equals(selectedTA.getType())){
                dialog.getDialogPane().lookupButton(okButton).setDisable(false);
            }
        }
        else{
            if(!validName||!validEmail){
                dialog.getDialogPane().lookupButton(okButton).setDisable(true);
            }
            else dialog.getDialogPane().lookupButton(okButton).setDisable(false);
        }
    }
    
    public boolean checkForRepeatedName(String newName){
        if(newName.isEmpty())return false;
        for(TeachingAssistantPrototype ta:data.getTABackup()){
            if(ta.getName().equalsIgnoreCase(newName)) return false;
        }
        return true;
    }
    
    public boolean checkForRepeatedAndValidEmail(String newEmail){
        if(newEmail.isEmpty())return false;
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(newEmail).matches()){
            return false;
        }
        for(TeachingAssistantPrototype ta:data.getTABackup()){
            if(ta.getEmail().equalsIgnoreCase(newEmail)) return false;
        }
        
        return true;
    }
}
