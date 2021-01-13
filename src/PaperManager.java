import java.util.Scanner;

class Paper {
    String author;
    String title;
    Date publicationDate;
    int pages;
}

class Date {
    int y, m, d;
}

class PaperDB {
    PaperDB db[];
}

public class PaperManager {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Literature Management Tool!");
        System.out.println("Choose what you want to do:");
        menu();

    }

    public static void add (PaperDB db, Paper p) {
        PaperDB paperDB[] = new PaperDB[0];
    }

    public static void createPaper(String author, String title, int y, int m, int d, int pages) {
        Paper p = new Paper();
        p.author = author;
        p.title = title;
        p.publicationDate.y = y;
        p.publicationDate.m = m;
        p.publicationDate.d = d;
        p.pages = pages;
    }

    public static void menu() {
        System.out.println("1 to create a new paper");
        System.out.println("2 to delete an entry");
        System.out.println("3 to display publications");
        System.out.println("4 to link a reference");
        System.out.println("5 to search through the papers");
        System.out.println("6 to get a statistical analysis");
        System.out.println("0 will exit the program!");

        int selection = sc.nextInt();
        while(selection<0 || selection >6) {
            System.out.println("Selection invalid. Please choose a number between 0-6.");
            selection = sc.nextInt();
        }

        if(selection == 0) {
            System.out.println("0 - closing program");
            System.exit(0);
        }else if(selection == 1) {
            System.out.println("1 - create a new paper:");

        }else if(selection == 2) {
            System.out.println("2 - delete an entry:");

        }else if(selection == 3) {
            System.out.println("3 - display publications:");

        }else if(selection == 4) {
            System.out.println("4 - link a reference:");

        }else if(selection == 5) {
            System.out.println("5 - search through the papers:");

        }else if(selection == 6) {
            System.out.println("6 - get a statistical analysis:");

        }else{
            System.err.println("You should not see this error message. Please contact the administrator!");
            System.exit(0);
        }

    }


}
