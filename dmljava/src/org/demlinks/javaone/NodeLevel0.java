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





// at this level the Nodes don't have IDs, they're just java objects

/**
 * NodeLevel0 is an object that contains two lists of NodeLevel0 objects<br>
 * called parents and children
 * It only knows to do operations for itself (ie. it won't also link parent to child is a child to parent link is requested via linkFrom)
 * linkTo(child) makes sure only this.childList.has(child) but nothing else like child.parentList.has(this) - that's in NodeLevel1
 */
public class NodeLevel0 {
	// if both lists are empty the node shouldn't exist (in the Environment)
	// lists should never be null
	protected ListOfUniqueNodesLevel0 parentsList=null;//list of all Nodes that point to <this>
	protected ListOfUniqueNodesLevel0 childrenList=null;//list of all Nodes that <this> points to
	
	public NodeLevel0() {
		createLists();
	}

	protected void createLists() {
		parentsList = new ListOfUniqueNodesLevel0();
		childrenList = new ListOfUniqueNodesLevel0();
	}

	
	/**
	 * Makes sure that after the call <tt>this</tt> NodeLevel0 object has <tt>childNodeLevel0</tt> in its children list.<br>
	 * @param childNodeLevel0 the NodeLevel0 object that will be a child for <tt>this</tt> NodeLevel0
	 * @return true if the link didn't exist before call
	 */
	public boolean linkTo(NodeLevel0 childNodeLevel0) {
		boolean ret = get(List.CHILDREN).addLast(childNodeLevel0);
		return ret;
	}

	/**
	 * ensures there's a link from parentNodeLevel0 to <tt>this</tt> NodeLevel0<br>
	 * @param parentNodeLevel0 the node that will point to us
	 * @return true if the link didn't exist before call
	 */
	public boolean linkFrom(NodeLevel0 parentNodeLevel0) {
		boolean ret = get(List.PARENTS).addLast(parentNodeLevel0);
		return ret;
	}
	
	/**
	 * we will no longer point to <tt>childNodeLevel0</tt>
	 * @param childNodeLevel0 NodeLevel0 that will be removed from being a child of <tt>this</tt> NodeLevel0
	 * @return
	 */
	public boolean unLinkTo(NodeLevel0 childNodeLevel0) {
		boolean ret = get(List.CHILDREN).removeObj(childNodeLevel0);
		return ret;
	}


	/**
	 * <tt>parentNodeLevel0</tt> will no longer point to <tt>this</tt><br>
	 * @param parentNodeLevel0 NodeLevel0 that will be removed from being a parent of <tt>this</tt> NodeLevel0
	 * @return
	 */
	public boolean unLinkFrom(NodeLevel0 parentNodeLevel0) {
		boolean ret = get(List.PARENTS).removeObj(parentNodeLevel0);
		return ret;
	}

	/**
	 * checks if <tt>this</tt> points to <tt>childNodeLevel0</tt>
	 * @param childNodeLevel0
	 * @return true is so
	 */
	public boolean isLinkTo(NodeLevel0 childNodeLevel0) {
		boolean ret = get(List.CHILDREN).containsObj(childNodeLevel0);
		return ret;
	}
	
	/**
	 * checks if <tt>this</tt> is pointed by <tt>parentNodeLevel0</tt>
	 * @param parentNodeLevel0
	 * @return true if so
	 * @see #isLinkTo(NodeLevel0)
	 */
	public boolean isLinkFrom(NodeLevel0 parentNodeLevel0) {
		boolean ret = get(List.PARENTS).containsObj(parentNodeLevel0);
		return ret;
	}
	
	/**
	 * @return true if the NodeLevel0 has no children and no parents
	 */
	public boolean isAlone() {
		return ( (get(List.PARENTS).isEmpty()) && (get(List.CHILDREN).isEmpty()) );
	}
	
	/**
	 * @param list List.CHILDREN or List.PARENTS
	 * @return the specified list object
	 * @throws AssertionError if you specify unknown type of list to be returned
	 */
	public ListOfUniqueNodesLevel0 get(List list) throws AssertionError {
		switch (list) {
		case CHILDREN:
			return this.childrenList;
		case PARENTS:
			return this.parentsList;
		default:
			throw new AssertionError("Unhandled list type: "+list);
		}
	}




}
