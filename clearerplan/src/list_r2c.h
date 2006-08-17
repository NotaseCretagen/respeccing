/****************************************************************************
*
*                             dmental links
*    Copyright (c) 28 Feb 2005 AtKaaZ, AtKaaZ at users.sourceforge.net
*
*  ========================================================================
*
*    This program is free software; you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation; either version 2 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program; if not, write to the Free Software
*    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*
*  ========================================================================
*
* Description: the part of demlinks which interfaces
*               Lists of <Referrers to Chain>
*
*    Practicaly a list of such kind contains referrers that refer to the same
*    Chain. (each of the referrers in the list refer to the same Chain)
*
****************************************************************************/


#ifndef LIST_R2C_H
#define LIST_R2C_H

#include "common.h"
#include "list.h"

class MListOfRef2Chain : public MListOfReferrers {
};


#endif
