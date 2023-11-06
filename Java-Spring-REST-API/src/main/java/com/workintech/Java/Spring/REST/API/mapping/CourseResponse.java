package com.workintech.Java.Spring.REST.API.mapping;

import com.workintech.Java.Spring.REST.API.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Course course ;
    private double totalGpa ;

}
