/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Customer;
import entity.GarbageCollect;
import entity.Woker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aloka
 */
@WebServlet(name = "GarbageCollecting", urlPatterns = {"/GarbageCollecting"})
public class GarbageCollecting extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("success", false);
        JsonObject requestjson = gson.fromJson(req.getReader(), JsonObject.class);
        
        String customermobile = requestjson.get("customer").getAsString();
        String gco = requestjson.get("gco").getAsString();
        String polyQty = requestjson.get("PolytinQTY").getAsString();
        String polyUnit = requestjson.get("polytinunit").getAsString();
        
        String orgQty = requestjson.get("organicQTY").getAsString();
        String orgUnit = requestjson.get("Orginicunit").getAsString();
        
        String othQty = requestjson.get("otherQTY").getAsString();
        String othUnit = requestjson.get("OtherUnit").getAsString();
        
        

        
        
        System.out.println(customermobile);
        System.out.println(gco);
        
        System.out.println(polyQty);
        System.out.println(polyUnit);
        System.out.println(orgQty);
        System.out.println(orgUnit);
        System.out.println(othQty);
        
        System.out.println(othUnit);

        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
            try {
            
            Criteria criteria1 = session.createCriteria(Customer.class);
            criteria1.add(Restrictions.eq("mobile", customermobile));
            
            if(criteria1.list().isEmpty()){
             responseJson.addProperty("message", "Invalid Cuctomer!"); 
            }else{
            
                
                
                Criteria criteria2 = session.createCriteria(Woker.class);
                criteria2.add(Restrictions.eq("email", gco));
                
                if(criteria2.list().isEmpty()){
             responseJson.addProperty("message", "Invalid GCO!"); 
            }else{
            
                  int polithenePricePerGram10 = 2;                     
                  int OrganicPricePerGram10 = 1; 
                  int otherPricePerGram10 = 1; 
                    
                                  
                  
                  
                 
                  
                         
                  
                Customer existCustomer = (Customer)criteria1.uniqueResult();
                    Woker existWoker = (Woker)criteria2.uniqueResult();
                
                int customerid = existCustomer.getId();
                int wokerid = existWoker.getId();
                
                    System.out.println(customerid);
                                        System.out.println(wokerid);
                                        
                
                  





double politheneprice = 0.0;
double OrganicPrice = 0.0;
double otherPrice = 0.0;

// Debugging prints to check values
System.out.println("polyQty: " + polyQty + ", polyUnit: " + polyUnit);
System.out.println("orgQty: " + orgQty + ", orgUnit: " + orgUnit);
System.out.println("othQty: " + othQty + ", othUnit: " + othUnit);

if (polyUnit != null && polyQty != null) {
    if (polyUnit.equals("Kg")) {                   
        politheneprice = (Double.parseDouble(polyQty) * 1000) / 10 * polithenePricePerGram10;
    } else {
        politheneprice = Double.parseDouble(polyQty) / 10 * polithenePricePerGram10;
    }
}

if (orgUnit != null && orgQty != null) {
    if (orgUnit.equals("Kg")) {
        OrganicPrice = Double.parseDouble(orgQty) * 1000 / 10 * OrganicPricePerGram10;
    } else {
        OrganicPrice = Double.parseDouble(orgQty) / 10 * OrganicPricePerGram10;
    }
}

if (othUnit != null && othQty != null) {
    if (othUnit.equals("Kg")) {
        otherPrice = Double.parseDouble(othQty) * 1000 / 10 * otherPricePerGram10;
    } else {
        otherPrice = Double.parseDouble(othQty) / 10 * otherPricePerGram10;
    }
}

// Final Debugging Before Total Calculation
System.out.println("Polithene Price: " + politheneprice);
System.out.println("Organic Price: " + OrganicPrice);
System.out.println("Other Price: " + otherPrice);

double total = politheneprice + OrganicPrice + otherPrice;
System.out.println("Total Price: " + total);

Customer customer = (Customer) session.load(Customer.class, customerid);
Woker woker = (Woker)session.load(Woker.class, wokerid);


                    GarbageCollect garbageCollect = new GarbageCollect();
                    garbageCollect.setPolythenevalue(Double.parseDouble(polyQty));
                    garbageCollect.setPolytheneunite(polyUnit);
                    garbageCollect.setOrganicvalue(Double.parseDouble(orgQty));
                    garbageCollect.setOrganicunite(orgUnit);
                    garbageCollect.setOthervalue(Double.parseDouble(othQty));
                    garbageCollect.setOtherunite(othUnit);
                    garbageCollect.setCustomer(customer);
                    garbageCollect.setWoker(woker);
                    garbageCollect.setCollectingdate(new Date());
                    garbageCollect.setWithdrowstatus(1);
                    garbageCollect.setCollectingprices(total);


session.save(garbageCollect);
            session.beginTransaction().commit();


           responseJson.addProperty("success", true);
           responseJson.addProperty("message", "success"); 
         



                 
            } 
            
            }
        } catch (Exception e) {
        }
        
        
        
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseJson));
        
    }

    

}
