/**
 * File creation: Oct 19, 2009 11:30:51 PM
 * 
 * Copyright (C) 2005-2009 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2009 UnKn <unkn@users.sourceforge.net>
 * 
 * This file and its contents are part of DeMLinks.
 * 
 * DeMLinks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DeMLinks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DeMLinks. If not, see <http://www.gnu.org/licenses/>.
 */


package org.dml.level3;



import org.dml.level1.Symbol;
import org.dml.tools.RunTime;



/**
 * list of NodeIDs in which order matters and it's known<br>
 * should be able to hold any number of NodeIDs even if they repeat inside the
 * list<br>
 * the order of insertion is kept<br>
 * this will be a double linked list represented in DMLEnvironment<br>
 * this is level 4
 */
public class ListOrderedOfSymbols extends ListOrderedOfElementCapsules {
	
	
	public ListOrderedOfSymbols( Level3_DMLEnvironment l3_DMLEnv, Symbol name1 ) {

		super( l3_DMLEnv, name1 );
	}
	
	@Override
	protected void internal_setName() {

		envL3.ensureVector( envL3.allListsSymbol, name );
	}
	
	@Override
	protected boolean internal_hasNameSetRight() {

		return envL3.isVector( envL3.allListsSymbol, name );
	}
	
	

	synchronized public void addLast( Symbol whichSymbol ) {

		ElementCapsule ec = internal_encapsulateSymbol( whichSymbol );
		
		if ( this.isEmpty() ) {
			RunTime.assumedTrue( getFirstCapsule() == null );
			RunTime.assumedTrue( getLastCapsule() == null );
			// we don't have a last thus list is empty
			// internal_setLast( ec ); is common below
			internal_setFirst( ec );
			// now list has 1 element
		} else {
			// list has a last, so at least 1 element
			ElementCapsule last = getLastCapsule();
			last.setNextCapsule( ec );// auto set ec.prev=last;
			RunTime.assumedTrue( ec.getPrevCapsule() == last );
		}
		
		internal_setLast( ec );
	}
	
	public void addFirst( Symbol whichSymbol ) {

	}
}
