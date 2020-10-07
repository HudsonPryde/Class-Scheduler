// Hudson Stuart 500890173
import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.Arrays;

public class Scheduler 
{
    // In main() after you create a Registry object, create a Scheduler object and pass in the students ArrayList/TreeMap
	// If you do not want to try using a Map then uncomment
	// the line below and comment out the TreeMap line
	
	//ArrayList<Student> students;
	
	TreeMap<String,ActiveCourse> schedule;
		
	public Scheduler(TreeMap<String,ActiveCourse> courses)
	{
	  schedule = courses;
	}
	
	public void setDayAndTime(String courseCode, String day, int startTime, int duration) throws RuntimeException
	{
		//check if the course exists
		if(schedule.containsKey(courseCode)){
			// if course found, error check the day
			String[] temp = {"mon","tue","wed","thur","fri"};
			ArrayList<String> week = new ArrayList<String>();
			// add days of the week to the week list
			for(String weekDay: temp) week.add(weekDay);
			if(week.contains(day.toLowerCase())){
				// if the day has no error, check the time
				if(800 <= startTime && (startTime+(duration*100)) <= 1700){
					// if the time is valid, check if the duration is valid(should be 1,2,3 hours)
					if(duration == 1 || duration == 2 || duration == 3){
						// if duration is valid, check if the time slot is taken
						int endTime = startTime+(duration*100);
						// for every course check if the selected timeslot is conflicting with one of their timeslots
						for(ActiveCourse course: schedule.values()){
							for(int i = 0; i<course.getLectureDay().size(); i++){
								if(course.getLectureDay().get(i).equalsIgnoreCase(day)){
									// get the other course's timeslot (starttime and endtime)
									int otherStart = course.getLectureStart().get(i);
									int otherDuration = course.getLectureDuration().get(i);
									int otherEndTime = otherStart + (otherDuration*100);
									// check if we start in their slot
									Boolean startOverlap = (otherStart <= startTime) && (startTime < otherEndTime);
									// check if we end in their slot
									Boolean endOverlap = (otherStart < endTime) && (endTime <= otherEndTime);
									// overTaken is a test to see if the chosen timeslot "swallows" another timeslot (ie. theres a class from 1000-1100 and we want to schedule for 900-1200)
									Boolean overTaken = (startTime <= otherStart) && (endTime >= otherEndTime);
									// if any of our checks are true we throw and exception and tell the user the slot is taken
									if(startOverlap || endOverlap || overTaken) throw new RuntimeException("Error: Timeslot taken");
								}
							}
						}
						// if no timeslot errors then set the variables for this course
						schedule.get(courseCode).addLectureStart(startTime);
						schedule.get(courseCode).addLectureDay(day);
						schedule.get(courseCode).addLectureDuration(duration);
					}else throw new RuntimeException("Error: invalid duration");
				}else throw new RuntimeException("Error: invalid time");
			}else throw new RuntimeException("Error: invalid day");
		}else throw new RuntimeException("Error: Course not Found");
	}

	// automatic version of the setDayAndTime method
	public Boolean setDayAndTime(String courseCode, int duration){
		// check of the course exists
		if(schedule.containsKey(courseCode)){
			// if course exists check if the duration is valid
			if(duration == 1 || duration == 2 || duration == 3){
				// variable to keep track of if a slot is available
				Boolean slotFound;
				// weekdays array
				String[] week = {"Mon","Tue","Wed","Thur","Fri"};
				// for everyday we check every timeslot for one thats available
				for(String day: week){
					// this for loop starts at the earliest starttime possible and increases by 1 hour times the duration the user chose 
					// until the slot before 1700 which is the latest endTime
					for(int startTime = 800; startTime<1700; startTime+=(100*duration)){
						// we begin every new timeslot as true then check the availablity to make it false
						slotFound = true;
						// make a new endtime everyloop since its dependant on the starttime
						int endTime = startTime+(duration*100);
						// check every course for conflicting time slots
						for(ActiveCourse course: schedule.values()){
							for(int i = 0; i<course.getLectureDay().size(); i++){
								if(course.getLectureDay().get(i).equalsIgnoreCase(day)){
									int otherStart = course.getLectureStart().get(i);
									int otherDuration = course.getLectureDuration().get(i);
									// these checks are the same as the non-automatic version
									int otherEndTime = otherStart + (otherDuration*100);
									Boolean startOverlap = (otherStart <= startTime) && (startTime < otherEndTime);
									Boolean endOverlap = (otherStart < endTime) && (endTime <= otherEndTime);
									// overTaken is a test to see if the chosen timeslot "swallows" another timeslot
									Boolean overTaken = (startTime <= otherStart) && (endTime >= otherEndTime);
									// if a conflict is found set slotFound to false and check the next timeSlot
									if(startOverlap || endOverlap || overTaken) slotFound = false;
								}
							}
						}
						// if no timeslot errors then set the variables for this course
						if(slotFound){
							schedule.get(courseCode).addLectureStart(startTime);
							schedule.get(courseCode).addLectureDay(day);
							schedule.get(courseCode).addLectureDuration(duration);
							System.out.println(courseCode+" scheduled for "+day+"/"+startTime+" - "+duration);
							return slotFound;
						}
					}
				}
				return false;
			}else throw new RuntimeException("Error: invalid duration");
		}else throw new RuntimeException("Error: Course not Found");
	}
	
	
	
	public void clearSchedule(String courseCode)
	{
		// check if the course exists
		if(schedule.containsKey(courseCode)){
			// if found reset the variables
			ActiveCourse course = schedule.get(courseCode);
			// for every day scheduled clear the values
			for(int i = 0; i<course.getLectureDay().size(); i++){
				course.remLectureDay(1);
				course.remLectureStart(1);
				course.remLectureDuration(1);
			}
		}else throw new RuntimeException("Error: course not found");
	}
		
	public void printSchedule()
	{
		// create a 2d array
		String[][] sch = new String[6][10];
		// create a list of the days of the week
		String[] week = {"Mon","Tue","Wed","Thur","Fri"};
		// fill in both with the schedule
		for(int y = 0; y<10; y++){
			for(int x = 0; x<6; x++){
				if(y == 0 && x != 0){
					// if the schedule is at the top make it a weekday
					sch[x][y] = week[x-1];
				}else if(y != 0 && x == 0){
					// if the schedule is at the far left side make it a timestamp 
					sch[x][y] = ""+(800+(100*y-100));
				}else{
					// find an active course with the same day as this coloumn
					for(ActiveCourse course: schedule.values()){
						// for each active course check this time slot for every day the course is scheduled
						for(int i = 0; i<course.getLectureDay().size(); i++){
							if(course.getLectureDay().get(i).equalsIgnoreCase(sch[x][0])){
								// if a course with this day is found check if it has the current timeslot
								String start = ""+course.getLectureStart().get(i);
								if(start.equals(sch[0][y])){
									// if this course has this timeslot add it to the schedule for its duration
									for(int j = 0; j<course.getLectureDuration().get(i); j++){
										sch[x][y+j] = course.getCode();
									}
								}
							}
						}
					}
				}
			}
		}
		// print the scedule
		for(int y = 0; y<10; y++){
			for(int x = 0; x < 6; x++){
				if(sch[x][y] == null){
					sch[x][y] = "";
				}
				System.out.print(sch[x][y]+"\t");
			}
			System.out.println("");
		}
	}
	
}

