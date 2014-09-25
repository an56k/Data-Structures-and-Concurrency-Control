/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author Ankit
 */
public class CDLCoarse<T> extends CDLList<T> {
       public CDLCoarse(T vl) {
              super(vl);
       }
 
       @Override
       public Element head() {
              return head;
       }
 
       @Override
       public synchronized Cursor reader(Element from) {
              return new Cursor(from);
       }
 
       public class Cursor extends CDLList<T>.Cursor {
              public Cursor(Element E) {
                     super(E);
              }
 
              @Override
              public synchronized Writer writer() {
                     Writer write = new Writer(this.current());
                     return write;
              }
       }
 
      
 
       public static void main(String[] args) {
              // TODO code application logic here
              CDLCoarse<String> f;
              CDLCoarse<String>.Cursor c;
              f = new CDLCoarse<>("Hii");
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