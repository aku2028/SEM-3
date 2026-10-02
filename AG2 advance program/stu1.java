class stu1{
    String name;
    int rollNumber;

    void display() {
        System.out.println("Student Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
    }

    public static void main(String[] args) {
        stu1 s = new stu1();
        System.out.println("RA2511026011370");

        s.name = "Afeefa";
        s.rollNumber = 370;

        s.display();
    }
}