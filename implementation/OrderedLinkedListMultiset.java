package implementation;

import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset
{

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
    }


    @Override
	public void add(String item) {
        // Implement me!
        Node newNode = new Node(item);
        int instances = 1;
        int count = 0;

        if(headNode == null)
        {
            headNode = newNode;
        }
        else
        {
            Node temp = headNode;
            if (temp.next == null)
            {
                if (newNode.data.compareTo(temp.data) > 0)
                {
                    temp.next = newNode;
//                    temp = temp.next;
                }
                else if (newNode.data.compareTo(temp.data) < 0)
                {
//                    if (temp.data.equals(newNode.data))
//                    {
//                        newNode.instances++;
//                    }
                    headNode = newNode;
                    headNode.next = temp;
                }
                else if (newNode.data.compareTo(temp.data) == 0)
                {
                    temp.instances++;
                }
            }
            else
            {
                while (temp.next != null)
                {
                    if (newNode.data.compareTo(temp.data) > 0)
                    {
                        if (temp.next == null || newNode.data.compareTo(temp.next.data) == 0)
                        {
                            if (temp.next.data.equals(newNode.data))
                            {
                                instances++;
                            }
                            newNode.instances = instances;
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
//                        if (temp.data.equals(newNode.data))
//                        {
//                            newNode.instances++;
//                        }
                        if (count == 0)
                        {
                            headNode = newNode;
                            headNode.next = temp;
                            break;
                        }
                        else
                        {
//                             newNode.instances = instances;
                             newNode.next = temp.next;
                             temp.next = newNode;
                        }

                        temp = temp.next;
                    }
                    else if (newNode.data.compareTo(temp.data) == 0)
                    {
                        temp.instances++;
                    }
                    count ++;
                }
            }

        }

    } // end of add()


    @Override
	public int search(String item) {
        // Implement me!
        Node temp = headNode;
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
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {

        // Placeholder, please update.
        return null;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        // Implement me!
        Node temp = headNode;
        while (temp.next != null)
        {
            if (temp.data.equals(item))
            {
                return true;
            }
            temp = temp.next;
        }
        // Placeholder, please update.
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        // Implement me!
        Node temp1 = headNode;
//        Node temp2 = temp1;

        while (temp1.next != null)
        {
            if (temp1.next.data.equals(item))
            {
                Node temp2 = temp1.next;
                temp1.next = temp2.next;
                break;
            }
            else
            {
                temp1 = temp1.next;
            }
        }


//        if (temp1.data.equals(item))
//        {
//            headNode = temp1.next;
//            temp1.next = null;
////            return ;
//        }
//        while (temp2.next != null)
//        {
//            if (temp2.data.equals(item))
//            {
//                temp1.next = temp2.next;
//                temp2.next = null;
//                temp2 = temp1.next;
//                while (temp2.next != null)
//                {
//                    if (temp2.data.equals(item))
//                    {
//                        temp2.instances = temp2.instances - 1;
//                    }
//                    temp2 = temp2.next;
//                }
////                temp2.next = null;
//                break;
//            }
//            temp1 = temp1.next;
//            temp2 = temp2.next;
//        }
    } // end of removeOne()


    @Override
	public String print() {
        Node temp = headNode;
        String list = "";
        String temp2;

        while (temp.next != null)
        {
            temp2 = temp.data.concat(":" + temp.instances);
            list = temp2.concat("\n".concat(list));
            temp = temp.next;
        }
        // Placeholder, please update.
        return list;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        // Placeholder, please update.
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of difference()

} // end of class OrderedLinkedListMultiset
