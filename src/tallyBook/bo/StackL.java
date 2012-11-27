package tallyBook.bo;

import java.util.LinkedList;

public class StackL {
	private LinkedList list = new LinkedList();

	public void push(Object o) {
		list.addFirst(o);
	}

	public Object top() {
		return list.getFirst();
	}

	public Object pop() {
		return list.removeFirst();
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
}
