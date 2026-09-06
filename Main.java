class MovieTicket {
    private String seatNumber;
    String screenId;
    protected double ticketPrice;
    public String movieTitle;

    MovieTicket(String seatNumber, String screenId, double ticketPrice, String movieTitle) {
        this.seatNumber = seatNumber;
        this.screenId = screenId;
        this.ticketPrice = ticketPrice;
        this.movieTitle = movieTitle;
    }

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return accessorContext.equals("DIFFERENT_PACKAGE") ? "DENIED" : "ALLOWED";
        }

        if (fieldModifier.equals("protected")) {
            return accessorContext.equals("DIFFERENT_PACKAGE") ? "DENIED" : "ALLOWED";
        }

        return "ALLOWED";
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {
            if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        MovieTicket ticket = new MovieTicket("A1", "S1", 250.0, "Avatar");

        System.out.println(ticket.seatNumber);
        System.out.println(ticket.screenId);
        System.out.println(ticket.ticketPrice);
        System.out.println(ticket.movieTitle);

        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(summarizeBatch(attempts));
    }
}