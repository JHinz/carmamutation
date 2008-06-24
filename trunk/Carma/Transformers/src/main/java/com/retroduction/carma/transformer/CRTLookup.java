package com.retroduction.carma.transformer;

import java.util.HashMap;

public class CRTLookup {

	private HashMap<Integer, CRTEntry> entryList = new HashMap<Integer, CRTEntry>();

	public void addCRTEntry(CRTEntry entry) {

		if (!entryList.containsKey(entry.getStartPC())) {
			entryList.put(entry.getStartPC(), entry);
		}

	}

	public java.util.Map<Integer, CRTEntry> getEntries() {
		return entryList;
	}

}
