package com.Clases.Estructuras.interfaces.stack;

public interface StackInterface<T> {
    public boolean clear();

    public boolean isEmpty();

    public T peek();

    public T pop();

    public boolean push(T object);

    public int size();

    public boolean search(T object);

    public boolean sort();

    public boolean reverse();

    public String toString();
}
