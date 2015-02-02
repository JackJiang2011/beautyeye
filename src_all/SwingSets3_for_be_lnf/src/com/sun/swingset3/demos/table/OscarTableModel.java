/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.swingset3.demos.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Data model for oscar candidate data: a list of OscarCandidate beans.
 *
 * @author aim
 */

public class OscarTableModel extends AbstractTableModel {
    public static final int CATEGORY_COLUMN = 0;
    public static final int YEAR_COLUMN = 1;
    public static final int WINNER_COLUMN = 2;
    public static final int MOVIE_COLUMN = 3;
    public static final int PERSONS_COLUMN = 4;
    public static final int COLUMN_COUNT = 5;

    private final List<OscarCandidate> candidates = new ArrayList<OscarCandidate>();

    public void add(List<OscarCandidate> newCandidates) {
        int first = candidates.size();
        int last = first + newCandidates.size() - 1;
        candidates.addAll(newCandidates);
        fireTableRowsInserted(first, last);
    }

    public void add(OscarCandidate candidate) {
        int index = candidates.size();
        candidates.add(candidate);
        fireTableRowsInserted(index, index);
    }

    public int getRowCount() {
        return candidates.size();
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public OscarCandidate getCandidate(int row) {
        return candidates.get(row);
    }

    public Object getValueAt(int row, int column) {
        OscarCandidate oscarCandidate = candidates.get(row);
        switch (column) {
            case CATEGORY_COLUMN:
                return oscarCandidate.getCategory();
            case YEAR_COLUMN:
                return oscarCandidate.getYear();
            case MOVIE_COLUMN:
                return oscarCandidate.getMovieTitle();
            case WINNER_COLUMN:
                return oscarCandidate.isWinner() ? Boolean.TRUE : Boolean.FALSE;
            case PERSONS_COLUMN:
                return oscarCandidate.getPersons();
        }
        return null;
    }
}