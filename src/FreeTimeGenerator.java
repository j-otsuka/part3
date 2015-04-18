
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FreeTimeGenerator {

  /**
   * Private member variable that holds an ArrayList of icsEvents.
   */
  private ArrayList<icsEvent> eventList;
  
  /**
   * Default constructor method to to generate the FreeTimeGenerator object.
   */
  public FreeTimeGenerator(){
    this.eventList = new ArrayList<icsEvent>();
  }
  /**
   * @return eventList is returned (ArrayList<icsEvent>).
   */
  public ArrayList<icsEvent> getEventList() {
    return eventList;
  }
  /**
   * Reads in files and generates icsEvent objects
   * @param filename is the filename or path to a filename to be read into an object.
   * @return an icsEvent object is returned.
   */
  icsEvent readIcs(String filename){
      
      BufferedReader bReader = null;
      String line, summary = null, location = null, classification = null, priority = null, dtstart = null, dtend = null;
      
      try {
        bReader = new BufferedReader(new FileReader(filename));   
        line = bReader.readLine();
        line = bReader.readLine();
        line = bReader.readLine();
        line = bReader.readLine();
        summary = line;
        line = bReader.readLine();
        location = line;
        line = bReader.readLine();
        classification = line;
        line = bReader.readLine();
        priority = line;
        line = bReader.readLine();
        dtstart = line;
        line = bReader.readLine();
        dtend = line;
      } 
      catch (IOException e) {
        e.printStackTrace();
      } 
      finally {
        try {
          if (bReader != null) bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
          }
      }
    //Use the second constructor for the icsEvent object to construct the icsEvent object to return.
    icsEvent event = new icsEvent(summary, location, classification, priority, dtstart, dtend);
    return event;
  }
  
  /**
   * Method to add to the icsEvent ArrayList constructed by the FreeTimeGenerator, calls the ArrayList add method.
   * @param event is the icsEvent to be added.
   * @return true if the event is successfully added, false otherwise.
   */
  public boolean addIcsEvent(icsEvent event){
    boolean returnValue = false;
    boolean dateCheck = true;
    
    //Compare to all existing events in the list already.
    for (int i = 0; i < this.eventList.size(); i++){
      if (!(this.compareDate(eventList.get(i), event))){
        dateCheck = false;
      }
    }
    
    if (dateCheck){
      if (eventList.add(event)){
        returnValue = true;
      }
    }
    
    return returnValue;
  }
  
  /**
   * Get method that returns the start time's hour component of an event as an int.
   * @param event is an icsEvent to get the start time from.
   * @return startHour (int)
   */
  public int getDtstartHour(icsEvent event){
    int startHour = 0;
    
    startHour = Integer.parseInt(event.getDtstart().substring(17, 19));
    
    return startHour;
  }
  
  /**
   * Get method that returns the start time's date component of an event as an String.
   * @param event is an icsEvent to get the start date from.
   * @return String
   */
  public String getDtstartDate(icsEvent event){
    String startDate = "";
    
    startDate = event.getDtstart().substring(8, 16);
    
    return startDate;
  }
  
  /**
   * Get method that returns the end time's date component of an event as an String.
   * @param event is an icsEvent to get the end date from.
   * @return String
   */
  public String getDtendDate(icsEvent event){
    String endDate = "";
    
    endDate = event.getDtend().substring(6, 14);
    
    return endDate;
  }
  
  /**
   * Get method that returns the start time's minute component of an event as an int.
   * @param event is an icsEvent to get the start time from.
   * @return startMinute (int)
   */
  public int getDtstartMinute(icsEvent event){
    int startMinute = 0;
    
    startMinute = Integer.parseInt(event.getDtstart().substring(19, 21));
    
    return startMinute;
  }
  
  /**
   * Get method that returns the end time's hour component of an event as an int.
   * @param event is an icsEvent to get the end time from.
   * @return endTime (int)
   */
  public int getDtendHour(icsEvent event){
    int endHour = 0;
    
    endHour = Integer.parseInt(event.getDtend().substring(15, 17));
    
    return endHour;
  }
  
  /**
   * Get method that returns the end time's minute component of an event as an int.
   * @param event is an icsEvent to get the end time from.
   * @return endTime (int)
   */
  public int getDtendMinute(icsEvent event){
    int endMinute = 0;
    
    endMinute = Integer.parseInt(event.getDtend().substring(17, 19));
    
    return endMinute;
  }
  
  /**
   * Writes the body of an ICS file from an ics object
   * to a freetimeX.ics extension file where X is an arbitrary number denoting the copy.
   * @param icsList is an ArrayList<icsEvent> object
   * @return void
   */
  void writeFreeTime(ArrayList<icsEvent> icsList){
    byte[] freeTimeMap = mapFreeTime(icsList);
    int mapIndex = 0;
    int freeTimeCounter = 0;
    int startHour = 0, startMinute = 0;
    int endHour = 0, endMinute = 0;
    String stringStartHour, stringStartMinute;
    String stringEndHour, stringEndMinute;
    String dtstart, dtend;
    ArrayList<Integer> startTimes = new ArrayList<Integer>();
    ArrayList<Integer> endTimes = new ArrayList<Integer>();
    
    //Use two loops within a for loop to obtain free time blocks from the map.
    for (int i = 0; i < icsList.size(); i++){
    	
      //Store the current mapIndex as the start time of this free block.
      startTimes.add(mapIndex);
      
      //Count out the number of free minutes.
      while (freeTimeMap[mapIndex] != 1 && mapIndex < 1440){
        freeTimeCounter++;
        mapIndex++;
      }
      
      //Store the current mapIndex as the end time of this free block.
      endTimes.add(mapIndex);
      
      //Create the free time block and store it in freeTimeMinutes
      //Move mapIndex to the next free block.
      while(freeTimeMap[mapIndex] != 0 && mapIndex < 1440){
        mapIndex++;
      }
      
      startHour = (startTimes.get(i) / 60);
      startMinute = (startTimes.get(i) % 60);
      
      endHour = (endTimes.get(i) / 60);
      endMinute = (endTimes.get(i) % 60);
    
    
      //Build the string values for dtstart and dtend.
      if (startHour == 0){
        stringStartHour = "00";
      }
      else if (startHour > 0 && startHour < 10){
        stringStartHour = "0" + startHour;
      }
      else{
        stringStartHour = "" + startHour; 
      }
      
      if (startMinute == 0){
        stringStartMinute = "00";
      }
      else if (startMinute > 0 && startMinute < 10){
        stringStartMinute = "0" + startMinute;
      }
      else{
        stringStartMinute = "" + startMinute;
      }
      
      if (endHour == 0){
        stringEndHour = "00";
      }
      else if (endHour > 0 && endHour < 10){
        stringEndHour = "0" + endHour;
      }
      else{
        stringEndHour = "" + endHour; 
      }
      
      if (endMinute == 0){
        stringEndMinute = "00";
      }
      else if (endMinute > 0 && endMinute < 10){
        stringEndMinute = "0" + endMinute;
      }
      else{
        stringEndMinute = "" + endMinute;
      }
      
      dtstart = ("DTSTART:" + this.getDtstartDate(icsList.get(i)) + "T" + stringStartHour + stringStartMinute + "00");
      dtend = ("DTEND:" + this.getDtendDate(icsList.get(i)) + "T" + stringEndHour + stringEndMinute + "00");
      
      icsEvent freeTime = new icsEvent("SUMMARY:FREE TIME", "LOCATION:N/A", "CLASSIFICATION:PUBLIC", "PRIORITY:0", dtstart, dtend);
      
      if (Integer.parseInt(stringEndHour + stringEndMinute) - Integer.parseInt((stringStartHour + stringStartMinute)) != 0){
        freeTime.writeIcsFile(freeTime, ("freetime" + (i + 1) + ".ics"));        
      }
    }
  }
  
  /**
   * Writes the body of an ICS file from an ics object
   * to a meetingtimeX.ics extension file where X is an arbitrary number denoting the copy.
   * This differs from the writeFreeTime method in that it takes two people's 
   * event lists and compares them both for mutually free periods.
   * @param icsListA is an ArrayList<icsEvent> object, icsListB is an ArrayList<icsEvent> object
   * @return void
   */
  void writeMeetingTimes(ArrayList<icsEvent> icsListA, ArrayList<icsEvent> icsListB){
    byte[] freeTimeMapA = mapFreeTime(icsListA);
    byte[] freeTimeMapB = mapFreeTime(icsListB);
    int mapIndex = 0;
    int freeTimeCounter = 0;
    int startHour = 0, startMinute = 0;
    int endHour = 0, endMinute = 0;
    String stringStartHour, stringStartMinute;
    String stringEndHour, stringEndMinute;
    String dtstart, dtend;
    ArrayList<Integer> startTimes = new ArrayList<Integer>();
    ArrayList<Integer> endTimes = new ArrayList<Integer>();
    
    //Use two loops within a for loop to obtain free time blocks from the map.
    for (int i = 0; i < icsListA.size() || i < icsListB.size(); i++){
      //Store the current mapIndex as the start time of this free block.
      startTimes.add(mapIndex);
      
      //Count out the number of free minutes.
      while ((freeTimeMapA[mapIndex] != 1 && freeTimeMapB[mapIndex] != 1) && mapIndex < 1440){
        freeTimeCounter++;
        mapIndex++;
      }
      
      //Store the current mapIndex as the end time of this free block.
      endTimes.add(mapIndex);
      
      //Create the free time block and store it in freeTimeMinutes
      //Move mapIndex to the next free block.
      while((freeTimeMapA[mapIndex] != 0 && freeTimeMapB[mapIndex] != 0) && mapIndex < 1440){
        mapIndex++;
      }
      
      startHour = (startTimes.get(i) / 60);
      startMinute = (startTimes.get(i) % 60);
      
      endHour = (endTimes.get(i) / 60);
      endMinute = (endTimes.get(i) % 60);
    
    
      //Build the string values for dtstart and dtend.
      if (startHour == 0){
        stringStartHour = "00";
      }
      else if (startHour > 0 && startHour < 10){
        stringStartHour = "0" + startHour;
      }
      else{
        stringStartHour = "" + startHour; 
      }
      
      if (startMinute == 0){
        stringStartMinute = "00";
      }
      else if (startMinute > 0 && startMinute < 10){
        stringStartMinute = "0" + startMinute;
      }
      else{
        stringStartMinute = "" + startMinute;
      }
      
      if (endHour == 0){
        stringEndHour = "00";
      }
      else if (endHour > 0 && endHour < 10){
        stringEndHour = "0" + endHour;
      }
      else{
        stringEndHour = "" + endHour; 
      }
      
      if (endMinute == 0){
        stringEndMinute = "00";
      }
      else if (endMinute > 0 && endMinute < 10){
        stringEndMinute = "0" + endMinute;
      }
      else{
        stringEndMinute = "" + endMinute;
      }
      
      dtstart = ("DTSTART:" + this.getDtstartDate(icsListA.get(i)) + "T" + stringStartHour + stringStartMinute + "00");
      dtend = ("DTEND:" + this.getDtendDate(icsListA.get(i)) + "T" + stringEndHour + stringEndMinute + "00");
      
      icsEvent freeTime = new icsEvent("SUMMARY:POSSIBLE MEETING TIME", "LOCATION:TBA", "CLASSIFICATION:PUBLIC", "PRIORITY:0", dtstart, dtend);
      
      if (Integer.parseInt(stringEndHour + stringEndMinute) - Integer.parseInt((stringStartHour + stringStartMinute)) != 0){
        freeTime.writeIcsFile(freeTime, ("meetingtime" + (i + 1) + ".ics"));        
      }
    }
  }
  
  /**
   * This method uses a byte array of 1440 members, each representing a minute, and whether or not
   * that minute is taken by an event.
   * @param events is an ArrayList<icsEvent> of icsEvents to be mapped into the byte array.
   * @return freeTimeMap(byte[])
   */
  public byte[] mapFreeTime(ArrayList<icsEvent> events){
    byte[] freeTimeMap = new byte[1440];
    int startMinute, endMinute;
    //Initialize minute map to zero.
    for (int i = 0; i < 1440; i++){
      freeTimeMap[i] = 0;     
    }
    
    //Read in free time from events, and convert to raw minutes
    for(int j = 0; j < events.size(); j++){
      startMinute = (((this.getDtstartHour(events.get(j))) * 60) + this.getDtstartMinute(events.get(j)));
      endMinute = (((this.getDtendHour(events.get(j))) * 60) + this.getDtendMinute(events.get(j)));
      
      //Map a value of 1 to all minute slots in the freeTimeMap that are taken by an event.
      for (int mapIndex = startMinute; mapIndex < endMinute; mapIndex++){
        freeTimeMap[mapIndex] = 1;
      }
    }
    //Uncomment code block for debug purposes only.
    /*
    int minCounter = 0, openCounter = 0;
    for (int k = 0; k < 1440; k++)
    {
     if (freeTimeMap[k] == 0){
       openCounter++;
     }
     else{
       minCounter++;
     }
    }
    System.out.println("Free minutes: " + openCounter + "\nTaken minutes: " + minCounter);
    */
    return freeTimeMap;
  }
  /**
   * compareDate compares dates from two ICS files.
   * @param event1 is an icsEvent object to be compared with event2
   * @param event2 is an icsEvent object to be compared with event1
   * @return true if dates are equal to each other,
   *         false if dates are not equal.
   */
  public boolean compareDate(icsEvent event1, icsEvent event2){
    String date1 = null, date2 = null;
    boolean returnValue = false;
    
    date1 = event1.getDtstart().substring(8, 16);
    date2 = event2.getDtstart().substring(8, 16);
    System.out.println(date1 + "\n" + date2 + "\n");
    
    if (date1.equals(date2)){
      returnValue = true;
    }
    
    return returnValue;
  }
  
  /**
   * compareTime compares start times from two ICS files.
   * @param event1 is an icsEvent object to be compared with event2
   * @param event2 is an icsEvent object to be compared with event1
   * @return true if time1 is earlier than or equal to time22,
   *         false if time11 is later than time22.
   */
  public boolean compareTime(icsEvent event1, icsEvent event2){
    int time1 = 0, time2 = 0;
    boolean returnValue = false;
    
    time1 = Integer.parseInt(event1.getDtstart().substring(17, 21));
    time2 = Integer.parseInt(event2.getDtstart().substring(17, 21));
    //Uncomment for debug purposes only.
    //System.out.println(time1 + "\n" + time2 + "\n");
    
    if (time1 <= time2){
      returnValue = true;
    }
    //Uncomment for debug purposes only.
    //System.out.println(returnValue);
    return returnValue;
  }
  
  
  /**
   * Sorts an ArrayList of icsEvent objects.
   * @param eventList is an ArrayList of icsEvent objects.
   */
  public void sortEvents(ArrayList<icsEvent> eventList){
    boolean loopCheck = true;
    icsEvent temp = null;   

    while (loopCheck)
    {
      loopCheck = false;
      for(int i = 0;  i < eventList.size() -1; i++)
      {
        if (!(compareTime(eventList.get(i), eventList.get(i + 1))))
        {
          temp = eventList.get(i); //Store icsEvent to swap
          eventList.set(i, eventList.get(i + 1)); //Set index of i to the icsEvent at index i + 1
          eventList.set((i + 1), temp);
          loopCheck = true;
        } 
      }
    }
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String icsStringList = "";
    for (int i = 0; i < this.eventList.size(); i++){
      icsStringList += eventList.get(i).toString() + "\n";
    }
    return icsStringList;
  }
  
  
}
