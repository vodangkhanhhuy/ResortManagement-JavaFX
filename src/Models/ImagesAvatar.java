/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author TechCare
 */
public class ImagesAvatar {
    private int ID;
    private String EMPID;
    private String imageLink;

    public ImagesAvatar() {
    }

    public ImagesAvatar(int ID, String EMPID, String imageLink) {
        this.ID = ID;
        this.EMPID = EMPID;
        this.imageLink = imageLink;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEMPID() {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    
    
}
