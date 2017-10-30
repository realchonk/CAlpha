package de.longcity.interpreter.type;

import include.nativelib.memory;

public class heap_pointer_t extends pointer_t {
	public heap_pointer_t() {
		super();
	}
	public heap_pointer_t(int value) {
		super(value);
	}
	@Override
	public Object getReference() {
		return memory.heap.get(value);
	}
	@Override
	public void setReference(Object obj) {
		memory.heap.set(value, obj);
	}
}
