package com.tg.gui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tg.backend.Logic;
import com.tg.util.Constants;
import com.tg.util.Event;

public class TodayComponent extends JPanel {
	Logic logic;
	public TodayComponent(Logic logic) {
		super();
		this.logic = logic;
		refresh();
	}

	/**
	 * Refresh the display in the Today Tab with an updated lists of events
	 */
	public void refresh(){
		removeAll();
		ArrayList<ArrayList<Event>> eventList = logic.updateTodayDisplay();

		String[] labels = {"Tasks","Deadlines","Schedules"};
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (int i = 0; i < 3; i++){
			add(new JLabel(labels[i]));
			if (i == Constants.EVENT_LIST_TASK)
				add(GUITools.createTaskTable(eventList.get(i)));
			else if (i == Constants.EVENT_LIST_DEADLINE)
				add(GUITools.createDeadlineTable(eventList.get(i)));
			else if (i == Constants.EVENT_LIST_SCHEDULE)
				add(GUITools.createScheduleTable(eventList.get(i)));
		}
		revalidate();
		repaint();
	}

}
