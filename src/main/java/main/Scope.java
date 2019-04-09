package main;

import java.util.LinkedList;
import java.util.List;

public class Scope {

	private List<Scope> _children = new LinkedList<>();
	private int _depth;
	private int _start;
	private int _end;

	/**
	 * Initialize the starting line number of this scope.
	 * 
	 * @param start the starting line number.
	 */
	public Scope(int start) {
		_start = start;
	}

	public Scope(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("start > end");
		}
		_start = start;
		_end = end;
	}

	public int getStart() {
		return _start;
	}
	
	public int getDepth() {
		return _depth;
	}
	
	public void setDepth(int depth) {
		_depth = depth;
	}

	public int getEnd() {
		return _end;
	}

	public void setEnd(int lineNumber) {
		_end = lineNumber;
	}

	public List<Scope> getChildren() {
		return _children;
	}

	public boolean addChild(Scope element) {
		return _children.add(element);
	}

	public Scope getChild(int index) {
		return _children.get(index);
	}

	public boolean hasChildren() {
		return !_children.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _end;
		result = prime * result + _start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scope other = (Scope) obj;
		return _end == other._end && _start == other._start;
	}

	@Override
	public String toString() {
		return String.format("[%d,%d], depth=%d, children=%d", _start, _end, _depth, _children.size());
	}

}
