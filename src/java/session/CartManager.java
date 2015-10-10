/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import entity.Product;
import javax.ejb.Stateful;

/**
 *
 * @author Jonathan, nidhu, cathal
 * 
 * Statefull cart manager
 * handles statefull cart functions
 */
@Stateful
public class CartManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     * Clears the cart
     * @param clear
     * @param cart
     * @return
     */
        public boolean clearCart(String clear, ShoppingCart cart) {
        if ((clear != null) && clear.equals("true")) {
            cart.clear();
        }

        return true;
    }
    
    /**
     * Calcs a total for a cart
     * @param cart
     * @return
     */
    public boolean calcTotal(ShoppingCart cart)
    {
        cart.calculateTotal();
        return true;
    }
    
    /**
     * Add a podcut to the cart
     * @param cart
     * @param product
     * @return
     */
    public boolean addtoCart(ShoppingCart cart, Product product){
    
        cart.addItem(product);
        return true;
    }
    
    /**
     * update the cart
     * @param cart
     * @param p
     * @param quantity
     * @return
     */
    public boolean updateCart(ShoppingCart cart, Product p, String quantity)
    {
    
        cart.update(p, quantity);
        return true;
    }
    
    

}