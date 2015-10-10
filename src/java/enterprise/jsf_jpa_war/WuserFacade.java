/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.jsf_jpa_war;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jonathan
 * Entity persistance module for accessing user inn DB
 */
@Stateless
public class WuserFacade extends AbstractFacade<Wuser> {
    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WuserFacade() {
        super(Wuser.class);
    }
    
}
