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
public class Room {
    private String ROOMID;
    private String RoomType;
    private Boolean RoomStatus;
    private Boolean Active;
    private Integer RoomPrice;

    public Room() {
    }

    public Room(String ROOMID, String RoomType, Boolean RoomStatus, Boolean Active, Integer RoomPrice) {
        this.ROOMID = ROOMID;
        this.RoomType = RoomType;
        this.RoomStatus = RoomStatus;
        this.Active = Active;
        this.RoomPrice = RoomPrice;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String RoomType) {
        this.RoomType = RoomType;
    }

    public Boolean getRoomStatus() {
        return RoomStatus;
    }

    public void setRoomStatus(Boolean RoomStatus) {
        this.RoomStatus = RoomStatus;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean Active) {
        this.Active = Active;
    }

    public Integer getRoomPrice() {
        return RoomPrice;
    }

    public void setRoomPrice(Integer RoomPrice) {
        this.RoomPrice = RoomPrice;
    }
    
    
}
