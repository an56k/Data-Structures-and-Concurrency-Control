
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ankit
 */
public class CDLCoarseRW<T> extends CDLList<T> {
    RWLock rw = new RWLock();
       public CDLCoarseRW(T vl) {
              super(vl);
       }
 
       @Override
       public Element head() {
              return head;
       }
 
       @Override
       public Cursor reader(Element from) {
        try {
            rw.lockRead();
        } catch (InterruptedException ex) {
            Logger.getLogger(CDLCoarseRW.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
              return new Cursor(from);
        }finally{
               rw.unlockRead();
               
        }   
       }
 
       public class Cursor extends CDLList<T>.Cursor {
              public Cursor(Element E) {
                     super(E);
              }
 
              @Override
              public Writer writer() {
               try {
                   rw.lockWrite();
               } catch (InterruptedException ex) {
                   Logger.getLogger(CDLCoarseRW.class.getName()).log(Level.SEVERE, null, ex);
               }
               try{
                     Writer write = new Writer(this.current());
                     return write;
               }finally{
                             rw.unlockWrite();
               
               }
              }
       }
 
      
 
       public static void main(String[] args) {
              // TODO code application logic here
              CDLCoarseRW<String> f;
              CDLCoarseRW<String>.Cursor c;
              f = new CDLCoarseRW<>("Hii");
              c = f.reader(f.head());
              c.next();
              System.out.println(c.current().value());
              if (f.head() == c.current()) {
                     System.out.println("Circular List\n");
              }
              System.out.println(c.writer().insertBefore("bye"));
              System.out.println(c.writer().insertBefore("hello"));
              System.out.println(c.writer().insertAfter("up"));
 
              c = f.reader(f.head());
              do{
                     System.out.println(c.current().value());
                     c.next();
              } while ((f.head() != c.current()));
 
       }
}
