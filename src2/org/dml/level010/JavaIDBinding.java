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



package org.dml.level010;



import org.dml.tools.RunTime;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;



/**
 * stores strings in UTF-8 encoding
 * 
 */
public class JavaIDBinding
		extends
		TupleBinding<JavaID>
{
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sleepycat.bind.tuple.TupleBinding#entryToObject(com.sleepycat.bind
	 * .tuple.TupleInput)
	 */
	@Override
	public
			JavaID
			entryToObject(
							TupleInput input )
	{
		
		RunTime.assumedNotNull( input );
		// Data must be read in the same order that it was
		// originally written.
		String strJavaID = input.readString();
		RunTime.assumedNotNull( strJavaID );
		JavaID myJavaID = JavaID.ensureJavaIDFor( strJavaID );
		RunTime.assumedNotNull( myJavaID );
		return myJavaID;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sleepycat.bind.tuple.TupleBinding#objectToEntry(java.lang.Object,
	 * com.sleepycat.bind.tuple.TupleOutput)
	 */
	@Override
	public
			void
			objectToEntry(
							JavaID object,
							TupleOutput output )
	{
		
		RunTime.assumedNotNull(
								object,
								output );
		String strJavaID = object.getObject();
		RunTime.assumedNotNull( strJavaID );
		// System.out.println( object );
		// it will never be null before writing it to dbase, else bug somewhere
		output.writeString( strJavaID );
	}
	
}
