import java.sql.SQLOutput;
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
    Paper[] paperDB = new Paper[10]; //instead of copying to new array (+1) every time, I do it in batches of ten
    int free = 0;
}

public class PaperManager {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        PaperDB db = new PaperDB();

        System.out.println("Welcome to the Literature Management Tool!");
        System.out.println("Choose what you want to do:");
        menu();

    }

    public static boolean checkDate(int y, int m, int d) {
        boolean isLeapYear = false;

        //check if year is correct
        if (y < 1665 || y > 2021) {
            /*
            Fun fact: the first published paper ever was from January 1665 and was called "Journal des sçavans".
            That is why no year before that could be correct.

            I also assume this program will not be used after this current year (2021), so no future papers
            can be saved with this program.
             */
            System.out.println("You're either a time traveler or put in the wrong year, try again please!");
            return false;
        }

        //check if year is a leap year
        if ((y % 4) == 0) {
            isLeapYear = true;
        }
        if ((y % 100) == 0) {
            isLeapYear = false;
        }
        if ((y % 400) == 0) {
            isLeapYear = true;
        }

        //check if month is correct
        if (m < 1 || m > 12)
            return false;

        //check days for February
        if (m == 2 && isLeapYear == true) {
            if (d < 1 || d > 29)
                return false;
        } else if (m == 2 && isLeapYear == false) {
            if (d < 1 || d > 28)
                return false;
            //check other months
        } else if (m == 4 || m == 6 || m == 8 || m == 10 || m == 12) {
            if (d < 1 || d > 30)
                return false;
        } else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 9 || m == 11) {
            if (d < 1 || d > 31)
                return false;
        }
        //IntelliJ does not want an "else{return true}
        return true;
    }

    public static Paper scanPaper() {
        boolean isDateCorrect = false;
        System.out.println("Please enter the author of the paper:");
        String author = sc.next();
        System.out.println("Please enter the title of the paper:");
        String title = sc.next();

        //put date in and check
        while (isDateCorrect == false) {
            System.out.println("Please enter the publication year:");
            int y = sc.nextInt();
            System.out.println("Please enter the publication month:");
            int m = sc.nextInt();
            System.out.println("Please enter the publication day:");
            int d = sc.nextInt();
            isDateCorrect = checkDate(y, m, d);
        }

        System.out.println("Please enter the amount of pages of the paper:");
        int pages = sc.nextInt();

    }

    public static void add(PaperDB db, Paper p) {
        if (db.free == db.paperDB.length) {
            Paper temp[] = new Paper[db.paperDB.length + 10];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = db.paperDB[i];
            }
            temp[db.free] = p;
            db.paperDB = temp;

        }
        db.paperDB[db.free] = p;
        db.free++;
    }

    public static Paper createPaper(String author, String title, int y, int m, int d, int pages) {
        Paper p = new Paper();
        p.author = author;
        p.title = title;
        p.publicationDate.y = y;
        p.publicationDate.m = m;
        p.publicationDate.d = d;
        p.pages = pages;

        return p;
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
        while (selection < 0 || selection > 6) {
            System.out.println("Selection invalid. Please choose a number between 0-6.");
            selection = sc.nextInt();
        }

        if (selection == 0) {
            System.out.println("0 - closing program");
            System.exit(0);
        } else if (selection == 1) {
            System.out.println("1 - create a new paper:");

        } else if (selection == 2) {
            System.out.println("2 - delete an entry:");

        } else if (selection == 3) {
            System.out.println("3 - display publications:");

        } else if (selection == 4) {
            System.out.println("4 - link a reference:");

        } else if (selection == 5) {
            System.out.println("5 - search through the papers:");

        } else if (selection == 6) {
            System.out.println("6 - get a statistical analysis:");

        } else {
            System.err.println("You should not see this error message. Please contact the administrator!");
            System.exit(0);
        }

    }


}
