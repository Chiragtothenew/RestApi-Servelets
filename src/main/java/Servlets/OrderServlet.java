package Servlets;

import Order.Order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private List<Order> orderList = new ArrayList<Order>();

    private int counter;

    @Override
    public void init() {
        System.out.println("Inside the Init Method");
        counter = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        if (name != null && quantity != 0){
            String status = addOrder(quantity, name, resp);
            PrintWriter out = resp.getWriter();
            out.println(status);
            out.close();
        } else{
            resp.setContentType("text/plain");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("Please try again later!");
            out.close();
        }
    }

    private String addOrder(int quantity, String name, HttpServletResponse resp){

        this.orderList.add(new Order(name, quantity));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        return "Order successfully added.";
    }

    private String deleteOrder(int id){

        for(Order orders : orderList)
        {
            int i=0;
            if(orders.getId()==id)
            {
                orderList.remove(i);
                i++;

                return "Order with Id : "+id+"Deleted";

            }
        }

        return "Order with id : "+id+" not found";

    }

    private void listAllOrders(HttpServletResponse resp)
    {
        if(orderList.size()>=0)
        {
            for(Order order : orderList)
            {
                System.out.println(order);
            }
        }

        else
        {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("Order List is Empty");
        }


    }

    private Order getOrderById(int id,HttpServletResponse resp)
    {
        for(Order orders : orderList)
        {
            if(orders.getId()==id)
            {
                return orders;
            }
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        System.out.println("Order Not Found!");
        return null;
    }

}
