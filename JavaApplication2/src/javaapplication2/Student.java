
package javaapplication2;


public class Student {
    
    private  String id;
    private  String name;
    private  String password;
    private  Course[] courses;// مصفوفة لكل طالب نخزن فيها معلومات كل كورس 
    private int enrolledCourseCount;// عدد الكورسات الي هيدخلها كل طالب

    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.courses = new Course[5];// عدد الكورسات المسموحة لكل طالب 5 
        this.enrolledCourseCount = 0;
    }

    public Course[] getCourses() {
        return courses;
    }

    public int getEnrolledCourseCount() {
        return enrolledCourseCount;
    }
    
    
    

    public String getId() {
        return id;
    }

    public boolean verifyPassword(String password) {// فانكشن للتأكد ان الباسورد الي بيدخله الطالب نفس الباسورد الي متخزن ليه
        return this.password.equals(password);
    }

    public void enrollCourse(Course course) {// فانكشن بتاخد اوبجيكت 
        if (enrolledCourseCount < 5) {// للتأكد ان كل طالب ميدخلش اكتر من 5 كورسات
            courses[enrolledCourseCount] = course;
            enrolledCourseCount++;
        } else {
            System.out.println("Maximum courses enrolled.");
        }
    }

    public void removeCourse(String courseId) {// فانكشن بتعدي على كل الكورسات الي دخلها الطالب لو الي دخله بيساوي لاي كورس بنخلي الكورس الاخير مكان الكورس دا وبنخلي مكان اخر كورس فاضي
        
        for (int i = 0; i < enrolledCourseCount; i++) {
            if (courses[i].getCourseId().equals(courseId)) {
                courses[i] = courses[enrolledCourseCount - 1]; 
                courses[enrolledCourseCount - 1] = null;
                enrolledCourseCount--;// ونقلل عدد الكورسات الي دخلها الطالب بواحد
                return;
            }
        }
        //System.out.println("Trying to remove a non-existent course");
    }

    public void showInfo() { // فانكشن لعرض تفاصيل كل طالب
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Enrolled Courses:");
        for (int i = 0; i < enrolledCourseCount; i++) {
            System.out.println(courses[i]);
        }
    }

    
}
