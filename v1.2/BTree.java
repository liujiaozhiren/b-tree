package b_tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class BTree<T> {
	
	/***
	 * v1.1
	 * 		1.对节点内部的直接操作被封装操作替换
	 * 		2.重构了原来的插入方法，现在的算法更容易理解
	 * 		3.调整了向上整合元素的位置，一个node节点会被更均匀的切成两半
	 * 		4.添加适当的注释
	 * v1.2 
	 * 		1.添加了删除节点方法，修正了部分问题
	 */
	
	
	BTreeNode<T> root;
	Comparator<T> com;
	int MAX_SIZE = 5;
	public BTree(Comparator<T> com){
		this.com=com;
		root = new BTreeNode<T>();
		root.addChild(null);						//root.children.add(null);
	}
	
	public static void main(String[] args) {
		
		BTree<Integer> btree = new BTree<>(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {if(o1==o2)return 0;
				return o1<o2?-1:1;
			}
		});
		
		Random rand = new Random();
		int[] a = {10,8,7,11,5,6,9,12,0,3,2,13,50,20,44,21,19,1,30};
		for(int i=0;i<a.length;i++) {
			int v = a[i];

			System.out.println("add "+v);;
			btree.insert(v);
			bianli(btree.root);
		}
		for(int i=0;i<a.length;i++) {
			int v =a[i];
			System.out.println("delete "+v);
			btree.delete(v);
			bianli(btree.root);
		}
		
		
		
	}
	
	public static void bianli(BTreeNode p) {
		System.out.print(p.getChild(0)==null?"x ":"↓ ");			//	System.out.print(p.children.get(0)==null?"x ":"↓ ");
		
		for(int i=0;i<p.getValsSize();i++) {						//	for(int i=0;i<p.vals.size();i++) {
			System.out.print(p.getVal(i)+" ");						//		System.out.print(p.vals.get(i)+" ");
			System.out.print(p.getChild(i+1)==null?"x ":"↓ ");		//		System.out.print(p.children.get(i+1)==null?"x ":"↓ ");
			
		}
		System.out.println();
	
		for(int i=0;i<p.getChildSize();i++) {						//	for(int i=0;i<p.children.size();i++) {
			if(p.getChild(i)!=null)bianli((BTreeNode) p.getChild(i));//		if(p.children.get(i)!=null)bianli((BTreeNode) p.children.get(i));
		}
		
	}
															
