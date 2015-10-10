/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Addlog;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nido
 */
@Stateless
public class AddlogFacade extends AbstractFacade<Addlog> {

    @PersistenceContext(unitName = "shopBeanPU")
    private EntityManager em;
    public Addlog getAddlogID(int id) {
        return (Addlog) em.createNamedQuery("Addlog.findById").
                setParameter("id", id).getSingleResult();

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddlogFacade() {
        super(Addlog.class);
    }

}