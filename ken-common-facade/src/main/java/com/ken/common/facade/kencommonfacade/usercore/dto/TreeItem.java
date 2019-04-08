package com.ken.common.facade.kencommonfacade.usercore.dto;


import com.ken.common.facade.kencommonfacade.usercore.entity.CoreFunction;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
	//用于存放所有的子节点和终结点
	//执行函数treeMenuList后childNode和lastChildNode都会存在重复值，因此需要去重处理
	List<CoreFunction> childNode = new  ArrayList<CoreFunction>();
	List<CoreFunction> lastChildNode = new  ArrayList<CoreFunction>();

	public List<CoreFunction> treeMenuList(List<CoreFunction> CoreFunctions ,int pid) {
		List<CoreFunction> tempCoreFunction =  new  ArrayList<CoreFunction>();
		List<CoreFunction> tempCoreFunction1 =  new  ArrayList<CoreFunction>();
		for(CoreFunction node : CoreFunctions) {
			if(node.getParentId() == pid) {
				//说明存在子节点
				tempCoreFunction1 = treeMenuList(CoreFunctions,node.getId());
				if(tempCoreFunction1.isEmpty()) {
					//不存在子节点
					lastChildNode.add(node);
				}
				childNode.add(node);
				//用于让上一级判断是否存在子节点
				//因为存在子节点则tempCoreFunction不为空
				//函数结束后返回tempCoreFunction给上一级以供判断
				tempCoreFunction.add(node);
			}
		}
		return tempCoreFunction; 
	}

	public List<CoreFunction> getChildNode() {
		return childNode;
	}

	public List<CoreFunction> getLastChildNode() {
		return lastChildNode;
	}

	public void setChildNode(List<CoreFunction> childNode) {
		this.childNode = childNode;
	}

	public void setLastChildNode(List<CoreFunction> lastChildNode) {
		this.lastChildNode = lastChildNode;
	}
}
