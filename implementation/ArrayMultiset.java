package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Array implementation of a multiset. See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset {
    /* Memory allocation for Dynamic Array*/
    String[] elements;
    int[] frequency;
    int count=0;

    public ArrayMultiset() {
        elements = new String[3000];
        frequency = new int[3000];
    }
    /*   Adding elements to Array */
    @Override
    public void add(String elem) {
        count++;
        if(count == elements.length )
        {
            String[] elementsTemp = new String[elements.length * 2];
            int[] frequencyTemp = new int[frequency.length * 2];

            for(int i=0;i<elements.length;i++)
            {
                elementsTemp[i] = elements[i];
                frequencyTemp[i] = frequency[i];
            }
            elements = elementsTemp;
            frequency = frequencyTemp;
        }
        if (elements[0] == null) {
            elements[0] = elem;
            frequency[0] = 1;
        } else {
            boolean found = false;
            int i = 0;
            while (elements[i] != null) {
                if (elements[i].equals(elem)) {
                    frequency[i] += 1;
                    found = true;
                    break;
                }
                i++;
            }
            if (!found) {
                elements[i] = elem;
                frequency[i] = 1;
            }

        }
    }
    /* Search an element in Array */
    @Override
    public int search(String elem) {

        int i = 0;
        boolean found = false;
        while (elements[i] != null) {
            if (elements[i].equals(elem)) {
                i = frequency[i];
                found = true;
                break;
            }
            i++;
        }
        if (found) {
            return i;
        }
        return searchFailed;
    } // end of search()
    /* Search the elements by Instances in Array */

    @Override
    public List<String> searchByInstance(int instanceCount) {

        List<String> found = new ArrayList<String>();
        int i = 0;
        boolean foundFrequncy = false;
        while (frequency[i] != 0) {
            if (frequency[i] == instanceCount) {
                found.add(elements[i]);
                foundFrequncy = true;
            }
            i++;
        }
        if (foundFrequncy) {
            return found;
        }
        return null;
    } // end of searchByInstance
    /*  Check whether the element is contained in the array */
    @Override
    public boolean contains(String elem) {
        int i = 0;
        while (elements[i] != null) {
            if (elements[i].equals(elem)) {
                return true;
            }
            i++;

        }
        return false;
    } // end of contains()
    /* Remove a element from array */
    @Override
    public void removeOne(String elem) {
        int i = 0;
        while (elements[i] != null) {
            if (elements[i].equals(elem)) {
                if (frequency[i] > 1) {
                    frequency[i] -= 1;
                } else {
                    frequency[i] = 0;
                    elements[i] = null;
                }
                break;
            }
            i++;
        }

    } // end of removeOne()
    /*    print the elements added in the array */
    @Override
    public String print() {
        Set<String> outPut=new TreeSet<>();
        String outPutString = "";
        for (int j = 0; j < elements.length; j++) {
            if (elements[j] != null) {
                outPut.add(elements[j] + " : " + String.valueOf(frequency[j]));
            }
        }
        for(String out:outPut)
        {
            outPutString+=out+"\n";
        }
        return outPutString;
    } // end of OrderedPrint
    /* Print range : lower to high range of elements in array multisets */
    @Override
    public String printRange(String lower, String upper) {

        String elems = "";
        int i = 0;
        while (elements[i] != null) {

            if (elements[i].compareTo(lower) >= 0 && elements[i].compareTo(upper) <= 0) {
                elems += elements[i] + " : "+ frequency[i] + "\n";
            }
            i++;
        }
        return elems;
    } // end of printRange()
    /* Union of  different array multisets */
    @Override
    public RmitMultiset union(RmitMultiset other) {
        int endPoint = 0;
        boolean matchFound = false;
        ArrayMultiset otherMultiSet = (ArrayMultiset) other;
        ArrayMultiset union = new ArrayMultiset();
        for (int i = 0; i < elements.length; i++) {
            if (frequency[i] != 0 && elements[i] != null) {
                union.elements[i] = elements[i];
                union.frequency[i] = frequency[i];
                endPoint++;
            }
        }

        for (int i = 0; i < otherMultiSet.elements.length; i++) {
            if (otherMultiSet.frequency[i] != 0 && otherMultiSet.elements[i] != null) {
                matchFound = false;
                for (int j = 0; j < union.elements.length; j++) {
                    if (union.frequency[j] != 0 && union.elements[j] != null
                            && union.elements[j].equals(otherMultiSet.elements[i])) {
                        union.frequency[j] += otherMultiSet.frequency[i];
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    union.elements[endPoint] = otherMultiSet.elements[i];
                    union.frequency[endPoint] = otherMultiSet.frequency[i];
                    endPoint++;
                }
            }

        }

        return union;
    } // end of union()
    /* Intersection of different array multisets  */
    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        int count=0;
        ArrayMultiset otherMultiSet = (ArrayMultiset) other;
        ArrayMultiset intersect = new ArrayMultiset();
        for (int i = 0; i <elements.length; i++) {
            if (frequency[i] != 0 && elements[i] != null) {
                for (int j = 0; j < otherMultiSet.elements.length; j++) {
                    if (otherMultiSet.frequency[j] != 0 && otherMultiSet.elements[j] != null
                            && elements[i].equals(otherMultiSet.elements[j])) {
                        intersect.elements[count]=elements[i];
                        intersect.frequency[count]=otherMultiSet.frequency[i]>frequency[j]?frequency[j]:otherMultiSet.frequency[i];
                        count++;

                    }
                }
            }

        }
        return intersect;

    } // end of intersect()
    /* Difference between different array multisets */
    @Override
    public RmitMultiset difference(RmitMultiset other) {
        boolean found=false;
        boolean eliminated=false;
        int count=0;
        ArrayMultiset otherMultiSet = (ArrayMultiset) other;
        ArrayMultiset difference = new ArrayMultiset();

        for (int i = 0; i < elements.length; i++) {

            found=false;
            eliminated=false;
            if (frequency[i] != 0 && elements[i] != null) {
                for (int j = 0; j < otherMultiSet.elements.length; j++)
                {
                    if (otherMultiSet.frequency[j] != 0 && otherMultiSet.elements[j] != null
                            && elements[i].equals(otherMultiSet.elements[j]) && (frequency[i]-otherMultiSet.frequency[j])>0)
                    {
                        found=true;
                        difference.elements[count]=elements[i];
                        difference.frequency[count]=frequency[i]-otherMultiSet.frequency[j];
                        count++;
                        break;
                    }
                    else if(otherMultiSet.frequency[j] != 0 && otherMultiSet.elements[j] != null
                            && elements[i].equals(otherMultiSet.elements[j]) && (frequency[i]-otherMultiSet.frequency[j] ==0 || frequency[i]-otherMultiSet.frequency[j]<0)  )
                    {
                        eliminated=true;
                        break;
                    }
                }

                if(!found && !eliminated)
                {
                    difference.elements[count]=elements[i];
                    difference.frequency[count]=frequency[i];
                    count++;
                }
            }
        }

        return difference;
    } // end of difference()

} // end of class ArrayMultiset