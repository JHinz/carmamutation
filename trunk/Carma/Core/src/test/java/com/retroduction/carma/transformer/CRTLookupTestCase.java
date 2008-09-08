package com.retroduction.carma.transformer;

import junit.framework.TestCase;

public class CRTLookupTestCase extends TestCase {

	public void testNoConflicts() {
		CRTLookup lookup = new CRTLookup();

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertNull(lookup.getEntries().get(1));
		assertNull(lookup.getEntries().get(2));

		CRTEntry entry00000 = new CRTEntry(0, 0, 0, 0, 0);
		lookup.addCRTEntry(entry00000);

		assertNull(lookup.getEntries().get(-1));
		assertEquals(entry00000, lookup.getEntries().get(0));
		assertEquals(0, lookup.getEntries().get(0).getStartPos());
		assertEquals(0, lookup.getEntries().get(0).getEndPos());
		assertEquals(0, lookup.getEntries().get(0).getStartPC());
		assertEquals(0, lookup.getEntries().get(0).getEndPC());
		assertEquals(0, lookup.getEntries().get(0).getFlags());
		assertNull(lookup.getEntries().get(1));
		assertNull(lookup.getEntries().get(2));
		assertNull(lookup.getEntries().get(3));

		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
		lookup.addCRTEntry(entry00120);

		assertNull(lookup.getEntries().get(-1));
		assertEquals(entry00000, lookup.getEntries().get(0));
		assertEquals(0, lookup.getEntries().get(0).getStartPos());
		assertEquals(0, lookup.getEntries().get(0).getEndPos());
		assertEquals(0, lookup.getEntries().get(0).getStartPC());
		assertEquals(0, lookup.getEntries().get(0).getEndPC());
		assertEquals(0, lookup.getEntries().get(0).getFlags());
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(0, lookup.getEntries().get(1).getStartPos());
		assertEquals(0, lookup.getEntries().get(1).getEndPos());
		assertEquals(1, lookup.getEntries().get(1).getStartPC());
		assertEquals(2, lookup.getEntries().get(1).getEndPC());
		assertEquals(0, lookup.getEntries().get(1).getFlags());
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(0, lookup.getEntries().get(2).getStartPos());
		assertEquals(0, lookup.getEntries().get(2).getEndPos());
		assertEquals(1, lookup.getEntries().get(2).getStartPC());
		assertEquals(2, lookup.getEntries().get(2).getEndPC());
		assertEquals(0, lookup.getEntries().get(2).getFlags());
		assertNull(lookup.getEntries().get(3));

	}

	public void testConflicts_SizesDifferent() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
		lookup.addCRTEntry(entry00120);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertNull(lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

		CRTEntry entry00130 = new CRTEntry(0, 0, 1, 3, 0);
		lookup.addCRTEntry(entry00130);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(entry00130, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

		CRTEntry entry00230 = new CRTEntry(0, 0, 2, 3, 0);
		lookup.addCRTEntry(entry00230);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(entry00230, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

		CRTEntry entry00010 = new CRTEntry(0, 0, 0, 1, 0);
		lookup.addCRTEntry(entry00010);

		assertNull(lookup.getEntries().get(-1));
		assertEquals(entry00010, lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(entry00230, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

	}

	public void testConflicts_MoreOverlapping_Same_Size() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry entry00180 = new CRTEntry(0, 0, 1, 6, 0);
		lookup.addCRTEntry(entry00180);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00180, lookup.getEntries().get(1));
		assertEquals(entry00180, lookup.getEntries().get(2));
		assertEquals(entry00180, lookup.getEntries().get(3));
		assertEquals(entry00180, lookup.getEntries().get(4));
		assertEquals(entry00180, lookup.getEntries().get(5));
		assertEquals(entry00180, lookup.getEntries().get(6));
		assertNull(lookup.getEntries().get(7));
		assertNull(lookup.getEntries().get(8));
		assertNull(lookup.getEntries().get(9));

		CRTEntry entry00380 = new CRTEntry(0, 0, 3, 8, 0);
		lookup.addCRTEntry(entry00380);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00180, lookup.getEntries().get(1));
		assertEquals(entry00180, lookup.getEntries().get(2));
		assertEquals(entry00180, lookup.getEntries().get(3));
		assertEquals(entry00180, lookup.getEntries().get(4));
		assertEquals(entry00180, lookup.getEntries().get(5));
		assertEquals(entry00180, lookup.getEntries().get(6));
		assertEquals(entry00380, lookup.getEntries().get(7));
		assertEquals(entry00380, lookup.getEntries().get(8));
		assertNull(lookup.getEntries().get(9));

	}

	public void testConflicts_MoreOverlapping_Different_Size_Different_Start() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry entry00180 = new CRTEntry(0, 0, 1, 6, 0);
		lookup.addCRTEntry(entry00180);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00180, lookup.getEntries().get(1));
		assertEquals(entry00180, lookup.getEntries().get(2));
		assertEquals(entry00180, lookup.getEntries().get(3));
		assertEquals(entry00180, lookup.getEntries().get(4));
		assertEquals(entry00180, lookup.getEntries().get(5));
		assertEquals(entry00180, lookup.getEntries().get(6));
		assertNull(lookup.getEntries().get(7));
		assertNull(lookup.getEntries().get(8));
		assertNull(lookup.getEntries().get(9));

