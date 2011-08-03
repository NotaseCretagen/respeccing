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



package org.dml.level040;



import org.dml.level010.Symbol;
import org.dml.level025.SetOfTerminalSymbols;
import org.dml.tools.RunTime;
import org.dml.tools.TwoKeyHashMap;
import org.references.Position;



/**
 * cannot have NULLs<br>
 * can have DUPs<br>
 * TODO: JUnit test
 * TODO: getNew, ensure, getExisting1&2
 */
public class ListOrderedOfSymbolsWithFastFind
		implements
		OrderedList
{
	
	private static final TwoKeyHashMap<Level040_DMLEnvironment, SetOfTerminalSymbols, ListOrderedOfSymbolsWithFastFind>	allListOOSWFFInstances	= new TwoKeyHashMap<Level040_DMLEnvironment, SetOfTerminalSymbols, ListOrderedOfSymbolsWithFastFind>();
	
	private final Level040_DMLEnvironment																				env;
	private final SetOfTerminalSymbols																					selfAsSet;
	// TODO accessors for the following(because they're so far cached instead of searched):
	private final SetOfTerminalSymbols																					cachedFastFindSet;
	private final ListOrderedOfSymbols																					cachedOrderedList;
	
	
	/**
	 * private constructor<br>
	 * caller must already have the 2 lists as children of self, prepared for us<br>
	 * 
	 * @param existingEnv
	 * @param existingSelf
	 * @param existingOrderedList
	 *            already existing created by the static method
	 * @param existingFastFindSet
	 *            -//-
	 */
	private ListOrderedOfSymbolsWithFastFind(
			Level040_DMLEnvironment existingEnv,
			SetOfTerminalSymbols existingSelf,
			ListOrderedOfSymbols existingOrderedList,
			SetOfTerminalSymbols existingFastFindSet )
	{
		
		RunTime.assumedNotNull(
								existingEnv,
								existingSelf,
								existingOrderedList,
								existingFastFindSet );
		env = existingEnv;
		selfAsSet = existingSelf;
		cachedOrderedList = existingOrderedList;
		cachedFastFindSet = existingFastFindSet;
		// this.internal_setName();
		RunTime.assumedTrue( this.isItself() );
	}
	

	/**
	 * override this and don't call super()
	 */
	protected
			boolean
			isItself()
	{
		
		RunTime.assumedNotNull(
								selfAsSet,
								env );
		RunTime.assumedTrue( env.isInitedSuccessfully() );
		return isListOrderedOfSymbolsWFF(
											env,
											selfAsSet.getAsSymbol() );
	}
	

	public static
			boolean
			isListOrderedOfSymbolsWFF(
										Level040_DMLEnvironment passedEnv,
										Symbol passedSelf )
	{
		
		RunTime.assumedNotNull(
								passedEnv,
								passedSelf );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		return passedEnv.allListsOOSWFF_Set.hasSymbol( passedSelf );
	}
	

	// /**
	// * override this and don't call super()
	// */
	// protected void internal_setName() {
	//
	// RunTime.assumedNotNull( selfAsSet );
	// RunTime.assumedFalse( env.allListsOOSWFF_Set.addToSet( selfAsSet.getAsSymbol() ) );
	// }
	
	private static
			void
			internal_setAsListOrderedOfSymbolsWFF(
													Level040_DMLEnvironment passedEnv,
													Symbol passedSelf )
	{
		
		RunTime.assumedNotNull(
								passedSelf,
								passedEnv );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		// was not set before
		RunTime.assumedFalse( passedEnv.allListsOOSWFF_Set.addToSet( passedSelf ) );
		RunTime.assumedTrue( isListOrderedOfSymbolsWFF(
														passedEnv,
														passedSelf ) );
	}
	

	@Override
	public
			Symbol
			getAsSymbol()
	{
		
		this.assumedValid();
		return selfAsSet.getAsSymbol();
	}
	

	private final static
			void
			registerInstance(
								Level040_DMLEnvironment env,
								SetOfTerminalSymbols passedSelf,
								ListOrderedOfSymbolsWithFastFind newOne )
	{
		
		RunTime.assumedNotNull(
								env,
								passedSelf,
								newOne );
		RunTime.assumedFalse( allListOOSWFFInstances.ensure(
																env,
																passedSelf,
																newOne ) );
	}
	

	private final static
			ListOrderedOfSymbolsWithFastFind
			getInstance(
							Level040_DMLEnvironment env,
							SetOfTerminalSymbols passedSelfSet )
	{
		
		RunTime.assumedNotNull(
								env,
								passedSelfSet );
		return allListOOSWFFInstances.get(
											env,
											passedSelfSet );
	}
	

	public static
			ListOrderedOfSymbolsWithFastFind
			getExistingListOOSWFF(
									Level040_DMLEnvironment passedEnv,
									Symbol existingSelfSymbol )
	{
		
		RunTime.assumedNotNull(
								passedEnv,
								existingSelfSymbol );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		
		if ( !isListOrderedOfSymbolsWFF(
											passedEnv,
											existingSelfSymbol ) )
		{
			RunTime.badCall( "existingSelfSymbol is not already a List" );
		}
		
		SetOfTerminalSymbols selfSet = passedEnv.getAsSet( existingSelfSymbol );
		RunTime.assumedTrue( selfSet.getAsSymbol() == existingSelfSymbol );
		
		// int tmpSize = selfSet.size();
		// if ( ( tmpSize != 0 ) && ( tmpSize != 2 ) ) {
		if ( selfSet.size() != 2 )
		{
			RunTime.bug( "bad existing listWFF. It should have exactly 2 kids" );
		}
		
		// let's see if we already had it in java, it can be in db but not in java yet though
		ListOrderedOfSymbolsWithFastFind ret = getInstance(
															passedEnv,
															selfSet );
		if ( null == ret )
		{
			// is not in java yet, but it is in storage
			Symbol existingOrderedListAsSymbol = passedEnv.findCommonTerminalForInitials(
																							existingSelfSymbol,
																							passedEnv.allListsOOS_Set
																									.getAsSymbol() );
			RunTime.assumedNotNull( existingOrderedListAsSymbol );
			
			// it has been established that there are only 2
			Symbol existingFFAsSymbol = selfSet.getSide( Position.FIRST );
			RunTime.assumedNotNull( existingFFAsSymbol );
			if ( existingFFAsSymbol == existingOrderedListAsSymbol )
			{
				// get next
				existingFFAsSymbol = selfSet.getSideOf(
														Position.AFTER,
														existingFFAsSymbol );
			}
			RunTime.assumedNotNull( existingFFAsSymbol );
			
			SetOfTerminalSymbols existingFFSet = passedEnv.getAsSet( existingFFAsSymbol );
			RunTime.assumedNotNull( existingFFSet );
			existingFFSet.assumedValid();
			
			// ensure:
			ListOrderedOfSymbols existingOrderedList = passedEnv.getExistingListOOS( existingOrderedListAsSymbol );
			RunTime.assumedNotNull( existingOrderedList );
			existingOrderedList.assumedValid();
			// consistency check:
			RunTime.assumedFalse( existingOrderedList.isNullAllowed() );
			RunTime.assumedTrue( selfSet.hasSymbol( existingFFSet.getAsSymbol() ) );
			RunTime.assumedTrue( selfSet.hasSymbol( existingOrderedList.getAsSymbol() ) );
			
			ret = new ListOrderedOfSymbolsWithFastFind(
														passedEnv,
														selfSet,
														existingOrderedList,
														existingFFSet );
			ret.assumedValid();
			registerInstance(
								passedEnv,
								selfSet,
								ret );
		}
		else
		{
			ret.assumedValid();
		}
		
		return ret;
	}
	

	public static
			ListOrderedOfSymbolsWithFastFind
			getExistingListOOSWFF(
									Level040_DMLEnvironment passedEnv,
									Symbol existingSelfSymbol,
									boolean passedAllowDUPs )
	{
		
		RunTime.assumedNotNull(
								passedEnv,
								existingSelfSymbol,
								passedAllowDUPs );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		
		ListOrderedOfSymbolsWithFastFind ret = getExistingListOOSWFF(
																		passedEnv,
																		existingSelfSymbol );
		RunTime.assumedNotNull( ret );
		// this is why this method exists:
		RunTime.assumedTrue( ret.isDUPAllowed() == passedAllowDUPs );
		return ret;
	}
	

	/**
	 * @param passedEnv
	 * @param existingSelfSymbol
	 * @param passedAllowDUPs
	 * @return
	 */
	public static
			ListOrderedOfSymbolsWithFastFind
			getNewListOOSWFF(
								Level040_DMLEnvironment passedEnv,
								Symbol existingSelfSymbol,
								boolean passedAllowDUPs )
	{
		
		RunTime.assumedNotNull(
								passedEnv,
								existingSelfSymbol,
								passedAllowDUPs );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		
		if ( isListOrderedOfSymbolsWFF(
										passedEnv,
										existingSelfSymbol ) )
		{
			RunTime.badCall( "existingSelfSymbol exists already as a List, use getExisting or ensure instead." );
		}
		
		SetOfTerminalSymbols selfSet = passedEnv.getAsSet( existingSelfSymbol );
		RunTime.assumedTrue( selfSet.getAsSymbol() == existingSelfSymbol );
		
		if ( selfSet.size() != 0 )
		{
			RunTime.bug( "should be empty" );
		}
		
		RunTime.assumedNull( getInstance(
											passedEnv,
											selfSet ) );
		
		// make new one
		Symbol newOrderedListAsSymbol = passedEnv.newUniqueSymbol();
		Symbol newFFAsSymbol = passedEnv.newUniqueSymbol();
		RunTime.assumedNotNull( newFFAsSymbol );
		
		// make new:
		// link self to those 2 new ones:
		RunTime.assumedFalse( selfSet.addToSet( newOrderedListAsSymbol ) );
		RunTime.assumedFalse( selfSet.addToSet( newFFAsSymbol ) );
		
		SetOfTerminalSymbols newFFSet = passedEnv.getAsSet( newFFAsSymbol );
		RunTime.assumedNotNull( newFFSet );
		newFFSet.assumedValid();
		
		ListOrderedOfSymbols newOrderedList = passedEnv.getNewListOOS(
																		newOrderedListAsSymbol,
																		false/* no nulls */,
																		passedAllowDUPs );
		RunTime.assumedNotNull( newOrderedList );
		newOrderedList.assumedValid();
		
		internal_setAsListOrderedOfSymbolsWFF(
												passedEnv,
												existingSelfSymbol );
		ListOrderedOfSymbolsWithFastFind ret = new ListOrderedOfSymbolsWithFastFind(
																						passedEnv,
																						selfSet,
																						newOrderedList,
																						newFFSet );
		ret.assumedValid();
		registerInstance(
							passedEnv,
							selfSet,
							ret );
		return ret;
	}
	

	/**
	 * @param passedEnv
	 * @param existingSelfSymbol
	 * @param passedAllowDUPs
	 * @return the new or existing list
	 */
	public static
			ListOrderedOfSymbolsWithFastFind
			ensureListOOSWFF(
								Level040_DMLEnvironment passedEnv,
								Symbol existingSelfSymbol,
								boolean passedAllowDUPs )
	{
		
		RunTime.assumedNotNull(
								passedEnv,
								existingSelfSymbol,
								passedAllowDUPs );
		RunTime.assumedTrue( passedEnv.isInitedSuccessfully() );
		
		if ( isListOrderedOfSymbolsWFF(
										passedEnv,
										existingSelfSymbol ) )
		{
			return getExistingListOOSWFF(
											passedEnv,
											existingSelfSymbol,
											passedAllowDUPs );
		}
		else
		{
			return getNewListOOSWFF(
										passedEnv,
										existingSelfSymbol,
										passedAllowDUPs );
		}
	}
	

	/**
	 * makes sure the Symbol exists, if it doesn't then append it<br>
	 * 
	 * @param whichSymbol
	 * @return
	 */
	@Override
	public
			boolean
			ensure(
					Symbol whichSymbol )
	{
		
		boolean ret = this.hasSymbol( whichSymbol );
		if ( !ret )
		{
			this.add(
						whichSymbol,
						Position.LAST );
		}
		RunTime.assumedTrue( this.hasSymbol( whichSymbol ) );
		return ret;
	}
	

	@Override
	public
			void
			add(
					Symbol whichSymbol,
					Position where )
	{
		
		RunTime.assumedNotNull(
								where,
								whichSymbol );
		
		this.internal_addToSet( whichSymbol );
		
		cachedOrderedList.add(
								whichSymbol,
								where );
	}
	

	private
			void
			internal_addToSet(
								Symbol whichSymbol )
	{
		
		RunTime.assumedNotNull( whichSymbol );
		if ( cachedFastFindSet.addToSet( whichSymbol ) )
		{
			if ( !this.isDUPAllowed() )
			{
				RunTime.badCall( "Symbol already existed and DUPs aren't allowed" );
			}
		}
	}
	

	@Override
	public
			void
			add(
					Symbol whichSymbol,
					Position pos,
					Symbol posSymbol )
	{
		
		RunTime.assumedNotNull(
								whichSymbol,
								pos,
								posSymbol );
		RunTime.assumedFalse( this.isDUPAllowed() );// don't allow dups with pos finding
		this.internal_addToSet( whichSymbol );
		cachedOrderedList.add(
								whichSymbol,
								pos,
								posSymbol );
	}
	

	@Override
	public
			void
			assumedValid()
	{
		
		RunTime.assumedNotNull(
								cachedFastFindSet,
								cachedOrderedList,
								selfAsSet );
		
		RunTime.assumedTrue( this.isItself() );
		cachedFastFindSet.assumedValid();
		
		if ( !this.isDUPAllowed() )
		{
			RunTime.assumedTrue( cachedFastFindSet.size() == cachedOrderedList.size() );
		}
		
		cachedOrderedList.assumedValid();
		RunTime.assumedFalse( cachedOrderedList.isNullAllowed() );
		
		RunTime.assumedTrue( selfAsSet.size() == 2 );
		
		RunTime.assumedTrue( selfAsSet.hasSymbol( cachedFastFindSet.getAsSymbol() ) );
		RunTime.assumedTrue( selfAsSet.hasSymbol( cachedOrderedList.getAsSymbol() ) );
	}
	

	@Override
	synchronized public
			Symbol
			get(
					Position pos )
	{
		
		RunTime.assumedNotNull( pos );
		return cachedOrderedList.get( pos );
	}
	

	@Override
	public
			Symbol
			get(
					Position pos,
					Symbol posSymbol )
	{
		
		RunTime.assumedNotNull(
								pos,
								posSymbol );
		RunTime.assumedFalse( this.isDUPAllowed() );
		if ( !cachedFastFindSet.hasSymbol( posSymbol ) )
		{
			RunTime.badCall( "posSymbol doesn't exist" );
		}
		return cachedOrderedList.get(
										pos,
										posSymbol );
	}
	

	/**
	 * this is the main method of this class
	 * 
	 * @param whichSymbol
	 * @return
	 */
	@Override
	public
			boolean
			hasSymbol(
						Symbol whichSymbol )
	{
		
		RunTime.assumedNotNull( whichSymbol );
		return cachedFastFindSet.hasSymbol( whichSymbol );
	}
	

	// TODO remove
	

	public
			boolean
			isDUPAllowed()
	{
		
		return cachedOrderedList.isDUPAllowed();
	}
	

	public
			boolean
			isNullAllowed()
	{
		
		RunTime.assumedFalse( cachedOrderedList.isNullAllowed() );
		return false;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.level040.OrderedList#size()
	 */
	@Override
	public
			long
			size()
	{
		
		RunTime.assumedNotNull(
								selfAsSet,
								cachedFastFindSet,
								cachedOrderedList );
		long size1 = cachedFastFindSet.size();
		long size2 = cachedOrderedList.size();
		RunTime.assumedTrue( size1 == size2 );
		return size1;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.level040.OrderedList#remove(org.references.Position, org.dml.level010.Symbol)
	 */
	@Override
	public
			Symbol
			remove(
					Position pos,
					Symbol posSymbol )
	{
		
		RunTime.assumedNotNull(
								pos,
								posSymbol );
		if ( this.isDUPAllowed() )
		{
			RunTime.badCall( "can't use this method while DUPs are on" );
		}
		
		long consistencyCheck = cachedOrderedList.size();
		Symbol removedSymbol = cachedOrderedList.remove(
															pos,
															posSymbol );
		if ( null != removedSymbol )
		{
			RunTime.assumedTrue( cachedFastFindSet.remove( removedSymbol ) );
		}
		else
		{
			// nothing was removed? making sure that remove() worked properly then
			RunTime.assumedTrue( consistencyCheck == cachedOrderedList.size() );
		}
		return removedSymbol;// can be null
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.level040.OrderedList#remove(org.references.Position)
	 */
	@Override
	public
			Symbol
			remove(
					Position pos )
	{
		
		RunTime.assumedNotNull( pos );
		long consistencyCheck = cachedOrderedList.size();
		Symbol removedSymbol = cachedOrderedList.remove( pos );
		if ( null != removedSymbol )
		{
			RunTime.assumedTrue( cachedFastFindSet.remove( removedSymbol ) );
		}
		else
		{
			// nothing was removed? making sure that remove() worked properly then
			RunTime.assumedTrue( consistencyCheck == cachedOrderedList.size() );
		}
		return removedSymbol;// can be null
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.level040.OrderedList#remove(org.dml.level010.Symbol)
	 */
	@Override
	public
			boolean
			remove(
					Symbol whichSymbol )
	{
		
		RunTime.assumedNotNull( whichSymbol );
		if ( !cachedFastFindSet.hasSymbol( whichSymbol ) )
		{
			return false;
		}
		
		RunTime.assumedTrue( cachedOrderedList.remove( whichSymbol ) );
		RunTime.assumedTrue( cachedFastFindSet.remove( whichSymbol ) );
		return true;
	}
}
