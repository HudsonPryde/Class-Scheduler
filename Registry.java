// Hudson Stuart 500890173
import java.io.File;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import javax.management.InvalidAttributeValueException;

public class Registry
{
   private TreeMap<String, Student> students = new TreeMap<String, Student>();
   private TreeMap<String, ActiveCourse> courses  = new TreeMap<String, ActiveCourse>();
   
   public Registry() throws IOException
   {
	// Add some students
	   // in A2 we will read from a file
		//    open the students text file
		File studentFile = new File("students.txt");
		// initalize a scanner with the student file 
		Scanner in = new Scanner(studentFile);
		while(in.hasNextLine()){
			// get the name and id from each line
			String[] line = in.nextLine().split(" ");
			if(line.length == 2){
				String name = line[0];
				String id = line[1];
				Student student = new Student(name, id);
				// add the student to the students map
				students.put(id, student);
			}else throw new IOException("Error: Bad Format for students.txt");
		}
		// create a temp list for students
	   ArrayList<Student> list = new ArrayList<Student>();
	   
	   // Add some active courses with students
		   //  open the courses file   
		   File coursesFile = new File("courses.txt");
			// initalize a scanner with the courses file    
		   in = new Scanner(coursesFile);
		   try{
				while(in.hasNextLine()){
					// get the name, code, description, format and list of students erolled
					String name = in.nextLine();
					String code = in.nextLine();
					String desc = in.nextLine();
					String form = in.nextLine();
					String studentlist = in.nextLine();
					// create a new scanner for the students line
					Scanner sc = new Scanner(studentlist);
					while(sc.hasNext()){
						// get the student name and find them in the student registry
						String id = sc.next();
						Student student = students.get(id);
						// once found add the student to the temp list
						list.add(student);
						// add this course to this student's course list
						student.addCourse(name, code, desc, form, "W2020", 0.0);
					}
					// add this course to the course list and clear the temp student list
					ActiveCourse course = new ActiveCourse(name, code, desc, form, "W2020", list);
					courses.put(code, course);
					list.clear();
					}
				}catch(RuntimeException e){
					throw new IOException("Error: Bad Format for courses.txt");
				}
		   in.close();
   }

	//get method for the course list, this is used to initialize the scheduler
   public TreeMap<String, ActiveCourse> getCourses(){
	   return courses;
   }
   
   // Add new student to the registry (students map above) 
   public boolean addNewStudent(String name, String id)
   {
	   // check to ensure student is not already in registry
	   if(students.containsKey(id)){
		   // if not, add them and return true, otherwise return false
			return false;
	   }
	   // Create a new student object
	   Student newStudent = new Student(name, id);
	   students.put(id, newStudent);
	   return true;
   }
   // Remove student from registry 
   public boolean removeStudent(String studentId) throws InvalidAttributeValueException
   {
	   // Find student in students map
		if(students.containsKey(studentId)){
		// If found, remove this student and return true
			students.remove(studentId);
		}else throw new InvalidAttributeValueException("Error: Student Not Found");
		return false;
   }
   
   // Print all registered students
   public void printAllStudents()
   {
	   Set<String> keys = students.keySet();
	   for(String key: keys){
		System.out.println("ID: " + key + " Name: " + students.get(key).getName() ); 
	   }
   }
   
   // Given a studentId and a course code, add student to the active course
   public void addCourse(String studentId, String courseCode) throws InvalidAttributeValueException
   {
	//make sure the student exists
	if(students.containsKey(studentId)){
		// Check if student has already taken this course in the past Hint: look at their credit course list
		Boolean taken = false;
		for(CreditCourse course: students.get(studentId).courses){
			// if the course is found in the student's credit course list change enrolled to true
			if(course.getCode().equals(courseCode)){taken = true;}
		}
		if(taken) throw new InvalidAttributeValueException("Error: Student Has Already Taken This Course");
		// If not, then find the active course in courses map using course code	
			//check if the course exists
		else if(courses.containsKey(courseCode)){
			// If active course found then check to see if student already enrolled in this course
			Boolean enrolled = false;
			for(Student student: courses.get(courseCode).getStudents()){
			// if stuednt is foundin the class list of this course change enrolled to true
				if(student.getId().equals(studentId)){enrolled = true;}
			}
			if(enrolled) throw new InvalidAttributeValueException("Error: Student Already Enrolled In This Course");
			else{
				// add student to the active course
				courses.get(courseCode).addStudent(students.get(studentId));
				//   add course to student list of credit courses with initial grade of 0
				ActiveCourse course = courses.get(courseCode); 
				students.get(studentId).addCourse(course.getName(), course.getCode(), course.getDescription(), course.getFormat(), course.getSemester(), 0);
			}
		// if course is not found throw an invalid exception
		}else throw new InvalidAttributeValueException("Error: Course Not Found");		
	// if student not found throw an exception
	}else throw new InvalidAttributeValueException("Error: Student Not Found");   
   }
   
