

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EventGenerator
 *
 * 
 *
 */
public class icsEvent {
  
  /**
   * Private Scanner object to be used for get methods.
   */
  private Scanner keybd = new Scanner(System.in);
  /**
   * Private member variable to hold the 'BEGIN:VCALENDAR' tag.
   */
  private String calBeginTag;
  
  /**
   * Private member variable to hold the version specification, which is 2.0 by default.
   */
  private String version;
  
  /**
   * Private member variable to hold the 'BEGIN:VEVENT' tag.
   */
  private String eventBeginTag;
  
  /**
   * Private member variable to hold the summary of the event.
   */
  private String summary;
  
  /**
   * Private member variable to hold the location of the event.
   */
  private String location;
  
  /**
   * Private member variable to hold the classification of an event (public, private, or confidential).
   * By default, this value is set to 'PUBLIC'.
   */
  private String classification;
  
  /**
   * Private member variable to hold the priority of an event (0 is no priority, 1 is highest priority, down to 9,
   * which is lowest priority).
   * By default, this value is set to 0 (no priority).
   */
  private String priority;
  
  /**
   * Private member variable to hold the DTSTART information (the start time of the event, in 
   * yyyymmddThhmmss format, e.g. 20150405T120000).
   */
  private String dtstart;
  
  /**
   * Private member variable to hold the DTEND information (the end time of the event, in 
   * yyyymmddThhmmss format, e.g. 20150405T120000).
   */
  private String dtend;
  
  /**
   * Private member variable to hold the 'END:VEVENT' tag.
   */
  private String eventEndTag;
  
  /**
   * Private member variable to hold the time zone begin tag ('BEGIN:VTIMEZONE').
   */
  private String tzBeginTag;
  
  /**
   * Private member variable to hold the time zone identifier tag, which by default is set to a value
   * retrieved from a user's computer time zone settings.
   */
  private String tzid;
  
  /**
   * Private member variable to hold the time zone end tag ('END:VTIMEZONE').
   */
  private String tzEndTag;
  
  /**
   * Private member variable to hold the 'END:VCALENDAR' tag.
   */
  private String calEndTag;
  
  /**
   * Default constructor for an icsEvent object.
   */
  public icsEvent()
  {
    this.calBeginTag = "BEGIN:VCALENDAR";
    this.version = "VERSION:2.0";
    this.eventBeginTag = "BEGIN:VEVENT";
    this.summary = null;
    this.location = null;
    this.classification = "CLASSIFICATION:PUBLIC";
    this.priority = "PRIORITY:0";
    this.dtstart = null;
    this.dtend = null;
    this.eventEndTag = "END:VEVENT";
    this.tzBeginTag = "BEGIN:VTIMEZONE";
    this.tzid = "TZID:" + TimeZone.getDefault().getID();
    this.tzEndTag = "END:VTIMEZONE";
    this.calEndTag = "END:VCALENDAR";
  }

  /**
   * Default constructor for an icsEvent object.
   */
  public icsEvent(String summary, String location, String classification, String priority, String dtstart, String dtend)
  {
    this.calBeginTag = "BEGIN:VCALENDAR";
    this.version = "VERSION:2.0";
    this.eventBeginTag = "BEGIN:VEVENT";
    this.summary = summary;
    this.location = location;
    this.classification = classification;
    this.priority = priority;
    this.dtstart = dtstart;
    this.dtend = dtend;
    this.eventEndTag = "END:VEVENT";
    this.tzBeginTag = "BEGIN:VTIMEZONE";
    this.tzid = "TZID:" + TimeZone.getDefault().getID();
    this.tzEndTag = "END:VTIMEZONE";
    this.calEndTag = "END:VCALENDAR";
  }
  
  /**
   * @return calBeginTag
   */
  public String getCalBeginTag() {
    return calBeginTag;
  }

  /**
   * @return calEndTag
   */
  public String getCalEndTag() {
    return calEndTag;
  }

  /**
   * @return classification
   */
  public String getClassification() {
    return classification;
  }


  /**
   * @return dtend
   */
  public String getDtend() {
    return dtend;
  }

