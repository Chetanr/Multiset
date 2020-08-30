package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */



public class OrderedLinkedListMultiset extends RmitMultiset
{

    //headnode for the list to be maintained based on alphabetical order
    Node headNode = null;
    Node temp;

    class Node
    {
        Node next;
        String data;
        int instances;

        public Node (Node node, String item)
        {
            this.next = node;
            this.data = item;
            this.instances = 1;
        }
    }

    //method to add the elements into the list
    @Override
	public void add(String item) {
        Node newNode = new Node(null, item);
        int instances = 1;
        int count = 0;
        if(this.headNode == null)
        {
            this.headNode = newNode;
        }
        else
        {
            Node temp = this.headNode;
            if (temp.next == null)
            {
                if (newNode.data.compareTo(temp.data) > 0)
                {
                    temp.next = newNode;
                    return;
                }
                else if (newNode.data.compareTo(temp.data) < 0)
                {
                    this.headNode = newNode;
                    this.headNode.next = temp;
                    return;
                }
                else if (newNode.data.compareTo(temp.data) == 0)
                {
                    temp.instances++;
                    return ;
                }
            }
            else
            {
                while (temp.next != null)
                {
                    if (newNode.data.compareTo(temp.data) ==  0)
                    {
                        temp.instances++;
                        break;
                    }
                    else if (newNode.data.compareTo(temp.data) > 0)
                    {
                        if (temp.next == null )
                        {
                            if (temp.next.data.equals(newNode.data))
                            {
                                instances = temp.next.instances + 1;
                                if (temp.next.next == null)
                                {
                                    newNode.instances = instances;
                                    newNode.next = temp.next;
                                    temp.next = newNode;
                                    break;
                                }
                                else
                                {
                                    temp.next.instances++;
                                    break;
                                }
                            }

                        }
                        else if (newNode.data.compareTo(temp.next.data) < 0)
                        {
                            newNode.next = temp.next;
                            temp.next = newNode;
                            break;
                        }
                        else
                        {
                            temp = temp.next;
                        }
                    }
                    else if (newNode.data.compareTo(temp.data) < 0)
                    {
                        if (count == 0)
                        {
                            this.headNode = newNode;
                            this.headNode.next = temp;
                            break;
                        }
                        else
                        {
                             newNode.next = temp.next;
                             temp.next = newNode;
                        }

                        temp = temp.next;
                    }

                    count ++;
                }
            }
        }
    }


    //method to search a given element in the list
    @Override
	public int search(String item) {
        Node temp = this.headNode;
        int totalInstances = 0;
        while (temp.next != null)
        {
            if (temp.data.equals(item))
            {
                if (temp.instances > totalInstances)
                {
                    totalInstances = temp.instances;
                }
            }
            temp = temp.next;
        }

        if (totalInstances > 0)
        {
            return totalInstances;
        }
        else
        {
            return searchFailed;
        }
    }


    //method to search the elements based on the instance count
    @Override
	public List<String> searchByInstance(int instanceCount) {
        Node temp = this.headNode;
        List<String> list = new ArrayList<>();
        while (temp.next != null)
        {
            if (temp.instances == instanceCount)
            {
                list.add(temp.data);
            }
            temp = temp.next;
        }
        if (list != null)
            return list;
        else
            return null;
    }


