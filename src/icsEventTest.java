

import static org.junit.Assert.*;

import java.util.TimeZone;

import org.junit.Test;

public class icsEventTest {

	@Test
	public void test() {	
		// Testing the constructor defaults here
		icsEvent test = new icsEvent();
		assertEquals("BEGIN:VCALENDAR", test.getCalBeginTag());
		assertEquals("VERSION:2.0", test.getVersion());
		assertEquals("BEGIN:VEVENT", test.getEventBeginTag());
		assertEquals(null, test.getSummary());
		assertEquals(null, test.getLocation());
		assertEquals("CLASSIFICATION:PUBLIC", test.getClassification());
		assertEquals("PRIORITY:0", test.getPriority());
		assertEquals(null, test.getDtstart());
		assertEquals(null, test.getDtend());
		assertEquals("END:VEVENT", test.getEventEndTag());
		assertEquals("BEGIN:VTIMEZONE", test.getTzBeginTag());
		assertEquals("TZID:Pacific/Honolulu", test.getTzid()); // Also works with TimeZone.getDefault().getID()
		assertEquals("END:VTIMEZONE", test.getTzEndTag());
		assertEquals("END:VCALENDAR", test.getCalEndTag());
		
		// Making sure these are null when initialized
		assertNull(test.getSummary());
		assertNull(test.getLocation());
		assertNull(test.getDtstart());
		assertNull(test.getDtend());
		
		// Testing set methods here
		assertEquals(test.setClassification(), test.getClassification());
		assertEquals(test.setPriority(), test.getPriority());
		assertEquals(test.setSummary(), test.getSummary());
		assertEquals(test.setLocation(), test.getLocation());
		assertEquals(test.setDtstart(), test.getDtstart());
		assertEquals(test.setDtend(), test.getDtend());
		
		// Making sure that they are not null after calling the set methods
		assertNotNull(test.getSummary());
		assertNotNull(test.getLocation());
		assertNotNull(test.getDtstart());
		assertNotNull(test.getDtend());
		
		// Testing other methods here
		assertEquals(false, test.validateDTime("DTSTART:20150322T200000", "DTEND:20150322T160000"));
		assertEquals(true, test.validateDTime("DTSTART:20150322T1200000", "DTEND:20150322T160000"));
		assertEquals(true, test.writeIcsFile(test, "test.ics"));
		
		// Testing the constructor with presets here
		icsEvent party = new icsEvent("SUMMARY:Party", "LOCATION:School", "CLASSIFICATION:PUBLIC",
				"PRIORITY:0", "DTSTART:20150515T143000", "DTEND:20150515T233000");
		assertEquals("SUMMARY:Party", party.getSummary());
		assertEquals("LOCATION:School", party.getLocation());
		assertEquals("CLASSIFICATION:PUBLIC", party.getClassification());
		assertEquals("PRIORITY:0", party.getPriority());
		assertEquals("DTSTART:20150515T143000", party.getDtstart());
		assertEquals("DTEND:20150515T233000", party.getDtend());
		
		icsEvent otherParty = new icsEvent("SUMMARY:Party", "LOCATION:Someone's House", "CLASSIFICATION:PRIVATE",
				"PRIORITY:7", "DTSTART:20150515T163000", "DTEND:20150515T235900");
		assertEquals("SUMMARY:Party", otherParty.getSummary());
		assertEquals("LOCATION:Someone's House", otherParty.getLocation());
		assertEquals("CLASSIFICATION:PRIVATE", otherParty.getClassification());
		assertEquals("PRIORITY:7", otherParty.getPriority());
		assertEquals("DTSTART:20150515T163000", otherParty.getDtstart());
		assertEquals("DTEND:20150515T235900", otherParty.getDtend());
		
		// Testing free time constructor
		FreeTimeGenerator free = new FreeTimeGenerator();
		assertEquals(0, free.getEventList().size());
		assertTrue(free.getEventList().isEmpty());
		//free.getEventList().add(test);
		//assertEquals(1, free.getEventList().size());
		//assertFalse(free.getEventList().isEmpty());
		
		// Testing free time readFile method
		icsEvent study = free.readIcs("studyfinal.ics");
		assertEquals("BEGIN:VCALENDAR", study.getCalBeginTag());
		assertEquals("VERSION:2.0", study.getVersion());
		assertEquals("BEGIN:VEVENT", study.getEventBeginTag());
		assertEquals("SUMMARY:Studying for finals", study.getSummary());
		assertEquals("LOCATION:Hamilton Library", study.getLocation());
		assertEquals("CLASSIFICATION:PUBLIC", study.getClassification());
		assertEquals("PRIORITY:1", study.getPriority());
		assertEquals("DTSTART:20150508T180000", study.getDtstart());
		assertEquals("DTEND:20150508T220000", study.getDtend());
		assertEquals("END:VEVENT", study.getEventEndTag());
		assertEquals("BEGIN:VTIMEZONE", study.getTzBeginTag());
		assertEquals("TZID:Pacific/Honolulu", study.getTzid());
		assertEquals("END:VTIMEZONE", study.getTzEndTag());
		assertEquals("END:VCALENDAR", study.getCalEndTag());
		
		// Testing new methods
		FreeTimeGenerator example = new FreeTimeGenerator();
		example.addIcsEvent(party); // Refer to lines 55~62
		assertEquals(1, example.getEventList().size());
		assertFalse(example.getEventList().isEmpty());
		assertEquals(14, example.getDtstartHour(party));
		assertEquals("20150515", example.getDtstartDate(party));
		assertEquals(30, example.getDtstartMinute(party));
		assertEquals(23, example.getDtendHour(party));
		assertEquals("20150515", example.getDtendDate(party));
		assertEquals(30, example.getDtendMinute(party));
		assertEquals(true, example.compareDate(party, otherParty));
		assertEquals(true, example.compareTime(party, otherParty));
		assertEquals(false, example.compareDate(study, party));
		assertEquals(false, example.compareTime(otherParty, party));	
		
		
	}

}
