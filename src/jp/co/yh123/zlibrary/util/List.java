package src.jp.co.yh123.zlibrary.util;

import java.util.Vector;

public class List extends ArrayList {

	public List(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	public List() {
		super(100);
		// TODO Auto-generated constructor stub
	}

	public synchronized void add(Object obj) {
		// TODO Auto-generated method stub
		super.add(obj);
	}

	public int capacity() {
		// TODO Auto-generated method stub
		return super.capacity();
	}

	public boolean contains(Object elem) {
		for (int i = 0; i < size(); i++) {
			if (elem == elementAt(i)) {
				return true;
			}
		}
		return false;
	}

	public synchronized void copyInto(Object[] anArray) {
		// TODO Auto-generated method stub
		super.copyInto(anArray);
	}

	public synchronized Object elementAt(int arg0) {
		// TODO Auto-generated method stub
		if (size() > arg0) {
			return super.elementAt(arg0);
		} else {
			return null;
		}
	}

	public synchronized void copyList(List destList) {
		for (int i = 0; i < size(); i++) {
			destList.add(elementAt(i));
		}
	}

	public synchronized double elementAtDouble(int arg0) {
		// TODO Auto-generated method stub
		return Double.parseDouble((String) super.elementAt(arg0));
	}

	public synchronized int elementAtInt(int arg0) {
		// TODO Auto-generated method stub
		return Integer.parseInt((String) super.elementAt(arg0));
	}

	public synchronized String elementAtString(int arg0) {
		// TODO Auto-generated method stub
		return (String) super.elementAt(arg0);
	}

	public synchronized void ensureCapacity(int minCapacity) {
		// TODO Auto-generated method stub
		super.ensureCapacity(minCapacity);
	}

	public synchronized Object firstElement() {
		// TODO Auto-generated method stub
		return super.firstElement();
	}

	public synchronized int indexOf(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return super.indexOf(arg0, arg1);
	}

	public int indexOf(Object elem) {
		// TODO Auto-generated method stub
		return super.indexOf(elem);
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	public synchronized Object lastElement() {
		// TODO Auto-generated method stub
		return super.lastElement();
	}

	public synchronized int lastIndexOf(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return super.lastIndexOf(arg0, arg1);
	}

	public int lastIndexOf(Object elem) {
		// TODO Auto-generated method stub
		return super.lastIndexOf(elem);
	}

	public synchronized void removeAllElements() {
		// TODO Auto-generated method stub
		super.removeAllElements();
	}

	public synchronized void removeLast() {
		// TODO Auto-generated method stub
		super.removeElementAt(super.size() - 1);
	}

	public Object getLast() {
		// TODO Auto-generated method stub
		return super.elementAt(super.size() - 1);
	}

	public Object getFirst() {
		// TODO Auto-generated method stub
		return super.elementAt(0);
	}

	public synchronized boolean removeElement(Object obj) {
		// TODO Auto-generated method stub
		return super.removeElement(obj);
	}

	public synchronized Object removeElementAt(int index) {
		// TODO Auto-generated method stub
		return super.removeElementAt(index);
	}

	public synchronized void setElementAt(Object obj, int index) {
		// TODO Auto-generated method stub
		super.setElementAt(obj, index);
	}

	public synchronized void setSize(int arg0) {
		// TODO Auto-generated method stub
		super.setSize(arg0);
	}

	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	private void add(int index, Object o) {
		super.insertElementAt(index, o);
	}

	public synchronized void sort(ListComparator c) throws Exception {

		Vector v = new Vector();
		for (int i = 0; i < size(); i++) {
			v.add(elementAt(i));
		}

		Object tmp;
		if (size() <= 1)
			return;
		for (int i = 0; i < v.size(); i++) {
			tmp = v.remove(0);
			for (int j = 0; j < v.size(); j++) {
				if (c.compare(tmp, v.elementAt(j)) == false) {
					v.add(j, tmp);
					tmp = v.remove(j + 1);
				}
			}
			v.add(v.size(), tmp);
		}

		removeAllElements();

		for (int i = 0; i < v.size(); i++) {
			add(v.elementAt(i));
		}

	}

}