   // Given a studentId and a course code, drop student from the active course
   public void dropCourse(String studentId, String courseCode) throws InvalidAttributeValueException
   {
	   // Find the active course
		if(courses.containsKey(courseCode)){	   
		   ActiveCourse course = courses.get(courseCode);
				// Find the student in the list of students for this course
				ArrayList<Student> classList = course.getStudents();
				Boolean studentFound = false;
				for(int j = 0; j<classList.size(); j++){
					Student student = classList.get(j);
					// If student found:
					if(student.getId().equals(studentId)){
						studentFound = true;
						//   remove the student from the active course
						//   remove the credit course from the student's list of credit courses
						course.removeStudent(student);
						student.removeActiveCourse(courseCode);
					}
				}if(!studentFound) throw new InvalidAttributeValueException("Error: Student Not Found");
	   }else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
   
   // Print all active courses
   public void printActiveCourses()
   {
		// run through the course list   
	   for (ActiveCourse course: courses.values())
	   {
			// print each course's description    
		   System.out.println(course.getDescription()+"\n");
	   }
   }
   
   // Print the list of students in an active course
   public void printClassList(String courseCode) throws InvalidAttributeValueException
   {
	//    find the course
	if(courses.containsKey(courseCode)){
		ActiveCourse course = courses.get(courseCode);
		// print its class list
		course.printClassList();
	}else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
   
   // Given a course code, find course and sort class list by student name
   public void sortCourseByName(String courseCode) throws InvalidAttributeValueException
   {
		// find course in ActiveCourse list
		if(courses.containsKey(courseCode)){
			ActiveCourse course = courses.get(courseCode);
			// sort class list by student name
			course.sortByName();
		}else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
   
   // Given a course code, find course and sort class list by student name
   public void sortCourseById(String courseCode) throws InvalidAttributeValueException
   {
	   	// find course in ActiveCourse list
		if(courses.containsKey(courseCode)){
			ActiveCourse course = courses.get(courseCode);
			// sort class list by student name
			course.sortById();
		}else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
   
   // Given a course code, find course and print student names and grades
   public void printGrades(String courseCode) throws InvalidAttributeValueException
   {
		//find the course in the course list    
		if(courses.containsKey(courseCode)){
			ActiveCourse course = courses.get(courseCode);
			// print the courses grades
			course.printGrades();
		}else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
   
   // Given a studentId, print all credit courses of student
   public void printStudentCourses(String studentId) throws InvalidAttributeValueException
   {
		//find the student by their id and print their active courses
		if(students.containsKey(studentId)){
			Student student = students.get(studentId);
			student.printActiveCourses();
		}else throw new InvalidAttributeValueException("Error: Student Not Found");
   }
   
   // Given a studentId, print all completed courses and grades of student
   public void printStudentTranscript(String studentId) throws InvalidAttributeValueException
   {
		// find the student in the student list
		if(students.containsKey(studentId)){
			Student student = students.get(studentId);
				// print their transcript
				student.printTranscript();
		}else throw new InvalidAttributeValueException("Error: Student Not Found");
   }
   
   // Given a course code, student id and numeric grade
   // set the final grade of the student
   public void setFinalGrade(String courseCode, String studentId, double grade) throws InvalidAttributeValueException
   {
	   // find the active course
	   if(courses.containsKey(courseCode)){
		   ActiveCourse course = courses.get(courseCode);
			// If found, find the student in class list
			Boolean enrolled = false;
			for(int j = 0; j<course.getStudents().size(); j++){
				Student student = course.getStudents().get(j);
				if(student.getId().equals(studentId)){
					enrolled = true;
					// then search student credit course list in student object and find course
					for(int h = 0; h<student.courses.size(); h++){
						if(student.courses.get(h).getCode().equals(courseCode)){
							// set the grade in credit course and set credit course inactive
							student.courses.get(h).setGrade(grade);
							student.courses.get(h).setInactive();
						}
					}
				}
			}
			if(!enrolled)throw new InvalidAttributeValueException("Error: Student Not Found");
	   }else throw new InvalidAttributeValueException("Error: Course Not Found");
   }
  
}