  /**
   * @return dtstart
   */
  public String getDtstart() {
    return dtstart;
  }

  /**
   * @return eventBeginTag
   */
  public String getEventBeginTag() {
    return eventBeginTag;
  }

  /**
   * @return eventEndTag
   */
  public String getEventEndTag() {
    return eventEndTag;
  }

  /**
   * @return location
   */
  public String getLocation() {
    return location;
  }

  /**
   * @return priority
   */
  public String getPriority() {
    return priority;
  }

  /**
   * @return summary
   */
  public String getSummary() {
    return summary;
  }

  /**
   * @return tzBeginTag
   */
  public String getTzBeginTag() {
    return tzBeginTag;
  }

  /**
   * @return tzEndTag
   */
  public String getTzEndTag() {
    return tzEndTag;
  }

  /**
   * @return tzid
   */
  public String getTzid() {
    return tzid;
  }

  /**
   * @return version
   */
  public String getVersion() {
    return version;
  }

  /**
   * User input method to set the classification of an event, requests input as a string, parses to an int,
   * then compares the int to determine value of classification.
   * @return classification (String)
   */
  public String setClassification() {
    int response = -1;
    
    do{
      System.out.print("Is this event private or confidential?\nPlease enter 1 if it is private"
          + " or 2 if it is confidential, or 0 if it is a public event.");
      //Get user input
      response = Integer.parseInt(keybd.nextLine());
      
      //Input check & error message
      if (response < 0 || response > 2){
        System.out.println("ERROR: The only valid values are 0, 1, or 2.");
      }
      
      switch(response){
        case 0: classification = "CLASSIFICATION:PUBLIC";
          break;
        case 1: classification = "CLASSIFICATION:PRIVATE";
          break;
        case 2: classification = "CLASSIFICATION:CONFIDENTIAL";
          break;
        default:
          break;
      }
      
      //Increment input buffer.
    } while (!(response >= 0 && response <= 2));
    
    return classification;
  }

  /**
   * User input method to set the end time of an event.
   * Performs function by calling getDTime() method.
   * @return dtend (String)
   */
  public String setDtend() {
    do{
      dtend = "DTEND:" + setDTime();
      if(!validateDTime(dtstart, dtend)){
        System.out.println("ERROR: End time cannot be earlier than start time.");
      }
      } while (!validateDTime(dtstart, dtend));
    return dtend;
  }

