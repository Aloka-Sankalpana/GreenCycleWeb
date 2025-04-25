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
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author aloka
 */
@WebServlet(name = "CustomerProfile", urlPatterns = {"/CustomerProfile"})
public class CustomerProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
    JsonObject responseObject = new JsonObject();
    responseObject.addProperty("success", false);

    try {
        JsonObject requestObject = gson.fromJson(req.getReader(), JsonObject.class);
        String mobile = requestObject.get("mobile").getAsString();

        System.out.println("Received Mobile: " + mobile);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction(); // Start transaction

        try {
            Criteria criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("mobile", mobile));

            Customer customerExist = (Customer) criteria.uniqueResult(); // Fetch unique result

            if (customerExist != null) {
                responseObject.addProperty("success", true);
                responseObject.addProperty("cid", customerExist.getId());
                responseObject.addProperty("fname", customerExist.getFname());
                responseObject.addProperty("lname", customerExist.getLname());
                responseObject.addProperty("mobile", customerExist.getMobile());
                responseObject.addProperty("email", customerExist.getEmail());
                
                if (customerExist.getWoker() != null) {
                    Criteria criteria1 = session.createCriteria(Woker.class);
                    criteria1.add(Restrictions.eq("id", customerExist.getWoker().getId())); // Assuming getWoker() returns a Woker object
                    
                    Woker relatedWoker = (Woker) criteria1.uniqueResult(); // Correct usage

                    if (relatedWoker != null) {
                        responseObject.addProperty("woker_email", relatedWoker.getEmail());
                       
                    }
                }
                
            }

            transaction.commit(); // Commit transaction
        } catch (Exception e) {
            transaction.rollback(); // Rollback on error
            e.printStackTrace();
        } finally {
            session.close(); // Always close session
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    resp.setContentType("application/json");
    resp.getWriter().write(gson.toJson(responseObject));
    }
  
}
