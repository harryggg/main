package com.tg.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@@author A0127604L
public class Event {
	private int type;
	private int ID;
	private String name;
	private Date startDate;
	private Date endDate;
	private String category;
	private int priority;
	private boolean isDone;
	private boolean hasClash;

	public Event(int ID, String name, Date startDate, Date endDate, String category, int priority){

		this.type = Constants.SCHEDULE_TYPE_NUMBER;
		this.ID = ID;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.priority = priority;
		this.isDone = false;
		this.hasClash = false;
	}

	public Event(int ID, String name, Date endDate, String category, int priority){

		this.type = Constants.DEADLINE_TYPE_NUMBER;
		this.ID = ID;
		this.name = name;
		this.endDate = endDate;
		this.category = category;
		this.priority = priority;
		this.isDone = false;
		this.hasClash = false;
	}


	public Event(int ID, String name, String category, int priority){

		this.type = Constants.TASK_TYPE_NUMBER;
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.priority = priority;
		this.isDone = false;
		this.hasClash = false;
	}


	public Event(int ID, String name, Date startDate, Date endDate, String category) {
		this(ID, name, startDate, endDate, category, Constants.DEFAULT_PRIORITY);
	}

	public Event(int ID, String name, Date endDate, String category) {
		this(ID, name, endDate, category, Constants.DEFAULT_PRIORITY);
	}

	public Event(int ID, String name, String category) {
		this(ID, name, category, Constants.DEFAULT_PRIORITY);
	}

	public Event(int ID, String name, Date startDate, Date endDate, int priority) {
		this(ID, name, startDate, endDate, Constants.DEFAULT_CATEGORY, priority);
	}

	public Event(int ID, String name, Date endDate, int priority) {
		this(ID, name, endDate, Constants.DEFAULT_CATEGORY, priority);
	}

	public Event(int ID, String name, int priority) {
		this(ID, name, Constants.DEFAULT_CATEGORY, priority);
	}

	public Event(int ID, String name, Date startDate, Date endDate) {
		this(ID, name, startDate, endDate, Constants.DEFAULT_CATEGORY, Constants.DEFAULT_PRIORITY);
	}

	public Event(int ID, String name, Date endDate) {
		this(ID, name, endDate, Constants.DEFAULT_CATEGORY, Constants.DEFAULT_PRIORITY);
	}

	public Event(int ID, String name) {
		this(ID, name, Constants.DEFAULT_CATEGORY, Constants.DEFAULT_PRIORITY);
	}

	public boolean isDone(){
		return isDone;
	}

	public void setIsDone(boolean boo){
		this.isDone = boo;
	}

	public boolean hasClash() {
		return hasClash;
	}

	public void setHasClash(boolean boo) {
		this.hasClash = boo;
	}

	public int getType() {
		return type;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public Date getStart() {
		return startDate;
	}

	public Date getEnd() {
		return endDate;
	}

	public String getCategory() {
		return category;
	}

	public int getPriority() {
		return priority;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Date startDate) {
		this.startDate = startDate;
	}

	public void setEnd(Date endDate) {
		this.endDate = endDate;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean contains(String input) {
		if (getName().toLowerCase().indexOf(input) > -1) {
			return true;
		} else if (getStart() != null && formatDate(getStart()).toLowerCase().indexOf(input) > -1) {
			return true;
		} else if (getEnd() != null && formatDate(getEnd()).toLowerCase().indexOf(input) > -1) {
			return true;
		} else if (getCategory().toLowerCase().indexOf(input) > -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns date in the form of "DAY MONTH DATE HOUR:MINUTE" in a 24-hour format
	 * is returned in the form of "DAY YEAR MONTH DATE HOUR:MINUTE" if year is not
	 * current year
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int currentYear = now.get(Calendar.YEAR);
		int dateYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));

		if (dateYear <= currentYear){
			return new SimpleDateFormat("E MMM dd HH:mm").format(date);
		} else {
			return new SimpleDateFormat("E yyyy MMM dd HH:mm").format(date);
		}
	}
}