  /**
   * User input method to set the date and time according to the format used in an ics file.
   * Gets the date, and the time, then builds a string value with the appropriate format.
   * @return dtime (String)
   */
  public String setDTime(){
    String dtime = "";
    DateValidator date = new DateValidator();
    String hour = null, minute = null, month = null, day = null, year = null, dateInput, timeInput, AMPM = null;
    int hh = -1;
    int mm = -1;
    String datePattern = "(0[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
    Pattern pattern = Pattern.compile(datePattern);
    Matcher matcher;
    
    //loop to set valid input for the date
    do{
      System.out.println("Enter the date in the format: mm/dd/yyyy (e.g. 03/22/2015)");
      dateInput = keybd.nextLine();
      
      if (!date.validate(dateInput)){
        System.out.println("ERROR: Incorrect date format, strict mm/dd/yyyy format required.");
      }
    } while (!date.validate(dateInput));
    
    //Initialize the matcher
    matcher = pattern.matcher(dateInput);
    
    //Use the matcher to grab year, month, and day values.
    if (matcher.find()){
      month = matcher.group(1);
      day = matcher.group(2);
      year = matcher.group(3);
    }
    //loop to set valid input for the time
    do{
      System.out.print("Enter the time in the format: hh:mm (e.g. 02:30, 22:15 ...)");
      timeInput = keybd.nextLine();
      
      //Check time format against a regular expression.
      if (!timeInput.matches("\\d\\d:\\d\\d")){
        System.out.println("ERROR: Incorrect time format, strict hh:mm format required.");
        continue;
      }
      
      //parse validated input into separate parts to validate hour and minutes
      hh = Integer.parseInt(timeInput.substring(0, 2));
      mm = Integer.parseInt(timeInput.substring(3, 5));

      if (hh < 0 || hh > 23){
        System.out.println("ERROR: Invalid hour specification.");
        continue;
      }
      if (mm < 0 || mm > 59){
        System.out.println("ERROR: Invalid minute specification.");
        continue;
      }
    } while ((!timeInput.matches("\\d\\d:\\d\\d")) || (mm < 0 || mm > 59) || (hh < 0 || hh > 23)); //
    
    //Check for 12 hour clock vs 24 hour clock
    if (hh < 13){
      do{
        System.out.print("Is this " + hh + "'o clock AM or PM? (Enter 'AM' or 'PM')");
        AMPM = keybd.nextLine();
        if (!AMPM.equalsIgnoreCase("AM") && !AMPM.equalsIgnoreCase("PM")){
          System.out.println("ERROR: Incorrect AM/PM specification, 'AM' or 'PM' required.");
          continue;
        }
      } while (!AMPM.equalsIgnoreCase("AM") && !AMPM.equalsIgnoreCase("PM"));
    }
    
    //If a 12 hour clock input is received, convert 12AM to 24 hour time (0000 hours).
    if ((AMPM.equalsIgnoreCase("AM")) && hh == 12){
      hour = "00";
    }
    //Convert all times to corresponding 24 hour times.
    if (AMPM.equalsIgnoreCase("AM"))
    {
      switch(hh){
      case 0:
        hour = "00";
        break;
      case 1:
        hour = "01";
        break;
      case 2:
        hour = "02";
        break;
      case 3:
        hour = "03";
        break;
      case 4:
        hour = "04";
        break;
      case 5:
        hour = "05";
      case 6:
        hour = "06";
        break;
      case 7:
        hour = "07";
        break;
      case 8:
        hour = "08";
        break;
      case 9:
        hour = "09";
        break;
      case 10:
        hour = "10";
        break;
      case 11:
        hour = "11";
        break;
      }
    }
    else if(AMPM.equalsIgnoreCase("PM")){
      switch (hh){
      case 1:
        hour = "13";
        break;
      case 2:
        hour = "14";
        break;
      case 3:
        hour = "15";
        break;
      case 4:
        hour = "16";
        break;
      case 5:
        hour = "17";
        break; 
      case 6:
        hour = "18";
        break;
      case 7:
        hour = "19";
        break;
      case 8:
        hour = "20";
        break;
      case 9:
        hour = "21";
        break;
      case 10:
        hour = "22";
        break;
      case 11:
        hour = "23";
        break;
      case 12:
        hour = "12";
      default:
        break;
      }
    }
    
    //Check for all XX:00 times.
    if (mm == 0){
      minute = "00";
    }
    else{
      minute = "" + mm;      
    }
    
    dtime = (year + month + day + "T" + hour + minute + "00");
    return dtime;
  }

  /**
   * User input method to set the start time of an event.
   * Performs function by calling getDTime() method.
   * @return dtstart (String)
   */
  public String setDtstart() {
    dtstart = "DTSTART:" + setDTime();
    return dtstart;
  }

  /**
   * User input method to set the location associated with the event.
   * @return location (String)
   */
  public String setLocation() {
    System.out.print("Where will this event take place? ");
    location = "LOCATION:" +keybd.nextLine();
    
    return location;
  }

  /**
   * User input method to set the priority of the event, 1 is highest priority, down to 9 which
   * is the lowest priority. Default priority is 0 ('PRIORITY: 0').
   * @return priority (String)
   */
  public String setPriority() {
    int response = -1;
    
    do{
      System.out.print("To add a priority for this event, enter a number in the range of 1 to 9.");
      response = Integer.parseInt(keybd.nextLine());
      
      if (response < 0 || response > 9)
      {
        System.out.println("ERROR: Only valid values are from the range 1 to 9.");
      }
    } while (response < 0 || response > 9);
    
    priority = "PRIORITY" + response;
    
    return priority;
  }
  
