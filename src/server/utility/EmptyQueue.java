package server.utility;


public class EmptyQueue extends Queue {
	/**
	 * 
	 * @author Angelo, Simone, Antonio
	 * Caso in cui la coda è vuota
	 *
	 */
  public class EmptyQueueException extends RuntimeException{}
  
  //Overriding
  public void dequeue(){
    try{
      throw new EmptyQueueException();
    }
    catch (EmptyQueueException e){
      throw e;
    }
  } 
}