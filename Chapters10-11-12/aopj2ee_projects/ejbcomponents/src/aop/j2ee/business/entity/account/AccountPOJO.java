/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.entity.account;

import java.util.*;
import java.math.*;
import javax.ejb.*;
import aop.j2ee.commons.to.AccountDetails;

import aop.j2ee.business.aspect.marker.EntityBeanProtocol;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccountPOJO implements EntityBeanProtocol {

    private String accountId;
    private String type;
    private String description;
    private BigDecimal balance;
    private BigDecimal creditLine;
    private BigDecimal beginBalance;
    private java.util.Date beginBalanceTimeStamp;
    private ArrayList customerIds;

    // business methods

    public AccountDetails getDetails() {

        try {
            loadCustomerIds();
        } catch (Exception ex) {
             throw new EJBException("loadCustomerIds:  " +
                ex.getMessage());
        }

        return new AccountDetails(accountId, type, description, balance, 
            creditLine, beginBalance, beginBalanceTimeStamp,
            customerIds);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public void setBeginBalanceTimeStamp(java.util.Date beginBalanceTimeStamp) {
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }

    private void loadCustomerIds() throws Exception {}

}
