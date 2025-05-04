// Student:

// so to enroll a student in multiple courses, we'll have to do it multiple times with same name, same roll, same department.

{
    id,
    name,
    roll,
    courseId, 
    deptId
}

// Course

{
    id,
    name, 
    deptId
}

// Department

{
    id,
    name
}
-----------------------------------------------

DTO required: 
1. CourseWithStudentDTO -> for /api/courses/{courseName} -> get course based on courseName which also has it's deptId && make a call to /api/studentList to get all studentList && then filter them based on same courseName. 

2. /api/dept/{deptName} -> need all the course, student details withing this department. So getAllDepartments -> for each dept -> using it's ID -> 1st get All courses -> filter them based on deptId -> then for each course -> get Students using courseId. Dept_Course_Student_DTO: {ArrayList<CourseWithStudentDTO>}

    - Course_Students_DTO : contain course, all it's studentList.
    - Department_Courses_Students_DTO: contain department, List<Course_Students_DTO>