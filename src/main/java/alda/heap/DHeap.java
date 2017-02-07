// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
package alda.heap;

//DHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */


public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private static final int DEFAULT_ARY = 2;
    private int ary = 0;
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array

    /**
     * Construct the binary heap.
     */
    public DHeap( )
    {
        this(DEFAULT_ARY);
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public DHeap(int ary)
    {
        if(ary < 2){
            throw new IllegalArgumentException();
        }
        else{
            currentSize = 0;
            array = (AnyType[]) new Comparable[ary+1];
            this.ary = ary;
        }
    }

    // Test program
    public static void main( String [ ] args )
    {
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x ) {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

        int parentIndex = (currentSize-1)/ary;
        int childIndex = currentSize;
        array[childIndex] = x;
        while(parentIndex >= 0 && array[parentIndex].compareTo(array[childIndex]) > 0){
            swap(parentIndex, childIndex);
            childIndex = parentIndex;
            parentIndex = (childIndex-1)/ary;
        }
        currentSize++;
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("isEmpty" );
        return array[0];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("isEmpty" );

        AnyType minItem = findMin();
        array[0] = array[currentSize-1];
        currentSize--;
        if(currentSize > 0){
            percolateDown(0);
        }
        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    public int size(){
        return currentSize;
    }

    public AnyType get(int index){ return array[index]; }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    private void swap(int first, int second){
        AnyType tmp = array[first];
        array[first] = array[second];
        array[second] = tmp;
    }

    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int startIndex){
        int firstChildIndex = startIndex*ary+1;
        int lastChildIndex = (startIndex*ary + ary < currentSize) ?
                startIndex*ary + ary : currentSize-1;
        int smallest = startIndex;
        for(int i = firstChildIndex; i < lastChildIndex; i++){
            if(array[i].compareTo(array[smallest]) < 0){
                smallest = i;
            }
        }
        if(array[smallest].compareTo(array[startIndex]) < 0){
            swap(smallest, startIndex);
            percolateDown(smallest);
        }
    }
}
