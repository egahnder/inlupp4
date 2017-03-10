/*
Gruppmedlemmar:
Tobias Ahnhem toah5501
Daniel Andersson daan2233
Eric Egan ereg8941
 */

package alda.heap;

public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private static final int DEFAULT_ARY = 2;
    private int ary = 0;
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array;

    public DHeap( )
    {
        this(DEFAULT_ARY);
    }

    public DHeap(int ary)
    {
        if(ary < 2){
            throw new IllegalArgumentException();
        }
        else{
            currentSize = 0;
            array = (AnyType[]) new Comparable[11];
            this.ary = ary;
        }
    }


    public void insert( AnyType x ) {

        currentSize++;
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );


        int parentIndex = (currentSize > 1) ? parentIndex(currentSize) : 1;
        int childIndex = currentSize;
        array[childIndex] = x;
        while(parentIndex >= 1 && array[parentIndex].compareTo(array[childIndex]) > 0){
            swap(parentIndex, childIndex);
            childIndex = parentIndex;
            parentIndex = (childIndex + ary - 2)/ary;
        }
    }

    /**
     * Calculates the index of the first child of the parent node
     *
     * @param parentIndex the index of the node whose first child you want to find
     * @return childIndex the index of the first child to the parent
     * @throws IllegalArgumentException if parentIndex < 1
     */

    public int firstChildIndex(int parentIndex){
        if(parentIndex < 1){
            throw new IllegalArgumentException();
        }
       return ((parentIndex - 1) * ary) + 2;

    }


    public int parentIndex(int childIndex){
        if(childIndex <= 1){
            throw new IllegalArgumentException();
        }

        return (childIndex + ary - 2)/ary;
    }

    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("isEmpty" );
        return array[1];
    }

    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("isEmpty" );

        AnyType minItem = findMin();
        array[1] = array[currentSize];
        array[currentSize] = null;
        currentSize--;
        if(currentSize > 1){
            percolateDown(1);
        }
        return minItem;
    }

    private void percolateDown(int startIndex){
        //int firstChildIndex = startIndex*ary+1;
        //int firstChildIndex = ((startIndex - 1) * ary) + 2;
        int firstChildIndex = firstChildIndex(startIndex);

        //int lastChildIndex = (startIndex*ary + ary < currentSize) ?
          //      startIndex*ary + ary : currentSize-1;
        int lastChildIndex = ((firstChildIndex + ary - 1) < currentSize) ? (firstChildIndex + ary - 1) : currentSize;

        int smallest = startIndex;
        for(int i = firstChildIndex; i <= lastChildIndex; i++){
            if(array[i].compareTo(array[smallest]) < 0){
                smallest = i;
            }
        }
        if(array[smallest].compareTo(array[startIndex]) < 0){
            swap(smallest, startIndex);
            percolateDown(smallest);
        }
    }


    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    public int size(){
        return currentSize;
    }

    public AnyType get(int index){ return array[index]; }

    private void swap(int first, int second){
        AnyType tmp = array[first];
        array[first] = array[second];
        array[second] = tmp;
    }

    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 1; i < old.length; i++ )
            array[ i ] = old[ i ];
    }



    public void makeEmpty( )
    {
        currentSize = 0;
    }



    public static void main( String [ ] args ) {
    }
}
