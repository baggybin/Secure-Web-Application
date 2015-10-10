/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Category;
//import entity.Product;
import entity.Addlog;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author nido
 */
@Stateless
public class AddlogManager {

    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    /**
     *
     * 
     * Log a product being added to the database
     * @param amount
     * @param c
     * @param desc
     * @param name
     * @param price
     * @return
     */
    public boolean addProductLog(int amount, Category c, String desc, String name, BigDecimal price) {
        System.out.println("in add p");
        // new add log
        Addlog a = new Addlog();
        a.setAmount(amount);
        //a.setCategoryId(c);
        a.setDescription("PRODUCT ADDED -> " + desc);
        a.setName(name);
        Date today = new Date();
        a.setLastUpdate(today);
        a.setPrice(price);
        try {
            em.persist(a);
        } catch (PersistenceException e) {

        }

        return true;
    }

    /**
     *
     * log when a prodcut is removed from the database
     * @param id
     */
    public void removeProductlog(int id) {
        // get details for the log
        Query query = null;
        query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1"); 
        query.setParameter(1, id);
        int amount = (int) query.getSingleResult();
        query = em.createNativeQuery("SELECT NAME FROM PRODUCT WHERE ID = ?1");
        query.setParameter(1, id);       
        String name = (String) query.getSingleResult();
        query = em.createNativeQuery("SELECT PRICE FROM PRODUCT WHERE ID = ?1");  
        query.setParameter(1, id);        
        double price = (double) query.getSingleResult();           
        BigDecimal p = new BigDecimal(price); 
        // log it
        Addlog a1 = new Addlog();
        a1.setAmount(amount);
        a1.setDescription("PRODUCT REMOVED -> ");
        a1.setName(name);
        Date today = new Date();
        a1.setLastUpdate(today);
        a1.setPrice(p);
        try {
            em.persist(a1);
        } catch (PersistenceException e) {
        }

    }
    
    /**
     * log when a checout occurs
     * @param id
     */
    public void checkoutProductlog(int id) {
//        Query query = null;
//        query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1"); 
//        query.setParameter(1, id);
//        int amount = (int) query.getSingleResult();
//        query = em.createNativeQuery("SELECT NAME FROM PRODUCT WHERE ID = ?1");
//        query.setParameter(1, id);       
//        String name = (String) query.getSingleResult();
//        query = em.createNativeQuery("SELECT PRICE FROM PRODUCT WHERE ID = ?1");  
//        query.setParameter(1, id);        
//        double price = (double) query.getSingleResult();      
        
        // add log about chechk out
        BigDecimal p = new BigDecimal(0); 
        Addlog a2 = new Addlog();
        a2.setAmount(0);
        a2.setDescription("PRODUCT CHECKED OUT ");
        a2.setName("checkout");
        Date today = new Date();
        a2.setLastUpdate(today);
        a2.setPrice(p);
        try {
            em.persist(a2);
        } catch (PersistenceException e) {
        }

    } 
}
