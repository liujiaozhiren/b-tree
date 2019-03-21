package b_tree;

import java.util.LinkedList;

public class BTreeNode<T>{
	/**
	 * v1.1 
	 * 		1.将节点内部链表封装，暴露了操作节点的方法
	 * v1.2
	 *   
	 */
	private LinkedList<T> vals = new LinkedList<>();
	private LinkedList<BTreeNode> children = new LinkedList<>();

	BTreeNode(T val){vals.add(val);children.add(null);children.add(null);}
	BTreeNode(){}
	public T getVal(int i) {return vals.get(i);}
	public BTreeNode getChild(int i) {return children.get(i);}
	
	public int getValsSize() {return vals.size();}
	public int getChildSize() {return children.size();}
	
	public void setChild(int i,BTreeNode c) {children.set(i, c);}
	public void setVal(int i,T v) {vals.set(i, v);}
	
	public BTreeNode pollFirstChild() {return children.pollFirst();}
	public BTreeNode pollLastChild() {return children.pollLast();}
	public BTreeNode getFirstChild() {return children.getFirst();}
	public BTreeNode getLastChild() {return children.getLast();}
	public T pollFirstVal() {return vals.pollFirst();}
	public T pollLastVal() {return vals.pollLast();}
	public T getFirstVal() {return vals.getFirst();}
	public T getLastVal() {return vals.getLast();}
	
	public void addVal(T v) {vals.addFirst(v);}
	public void addChild(BTreeNode c) {children.addFirst(c);}
	
	public void addFirstChild(BTreeNode c) {children.addFirst(c);}
	public void addLastChild(BTreeNode c) {children.addLast(c);}
	public void addChild(int i,BTreeNode c) {children.add(i, c);}
	public void addVal(int i,T v) {vals.add(i,v);}
	public void addFirstVal(T v) {vals.addFirst(v);}
	public void addLastVal(T v) {vals.addLast(v);}
	public BTreeNode removeChild(int i) {return children.remove(i);}
	public T removeVal(int i) {return vals.remove(i);}
}
