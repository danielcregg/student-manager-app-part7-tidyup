package ie.gmit.studentmanagerpackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentManager implements Serializable {

	private static final long serialVersionUID = 1L;

	// Create Student ArrayList
	private List<Student> studentList;

	// Constructor
	public StudentManager() {
		// Instantiate a student ArrayList
		studentList = new ArrayList<Student>();
	}

	// Getters and Setters
	public List<Student> getStudents() {
		return studentList;
	}

	public void setStudents(List<Student> studentList) {
		this.studentList = studentList;
	}

	// Student Add Method
	public boolean addStudent(Student studentObject) {

		// Loop over all Students on list and check if new Student being added is
		// already on List
		for (Student stuObj : studentList) {
			if (stuObj.getStudentId().equals(studentObject.getStudentId())) {
				System.out.println("Student NOT added to Student List. Student already on Student List!");
				return false;
			}
		}

		return studentList.add(studentObject);
	}

	// Student Add Method
	public boolean removeStudent(Student studentObject) {
		return studentList.remove(studentObject);
	}

	public Student deleteStudentByNumber(int number) {
		try {
			return studentList.remove(number - 1);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("There are " + studentList.size()
					+ "sudents on the list. Please pick a number from 1 to " + studentList.size());
		}
		return null;
	}

	public int findTotalStudents() {
		// Returns the current number of Students in the ArrayList
		return studentList.size();
	}

	public String listAllStudnets() {
		// Create a StringBuilder object
		StringBuilder sb = new StringBuilder();
		int counter = 1;

		sb.append(String.format("%-20s%-20s%-20s%-20s%-20s\n", "No.", "ID", "Name", "Surname", "Year Of Study"));
		sb.append(String.format("===============================================================\n"));

		// sb.append("No.\tID\t\t\t\tName\t\t\tSurname\t\t\tYear of Study\n");
		// sb.append("----------------------------------------------------------\n");
		for (Student student : this.studentList) {
			sb.append(counter + ": " + student.findAllFieldValuesInCSVFormat().replace(",", "\t\t") + "\n");
			// sb.append(String.format("%-20s%-20\n",counter,
			// student.findAllFieldValuesInCSVFormat().replace(",", "\t\t") + "\n"));
			counter++;
		}

		return sb.toString();
	}

	public void loadStudentsFromCSVFile(File studentCSVFile) {
		FileReader studentCSVFileReader = null;
		BufferedReader bufferedStudentCSVFileReader = null;
		String bufferData = null; // Used to store lines of data we read from the buffer

		// Create a file reader
		try {
			studentCSVFileReader = new FileReader(studentCSVFile);
			// Add a buffer to the file reader
			bufferedStudentCSVFileReader = new BufferedReader(studentCSVFileReader);
			// Read first line of file and discard it. It contains column headers.
			bufferedStudentCSVFileReader.readLine();

			while ((bufferData = bufferedStudentCSVFileReader.readLine()) != null) {
				// System.out.println(bufferData);
				String[] studentFieldValues = bufferData.split(",");
				// System.out.println(Arrays.toString(studentFieldValues));
				Student newStudent = new Student(studentFieldValues[0], studentFieldValues[1], studentFieldValues[2],
						Integer.parseInt(studentFieldValues[3]));
				this.addStudent(newStudent); // Add student to the studentList
			}
			System.out.println("Loaded Students List from CSV file successfully!");
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				if (studentCSVFileReader != null) {
					// Flushes buffer, which transfers buffer data to the file, then closes buffer.
					bufferedStudentCSVFileReader.close();
					// Close the file reader stream
					studentCSVFileReader.close();
				}
			} catch (IOException IOExc) {
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End load method

	public void saveStudentsToCSVFile(File studentDBFile) {
		FileWriter studentFileWriterStream = null;
		BufferedWriter bufferedstudentFileWriterStream = null;
		try {
			studentFileWriterStream = new FileWriter(studentDBFile);
			bufferedstudentFileWriterStream = new BufferedWriter(studentFileWriterStream);
			bufferedstudentFileWriterStream.write("ID,First Name,Surname,Year of Study" + "\n");

			// Write out student data from studentList to buffer and flush it to CSV file
			for (Student studentObject : studentList) {
				bufferedstudentFileWriterStream.write(studentObject.getStudentId() + "," + studentObject.getFirstName()
						+ "," + studentObject.getSurname() + "," + studentObject.getYearOfStudy() + "\n");
				// bufferedstudentFileWriterStream.write(studentObject.findAllFieldValuesInCSVFormat()
				// + "\n");
				bufferedstudentFileWriterStream.flush(); // Flushes buffer which transfers buffer data to the file
			}
			System.out.println("Saved Students List to CSV file successfully!");
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				if (studentFileWriterStream != null) {
					// Close buffer
					bufferedstudentFileWriterStream.close();
					// Close file writer
					studentFileWriterStream.close();
				}
			} catch (IOException IOExc) {
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End Save method

	// Method to de-serialize the Student Manager Object
	public StudentManager loadStudentManagerObjectFromFile(File studentObjectsFile) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		StudentManager sm = null;

		try {
			fis = new FileInputStream(studentObjectsFile);
			ois = new ObjectInputStream(fis);
			sm = (StudentManager) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("hi mom");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					// Close ObjectOutputStream
					ois.close();
				}
				if (fis != null) {
					// Close FileOutputStream
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} // End catch
		} // End finally
		if (sm == null) {
		}
		return sm; // Returns null if no object is read in.
	}

	// Method to serialize the Student Manager Object
	public void saveStudentManagerObjectToFile(File studentObjectsFile) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(studentObjectsFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					// Close ObjectOutputStream
					oos.close();
				}
				if (fos != null) {
					// Close FileOutputStream
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} // End catch
		} // End finally
	}

} // End Class
