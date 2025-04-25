/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.GarbageCollect;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/TotalGarbagePrice"})
public class TotalGarbagePrice extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     
   Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("success", false);

        try {
            JsonObject requestObject = gson.fromJson(req.getReader(), JsonObject.class);
            int cid = requestObject.get("cid").getAsInt();
            System.out.println("Received Customer ID: " + cid);

            Session session = HibernateUtil.getSessionFactory().openSession();
            Double totalPrice = 0.0; // Default value if no records are found

            try {
                // Query to sum collectingPrices where withdrowstatus = 1 for the given customer ID
                Double sum = (Double) session.createQuery(
                        "SELECT SUM(g.collectingprices) FROM GarbageCollect g WHERE g.customer.id = :cid AND g.withdrowstatus = 1"
                ).setParameter("cid", cid).uniqueResult();

                if (sum != null) {
                    totalPrice = sum;
                }

                responseJson.addProperty("success", true);
                responseJson.addProperty("totalPrice", totalPrice);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close(); // Always close the session
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseJson));

        
    }

}
