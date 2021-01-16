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
        add(db, createPaper("Benjamin Alexander Buisman Aguilar", "Coding for Dummies, by Dummies", 2004, 8, 17, 1232));
        add(db, createPaper("Claude Cohen-Tinnoudji", "Why longer titles don't make you sound smart, and other myths to be discovered", 2004, 8, 17, 2));

        while (true) {
            menu(db);
            System.out.println();
        }
    }

    public static void menu(PaperDB db) {
        System.out.println("( 1 ) to create a new paper");
        System.out.println("( 2 ) to delete an entry");
        System.out.println("( 3 ) to display publications");
        System.out.println("( 4 ) to link a reference");
        System.out.println("( 5 ) to search through the papers");
        System.out.println("( 6 ) to get a statistical analysis");
        System.out.println("( 0 ) will exit the program!");

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
            printPaper(db);

        } else if (selection == 4) {
            System.out.println("4 - link a reference:");

        } else if (selection == 5) {
            System.out.println("5 - search through the papers:");

        } else if (selection == 6) {
            System.out.println("6 - get a statistical analysis:");

        } else {
            System.err.println("There was an error in the menu (line 84)");
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
                System.out.println("The date you entered seems to be incorrect, please try again:");
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
            //System.out.println("Added 10 extra spaces");

        }
        db.paperDB[db.free] = p;
        db.free++;
        //System.out.printf("db.free is now %d", db.free);
    }

    private static void deletePaper(PaperDB db) {
        System.out.printf("Which paper do you want to delete? Enter a number between 0 and %d\n", db.free - 1);

        int idx = sc.nextInt();
        while (idx >= db.free) {
            System.out.println("There is no paper at that location.!");
            System.out.printf("Please enter a number between 0 and %d\n", db.free - 1);
            idx = sc.nextInt();
        }
        int i = 0;
        while (db.paperDB[i + 1] != null) {
            if (idx <= i) {
                db.paperDB[i] = db.paperDB[i + 1];
            }
            i++;
        }
        db.paperDB[i] = null;
        db.free--; //one free space is added back
        System.out.println("Paper successfully deleted!\n");
    }

    private static void printPaper(PaperDB db) {
        //enter display option
        System.out.println("Enter 0 to display all papers or 1 to display a specific one:");
        int display = sc.nextInt();

        while (display < 0 || display > 1) {
            System.out.println("Please enter 0 to display all papers, or 1 to display a specific one:");
            display = sc.nextInt();
        }

        //enter type of display (line, short, detail)
        System.out.println("Enter ( 0 ) to display in the 'line' format");
        System.out.println("Enter ( 1 ) to display in the 'short' format");
        if (display == 1)
            System.out.println("Enter ( 2 ) to display in the 'detailed' format");
        int type = sc.nextInt();

        if (display == 0) {
            while (type < 0 || type > 1) {
                System.out.println("Enter ( 0 ) to display in the 'line' format");
                System.out.println("Enter ( 1 ) to display in the 'short' format");
                type = sc.nextInt();
            }
        } else if (display == 1) {
            while (type < 0 || type > 2) {
                System.out.println("Enter ( 0 ) to display in the 'line' format");
                System.out.println("Enter ( 1 ) to display in the 'short' format");
                System.out.println("Enter ( 2 ) to display in the 'detailed' format");
                type = sc.nextInt();
            }
        }

        if (display == 1) {
            System.out.printf("Which paper do you want to print? Enter a number between 0 and %d\n", db.free - 1);
            int idx = sc.nextInt();
            while (idx < 0 || idx > db.free - 1) {
                System.out.printf("Which paper do you want to print? Enter a number between 0 and %d\n", db.free - 1);
                idx = sc.nextInt();
            }
            if (type == 0) {
                System.out.printf("-----------------------------------------------------------------------\n");
                printPaperLine(db, idx);
                System.out.printf("-----------------------------------------------------------------------\n");
            } else if (type == 1) {
                System.out.printf("----------------------------------------------------\n");
                printPaperShort(db, idx);
                System.out.printf("----------------------------------------------------\n");
            } else if (type == 2) {
                System.out.printf("----------------------------------------------------\n");
                printPaperDetail(db, idx);
                System.out.printf("----------------------------------------------------\n");
            }
        }

        if (display == 0) {
            if (type == 0) {
                System.out.printf("-----------------------------------------------------------------------\n");
                System.out.printf("%-25s  %-25s  %-10s  %-5s\n", "Author", "Title", "Pub.Date", "Pages");
                System.out.printf("-------------------------  -------------------------  ----------  -----\n");
                for (int i = 0; i < db.free; i++) {
                    printPaperLine(db, i);
                }
                System.out.printf("-----------------------------------------------------------------------\n");
                System.out.printf("%d element(s)\n", db.free-1);
                System.out.printf("-----------------------------------------------------------------------\n");

            } else if (type == 1) {
                System.out.printf("----------------------------------------------------\n");
                System.out.printf("%-25s  %-25s\n", "Author", "Title");
                System.out.printf("-------------------------  -------------------------\n");
                for (int i = 0; i < db.free; i++) {
                    printPaperShort(db, i);
                }
                System.out.printf("----------------------------------------------------\n");
                System.out.printf("%d element(s)\n", db.free-1);
                System.out.printf("----------------------------------------------------\n");
            }
        }
    }

    public static void printPaperLine(PaperDB db, int idx) {
        System.out.printf("%-25.25s  %-25.25s  %4d-%02d-%02d   %4d\n", db.paperDB[idx].author,
                db.paperDB[idx].title,
                db.paperDB[idx].publicationDate.y,
                db.paperDB[idx].publicationDate.m,
                db.paperDB[idx].publicationDate.d,
                db.paperDB[idx].pages);
    }

    public static void printPaperShort(PaperDB db, int idx) {
        System.out.printf("%-25.25s  %25.25s\n", db.paperDB[idx].author, db.paperDB[idx].title);
    }

    public static void printPaperDetail(PaperDB db, int idx) {
        System.out.printf("%11s: %s\n", "Author", db.paperDB[idx].author);
        System.out.printf("%11s: %-50.50s\n", "Title", db.paperDB[idx].title);
        System.out.printf("%11s: %d-%d-%d\n", "Date",   db.paperDB[idx].publicationDate.y,
                                                        db.paperDB[idx].publicationDate.m,
                                                        db.paperDB[idx].publicationDate.d);
        System.out.printf("%11s: %d\n", "Pages", db.paperDB[idx].pages);
        System.out.printf("%11s: %d\n", "References", 0);
        //Code for references here
    }

}
