/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */


// Netbeans sample projoct jsf_jpa


//Authors - Jonathan , Nidhu, Cathal
//Modifications and Additions
//Password Hashing 
//Antisamy cleaning of input
//password complexity checking


package enterprise.jsf_jpa_war;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;


/**
 * <p>
 * A simple managed bean to mediate between the user and the persistence
 * layer.</p>
 *
 * @author rlubke netbeans example project code
 */
public class UserManager {

    /**
     * <p>
     * The key for the session scoped attribute holding the appropriate
     * <code>Wuser</code> instance.</p>
     */
    public static final String USER_SESSION_KEY = "user";

    /**
     * <p>
     * The <code>PersistenceContext</code>.</p>
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * <p>
     * The transaction resource.</p>
     */
    @Resource
    private UserTransaction utx;

    /**
     * <p>
     * User properties.</p>
     */
    private String username;
    private String password;
    private String passwordv;
    private boolean role;

    // -------------------------------------------------------------- Properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordv() {
        return passwordv;
    }

    public void setPasswordv(String passwordv) {
        this.passwordv = passwordv;
    }

    public boolean getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    // ---------------------------------------------------------- Public Methods
    /**
     * <p>
     * Validates the user. If the user doesn't exist or the password is
     * incorrect, the appropriate message is added to the current
     * <code>FacesContext</code>. If the user successfully authenticates,
     * navigate them to the page referenced by the outcome
     * <code>app-main</code>.
     * </p>
     *
     * uses AntiSamy to clean th username to stop reflexive XSS
     * 
     * 
     * 
     * @return <code>app-main</code> if the user authenticates, otherwise
     * returns <code>null</code>
     */
    public String validateUser() {
        try {
            // get a faces context view to display to browser
            FacesContext context = FacesContext.getCurrentInstance();
            // egt the user based on name
            Wuser user = getUser();
            // new hashing object
            hashPass hash = new hashPass();
            String pass = "";
           
            // get the username
            String username = getUsername();
            System.out.println("Username =" + username);
            
            // antisamy setup
            org.owasp.validator.html.Policy policy = null;
            try {
                policy = org.owasp.validator.html.Policy.getInstance("c:\\AntiSamy\\antisamy-slashdot-1.4.4.xml");
            } catch (PolicyException ex) {
                System.out.println("exception in policey loader");
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
            
            // clearn the input
            AntiSamy as = new AntiSamy();
            CleanResults cr = null;
            try {
                cr = as.scan(username, policy);
                System.out.println(cr.getCleanHTML());
            } catch (PolicyException pe) {
                System.out.println(pe.getMessage());
            } catch (ScanException se) {
                System.out.println(se.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("eerrerereerererere");
            }
            System.out.println("just here");
            String clean = cr.getCleanHTML();
            System.out.println("clean username = " + clean);

            // disaply fail if issue with cleaned
            if (clean == null || clean.equals("")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username invalid.");
                context.addMessage(null, message);
                return null;

            }
            // set the user anme to clean string
            user.setUsername(clean);

            
            // hash the entered password an compare agains the retieved hashed passowrd in the database
            if (user != null) {
                try {
                    System.out.println("passwords is " + password);
                    // has the entered passoword
                    pass = hash.start(password);
                    System.out.println("hased pass is " + pass);
                    System.out.println("original pass h " + user.getPassword());
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                // display failed hash match
                if (!user.getPassword().equals(pass)) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Login Failed!",
                            "The password specified is not correct.");
                    context.addMessage(null, message);
                    return null;
                }
                // add a sesson key to conexg for the user
                context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);
                return "app-main";
            } else {
                // else displau failed
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed!",
                        "Username '"
                        + username
                        + "' does not exist.");
                context.addMessage(null, message);
                return null;
            }
        } catch (NullPointerException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!",
                    "Username '"
                    + "' invalid input.");
            context.addMessage(null, message);
            return null;
        }
    }

    /**
     * <p>
     * Creates a new <code>Wuser</code>. If the specified user name exists or an
     * error occurs when persisting the Wuser instance, enqueue a message
     * detailing the problem to the <code>FacesContext</code>. If the user is
     * created, move the user back to the login view.</p>
     *
     * hash the entered password
     * clean namewith antisamy
     * 
     * @return <code>login</code> if the user is created, otherwise returns
     * <code>null</code>
     */
    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        // Find user if they exist already
        Wuser wuser = getUser();
        // if doesnt
        if (wuser == null) {
            // check if the passwords match
            if (!password.equals(passwordv)) {
                FacesMessage message = new FacesMessage("The  passwords do not match. try again");
                // display message though face
                context.addMessage(null, message);
                return null;
            }
            
            // Check the password strenght with a password strenght object
            passwordStrength pStrenghtCheck = new passwordStrength();
            // return the strenght vlaue of password
            int strenght = pStrenghtCheck.checkPasswordStrength(password);

            System.out.println("Stenght of the password is " + strenght);

            // if password strenght is below a certain threshol
            if (strenght < 55) {
                FacesMessage message = new FacesMessage("The specified password is not Strong enough, Please use ALPHANUMERICS, over 7 characters + Upper and lower");
                context.addMessage(null, message);
                return null;
            }

            //setup antisamy
            org.owasp.validator.html.Policy policy = null;
            try {
                policy = org.owasp.validator.html.Policy.getInstance("c:\\AntiSamy\\antisamy-slashdot-1.4.4.xml");
            } catch (PolicyException ex) {
                System.out.println("exception in policey loader");
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }

            
            // clean the username
            AntiSamy as = new AntiSamy();
            CleanResults cr = null;
            try {
                cr = as.scan(username, policy);
                System.out.println(cr.getCleanHTML());
            } catch (PolicyException pe) {
                System.out.println(pe.getMessage());
            } catch (ScanException se) {
                System.out.println(se.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("eerrerereerererere");
            }
            System.out.println("just here");
            String clean = cr.getCleanHTML();

            System.out.println("clean username = " + clean);

            if (clean == null || clean.equals("")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "create failed! invalid username", "Username invalid.");
                context.addMessage(null, message);
                return null;
            }

            // hash the password
            hashPass h = new hashPass();
            String hashedPass = "";
            try {
                hashedPass = h.start(password);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // create the new user ans store the hash ans user
            wuser = new Wuser();
            wuser.setPassword(hashedPass);
            wuser.setUsername(clean);
            role = false;
            wuser.setRole(role);
            wuser.setSince(new Date());
            try {
                utx.begin();
                em.persist(wuser);
                utx.commit();
                return "login";
            } catch (Exception e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error creating user!",
                        "Unexpected error when creating your account.  Please contact the system Administrator");
                context.addMessage(null, message);
                Logger.getAnonymousLogger().log(Level.SEVERE,
                        "Unable to create new user",
                        e);
                return null;
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Username '"
                    + username
                    + "' already exists!  ",
                    "Please choose a different username.");
            context.addMessage(null, message);
            return null;
        }
    }

    /**
     * <p>
     * When invoked, it will invalidate the user's session and move them to the
     * login view.</p>
     *
     * @return <code>login</code>
     */
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "login";

    }

