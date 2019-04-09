package main;

import java.util.EmptyStackException;

public class IntDeque {

	private static final int DEFAULT_CAPACITY = 15;

	private int[] _elements;

	private int _size = 0;

	public IntDeque() {
		_elements = new int[DEFAULT_CAPACITY];
	}

	public IntDeque(int initialCapacity) {
		_elements = new int[initialCapacity];
	}

	public int size() {
		return _size;
	}

	public boolean isEmpty() {
		return _size <= 0;
	}

	public int pop() {
		if (_elements.length == 0) {
			throw new EmptyStackException();
		}
		return _elements[--_size];
	}

	public void push(int element) {
		ensureCapacity(_size + 1);
		_elements[_size++] = element;
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity >= _elements.length) {
			int neededCapacity = minCapacity;

			// 1.5 greater than previous capacity
			int newCapacity = neededCapacity + (neededCapacity >> 1);
			int[] temp = new int[newCapacity];

			System.arraycopy(_elements, 0, temp, 0, _elements.length);
			_elements = temp;
		}
	}
	
	public void enqueue(int element) {
		ensureCapacity(_size + 1);
		_elements[_size] = element;
		_size++;
	}
	
	public int dequeue() {
		int value = _elements[0];
		// shift everything to the left
		int end = _size - 1;
		for (int i = 0; i < end; i++) {
			_elements[i] = _elements[i + 1];
		}
		_size--;
		return value;
	}
	
	public int peekFirst() {
		return _elements[0];
	}
	
	public int peekLast() {
		return _elements[_size - 1];
	}

	@Override
	public String toString() {
		final int[] elements = _elements;
		final int size = _size - 1;
		int i;

		StringBuilder sb = new StringBuilder(elements.length << 1);
		sb.append("[");

		for (i = 0; i < size; i++) {
			sb.append(String.format("%d,", elements[i]));
		}

		sb.append(String.format("%s]", elements[i]));

		return sb.toString();
	}
}
