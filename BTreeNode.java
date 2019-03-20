package b_tree;

import java.util.LinkedList;

public class BTreeNode<T>{
	/**
	 * v1.1 
	 * 		1.将节点内部链表封装，暴露了操作节点的方法
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
	public T pollFirstVal() {return vals.pollFirst();}
	public T pollLastVal() {return vals.pollLast();}
	
	public void addVal(T v) {vals.addFirst(v);}
	public void addChild(BTreeNode c) {children.addFirst(c);}
	
	public void addFirstChild(BTreeNode c) {children.addFirst(c);}
	public void addLastChild(BTreeNode c) {children.addLast(c);}
	public void addChild(int i,BTreeNode c) {children.add(i, c);}
	public void addVal(int i,T v) {vals.add(i,v);}
	public void addFirstVal(T v) {vals.addFirst(v);}
	public void addLastVal(T v) {vals.addLast(v);}
	
}