  /**
   * User input method to set the summary description of the event.
   * @return summary (String)
   */
  public String setSummary() {
    System.out.print("Add a description of this event: ");
    summary = "SUMMARY:" + keybd.nextLine();
    
    return summary;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return calBeginTag + "\n" +version + "\n" + eventBeginTag
        + "\n" + summary + "\n" + location + "\n" + classification + "\n"
        + priority + "\n" + dtstart + "\n" + dtend + "\n" + eventEndTag + "\n"
        + tzBeginTag + "\n" + tzid + "\n" + tzEndTag + "\n" + calEndTag + "\n";
  }
  
  /**
   * Boolean method to validate the start and end times of an event according to the
   * specification that the end time is not earlier than the start time.
   * @param start is the DTime formatted string for the start time
   * @param end is the DTime formatted string for the end time.
   * @return true if the end is after the start time, false otherwise.
   */
  public boolean validateDTime(String start, String end){
    int startYear, startMonth, startDay, startHour, startMinute;
    int endYear, endMonth, endDay, endHour, endMinute;
    boolean returnValue = true;
    
    //Parse the start string into its components.
    //System.out.println("Start: " + start + "\n" + "End: " + end);
    startYear = Integer.parseInt(start.substring(8, 12));
    startMonth = Integer.parseInt(start.substring(12, 14));
    startDay = Integer.parseInt(start.substring(14, 16));
    startHour = Integer.parseInt(start.substring(17, 19));
    startMinute = Integer.parseInt(start.substring(19, 21));
    //Uncomment the line below for debugging purposes only.
    //System.out.println(startYear + " " + startMonth + " " + startDay + " " + startHour + " " + startMinute);
    
    //Parse the end string into its components.
    endYear = Integer.parseInt(end.substring(6, 10));
    endMonth = Integer.parseInt(end.substring(10, 12));
    endDay = Integer.parseInt(end.substring(12, 14));
    endHour = Integer.parseInt(end.substring(15, 17));
    endMinute = Integer.parseInt(end.substring(17, 19));
    //Uncomment the line below for debugging purposes only.
    //System.out.println(endYear + " " + endMonth + " " + endDay + " " + endHour + " " + endMinute);
    
    //Compare components to each other.
    if (endYear < startYear){
      returnValue = false;
    }
    else if (endMonth < startMonth){
      returnValue = false;
    }
    else if (endDay < startDay){
      returnValue = false;
    }
    else if (endHour < startHour){
      returnValue = false;
    }
    else if (endMinute < startMinute){
      returnValue = false;
    }
    
    return returnValue;
  }
  
  /**
   * A write file method that takes a filename as a string, and an icsEvent object, and writes
   * the values of the icsEvent object to a file.
   * @param event is the icsEvent object to be written to the file.
   * @param filename is a String representation of the file name desired for the file.
   */
  public boolean writeIcsFile(icsEvent event, String filename){
    try{
      File file = new File(filename);
      
      FileWriter fWriter = new FileWriter(file);
      BufferedWriter bWriter = new BufferedWriter(fWriter);
      
      bWriter.write(event.calBeginTag);
      bWriter.newLine();
      bWriter.write(event.version);
      bWriter.newLine();
      bWriter.write(event.eventBeginTag);
      bWriter.newLine();
      bWriter.write(event.summary);
      bWriter.newLine();
      bWriter.write(event.location);
      bWriter.newLine();
      bWriter.write(event.classification);
      bWriter.newLine();
      bWriter.write(event.priority);
      bWriter.newLine();
      bWriter.write(event.dtstart);
      bWriter.newLine();
      bWriter.write(event.dtend);
      bWriter.newLine();
      bWriter.write(event.eventEndTag);
      bWriter.newLine();
      bWriter.write(event.tzBeginTag);
      bWriter.newLine();
      bWriter.write(event.tzid);
      bWriter.newLine();
      bWriter.write(event.tzEndTag);
      bWriter.newLine();
      bWriter.write(event.calEndTag);
      
      bWriter.close();
      
      return true;
    } catch(IOException e){
      System.out.println(e);
      return false;
    }
  }
}