		CRTEntry entry00380 = new CRTEntry(0, 0, 3, 4, 0);
		lookup.addCRTEntry(entry00380);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00180, lookup.getEntries().get(1));
		assertEquals(entry00180, lookup.getEntries().get(2));
		assertEquals(entry00380, lookup.getEntries().get(3));
		assertEquals(entry00380, lookup.getEntries().get(4));
		assertEquals(entry00180, lookup.getEntries().get(5));
		assertEquals(entry00180, lookup.getEntries().get(6));
		assertNull(lookup.getEntries().get(7));
		assertNull(lookup.getEntries().get(8));
		assertNull(lookup.getEntries().get(9));

	}

	public void testConflicts_MoreOverlapping_Different_Size_Same_Start_LongerOneFirst() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry entry00130 = new CRTEntry(0, 0, 1, 3, 0);
		lookup.addCRTEntry(entry00130);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00130, lookup.getEntries().get(1));
		assertEquals(entry00130, lookup.getEntries().get(2));
		assertEquals(entry00130, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
		lookup.addCRTEntry(entry00120);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(entry00130, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

	}

	public void testConflicts_MoreOverlapping_Different_Size_Same_Start_ShorterOneFirst() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
		lookup.addCRTEntry(entry00120);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertNull(lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

		CRTEntry entry00130 = new CRTEntry(0, 0, 1, 3, 0);
		lookup.addCRTEntry(entry00130);

		assertNull(lookup.getEntries().get(-1));
		assertNull(lookup.getEntries().get(0));
		assertEquals(entry00120, lookup.getEntries().get(1));
		assertEquals(entry00120, lookup.getEntries().get(2));
		assertEquals(entry00130, lookup.getEntries().get(3));
		assertNull(lookup.getEntries().get(4));

	}

	public void testConflicts_RFC1522_Line101() {
		CRTLookup lookup = new CRTLookup();

		CRTEntry e1 = new CRTEntry(0, 0, 6, 11, 0);
		lookup.addCRTEntry(e1);

		CRTEntry e2 = new CRTEntry(0, 0, 12, 14, 0);
		lookup.addCRTEntry(e2);

		CRTEntry e3 = new CRTEntry(0, 0, 15, 20, 0);
		lookup.addCRTEntry(e3);

		CRTEntry e4 = new CRTEntry(0, 0, 6, 20, 0);
		lookup.addCRTEntry(e4);

		CRTEntry e5 = new CRTEntry(0, 0, 21, 23, 0);
		lookup.addCRTEntry(e5);

		CRTEntry e6 = new CRTEntry(0, 0, 24, 33, 0);
		lookup.addCRTEntry(e6);

		CRTEntry e7 = new CRTEntry(0, 0, 24, 33, 0);
		lookup.addCRTEntry(e7);

		CRTEntry e8 = new CRTEntry(0, 0, 6, 33, 0);
		lookup.addCRTEntry(e8);

		assertNull(lookup.getEntries().get(5));
		assertEquals(e1, lookup.getEntries().get(6));
		assertEquals(e1, lookup.getEntries().get(7));
		assertEquals(e1, lookup.getEntries().get(8));
		assertEquals(e1, lookup.getEntries().get(9));
		assertEquals(e1, lookup.getEntries().get(10));
		assertEquals(e1, lookup.getEntries().get(11));
		assertEquals(e2, lookup.getEntries().get(12));
		assertEquals(e2, lookup.getEntries().get(13));
		assertEquals(e2, lookup.getEntries().get(14));
		assertEquals(e3, lookup.getEntries().get(15));
		assertEquals(e3, lookup.getEntries().get(16));
		assertEquals(e3, lookup.getEntries().get(17));
		assertEquals(e3, lookup.getEntries().get(18));
		assertEquals(e3, lookup.getEntries().get(19));
		assertEquals(e3, lookup.getEntries().get(20));
		assertEquals(e5, lookup.getEntries().get(21));
		assertEquals(e5, lookup.getEntries().get(22));
		assertEquals(e5, lookup.getEntries().get(23));
		assertEquals(e6, lookup.getEntries().get(24));
		assertEquals(e6, lookup.getEntries().get(25));
		assertEquals(e6, lookup.getEntries().get(26));
		assertEquals(e6, lookup.getEntries().get(27));
		assertEquals(e6, lookup.getEntries().get(28));
		assertEquals(e6, lookup.getEntries().get(29));
		assertEquals(e6, lookup.getEntries().get(30));
		assertEquals(e6, lookup.getEntries().get(31));
		assertEquals(e6, lookup.getEntries().get(32));
		assertEquals(e6, lookup.getEntries().get(33));
		assertNull(lookup.getEntries().get(34));

	}

}
