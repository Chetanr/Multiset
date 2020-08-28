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

    Node headNode;

    static class Node
    {
        Node next;
        String data;
        int instances;

        public Node (String item)
        {
            this.next = null;
            this.data = item;
            this.instances = 1;
        }

        public Node ()
        {
            this.next = null;
            this.data = null;
            this.instances = 1;
        }
    }



    @Override
	public void add(String item) {

        //using the same implementeation of Ordered linked list for alphabetical ordering
        list.add(item);

        Node newNode = new Node(item);

        if(headNode == null)
        {
            this.headNode = newNode;
        }
        else
        {
            Node temp = this.headNode;
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


    @Override
	public int search(String item) {

        if (list.search(item) != searchFailed)
            return list.search(item);

        return searchFailed;
    }


    @Override
	public List<String> searchByInstance(int instanceCount) {

        if (list.searchByInstance(instanceCount) != null)
            return list.searchByInstance(instanceCount);
        else
            return null;
    }


    @Override
	public boolean contains(String item) {
        if (list.contains(item))
            return list.contains(item);
        return false;
    }


    @Override
	public void removeOne(String item) {
        list.removeOne(item);
    }


    @Override
	public String print() {
        String output = list.print();

        return output;
    }


    @Override
	public String printRange(String lower, String upper) {

        String output = list.printRange(lower, upper);
        return output;
    }


    @Override
	public RmitMultiset union(RmitMultiset other) {


        return list.union(other);
    }


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        return list.intersect(other);
    }


    @Override
	public RmitMultiset difference(RmitMultiset other) {


        return list.difference(other);
    }
}
