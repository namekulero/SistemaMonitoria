package com.Clases.Estructuras.interfaces.queue;

public interface QueueInterface<T> {
    public boolean clear();

    public boolean isEmpty();

    public T peek();

    public T extract();

    public boolean insert(T object);

    public int size();

    public boolean search(T object);

    public boolean sort();

    public boolean reverse();

    public String toString();
}
