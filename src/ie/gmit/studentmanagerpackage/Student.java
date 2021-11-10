package ie.gmit.studentmanagerpackage;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Instance Variables
	private String studentId;
	private String firstName;
	private String surname;
	private int yearOfStudy;

	// Constructor1
	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	// Constructor2
	public Student(String studentId, String fisrtName, String surname) {
		this.studentId = studentId;
		this.firstName = fisrtName;
		this.surname = surname;
	}

	// Constructor3
	public Student(String studentId, String fisrtName, String surname, int yearOfStudy) {
		this.studentId = studentId;
		this.firstName = fisrtName;
		this.surname = surname;
		this.yearOfStudy = yearOfStudy;
	}

	// Getters and Setters
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fisrtName) {
		this.firstName = fisrtName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	
	public String findAllFieldValuesInCSVFormat() {
		StringBuilder listOfFields = new StringBuilder();
		//String newLine = System.getProperty("line.separator");
		
		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names
		for (Field field : fields) {
		//	result.append("  ");
		try {
			// discard static fields
			if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				//listOfFields.append(field.getName());
				//requires access to private field:
				listOfFields.append( field.get(this) );
				listOfFields.append(",");
		    }	
				// requires access to private field:
				//result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			//result.append(newLine);
		}
		// Remove comma at end
		if( listOfFields.length() > 0 ) {
			listOfFields.setLength( listOfFields.length() - 1 );
			//listOfFields.append(newLine); 
		}

		return listOfFields.toString();
	}

}
