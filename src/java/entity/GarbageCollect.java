/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "garbagecollect")
public class GarbageCollect implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "polythenevalue",nullable = true)
    private Double polythenevalue;
    
    @Column(name = "polytheneunite",length = 5,nullable = true)
    private String polytheneunite;
    
    @Column(name = "organicvalue",nullable = true)
    private Double organicvalue;
    
    @Column(name = "organicunite",length = 5,nullable = true)
    private String organicunite;
    
    @Column(name = "othervalue",nullable = true)
    private Double othervalue;
    
    @Column(name = "otherunite",length = 5,nullable = true)
    private String otherunite;
    
    @Column(name = "collectingdate",nullable = true)
    private Date collectingdate;

    @Column(name = "withdrowstatus",nullable = false)
    private int withdrowstatus;
         
    @Column(name = "collectingprices",nullable = false)
    private Double collectingprices;

      @ManyToOne
     @JoinColumn(name = "customer_id")
     private Customer customer;
       
       @ManyToOne
     @JoinColumn(name = "wokers_id")
     private Woker woker;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the polythenevalue
     */
    public Double getPolythenevalue() {
        return polythenevalue;
    }

    /**
     * @param polythenevalue the polythenevalue to set
     */
    public void setPolythenevalue(Double polythenevalue) {
        this.polythenevalue = polythenevalue;
    }

    /**
     * @return the polytheneunite
     */
    public String getPolytheneunite() {
        return polytheneunite;
    }

    /**
     * @param polytheneunite the polytheneunite to set
     */
    public void setPolytheneunite(String polytheneunite) {
        this.polytheneunite = polytheneunite;
    }

    /**
     * @return the organicvalue
     */
    public Double getOrganicvalue() {
        return organicvalue;
    }

    /**
     * @param organicvalue the organicvalue to set
     */
    public void setOrganicvalue(Double organicvalue) {
        this.organicvalue = organicvalue;
    }

    /**
     * @return the organicunite
     */
    public String getOrganicunite() {
        return organicunite;
    }

    /**
     * @param organicunite the organicunite to set
     */
    public void setOrganicunite(String organicunite) {
        this.organicunite = organicunite;
    }

    /**
     * @return the othervalue
     */
    public Double getOthervalue() {
        return othervalue;
    }

    /**
     * @param othervalue the othervalue to set
     */
    public void setOthervalue(Double othervalue) {
        this.othervalue = othervalue;
    }

    /**
     * @return the otherunite
     */
    public String getOtherunite() {
        return otherunite;
    }

    /**
     * @param otherunite the otherunite to set
     */
    public void setOtherunite(String otherunite) {
        this.otherunite = otherunite;
    }

    /**
     * @return the collectingdate
     */
    public Date getCollectingdate() {
        return collectingdate;
    }

    /**
     * @param collectingdate the collectingdate to set
     */
    public void setCollectingdate(Date collectingdate) {
        this.collectingdate = collectingdate;
    }

    /**
     * @return the withdrowstatus
     */
    public int getWithdrowstatus() {
        return withdrowstatus;
    }

    /**
     * @param withdrowstatus the withdrowstatus to set
     */
    public void setWithdrowstatus(int withdrowstatus) {
        this.withdrowstatus = withdrowstatus;
    }

    /**
     * @return the collectingprices
     */
    public Double getCollectingprices() {
        return collectingprices;
    }

    /**
     * @param collectingprices the collectingprices to set
     */
    public void setCollectingprices(Double collectingprices) {
        this.collectingprices = collectingprices;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the woker
     */
    public Woker getWoker() {
        return woker;
    }

    /**
     * @param woker the woker to set
     */
    public void setWoker(Woker woker) {
        this.woker = woker;
    }
    
}
