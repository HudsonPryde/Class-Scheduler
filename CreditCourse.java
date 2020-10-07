// Hudson Stuart 500890173
public class CreditCourse  extends Course
{
	// add a constructor method with appropriate parameters
	// should call the super class constructor
	private String  semester;
	private double  grade;
	private boolean active;
	public CreditCourse(String name, String code, String descr, String fmt, String semester, Double grade)
	{
		super(name, code, descr, fmt);
		this.semester = semester;
		this.grade = grade;
	}
	// return the active variable
	public boolean getActive()
	{
		// add code and remove line below
		return this.active;
	}
	// set the active variable to true
	public void setActive()
	{
		this.active = true;
	}
	// set the active variable to false
	public void setInactive()
	{
		this.active = false;
	}
// a student's grade in this course
	public double getGrade(){
		return this.grade;
	}
// set a student's grade in this course
	public void setGrade(double grade){
		this.grade = grade;
	}
	// return the super class's getInfo() method as well as this course's semester and letter grade
	public String displayGrade()
	{
		// Change line below and print out info about this course plus which semester and the grade achieved
		// make use of inherited method in super class
		return super.getInfo()+"\n"+this.semester+"\n"+super.convertNumericGrade(grade);
	}
	
}