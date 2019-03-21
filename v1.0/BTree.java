package b_tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class BTree<T> {
	BTreeNode<T> root;
	Comparator<T> com;
	int MAX_SIZE = 4;
	public BTree(Comparator<T> com){
		this.com=com;
		root = new BTreeNode<T>();
		root.children.add(null);
	}
	
	public static void main(String[] args) {
		BTree<Integer> b = new BTree<>(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {if(o1==o2)return 0;
				return o1<o2?-1:1;
			}
		});
		LinkedList<Integer> l = new LinkedList<>();
		Random rand = new Random();
		for(int i=0;i<20;i++) {
			int v = rand.nextInt(30);
			l.add(v);
			b.add2(v);
			System.out.println("add "+v);
			bianli(b.root);
		}
		for(Integer i:l)
			System.out.print(i+" ");
		
		
	}
	public static void bianli(BTreeNode p) {
		System.out.print(p.children.get(0)==null?"x ":"↓ ");
		for(int i=0;i<p.vals.size();i++) {
			System.out.print(p.vals.get(i)+" ");
			System.out.print(p.children.get(i+1)==null?"x ":"↓ ");
		}
		System.out.println();
		for(int i=0;i<p.children.size();i++) {
			if(p.children.get(i)!=null)bianli((BTreeNode) p.children.get(i));
		}
		
	}

	public void add(T val1) {
		BTreeNode temp = root;
		LinkedList<BTreeNode> nodeStack = new LinkedList<>();	//将每次向下搜索的节点压栈 便于向上回溯
		LinkedList<Integer> intStack = new LinkedList<>();
		T val = val1;
		BTreeNode afterVal = null;
		while(temp!=null) {
			int i=0;
			for(i=0;i<temp.vals.size();i++) 					//找到在这个结点的位置 使得 val[i-1] <= val < val[i]
				if(com.compare(val, (T) temp.vals.get(i))<0)break;
			if(temp.children.get(i)!=null) { 													
				temp = (BTreeNode) temp.children.get(i);		//压栈
				nodeStack.addFirst(temp);
				intStack.addFirst(i);
			}else {																			
				temp.vals.add(i, val);
				temp.children.add(i+1, afterVal);
				if(temp.vals.size()>MAX_SIZE) {					//插入后节点数量大于最大SIZE  准备拆分节点								
					int position = temp.vals.size()/2;			//向上整合的元素位置
					val = (T) temp.vals.get(position);			// temp item 是拆分后的 两个新node节点
					BTreeNode item = new BTreeNode();
					for(int j=temp.vals.size()-1;j>=position;j--) {							//    0   1   2   3   
						item.children.addFirst(temp.children.pollLast());					//  0   1   2   3   4 
						item.vals.addFirst(temp.vals.pollLast());
					}
					item.vals.removeFirst();					//上面的循环里 多存了一个val值         取出
					if(!nodeStack.isEmpty()) { 												
						BTreeNode father = nodeStack.peek();
						int faInt = intStack.peek();
						afterVal = item;
						temp = father;
					}else {																
						BTreeNode father = new BTreeNode();
						father.vals.add(val);
						father.children.addFirst(temp);
						father.children.addLast(item);
						root = father;
						temp = null;
					}
				}else {break;}
			}
		}
	}
	
	
	public T search(T val) {
		BTreeNode temp = root;
		while(true) {
			int i;
			for(i=0;i<temp.vals.size();i++) {
				if(com.compare(val, (T) temp.vals.get(i))==0)return (T) temp.vals.get(i);
				else if(com.compare(val, (T) temp.vals.get(i))<0)break;
			}
			if(temp.children.get(i)!=null)temp=(BTreeNode) temp.children.get(i);
			else return null;
		}
	}
}