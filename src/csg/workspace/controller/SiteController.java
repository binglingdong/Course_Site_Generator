/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.INVALID_IMAGE_TITLE;
import static csg.SitePropertyType.IO_IMAGE_EXCEPTION_CONTENT;
import csg.data.LocateImages;
import csg.transaction.Site_EditImageView_Transaction;
import csg.transaction.Site_changeComboBox_Transaction;
import djf.ui.dialogs.AppDialogsFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    
    public void comboBoxChanged(Object oldValue, Object newValue, ComboBox cb){
        Site_changeComboBox_Transaction tran = new Site_changeComboBox_Transaction(oldValue, newValue, cb, app);
        app.processTransaction(tran);
    }
    
    public void editableComboBox(Object oldValue, Object newValue, ComboBox cb){
        
    }
           
}
