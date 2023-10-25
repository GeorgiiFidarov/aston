package ru.fidarov.customJava;

import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    //само-расширяемый лист
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Wrong Capacity definition");
        }
        data = new Object[initialCapacity];
    }

    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
    }

    public void add(E e) {
        ensureCapacity(size + 1);
        data[size++] = e;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    public void addAll(Collection<? extends E> c) {
        ensureCapacity(size + c.size());
        for (E element : c) {
            data[size++] = element;
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return (E) data[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
    }

    public void remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                return;
            }
        }
    }



    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length + 1, minCapacity);
            data = copyOf(data, newCapacity);
        }
    }

    public void sort(Comparator<? super E> c) {
        if (size > 1) {
            Object[] temp = new Object[size];
            mergeSort(c, temp, 0, size - 1);
        }
    }

    private void mergeSort(Comparator<? super E> c, Object[] temp, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(c, temp, left, middle);
            mergeSort(c, temp, middle + 1, right);
            merge(c, temp, left, middle, right);
        }
    }

    private void merge(Comparator<? super E> c, Object[] temp, int left, int middle, int right) {
        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (c.compare((E) data[i], (E) data[j]) <= 0) {
                temp[k++] = data[i++];
            } else {
                temp[k++] = data[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = data[i++];
        }

        while (j <= right) {
            temp[k++] = data[j++];
        }

        for (k = left; k <= right; k++) {
            data[k] = temp[k];
        }
    }

    private Object[] copyOf(Object[] original, int newLength) {
        Object[] copy = new Object[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public int size() {
        return size;
    }
}
