/**
 * File creation: Jun 17, 2009 6:54:03 PM
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


package org.dml.level1;



import java.io.File;

import org.dml.database.bdb.level1.Level1_Storage_BerkeleyDB;
import org.dml.storagewrapper.StorageException;
import org.dml.tools.MainLevel0;
import org.dml.tools.RunTime;
import org.dml.tools.VarLevel;
import org.references.method.MethodParams;
import org.references.method.PossibleParams;

import com.sleepycat.je.DatabaseException;



/**
 * should throw only StorageException.<br>
 * this is done mostly for wrapping Exceptions under StorageException<br>
 */
public class Level1_DMLStorage_BerkeleyDB extends MainLevel0 implements
		Level1_DMLStorageWrapper {
	
	@VarLevel
	private final Level1_Storage_BerkeleyDB	bdb						= null;
	
	private final static String				DEFAULT_BDB_ENV_PATH	= "."
																			+ File.separator
																			+ "bin"
																			+ File.separator
																			+ "mainEnv"
																			+ File.separator;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.MainLevel0#start(org.references.method.MethodParams)
	 */
	@Override
	protected void start( MethodParams<Object> params ) {

		// this method is not needed, but it's here for clarity
		super.start( params );
	}
	
	/**
	 * constructor, don't forget to call init(...)
	 */
	public Level1_DMLStorage_BerkeleyDB() {

		super();
	}
	
	@Override
	protected MethodParams<Object> getDefaults() {

		MethodParams<Object> def = super.getDefaults();
		
		def.set( PossibleParams.homeDir, DEFAULT_BDB_ENV_PATH );
		def.set( PossibleParams.jUnit_wipeDB, false );
		return def;
	}
	
	
	// =============================================
	@Override
	public final JavaID getJavaID( Symbol identifiedByThisSymbol )
			throws StorageException {

		RunTime.assumedNotNull( identifiedByThisSymbol );
		try {
			return bdb.getDBMap_JavaIDs_To_Symbols().getJavaID(
					identifiedByThisSymbol );
		} catch ( DatabaseException ex ) {
			throw new StorageException( ex );
		}
	}
	
	@Override
	public final Symbol getSymbol( JavaID identifiedByThisJavaID )
			throws StorageException {

		RunTime.assumedNotNull( identifiedByThisJavaID );
		try {
			return bdb.getDBMap_JavaIDs_To_Symbols().getSymbol( identifiedByThisJavaID );
		} catch ( DatabaseException dbe ) {
			throw new StorageException( dbe );
		}
	}
	
	@Override
	public final Symbol createSymbol( JavaID fromJavaID ) throws StorageException {

		RunTime.assumedNotNull( fromJavaID );
		try {
			return bdb.getDBMap_JavaIDs_To_Symbols().createSymbol( fromJavaID );
		} catch ( DatabaseException dbe ) {
			throw new StorageException( dbe );
		}
	}
	
	
	@Override
	public final Symbol ensureSymbol( JavaID theJavaID ) throws StorageException {

		RunTime.assumedNotNull( theJavaID );
		try {
			return bdb.getDBMap_JavaIDs_To_Symbols().ensureSymbol( theJavaID );
		} catch ( DatabaseException de ) {
			throw new StorageException( de );
		}
	}
	

}// end of class
