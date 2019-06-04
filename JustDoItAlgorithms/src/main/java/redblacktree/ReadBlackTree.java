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
 * https://www.cnblogs.com/CarpenterLee/p/5503882.html
 * https://www.cnblogs.com/CarpenterLee/p/5525688.html
 * https://www.cnblogs.com/skywang12345/p/3245399.html
 */
public class ReadBlackTree<T extends Comparable<T>> {
	private RBNode root;
	private static final boolean Black = true;
	private static final boolean Red = false;
	
	public ReadBlackTree() {
		this.root = null;
	}
	
	/*
	 * 插入
	 */
	
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
	
	public void LeftRotate(RBNode x) {
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

	public void RightRotate(RBNode y) {
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
