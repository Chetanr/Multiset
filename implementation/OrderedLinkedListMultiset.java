package implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */

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

    public Node ()
    {
        this.next = null;
        this.data = null;
        this.instances = 1;
    }
}

public class OrderedLinkedListMultiset extends RmitMultiset
{
    Node headNode;

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
                        if (temp.next == null || newNode.data.compareTo(temp.next.data) == 0)
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


    @Override
	public boolean contains(String item) {
        Node temp = this.headNode;
        while (temp.next != null)
        {
            if (temp.data.equals(item))
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }


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


    @Override
	public String print() {
        Node temp = this.headNode;
        String list = "";
        String temp2;

//        temp2 = temp.data.concat(":" + temp.instances);
//        list = temp2.concat("\n".concat(list));
//        temp = temp.next;

        while (temp != null)
        {
            temp2 = temp.data.concat(":" + temp.instances);
            list = temp2.concat("\n".concat(list));
            temp = temp.next;
        }
//
//        if (temp.next != null)
//        {
//            temp = temp.next;
//            while (temp.next != null)
//            {
//                temp2 = temp.data.concat(":" + temp.instances);
//                list = temp2.concat("\n".concat(list));
//                temp = temp.next;
//            }
//        }
//
//        if (temp.next == null)
//        {
//            temp2 = temp.data.concat(":" + temp.instances);
//            list = temp2.concat("\n".concat(list));
//        }

        return list;
    }


    @Override
	public String printRange(String lower, String upper) {
        Node temp = this.headNode;

        String list = "";
        while (temp.next != null)
        {
            if (temp.data.compareTo(lower) >=0 && temp.data.compareTo(upper) <= 0)
            {
                list = list.concat(temp.data.concat(" "));
            }
            temp = temp.next;
        }
        if (list != null)
            return list.concat("\n");

        return null;
    }


    @Override
	public RmitMultiset union(RmitMultiset other) {

        RmitMultiset unionList = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset input = (OrderedLinkedListMultiset) other;


        Node temp = this.headNode.next;
        int found = 0;


        ((OrderedLinkedListMultiset) unionList).headNode = this.headNode;

        Node temp2 = ((OrderedLinkedListMultiset) unionList).headNode;

        Node temp3 = input.headNode;



        while (temp.next != null)
        {
            Node newNode = new Node();
            newNode.data = temp.data;
            newNode.instances = temp.instances;
            newNode.next = temp.next;

            temp2.next = newNode;

            temp = temp.next;
            temp2 = temp2.next;
        }


        while (temp.next != null)
        {
            while (temp3.next != null)
            {
                if (temp3.data.equals(temp2.data))
                {
                    temp2.instances = temp2.instances + temp3.instances;
                    found = 1;
                    break;
                }

                temp3 = temp3.next;
            }

            if (found == 0)
            {
                Node node = new Node();
                node.data = temp2.data;
                node.instances = temp2.instances;
                node.next = temp2.next;

                temp2.next = node;
            }

            temp = temp.next;
        }
        return unionList;
    }


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        return null;
    }


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        return null;
    }

}
