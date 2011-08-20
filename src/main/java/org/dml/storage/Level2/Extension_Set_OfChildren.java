/**
 * 
 * Copyright (c) 2005-2011, AtKaaZ
 * All rights reserved.
 * this file is part of DemLinks
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * * Neither the name of 'DemLinks' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.dml.storage.Level2;

import org.dml.storage.commons.*;



/**
 * cannot have null elements in set for obvious reasons ie. null cannot be stored in database though user can define some value
 * to act as null but that's on a higher level<br>
 * allows self to be added to set, ie. circular allower<br>
 */
public class Extension_Set_OfChildren
		extends NodeGenericExtensions
		implements IExtension_Set
{
	
	/**
	 * set may already exist ie. have children<br>
	 * 
	 * @param store
	 * @param selfNode
	 *            non-null<br>
	 *            will be cloned(for now)<br>
	 */
	protected Extension_Set_OfChildren( final NodeGeneric selfNode ) {
		super( selfNode );
		// System.err.println( this.getClass() + " " + selfNode.getClass() );
		// boolean b = L0Set_OfChildren.class.equals( this.getClass() );
		// assert (b)||(!)&&(getSelf().equals( getSelfImpl()));
	}
	
	
	// /* (non-Javadoc)
	// * @see org.dml.storage.commons.NodeGenericCommon#isStillValid()
	// */
	// @Override
	// public boolean isStillValid() {
	// boolean ret1 = super.isStillValid();
	// ret2=
	// return ret1&& ret2;
	// }
	
	/**
	 * @param node
	 * @return true if already existed
	 */
	@Override
	public boolean ensureIsAddedToSet( final NodeGeneric node ) {
		assertIsStillValid();
		assert isValidChild( node );
		return getStorage().ensureVector( getSelf(), node );
	}
	
	
	@Override
	public boolean contains( final NodeGeneric node ) {
		// assertIsStillValid();
		// assert this.isValidChild( longIdent ); no
		return getStorage().isVector( getSelf(), node );
	}
	
	
	
	@Override
	public boolean remove( final NodeGeneric node ) {
		assertIsStillValid();
		assert isValidChild( node );
		return getStorage().removeVector( getSelf(), node );
	}
	
	
	@Override
	public boolean isValidChild( final NodeGeneric node ) {
		return ( null != node );// allowing add self to set //&& ( !self.equals( longIdent ) );
	}
	
	
	/**
	 * don't forget to call iter.close() when done<br>
	 * 
	 * @return
	 */
	@Override
	public IteratorGeneric_OnChildNodes getIterator() {
		// assertIsStillValid();
		return getStorage().getIterator_on_Children_of( getSelf() );
	}
	
	
	@Override
	public void clearAll() {
		// assertIsStillValid();
		// Q.ni();
		// TODO: implement this in another way, ie. by deleting that key? because deleting all its children kind of does that
		// exact thing, but slower, yes?
		final IteratorGeneric_OnChildNodes iter = getIterator();
		try {
			iter.deleteAll();
			assert iter.size() == 0;
			iter.success();
		} finally {
			iter.finished();
		}
		assert isEmpty();
	}
	
	
}
