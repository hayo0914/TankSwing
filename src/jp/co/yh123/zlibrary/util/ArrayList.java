package src.jp.co.yh123.zlibrary.util;

import java.util.NoSuchElementException;

/**
 * 高速な挿入・削除を行う動的配列クラス
 * 
 */
public class ArrayList {
	protected int capacityIncrement;

	protected int elementCount;

	protected Object[] elementData;

	// private static final int INITIAL_SIZE = 10;

	private static final int EXTEND_SIZE = 10;

	// protected ArrayList() {
	// elementData = new Object[INITIAL_SIZE];
	// capacityIncrement = EXTEND_SIZE;
	// }

	public ArrayList(int size) {
		elementData = new Object[size];
		// capacityIncrement = size >> 1;
		capacityIncrement = EXTEND_SIZE;
	}

	// public ArrayList(int size, int next) {
	// elementData = new Object[size];
	// capacityIncrement = next;
	// }

	private void reAlloc(int size) {
		Object[] swap = new Object[elementData.length];
		swap = elementData;
		elementData = new Object[size];
		int times = swap.length < size ? swap.length : size;
		for (int i = 0; i < times; i++) {
			elementData[i] = swap[i];
		}
		swap = null;
		System.gc();
	}

	public void copyInto(Object[] objs) {
		for (int i = 0; i < elementData.length; i++) {
			objs[i] = elementData[i];
		}
	}

	public void trimToSize() {
		reAlloc(elementCount);
	}

	public void ensureCapacity(int min) {
		if (size() < elementCount + min) {
			reAlloc(elementCount + min);
		}
	}

	public void setSize(int size) {
		reAlloc(size);
	}

	public int capacity() {
		return elementData.length;
	}

	public int size() {
		return elementCount;
	}

	public synchronized boolean isEmpty() {
		return elementCount == 0 ? true : false;
	}

	// public synchronized boolean contains(Object element) {
	// for (int i = 0; i < elementData.length; i++) {
	// if (elementData[i].equals(element)) {
	// return true;
	// }
	// }
	// return false;
	// }

	public synchronized int indexOf(Object element) {
		for (int i = 0; i < elementData.length; i++) {
			if (elementData[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized int indexOf(Object element, int index) {
		for (int i = index; i < elementData.length; i++) {
			if (elementData[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized int lastIndexOf(Object element) {
		for (int i = elementData.length - 1; 0 <= i; i--) {
			if (elementData[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized int lastIndexOf(Object element, int index) {
		for (int i = index - 1; 0 <= i; i--) {
			if (elementData[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized Object elementAt(int index) {
		if (elementData[index] != null) {
			return elementData[index];
		} else {
			throw new NoSuchElementException();
		}
	}

	public synchronized Object firstElement() {
		return elementAt(0);
	}

	public synchronized Object lastElement() {
		return elementAt(size() - 1);
	}

	public synchronized void setElementAt(Object element, int index) {
		elementData[index] = element;
	}

	public synchronized void insertElementAt(int index, Object element) {

		if (elementCount == elementData.length) {
			reAlloc(elementData.length + capacityIncrement);
		}

		for (int i = elementCount - 1; i >= index; i--) {
			setElementAt(elementAt(i), i + 1);
		}

		setElementAt(element, index);
		elementCount++;

	}

	public synchronized Object removeElementAt(int index) {
		Object obj = elementData[index];
		elementData[index] = elementData[--elementCount];
		return obj;
	}

	public synchronized void add(Object element) {
		if (elementCount < elementData.length) {
			elementData[elementCount++] = element;
		} else {
			reAlloc(elementData.length + capacityIncrement);
			add(element);
		}
	}

	public synchronized boolean removeElement(Object element) {
		int count = 0;
		for (int i = 0; i < elementData.length; i++) {
			if (elementData[i].equals(element)) {
				removeElementAt(i);
				i--;
				count++;
				break;
			}
		}
		return count != 0 ? true : false;
	}

	public synchronized void removeAllElements() {
		elementCount = 0;
	}

}
