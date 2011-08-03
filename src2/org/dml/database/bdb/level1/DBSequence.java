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



package org.dml.database.bdb.level1;



import org.dml.tools.Initer;
import org.dml.tools.RunTime;
import org.references.method.MethodParams;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Sequence;
import com.sleepycat.je.SequenceConfig;



/**
 * it's a BerkeleyDB database that stores any and all Sequences<br>
 * 
 * we need a BerkeleyDB to store sequences for all other databases<br>
 * each sequence is gotten via a key, and it's only one key->data pair per
 * sequence
 * that is, a sequence used for another database will only have one key and
 * one data in this BerkeleyDB
 */
public class DBSequence extends Initer {
	
	

	// FIXME: maybe we don't want to keep it same for all?
	// SequenceConfig will be kept the same for all Sequence -s, for now;that is
	// same settings not same variable
	private final SequenceConfig			allSequencesConfig	= new MySequenceConfig();
	


	private static final String				seqPrefix			= (char)0 + "_preseq_" + (char)0;
	private static final String				seqSuffix			= (char)255 + "_postseq_" + (char)255;
	
	// non-static follows:
	private Sequence						thisSeq				= null;
	private String							thisSeqName			= null;
	private final Level1_Storage_BerkeleyDB	bdb;
	
	
	/**
	 * constructor
	 * 
	 * @param seqName
	 * @throws DatabaseException
	 */
	public DBSequence( Level1_Storage_BerkeleyDB bdb1, String seqName ) {

		RunTime.assumedNotNull( bdb1 );
		RunTime.assumedNotNull( seqName );
		RunTime.assumedFalse( seqName.isEmpty() );
		bdb = bdb1;
		thisSeqName = seqPrefix + seqName + seqSuffix;
	}
	
	/**
	 * @return
	 * @throws DatabaseException
	 */
	public Sequence getSequence() throws DatabaseException {

		if ( null == thisSeq ) {
			RunTime.assumedNotNull( bdb );
			thisSeq = bdb.getNewSequence( thisSeqName, allSequencesConfig );
			RunTime.assumedNotNull( thisSeq );
		}
		return thisSeq;
	}
	
	
	@Override
	protected void done( MethodParams params ) {

		try {
			if ( null != thisSeq ) {
				RunTime.assumedNotNull( bdb, thisSeqName );
				thisSeq = bdb.closeAnySeq( thisSeq, thisSeqName );
			}
		} catch ( Throwable t ) {
			RunTime.throPostponed( t );
		}
		RunTime.throwAllThatWerePostponed();
	}
	
	@Override
	protected void start( MethodParams params ) {

		RunTime.assumedNull( params );
		
	}
}
