

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface WashType {
    String getName();
    int getDuration();
    double getCharge();
}

class QuickWash implements WashType {
    public String getName() {
        return "Quick";
    }

    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20;
    }
}

class NormalWash implements WashType {
    public String getName() {
        return "Normal";
    }

    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30;
    }
}

class HeavyWash implements WashType {
    public String getName() {
        return "Heavy";
    }

    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45;
    }
}


class DelicateWash implements WashType {
    public String getName() {
        return "Delicate";
    }

    public int getDuration() {
        return 50;
    }

    public double getCharge() {
        return 40;
    }
}

class WashCycle {
    private final Student student;
    private final WashingMachine machine;
    private final WashType washType;

    public WashCycle(Student student, WashingMachine machine,
                     WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public Student getStudent() {
        return student;
    }

    public WashingMachine getMachine() {
        return machine;
    }

    public WashType getWashType() {
        return washType;
    }

    public double getCharge() {
        return washType.getCharge();
    }
}

class WashingMachine {
    private final String machineId;
    private WashCycle currentCycle;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isFree() {
        return currentCycle == null;
    }

    public WashCycle startWash(Student student, WashType washType) {
        if (!isFree()) {
            return null;
        }

        currentCycle = new WashCycle(student, this, washType);
        return currentCycle;
    }

    public WashCycle completeWash() {
        if (currentCycle == null) {
            return null;
        }

        WashCycle completed = currentCycle;
        currentCycle = null;
        return completed;
    }
}

class LaundrySystem {

    public void startWash(Student student,
                           WashingMachine machine,
                           WashType washType) {

        if (!machine.isFree()) {
            System.out.println(
                "Machine " + machine.getMachineId() + " is currently busy."
            );
            return;
        }

        WashCycle cycle = machine.startWash(student, washType);

        System.out.printf(
            "%s wash started on %s for %s (%d min).%n",
            washType.getName(),
            machine.getMachineId(),
            student.getName(),
            washType.getDuration()
        );

        System.out.printf(
            "Charge: ₹%.2f%n",
            cycle.getCharge()
        );
    }

    public void completeWash(WashingMachine machine) {
        WashCycle cycle = machine.completeWash();

        if (cycle == null) {
            System.out.println(
                machine.getMachineId() + " has no active cycle."
            );
            return;
        }

        System.out.println(
            machine.getMachineId() + " cycle completed."
        );

        System.out.println(
            machine.getMachineId() + " is now free."
        );
    }
}

public class LaundryDemo {
    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        LaundrySystem system = new LaundrySystem();

        system.startWash(asha, m1, new QuickWash());

        system.startWash(ravi, m1, new HeavyWash());

        system.startWash(ravi, m2, new HeavyWash());

        system.completeWash(m1);

        system.startWash(neha, m1, new NormalWash());
    }
}