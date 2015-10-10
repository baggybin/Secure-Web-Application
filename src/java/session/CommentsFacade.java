/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Comments;
import entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Jonathan 
 * 
 * for comments access in DB
 */
@Stateless
public class CommentsFacade extends AbstractFacade<Comments> {
    
    //persistance unit
    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    // manager
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentsFacade() {
        super(Comments.class);
    }

    /**
     * get the comments assosiated with a product ID via foreign key
     * 
     * @param id
     * @return
     */
    public List<String> getComments(int id) {
        Query query;
        query = em.createNativeQuery("SELECT comment FROM COMMENTS WHERE PRODUCT_ID = ?1");
        query.setParameter(1, id);
        List<String> ProductsAvailable = query.getResultList();
        return ProductsAvailable;
    }

    /**
     *
     * add a comment to a specific product
     * 
     * @param comment
     * @param id
     * @return
     */
    public boolean addComment(String comment, int id) {
        System.out.println("in add comment");
        Comments c = new Comments();
        
        boolean result = false;
        c.setComment(comment);
        // prodcut to fill the foreign key column
        Product p = new Product();
        p.setId(id);
        p.setAmount(0);
        c.setProductId(p);
        //a redunant column in DB
        c.setId(0);

        try {
            em.persist(c);
            result = true;
        } catch (PersistenceException e) {
        result = false;
        }

        return result;
    }

    
}