//    private boolean getUserRole() {
//        try {
//            boolean role = false;
//            Wuser user = (Wuser) em.createNamedQuery("Wuser.findByUsername").
//                    setParameter("username", username).getSingleResult();
//
//            role = user.getRole();
//            return role;
//        } catch (NoResultException nre) {
//            return role;
//        }
//
//    }

    // --------------------------------------------------------- Private Methods
    /**
     * <p>
     * This will attempt to lookup a <code>Wuser</code> object based on the
     * provided user name.</p>
     *
     * @return a <code>Wuser</code> object associated with the current username,
     * otherwise, if no <code>Wuser</code> can be found, returns
     * <code>null</code>
     */
    public Wuser getUser() {
        try {
            Wuser user = (Wuser) em.createNamedQuery("Wuser.findByUsername").
                    setParameter("username", username).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }

}

//
// Simple password strenght class 
// chechk for lenght, uppercase, lowercase, digits and number and
// generates a score from 17.5 to 100
//
class passwordStrength {

    public int checkPasswordStrength(String password) {
        int strength = 0;
        int lenght = password.length();
        // lowerCase
        String[] Checks = {".*[a-z]+.*",
            ".*[A-Z]+.*", // upperCase
            ".*[\\d]+.*", // digits
            ".*[@#$%]+.*"};// symbols

        if (password.matches(Checks[0])) {
            strength += 17.5;
        }
        if (password.matches(Checks[1])) {
            strength += 17.5;
        }
        if (password.matches(Checks[2])) {
            strength += 17.5;
        }
        if (password.matches(Checks[3])) {
            strength += 17.5;
        }
        if (lenght >= 8) {
            strength += 30;
        }

        return strength;
    }

}
