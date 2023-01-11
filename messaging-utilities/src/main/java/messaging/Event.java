package messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Event implements Serializable {

	private static final long serialVersionUID = 4986172999588690076L;
	private String type;
	private Object[] arguments = null;
	
	public Event() {};
	public Event(String topic, Object[] arguments) {
		this.type = topic;
		this.arguments = arguments;
	}
	
	public Event(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	private Object[] getArguments() {
		return arguments;
	}
	
	public <T> T getArgument(int i, Class<T> cls) {
		// The hack is needed because of Events are converted
		// to JSon for transport. Because JSon does not store
		// the class of an Object, when deserializing the arguments
		// of an Event, LinkedTreeLists are returned, which cannot be 
		// cast to real objects or converted to JSonObjects.
		// The trick is to generated a JSon string from the argument and 
		// then parse that string back to the class one needs.
		// This also works, for tests, where the arguments to an Event contain
		// the original objects.
		var gson = new Gson();
		var jsonString = gson.toJson(arguments[i]);
		return gson.fromJson(jsonString, cls);
	}

	public boolean equals(Object o) {
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		Event other = (Event) o;
		return this.type.equals(other.type) &&
				(this.getArguments() != null &&
				Arrays.equals(getArguments(),other.getArguments())) ||
				(this.getArguments() == null && other.getArguments() == null);
	}
	
	public int hashCode() {
		return type.hashCode();
	}
	
	public String toString() {
		List<String> strs = new ArrayList<>();
		if (arguments != null) {
			List<Object> objs = Arrays.asList(arguments);
			strs = objs.stream().map(o -> o.toString()).collect(Collectors.toList());
		}
		
		return String.format("event(%s,%s)", type,String.join(",", strs));
	}
}
