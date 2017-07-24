package net.ewide.platform.interfaces.utils;

import java.util.Comparator;

import net.ewide.platform.interfaces.utils.Node;

/**
 * @author wanghaozhe
 *
 */
public class NodeIDComparator  implements Comparator {
	 // 按照节点编号比较  
	 public int compare(Object o1, Object o2) {  
	  int j1 = Integer.parseInt(((Node)o1).getId());  
	     int j2 = Integer.parseInt(((Node)o2).getId());  
	     return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));  
	 }   
}
