package com.example.student.Student.dto;

import com.example.student.Student.Student;
import com.example.student.Student.external.Course;
import com.example.student.Student.external.Department;

import java.util.List;

public class Student_Course_Dept_DTO {
    private Student student;
    private Department department;
    private List<Course> course;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }
}
