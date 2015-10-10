
/*

 Author Jonanthan, nidhu, cathal
 search by product 
 */
package controller;

import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.ProductFacade;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import enterprise.jsf_jpa_war.Wuser;
import javax.servlet.ServletConfig;
import captchas.CaptchasDotNet;

/**
 *
 * @author Jonathan
 */
@WebServlet(name = "SearchController",
        loadOnStartup = 1,
        urlPatterns = {"/searching", "/searchingDelete"})

public class searchController extends HttpServlet {

    /**
     *
     * The key for the session scoped attribute holding the appropriate Wuser
     * ROLE instance
     */
    public static final String USER_SESSION_KEY = "user";

    /**
     *
     * The transaction resource.
     */
    @Resource
    private UserTransaction utx;

    // Product Enterpise bean
    @EJB
    private ProductFacade productFacade;

    //Persistance Unit to access the Derby Database - Entity Class manager
    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    /**
     * Servelet context
     *
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    /**
     *
     * handles searching for Products via passed paramters in the database
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // get the userapath
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();

        // Construct the captchas object
        CaptchasDotNet captchas = new captchas.CaptchasDotNet(
                request.getSession(true), // Ensure session
                "cathaldcronin1", // client
                "pMEUfYtuPU9Uku8X0OxdFrFaoXDkI7PNq5hLFIdS" // secret
        );
        
        
        Wuser u = (Wuser) session.getAttribute("user");     
       
        

        // Read the form values
        String password = request.getParameter("password");
        if (u.getRole() == true)
        {
        password = "admin";
        }
        // Check captcha
        String body;
        boolean verified = false;

        switch (captchas.check(password)) {
            case 's':
                body = "Session seems to be timed out or broken. ";
                body += "Please try again or report error to administrator.";
                break;
            case 'm':
                body = "Every CAPTCHA can only be used once. ";
                body += "The current CAPTCHA has already been used. ";
                body += "Please use back button and reload";
                break;
            case 'w':
                body = "You entered the wrong password. ";
                body += "Please use back button and try again. ";
                break;
            default:
                body = "Your message was verified to be entered by a human";
                verified = true;
                break;
        }
        
       // get user role for access to certain admin functions
        
        if (u.getRole() == true)
        {verified = true;}
        if(verified) {
            // type of search, id or name
            String type = request.getParameter("type");
            Product product;// = new Product();
            List<Product> products;


            if (u.getRole() == false && userPath.equals("/searchingDelete")) {
                // redirect if not admin
                userPath = "/errorPage";
            }

        // return prodcuts searched for by an admin, match by pattern and exact match
            // set to session and redirect to admin delete page
            if (userPath.equals("/searchingDelete")) {
                System.out.println("search delete");
                String name = request.getParameter("search");
                products = productFacade.getProduct(name);
                session.setAttribute("products", products);
                Query queryProductsByName = em.createNamedQuery("Product.getProductPattern");
                queryProductsByName.setParameter(1, "%" + name + "%");
                List<Product> pPattern = queryProductsByName.getResultList();
                session.setAttribute("pPattern", pPattern);

                // if the srandard user search typs is via ID then access database via id 
            } else if (type.equals("1")) {
                System.out.println("here1");
                int id = Integer.parseInt(request.getParameter("search"));
                System.out.println("id = " + id);
                product = productFacade.getProductID(id);
                String result = "true";
                if (product == null) {
                    result = "false";
                }
                // set a boolean vlaue (string) about result and add prodcuts if found
                session.setAttribute("r", result);
                session.setAttribute("products", product);

                // if search typs is equal to name type then search by pattern match with regular expressions
            } else {
                System.out.println("here2");
                String name = request.getParameter("search");
                // get single if exact match and exits
                products = productFacade.getProduct(name);
                session.setAttribute("products", products);
                // get and muti if match a portion of the string
                Query queryProductsByName = em.createNamedQuery("Product.getProductPattern");
                queryProductsByName.setParameter(1, "%" + name + "%");
                List<Product> pPattern = queryProductsByName.getResultList();
                session.setAttribute("pPattern", pPattern);
            }

            // redirect to appropriate JSP page // will have been cgnaned if restricted and user not admin role
            if (userPath.equals("/searchingDelete")) {
                userPath = "/adminDelete";
            } else if (type.equals("1")) {
                userPath = "/searching";
            } else if (type.equals("2")) {
                userPath = "/searchingName";
            }

            String url = "/WEB-INF/view" + userPath + ".jsp";

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (ServletException | IOException ex) {
            }
        }
        else
        {
            userPath = "/WrongCaptcha";
            String url = "/WEB-INF/view" + userPath + ".jsp";
            
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (ServletException | IOException ex) {
            }
        }
        
    }

}
