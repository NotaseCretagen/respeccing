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
* Description: provides personalized FIFO notify tracking capab@source level
*
****************************************************************************/

#include <stdlib.h>
#include <stdio.h>

#include "pnotetrk.h"

/* global uniq variable, this is where we keep all errors
 * this is used in almost all source files */
MNotifyTracker *gNotifyTracker;

/*  the textual descriptions of notification types
 *  ie. "WARN" */
const PChar_t kNotifyDescriptions[kNumNotifyTypes]={
        "NONE",
        "WARN",
        "ERR",
        "EXIT",
        "INFO",
        "LAMER", /* most prolly developer's fault */

        /* triggered by a paranoid check(which should always be false) */
        "FATAL"
};


/* constructor */
MNotifyTracker::MNotifyTracker()
{
}

/* destructor */
MNotifyTracker::~MNotifyTracker()
{
        /* show them all if we didn't got the chance */
        if (GetLastNote())
                ShowAllNotes();
}

/* adds a notification and checks to see if we failed to properly add it
 * if so displays a message and aborts the running program */
void
CheckedAddNote(
        const NotifyType_t a_NotifyType,
        PChar_t a_Desc,
        File_t a_FileName,
        Func_t a_Func,
        const Line_t a_Line)
{
        if (!gNotifyTracker) {
                fprintf(stderr,"lame programmer: notification subsystem is "
                                "not initialized\n");
                abort();
        }

        bool tmpres=gNotifyTracker->AddUserNote(a_NotifyType,
                                                a_Desc,
                                                a_FileName,
                                                a_Func,
                                                a_Line);

        if ((tmpres==false) && (gNotifyTracker->HasFailedInternally())) {

                fprintf(stderr,
                        "The NotifyTracker has failed internally.\n"
                        "This is perhaps due to lack of memory.\n"
                        "However we're going to try and show you %d"
                        "notifications before aborting...\n",
                        gNotifyTracker->GetNumNotes());

                gNotifyTracker->ShowAllNotes();

                abort();
        }

}

/* show a list of all notifications that happened since last time the list was
 * empty */
void
MNotifyTracker::ShowAllNotes()
{
        NotifyItem_st *tmp=GetLastNote();
        while (tmp){
                fprintf(stderr,
                        "%s#%d: `%s' at line %u in file %s\n\tfunc: %s\n",
                        kNotifyDescriptions[tmp->Contents.Type],
                        tmp->Contents.Depth,
                        tmp->Contents.UserDesc,
                        tmp->Contents.Line,
                        tmp->Contents.File,
                        tmp->Contents.Func);
                ClearLastNote();
                tmp=GetLastNote();
        }
}


void
InitNotifyTracker()
{
        gNotifyTracker=new MNotifyTracker;
        if (!gNotifyTracker) {
                fprintf(stderr,
                        "error allocating memory\n"
                        "in file %s at line %d in func %s\n",
                        __FILE__,
                        __LINE__,
                        __func__);
                abort();
        }

        /* make sure the sutdown procedure is called on normal exit, even if the
         * programmer forgets to call it */
        ERR_IF( 0 != atexit(ShutDownNotifyTracker),
                        abort());
}

void
ShutDownNotifyTracker(void)
{
        if (gNotifyTracker) {
                if (gNotifyTracker->GetLastNote() != NULL) {
                        gNotifyTracker->ShowAllNotes();
                        fprintf(stderr,
                                "Notice: Read the above errors in reverse order"
                                        " of appearence!\n");
                }
                delete gNotifyTracker;
                gNotifyTracker=NULL;
        }
}

void
PurgeAllNotifications()
{
        if (gNotifyTracker)
                gNotifyTracker->PurgeThemAll();
}

void
ShowAllNotifications()
{
        if (gNotifyTracker)
                gNotifyTracker->ShowAllNotes();
}

