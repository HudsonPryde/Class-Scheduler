// Hudson Stuart 500890173
public class Course 
{
	private String code;
	private String name;
	private String description;
	private String format;
	   
	public Course()
	{
	  this.code        = "";
	  this.name        = "";
	  this.description = "";
	  this.format      = "";
	}
	   
	public Course(String name, String code, String descr, String fmt)
	{
	  this.code        = code;
	  this.name        = name;
	  this.description = descr;
	  this.format      = fmt;
	}
	//   return course code
	public String getCode()
	{
	   return code;
	}
	//    return course name
	public String getName()
	{
	  return name;
	}
	//    return course format
	public String getFormat()
	{
	  return format;
	}
	//    return course description
	public String getDescription()
	{
	  return code +" - " + name + "\n" + description + "\n" + format;
	}
	// return course code and name
	 public String getInfo()
	 {
	   return code +" - " + name;
	 }
	 
	 // static method to convert numeric score to letter grade string 
	 // e.g. 91 --> "A+"
	 public static String convertNumericGrade(double score)
	 {
		//  numeric grade to letter grade is based on the Ryerson grading system
		if(score >= 90){
			return "A+";
		  }else if(score >= 85){
			return "A";
		  }else if(score >= 80){
			return "A-";
		  }else if (score >= 77) {
			return "B+";
		  }else if(score >= 73){
			return "B";
		  }else if (score >= 70) {
			return "B-";
		  }else if (score >= 67) {
			return "C+";
		  }else if (score >= 63) {
			return "C";
		  }else if (score >= 60) {
			return "C-";
		  }else if (score >= 57) {
			return "D+";
		  }else if (score >= 53) {
			return "D";
		  }else if (score >= 50) {
			return "D-";
		  }else{
			return "F";
		  }
	 }
	 
}
