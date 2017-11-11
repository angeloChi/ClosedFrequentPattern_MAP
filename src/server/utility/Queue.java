package server.utility;



/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Struttura che contiene in modo FIFO pattern frequenti a livello k 
 * da usare per generare i pattern frequenti a livello k+1
 * 
 *
 * @param <T>
 */
public class Queue<T> {

		private Record begin = null;

		private Record end = null;
		
		private class Record {

	 		public T elem;

	 		public Record next;

			public Record(T e) {
				this.elem = e; 
				this.next = null;
			}
		}
		

		public boolean isEmpty() {
			return this.begin == null;
		}

		public void enqueue(T e) {
			if (this.isEmpty())
				this.begin = this.end = new Record(e);
			else {
				this.end.next = new Record(e);
				this.end = this.end.next;
			}
		}


		public T first(){
			return this.begin.elem;
		}

		public void dequeue(){
			if(this.begin==this.end){
				if(this.begin==null)
				System.out.println("The queue is empty!");
				else
					this.begin=this.end=null;
			}
			else{
				begin=begin.next;
			}
			
		}

	}