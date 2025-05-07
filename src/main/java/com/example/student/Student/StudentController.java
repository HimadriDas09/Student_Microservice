package com.example.student.Student;

import com.example.student.Student.dto.Student_Course_Dept_DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student studentById = studentService.getStudentById(id);

        if(studentById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Student_Course_Dept_DTO>> getAllStudents(){
        List<Student_Course_Dept_DTO> studentCourseDeptDtoList = studentService.getAllStudents();

        return new ResponseEntity<>(studentCourseDeptDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student){
        boolean result = studentService.createStudent(student, student.getDeptId(), student.getCourseId());
        if(result){
            return new ResponseEntity<>("Student successfully created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("student not created", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Student_Course_Dept_DTO> getStudentByRollNo(@PathVariable Long roll){
        Student_Course_Dept_DTO studentCourseDeptDto = studentService.getStudentByRollNo(roll);

        if(studentCourseDeptDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(studentCourseDeptDto, HttpStatus.OK);
    }

    @GetMapping("/getName")
    public ResponseEntity<List<Student_Course_Dept_DTO>> getStudentByName(@RequestParam String name){
        List<Student_Course_Dept_DTO> studentByStudentName = studentService.getStudentByStudentName(name);

        if(studentByStudentName == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(studentByStudentName, HttpStatus.OK);
    }
}
