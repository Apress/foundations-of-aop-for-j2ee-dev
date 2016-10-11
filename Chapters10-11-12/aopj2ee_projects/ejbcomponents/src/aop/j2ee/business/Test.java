/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business;

import aop.j2ee.business.session.txcontroller.TxControllerPOJO;
import javax.ejb.*;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Test {

	public static void main(String[] args) {
        System.out.println("instantiating pojo");        
        TxControllerPOJO pojo=new TxControllerPOJO();
        System.out.println("pojo="+pojo);        
        try {
        ((SessionBean)pojo).ejbRemove();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("calling test");        
        //pojo.test();        
        System.out.println("success");        
	}

  // =================================================================

   /*
   public void test() {
       try {
           System.out.println("1");
           findTxByPrimaryKey("essai");
       } catch(Exception e) {
           System.out.println("findTxByPrimaryKey failed: "+e);
       }
       try {
           System.out.println("2");
           withdraw(new BigDecimal(33),null,"essai");
       } catch(Exception e) {
           System.out.println("withdraw failed: "+e);
       }
   }

*/
}
