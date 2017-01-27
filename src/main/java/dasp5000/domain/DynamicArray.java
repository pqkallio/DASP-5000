
package dasp5000.domain;

import java.lang.reflect.Array;

/**
 *
 * @author petrikallio
 */
public class DynamicArray<T> {
    private T[] array;
    private int size;
    private final Class<T> objectClass;
    
    public DynamicArray(Class<T> objectClass) {
        this.size = 0;
        this.objectClass = objectClass;
        this.array = (T[])Array.newInstance(objectClass, 64);
        System.out.println(this.array.length);
    }
    
    public void add(T object) {
        array[size] = object;
        size++;
        
        if (size > array.length * 0.75) {
            increaseArrayLength();
        } else if (size < array.length * 0.25) {
            decreaseArrayLength();
        }
    }
    
    public T get(int index) {
        return array[index];
    }
    
    public int size() {
        return size;
    }

    private void increaseArrayLength() {
        T[] newArray = (T[])Array.newInstance(objectClass, array.length * 2);
        copyArray(newArray, array);
        this.array = newArray;
    }

    private void decreaseArrayLength() {
        T[] newArray = (T[])Array.newInstance(objectClass, array.length / 2);
        copyArray(newArray, array);
        this.array = newArray;
    }

    private void copyArray(T[] to, T[] from) {
        for (int i = 0; i < size; i++) {
            to[i] = from[i];
        }
    }
}
