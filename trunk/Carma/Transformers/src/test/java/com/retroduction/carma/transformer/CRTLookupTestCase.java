package com.retroduction.carma.transformer;

import junit.framework.TestCase;

public class CRTLookupTestCase extends TestCase {

	public void testNoConflicts() {
//		CRTLookup lookup = new CRTLookup();
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertNull(lookup.getEntry(1));
//		assertNull(lookup.getEntry(2));
//
//		CRTEntry entry00000 = new CRTEntry(0, 0, 0, 0, 0);
//		lookup.addCRTEntry(entry00000);
//
//		assertNull(lookup.getEntry(-1));
//		assertEquals(entry00000, lookup.getEntry(0));
//		assertEquals(0, lookup.getEntry(0).getStartPos());
//		assertEquals(0, lookup.getEntry(0).getEndPos());
//		assertEquals(0, lookup.getEntry(0).getStartPC());
//		assertEquals(0, lookup.getEntry(0).getEndPC());
//		assertEquals(0, lookup.getEntry(0).getFlags());
//		assertNull(lookup.getEntry(1));
//		assertNull(lookup.getEntry(2));
//		assertNull(lookup.getEntry(3));
//
//		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
//		lookup.addCRTEntry(entry00120);
//
//		assertNull(lookup.getEntry(-1));
//		assertEquals(entry00000, lookup.getEntry(0));
//		assertEquals(0, lookup.getEntry(0).getStartPos());
//		assertEquals(0, lookup.getEntry(0).getEndPos());
//		assertEquals(0, lookup.getEntry(0).getStartPC());
//		assertEquals(0, lookup.getEntry(0).getEndPC());
//		assertEquals(0, lookup.getEntry(0).getFlags());
//		assertEquals(entry00120, lookup.getEntry(1));
//		assertEquals(0, lookup.getEntry(1).getStartPos());
//		assertEquals(0, lookup.getEntry(1).getEndPos());
//		assertEquals(1, lookup.getEntry(1).getStartPC());
//		assertEquals(2, lookup.getEntry(1).getEndPC());
//		assertEquals(0, lookup.getEntry(1).getFlags());
//		assertEquals(entry00120, lookup.getEntry(2));
//		assertEquals(0, lookup.getEntry(2).getStartPos());
//		assertEquals(0, lookup.getEntry(2).getEndPos());
//		assertEquals(1, lookup.getEntry(2).getStartPC());
//		assertEquals(2, lookup.getEntry(2).getEndPC());
//		assertEquals(0, lookup.getEntry(2).getFlags());
//		assertNull(lookup.getEntry(3));
//
//	}
//
//	public void testConflicts_SizesDifferent() {
//		CRTLookup lookup = new CRTLookup();
//
//		CRTEntry entry00120 = new CRTEntry(0, 0, 1, 2, 0);
//		lookup.addCRTEntry(entry00120);
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertEquals(entry00120, lookup.getEntry(1));
//		assertEquals(entry00120, lookup.getEntry(2));
//		assertNull(lookup.getEntry(3));
//		assertNull(lookup.getEntry(4));
//
//		CRTEntry entry00130 = new CRTEntry(0, 0, 1, 3, 0);
//		lookup.addCRTEntry(entry00130);
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertEquals(entry00120, lookup.getEntry(1));
//		assertEquals(entry00120, lookup.getEntry(2));
//		assertEquals(entry00130, lookup.getEntry(3));
//		assertNull(lookup.getEntry(4));
//
//		CRTEntry entry00230 = new CRTEntry(0, 0, 2, 3, 0);
//		lookup.addCRTEntry(entry00230);
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertEquals(entry00120, lookup.getEntry(1));
//		assertEquals(entry00120, lookup.getEntry(2));
//		assertEquals(entry00230, lookup.getEntry(3));
//		assertNull(lookup.getEntry(4));
//
//		CRTEntry entry00010 = new CRTEntry(0, 0, 0, 1, 0);
//		lookup.addCRTEntry(entry00010);
//
//		assertNull(lookup.getEntry(-1));
//		assertEquals(entry00010, lookup.getEntry(0));
//		assertEquals(entry00120, lookup.getEntry(1));
//		assertEquals(entry00120, lookup.getEntry(2));
//		assertEquals(entry00230, lookup.getEntry(3));
//		assertNull(lookup.getEntry(4));
//
//	}
//
//	public void testConflicts_MoreOverlapping() {
//		CRTLookup lookup = new CRTLookup();
//
//		CRTEntry entry00180 = new CRTEntry(0, 0, 1, 6, 0);
//		lookup.addCRTEntry(entry00180);
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertEquals(entry00180, lookup.getEntry(1));
//		assertEquals(entry00180, lookup.getEntry(2));
//		assertEquals(entry00180, lookup.getEntry(3));
//		assertEquals(entry00180, lookup.getEntry(4));
//		assertEquals(entry00180, lookup.getEntry(5));
//		assertEquals(entry00180, lookup.getEntry(6));
//		assertNull(lookup.getEntry(7));
//		assertNull(lookup.getEntry(8));
//		assertNull(lookup.getEntry(9));
//
//		CRTEntry entry00380 = new CRTEntry(0, 0, 3, 8, 0);
//		lookup.addCRTEntry(entry00380);
//
//		assertNull(lookup.getEntry(-1));
//		assertNull(lookup.getEntry(0));
//		assertEquals(entry00180, lookup.getEntry(1));
//		assertEquals(entry00180, lookup.getEntry(2));
//		assertEquals(entry00180, lookup.getEntry(3));
//		assertEquals(entry00180, lookup.getEntry(4));
//		assertEquals(entry00180, lookup.getEntry(5));
//		assertEquals(entry00180, lookup.getEntry(6));
//		assertEquals(entry00380, lookup.getEntry(7));
//		assertEquals(entry00380, lookup.getEntry(8));
//		assertNull(lookup.getEntry(9));
//
	}

}
