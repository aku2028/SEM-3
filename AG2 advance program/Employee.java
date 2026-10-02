class Employee {
    String name;
    int id;
    double salary;

    void display() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Salary: " + salary);
        System.out.println();
    }

    public static void main(String[] args) {

        System.out.println("RA2511026011370");
        Employee e1 = new Employee();
        Employee e2 = new Employee();

        e1.name = "Afeefa";
        e1.id = 370;
        e1.salary = 30000;

        e2.name = "Anu";
        e2.id = 438;
        e2.salary = 35000;

        e1.display();
        e2.display();
    }
}