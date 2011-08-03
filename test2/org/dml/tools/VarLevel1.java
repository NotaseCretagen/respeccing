/**
 * 
 * Copyright (C) 2005-2010 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2010 UnKn <unkn@users.sourceforge.net>
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



package org.dml.tools;



import org.dml.tracking.Factory;
import org.references.method.MethodParams;



/**
 * 
 *
 */
public class VarLevel1 extends Initer implements VarLevel1Interface {
	
	
	public String getName() {

		return this.getClass().getSimpleName();
	}
	
	@Override
	public void sayHello() {

		System.out.println( this.getName() + " says Hello." );
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.StaticInstanceTracker#done()
	 */
	@Override
	protected void done( MethodParams params ) {

		System.out.println( this.getName() + " DeInited." );
		
	}
	
	@Override
	protected void start( MethodParams params ) {

		System.out.println( this.getName() + " inited." );
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.VarLevel1Interface#sneakyDeInit()
	 */
	@Override
	public void sneakyDeInit() {

		Factory.deInit( this );
	}
	
}
