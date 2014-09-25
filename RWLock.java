/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import java.util.concurrent.*;
/**
 *
 * @author Ankit
 */
//import java.util.concurrent.atomic.AtomicInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ankit
 */
//   Hint taken from java2s.com




public class RWLock{

    private static final int wt = 5000;
    private ThreadLocal rlt;
    private int r;
    private int w;
    private Thread wlt;
    private static class Count
    {
        public int val = 0;
    }


    public RWLock()
    {
        rlt = new ThreadLocal()
            {
                public Object initialValue()
                {
                    return new Count();
                }
            };

        r= 0;
        w= 0;
        wlt= null;
    }


    
    public synchronized void lockRead() throws InterruptedException
    {
        Thread me = Thread.currentThread();
        Count mrl = (Count)rlt.get();

        if (wlt != me)
        {
            while (w > 0)
            {
                wait(wt);

                if (w > 0)
                    System.out.println("Still waiting for read lock on ");
            }
        }

        ++r;
        ++mrl.val;
    }


   
    public synchronized void lockWrite() throws InterruptedException
    {
        Thread me = Thread.currentThread();
        Count mrl= (Count)rlt.get();

        if (mrl.val > 0)
            throw new IllegalStateException("Thread already holds a read lock");

        if (wlt != me)
        {
            while (w > 0 || r > 0)
            {
                wait(wt);

                if (w > 0 || r > 0)
                    System.out.println("Still waiting for write lock on ");
            }

            wlt = me;
        }

        ++w;
    }


        public synchronized void unlockRead()
    {
        Thread me = Thread.currentThread();
        Count mrl= (Count)rlt.get();

        if (mrl.val > 0)
        {
            --mrl.val;
            --r;
        }
        notifyAll();
    }
public synchronized void unlockWrite(){        
        Thread me = Thread.currentThread();
        Count mrl = (Count)rlt.get();

         if (wlt == me)
        {
            if (w > 0)
            {
                if (--w == 0){
                    wlt = null;
                }
            }
        }

        notifyAll();
    }


    public String toString()
    {
        StringBuffer s = new StringBuffer(super.toString());

        s.append(": readLocks = ").append(r)
         .append(", writeLocks = ").append(w)
         .append(", writeLockedBy = ").append(wlt);

        return s.toString();
    }
}