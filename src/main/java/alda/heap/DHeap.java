/*
Gruppmedlemmar:
Tobias Ahnhem toah5501
Daniel Andersson daan2233
Eric Egan ereg8941
 */

package alda.heap;

public class DHeap<AnyType extends Comparable<? super AnyType>> {
    private static final int DEFAULT_ARY = 2;
    private static final int ARRAY_MULTIPLIER = 2;
    private int ary = 0;
    private int indexOfLastElement;      // Number of elements in heap
    private AnyType[] internalArray;

    public DHeap() {
        this(DEFAULT_ARY);
    }

    public DHeap(int ary) {
        if (ary < 2) {
            throw new IllegalArgumentException();
        } else {
            indexOfLastElement = 0;
            internalArray = (AnyType[]) new Comparable[ary];
            this.ary = ary;
        }
    }


    public void insert(AnyType newElement) {

        indexOfLastElement++;

        if (arrayIsFull()) {
            enlargeArray();
        }

        int parentIndex = getParentIndex(indexOfLastElement);

        int childIndex = indexOfLastElement;

        internalArray[childIndex] = newElement;

        perculateUp(parentIndex, childIndex);
    }

    private boolean arrayIsFull() {
        return indexOfLastElement == internalArray.length - 1;
    }

    private void perculateUp(int parentIndex, int childIndex) {
        while (parentIndex >= 1 && internalArray[parentIndex].compareTo(internalArray[childIndex]) > 0) {
            swapElement(parentIndex, childIndex);
            childIndex = parentIndex;
            parentIndex = getParentIndex(childIndex);
        }
    }

    private void swapElement(int parent, int child) {
        AnyType tmp = internalArray[parent];
        internalArray[parent] = internalArray[child];
        internalArray[child] = tmp;
    }

    private int getParentIndex(int childIndex) {
        return (childIndex > 1) ? parentIndex(childIndex) : 1;
    }

    /**
     * Calculates the index of the first child of the parent node
     *
     * @param parentIndex the index of the node whose first child you want to find
     * @return childIndex the index of the first child to the parent
     * @throws IllegalArgumentException if parentIndex < 1
     */

    public int firstChildIndex(int parentIndex) {
        if (parentIndex < 1) {
            throw new IllegalArgumentException();
        }
        return ((parentIndex - 1) * ary) + 2;

    }

    private int getLastChild(int firstChild) {
        return ((firstChild + ary - 1) < indexOfLastElement) ? (firstChild + ary - 1) : indexOfLastElement;
    }


    public int parentIndex(int childIndex) {
        if (childIndex <= 1) {
            throw new IllegalArgumentException();
        }

        return (childIndex + ary - 2) / ary;
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new UnderflowException("isEmpty");
        }

        return internalArray[1];
    }

    public AnyType deleteMin() {
        if (isEmpty())
            throw new UnderflowException("isEmpty");

        AnyType minItem = findMin();
        internalArray[1] = internalArray[indexOfLastElement];
        internalArray[indexOfLastElement] = null;
        indexOfLastElement--;

        if (indexOfLastElement > 1) {
            percolateDown(1);
        }

        return minItem;
    }

    private void percolateDown(int start) {

        boolean shouldPerculateDown = false;

        int firstChild = firstChildIndex(start);

        int lastChild = getLastChild(firstChild);

        int indexOfMovedElement = start;

        for (int i = firstChild; i <= lastChild; i++) {
            if (internalArray[i].compareTo(internalArray[indexOfMovedElement]) < 0) {
                shouldPerculateDown = true;
                indexOfMovedElement = i;
            }
        }

        if (shouldPerculateDown) {
            swapElement(indexOfMovedElement, start);
            percolateDown(indexOfMovedElement);
        }
    }


    public boolean isEmpty() {
        return indexOfLastElement == 0;
    }

    public int size() {
        return indexOfLastElement;
    }

    public AnyType get(int index) {
        return internalArray[index];
    }


    private void enlargeArray() {
        AnyType[] oldInternalArray = internalArray;
        internalArray = (AnyType[]) new Comparable[internalArray.length * ARRAY_MULTIPLIER];
        System.arraycopy(oldInternalArray, 0, internalArray, 0, oldInternalArray.length);

    }


    public void makeEmpty() {
        indexOfLastElement = 0;
    }


    public static void main(String[] args) {

    }

}
