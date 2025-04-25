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
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aloka
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Gson gson = new Gson();
    JsonObject responseObject = new JsonObject();
    responseObject.addProperty("success", false);
    JsonObject requestObject = gson.fromJson(req.getReader(), JsonObject.class);
    
    String username = requestObject.get("username").getAsString();
    String password = requestObject.get("password").getAsString();
    String userType = requestObject.get("userType").getAsString();
    
    if(userType.equals("customer")){
      
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(Customer.class);
             criteria1.add(Restrictions.eq("mobile", username));
              criteria1.add(Restrictions.eq("password", password));
              criteria1.add(Restrictions.eq("status", 1));
              
              if(!criteria1.list().isEmpty()){
               responseObject.addProperty("message", "success");
               
              }else{
              responseObject.addProperty("message", "Invalid username or password");
              }
        } catch (Exception e) {
        }
        
    }else{
    
       try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria2 = session.createCriteria(Woker.class);
             criteria2.add(Restrictions.eq("email", username));
              criteria2.add(Restrictions.eq("password", password));
//              criteria2.add(Restrictions.eq("status", "1"));
              
              if(!criteria2.list().isEmpty()){
               responseObject.addProperty("message", "success");
               
              }else{
              responseObject.addProperty("message", "Invalid username or password");
              }
        } catch (Exception e) {
        }
        
        
    }
      resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseObject));
    }
   
}
