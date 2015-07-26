package org.rgtv.btree;

import java.util.ArrayList;
import java.util.List;

class Pair<T,V> {
	   T left;
	   V right;
	   public Pair(T left,V right) {
		   this.left = left;
		   this.right = right;
	   }
	   public T getLeft() {
		   return this.left;
	   }
	   
	   public V getRight() {
		   return this.right;
	   }		   
}


class PairUtils{
	   public static <T1,V1> Pair<T1,V1> makePair(T1 left,V1 right) {
		   Pair<T1,V1> pair = new Pair<T1,V1>(left,right);
		   return pair;			   
	   }
}

class BtreeNode {
	private static int MAX_NODE_SIZE = 4;
	private static int MAX_CHILD_SIZE = 5;	
	   
    private List<Pair<Integer,Integer>> nodeArray;
    private List<BtreeNode> childNodes;
    boolean isIndexNode;
    
    public void insert(Integer key,Integer value) {
    	Pair<Integer,Integer> pair = PairUtils.makePair(key, value);
    	nodeArray.add(pair);
    	sortNodeArrayElements();
    }
    
    public boolean isNodeOverflow() {
    	return nodeArray.size() == MAX_NODE_SIZE ? true:false;
    }
    
    public boolean isNodeUnderflow() {
    	return nodeArray.size() /2 <= MAX_NODE_SIZE/2 ? true:false; 
    }
    
    public void setIndexNode(boolean isIndexNode) {
    	this.isIndexNode = isIndexNode;
    }
    
    public boolean isIndexNode () {
    	return isIndexNode;
    }
    
    public static BtreeNode createBtreeNode() {
    	BtreeNode node = new BtreeNode();
    	node.nodeArray = new ArrayList<Pair<Integer,Integer>>(MAX_NODE_SIZE+1);
    	node.childNodes = new ArrayList<BtreeNode>(MAX_CHILD_SIZE);
    	for(int i=0;i<MAX_CHILD_SIZE;i++){
    		node.childNodes.add(null);
    	}
    	node.setIndexNode(false);
    	return node;
   }
   
   public Pair<Integer,Integer> getMiddleNode() {
	   int size = nodeArray.size();
	   Pair<Integer,Integer> middleNodeValue = nodeArray.get(size/2);
	   return middleNodeValue;
   }
   
   public List<Pair<Integer,Integer>> getLeftSideNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   List<Pair<Integer,Integer>> leftSideElements = new ArrayList<Pair<Integer,Integer>>();
	   for(int i=0;i<middleNodeIndex;i++) {
		   leftSideElements.add(nodeArray.get(i));
	   }
	   return leftSideElements;
   }
   
   public List<Pair<Integer,Integer>> getRightSideNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   List<Pair<Integer,Integer>> rightSideElements = new ArrayList<Pair<Integer,Integer>>();
	   for(int i=middleNodeIndex+1;i<size;i++) {
		   rightSideElements.add(nodeArray.get(i));
	   }
	   return rightSideElements;
   }
   
   public void removeLeftSideNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   
	   for(int i=0;i<middleNodeIndex;i++) {
		   nodeArray.add(i,null);
	   }
	   
   }
   
   public void removeRightSideNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   
	   for(int i=middleNodeIndex+1;i<size;i++) {
		   nodeArray.add(i,null);
	   }
	   return ;
   }
   public List<BtreeNode> getRightChildNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   List<BtreeNode> rightSideChildNodes = new ArrayList<BtreeNode>();
	   for(int i= middleNodeIndex+1;i<size;i++) {
		   rightSideChildNodes.add(childNodes.get(i));
	   }
	   return rightSideChildNodes;
   }
   
   public List<BtreeNode> getLeftChildNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   List<BtreeNode> leftSideChildNodes = new ArrayList<BtreeNode>();
	   for(int i=0;i<middleNodeIndex;i++) {
		   leftSideChildNodes.add(childNodes.get(i));
	   }
	   return leftSideChildNodes;
   }
   
   public void removeRightChildNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   
	   for(int i= middleNodeIndex+1;i<size;i++) {
		  childNodes.add(i,null);
	   }
	   return;
   }
   
   public void  removeLeftChildNodes() {
	   int size = nodeArray.size();
	   int middleNodeIndex = size/2;
	   
	   for(int i=0;i<middleNodeIndex;i++) {
		   childNodes.add(i,null);
	   }
	   return;
   }
   
   public void insert(Pair<Integer,Integer> elem) {
	   this.insert(elem.getLeft(), elem.getRight());
   }
   
   public void insert(List<Pair<Integer,Integer>> elemList) {
	   for(int i=0;i<elemList.size();i++) {
		   Pair<Integer,Integer> elem = elemList.get(i);
		   this.insert(elem);
	   }
   }
    
   public void insertChildNodes(int index,BtreeNode leftNode,BtreeNode rightNode) {
	   if(index >= MAX_CHILD_SIZE) {
		   throw new IllegalArgumentException();
	   }
	   childNodes.add(index,leftNode);
	   childNodes.add(index+1,rightNode);
   }
   
   public void insert(int childIndexNode,Pair<Integer,Integer> elem) {
	   List<Pair<Integer,Integer>> nodes = new ArrayList<Pair<Integer, Integer>>();
	   for(int i= childIndexNode+1;i<nodeArray.size();i++) {
		   nodes.add(nodeArray.get(i));
	   }
	   nodeArray.add(childIndexNode,elem);
	   childIndexNode +=1;
	   for(int i=0;i<nodes.size();i++) {
		   nodeArray.add(childIndexNode,nodes.get(i));
	   }
   }
   
   public int getChildIndexNode(Integer key) {
	   int returnIndex = nodeArray.size();
	   for(int i=0;i<nodeArray.size();i++) {
		   if(nodeArray.get(i).getLeft() < key) {
			   returnIndex = i;
		   }
	   }
	   return returnIndex;
   }
   
   public BtreeNode getChildNode(int index) {
	   return childNodes.get(index);
   }
   
   public void insertChildNodes(List<BtreeNode> childNodes) {
	   for(int i=0;i<childNodes.size();i++) {
		   this.childNodes.add(childNodes.get(i));
	   }
   }
		   
   
   private void sortNodeArrayElements() {
	   for(int i=0;i<nodeArray.size();i++) {
		   for(int j=i+1;j<nodeArray.size();j++) {
			   if(nodeArray.get(i).getLeft() > nodeArray.get(j).getLeft()) {
				   Pair<Integer,Integer> pair = nodeArray.get(i);
				   nodeArray.add(i,nodeArray.get(j));
				   nodeArray.add(j,pair);
			   }
		   }
	   }
   }
    
}  

