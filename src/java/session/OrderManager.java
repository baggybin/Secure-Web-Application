/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jonathan
 */
@Stateless
public class OrderManager {
    
    // a mamanger for logs
    @EJB
    private AddlogManager aManager;
    
    
    //persistance unit
    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    /**
     * place an order and remove the quanity of that products from the database
     * log it
     * make aure enough there
     * 
     * @param cart
     * @return
     */
    public boolean placeOrder(ShoppingCart cart) {
        
        // get itesm from cart
        List<ShoppingCartItem> items = cart.getItems();
        Query query = null;
        boolean r = false;

        
        // for each product chechk its current quantity
        for (ShoppingCartItem scItem : items) {
            int productId = scItem.getProduct().getId();
            short quantity = scItem.getQuantity();
            System.out.println("QUANITTY" + quantity);
            
            // 
            query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1");
            query.setParameter(1, productId);

            int result = (int) query.getSingleResult();
            
            // test to see if there is enought there
            System.out.print(result);
            int test = result - (quantity + 1);
            System.out.println("TEST " + test);
            // log it
           aManager.checkoutProductlog(productId);            
            if (test > 0) {     
                // remove them if enough stock
                query = em.createQuery("UPDATE Product p SET p.amount = p.amount - :quantity  WHERE p.id = :productId");
                query.setParameter("quantity",quantity);
                query.setParameter("productId",productId);
                query.executeUpdate();
                System.out.println("herererere");
                r = true;
            } else {
                // otherwsie return false
                System.out.println("Not enough Stock");
                r = false;
            }

        }
        return r;
    }
}
