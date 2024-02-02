package com.solvd.universityapp;

import com.solvd.universityapp.bin.Address;
import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.Student;
import com.solvd.universityapp.bin.Term;
import com.solvd.universityapp.bin.exception.DegreeProgramNotFoundException;
import com.solvd.universityapp.service.StudentService;
import com.solvd.universityapp.service.mybatisimpl.StudentServiceImpl;
import com.solvd.universityapp.util.ConnectionPool;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StudentServiceTest {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    @BeforeSuite
    public void suiteSetUp(){
        runSQLScripts("src/test/resources/SQLScripts/MySQLCreation.sql");

    }

    @AfterSuite
    public void suiteClose(){
        runSQLScripts("src/test/resources/SQLScripts/DropTestSchema.sql");
    }

    @BeforeTest
    public void testSetUp(){
        runSQLScripts("src/test/resources/SQLScripts/PopulateSQLTables.sql");
    }

    @AfterTest
    public void testClose(){
        runSQLScripts("src/test/resources/SQLScripts/TruncateTestTables.sql");
    }


    public void runSQLScripts(String fileName){
        Connection conn = connectionPool.getConnection();
        try(Reader reader = new FileReader(fileName)) {
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.runScript(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Test(description = "Verify find by id method in student service")
    public void verifyStudentFindById(){
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.findById(1L);

        Assert.assertNotNull(student);
        Assert.assertEquals("Jakob", student.getFirstName());
        Assert.assertEquals("Ableitner", student.getLastName());
        Assert.assertEquals("ja@gmail.com", student.getEmail());
        Assert.assertEquals("Bachelor of Science in Chemical Engineering", student.getDegreeProgram().getDegreeProgramName());
    }

    @Test(description = "Verify create student method")
    public void verifyStudentCreate(){
        Student student = new Student();
        student.setFirstName("Tim");
        student.setLastName("Smith");
        student.setEmail("tim@solvd.com");

        StudentService studentService = new StudentServiceImpl();
        studentService.create(student, 1L);

        Student retrievedStudent = studentService.findByEmail("tim@solvd.com");

        Assert.assertNotNull(retrievedStudent);
        Assert.assertEquals("Tim", retrievedStudent.getFirstName());
        Assert.assertEquals("Smith", retrievedStudent.getLastName());
        Assert.assertEquals("tim@solvd.com", retrievedStudent.getEmail());
    }

    @Test(description = "Verify update student degree program")
    public void verifyUpdateStudentDegreeProgram(){
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.findById(1L);
        studentService.updateStudentDegreeProgram(student, 1L);

        Student retrievedStudent = studentService.findById(1L);

        Assert.assertEquals(1, retrievedStudent.getDegreeProgram().getId());
        Assert.assertEquals("Bachelor of Science in Computer Science", retrievedStudent.getDegreeProgram().getDegreeProgramName());
    }

    @Test(description = "Verify update student with incorrect degree program id")
    public void verifyUpdateStudentWithIncorrectDegreeProgram(){
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.findById(1L);
        Assert.expectThrows(DegreeProgramNotFoundException.class, () -> studentService.updateStudentDegreeProgram(student, 5L));
    }

    @Test (description = "Verify delete student by email")
    public void verifyDeleteStudent(){
        StudentService studentService = new StudentServiceImpl();
        studentService.deleteByEmail("tt@hotmail.com");

        Assert.expectThrows(com.solvd.universityapp.bin.exception.StudentNotFoundException.class, () -> studentService.findByEmail("tt@hotmail.com"));
    }

    @Test (description = "Verify add course to student")
    public void verifyAddCourseToStudent() {
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.findById(2L);

        studentService.addCourse(student, 2L);

        Student retrieveStudent = studentService.findById(2L);
        Set<Course> courses = retrieveStudent.getCourses();

        Assert.assertTrue(courses.size() == 3);

        boolean hasCourse = false;
        for (Course course : courses) {
            if (course.getId() == 2L) {
                hasCourse = true;
            }
        }
        Assert.assertTrue(hasCourse);
    }

    @Test (description = "Verify remove course from student")
    public void verifyRemoveCourseFromStudent(){
        StudentService studentService = new StudentServiceImpl();

        Student student = studentService.findById(1L);

        studentService.removeCourse(student, 2L);

        Student retrieveStudent = studentService.findById(1L);
        Set<Course> courses = retrieveStudent.getCourses();

        Assert.assertTrue(courses.size() == 1);

        boolean hasCourse = false;
        for (Course course : courses) {
            if (course.getId() == 2L) {
                hasCourse = true;
            }
        }
        Assert.assertFalse(hasCourse);
    }

    @Test
    public void verifyAddXMLDataToStudent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Course course = new Course();
        course.setId(5L);
        course.setTerm(new Term(1L));

        Student student = new Student();
        student.setId(1L);
        student.setEmail("j");
        student.setFirstName("Jimmy");
        student.setLastName("Smalls");
        student.setAddress(new Address(2L));
        student.setCourses(new HashSet<>(Arrays.asList(course)));

        Method method = StudentServiceImpl.class.getDeclaredMethod("addXMLDataToStudent", Student.class);
        method.setAccessible(true);
        method.invoke(StudentServiceImpl.class.getDeclaredConstructor().newInstance(), student);

        Assert.assertEquals("60th street", student.getAddress().getStreetLine());
        Assert.assertEquals("Minneapolis", student.getAddress().getCity());

        Optional<Course> outputCourse = student.getCourses().stream().findFirst();

        Assert.assertTrue(outputCourse.isPresent());
        Assert.assertEquals("Spring 2024", outputCourse.get().getTerm().getTermName());
        Assert.assertEquals(Date.valueOf("2024-01-06"), outputCourse.get().getTerm().getStartDate());
    }
}
