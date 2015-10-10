/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author joNATHAN
 * 
 * use for modifications to products, autogen by netbeans
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    // get a prodcut
    public List<Product> getProduct(String name) {
        // create named query from Customer Entity Class
        Query query = em.createNamedQuery("Product.findByName");
        // set paramter for query
        query.setParameter("name", name);
        // get results for query
        List<Product> ProductsAvailable = query.getResultList();
        return ProductsAvailable;
    }

    
    // Get a product with a simular pattern match
    public List<Product> getProductPattern(String name) {
        // create named query from Customer Entity Class
        Query query = em.createNamedQuery("Product.getProductPattern");
        // set paramter for query
        query.setParameter("name", name);
        // get results for query
        List<Product> ProductsAvailable = query.getResultList();
        return ProductsAvailable;
    }

    // egt the prodct via its ID
    public Product getProductID(int id) {
        Product p = null;
        try {
            p = (Product) em.createNamedQuery("Product.findById").setParameter("id", id).getSingleResult();
        } catch (Exception ex) {
        }
        return p;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

}
