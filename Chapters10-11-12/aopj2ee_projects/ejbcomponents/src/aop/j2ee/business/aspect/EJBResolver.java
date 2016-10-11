/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import aop.j2ee.business.entity.account.AccountHome;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.business.entity.customer.CustomerHome;
import aop.j2ee.business.entity.customer.Customer;
import aop.j2ee.business.entity.tx.TxHome;
import aop.j2ee.business.entity.tx.Tx;
import aop.j2ee.business.aspect.sql.DBUtil;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract aspect EJBResolver {

    //public EJBResolver() {}

    // used for other bean resolving
    static protected TxHome txHome;
    static protected AccountHome accountHome;
  static protected CustomerHome customerHome;

    Account around(String accountID) throws Exception:
    execution(private Account *.findAccountByPrimaryKey(String))
    && args(accountID) {
        return accountHome.findByPrimaryKey(accountID);
    }

    Tx around(String txID) throws Exception:
    execution(private Tx *.findTxByPrimaryKey(String))
    && args(txID) {
        return txHome.findByPrimaryKey(txID);
    }

  Customer around(String customerID) throws Exception:
  execution(private Customer *.findCustomerByPrimaryKey(String))
  && args(customerID) {
      return customerHome.findByPrimaryKey(customerID);
  }


    Collection around(Date startDate,Date endDate,String accountId) throws Exception:
    execution(private Collection *.findTxByAccountId(Date,Date,String))
    && args(startDate,endDate,accountId) {
        return txHome.findByAccountId(startDate,endDate,accountId);
    }

    Tx around(String accountId, Date date,
    BigDecimal amount, BigDecimal newBalance, String description)  throws Exception: 
    execution(private Tx *.createTx(String, Date,
    BigDecimal, BigDecimal, String))
    && args(
    accountId,
    date,
    amount,
    newBalance,
    description) {

    return txHome.create(
        DBUtil.getNextTxId(),
        accountId,
        date,
        amount,
        newBalance,
        description);        
    }

  Collection around(String customerId) throws Exception:
  execution(private Collection *.findAccountsByCustomerId(String))
  && args(customerId) {
    // todo
    return null;
  }

  Collection around(String accountId) throws Exception:
  execution(private Collection *.findCustomersByAccountId(String))
  && args(accountId) {
    // todo
    return null;
  }

  Collection around(String lastName) throws Exception:
  execution(private Collection *.findCustomerByLastName(String))
  && args(lastName) {
    // todo
    return null;
  }

}
