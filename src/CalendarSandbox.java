
import java.io.IOException;

public class CalendarSandbox {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

     /*icsEvent party = new icsEvent();
     party.setSummary();
     party.setLocation();
     party.setClassification();
     party.setDtstart();
     party.setDtend();
     System.out.println(party.toString());
     System.out.println(party.validateDTime(party.getDtstart(), party.getDtend()));
     party.writeIcsFile(party, "party.ics");*/
    
    icsEvent test1 = new icsEvent("SUMMARY:Test1", "LOCATION:Test1", "CLASSIFICATION:PUBLIC", "PRIORITY:1", "DTSTART:20150405T080000", "DTEND:20150405T090000");
    icsEvent test2 = new icsEvent("SUMMARY:Test2", "LOCATION:Test2", "CLASSIFICATION:PUBLIC", "PRIORITY:1", "DTSTART:20150405T100000", "DTEND:20150405T110000");
    icsEvent test3 = new icsEvent("SUMMARY:Test3", "LOCATION:Test3", "CLASSIFICATION:PUBLIC", "PRIORITY:1", "DTSTART:20150405T120000", "DTEND:20150405T130000");
    icsEvent test4 = new icsEvent("SUMMARY:Test4", "LOCATION:Test4", "CLASSIFICATION:PUBLIC", "PRIORITY:1", "DTSTART:20150405T140000", "DTEND:20150405T150000");
    icsEvent test5 = new icsEvent("SUMMARY:Test5", "LOCATION:Test5", "CLASSIFICATION:PUBLIC", "PRIORITY:1", "DTSTART:20150405T225900", "DTEND:20150405T235900");
    
    test1.writeIcsFile(test1, "test1.ics");
    test2.writeIcsFile(test2, "test2.ics");
    test3.writeIcsFile(test3, "test3.ics");
    test4.writeIcsFile(test4, "test4.ics");
    test5.writeIcsFile(test5, "test5.ics");
    
    FreeTimeGenerator free = new FreeTimeGenerator();
    free.addIcsEvent(test3);
    free.addIcsEvent(test2);
    free.addIcsEvent(test1);
    free.addIcsEvent(test4);
    free.addIcsEvent(test5);
    
   // System.out.println(free.readIcs("party.ics").toString());
    //System.out.println(free.compareDate(free.readIcs("party.ics"), free.readIcs("party.ics")));
  //  System.out.println(free.compareTime(free.readIcs("party.ics"), free.readIcs("party.ics")));
    System.out.println(free.toString());
    
    free.sortEvents(free.getEventList());
    free.mapFreeTime(free.getEventList());
    
    System.out.println(free.toString());
    
    free.writeFreeTime(free.getEventList());
    free.writeMeetingTimes(free.getEventList(), free.getEventList());
  }  
}
