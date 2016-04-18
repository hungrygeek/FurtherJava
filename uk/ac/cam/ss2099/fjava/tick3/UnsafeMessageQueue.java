package uk.ac.cam.ss2099.fjava.tick3;

public class UnsafeMessageQueue<T> implements MessageQueue<T> {
	private static class Link<L> {
		L val;
		Link<L> next;
		Link(L val) { this.val = val; this.next = null; }
	}
	private Link<T> first = null;
	private Link<T> last = null;

	public void put(T val) {
		Link<T> new_Link = new Link<T>(val);
		if (first == null){
			first = new_Link;
			last = first;
		} else {
			last.next = new_Link;
			last = new_Link;
		}
	}

	public T take() {
		while(first == null) //use a loop to block thread until data is available
			try {Thread.sleep(100);} catch(InterruptedException ie) {}
		T val = first.val;
		first = first.next;
		return val;
	}
}