/*	public void add(T val1) {
		BTreeNode temp = root;
		LinkedList<BTreeNode> nodeStack = new LinkedList<>();	//将每次向下搜索的节点压栈 便于向上回溯
		LinkedList<Integer> intStack = new LinkedList<>();
		T val = val1;
		BTreeNode afterVal = null;
		while(temp!=null) {
			int i=0;
			for(i=0;i<temp.vals.size();i++) 					//找到在这个结点的位置 使得 val[i-1] <= val < val[i]
				if(com.compare(val, (T) temp.vals.get(i))<0)break;
		//	if(temp.children.get(i)!=null) { 													
			if(temp.getChild(i)!=null) { 	
		//		temp = (BTreeNode) temp.children.get(i);		//压栈
				temp = (BTreeNode) temp.getChild(i);
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
	
	
*/

	/**add方法存在问题且逻辑不易理解
	 * 重构为insert方法
	 * 
	 * 
	 * @param val1
	 */
	public void insert(T val1) {
		T val = val1;
		LinkedList<BTreeNode> nodeStack = new LinkedList<>();		/* 将每次向下搜索的节点压栈 便于向上回溯 */
		LinkedList<Integer> intStack = new LinkedList<>();
		BTreeNode temp = root;boolean bottom = false;				
		int i=0;							
		while(!bottom) {											/* 找到在这个结点的位置 使得 val[i-1] <= val < val[i] */
			for(i=0;i<temp.getValsSize();i++) 						//for(i=0;i<temp.vals.size();i++) 
				if(com.compare(val, (T) temp.getVal(i))<0)break;	//	if(com.compare(val, (T) temp.vals.get(i))<0)break;				
			if(temp.getChild(i)!=null) {							//if(temp.children.get(i)!=null) {
				nodeStack.addFirst(temp);
				intStack.addFirst(i);
				temp=(BTreeNode) temp.getChild(i);					//temp=(BTreeNode) temp.children.get(i);
			}
			else bottom=true;
		}												
		/***
		 * 至此，已找到树底需插入的位置 ，接下来进入拆分节点向上整合阶段
		 */
		temp.addVal(i,val);											//temp.vals.add(i, val);	
		temp.addChild(i+1,null);									//temp.children.add(i+1, null);		
		BTreeNode temp2;
		while(temp.getValsSize()>MAX_SIZE) {							//while(temp.vals.size()>MAX_SIZE) {
			int position = (MAX_SIZE+1)/2;
			val = (T) temp.getVal(position);								//val = (T) temp.vals.get(i);
			System.out.println("向上整合的是  "+val.toString());
			temp2 = new BTreeNode();
			for(int j=MAX_SIZE;j>=position;j--) {
				temp2.addFirstChild(temp.pollLastChild()); 			//temp2.children.addFirst(temp.children.pollLast());
				temp2.addFirstVal(temp.pollLastVal());				//temp2.vals.addFirst(temp.vals.pollLast());
			}
			temp2.pollFirstVal();									//temp2.vals.pollFirst();
			if(nodeStack.isEmpty()) {
				root = new BTreeNode();
				root.addVal(val);									//root.vals.add(val);
				root.addFirstChild(temp);							//root.children.addFirst(temp);
				root.addLastChild(temp2);							//root.children.addLast(temp2);
				break;
			}else {
				temp = nodeStack.pollFirst();
				i = intStack.pollFirst();
				temp.addVal(i,val);							//temp.vals.add(i, val);
				temp.addChild(i+1, temp2);							//temp.children.add(i+1,temp2);
			}
		}
		
		
	}
	public T search(T val) {
		BTreeNode temp = root;
		while(true) {
			int i;
			for(i=0;i<temp.getValsSize();i++) {
				if(com.compare(val, (T) temp.getVal(i))==0)return (T) temp.getVal(i);
				else if(com.compare(val, (T) temp.getVal(i))<0)break;
			}
			if(temp.getChild(i)!=null)								//if(temp.children.get(i)!=null)
				temp=(BTreeNode) temp.getChild(i);			//temp=(BTreeNode) temp.children.get(i);
			else return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delete(T val1) {
		BTreeNode temp = root;
		LinkedList<BTreeNode> nodeStack = new LinkedList<>();
		LinkedList<Integer>   intStack  = new LinkedList<>();
		int i;T val = val1;
		if(temp.getValsSize()==0)return;
		/**
		 * 查找val1所在节点  ，未找到直接return
		 * 
		 */
		while(true) {								
			for(i=0;i<temp.getValsSize();i++) {
				if(com.compare(val,(T)temp.getVal(i))<=0) {
					break;}}
			if(i<temp.getValsSize()&&com.compare(val, (T)temp.getVal(i))==0)
				break;
			else if(temp.getChild(i)!=null) {
				nodeStack.addFirst(temp);
				intStack.addFirst(i);
				temp=temp.getChild(i);
			}else return;
		}
		/**
		 * 判断节点是否为子叶节点，若不是  复制比其大的最小的元素p[0]到  temp[i] 位置  将待删除元素设为 p[0]
		 * 						即temp的i+1子树中 最左一个
		 */
		if(temp.getChild(0)!=null) {
			nodeStack.addFirst(temp);
			intStack.addFirst(i+1);
			BTreeNode p = temp.getChild(i+1);
			while(p.getChild(0)!=null) {
				nodeStack.addFirst(p);
				intStack.addFirst(0);
				p=p.getFirstChild();
			}
			temp.setVal(i, p.getFirstVal());
			temp = p;
			i = 0;
		}																							
		/**
		 * 删除当前子叶节点
		 */
		
	//	if(temp.getChild(0)==null) {
			temp.removeChild(i);
			temp.removeVal(i);																
			if(temp.getValsSize()>=MAX_SIZE/2) { 											 
				/*
				temp.removeChild(i);
				temp.removeVal(i);*/
				return;
			}
			/**如果删除后端点集合长度小于MAX_SIZE/2 
			 * 向上整合
			 * 
			 */
			else if(!nodeStack.isEmpty()) {
				BTreeNode father = nodeStack.poll();
				Integer lastNum = intStack.poll();
				if(lastNum!=0&&father.getChild(lastNum-1).getValsSize()-1>=MAX_SIZE/2) {    	
					BTreeNode left  = father.getChild(lastNum-1);
					BTreeNode right = temp;
					T a = (T) left.pollLastVal();
					T b = (T) father.getVal(lastNum-1);
					right.addFirstVal(b);
					right.addFirstChild(left.pollLastChild());
					father.setVal(lastNum-1,a);return;
				}else if(lastNum!=father.getValsSize()&&father.getChild(lastNum+1).getValsSize()-1>=MAX_SIZE/2) {
					BTreeNode left  = temp;
					BTreeNode right = father.getChild(lastNum+1);
					T c = (T) right.pollFirstVal();
					T b = (T) father.getVal(lastNum);
					left.addLastVal(b);
					left.addLastChild(right.pollFirstChild());
					father.setVal(lastNum,c);return;
				}else {												
					if(lastNum==0) {/*
						temp.removeChild(i);
						temp.removeVal(i);*/
						BTreeNode left  = temp;
						BTreeNode right = father.getChild(lastNum+1);
						left.addLastVal(father.removeVal(lastNum));
						left.addLastChild(right.pollFirstChild());
						father.removeChild(lastNum+1);int n = right.getChildSize();
						for(int j=0;j<n;j++) {
							left.addLastVal(right.pollFirstVal());
							left.addLastChild(right.pollFirstChild());
						}
					}else {/*										
						temp.removeChild(i);
						temp.removeVal(i);*/
						BTreeNode left  = father.getChild(lastNum-1);										
						BTreeNode right = temp;
						left.addLastVal(father.removeVal(lastNum-1));
						left.addLastChild(right.pollFirstChild());
						father.removeChild(lastNum);int n = right.getChildSize();
						for(int j=0;j<n;j++) {
							left.addLastVal(right.pollFirstVal());
							left.addLastChild(right.pollFirstChild());
						}
					}
					temp=father;

					father = nodeStack.poll();
					lastNum = intStack.poll();
					while(temp.getValsSize()<MAX_SIZE/2&&father!=null) {
						if(lastNum!=0&&father.getChild(lastNum-1).getValsSize()-1>=MAX_SIZE/2) {
							BTreeNode left  = father.getChild(lastNum-1);
							BTreeNode right = temp;
							BTreeNode leftno = left.pollLastChild();
							T a = (T) left.pollLastVal();
							T b = (T) father.getVal(lastNum-1);
							right.addFirstVal(b);
							right.addFirstChild(leftno);
							father.setVal(lastNum-1,a);return;
						}else if(lastNum!=father.getValsSize()&&father.getChild(lastNum+1).getValsSize()-1>=MAX_SIZE/2) {
							BTreeNode left  = temp;
							BTreeNode right = father.getChild(lastNum+1);
							BTreeNode rightno = right.pollFirstChild();
							T c = (T) right.pollFirstVal();
							T b = (T) father.getVal(lastNum);
							left.addLastVal(b);
							left.addLastChild(rightno);
							father.setVal(lastNum,c);return;
						}else {
							if(lastNum==0) {
								BTreeNode left  = temp;
								BTreeNode right = father.getChild(lastNum+1);
								left.addLastVal(father.removeVal(lastNum));
								left.addLastChild(right.pollFirstChild());
								father.removeChild(lastNum+1);
								int n = right.getChildSize();
								for(int j=0;j<n;j++) {
									left.addLastVal(right.pollFirstVal());
									left.addLastChild(right.pollFirstChild());
								}
							}else {
								BTreeNode left  = father.getChild(lastNum-1);
								BTreeNode right = temp;
								left.addLastVal(father.removeVal(lastNum-1));
								left.addLastChild(right.pollFirstChild());
								father.removeChild(lastNum);
								int n = right.getChildSize();
								for(int j=0;j<n;j++) {
									left.addLastVal(right.pollFirstVal());
									left.addLastChild(right.pollFirstChild());
								}
							}
						}
						/**
						 * 如果整合最后root节点指向一个没有val的节点
						 * 删除此节点，并将root指向其下部节点
						 * 
						 * 
						 */

						if(nodeStack.isEmpty()) 
							if(temp.getValsSize()==0) {
								root= temp.getFirstChild();
								return;
							}
						father = nodeStack.poll();
						lastNum = intStack.poll();
					}
					if(temp==root&&temp.getValsSize()==0){root = temp.getChild(0);return;}
					
				}
				
			}else {
				return;
			}
				
	//	}
		
		
		
	}
}
