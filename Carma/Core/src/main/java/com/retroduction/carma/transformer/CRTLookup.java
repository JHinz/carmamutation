/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */
package com.retroduction.carma.transformer;

import java.util.HashMap;

public class CRTLookup {

	private HashMap<Integer, CRTEntry> entryList = new HashMap<Integer, CRTEntry>();

	public void addCRTEntry(CRTEntry entry) {
		
		if ( entry.getStartLine()==101) {
			System.out.println();
		}

		for (int pc = entry.getStartPC(); pc <= entry.getEndPC(); pc++) {

			if (!entryList.containsKey(pc)) {
				entryList.put(pc, entry);
			} else {
				CRTEntry originalEntry = entryList.get(pc);
				if (entry.getEndPC() - entry.getStartPC() < originalEntry
						.getEndPC()
						- originalEntry.getStartPC()) {
					entryList.put(pc, entry);
				}

			}
		}
	}

	public java.util.Map<Integer, CRTEntry> getEntries() {
		return entryList;
	}

}
