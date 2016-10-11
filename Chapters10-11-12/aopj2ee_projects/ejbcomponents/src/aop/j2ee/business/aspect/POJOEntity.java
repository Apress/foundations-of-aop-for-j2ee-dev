/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import java.sql.*;
import javax.ejb.*;
import aop.j2ee.business.aspect.marker.EntityBeanProtocol;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract aspect POJOEntity extends EJBResolver {

    declare parents: EntityBeanProtocol extends javax.ejb.EntityBean;

    private EntityContext EntityBeanProtocol.context;

    // ejb methods 
    public String EntityBeanProtocol.ejbFindByPrimaryKey(String primaryKey) 
        throws FinderException {
   
        boolean result;

        try {
            result = selectByPrimaryKey(primaryKey);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByPrimaryKey: " + 
                  ex.getMessage());
        }
   
        if (result) {
            return primaryKey;
        }
        else {
            throw new ObjectNotFoundException
                ("Row for id " + primaryKey + " not found.");
        }
    }

    public void EntityBeanProtocol.ejbRemove() {
   
        try {
            deleteRow(getEntityId());
         } catch (Exception ex) {
              throw new EJBException("ejbRemove: " + 
                  ex.getMessage());
         }
    } 
   
    public void EntityBeanProtocol.setEntityContext(EntityContext context) {
   
        this.context = context;
        setExtraContext();
    }

    public void EntityBeanProtocol.unsetEntityContext() {
   
    }
   
    public void EntityBeanProtocol.ejbLoad() {
   
        try {
            loadEntity();
         } catch (Exception ex) {
              throw new EJBException("ejbLoad: " + 
                  ex.getMessage());
         }
    }
    
    public void EntityBeanProtocol.ejbStore() {

        try {
            storeEntity();
         } catch (Exception ex) {
              throw new EJBException("ejbStore: " + 
                  ex.getMessage());
         }
    }
   
    public void EntityBeanProtocol.ejbActivate() {

        setEntityId((String)context.getPrimaryKey());
    }

    public void EntityBeanProtocol.ejbPassivate() {

        setEntityId(null);
    }

    private void EntityBeanProtocol.makeConnection() {}
    private void EntityBeanProtocol.releaseConnection() {}
    private void EntityBeanProtocol.insertRow () throws SQLException {}
    private void EntityBeanProtocol.deleteRow(String id) throws SQLException {}
    private boolean EntityBeanProtocol.selectByPrimaryKey(String primaryKey) { return false; }
    private void EntityBeanProtocol.loadEntity() throws SQLException {}
    private void EntityBeanProtocol.storeEntity() throws SQLException {}
    private String EntityBeanProtocol.getEntityId() throws SQLException { return null; }
    private void EntityBeanProtocol.setEntityId(String id) {}
    private void EntityBeanProtocol.setExtraContext() {}

}
