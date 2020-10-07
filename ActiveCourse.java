// Hudson Stuart 500890173
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

// Active University Course
 
public class ActiveCourse extends CreditCourse
{
	private ArrayList<Student> students = null; 
   private String             semester;
   // lecture start,duration and day are lists to make multi scheduling possible
   private ArrayList<Integer> lectureStart = new ArrayList<Integer>();
   private ArrayList<Integer>  lectureDuration = new ArrayList<Integer>();
   private ArrayList<String> lectureDay = new ArrayList<String>();
	   
   // Add a constructor method with appropriate parameters
   // should call super class constructor to initialize inherited variables
   // make sure to *copy* students array list being passed in into new arraylist of students
   // create Grade objects for each Student object and add to grades arraylist. Set grade to 0
   // see class Registry to see how an ActiveCourse object is created and used
   public ActiveCourse(String name, String code, String descr, String fmt,String semester,ArrayList<Student> students)
   {
      super(name, code, descr, fmt, semester, 0.0);
      this.students = new ArrayList<Student>(students);
      this.semester = semester;
      this.lectureDay.add("");
      this.lectureDuration.add(0);
      this.lectureStart.add(0);
   }
   // add methods for day,start, and duration
   public void addLectureDay(String day){
      this.lectureDay.add(day);
   }
   public void addLectureStart(int start){
      this.lectureStart.add(start);
   }
   public void addLectureDuration(int duration){
      this.lectureDuration.add(duration);
   }
   // getters for day,start and duration
   public ArrayList<String> getLectureDay(){
      return this.lectureDay;
   }
   public ArrayList<Integer> getLectureStart(){
      return this.lectureStart;
   }
   public ArrayList<Integer> getLectureDuration(){
      return this.lectureDuration;
   }
   // remove methods
   public void remLectureDay(int i){
      this.lectureDay.remove(i);
   }
   public void remLectureStart(int i){
      this.lectureStart.remove(i);
   }
   public void remLectureDuration(int i){
      this.lectureDuration.remove(i);
   }
   // return this course's semester
   public String getSemester()
   {
	   return semester;
   }
// return the students list for this course
   public ArrayList<Student> getStudents(){
      return this.students;
   }
// add a student to the class list
   public void addStudent(Student student){
      this.students.add(student);
   }
// remove a student from the class list
   public void removeStudent(Student student){
      this.students.remove(student);
   }
   
   // Prints the students in this course  (name and student id)
   public void printClassList()
   {
	   for(int i = 0; i<this.students.size(); i++){
         // use the student class' toString method to print out class list info
         System.out.println(this.students.get(i).toString());
      }
   }
   
   // Prints the grade of each student in this course (along with name and student id)
   // 
   public void printGrades()
   {
	   for(int i = 0; i<this.students.size(); i++){
         Student student = this.students.get(i);
         // run through the class list and print each student's string and grade for this course
         System.out.println(student.toString()+" Grade: "+student.getGrade(this.getCode()));
      }
   }
   
   // Returns the numeric grade in this course for this student
   // If student not found in course, return 0 
   public double getGrade(String studentId)
   {
	  // Search the student's list of credit courses to find the course code that matches this active course
     for(int i = 0; i<students.size(); i++){
        if(students.get(i).getId() == studentId){
            Student student = students.get(i);
            // see class Student method getGrade() 
	         // return the grade stored in the credit course object
            return student.getGrade(super.getCode());
        }
     }
	  return 0; 
   }
   
   // Returns a String containing the course information as well as the semester and the number of students 
   // enrolled in the course
   // must override method in the superclass Course and use super class method getDescription()
   public String getDescription()
   {
      return(super.getDescription() + "\n" + this.semester + " Enrolment: " + this.students.size());
   }
    
   
   
   
   // Sort the students in the course by name using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortByName()
   {
 	  Collections.sort(this.students, new NameComparator());
   }
   
   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student name
   private class NameComparator implements Comparator<Student>
   {
      public int compare(Student student1, Student student2) {
         // sort by using the student compareTo method
         return student1.compareTo(student2);
      }
      
   }
   
   // Sort the students in the course by student id using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortById()
   {
 	  Collections.sort(this.students, new IdComparator());
   }
   
   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student id
   private class IdComparator implements Comparator<Student>
   {
   	public int compare(Student student1, Student student2) {
         // parse id strings into ints to be comapred
         int id1 = Integer.parseInt(student1.getId());
         int id2 = Integer.parseInt(student2.getId());
         // sort from lowest to highest
         if(id1 > id2){
            return 1;
         }else if(id1 < id2){
            return -1;
         }else{
            return 0;
         }
      }
   }
}
