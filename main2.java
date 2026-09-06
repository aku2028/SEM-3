class AccessChecker {
    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (accessorContext.equals("SAME_CLASS")) {
            return "ALLOWED";
        }

        if (accessorContext.equals("SAME_PACKAGE")) {
            return fieldModifier.equals("private") ? "DENIED" : "ALLOWED";
        }

        if (accessorContext.equals("DIFFERENT_PACKAGE")) {
            return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
        }

        if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
            return fieldModifier.equals("protected") || fieldModifier.equals("public")
                    ? "ALLOWED" : "DENIED";
        }

        if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {
            return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
        }

        return "DENIED";
    }
}

public class main2 {
    public static void main(String[] args) {
        System.out.println(
            AccessChecker.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
            )
        );

        System.out.println(
            AccessChecker.classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );
    }
}