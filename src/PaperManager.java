import java.util.Scanner;

class Paper {
    String author;
    String title;
    Date publicationDate = new Date();
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

        add(db, createPaper("Brunnbauer", "b-title", 2014, 5, 30, 2));
        add(db, createPaper("Cerny", "a-title", 2020, 3, 13, 3));
        add(db, createPaper("Demetz", "r-title", 1990, 1, 7, 88));
        add(db, createPaper("Hösel", "R-title", 1999, 12, 31, 2));
        add(db, createPaper("Mandl", "g-title", 2018, 7, 6, 5));
        add(db, createPaper("Satek", "z-title", 2004, 8, 27, 12));
        add(db, createPaper("Brunnbauer", "l-title", 2004, 8, 17, 2));

        while (true) {
            menu(db);
            System.out.println();
        }
    }

    public static void menu(PaperDB db) {
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
            scanPaper(db);

        } else if (selection == 2) {
            System.out.println("2 - delete an entry:");
            deletePaper(db);

        } else if (selection == 3) {
            System.out.println("3 - display publications:");
            printPaperLine(db, 0);
            printPaperLine(db, 1);

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

    public static void scanPaper(PaperDB db) {
        boolean isDateCorrect = false;
        sc.nextLine(); //it catches the newline (\n) of me pressing "Enter".
        System.out.println("Please enter the author of the paper:");
        String author = sc.nextLine();
        System.out.println("Please enter the title of the paper:");
        String title = sc.nextLine();

        //put date in and check
        //compiler doesn't like uninitialized variables, even if they will be further down
        int y = 1970;
        int m = 1;
        int d = 1;
        while (isDateCorrect == false) {
            System.out.println("Please enter the publication year:");
            y = sc.nextInt();
            System.out.println("Please enter the publication month:");
            m = sc.nextInt();
            System.out.println("Please enter the publication day:");
            d = sc.nextInt();
            isDateCorrect = checkDate(y, m, d);
            if (isDateCorrect == false)
                System.out.println("The Date you entered seems to be incorrect, please try again:");
        }

        System.out.println("Please enter the amount of pages of the paper:");
        int pages = sc.nextInt();

        add(db, createPaper(author, title, y, m, d, pages));
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
            System.out.println("You're either a time traveler or entered the wrong year, try again please!");
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
        } else if (m == 2) {
            if (d < 1 || d > 28)
                return false;
            //check other months
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            if (d < 1 || d > 30)
                return false;
        } else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            if (d < 1 || d > 31)
                return false;
        }
        //IntelliJ does not want an "else{return true}
        return true;
    }

    public static Paper createPaper(String author, String title, int y, int m, int d, int pages) {
        Paper p = new Paper();
        p.author = author;
        p.title = title;
        p.publicationDate.y = y;
        p.publicationDate.m = m;
        p.publicationDate.d = d;
        p.pages = pages;

        System.out.println("Paper successfully created!\n");

        return p;
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

    private static void deletePaper(PaperDB db) {
        System.out.println("Which entry would you like to delete? ");
        int idx = sc.nextInt();
        while(idx>=db.free){
            System.out.println("There is no paper at that location.!");
            System.out.printf("Please enter a number between 0 and %d\n", db.free-1);
            idx = sc.nextInt();
        }
        int i=0;
        while(db.paperDB[i] != null){
            if(idx<=i){
                db.paperDB[i] = db.paperDB[i+1];
                db.paperDB[i+1] = null;
            }
            i++;
        }
        db.free--; //one free space is added back
    }

    public static void printPaperLine(PaperDB db, int idx) {
        while(idx>=db.free){
            System.out.println("There is no paper at that location.!");
            System.out.printf("Please enter a number between 0 and %d\n", db.free-1);
            idx = sc.nextInt();
        }

        System.out.printf("%-30.30s - %-30.30s - %4d.%02d.%02d %d\n", db.paperDB[idx].author,
                db.paperDB[idx].title,
                db.paperDB[idx].publicationDate.y,
                db.paperDB[idx].publicationDate.m,
                db.paperDB[idx].publicationDate.d,
                db.paperDB[idx].pages);

    }

    public static void printPaperShort(PaperDB db, int idx) {
        if(db.paperDB[idx]==null) {
            System.out.println("There is no paper at that location!");
            return;
        }

    }

    public static void printPaperDetail(PaperDB db, int idx) {
        if(db.paperDB[idx]==null) {
            System.out.println("There is no paper at that location!");
            return;
        }
    }

}
