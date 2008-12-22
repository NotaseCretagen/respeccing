/*  Copyright (C) 2005-2008 AtKaaZ <atkaaz@users.sourceforge.net>
 	
 	This file and its contents are part of DeMLinks.

    DeMLinks is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DeMLinks is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DeMLinks.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.demlinks.javaone;

/**
 * NodeLevel1
 * linkTo(child) makes sure child.parentList.has(this) and also this.childList.has(child)
 * a.linkTo(b) is equivalent to b.linkFrom(a) 
 */
public class NodeLevel1 extends NodeLevel0 {

	/**
	 * 
	 */
	public NodeLevel1() {
		super();
	}
	
	@Override
	protected void createLists() {
		parentsList = new ListOfUniqueNodesLevel1(this);
		parentsList.init();
		childrenList = new ListOfUniqueNodesLevel1(this);
		childrenList.init();
	}

	/**
	 * @param childNode2
	 * @return
	 */
	public boolean linkTo(NodeLevel1 childNode2) {
		boolean ret1 = super.linkTo(childNode2);
		boolean ret2 = ((NodeLevel0)childNode2).linkFrom(this);
		return ret1 && ret2;
	}
	
	/**
	 * @param parentNode2
	 * @return
	 */
	public boolean linkFrom(NodeLevel1 parentNode2) {
		boolean ret1 = super.linkFrom(parentNode2);
		boolean ret2 = ((NodeLevel0)parentNode2).linkTo(this);
		return ret1 && ret2;
	}
	
	/**
	 * @param childNodeL1
	 * @return
	 */
	public boolean isLinkTo(NodeLevel1 childNodeL1) {
		boolean ret1 = super.isLinkTo(childNodeL1);
		boolean ret2 = ((NodeLevel0)childNodeL1).isLinkFrom(this);
		if (ret1 ^ ret2) {
			throw new AssertionError("inconsistent link detected");
		}
		return ret1 && ret2;
	}
	
	/**
	 * @param parentNodeL1
	 * @return
	 */
	public boolean isLinkFrom(NodeLevel1 parentNodeL1) {
		boolean ret1 = super.isLinkFrom(parentNodeL1);
		boolean ret2 = ((NodeLevel0)parentNodeL1).isLinkTo(this);
		if (ret1 ^ ret2) {
			throw new AssertionError("inconsistent link detected");
		}
		return ret1 && ret2;
	}
	
	
	/**
	 * @param childNodeLevel1
	 * @return
	 */
	public boolean unLinkTo(NodeLevel1 childNodeLevel1) {
		boolean ret1 = super.unLinkTo(childNodeLevel1);
		boolean ret2 = ((NodeLevel0)childNodeLevel1).unLinkFrom(this);
		incoLinkError(ret1, ret2);
		return ret1 && ret2;
	}
	
	/**
	 * @param parentNodeLevel1
	 * @return
	 */
	public boolean unLinkFrom(NodeLevel1 parentNodeLevel1) {
		boolean ret1 = super.unLinkFrom(parentNodeLevel1);
		boolean ret2 = ((NodeLevel0)parentNodeLevel1).unLinkTo(this);
		incoLinkError(ret1, ret2);
		return ret1 && ret2;
	}
	
	/**
	 * @param ret1
	 * @param ret2
	 */
	public void incoLinkError(boolean ret1, boolean ret2) {
		if (ret1 ^ ret2) {
			throw new AssertionError("inconsistent link detected");
		}
	}
	
//	@Override
//	public boolean equals(Object o) {
//		boolean ret = super.equals(o);
//		boolean ret2 = (this == o);
//		return ret || ret2;
//		
//	}
}
