
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Customer;
import entity.Woker;
import java.io.IOException;
import java.io.PrintWriter;
import org.hibernate.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author aloka
 */
@WebServlet(name = "CustomerRegistration", urlPatterns = {"/CustomerRegistration"})
public class CustomerRegistration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
         Gson gson = new Gson();
    JsonObject responseObject = new JsonObject();
    responseObject.addProperty("success", false);
    JsonObject requestObject = gson.fromJson(req.getReader(), JsonObject.class);
    
    String fname = requestObject.get("fname").getAsString();
    String lname = requestObject.get("lname").getAsString();
    String mobile = requestObject.get("mobile").getAsString();
    String email = requestObject.get("email").getAsString();
    String password = requestObject.get("password").getAsString();
    String gco = requestObject.get("gco").getAsString();
    
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria1 = session.createCriteria(Customer.class);
            criteria1.add(Restrictions.eq("mobile", mobile));
            
            if(!criteria1.list().isEmpty()){
             responseObject.addProperty("message", "User Already have an Exist"); 
            }else{
            Customer customer = new Customer();
            customer.setFname(fname);
            customer.setLname(lname);
            customer.setMobile(mobile);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setStatus(1);
            customer.setVerification("1010");
            
            Woker woker = (Woker)session.load(Woker.class, Integer.parseInt(gco));
            
           customer.setWoker(woker);
            
            
            session.save(customer);
            session.beginTransaction().commit();
         // req.getSession().setAttribute("mobile", mobile);
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestObject.addProperty("success", true);
        requestObject.addProperty("message", "success");
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseObject));
        
    }

}
