/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;
import session.AddlogManager;
import cart.ShoppingCart;
import enterprise.jsf_jpa_war.Wuser;
import entity.Category;
import entity.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CategoryFacade;
import session.CommentsFacade;
import session.ProductFacade;
import session.OrderManager;
import session.productManager;
import org.owasp.validator.html.*;
import java.io.IOException;
import java.util.Arrays;
import session.CartManager;

/**
 *  
 * @author Jonathan, nidhu , cathal
 */
// Url pattrns that this controller handles
@WebServlet(name = "Controller", loadOnStartup = 1, urlPatterns = {"/category", "/addToCart", "/viewCart", "/updateCart", "/checkout", "/addProduct", "/admin", "/removeProduct",
    "/increaseProduct", "/decreaseProduct", "/addComment", "/cancel", "/final"})

public class ControllerServlet extends HttpServlet {

    // enterprise Java beans declaration
    // catagory
    @EJB
    private CategoryFacade categoryFacade;
    //product
    @EJB
    private ProductFacade productFacade;
    // session bean for managing orders
    @EJB
    private OrderManager orderManager;
    // session bean for managing products in DB
    @EJB
    private productManager pManager;
    // comments manager
    @EJB
    private CommentsFacade com;
    // log manager
    @EJB
    private AddlogManager aManager;
    // cart statefull manager
    @EJB
    private CartManager cartManager;
    
