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
package org.q;

import java.lang.Thread.UncaughtExceptionHandler;

import org.aspectj.ExceptionsHandling.ToE.swing.*;



/**
 *
 */
public abstract class Q
{
	
	private static final boolean	showFullInfo			= false;
	private static final boolean	infoEnabled				= false;
	
	// if true, note that some of them might get handled on top levels yet they will still remain reported on console
	// this here is implemented for the case when you suspect a finally is overwriting the "real" exception thrown in try
	private static final boolean	showAllThrownExceptions	= false;
	
	static {
		final UncaughtExceptionHandler eh = new ExHandlerForThoseThatAreNotWithinCallsIeMain();
		Thread.setDefaultUncaughtExceptionHandler( eh );
	}
	
	
	/**
	 * typical usage:<br>
	 * throw Q.cantClone();<br>
	 * use this in clone() methods<br>
	 * 
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static CloneNotSupportedException cantClone() throws CloneNotSupportedException {
		final CloneNotSupportedException cnse = new CloneNotSupportedException( "not implemented" );
		toTree( cnse );
		throw cnse;
	}
	
	
	public static void toTree( final Throwable t ) {
		TreeOfExceptions.getTreeOfExceptions().addException( t );
	}
	
	
	public static BadCallError ni() {
		throw Q.badCall( "not implemented" );
	}
	
	
	public static JUnitFailException fail() {
		final JUnitFailException jufe = new JUnitFailException( "" );
		toTree( jufe );
		throw jufe;
	}
	
	
	/**
	 * nn=notNull<br>
	 * typical usage: assert Q.nn(var);<br>
	 * 
	 * @param expectedNotNullObjectHere
	 * @return
	 */
	public static boolean nn( final Object expectedNotNullObjectHere ) {
		if ( null == expectedNotNullObjectHere ) {
			final NullPointerException npe = new NullPointerException( "non-null assumption failed" );
			toTree( npe );
			throw npe;
		} else {
			return true;
		}
	}
	
	
	/**
	 * @param t
	 * @return RethrownException
	 */
	public static RethrownException rethrow( final Throwable t ) {
		assert null != t;
		final RethrownException ex = new RethrownException( t );
		toTree( ex );
		throw ex;
	}
	
	
	// private static void showEx( final Throwable ex ) {
	// if ( showAllThrownExceptions ) {
	// ex.printStackTrace();
	// // System.err.println( ex );
	// }
	// }
	
	
	public static BadCallError badCall() {
		throw badCall( "" );
	}
	
	
	public static BadCallError badCall( final String msg, final Throwable cause ) {
		assert null != cause : "cause shouldn't be null";
		final BadCallError ex = new BadCallError( msg, cause );
		toTree( ex );
		throw ex;
	}
	
	
	/**
	 * @param msg
	 * @return never
	 */
	public static BadCallError badCall( final String msg ) {
		final BadCallError ex = new BadCallError( msg );
		toTree( ex );
		throw ex;
	}
	
	
	public static BugError bug() {
		throw bug( "" );
	}
	
	
	/**
	 * @param msg
	 * @return never
	 */
	public static BugError bug( final String msg ) {
		throw bug( msg, null );
	}
	
	
	public static BugError bug( final String msg, final Throwable cause ) {
		final BugError ex = new BugError( msg, cause );
		toTree( ex );
		throw ex;
	}
	
	
	/**
	 * @param msg
	 * @return true
	 */
	public static boolean warn( final String msg ) {
		Q.warn( msg, null );
		return true;
	}
	
	
	/**
	 * @param msg
	 * @param cause
	 */
	public static void warn( final String msg, final Throwable cause ) {
		// both params can be null
		final WarningException we = new WarningException( msg, cause );
		toTree( we );
		// we.printStackTrace();
	}
	
	
	/**
	 * FIXME: should prolly replace this with Logger or something; and check all places where it's used
	 * 
	 * @param msg
	 */
	public static void info( final String msg ) {
		Q.info( msg, null );
	}
	
	
	/**
	 * @param msg
	 * @param cause
	 */
	private static void info( final String msg, final Throwable cause ) {
		if ( infoEnabled ) {
			if ( Q.showFullInfo ) {
				final InfoException ie = new InfoException( msg, cause );
				toTree( ie );
				// .printStackTrace();
			} else {
				System.out.println( "INFO: " + msg + " ||| " + Thread.currentThread().getStackTrace()[3] );
			}
		}
	}
	
	
	
