package messaging;

import java.util.function.Consumer;

/*
This file is copied from the "Correlation Student Registration Example" zip file.
Created by Hubert Baumeister.
Accessed on 2023-01-11
 */
public interface MessageQueue {

	void publish(Event message);
	void addHandler(String eventType, Consumer<Event> handler);

}
