## Introduction
This project was great for practicing a number of different skills, including, object-oriented programing, array manipulation, and I/O. This program's purpose is to be a school's database with all the necessities of a real database, those being, the ability to add/remove students and courses to the database, enrolling those students in class and giving them marks, printing a student's transcript, and a scheduler to organize the course's timeslots for the week.

## User Guide
This project is split into two parts; the registry and the scheduler. The registry controls which courses have which students, the grades of those students in those courses and their GPA overall. The scheduler handles the timeslots of the courses and ensuring no conflicts between them, as well as printing the schedule.  
To run the registry or scheduler, simply compile run the Registry.java or Scheduler.java file in your command prompt or preffered shell.
## Commands
### Registry
addNewStudent(String name, String id)
removeStudent(String studentId)
printAllStudents()
addCourse(String studentId, String courseCode)
dropCourse(String studentId, String courseCode)
printActiveCourses()
printClassList(String courseCode)
sortCourseById(String courseCode)
printGrades(String courseCode)
printStudentCourses(String studentId)
printStudentTranscript(String studentId)
setFinalGrade(String courseCode, String studentId, double grade)
### Scheduler
setDayAndTime(String courseCode, String day, int startTime, int duration)
setDayAndTime(String courseCode, int duration)

The scheduler has both a non-automatic version and 
automatic version, use sch courseCode day startTime duration for the non-automatic and
sch courseCode duration for the automatic version.

Both versions can be used to schedule a course multiple times.

Cache clears all scheduled time slots for a course.

Both the students, and the courses arrayLists have been converted to TreeMaps and the registry class has been updated to work with the new TreeMaps.

There's a try catch block surrounding the registry object in the main method which catches all IO exceptions that might occur when reading the students.txt or courses.txt files, these errors include; the wrong file name and a bad file format. A bad file format for the students file is caught by checking if, when a line is read from the file and split to an array if that array is anything other than 2 items in length [name, id] then a bad format exception has occurred and an IOException is thrown. For the courses file I caught the bad file format by using a try catch block to catch any RunTimeExceptions that would occur if a parameter was missing when creating a new course object.
