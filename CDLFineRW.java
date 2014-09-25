
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
public class CDLFineRW<T> extends CDLList<T> {
    RWLock rw = new RWLock();   
    public CDLFineRW(T vl) {
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
            Logger.getLogger(CDLFineRW.class.getName()).log(Level.SEVERE, null, ex);
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
              
              
       }
       
       public class Writer extends CDLList<T>.Writer{
           public Writer(Element node){
               super(node);
           }
           
           @Override
           public boolean insertBefore(T val){
               try {
                   rw.lockWrite();
               } catch (InterruptedException ex) {
                   Logger.getLogger(CDLFineRW.class.getName()).log(Level.SEVERE, null, ex);
               }
               try{
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
               }finally{
                       rw.unlockWrite();
                             }
           }
           
           @Override
           public boolean insertAfter(T val){
               try {
                   rw.lockWrite();
               } catch (InterruptedException ex) {
                   Logger.getLogger(CDLFineRW.class.getName()).log(Level.SEVERE, null, ex);
               }
               try{
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
           }finally{
                   
                       rw.unlockWrite();
                   
               }
         }
       }
}
 
      
 
