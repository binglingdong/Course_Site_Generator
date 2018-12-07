/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.INVALID_IMAGE_TITLE;
import static csg.SitePropertyType.IO_IMAGE_EXCEPTION_CONTENT;
import csg.data.AppData;
import csg.data.LocateImages;
import csg.transaction.Site_EditableComboBox_Transaction;
import csg.transaction.Site_EditCheckbox_Transaction;
import csg.transaction.Site_EditImageView_Transaction;
import csg.transaction.All_EditTextArea_Transaction;
import csg.transaction.Site_EditTextField_Transaction;
import csg.transaction.Site_NormalChangeComboBox_Transaction;
import csg.transaction.Site_SpecialChangeComboBox_Transaction;
import djf.ui.dialogs.AppDialogsFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author bingling.dong
 */
public class SiteController {
    CourseSiteGeneratorApp app;
    public SiteController(CourseSiteGeneratorApp App){
        app= App;
    }
    
    public void styleButtonsClicked(Object imageViewID){
        LocateImages iv= (LocateImages)app.getGUIModule().getGUINode(imageViewID);
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = 
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = 
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = 
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = 
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        try {
            if(file!=null){
                String path = file.getPath();
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                Site_EditImageView_Transaction editSiteImageView_Transaction = new Site_EditImageView_Transaction(image,iv.getImageView());
                app.processTransaction(editSiteImageView_Transaction);
                iv.setURL(path);
            }
        } catch (IOException ex) {
            AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(),INVALID_IMAGE_TITLE, IO_IMAGE_EXCEPTION_CONTENT);
        }
    }
    
    public void specialComboBoxChanged(Object oldValue, Object newValue, ComboBox cb){
        Site_SpecialChangeComboBox_Transaction tran = new Site_SpecialChangeComboBox_Transaction(oldValue, newValue, cb, app);
        app.processTransaction(tran);
    }
    
    public void normalComboBoxChanged(Object oldValue, Object newValue, ComboBox cb){
        Site_NormalChangeComboBox_Transaction tran = new Site_NormalChangeComboBox_Transaction(oldValue, newValue, cb);
        app.processTransaction(tran);
    }
    
    public void editableComboBox(Object oldValue, Object newValue, ComboBox cb){
        Site_EditableComboBox_Transaction tran = new Site_EditableComboBox_Transaction(oldValue, newValue, cb, app);
        app.processTransaction(tran);
    }
    
    public void checkBoxChanged(boolean oldValue, boolean newValue, CheckBox cb){
        Site_EditCheckbox_Transaction tran = new Site_EditCheckbox_Transaction(oldValue, newValue, cb);
        app.processTransaction(tran);
    }
    
    public void textFieldChanged(String oldValue, String newValue, TextField tf){
        if(app.getMostRecentTransaction()!=null){
            if(app.getMostRecentTransaction() instanceof Site_EditTextField_Transaction){
                Site_EditTextField_Transaction oldTran = (Site_EditTextField_Transaction)app.getMostRecentTransaction();
                if(oldTran.getTF()==tf){
                    oldValue = oldTran.getOldValue();
                    app.moveBackPointer();
                }
            }
        }
        Site_EditTextField_Transaction tran = new Site_EditTextField_Transaction(oldValue, newValue,tf);
        app.processTransaction(tran);
    }
    
    public void textAreaChanged(String oldValue, String newValue, TextArea ta){
        if(app.getMostRecentTransaction()!=null){
            if(app.getMostRecentTransaction() instanceof All_EditTextArea_Transaction){
                All_EditTextArea_Transaction oldTran = (All_EditTextArea_Transaction)app.getMostRecentTransaction();
                oldValue = oldTran.getOldValue();
                app.moveBackPointer();
            }
        }
        All_EditTextArea_Transaction tran = new All_EditTextArea_Transaction(oldValue, newValue,ta);
        app.processTransaction(tran);
    }
}
