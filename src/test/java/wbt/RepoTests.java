package wbt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wbt.wbt.domain.Student;
import wbt.wbt.repository.NotaXMLRepo;
import wbt.wbt.repository.StudentXMLRepo;
import wbt.wbt.repository.TemaXMLRepo;
import wbt.wbt.service.Service;
import wbt.wbt.validation.NotaValidator;
import wbt.wbt.validation.StudentValidator;
import wbt.wbt.validation.TemaValidator;
import wbt.wbt.validation.ValidationException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoTests {

    private StudentValidator studentValidator;
    private TemaValidator temaValidator;
    String filenameStudent = "fisiere/Studenti.xml";
    String filenameTema = "fisiere/Teme.xml";
    String filenameNota = "fisiere/Note.xml";
    private StudentXMLRepo studentXMLRepository;
    private TemaXMLRepo temaXMLRepository;
    private NotaValidator notaValidator;
    private NotaXMLRepo notaXMLRepository;
    private Service service;

    @BeforeEach
    public void setup() {
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testStudent() {
        Student student = new Student("1234", "Sanda", 934, "sanda@gmail.com");
        assertSame(service.addStudent(student), student);

    }

    @Test
    public void testStudentFail() {
        Student student = new Student("1234", "Sanda", -934, "sanda@gmail.com");
        Exception exception = assertThrows(ValidationException.class, () -> service.addStudent(student));

        String expectedMessage = "Grupa incorecta!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
