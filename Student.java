// Hudson Stuart 500890173
import java.util.ArrayList;

// Make class Student implement the Comparable interface
// Two student objects should be compared by their name
public class Student implements Comparable<Student>
{
  private String name;
  private String id;
  public  ArrayList<CreditCourse> courses;
  
  
  public Student(String name, String id)
  {
	 this.name = name;
	 this.id   = id;
	 courses   = new ArrayList<CreditCourse>();
  }
  
  public String getId()
  {
	  return this.id;
  }
  
  public String getName()
  {
	  return this.name;
  }

  // get this student's grade from a specific credit course
  public double getGrade(String courseCode){
    // search for the course in this student's course list
    for(int i = 0; i<courses.size(); i++){
      CreditCourse course = courses.get(i);
      if(course.getCode().equals(courseCode)){
        // if course is founc return the grade
        return course.getGrade();
      }
    }
    return 0;
  }

  // set the grade of a specific credit course for this student
  public void setGrade(String courseCode, double grade){
    for(int i = 0; i<courses.size(); i++){
      CreditCourse course = courses.get(i);
      if(course.getCode() == courseCode){
        course.setGrade(grade);
      }
    }
  }
  
  // add a credit course to list of courses for this student
  public void addCourse(String courseName, String courseCode, String descr, String format,String sem, double grade)
  {
    // create a CreditCourse object
    CreditCourse course = new CreditCourse(courseName, courseCode, descr, format, sem, grade);
    // set course active
    course.setActive();
    // add to courses array list
    this.courses.add(course);
  }
  
  
  
  // Prints a student transcript
  // Prints all completed (i.e. non active) courses for this student (course code, course name, 
  // semester, letter grade)
  // see class CreditCourse for useful methods
  public void printTranscript()
  {
    for(int i = 0; i<this.courses.size(); i++){
      CreditCourse course = this.courses.get(i);
      if(course.getActive() == false){
        // display the grade and course info for every completed course this student has
        System.out.println(course.displayGrade());
      }
    }
  }
  
  // Prints all active courses this student is enrolled in
  // see variable active in class CreditCourse
  public void printActiveCourses()
  {
    for(int i = 0; i<this.courses.size(); i++){
      if(this.courses.get(i).getActive() == true){
        // display the info for every currently active course this student has
        System.out.println(this.courses.get(i).getInfo());
      }
    }
  }
  
  // Drop a course (given by courseCode)
  // Find the credit course in courses arraylist above and remove it
  // only remove it if it is an active course
  public void removeActiveCourse(String courseCode)
  {
    for(int i = 0; i<this.courses.size(); i++){
      CreditCourse course = this.courses.get(i);
      if(course.getCode().equals(courseCode) && course.getActive() == true){
        courses.remove(i);
      }
    }
  }
  
  public String toString()
  {
	  return "Student ID: " + id + " Name: " + name;
  }
  
  // override equals method inherited from superclass Object
  // if student names are equal *and* student ids are equal (of "this" student
  // and "other" student) then return true
  // otherwise return false
  // Hint: you will need to cast other parameter to a local Student reference variable
  public boolean equals(Object other)
  {
    Student otherS = (Student) other;
    if(this.name == otherS.getName() && this.id == otherS.getId()){
      return true;
    }
	  return false;
  }
 
  @Override
  public int compareTo(Student other) {
    return this.name.compareToIgnoreCase(other.getName());
  }
  
}
