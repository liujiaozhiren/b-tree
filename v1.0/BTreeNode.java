package b_tree;

import java.util.Comparator;
import java.util.LinkedList;

public class BTreeNode<T>{
	Comparator<T> com;
	public static int MAX_SIZE = 10;
	LinkedList<T> vals = new LinkedList<>();
	LinkedList<BTreeNode> children = new LinkedList<>();

	BTreeNode(T val){vals.add(val);children.add(null);children.add(null);}
	BTreeNode(){}
	public T getval(int i) {return vals.get(i);}
	
	public BTreeNode getchild(int i) {return children.get(i);}
	public void setchild(int i,BTreeNode c) {children.set(i, c);}
	public BTreeNode pollFirstChild() {return children.pollFirst();}
	/*
	public void add(T val) {
		if(vals.isEmpty()) {vals.add(val);children.add(null);children.add(null);}
		BTreeNode p = children.get(0);
		for(int i=0;i<vals.size();i++) ////找到在这个结点的位置 使得 val[i-1] <= val < val[i]
			if(com.compare(val, vals.get(i))<0)break;
	}*/
}