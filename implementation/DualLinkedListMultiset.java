package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */

public class DualLinkedListMultiset extends RmitMultiset
{
    OrderedLinkedListMultiset list = new OrderedLinkedListMultiset();

    //headnode for the list to be maintained based on instance count
    SecondNode headNode;

    static class SecondNode
    {
        SecondNode next;
        String data;
        int instances;

        public SecondNode (String item)
        {
            this.next = null;
            this.data = item;
            this.instances = 1;
        }
    }


    /*
    method to add the elements into the list.
    Note that 2 lists are maintained.
    1. to maintain the list in alphabetical order
    2. to maintain the list based on the instance count
     */
    @Override
	public void add(String item) {

        //using the same implementeation of Ordered linked list for alphabetical ordering
        list.add(item);

        SecondNode newNode = new SecondNode(item);

        if(headNode == null)
        {
            this.headNode = newNode;
        }
        else
        {
            SecondNode temp = this.headNode;
            while (temp.next !=null)
            {
                if (newNode.data.compareTo(temp.data) == 0)
                {
                    temp.instances = temp.instances++;
                    return;
                }
                else
                {
                    temp = temp.next;
                }
            }
            if (temp.next == null)
            {
                temp.next = newNode;
            }
        }
    }


    //method to search a given element in the list
    @Override
	public int search(String item) {

        if (list.search(item) != searchFailed)
            return list.search(item);

        return searchFailed;
    }


    //method to search the elements based on the instance count
    @Override
	public List<String> searchByInstance(int instanceCount) {

        if (list.searchByInstance(instanceCount) != null)
            return list.searchByInstance(instanceCount);
        else
            return null;
    }


    //method to check if the given element is in the list
    @Override
	public boolean contains(String item) {
        if (list.contains(item))
            return list.contains(item);
        return false;
    }


    //method to remove the given element from both the lists
    @Override
	public void removeOne(String item)
    {
        list.removeOne(item);

        SecondNode temp1 = this.headNode;

        while (temp1.next != null)
        {
            if (temp1.next.data.equals(item))
            {
                if (temp1.next.instances > 1)
                {
                    temp1.next.instances --;
                    break;
                }
                else {
                    SecondNode temp2 = temp1.next;
                    temp1.next = temp2.next;
                    break;
                }
            }
            else
            {
                temp1 = temp1.next;
            }
        }
    }


    //method to print all the elements in the list
    @Override
	public String print() {
        long start = System.nanoTime();
        String output = list.print();

        long end = System.nanoTime();
        System.out.println("print " + (end - start));
        return output;
    }


    //method to print the elements in the list in between a given range
    @Override
	public String printRange(String lower, String upper)
    {

        String output = list.printRange(lower, upper);
        return output;
    }


    //method to find the union of 2 lists
    @Override
	public RmitMultiset union(RmitMultiset other)
    {
        return list.union(other);
    }


    //method to find the intersection of 2 lists
    @Override
	public RmitMultiset intersect(RmitMultiset other)
    {
        return list.intersect(other);
    }


    //method to find the difference between the 2 lists.
    @Override
	public RmitMultiset difference(RmitMultiset other) {


        return list.difference(other);
    }
}
