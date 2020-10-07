Hudson Stuart 500890173

All old and new commands work.
The scheduler has both a non-automatic version and 
automatic version, use sch courseCode day startTime duration for the non-automatic and
sch courseCode duration for the automatic version.

Both versions can be used to schedule a course multiple times.

Cache clears all scheduled time slots for a course.

Both the students, and the courses arrayLists have been converted to TreeMaps and the registry class has been updated to work with the new TreeMaps.

There's a try catch block surrounding the registry object in the main method which catches all IO exceptions that might occur when reading the students.txt or courses.txt files, these errors include; the wrong file name and a bad file format. A bad file format for the students file is caught by checking if, when a line is read from the file and split to an array if that array is anything other than 2 items in length [name, id] then a bad format exception has occurred and an IOException is thrown. For the courses file I caught the bad file format by using a try catch block to catch any RunTimeExceptions that would occur if a parameter was missing when creating a new course object.