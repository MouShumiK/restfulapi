package com.example.restfulapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepo;
    @Autowired
    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }
    public List<Student> getStudents(){
        return studentRepo.findAll();
    }

    public Student addStudent(Student student) {
        //Need to check repo to ensure student doesn't already exist
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Student already exists");
        }
        studentRepo.save(student);
        return student;
    }
    public void deleteStudent(Long studentId){
        if(studentRepo.existsById(studentId)){
            studentRepo.deleteById(studentId);
        } else {
            throw new IllegalStateException("Student not found");
        }
    }
    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate dob) {

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new IllegalStateException("Student not found"));
        if (student.getName() !=name && student.getName() != null) {
            student.setName(name);
            System.out.println("Name updated");
        }

        Optional<Student> studentOptional = studentRepo.findStudentByEmail(student.getEmail());
        if (!studentOptional.isPresent()) {
            student.setEmail(email);
        }
        if (!student.getDob().equals(dob) && student.getDob() != null) {
            student.setDob(dob);
        }
    }
}

//    Next we need a method that will delete a student when we include the students id as a Path variable. In the StudentService class create a method named deleteStudent() that takes in a parameter named studentId of type Long. We have a method from our JpaRepository available to us named deleteById() that will work for deleting the student and we have a method named existsById that will allow us to know if a student with that id exists. Write the code to check if the student exists by the id supplied. If they don’t exist you should throw new IllegalStateException(“Student not found”). If they do exist you should delete them using the deleteById() method.
//
//        The last method we need to create in our StudentService class is our updateStudent() method. This method should take in the following parameters:
//        Long studentId, String name, String email, LocalDate dob. Add the @Transactional annotation to this class as well. This method will require more logic than the others. First of all you will need to check if the id exists for the student that is being updated. One way to do this would be as follows:
//
