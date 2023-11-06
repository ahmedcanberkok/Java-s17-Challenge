package com.workintech.Java.Spring.REST.API.controller;

import com.workintech.Java.Spring.REST.API.exceptions.CourseException;
import com.workintech.Java.Spring.REST.API.exceptions.CourseValidation;
import com.workintech.Java.Spring.REST.API.mapping.CourseResponse;
import com.workintech.Java.Spring.REST.API.mapping.CourseResponseFactory;
import com.workintech.Java.Spring.REST.API.model.Course;
import com.workintech.Java.Spring.REST.API.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses = new ArrayList<>();
    private CourseGpa lowGpa;
    private CourseGpa mediumGpa;
    private CourseGpa highGpa;


    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumGpa,
                            @Qualifier("highCourseGpa") CourseGpa highGpa) {
        this.lowGpa = lowGpa;
        this.mediumGpa = mediumGpa;
        this.highGpa = highGpa;
    }

    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
    }

    @GetMapping("/")
    public List<Course> getAll() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course getById(@PathVariable String name) {
        List<Course> foundCourses = courses.stream().filter(course -> course.getName().
                equals(name)).collect(Collectors.toList());
        if (foundCourses.size() == 0) {
            throw new CourseException("Course with given name is not exist"+name, HttpStatus.BAD_REQUEST);
        }

        return foundCourses.get(0);
    }

    @PostMapping("/")
    public CourseResponse save(@RequestBody Course course) {
        CourseValidation.isIdValid(course.getId());
        CourseValidation.checkCourseIsValid(course);
        CourseValidation.isDuplicateNameFound(courses, course.getName());

        courses.add(course);

       return CourseResponseFactory.createCourseResponse(course,lowGpa,mediumGpa,highGpa);


    }

    @PutMapping("{id}")
    public Course update(@RequestBody Course course, @PathVariable int id) {
        // TODO id check valid
        CourseValidation.checkCourseIsValid(course);
        Optional<Course> foundCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (foundCourse.isPresent()) {
            int index = courses.indexOf(foundCourse.get());
            course.setId(id);
            courses.set(index, course);

            return course;
        } else {
            throw new CourseException("Course with given id is not exist" + id, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id) {
        Optional<Course> foundCourse = courses.stream().filter(c -> c.getId() == id).findFirst();
        if (foundCourse.isPresent()) {
            int index = courses.indexOf(foundCourse.get());
            courses.remove(index);
            return foundCourse.get();
        } else {
            throw new CourseException("Course with given id is not exist" + id, HttpStatus.BAD_REQUEST);
        }


    }
}

