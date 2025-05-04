package com.example.student.Student;

import com.example.student.Student.dto.Student_Course_Dept_DTO;

import java.util.List;

public interface StudentService {
    Student getStudentById(Long id);

    List<Student_Course_Dept_DTO> getAllStudents();
    Student_Course_Dept_DTO getStudentByRollNo(Long roll);
    List<Student_Course_Dept_DTO> getStudentByStudentName(String name);
    boolean createStudent(Student student, Long deptId, Long courseId);
}
