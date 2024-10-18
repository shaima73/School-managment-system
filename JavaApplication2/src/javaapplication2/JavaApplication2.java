package javaapplication2;

import java.util.Scanner;

public class JavaApplication2 {
    private static  Student[] students = new Student[100];// اراي من نوع طالب نخزن فيها معلومات كل طالب   نستخدم ستاتيك علشان الطالب كلهم يشتركوا في نفس المصفوفة
    private static int studentCount = 0;
    private static  Course[] courses = new Course[5];// اراي نخزن فيها المواد لكل طالب
    private static  Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();// فانكشن جواها تعريف للخمس مواد المتاحة
        while (true) {
            int option = 0;
            boolean validOption = false;
            while (!validOption) {
                System.out.println("==========================================================");
                System.out.println("                      School Management                    ");
                System.out.println("==========================================================");
                System.out.println("Menu:");
                System.out.println("1- Add Student");
                System.out.println("2- Enroll Course");
                System.out.println("3- Remove Course");
                System.out.println("4- Show Student Info");
                System.out.println("5- Exit");
                System.out.println("-----------------------------------------------------------");
                System.out.print("Choose an option: ");

                if (scanner.hasNextInt()) { // دالة بترجع ترو لو الانبت ارقام علشان لما ندخل حرف البرنامج مش يقف
                    option = scanner.nextInt();
                    scanner.nextLine(); // للتخلص من الخط الجديد الي هو انتر بعد ما بندخل الرقم
                    if (option >= 1 && option <= 5) {
                        validOption = true; // خرج من الحلقة عند إدخال خيار صحيح
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");// لو دخل حرف 
                    scanner.nextLine(); // للتخلص من الإدخال غير الصحيح
                }
            }

            switch (option) {
                case 1 -> addStudent();
                case 2 -> enrollCourse();
                case 3 -> removeCourse();
                case 4 -> showStudentInfo();
                case 5 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
            }
        }
    }

    private static void initializeCourses() {
        courses[0] = new Course("A1", "OOP", 100);
        courses[1] = new Course("A2", "DataBase", 80);
        courses[2] = new Course("A3", "Datastructure", 50);
        courses[3] = new Course("A4", "Algorithm", 70);
        courses[4] = new Course("A5", "Computer Science", 150);
    }

    private static void addStudent() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("                  Adding a New Student                     ");
        System.out.println("-----------------------------------------------------------");

        String id;
        do {
            System.out.print("Enter Student ID (16 digits): ");
            id = scanner.nextLine();
            if (!id.matches("\\d{16}")) {// للتأكد انه ارقام ومن 16 رقم
                System.out.println("Invalid ID. Student ID must contain 16 digits and only numbers.");
            }
        } while (!id.matches("\\d{16}"));  

        if (findStudentById(id) != null) {// دالة بتتأكد ان ال دا مش موجود قبل كداid 
            System.out.println("Student ID already exists.");
            return;
        }

        String name;
        do {
            System.out.print("Enter Name (letters and spaces only): ");
            name = scanner.nextLine();
            if (" ".equals(name) || !name.matches("[a-zA-Z ]+")) {// للتأكد انه حروف ومسافات فقط وميكونش فاضي 1
                
                System.out.println("Invalid name. The name must contain only letters and spaces.");
            }
        } while (!name.matches("[a-zA-Z ]+") || " ".equals(name)); 

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        students[studentCount] = new Student(id, name, password);// بنخزن معلومات الطالب في الاراي 
        studentCount++;// نزود عدد الطلاب 
        System.out.println("-----------------------------------------------------------");
        System.out.println("Student added successfully!");
        System.out.println("-----------------------------------------------------------");
    }

    private static void enrollCourse() {// فانكشن علشان الطالب يختار الكورس الي عايز يدخله
        System.out.println("-----------------------------------------------------------");
        System.out.println("                Enrolling in a Course                      ");
        System.out.println("-----------------------------------------------------------");

        Student student = authenticateStudent();// دالة بتاخد معلومات الطالب وبتتأكد انه مسجل قبل كدا
        if (student == null) return;// لو الدالة رجعت يبقى الطالب مش موجود null 

        System.out.println("Available courses:");
        System.out.println("-----------------------------------------------------------");
        for (Course course : courses) { // فور لوب تعرض الكورسات المتخزنة في الاراي بتاعت الكورسات
            System.out.println(course);
        }
        System.out.println("-----------------------------------------------------------");

        System.out.print("Enter Course ID to enroll: ");
        String courseId = scanner.nextLine();
        Course course = findCourseById(courseId);//  فانكشن بتتأكد ان الكورس دا موجودid  
        if (course == null) {// لو رجعت يبقى الكورس مش موجود null 
            System.out.println("Invalid Course ID.");
            return;
        }
        student.enrollCourse(course);// فانكشن بتخزن الكورس لكل طالب 
        System.out.println("Course enrolled successfully!");
        System.out.println("-----------------------------------------------------------");
    }

    private static void removeCourse() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("                Removing a Course                          ");
        System.out.println("-----------------------------------------------------------");

        Student student = authenticateStudent(); // دالة بتاخد معلومات الطالب وبتتأكد انه مسجل قبل كدا
        if (student == null) return;

        System.out.print("Enter Course ID to remove: ");
        String courseId = scanner.nextLine();
        
        boolean isEnrolled = false;
        for (int i = 0; i < student.getEnrolledCourseCount(); i++) { // فور لوب بتمشي لحد عدد الكورسات الي دخلها كل طالب 
            if (student.getCourses()[i].getCourseId().equals(courseId)) {//
                isEnrolled = true;
                break;
            }
        }

        if (!isEnrolled) {
            System.out.println("Cannot remove course. Student is not enrolled in this course.");
            return;
        }
        
        student.removeCourse(courseId);
        System.out.println("Course removed successfully!");
        System.out.println("-----------------------------------------------------------");
    }

    private static void showStudentInfo() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("                Showing Student Information                ");
        System.out.println("-----------------------------------------------------------");

        Student student = authenticateStudent();
        if (student == null) return;

        student.showInfo();
        System.out.println("-----------------------------------------------------------");
    }

    private static Student authenticateStudent() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("                Student Authentication                     ");
        System.out.println("-----------------------------------------------------------");

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return null;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (!student.verifyPassword(password)) {
            System.out.println("Incorrect password.");
            return null;
        }
        System.out.println("-----------------------------------------------------------");
        return student;
    }

    private static Student findStudentById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    private static Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}