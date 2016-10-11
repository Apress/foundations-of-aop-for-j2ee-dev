// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 4: JAC                                                  -
// -------------------------------------------------------------------

package aop.jac;

/**
 * This class generates an exception to illustrate the exception handling
 * mechanism provided by JAC.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class GenerateExcept {
    
    public static void main(String[] args) {
        new GenerateExcept().run();
    }
    
    /**
     * Random number generation till a divide by 0 exception is thrown.
     */
    public void run() {
        for( int i=0; true; i++ ) {
            int rand = (int) (Math.random()*5);
            System.out.println(i+": "+rand);
            int res = i/rand;
        }
    }

}
