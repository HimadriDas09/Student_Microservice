package com.example.student.Student.Implementation;

import com.example.student.Student.Student;
import com.example.student.Student.StudentRepository;
import com.example.student.Student.StudentService;
import com.example.student.Student.dto.Student_Course_Dept_DTO;
import com.example.student.Student.external.Course;
import com.example.student.Student.external.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {

    @Autowired
    RestTemplate restTemplate;

    StudentRepository studentRepository;

    StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    // RestTemplate -> make api call to another ms
    private Course getCourseById(Long id){
        String url = "http://localhost:8081/api/courses/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Course> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, Course.class, id);

        return exchange.getBody();
    }

    private Department getDepartmentById(Long id){
        String url = "http://localhost:8080/api/dept/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Department> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, Department.class, id);

        return exchange.getBody();
    }


    @Override
    public Student getStudentById(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public List<Student_Course_Dept_DTO> getAllStudents() {
        List<Student> allStudents = studentRepository.findAll();

        List<Student_Course_Dept_DTO> studentCourseDeptDtoList = new ArrayList<>();

        // iterate all students -> via student.deptId, student.courseId we can get the Course,Department object.
        // now check if studentCourseDeptDtoList contains a student with roll same as current student -> then just add course to courseList. Else -> create a DTO object and add all student, Department, empty list to companies

        Iterator<Student> studentIterator = allStudents.iterator();
        while(studentIterator.hasNext()){
            Student student = studentIterator.next();

            Long courseId = student.getCourseId();
            Long deptId = student.getDeptId();
            Long studentRoll = student.getRoll();

            // get Course, Department via RestTemplate
            Course course = getCourseById(courseId);
            Department department = getDepartmentById(deptId);

            Iterator<Student_Course_Dept_DTO> dtoIterator = studentCourseDeptDtoList.iterator();

            while(dtoIterator.hasNext()){
                Student_Course_Dept_DTO dto = dtoIterator.next();

                if(dto.getStudent().getRoll().equals(studentRoll)){
                    dto.getCourse().add(course);
                }else{
                    Student_Course_Dept_DTO studentCourseDeptDto = new Student_Course_Dept_DTO();

                    studentCourseDeptDto.setStudent(student);
                    studentCourseDeptDto.setCourse(new ArrayList<>(List.of(course)));
                    studentCourseDeptDto.setDepartment(department);

                    studentCourseDeptDtoList.add(studentCourseDeptDto);
                }
            }

        }

        return studentCourseDeptDtoList;
    }

    @Override
    public Student_Course_Dept_DTO getStudentByRollNo(Long roll) {

        // call above getAllStudents() -> get List<Student_Course_Dept_DTO> and then just find the 1st object which has the same rollNo.

        List<Student_Course_Dept_DTO> studentCourseDeptDtoList = getAllStudents();

        for(Student_Course_Dept_DTO studentCourseDeptDto : studentCourseDeptDtoList){
            if(studentCourseDeptDto.getStudent().getRoll().equals(roll)){
                return studentCourseDeptDto;
            }
        }

        return null;

    }

    @Override
    public List<Student_Course_Dept_DTO> getStudentByStudentName(String name) {
        // call getAllStudents from above -> push all the instances in a new list with matching names.

        List<Student_Course_Dept_DTO> studentCourseDeptDtoList = getAllStudents();

        List<Student_Course_Dept_DTO> result = new ArrayList<>();

        for(Student_Course_Dept_DTO studentCourseDeptDto : studentCourseDeptDtoList){
            if(studentCourseDeptDto.getStudent().getName().equals(name)){
                result.add(studentCourseDeptDto);
            }
        }

        return result.isEmpty() ? result : null;
    }

    @Override
    public boolean createStudent(Student student, Long deptId, Long courseId) {
        try {
            // check if dept exists > under it check if course exist.

            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
