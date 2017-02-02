
package dasp5000.domain;

import java.lang.reflect.Array;

/**
 * A dynamic and generic array to store data into. 
 * 
 * @author Petri Kallio
 */
public class DynamicArray<T> {
    private T[] array;
    private int size;
    private final Class<T> objectClass;
    
    /**
     * A constructor that creates a class-specific DynamicArray.
     * 
     * @param objectClass the class of the objects to store in the DynamicArray
     */
    public DynamicArray(Class<T> objectClass) {
        this.size = 0;
        this.objectClass = objectClass;
        this.array = (T[])Array.newInstance(objectClass, 64);
    }
    
    /**
     * Adds the object given as parameter to the DynamicArray
     * 
     * @param object 
     */
    public void add(T object) {
        array[size] = object;
        size++;
        
        if (size > array.length * 0.75) {
            increaseArrayLength();
        } else if (array.length > 64 && size < array.length * 0.25) {
            decreaseArrayLength();
        }
    }
    
    /**
     * Gets the object at the given index of the DynamicArray
     * 
     * @param index
     * @return 
     */
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }
    
    public void replace(int index, T value) {
        checkIndex(index);
        array[index] = value;
    }
    
    /**
     * Returns the current amount of objects in the DynamicArray
     * 
     * @return 
     */
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
