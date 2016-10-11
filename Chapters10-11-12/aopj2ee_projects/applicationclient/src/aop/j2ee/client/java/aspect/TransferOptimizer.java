/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.java.aspect;

import aop.j2ee.commons.aspect.to.*;
import aop.j2ee.business.session.bank.Bank;
import aop.j2ee.client.java.aspectized.Simple;
import java.util.Date;
import java.math.BigDecimal;

public aspect TransferOptimizer {

  /*
  ThreadLocal to = new ThreadLocal();

  Object around(
    String lastName,
    String firstName,
    String middleInitial,
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email) : call(
    String aop.j2ee.business.session.bank.Bank +.createCustomer(..))
    && args(
      lastName,
      firstName,
      middleInitial,
      street,
      city,
      state,
      zip,
      phone,
      email)
    && withincode(void Simple.main(String[])) {
    CustomerAndAccountInfos infos = new CustomerAndAccountInfos();
    infos.setLastName(lastName);
    infos.setFirstName(firstName);
    infos.setMiddleInitial(middleInitial);
    infos.setStreet(street);
    infos.setCity(city);
    infos.setState(state);
    infos.setZip(zip);
    infos.setPhone(phone);
    to.set(infos);
    return null;
  }

  Object around(
    Bank bank,
    String customerId,
    String type,
    String description,
    BigDecimal balance,
    BigDecimal creditLine,
    BigDecimal beginBalance,
    Date beginBalanceTimeStamp) : call(
    String aop.j2ee.business.session.bank.Bank +.createAccount(..))
    && args(
      customerId,
      type,
      description,
      balance,
      creditLine,
      beginBalance,
      beginBalanceTimeStamp)
    && withincode(void Simple.main(String[]))
    && target(bank) {
    CustomerAndAccountInfos infos = (CustomerAndAccountInfos)to.get();
    if (infos == null) {
      return proceed(
        bank,
        customerId,
        type,
        description,
        balance,
        creditLine,
        beginBalance,
        beginBalanceTimeStamp);
    } else {
      infos.setType(type);
      infos.setDescription(description);
      infos.setBalance(balance);
      infos.setCreditLine(creditLine);
      infos.setBeginBalance(beginBalance);
      infos.setBeginBalanceTimeStamp(beginBalanceTimeStamp);
      String id = bank.createAccountWithCustomer(infos);
      // reset the transfer object 
      to.set(null);
      return id;
    }
  }
*/
  /*
      pointcut currencyConversion(double aValue):
        call(* aop.j2ee.business.session.bank.Bank+.*(..)) 
        && args(aValue) && !within(ClientAspect);
  
      double around(double aValue) throws java.rmi.RemoteException,
    TooLargeValueException: currencyConversion(aValue) {
        Signature sig = thisJoinPointStaticPart.getSignature();
        String name = sig.getName();
        ICurrency currency = null;
        try {
          currency = (ICurrency)getServiceFacade(Currency.class);
        } catch(SystemException se) {}
        return currency.dollarToPound(aValue);
      }
  
  
        //public static final String CURRENCY = "CurrencyTO";
  
        private HashMap transferObjectMap = new HashMap();
  
        pointcut currencytransfer(ICurrency aCurrency):
          call(* aop.j2ee.business.ICurrency+.get*Currency()) && 
          target(aCurrency) && !within(ClientAspect);
  
  
      
        Object around(ICurrency aCurrency)
          throws java.rmi.RemoteException: currencytransfer(aCurrency) {
    
          CurrencyTO to = (CurrencyTO)transferObjectMap.get(CURRENCY);
              if(to == null) {
                to = (CurrencyTO)aCurrency.getCurrencyTO();
                // Store the TransferObject in client cache 
                transferObjectMap.put(CURRENCY,to);            
          }
          Signature sig = thisJoinPointStaticPart.getSignature();
              String name = sig.getName();
          if(name.equals("getUsCurrency"))
            return to.getUsCurrency();
          else if(name.equals("getUkCurrency"))
            return to.getUkCurrency();
            else if(name.equals("getEuropeCurrency"))
            return to.getEuropeCurrency();
            else if(name.equals("getPolandCurrency"))
            return to.getPolandCurrency();
            return null;
          }
  
  
            double around(double aValue) throws java.rmi.RemoteException,
          TooLargeValueException: currencyConversion(aValue) {
              Signature sig = thisJoinPointStaticPart.getSignature();
              String name = sig.getName();
              ICurrency currency = null;
              try {
                currency = (ICurrency)getServiceFacade(Currency.class);
              } catch(SystemException se) {}
              return currency.dollarToPound(aValue);
            }
      
        public Object getServiceFacade(Class aClass)
          throws SystemException {
              return null;
        }
      }
  
  */

}
