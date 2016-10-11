/*
 * Created on Mar 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.entity.tx;

import java.math.BigDecimal;
import aop.j2ee.commons.to.TxDetails;
import aop.j2ee.business.aspect.marker.EntityBeanProtocol;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TxPOJO implements EntityBeanProtocol {

  private String txId;
  private String accountId;
  private java.util.Date timeStamp;
  private BigDecimal amount;
  private BigDecimal balance;
  private String description;

  // business methods

  public TxDetails getDetails() {
    return new TxDetails(
      txId,
      accountId,
      timeStamp,
      amount,
      balance,
      description);
  }

}
