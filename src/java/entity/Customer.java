/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "fname",length = 55,nullable = false)
    private String fname;
    
     @Column(name = "lname",length = 55,nullable = false)
    private String lname;
     
      @Column(name = "mobile",length = 10,nullable = false)
    private String mobile;
      
       @Column(name = "email",length = 85,nullable = false)
    private String email;
       
     @Column(name = "password",length = 20,nullable = false)
    private String password;
     
      @Column(name = "status",nullable = false)
    private int status;
      
       @Column(name = "verification",length = 45,nullable = true)
    private String verification;
//verification
       
       @ManyToOne
      @JoinColumn(name = "wokers_id")
      private Woker Woker;
       
     public Customer(){}

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
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the verification
     */
    public String getVerification() {
        return verification;
    }

    /**
     * @param verification the verification to set
     */
    public void setVerification(String verification) {
        this.verification = verification;
    }

    /**
     * @return the Woker
     */
    public Woker getWoker() {
        return Woker;
    }

    /**
     * @param Woker the Woker to set
     */
    public void setWoker(Woker Woker) {
        this.Woker = Woker;
    }
       
   
    
    

   


   

  
}
