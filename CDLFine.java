/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ankit
 */
public class CDLFine<T> extends CDLList<T> {
       public CDLFine(T vl) {
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
              
              
       }
       
       public class Writer extends CDLList<T>.Writer{
           public Writer(Element node){
               super(node);
           }
           
           @Override
           public boolean insertBefore(T val){
               synchronized(node.prev){
                   synchronized(node){
               if(node==null)
                {
                    return false;
                }
                Element newnode = new Element(null,val,null);
                newnode.next=node;
                newnode.prev=node.prev;
                node.prev.next=newnode;
                node.prev=newnode;
                return true; 
                   }
               } 
           }
           
           @Override
           public boolean insertAfter(T val){
               synchronized(node.next){
                   synchronized(node){
                       if(node==null)
                {
                    return false;
                }
                Element newnode = new Element(null,val,null);
                newnode.prev=node;
                newnode.next=node.next;
                node.next.prev=newnode;
                node.next=newnode;
                return true;
                   }
               }
           }
       }
 
      
 
       public static void main(String[] args) {
              // TODO code application logic here
              CDLFine<String> f;
              CDLFine<String>.Cursor c;
              f = new CDLFine<>("Hii");
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