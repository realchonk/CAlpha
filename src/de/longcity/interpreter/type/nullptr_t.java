package de.longcity.interpreter.type;

import java.io.Closeable;
import java.io.Serializable;

public class nullptr_t extends pointer_t implements Closeable, Serializable {
	private static final long serialVersionUID = -7839264432659582024L;
	public static final nullptr_t nullptr = new nullptr_t();
	private nullptr_t() {
		super(-1);
	}
	@Override
	public boolean equals(Object obj) {
		return obj == null || obj == this;
	}
	@Override
	public void close() {}
	@Override
	public Object getReference() {
		return this;
	}
	@Override
	public nullptr_t clone() {
		return this;
	}
}
