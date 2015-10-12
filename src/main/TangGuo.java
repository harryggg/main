package main;
import java.lang.String;
import java.text.ParseException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;


public class TangGuo {
	
	private static String fileName;
	private Scanner scanner = new Scanner(System.in);
	private TGStorageManager storage;
	private HashMap<String,Integer> TGIDMap;
	
	public TangGuo(String file) {
		fileName = file;
		storage = new TGStorageManager(fileName);
		TGIDMap = new HashMap<String,Integer>();
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		TangGuo tg = new TangGuo(args[0]);
		showToUser(String.format(Constants.TANGGUO_START, fileName));
		
		while (true) {
			tg.runUserInput();
		}
	}
	
	private void runUserInput(){
		requestInput();
		String input = scanner.nextLine();
		String output = executeinputs(input);
		showToUser(output);
	}
	
	private static void showToUser(String display) {
		System.out.println(display);
	}
	
	private static void requestInput() {
		System.out.print("input: ");
	}
	
	public String executeinputs(String input) {
		Command currentCommand;
		try {
			currentCommand = Parser.parseCommand(input);
		} catch (ParseException e) {
			return Constants.TANGGUO_INVALID_DATE;
		} catch (IndexOutOfBoundsException e){
			return Constants.TANGGUO_INVALID_COMMAND;
		}
		switch (currentCommand.getType()) {
			case ADD_DEADLINE:
				return addDeadline(currentCommand);
			case ADD_SCHEDULE:
				return addSchedule(currentCommand);
			case ADD_TASK:
				return addTask(currentCommand);
			case DISPLAY:
				return displayTangGuo();
			case UPDATE:
				return updateName(currentCommand);
			case DELETE:
				return deleteEvent(currentCommand);
			case EXIT:
				showToUser(Constants.TANGGUO_EXIT);
				System.exit(0);
			case INVALID:
				return Constants.TANGGUO_INVALID_COMMAND;
			default:
				return Constants.TANGGUO_IO_EXCEPTION;
		} 
	}
	
	private String addDeadline(Command command){
		storage.addDeadline(command.getEventName(), command.getEventEnd());			
		
		return String.format(Constants.TANGGUO_ADD_SUCCESS, fileName, command.getEventName());
	}
	
	private String addSchedule(Command command){
		// add into storage
		storage.addSchedule(command.getEventName(), command.getEventStart(), command.getEventEnd());
		return String.format(Constants.TANGGUO_ADD_SUCCESS, fileName, command.getEventName());
	}
	
	private String addTask(Command command) {
		// add into storage
		storage.addTask(command.getEventName());
		
		return String.format(Constants.TANGGUO_ADD_SUCCESS, fileName, command.getEventName());
	}
	
	private String displayTangGuo() {
		String printOut = "";
		
		if (allCachesEmpty()) {
			return String.format(Constants.TANGGUO_EMPTY_FILE, fileName);
		}
		TGIDMap.clear();
		printOut += displayCache("Tasks", storage.getTaskCache(),"t");
		printOut += displayCache("Deadlines", storage.getDeadlineCache(),"d");
		printOut += displayCache("Schedules", storage.getScheduleCache(),"s");
		
		return printOut;
	}
	
	private boolean allCachesEmpty(){
		return(storage.getDeadlineCache().isEmpty() && storage.getTaskCache().isEmpty()
				&& storage.getScheduleCache().isEmpty());			
	}
	
	private String displayCache(String cacheName, ArrayList<Event> cache, String header){
		String printOut = cacheName + ":\n";
		for (int i = 0; i < cache.size(); i++) {
			TGIDMap.put(header+(i+1), cache.get(i).getID());
			printOut = printOut + (i+1) + ". " + cache.get(i).getName() + "\n";
		}
		return printOut;
	}
	
	private String updateName(Command command) {
		String newName = command.getEventName();
		
		int taskID = TGIDMap.get(command.getDisplayedIndex());
		String oldVersion = storage.getEventByID(taskID).getName();
		storage.updateNameByID(taskID, newName);
		
		return String.format(Constants.TANGGUO_UPDATE_NAME, oldVersion,
				newName) + "\n" + displayTangGuo();
		
	}
	
	/**
	 * deletes an Event
	 * @param toBeDeleted : [letter][number] 
	 * letter refers to the type of event = {t, s, d}; number refers to index displayed
	 * @return
	 */
	private String deleteEvent(Command command) {
		
		int IDToDelete = TGIDMap.get(command.getDisplayedIndex());
				
		Event deletedEvent = storage.deleteEventByID(IDToDelete);

		System.out.println(String.format(Constants.TANGGUO_DELETE_SUCCESS, fileName, deletedEvent.getName()));
		return displayTangGuo();
	}

	
	

}