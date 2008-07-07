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
