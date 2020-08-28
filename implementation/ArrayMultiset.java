package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Array implementation of a multiset. See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset {

    String[] elements;
    int[] frequency;


    int count=0;

    public ArrayMultiset() {
        elements = new String[20];
        frequency = new int[20];
    }

    @Override
    public void add(String elem) {
        //long startTime=System.nanoTime();
        count++;
        if(count == elements.length )
        {
            String[] elementsTemp=new String[elements.length * 2];
            int[] frequencyTemp=new int[frequency.length * 2];

            for(int i=0;i<elements.length;i++)
            {
                elementsTemp[i]=elements[i];
                frequencyTemp[i]=frequency[i];
            }
            elements=elementsTemp;
            frequency=frequencyTemp;
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
        // long endTime=System.nanoTime();
        // long timeTaken=endTime-startTime;
    }

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

    @Override
    public String print() {
        String outPut = "";
        for (int j = 0; j < elements.length; j++) {
            if (elements[j] != null) {
                outPut += elements[j] + " : " + String.valueOf(frequency[j]) + "\n";
            }
        }
        return outPut;
    } // end of OrderedPrint

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

    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        int count=0;
        ArrayMultiset otherMultiSet = (ArrayMultiset) other;
        ArrayMultiset intersect = new ArrayMultiset();
        for (int i = 0; i < elements.length; i++) {
            if (frequency[i] != 0 && elements[i] != null) {
                for (int j = 0; j < otherMultiSet.elements.length; j++) {
                    if (otherMultiSet.frequency[j] != 0 && otherMultiSet.elements[j] != null
                            && elements[i].equals(otherMultiSet.elements[j])) {
                        intersect.elements[count]= elements[i];
                        intersect.frequency[count]=otherMultiSet.frequency[i]>frequency[j]?frequency[j]:otherMultiSet.frequency[i];
                        count++;

                    }
                }
            }

        }
        return intersect;

    } // end of intersect()

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
