/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Category;
import entity.Product;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Jonathan
 */
@Stateless
public class productManager {

    // persistance unit to store prodcut in the database
    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    /**
     *
     * Takes values to create a new prodcut and add it to the database
     * 
     * @param amount
     * @param c
     * @param desc
     * @param name
     * @param price
     * @return
     */
    public boolean addProduct(int amount, Category c, String desc, String name, BigDecimal price) {
        System.out.println("in add p");
        
        // prodcut setup
        Product p = new Product();
        boolean val = false;
        p.setAmount(amount);
        p.setCategoryId(c);
        p.setDescription(desc);
        p.setName(name);
        Date today = new Date();
        p.setLastUpdate(today);
        p.setPrice(price);
        
        // try and store in the DB
        try {
            em.persist(p);
            val = true;
        } catch (PersistenceException e) {
            
            System.out.println(e.toString());
            val = false;
        }

        return val;
    }
    
    /**
     * id used to remove specific prodcut from the database by admin
     * @param id
     * @return
     */
    public boolean removeProduct(int id) {
        System.out.println("You are heer removeing this :" + id);
        // find prodcuct by id
        Product p = em.find(Product.class, id);
        boolean val = false;
        try {
            em.remove(p);
            val = true;
        } catch (PersistenceException e) {
            val = false;

        }
        return val;

    }

    /**
     * Decrease the number of a product by one
     * @param id
     * @return
     */
    public boolean decreaseProduct(int id) {
        boolean r = false;
        try {
            Query query = null;
  
            //Java Prepared Statement
            query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1");
            // set quesry param
            query.setParameter(1, id);
            //get result
            int result = (int) query.getSingleResult();
            System.out.print("before " + result);
            // make sure theres one there
            int test = result - 1;
            if (test > 0) {
                // change ammount of prodcut
                query = em.createQuery("UPDATE Product p SET p.amount = p.amount - " + 1 + " WHERE p.id = ?1");
                query.setParameter(1, id);
                query.executeUpdate();
                System.out.println("herererere");
                r = true;
            } else {
                System.out.println("Not enough Stock");
                r = false;
            }
            boolean val = false;
        } catch (PersistenceException e) {
            r = true;
        }
        
        // show afterwards for testing in console
        Query query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1");
        query.setParameter(1, id);
        int afterresult = (int) query.getSingleResult();
        System.out.println("after wards :" + afterresult);
        return r;

    }

    /**
     * Icrease the amount of a prodcut 
     * same as decreaseProduct() above but decrements amount
     * 
     * @param id
     * @return
     */
    public boolean increaseProduct(int id) {
        boolean r = false;
        try {
            Query query = null;
            query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1");
            query.setParameter(1, id);
            int result = (int) query.getSingleResult();
            System.out.print("before " + result);
            int test = result + 1;
            if (test > 0) {
                query = em.createQuery("UPDATE Product p SET p.amount = p.amount + " + 1 + " WHERE p.id = ?1");
                query.setParameter(1, id);
                query.executeUpdate();
                //System.out.println("herererere");
                r = true;
            } else {
                System.out.println("Not enough Stock");
                r = false;
            }
            boolean val = false;
        } catch (PersistenceException e) {
            r = true;
        }
        Query query = em.createNativeQuery("SELECT AMOUNT FROM PRODUCT WHERE ID = ?1");
        query.setParameter(1, id);
        int afterresult = (int) query.getSingleResult();
        System.out.println("after wards :" + afterresult);

        return r;

    }

}