	/**
	 * use with `assert` before it<br>
	 * 
	 * @param _this
	 * @param sameOrSubclassObjectHere
	 * @return
	 */
	public static boolean assumeSameFamilyClasses( final Object _this, final Object sameOrSubclassObjectHere ) {
		assert null != _this;
		assert null != sameOrSubclassObjectHere;
		if ( _this.getClass().isAssignableFrom( sameOrSubclassObjectHere.getClass() ) ) {
			// if (is obj's class same or subclass of this's class)
			// if ( ( obj instanceof Pointer_ToChild ) ) {// means !(this class and all its subclasses)
			return true;
		}
		throw Q.badCall( "you're comparing two very different class types!(obj.class==" + sameOrSubclassObjectHere.getClass()
			+ " the object to compare should be same/subclass of this.class" );
	}
	
	
	public static boolean assumeSameExactClassElseThrow( final Object _this, final Object sameClassObjectHere ) {
		assert null != _this;
		assert null != sameClassObjectHere;
		if ( sameClassObjectHere.getClass() == _this.getClass() ) {
			return true;
		}
		throw Q.badCall( "the two objects are equals but different classes; " + "for these classes this is not valid!"
			+ "obj.class==" + sameClassObjectHere.getClass() );
	}
	
	
	
	public static boolean returnParamButIfTrueAssertSameClass( final boolean param, final Object _this,
																final Object sameClassObjectHere ) {
		if ( param ) {
			assumeSameExactClassElseThrow( _this, sameClassObjectHere );
		}
		return param;
	}
	
	
	public static boolean plainThrow( final RuntimeException childOfRTE ) {
		assert null != childOfRTE;
		throw childOfRTE;
	}
	
	
	public static boolean thro( final Throwable cause ) {
		assert null != cause;
		final ManuallyThrownException ex = new ManuallyThrownException( cause );
		toTree( ex );
		throw ex;
	}
	
	
	/**
	 * @param wrappedException
	 * @param ofThisExceptionType
	 * @return the plain exception
	 */
	public static boolean isBareException( final Throwable wrappedException,
											final Class<? extends Throwable> ofThisExceptionType ) {
		assert null != wrappedException;
		final Throwable unwrapped = Q.getBareException( wrappedException );
		assert null != unwrapped;
		return ofThisExceptionType == unwrapped.getClass();
	}
	
	
	public static boolean isBareException( final Throwable wrappedException ) {
		assert null != wrappedException;
		return !( wrappedException instanceof QException );// same or subclass of QException ?
		// return ( !Q.isThrownExceptionUsingQThro( wrappedException ) ) && ( !Q.isRethrownException( wrappedException ) );
	}
	
	
	/**
	 * returns the unwrapped (even if it was already unwrapped) exception
	 * 
	 * @param ex
	 * @return never null; if null it will just throw
	 */
	public static Throwable getBareException( final Throwable ex ) {
		assert null != ex;
		Throwable t = ex;// can be already bare
		while ( !Q.isBareException( t ) ) {
			t = t.getCause();// skip to next
			assert null != t : Q.bug( "the wraps encompased no real exception? bug somewhere "
				+ "OR you manually threw any of these `wrap` exception" );
		}
		return t;
	}
	
	
	// each thread has a different one
	private static ThreadLocal<Throwable>	lastPostponed	= new ThreadLocal<Throwable>();
	
	
	public static void postpone( final Throwable t ) {
		if ( Q.lastPostponed.get() == null ) {
			Q.lastPostponed.set( t );// set only the first one, ignore others until throwPostponedOnes()
		}
	}
	
	
	/**
	 * if there were any exceptions postponed with Q.postpone() it will Q.rethrow the first postponed one<br>
	 */
	public static void throwPostponedOnes() {
		final Throwable last = Q.lastPostponed.get();
		if ( last != null ) {
			Q.lastPostponed.set( null );
			Q.rethrow( last );
		}
	}
	
	
	/**
	 * show your position by dumping the StackTrace<br>
	 */
	public static void dumpStack() {
		StackDumper.dumpStack();
		// Thread.dumpStack();
	}
}
