package com.example.matriculas.controller;

import com.example.matriculas.StudentService;
import com.example.matriculas.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(@RequestParam(value = "name", required = false) String name, Model model) {
        List<Student> students;
        if (name != null) {
            students = studentService.findByNameContaining(name);
        } else {
            students = studentService.getAllStudents();
        }
        model.addAttribute("students", students);
        model.addAttribute("searchTerm", name);
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        studentService.saveStudent(existingStudent);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
