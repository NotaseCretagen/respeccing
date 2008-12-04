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

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.ListIterator;

import org.junit.Test;


public class NodeTest {
	@Test
	public void testNode() {
		Node a = new Node();
		Node b = new Node();
		Node c = new Node();
		a.linkTo(b);
		checkIntegrity(a);
		checkIntegrity(b);
		assertTrue(a.isLinkTo(b));
		assertTrue(b.isLinkFrom(a));
		b.unlinkFrom(a);
		assertFalse(a.isLinkTo(b));
		assertFalse(b.isLinkFrom(a));

		a.linkTo(b);
		b.linkTo(c);
		c.linkTo(a);
		b.linkTo(a);
		assertTrue(a.isLinkFrom(b));
		assertTrue(a.isLinkTo(b));
		assertFalse(a.isLinkTo(c));
		assertTrue(a.isLinkFrom(c));
		//a.getChildrenList().add
		ListIterator<Node> itr = a.getChildrenList().listIterator();
		//System.out.println(itr.toString());
		itr.add(c);
		itr.previous();
		while(itr.hasNext()) {
			System.out.println(itr.next()+".");
		}
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	
	public void checkIntegrity(Node masterNode) {
		// check if both lists are null/empty
//		assertFalse ( ((masterNode.parentsList == null) || (masterNode.parentsList.isEmpty())) &&
//				((masterNode.childrenList == null) || (masterNode.childrenList.isEmpty())) );
		assertFalse(masterNode.isDead());
		
		// parse parentsList; parent <- masterNode
		//if (masterNode.parentsList != null) {
		ListIterator<Node> pitr = masterNode.getParentsList().listIterator();
		while (pitr.hasNext()) {
			Node parentNode = pitr.next();
			assertTrue(parentNode.isLinkTo(masterNode));
			assertTrue(masterNode.isLinkFrom(parentNode));
		}
		//}

		//parse childrenList;  masterNode -> child
		//if (masterNode.childrenList != null) {
		Iterator<Node> citr = masterNode.getChildrenList().iterator();
		while (citr.hasNext()) {
			Node childNode = citr.next();
			assertTrue(childNode.isLinkFrom(masterNode));
			assertTrue(masterNode.isLinkTo(childNode));
		}
		//}
	}
}
