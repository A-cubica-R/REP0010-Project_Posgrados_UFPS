package ufps.edu.co.domain.utilities;

public class PrinterObjects {

    // private static final String BORDER = "═".repeat(100);
    private static final String CORNER_TL = "╔";
    private static final String CORNER_TR = "╗";
    private static final String CORNER_BL = "╚";
    private static final String CORNER_BR = "╝";
    private static final String VERTICAL = "║";
    private static final String HORIZONTAL = "═";

    /**
     * Imprime un objeto de forma notoria y muy visible en la consola
     * 
     * @param object El objeto a imprimir
     */
    public static void printNotorious(Object object) {
        printNotorious("OBJETO", object);
    }

    /**
     * Imprime un objeto con título de forma notoria y muy visible en la consola
     * 
     * @param title  Título del mensaje
     * @param object El objeto a imprimir
     */
    public static void printNotorious(String title, Object object) {
        String message = object != null ? object.toString() : "null";
        int padding = 5;
        int contentWidth = Math.max(title.length(), message.length()) + (padding * 2);
        int totalWidth = contentWidth + 4;

        System.out.println();
        System.out.println(CORNER_TL + HORIZONTAL.repeat(totalWidth) + CORNER_TR);
        System.out.println(VERTICAL + " ".repeat(totalWidth) + VERTICAL);
        System.out
                .println(VERTICAL + " ▶▶ " + title + " ◀◀ " + " ".repeat(totalWidth - title.length() - 12) + VERTICAL);
        System.out.println(VERTICAL + " ".repeat(totalWidth) + VERTICAL);
        System.out.println(VERTICAL + " " + message + " ".repeat(totalWidth - message.length()) + VERTICAL);
        System.out.println(VERTICAL + " ".repeat(totalWidth) + VERTICAL);
        System.out.println(CORNER_BL + HORIZONTAL.repeat(totalWidth) + CORNER_BR);
        System.out.println();
    }

    /**
     * Imprime una lista de objetos de forma notoria
     * 
     * @param objects Objetos a imprimir
     */
    public static void printNotoriousList(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            printNotorious("OBJETO #" + (i + 1), objects[i]);
        }
    }
}