public class Btree {
	          
       private BtreeNode rootNode;
       public Btree() {
    	   rootNode = null;
       }
       
       public void insert(Integer key,Integer value) {
              if(rootNode == null) {
            	  rootNode = BtreeNode.createBtreeNode();
            	  rootNode.insert(key, value);
              } else {
            	  //a) Is root node overflow
            	  verifyRootNodeOverflowAndInsert(key,value);            	              	  
              }
       }
       
       private void verifyRootNodeOverflowAndInsert(Integer key,Integer value) {
    	   if(rootNode.isNodeOverflow()) {
    		   BtreeNode newRootNode = BtreeNode.createBtreeNode();
    		   BtreeNode leftNode = BtreeNode.createBtreeNode();
    		   BtreeNode rightNode = BtreeNode.createBtreeNode();
    		   Pair<Integer,Integer> middleElement = rootNode.getMiddleNode();
    		   newRootNode.insert(middleElement);
    		   List<Pair<Integer,Integer>> leftSideElements = rootNode.getLeftSideNodes();
    		   leftNode.insert(leftSideElements);
    		   List<Pair<Integer,Integer>> rightSideElements = rootNode.getRightSideNodes();
    		   rightNode.insert(rightSideElements);
    		   newRootNode.insertChildNodes(0, leftNode, rightNode);
    		   rootNode = null;
    		   rootNode = newRootNode;
    		   rootNode.setIndexNode(true);
    		   insertIntoNonOverflowRootNode(rootNode,key,value);
    	  }else {
    		  insertIntoNonOverflowRootNode(rootNode,key,value);
    	  }    	   
       }
       
       private void insertIntoNonOverflowRootNode(BtreeNode node,Integer key,Integer value) {
    	   //a)Check leaf node.
    	   //b)get the index value where key value pointing.
    	   //c)is index child node overflow,split the node and copy the middle value to node index where step -b pointed.Adject the node values.
    	   //d)if key value greater then left side value of split node then take left side node of split node. otherwise right node.
    	   if(!node.isIndexNode()) {
    		   node.insert(key, value);
    	   } else {
    		   int childNodeIndex = node.getChildIndexNode(key);
    		   BtreeNode childNode = node.getChildNode(childNodeIndex);
    		   if(childNode.isNodeOverflow()) {
    		     //if(childNode.isIndexNode()) {
    			   
    		     //} else {
    			   BtreeNode splitNode = BtreeNode.createBtreeNode();
    			   Pair<Integer,Integer> middleNode = childNode.getMiddleNode();
    			   node.insert(childNodeIndex,middleNode);
    			   List<Pair<Integer,Integer>> rightNodes = childNode.getRightSideNodes();
    			   List<BtreeNode> rightNodeChildNodes = childNode.getRightChildNodes();
    			   childNode.removeRightChildNodes();
    			   splitNode.insert(rightNodes);
    			   splitNode.insertChildNodes(rightNodeChildNodes);
    			   childNode.removeRightSideNodes();
    			   node.insertChildNodes(childNodeIndex, childNode, splitNode);
    		    //}
    		  }   
    		  insertIntoNonOverflowRootNode(childNode,key,value);
    	   }
       }
       
       
}
