/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Site_EditImageView_Transaction implements jTPS_Transaction{
    private ImageView imv;
    private Image newImage;
    private Image oldImage;
    
    public Site_EditImageView_Transaction(Image newImage, ImageView imView){
        imv= imView;
        this.newImage= newImage;
        oldImage= imView.getImage();
    }
    
    @Override
    public void doTransaction() {
        imv.setImage(newImage);
    }

    @Override
    public void undoTransaction() {
        imv.setImage(oldImage);
    }
}
