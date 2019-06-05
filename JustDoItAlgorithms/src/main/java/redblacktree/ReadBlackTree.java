package redblacktree;

/*
 * 红黑树
 * 定义:
 * 1. 节点颜色不是黑色就是红色
 * 2. 根节点是黑色
 * 3. 父节点和子节点不能同时是红色
 * 4. 所有非叶子节点到叶子节点的路径包含相同的黑色节点数量
 * 
 * what:红黑树
 * 
 * 参考:
 * https://blog.csdn.net/eson_15/article/details/51144079
 */
public class ReadBlackTree<T extends Comparable<T>> {
	private RBNode root;
	private static final boolean Black = true;
	private static final boolean Red = false;
	
	public ReadBlackTree() {
		this.root = null;
	}
	
	public RBNode<T> parentOf(RBNode<T> node) {
		return node != null ? node.parent : null;
	}
	
	/*
	 * 插入
	 */
	public void insert(T key) {
		RBNode<T> node = new RBNode<T>(Red, key, null, null, null);
		if(node != null) {
			insert(node);
		}
	}
	
	public void insert(RBNode<T> node) {
		RBNode<T> current = null; //表示当前节点
		RBNode<T> x = this.root; //用来向下搜索
		
		//1. 找到插入位置
		while(x != null) {
			current = x;
			if(node.key.compareTo(x.key) < 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		
		//2. 将当前节点作为node的父节点
		node.parent = current;
		
		//3. 判断node是current的左节点还是右节点
		if(current != null) {
			if(node.key.compareTo(current.key) < 0) {
				current.left = node;
			} else {
				current.right = node;
			}
			
		} else {
			this.root = node;
		}
		
		//4. 修正红黑树
		insertFixUp(node);
	}
	
	/*
	 * 修正红黑树
	 * 
	 *          
	 * 
	 */
	public void insertFixUp(RBNode<T> node) {
		
		RBNode<T> parent; //父节点
		RBNode<T> gParent; //祖父节点
		
		//父节点存在且是红色
		while((parent = parentOf(node)) != null && parent.color == Red) {
			gParent = parentOf(parent); //祖父节点
			
			//若父节点是祖父节点的左节点,下面情况相反
			if(parent == gParent.left) {
				RBNode<T> uncle = gParent.right; //获得叔叔节点
				
				//case 1: 插入节点的父节点和叔叔节点均是红色
				if(uncle != null && uncle.color == Red) {
					//将父节点和叔叔节点涂黑
					uncle.color = Black;
					parent.color = Black;
					
					gParent.color = Red; //将祖父节点涂红
					
					node = gParent;
					continue;
				}
				
				//case2: 叔叔节点是黑色,且当前节点是右子节点
				if(node == parent.right) {
					leftRotate(parent); //以父节点为轴左旋
					
					RBNode tmp = node; //父节点和当前节点调换，为下面的右旋作准备
					node = parent;
					parent = tmp;
				}
				
				//case3: 当前节点的父节点是红色,叔叔节点是黑色，且当前节点是左子节点
				parent.color = Black;
				gParent.color = Red;
				rightRotate(gParent);
			} else {
				RBNode<T> uncle = gParent.left; //获得叔叔节点
				
				//case1: 父和叔节点均是红色
				if(uncle != null && uncle.color == Red) {
					//将父和叔节点涂黑，祖父节点涂红
					parent.color = Black;
					uncle.color = Black;
					gParent.color = Red;
					
					node = gParent;
					continue;
				}
			}
		}
		
		//设置根节点的颜色为黑色
		this.root.color = Black;
	}
	
	/*
	 * 旋转
	 * 
	 *           |     --LeftRotate(x)-->      |  
	 *           x                             y 
	 *         /   \   <--RightRotate(y)--   /   \
	 *        α     y                       x     γ
	 *             / \                     / \ 
	 *            β   γ                   α   β 
	 */
	
	public void leftRotate(RBNode x) {
		RBNode y = x.right;
		
		//1. 处理y的左子节点β
		x.right = y.left;
		if(y.left != null) {
			y.left.parent = x;
		}
		
		//2. 处理y的parent节点
		y.parent = x.parent;
		if(x.parent == null) {
			this.root = y;
		} else {
			if(x == x.parent.left) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		}
		
		//3. 调整x和y的父子关系
		x.parent = y;
		y.left = x;
	}

	public void rightRotate(RBNode y) {
		RBNode x = y.left;
		
		//1. 处理x的右节点β
		y.left = x.right;
		if(x.right != null) {
			x.right.parent = y;
		}
		
		//2. 处理x的parent节点
		x.parent = y.parent;
		if(y.parent == null) {
			this.root = x;
		} else {
			if(y == y.parent.left) {
				y.parent.left = x;
			} else {
				y.parent.right = x;
			}
		}
		
		//3. 调整x和y的父子关系
		x.right = y;
		y.parent =x;
		
	}
	
	class RBNode<T extends Comparable<T>> {
		boolean color;
		T key;
		RBNode left;
		RBNode right;
		RBNode parent;
		
		public RBNode(boolean color, T key, RBNode left, RBNode right, RBNode parent) {
			this.color = color;
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

}