    //method to check if the given element is in the list
    @Override
	public boolean contains(String item) {
        Node temp = this.headNode;
        while (temp != null)
        {
            if (temp.data.equals(item))
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }


    //method to remove the given element from the list
    @Override
	public void removeOne(String item) {
        Node temp1 = this.headNode;

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
                    Node temp2 = temp1.next;
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
        Node tempNode = this.headNode;
        Node tempNode2;
        Node tempNode3;
        String list = "";
        String temp2;


        while (tempNode != null)
        {
            tempNode2 = tempNode;
            int instances = 0;
            tempNode3 = tempNode;
            while (tempNode2 != null)
            {
                if (tempNode2.instances > instances)
                {
                    instances = tempNode2.instances;
                    tempNode3 = tempNode2;
                }
                tempNode2 = tempNode2.next;
            }
            temp2 = tempNode3.data.concat(":" + tempNode3.instances);
            list = list.concat(temp2.concat("\n"));
            tempNode = tempNode.next;
        }
        return list;
    }


    //method to print the elements in the list in between a given range
    @Override
	public String printRange(String lower, String upper) {
        Node temp = this.headNode;
        String list = "";
        while (temp.next != null)
        {
            if (temp.data.compareTo(lower) >=0 && temp.data.compareTo(upper) <= 0)
            {
                list = list.concat(temp.data.concat(":" + (temp.instances + "\n")));
            }
            temp = temp.next;
        }
        if (list != null)
            return list;

        return null;
    }


    //method to find the union of 2 lists
    @Override
	public RmitMultiset union(RmitMultiset other) {

        OrderedLinkedListMultiset list = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset input = (OrderedLinkedListMultiset) other;

        Node temp = this.headNode;
        list.headNode = null;

        while(temp != null)
        {
            Node temp3 = new Node(null, temp.data);
            temp3.instances = temp.instances;

            if (list.headNode == null)
            {
                list.headNode = temp3;
            }
            else
            {
                OrderedLinkedListMultiset tempList = new OrderedLinkedListMultiset();
                tempList.temp = list.headNode;

                while (tempList.temp.next != null)
                {
                    tempList.temp = tempList.temp.next;

                }
                Node node = new Node (null, temp3.data);
                node.instances = temp3.instances;
                tempList.temp.next = node;
            }
            temp = temp.next;
        }

        temp = input.headNode;
        int instances;

        while (temp != null)
        {
            if (list.contains(temp.data))
            {
                Node temp2 = list.headNode;
                while (temp2 != null)
                {
                        if (temp2.data.compareTo(temp.data) == 0)
                        {
                            instances = temp.instances + temp2.instances;
                            temp2.instances = instances;
                            break;
                        }
                    temp2 = temp2.next;
                }
            }
            else
            {
                Node temp3 = list.headNode;
                while (temp3.next != null)
                {
                    temp3 = temp3.next;
                }
                Node temp4 = new Node(null, temp.data);
                temp4.instances = temp.instances;
                temp3.next = temp4;
            }
            temp = temp.next;
        }

        return list;
    }


    //method to find the intersection of 2 lists
    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        OrderedLinkedListMultiset list = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset input = (OrderedLinkedListMultiset) other;

        Node temp = this.headNode;
        list.headNode = null;

        while(temp != null)
        {
            Node temp2 = input.headNode;
            while (temp2 != null)
            {
                if (temp.data.compareTo(temp2.data) == 0)
                {
                    Node temp3 = new Node(null, temp2.data);
                    if (list.headNode == null)
                    {
                        list.headNode = temp3;
                    }
                    else
                    {
                        OrderedLinkedListMultiset tempList = new OrderedLinkedListMultiset();
                        tempList.temp = list.headNode;

                        while (tempList.temp.next != null)
                        {
                            tempList.temp = tempList.temp.next;

                        }
                        temp3 = new Node(null, temp3.data);
                        tempList.temp.next = temp3;
                    }
                }

                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        return list;
    }


    //method to find the difference between 2 lists
    @Override
	public RmitMultiset difference(RmitMultiset other) {

        OrderedLinkedListMultiset list = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset input = (OrderedLinkedListMultiset) other;

        Node temp = this.headNode;
        list.headNode = null;

        int found;
        int eliminated;
        int instances = 0;

        while (temp != null)
        {
            Node temp2 = input.headNode;
            found = 0;
            eliminated = 0;
            while (temp2 != null)
            {
                if (temp.data.compareTo(temp2.data) == 0)
                {
                    found = 1;
                    if (temp.instances > temp2.instances)
                    {
                        instances = temp.instances - temp2.instances;
                        eliminated = 1;
                    }
                    else
                    {
                        instances = temp.instances;
                    }
                    break;
                }
                temp2 = temp2.next;
            }
            if (found == 0 || eliminated == 1)
            {
                Node temp3 = new Node(null, temp.data);
                temp3.instances = instances;
                if (list.headNode == null)
                {
                    list.headNode = temp3;
                }
                else
                {
                    OrderedLinkedListMultiset tempList = new OrderedLinkedListMultiset();
                    tempList.temp = list.headNode;

                    while (tempList.temp.next != null)
                    {
                        tempList.temp = tempList.temp.next;
                    }
                    temp3 = new Node(null, temp3.data);
                    temp3.instances = instances;
                    tempList.temp.next = temp3;
                }
            }
            temp = temp.next;
        }
        return list;
    }

}