    /**
     * generates the catagories list used by the main JSP pages generates the
     * comments list by find them all ( both are stored in the servlet context)
     *
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        // store the category list in the servlet's context
        getServletContext().setAttribute("categories", categoryFacade.findAll());
        getServletContext().setAttribute("comments", com.findAll());
    }

    /**
     * Handles the HTTP GET method.
     *
     * Handles are redirects based on URL patterns
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the userPath supplied ( one if the handles URL patterns) for redirection
        String userPath = request.getServletPath();
        // get the servlet session
        HttpSession session = request.getSession();
        Category selectedCategory;
        Collection<Product> categoryProducts;
        // if the category page is requested
        
        // stop unautorised access to admin pages and funtions
        Wuser u = (Wuser) session.getAttribute("user");
        if (u.getRole() == false && userPath.equals("/admin")) 
        {
            userPath = "/pManageIssue";
        }      
               
        //Switch through the URL patterns       
        switch (userPath) {
            case "/category":
                // gets the categorys Id from the  request
                String categoryId = request.getQueryString();
                if (categoryId != null) {
                    // get the  selected category
                    selectedCategory = categoryFacade.find(Short.parseShort(categoryId));
                    // place the selected category in the  session
                    session.setAttribute("selectedCategory", selectedCategory);
                    // then get all products for the  selected catagory
                    categoryProducts = selectedCategory.getProductCollection();
                    //  products in session 
                    session.setAttribute("categoryProducts", categoryProducts);

                    // create a hashmap, and for each product, retieve the comments 
                    // and map them to the id of the product in the map
                    Map comms = new HashMap();
                    for (Product p : categoryProducts) {
                        System.out.println(p.getId());
                        System.out.println(com.getComments(p.getId()));
                        List<String> c = com.getComments(p.getId());
                        comms.put(p.getId(), c);
                    }
                    // add the mapped comments to the session scope
                    session.setAttribute("commentsProducts", comms);

                }
                // if cart page is requested
                break;
            case "/viewCart":
                String clear = request.getParameter("clear");
                // clear the cart if it is requested
                if ((clear != null) && clear.equals("true")){
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                boolean result = cartManager.clearCart(clear, cart);
                
                }   
                // chagne the path to cart
                userPath = "/cart";
                break;
            // if checkout  is requested
            case "/checkout":
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                // calculate total
                cartManager.calcTotal(cart);
                break;

            // place the order and remove the quantity of items from the database
            case "/final":
                ShoppingCart cart2 = (ShoppingCart) session.getAttribute("cart");
                boolean result = orderManager.placeOrder(cart2);
                //cart2.clear();
                cartManager.clearCart("true", cart2);
                // if there was a stock problem redirect to that page
                if (result == false) {
                    userPath = "/stockError";
                }
                System.out.println("ORDER =" + result);
                break;
            // if cancel then just clear the cart, no rollback of a commited order used
            case "/cancel":
                ShoppingCart cart3 = (ShoppingCart) session.getAttribute("cart");
                cartManager.clearCart("true", cart3);                // prepair details for the Cancel LOG in the DB
                Category c = new Category();
                c.setId((short) 0);
                BigDecimal d = new BigDecimal(0);
                // add log event
                aManager.addProductLog(0, c, "CANCELED ORDER -->>>>>", "CANCEL", d);
                // redirect to confirmation
                userPath = "/confirmation";
                break;
           // admin page
            case "/admin": {
                userPath = "/admin";
            }
            break;
        }

        // use RequestDispatcher to forward request internally to the orginal path or modifed path
        String url = "/WEB-INF/view" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException ex) {
        }
    }

    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * 
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the called URL pattern
        String userPath = request.getServletPath();
        // get the session
        HttpSession session = request.getSession();
        
        // create an AntiSamy Policey and object
        org.owasp.validator.html.Policy policy = null;
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        try {
            policy = org.owasp.validator.html.Policy.getInstance("c:\\AntiSamy\\antisamy-slashdot-1.4.4.xml");
        } catch (PolicyException ex) {
            System.out.println("exception in policey loader");
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        AntiSamy as = new AntiSamy();
        
        
        //A7-Missing Function Level Access Control
        // stop unauthorised access to amdin functions and pages
        Wuser u = (Wuser) session.getAttribute("user");
        if (u.getRole() == false && userPath.equals("/removeProduct")) //|| userPath.equals("/admin")) {
        {
            userPath = "/pManageIssue";
        }
        if (u.getRole() == false && userPath.equals("/increaseProduct")) {
            userPath = "/pManageIssue";
        }
        if (u.getRole() == false && userPath.equals("/decreaseProduct")) {
            userPath = "/pManageIssue";
        }
        if (u.getRole() == false && userPath.equals("/admin")) {
            userPath = "/pManageIssue";
        }
        if (u.getRole() == false && userPath.equals("/searchingDelete")) {
            userPath = "/pManageIssue";
        }


        System.out.println("value is " + userPath);
        // if comment action is called
        switch (userPath) {
            case "/addComment": {
                // get the product id and unchecked comment string
                int commentID = Integer.parseInt(request.getParameter("productId"));
                String commentForProduct = request.getParameter("comment");
                // Object store results from AntiSami
                CleanResults cr = null;
                try {
                    // scan comment and clean
                    cr = as.scan(commentForProduct, policy);
                    System.out.println(cr.getCleanHTML());
                } catch (PolicyException pe) {
                    System.out.println(pe.getMessage());
                } catch (ScanException se) {
                    System.out.println(se.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                // get cleaned comment
                String clean = cr.getCleanHTML();
                System.out.println("you are in the commend command");
                // add the cleaned comment to with id as foriegn key
                boolean v = com.addComment(clean, commentID);
                userPath = "/commentadded";
                break;
            }

            
            case "/addToCart": {
                // if user is adding item to cart for first time
                // create cart object and attach it to user session
                if (cart == null) {
                    cart = new ShoppingCart();
                    session.setAttribute("cart", cart);
                }       // get user input from request
                String productId = request.getParameter("productId");
                if (!productId.isEmpty()) {
                    // get and add the selected prodcut
                    Product product = productFacade.find(Integer.parseInt(productId));
                    cartManager.addtoCart(cart, product);                }
                userPath = "/category";
                break;
            }
            
            
            case "/updateCart": {
                // get input from request
                String productId = request.getParameter("productId");
                String quantity = request.getParameter("quantity");
                // get seleced producr
                Product product = productFacade.find(Integer.parseInt(productId));
                // update the quantity
                cartManager.updateCart(cart, product, quantity);               
                userPath = "/cart";
                break;
            }

            // add a new prodcut - get the detials from session
            case "/addProduct": {
                int amount = Integer.parseInt(request.getParameter("amount"));
                int price = Integer.parseInt(request.getParameter("price"));
                BigDecimal biggy = new BigDecimal(price);
                String desc = request.getParameter("desc");
                String name = request.getParameter("name");

                String descclean = "";
                String nameclean = "";
                try {
                    // clean the desc and name with AntiSamy (just in case admin comprimised)
                    CleanResults cr = as.scan(desc, policy);
                    descclean = cr.getCleanHTML();
                    CleanResults cr2 = as.scan(name, policy);
                    nameclean = cr2.getCleanHTML();

                    System.out.println(cr.getCleanHTML());
                } catch (PolicyException pe) {
                    System.out.println(pe.getMessage());
                } catch (ScanException se) {
                    System.out.println(se.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                // selected catagory
                short cat = Short.parseShort(request.getParameter("cat"));
                System.out.print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Catagory " + cat);
                Category c = new Category();
                c.setId(cat);
                c.setName("new");
                String ca = c.toString();
                System.out.println(ca);
                
                // add new prodcut with detials and clean strings
                boolean val = pManager.addProduct(amount, c, descclean, nameclean, biggy);
                aManager.addProductLog(amount, c, descclean, nameclean, biggy);
                
                // redirect if valid or not
                if (val == true) {
                    userPath = "/pManageConfirm";
                } else {
                    userPath = "/pManageIssue";
                }
                break;

            }

            
            // admin remove product function
            case "/removeProduct": {
                int del = Integer.parseInt(request.getParameter("productId"));
                // log the deletetion
                aManager.removeProductlog(del);
                // and redirect if true
                boolean val = pManager.removeProduct(del);
                if (val == true) {
 
                    userPath = "/pManageConfirm";
                } else {
                    userPath = "/pManageIssue";
                }
                break;
            }

            // decrease the number of a prodcut in the database by one
            case "/decreaseProduct": {
                int del = Integer.parseInt(request.getParameter("productId"));
                boolean val = pManager.decreaseProduct(del);
                if (val == true) {
                    userPath = "/pManageConfirm";
                } else {
                    userPath = "/pManageIssue";
                }
                break;
            }

            // incearse the number of a product in the database by one
            case "/increaseProduct": {
                int del = Integer.parseInt(request.getParameter("productId"));
                boolean val = pManager.increaseProduct(del);

                if (val == true) {
                    userPath = "/pManageConfirm";
                } else {
                    userPath = "/pManageIssue";
                }
                break;
            }
            
            // admin page
            case "/admin": {
                if (u.getRole() == false && userPath.equals("/admin")) {
                    userPath = "/error";
                }

                break;

            }
        }
        // use RequestDispatcher to forward to uSERPATH
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException ex) {
        }
    }

}
