package com.kiet.assignment.controller;

import com.kiet.assignment.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final List<Student> students = new CopyOnWriteArrayList<>();

    // Add 5 students initially
    public StudentController() {
        students.add(new Student(101, "Rahul Sharma",
                "rahul@kiet.edu", "CSE", 2, 8.65));

        students.add(new Student(102, "Priya Singh",
                "priya@kiet.edu", "CSE", 2, 9.20));

        students.add(new Student(103, "Aman Verma",
                "aman@kiet.edu", "ECE", 3, 8.10));

        students.add(new Student(104, "Neha Gupta",
                "neha@kiet.edu", "CSE", 3, 9.10));

        students.add(new Student(105, "Rohit Kumar",
                "rohit@kiet.edu", "IT", 2, 7.90));
    }

    // 1. Get all students
    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    // 2. Get student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(
            @PathVariable int studentId) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 3. Add a new student
    @PostMapping
    public Student addStudent(
            @RequestBody Student student) {

        students.add(student);
        return student;
    }

    // 4. Update student
    @PutMapping("/{studentId}")
    public Object updateStudent(
            @PathVariable int studentId,
            @RequestBody Student updatedStudent) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());

                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 5. Delete student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(
            @PathVariable int studentId) {

        boolean removed = students.removeIf(
                s -> s.getStudentId() == studentId);

        if (removed) {
            return Map.of(
                    "message", "Student deleted successfully");
        }

        return Map.of("message", "Student not found");
    }

    // 6. Search by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getBranch().equalsIgnoreCase(branch)
                    && s.getYear() == year) {
                result.add(s);
            }
        }

        return result;
    }

    // 7. Find students with CGPA greater than or equal to given value
    @GetMapping("/topper")
    public List<Student> getToppers(
            @RequestParam double cgpa) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {
            if (s.getCgpa() >= cgpa) {
                result.add(s);
            }
        }

        return result;
    }
}