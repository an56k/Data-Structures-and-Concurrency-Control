/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ankit
 */
public class CDLList<T>{

    /**
     * @param args the command line arguments
     */
   
     T vl;
     Element head;
     public CDLList(T v){
        vl=v;
        head=new Element(head, vl,head);
        head.value(); 
        head.prev=head.next=head;
    }
      public class Element{
        Element next, prev;
        T val;
        
        public Element(Element prev, T val, Element next){
            this.prev=prev;
            this.val=val;
            this.next=next;
        }
        public T value(){
            //val=vl;
            return val;
        }
    }  
        public Element head(){
           return head; 
        }
        public Cursor reader(Element from){
            return new Cursor(from);
        }
        public class Cursor{
            Element E;
            public Cursor(Element E){
                this.E=E;
            }
          public Element current(){
               if(E==null) {
                    throw new IllegalStateException("List is Empty.");
                }
                else {
                    
                    return E;
                    
                }
        }
          public void previous(){
             E=(Element) E.prev;
          }
          public void next(){
              E=(Element) E.next;
          }
          public Writer writer(){
              Writer write = new Writer(this.current());
                return write;
          }
    }
        public class Writer{
            Element node;
            public Writer(Element node){
                this.node=node;
            }
            public boolean insertBefore(T val){
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
            public boolean insertAfter(T val){
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
    public static void main(String[] args) {
        // TODO code application logic here
            CDLList<String> f; 
            CDLList<String>.Cursor c;
            f = new CDLList<>("Hii");
            c = f.reader( f.head() );
            c.next();
            if(f.head() == c.current())
                {
                    System.out.println("Circular List\n");
                }
            System.out.println(c.writer().insertBefore("bye"));
            System.out.println(c.writer().insertBefore("hello"));
            System.out.println(c.writer().insertAfter("up"));
            f.print();
    }
    
    
    
    public void print(){
        CDLList<T> lst = this;
        CDLList<T>.Cursor c = this.reader(this.head());
        c.next();
        while(!(c.current() == this.head())){
            System.out.println(c.current().value());
            c.next();
        }
    }
}
