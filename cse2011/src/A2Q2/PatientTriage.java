package A2Q2;

import java.util.*;

/**
 * Triages patients in Emergency Ward according to medical priority and wait
 * time. Priorities are positive integers; the highest priority is 1. Normally
 * patients are seen in priority order, however, if there are patients who have
 * waited longer than a specified time (maxWait), they are seen first, in order
 * of their arrival.
 * 
 * @author elder
 */
public class PatientTriage {

	private APQ<Patient> priorityHeap; // maintain patients in priority order
	private APQ<Patient> timeHeap; // maintain patients in order of arrival
	private Time maxWait; // maximum waiting time

	/**
	 * Constructor
	 * 
	 * @param time
	 *            Maximum wait time. Patients waiting longer than this are seen
	 *            first.
	 */
	public PatientTriage(Time time) {
		Comparator<Patient> priorityComparator = new PatientPriorityComparator();
		Comparator<Patient> timeComparator = new PatientTimeComparator();
		Locator<Patient> priorityLocator = new PatientPriorityLocator();
		Locator<Patient> timeLocator = new PatientTimeLocator();
		priorityHeap = new APQ<Patient>(priorityComparator, priorityLocator);
		timeHeap = new APQ<Patient>(timeComparator, timeLocator);
		setMaxWait(time);
	}

	/**
	 * Adds patient to queues.
	 * 
	 * @param patient
	 *            to add.
	 */
	public void add(Patient patient) throws NullPointerException {
		if (patient == null) {
			throw new NullPointerException();
		}
		priorityHeap.offer(patient); // add to priority queue
		timeHeap.offer(patient); // add to arrival time queue

	}

	/**
	 * Removes next patient in queue.
	 * 
	 * @param currentTime
	 *            used to determine whether to use priority or arrival time
	 */
	public Patient remove(Time currentTime) throws NullPointerException,
			BoundaryViolationException {

		Patient placeHolder;
		// Algorithm discussed in comments.
		// Get integer values of waiting times.
		int arivalTime = (timeHeap.peek().getArrivalTime().getHour() * 60)
				+ timeHeap.peek().getArrivalTime().getMinute();
		int currentIntTime = (currentTime.getHour() * 60)
				+ currentTime.getMinute();
		int maxWaitInt = (maxWait.getHour() * 60) + maxWait.getMinute();

		int timeWaited = currentIntTime - arivalTime;
		// peek at timeHeap, if the difference between the patients arrival time
		// and the current time is >= the max waiting time
		if (timeWaited >= maxWaitInt) {
			placeHolder = timeHeap.poll();
		//	priorityHeap.remove(priorityHeap.find(placeHolder));
			return placeHolder; // poll timeHeap and give return
									// that element.
			
		} else {
			placeHolder = priorityHeap.poll();
		//	timeHeap.remove(timeHeap.find(placeHolder));
			return placeHolder; // else take from priority queue.
		}
	}

	/**
	 * @return maximum wait time
	 */
	public Time getMaxWait() {
		return maxWait;
	}

	/**
	 * Set the maximum wait time
	 * 
	 * @param time
	 *            - the maximum wait time
	 */
	public void setMaxWait(Time time) throws NullPointerException {
		if (time == null) {
			throw new NullPointerException();
		}
		maxWait = time;
	}

}
