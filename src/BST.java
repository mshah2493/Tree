import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;

public class BST <Key extends Comparable<Key>, Value>
{
	private Node root;
	
	private class Node
	{
		private Key key;
		private Value value;
		private Node left, right;
		private int count;
		
		public Node(Key key, Value value, int size)
		{
			this.key = key;
            this.value = value;
            this.count = size;
		}
	}
	
	public boolean isEmpty() 
	{
        return size() == 0;
    }

    public int size() 
    {
        return size(root);
    }
    
    private int size(Node node)
    {
    	if (node == null) return 0;
    	
    	return node.count;
    }
    
    public boolean contains(Key key) 
    {
        if (key == null) throw new IllegalArgumentException();
        
        return get(key) != null;
    }
    
    public Value get(Key key) 
    {
        return get(root, key);
    }
    
    private Value get(Node node, Key key) 
    {
        if (key == null) throw new IllegalArgumentException();
        
        if (node == null) return null;
       
        while (node != null)
        {
        	int cmp = key.compareTo(node.key);
            
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;          
            else return node.value;
        } 
        
        return null;
    }
    
    public void put(Key key, Value value) 
    {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        
        if (value == null) 
        {
            delete(key);
            
            return;
        }
        
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) 
    {
        if (node == null) return new Node(key, value, 1);
        
        int cmp = key.compareTo(node.key);
        
        if (cmp < 0) node.left  = put(node.left,  key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.value = value;
        
        node.count = 1 + size(node.left) + size(node.right);
        
        return node;
    }
    
    public void deleteMin() 
    {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) 
    {
    	if (isEmpty()) throw new NoSuchElementException();
    	 
        if (node.left == null) return node.right;
        
        node.left = deleteMin(node.left);
        node.count = size(node.left) + size(node.right) + 1;
        
        return node;
    }
    
    public void deleteMax() 
    {
        root = deleteMax(root);
    }

    private Node deleteMax(Node node)
    {
    	if (isEmpty()) throw new NoSuchElementException();
    	
        if (node.right == null) return node.left;
        
        node.right = deleteMax(node.right);
        node.count = size(node.left) + size(node.right) + 1;

        return node;
    }
    
    public void delete(Key key) 
    {
        if (key == null) throw new IllegalArgumentException();

        root = delete(root, key);
    }

    private Node delete(Node node, Key key) 
    {
    	if (node == null) return null;
    	
    	int cmp = key.compareTo(node.key);
    	
    	if (cmp > 0) return delete(node.right, key);
    	else if (cmp < 0) return delete(node.left, key);
    	else
    	{
    		if (node.right == null) return node.left;
    		else if (node.left == null) return node.right;
    		else
    		{
    			Node temp = node;
    			node = min(node.right);
    			node.right = deleteMin(node.right);
    			node.left = temp.left;
    		}
    	}
    	
    	node.count = 1 + size(node.left) + size(node.right);
    	
    	return node;
    }
    
 
    public Key min() 
    {
        return min(root).key;
    } 

    private Node min(Node node) 
    { 
    	 if (isEmpty() || node == null) throw new NoSuchElementException();
    	 
    	 while(node.left != null)
    	 {
    		 node = node.left;
    	 }
    	 
    	 return node;
    } 

    public Key max() 
    {
    	return max(root).key;
    } 

    private Node max(Node node) 
    {
    	if (isEmpty() || node == null) throw new NoSuchElementException();
   	 
   	 	while(node.right != null)
   	 	{
   	 		node = node.right;
   	 	}
   	 
   	 	return node;
    }
    
    public Key floor(Key key)
    { 	
    	Node node = floor(root, key);
    	
    	if (node == null) return null;
    	
    	return node.key;
    }
    
    private Node floor(Node node, Key key)
    {
    	if (node == null) return null;
    	
    	int cmp = key.compareTo(node.key);
    	
    	if (cmp == 0) return node;
    	
    	if (cmp < 0) return floor(node.left, key);
    	
    	Node n = floor(node.right, key);
    	if (n != null) return n;
    	else return node;
    }
    
    public Key ceiling(Key key)
    { 	
    	Node node = ceiling(root, key);
    	
    	if (node == null) return null;
    	
    	return node.key;
    }
    
    private Node ceiling(Node node, Key key)
    {
    	if (node == null) return null;
    	
    	int cmp = key.compareTo(node.key);
    	
    	if (cmp == 0) return node;
    	
    	if (cmp < 0) 
    	{
    		Node n = ceiling(node.left, key); 
           
    		if (n != null) return n;
            
    		else return node; 
    	}
    	
    	return ceiling(node.right, key);
    }
    
    public int rank(Key key)
    {
    	return rank(root, key);
    }
    
    private int rank(Node node, Key key)
    {
    	int rank = 0;
    	
    	int cmp = node.key.compareTo(key);
    	
    	if (cmp == 0) return size(node.left);
    	
    	if (cmp < 0) return 1 + size(node.left) + rank(node.right, key);
    	
    	return rank(node.left, key);
    }
    
    public Iterable<Key> inOrderKeys()
    {
		Queue<Key> queue = new Queue<Key>();
		inorder(root, queue);
		return queue;
    }
    
    private void inorder(Node node, Queue<Key> queue)
    {
    	if (node == null) return;
    	
    	inorder(node.left, queue);
    	queue.enqueue(node.key);
    	inorder(node.right, queue);
    }
    
    public Iterable<Key> preOrderKeys()
    {
		Queue<Key> queue = new Queue<Key>();
		preorder(root, queue);
		return queue;
    }
    
    private void preorder(Node node, Queue<Key> queue)
    {
    	if (node == null) return;
    	
    	queue.enqueue(node.key);
    	postorder(node.left, queue);
    	postorder(node.right, queue);
    }
    
    public Iterable<Key> postOrderKeys()
    {
		Queue<Key> queue = new Queue<Key>();
		postorder(root, queue);
		return queue;
    }
    
    private void postorder(Node node, Queue<Key> queue)
    {
    	if (node == null) return;
    	
    	postorder(node.left, queue);
    	postorder(node.right, queue);
    	queue.enqueue(node.key);
    }
    
    public boolean isBST()
    {
    	return isBST(root, null, null);
    }
    
    private boolean isBST(Node node, Key min, Key max)
    {
    	if (node == null) return true;
        if (min != null && node.key.compareTo(min) <= 0) return false;
        if (max != null && node.key.compareTo(max) >= 0) return false;
        
        return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
    }
    
    private boolean isSizeConsistent() 
    { 
    	return isSizeConsistent(root); 
    }
    
    private boolean isSizeConsistent(Node node) 
    {
        if (node == null) return true;
        if (node.count != size(node.left) + size(node.right) + 1) return false;
        
        return isSizeConsistent(node.left) && isSizeConsistent(node.right);
    } 
}
