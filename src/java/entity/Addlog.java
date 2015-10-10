/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nido
 * 
 * generated class to access logs
 */
@Entity
@Table(name = "ADDLOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addlog.findAll", query = "SELECT a FROM Addlog a"),
    @NamedQuery(name = "Addlog.findById", query = "SELECT a FROM Addlog a WHERE a.id = :id"),
    @NamedQuery(name = "Addlog.findByName", query = "SELECT a FROM Addlog a WHERE a.name = :name"),
    @NamedQuery(name = "Addlog.findByPrice", query = "SELECT a FROM Addlog a WHERE a.price = :price"),
    @NamedQuery(name = "Addlog.findByDescription", query = "SELECT a FROM Addlog a WHERE a.description = :description"),
    @NamedQuery(name = "Addlog.findByLastUpdate", query = "SELECT a FROM Addlog a WHERE a.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "Addlog.findByAmount", query = "SELECT a FROM Addlog a WHERE a.amount = :amount"),
    @NamedQuery(name="Addlog.getAddlogPattern", query="SELECT a FROM Addlog a WHERE a.name LIKE ?1 ORDER BY a.name ASC")
    })
public class Addlog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "AMOUNT")
    private Integer amount;    


    public Addlog() {
    }

    public Addlog(Integer id) {
        this.id = id;
    }

    public Addlog(Integer id, String name, BigDecimal price, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addlog)) {
            return false;
        }
        Addlog other = (Addlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Addlog[ id=" + id + " ]";
    }
    
}
