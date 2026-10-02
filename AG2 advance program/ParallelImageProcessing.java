class ImageThread extends Thread {
    String task;
    String value;

    ImageThread(String task, String value) {
        this.task = task;
        this.value = value;
    }

    public void run() {
        System.out.println(task + " started");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(task + " completed");
    }
}

public class ParallelImageProcessing {
    public static void main(String[] args) throws Exception {

  
        String input = "image:jpg resize:300 greyscale:900 compression:80";

        String[] parts = input.split(" ");

        String image = parts[0].split(":")[1];
        String resize = parts[1].split(":")[1];
        String greyscale = parts[2].split(":")[1];
        String compression = parts[3].split(":")[1];

        Thread t1 = new ImageThread("Resize", resize);
        Thread t2 = new ImageThread("Greyscale", greyscale);
        Thread t3 = new ImageThread("Compression", compression);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Image processing completed");
    }
}