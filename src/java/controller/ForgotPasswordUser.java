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
@WebServlet(name = "ForgotPasswordUser", urlPatterns = {"/ForgotPasswordUser"})
public class ForgotPasswordUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("success", false);
        JsonObject requestObject = gson.fromJson(req.getReader(),JsonObject.class);
        
        final String emailAddress = requestObject.get("email").getAsString();
        final int user = requestObject.get("spinnerUser").getAsInt();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        System.out.println(emailAddress);
        
        if(user == 0){
        
        try {
            transaction = session.beginTransaction();
            Criteria criteria1 = session.createCriteria(Customer.class);
            criteria1.add(Restrictions.eq("email", emailAddress));
            
            int code = (int) (Math.random()*1000000);
            
            if(!criteria1.list().isEmpty()){
                final Customer customerexist = (Customer) criteria1.uniqueResult();
                System.out.println(customerexist.getFname());
                if(customerexist != null){
                     Thread sendMailThread = new Thread() {
                        @Override
                        
                        public void run() {
                            Mail.sendMail(emailAddress, "GreenCycle Account Verification Code", customerexist.getVerification());
                        }
                    };
                    sendMailThread.start();
                    customerexist.setVerification(String.valueOf(code));
                    session.update(customerexist);
                    transaction.commit();
                    responseJson.addProperty("success", true);
                responseJson.addProperty("message", "success");
                }
            }else{
           responseJson.addProperty("message", "Invalid User!"); 
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        }else{
            try {
                transaction = session.beginTransaction();
            Criteria criteria2 = session.createCriteria(Woker.class);
            criteria2.add(Restrictions.eq("email", emailAddress));
            
             int code = (int) (Math.random()*1000000);
            
             if(!criteria2.list().isEmpty()){
                 final Woker wokerExsit = (Woker) criteria2.uniqueResult();
                 
                 Thread sendMailThread = new Thread() {
                        @Override
                        
                        public void run() {
                            Mail.sendMail(emailAddress, "GreenCycle Account Verification Code", wokerExsit.getVerification());
                        }
                    };
                    sendMailThread.start();
                 
                 
                 wokerExsit.setVerification(String.valueOf(code));
                 session.update(wokerExsit);
                 transaction.commit();
                 responseJson.addProperty("success", true);
                 responseJson.addProperty("message", "success");
             }else{
             responseJson.addProperty("message", "success");
             }
            } catch (Exception e) {
            }
        }
        
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseJson));
        
    }

    
   
}
