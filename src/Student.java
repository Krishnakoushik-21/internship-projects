public class Student {
    public static final String[] SUBJECT_NAMES = {
            "Java",
            "Python",
            "DBMS",
            "Operating Systems",
            "Computer Networks"
    };

    private int studentId;
    private String studentName;
    private int age;
    private double[] marks;
    private double totalMarks;
    private double averageMarks;
    private String grade;
    private boolean passStatus;

    public Student(int studentId, String studentName, int age, double[] marks) {
        this.studentId = studentId;
        this.studentName = studentName.trim();
        this.age = age;
        this.marks = marks;
        calculateResult();
    }

    public void calculateResult() {
        totalMarks = calculateTotal(marks);
        averageMarks = calculateAverage(totalMarks, marks.length);
        grade = calculateGrade(averageMarks);
        passStatus = isPass();
    }

    public double calculateTotal(double[] studentMarks) {
        double result = 0.0;
        for (int i = 0; i < studentMarks.length; i++) {
            result = result + studentMarks[i];
        }
        return result;
    }

    public double calculateAverage(double total, int subjectCount) {
        return subjectCount == 0 ? 0 : total / subjectCount;
    }

    public String calculateGrade(double average) {
        if (average >= 90 && average <= 100) {
            return "A+";
        } else if (average >= 80 && average < 90) {
            return "A";
        } else if (average >= 70 && average < 80) {
            return "B";
        } else if (average >= 60 && average < 70) {
            return "C";
        } else if (average >= 50 && average < 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public boolean isPass() {
        boolean allSubjectsPassed = true;

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 40) {
                allSubjectsPassed = false;
                break;
            }
        }

        if (averageMarks < 50) {
            allSubjectsPassed = false;
        }

        return allSubjectsPassed;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double[] getMarks() {
        return marks;
    }

    public void setMarks(double[] marks) {
        this.marks = marks;
        calculateResult();
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isPassStatus() {
        return passStatus;
    }

    public String getPassStatusText() {
        return passStatus ? "PASS" : "FAIL";
    }
}
