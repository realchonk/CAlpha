package include.nativelib;

import java.util.LinkedList;

import de.longcity.interpreter.IncludeLib;
import de.longcity.interpreter.type.heap_pointer_t;
import de.longcity.interpreter.type.pointer_t;

public class memory extends IncludeLib {
	public static final LinkedList<Object> heap = new LinkedList<>();
	@Override
	protected String getNameSpace() {
		return "";
	}
	@Override
	protected void init() {
		super.init();
		heap.clear();
	}
	@NativeFunction(name = "malloc")
	public heap_pointer_t malloc() {
		heap_pointer_t ptr = new heap_pointer_t(heap.size());
		heap.add(nullptr.getReference());
		return ptr;
	}
	@NativeFunction(name = "free", paramtypes = "pointer_t")
	public void free(pointer_t ptr) {
		System.err.println("Unsupported");
	}
}
