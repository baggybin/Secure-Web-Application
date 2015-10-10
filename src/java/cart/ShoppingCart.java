/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Product;
import java.util.*;

/**
 * @author orginal Netbeans Tutorial Affablebean
 * @author Jonathan Nidhu, Cathal
 */
public class ShoppingCart {
    List<ShoppingCartItem> items;
    int numberOfItems;
    double total;

    // contructor to create cart
    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    // syncronised access to add an item
    public synchronized void addItem(Product product) {
        boolean newItem = true;
        for (ShoppingCartItem scItem : items) {
            if (scItem.getProduct().getId() == product.getId()) {
                newItem = false;
                // increase as=mount if this id exists in the cart
                scItem.incrementQuantity();
            }
        }

        // add new item
        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }


    // synorconised acces to update the cart
    public synchronized void update(Product product, String quantity) {
        short qty = -1;
        // cast quantity as short
        qty = Short.parseShort(quantity);
        if (qty >= 0) {
            ShoppingCartItem item = null;
            for (ShoppingCartItem scItem : items) {
                if (scItem.getProduct().getId() == product.getId()) {
                    if (qty != 0) {
                        // set item quantity to new value
                        scItem.setQuantity(qty);
                    } else {
                        // if quantity equals 0, save item and break
                        item = scItem;
                        break;
                    }
                }
            }
            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }

    /**
     * Returns the list of items
     *
     * @return the items list
     */
    public synchronized List<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Returns the addition 
     * items list
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem scItem : items) {
            numberOfItems += scItem.getQuantity();
        }
        return numberOfItems;
    }

    /**
     * Returns cost.
     *
     * @return the cost of all items * their quantities
     */
    public synchronized double getSubtotal() {

        double amount = 0;
        for (ShoppingCartItem scItem : items) {
            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice().doubleValue());
        }
        return amount;
    }

    /**
     * Calculates the total cost of the order.
     * @param none
     */
    public synchronized void calculateTotal() {
        double amount = 0;
        amount = this.getSubtotal();
        total = amount;
    }

    /**
     * Returns the total cost of the order 
     *
     * @return the cost of all items times their quantities 
     */
    public synchronized double getTotal() {
        return total;
    }

    /**
     * Empties the shopping cart.
     * 
     * @see ShoppingCartItem
     */
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}