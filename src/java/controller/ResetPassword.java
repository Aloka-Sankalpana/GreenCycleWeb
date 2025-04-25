/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Customer;
import entity.Woker;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Mail;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aloka
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/ResetPassword"})
public class ResetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("success", false);
        JsonObject requestJson = gson.fromJson(req.getReader(), JsonObject.class);
        
        String emailAddress = requestJson.get("email").getAsString();
        int user = requestJson.get("spinnerUser").getAsInt();
       
        String vpcode = requestJson.get("vpcode").getAsString();
        String password = requestJson.get("password").getAsString();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        if(user == 0){
        
        try {
            transaction = session.beginTransaction();
            Criteria criteria1 = session.createCriteria(Customer.class);
            criteria1.add(Restrictions.eq("email", emailAddress));
            
            
            
            if(!criteria1.list().isEmpty()){
                final Customer customerexist = (Customer) criteria1.uniqueResult();
                System.out.println(customerexist.getFname());
                if(customerexist != null){
                    
                    if(!customerexist.getVerification().equals(vpcode)){
                    responseObject.addProperty("message", "Invalid verification code!"); 
                    }else{
                    customerexist.setPassword(password);
                    session.update(customerexist);
                    transaction.commit();
                   responseObject.addProperty("success", true);
                   responseObject.addProperty("message", "success");
                   responseObject.addProperty("usertype", "customer");
                    }
//                    cu
//                   
//                    
                }
            }else{
           responseObject.addProperty("message", "Invalid User!"); 
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }else{
         try {
            transaction = session.beginTransaction();
            Criteria criteria2 = session.createCriteria(Woker.class);
            criteria2.add(Restrictions.eq("email", emailAddress));
            
            
            
            if(!criteria2.list().isEmpty()){
                final Woker wokerexist = (Woker) criteria2.uniqueResult();
                System.out.println(wokerexist.getFname());
                if(wokerexist != null){
                    
                    if(!wokerexist.getVerification().equals(vpcode)){
                    responseObject.addProperty("message", "Invalid verification code!"); 
                    }else{
                    wokerexist.setPassword(password);
                    session.update(wokerexist);
                    transaction.commit();
                    
                    if(user == 1){
                    responseObject.addProperty("usertype", "gco");
                    }else{
                    responseObject.addProperty("usertype", "officer");
                    }
                    
                   responseObject.addProperty("success", true);
                   responseObject.addProperty("message", "success");
                    }
//                    cu
//                   
//                    
                }
            }else{
           responseObject.addProperty("message", "Invalid User!"); 
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseObject));
    }

}
