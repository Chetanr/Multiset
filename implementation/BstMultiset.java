package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{ /* Initialization of BST variables : Nodes ,Values  */
    Node root;
    String outPutString="";
    List<String> instances=new ArrayList<>();
    List<Node> otherBstNodes=new ArrayList<>();
    List<Node> bstNodes=new ArrayList<>();
    List<Node> genericResultNodes=new ArrayList<>();
    Set<String> outPut=new TreeSet<>();
    class Node {
        String key;
        int value;
        Node left, right;

        public Node(String item) {
            key = item;
            left = right = null;
        }
        public Node(String key,int value)
        {
            this.key=key;
            this.value=value;
        }
    }


    Node insertRec(Node root, String key) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(key);
            root.value=1;
            return root;
        }

        if(key.compareTo(root.key)== 0)
        {
            root.value+=1;
        }
        /* Otherwise, recur down the tree */
        else if (key.compareTo(root.key) <0)
            root.left = insertRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key);

        /* return the (unchanged) node pointer */
        return root;
    }

    Node insertNodes(Node start, Node node)
    {
        if(start == null)
        {
            return node;
        }
        if (node.key.compareTo(start.key) <0)
            start.left = insertNodes(start.left, node);
        else if (node.key.compareTo(start.key) > 0)
            start.right = insertNodes(start.right, node);

        /* return the (unchanged) node pointer */
        return start;
    }
    /* Adding Items to BST*/
    @Override
    public void add(String item) {
        this.root=insertRec(this.root,item);
    } // end of add()

    /* Searching an item in BST */
    @Override
    public int search(String item) {

        Node node=inorderSearch(root,item);
        if(node == null)
        {
            return searchFailed;
        }else
        {
            return node.value;
        }
    } // end of search()

    /* Searching an item in BST by Instance */
    @Override
    public List<String> searchByInstance(int instanceCount) {
        searchByValue(root,instanceCount);

        return instances.size()>0?instances:null;
    } // end of searchByInstance


    private void searchByValue(Node node, int instanceCount) {
        if(node == null)
        {
            return;
        }
        if(node.value == instanceCount)
        {
            instances.add(node.key+" : "+String.valueOf(instanceCount));
        }
        if(node.right !=null)
            searchByValue(node.right,instanceCount);
        if(node.left !=null)
            searchByValue(node.left,instanceCount);
    }
    /*  Check whether an item is contained in BST  */
    @Override
    public boolean contains(String item) {

        return inorderSearch(root,item)!=null?true:false;
    } // end of contains()
    /* Removal of an item from BST multiset  */
    public Node removeItem(Node node,String item)
    {
        if(node == null)
        {
            return null;
        }
        if (item.compareTo(node.key) <0)
            node.left = removeItem(node.left, item);
        else if (item.compareTo(node.key) >0)
            node.right = removeItem(node.right, item);
        else
        {
            if(node.value>1)
            {
                node.value-=1;
                return node;
            }
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;
            node.key = minValue(node.right);

            // Delete the inorder successor
            node.right = removeItem(node.right, node.key);
        }
        return node;
    }
    String minValue(Node node)
    {
        String minv = node.key;
        while (node.left != null)
        {
            minv = node.left.key;
            node = node.left;
        }
        return minv;
    }
    /* Remove an item from BST */
    @Override
    public void removeOne(String item) {
        root=removeItem(root,item);

    } // end of removeOne()

    Node inorderSearch(Node root,String key)
    {
        if(root == null)
            return null;
        if(root.key.equals(key))
        {
            return root;
        }

        else if (key.compareTo(root.key) <0)
            root=inorderSearch(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root=inorderSearch(root.right, key);
        return root;
    }


    Set<String> inorderRec(Node root) {
        if(root==null)
        {
            return outPut;
        }
        else {
            //outPut += root.key + " : " + String.valueOf(root.value) + "\n";
            outPut.add(root.key + " : " + String.valueOf(root.value));
            inorderRec(root.left);
            inorderRec(root.right);
        }
        return outPut;
    }


    Node inOrdertraversal(Node node,Boolean isOwn)
    {
        if(node==null)
        {
            return node;
        }
        else {
            if(isOwn)
            {
                bstNodes.add(node);
            }else
            {
                otherBstNodes.add(node);
            }
            inOrdertraversal(node.left,isOwn);
            inOrdertraversal(node.right,isOwn);
        }
        return node;
    }
    Set<String> inorderRange(Node root,String lower,String upper) {
        if(root==null)
        {
            return outPut;
        }
        else
        {
            if(root.key.compareTo(lower) >= 0 && root.key.compareTo(upper) <=0) {
                outPut.add(root.key + " : " + String.valueOf(root.value));
            }
            inorderRange(root.left,lower,upper);
            inorderRange(root.right,lower,upper);
        }
        return outPut;
    }
    /* Print the items present in BST*/
    @Override
    public String print() {
        long start = System.nanoTime();
        String outPutString="";
        for(String out:inorderRec(root))
        {
            outPutString+=out+"\n";
        }
        long end = System.nanoTime();
        System.out.println("remove " + (end - start));
        return outPutString;

    } // end of OrderedPrint

    /* Printing items  from lower to upper range in BST*/
    @Override
    public String printRange(String lower, String upper) {
        outPutString="";
        for(String out: inorderRange(this.root,lower,upper))
        {
            outPutString+=out+"\n";
        }
        return outPutString;
    } // end of printRange()

    /* Union between different BST multisets */
    @Override
    public RmitMultiset union(RmitMultiset other) {
        otherBstNodes.clear();
        bstNodes.clear();
        genericResultNodes.clear();
        boolean found=false;
        BstMultiset otherBstMultiset=(BstMultiset)other;
        BstMultiset unionBstMultiset=new BstMultiset();
        inOrdertraversal(otherBstMultiset.root,false);
        inOrdertraversal(this.root,true);

        for(int i=0;i<bstNodes.size();i++)
        {
            genericResultNodes.add(new Node(bstNodes.get(i).key,bstNodes.get(i).value));
        }
        for(int i=0;i<otherBstNodes.size();i++)
        {
            found=false;
            for(int j=0;j<bstNodes.size();j++)
            {
                if(otherBstNodes.get(i).key.equals(bstNodes.get(j).key))
                {
                    found=true;
                    genericResultNodes.get(j).value+=otherBstNodes.get(i).value;
                }
            }
            if(!found)
            {
                genericResultNodes.add(new Node(otherBstNodes.get(i).key,otherBstNodes.get(i).value));
            }
        }

        unionBstMultiset.root=genericResultNodes.get(0);
        for(int i=1;i<genericResultNodes.size();i++)
        {
            unionBstMultiset.insertNodes(unionBstMultiset.root,genericResultNodes.get(i));
        }



        return unionBstMultiset;
    } // end of union()

    /* Intersection between BST multisets */
    @Override
    public RmitMultiset intersect(RmitMultiset other) {
        otherBstNodes.clear();
        bstNodes.clear();
        genericResultNodes.clear();
        boolean found=false;
        BstMultiset otherBstMultiset=(BstMultiset)other;
        BstMultiset intersectBstMultiset=new BstMultiset();
        inOrdertraversal(otherBstMultiset.root,false);
        inOrdertraversal(this.root,true);

        for(int i=0;i<bstNodes.size();i++)
        {
            found=false;
            for(int j=0;j<otherBstNodes.size();j++)
            {
                if(bstNodes.get(i).key.equals(otherBstNodes.get(j).key))
                {
                    genericResultNodes.add(new Node(bstNodes.get(i).key,bstNodes.get(i).value>otherBstNodes.get(j).value?otherBstNodes.get(j).value:bstNodes.get(i).value));
                    break;
                }
            }
        }

        intersectBstMultiset.root=genericResultNodes.get(0);
        for(int i=1;i<genericResultNodes.size();i++)
        {
            intersectBstMultiset.insertNodes(intersectBstMultiset.root,genericResultNodes.get(i));
        }
        return intersectBstMultiset;
    } // end of intersect()

    /* Difference between BST multisets  */
    @Override
    public RmitMultiset difference(RmitMultiset other) {

        otherBstNodes.clear();
        bstNodes.clear();
        genericResultNodes.clear();
        boolean found=false;
        boolean eliminated=false;
        BstMultiset otherBstMultiset=(BstMultiset)other;
        BstMultiset differnceBstMultiset=new BstMultiset();
        inOrdertraversal(otherBstMultiset.root,false);
        inOrdertraversal(this.root,true);

        for(int i=0;i<bstNodes.size();i++)
        {
            found=false;
            eliminated=false;
            for(int j=0;j<otherBstNodes.size();j++)
            {
                if(bstNodes.get(i).key.equals(otherBstNodes.get(j).key) && bstNodes.get(i).value - otherBstNodes.get(j).value > 0)
                {
                    genericResultNodes.add(new Node(bstNodes.get(i).key,bstNodes.get(i).value - otherBstNodes.get(j).value));
                    break;
                }
                else if(bstNodes.get(i).key.equals(otherBstNodes.get(j).key) && bstNodes.get(i).value - otherBstNodes.get(j).value == 0)
                {
                    eliminated=true;
                    break;
                }
                else if(bstNodes.get(i).key.equals(otherBstNodes.get(j).key))
                {
                    found=true;
                    break;
                }
            }
            if(!found && !eliminated)
            {
                genericResultNodes.add(new Node(bstNodes.get(i).key,bstNodes.get(i).value));
            }
        }

        differnceBstMultiset.root=genericResultNodes.get(0);
        for(int i=1;i<genericResultNodes.size();i++)
        {
            differnceBstMultiset.insertNodes(differnceBstMultiset.root,genericResultNodes.get(i));
        }
        return differnceBstMultiset;

    } // end of difference()

} // end of class BstMultiset