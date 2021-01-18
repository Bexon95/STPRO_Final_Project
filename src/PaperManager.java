import java.util.Scanner;

class Paper {
    String author;
    String title;
    Date publicationDate = new Date();
    int pages;
    RefNode reference = null;
}

class Date {
    int y, m, d;
}

class PaperDB {
    Paper[] paperDB = new Paper[10]; //instead of copying to new array (+1) every time, I do it in batches of ten
    int free = 0;
}

class RefNode {
    Paper paper;
    RefNode left, right;
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

    /* ----------------------- */
    /* ------- M E N U ------- */
    /* ----------------------- */
    public static void menu(PaperDB db) {
        System.out.println("\n#---------- M E N U ----------#");
        System.out.println("( 1 ) to create a new paper");
        System.out.println("( 2 ) to delete an entry");
        System.out.println("( 3 ) to display publications");
        System.out.println("( 4 ) to link a reference");
        System.out.println("( 5 ) to search through the papers");
        System.out.println("( 6 ) to get a statistical analysis");
        System.out.println("( 7 ) to sort the paper database");
        System.out.println("( 8 ) to show the references of a paper");
        System.out.println("( 0 ) will exit the program!");

        int selection = sc.nextInt();
        while (selection < 0 || selection > 8) {
            System.out.println("Selection invalid. Please choose a number between 0-8.");
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
            addReference(db);

        } else if (selection == 5) {
            System.out.println("5 - search through the papers:");
            searchPapers(db);

        } else if (selection == 6) {
            System.out.println("6 - get a statistical analysis:");
            analyze(db);

        } else if (selection == 7) {
            System.out.println("7 - sort the papers:");
            sortMenu(db);

        } else if (selection == 8) {
            System.out.println("8 - list the references of a paper:");
            listReferences(db);

        } else {
            System.err.println("There was an error in the menu.");
            System.exit(0);
        }

    }

    /* ----------------------------------- */
    /* ------- S C A N   P A P E R ------- */
    /* ----------------------------------- */
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

    /* ----------------------------------- */
    /* ------- C H E C K   D A T E ------- */
    /* ----------------------------------- */
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

    /* --------------------------------------- */
    /* ------- C R E A T E   P A P E R ------- */
    /* --------------------------------------- */
    public static Paper createPaper(String author, String title, int y, int m, int d, int pages) {
        Paper p = new Paper();
        p.author = author;
        p.title = title;
        p.publicationDate.y = y;
        p.publicationDate.m = m;
        p.publicationDate.d = d;
        p.pages = pages;

        System.out.println("Paper successfully created!");

        return p;
    }

    /* --------------------------------- */
    /* ------- A D D   P A P E R ------- */
    /* --------------------------------- */
    public static void add(PaperDB db, Paper p) {
        if (db.free == db.paperDB.length) {
            Paper[] temp = new Paper[db.paperDB.length + 10];
            for (int i = 0; i < db.paperDB.length; i++) {
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

    /* --------------------------------------- */
    /* ------- D E L E T E   P A P E R ------- */
    /* --------------------------------------- */
    public static void deletePaper(PaperDB db) {
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

    /* ------------------------------------- */
    /* ------- P R I N T   P A P E R ------- */
    /* ------------------------------------- */
    public static void printPaper(PaperDB db) {
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
                System.out.printf("%d element(s)\n", db.free - 1);
                System.out.printf("-----------------------------------------------------------------------\n");

            } else if (type == 1) {
                System.out.printf("----------------------------------------------------\n");
                System.out.printf("%-25s  %-25s\n", "Author", "Title");
                System.out.printf("-------------------------  -------------------------\n");
                for (int i = 0; i < db.free; i++) {
                    printPaperShort(db, i);
                }
                System.out.printf("----------------------------------------------------\n");
                System.out.printf("%d element(s)\n", db.free - 1);
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
        System.out.printf("%11s: %d-%d-%d\n", "Date", db.paperDB[idx].publicationDate.y,
                db.paperDB[idx].publicationDate.m,
                db.paperDB[idx].publicationDate.d);
        System.out.printf("%11s: %d\n", "Pages", db.paperDB[idx].pages);
        System.out.printf("%11s: %d\n", "References", 0);
        //Code for references here
    }

    /* ----------------------------------------- */
    /* ------- A D D   R E F E R E N C E ------- */
    /* ----------------------------------------- */
    public static void addReference(PaperDB db) {
        /*
        Notizen an mich, sonst blicke ich da niemals durch...
        Außerdem habe ich mir das Tutorium "Trees" (und besonders BSTPhoneBook) zur Hilfe und zum Verständnis dazugezogen

            Jedes neu erstelle Paper enthält eine (leere) RefNode reference.
            Eine RefNode besteht aus einem paper, und einer RefNode left/right (um sie zu sortieren)

            Wir wählen zuerst ein Paper zum bearbeiten aus unserer db aus. (db.paperDB[mainPaper])
            Dann lassen wir die reference auf das paper db.paperDB[refPaper] zeigen

            Dann füge ich diese neue RefNode zu dem mainPaper Reference hinzu (db.paperDB.[mainPaper].reference)
         */

        System.out.println("select paper, then reference:");

        System.out.printf("----------------------------------------------------------\n");
        System.out.printf("%-5s %-25s  %-25s\n", "Nr", "Author", "Title");
        System.out.printf("-------------------------------  -------------------------\n");
        for (int i = 0; i < db.free; i++) {
            System.out.printf("( %d ) ", i);
            printPaperShort(db, i);
        }
        int mainPaper = sc.nextInt();
        int refPaper = sc.nextInt();

        boolean wrongChoice = true;
        while (wrongChoice) {
            //does paper exist
            if (db.paperDB[mainPaper] == null || db.paperDB[refPaper] == null) {
                System.out.println("Paper doesn't exist! Please choose a paper, then a reference:");
                mainPaper = sc.nextInt();
                refPaper = sc.nextInt();
            }

            //does paper reference itself
            else if (mainPaper == refPaper) {
                System.out.println("A paper can't reference itself! Please choose a paper, then a reference:");
                mainPaper = sc.nextInt();
                refPaper = sc.nextInt();
            } else {
                wrongChoice = false;
            }
        }

        RefNode nN = new RefNode();
        db.paperDB[mainPaper].reference = insert(db.paperDB[mainPaper].reference, db.paperDB[refPaper]);
    }

    public static RefNode insert(RefNode bst, Paper paper) {
        if (bst == null) {
            RefNode nN = new RefNode();
            nN.paper = paper;
            return nN;
        }

        if (stringComparator(bst.paper.author, paper.author) > 0) //passes paper at array[j/i]
            bst.left = insert(bst.left, paper);
        else if (stringComparator(bst.paper.author, paper.author) < 0)
            bst.right = insert(bst.right, paper);
        else
            System.out.println("Paper is already a reference!");

        return bst;
    }

    /* --------------------------------------- */
    /* ------- S E A R C H   P A P E R ------- */
    /* --------------------------------------- */
    public static void searchPapers(PaperDB db) {
        int choice = 2;
        while (choice < 0 || choice > 1) {
            System.out.println("Enter ( 0 ) to filter by author");
            System.out.println("Enter ( 1 ) to filter by title");
            choice = sc.nextInt();
        }
        PaperDB searchMatch = new PaperDB();
        searchString(db, searchMatch, choice);

        /*
        Ursprünglich war mein Plan folgender:
        PaperDB searchMatch = searchString(db, choice);
        searchString wird aufgerufen, ich erstelle temp (wie auch jetzt der Fall)
        Und dann return temp. Allerdings kommt der Fehler: Required PaperDB, provided Paper[].
        Warum funktioniert dann allerdings die jetzige Funktion mit:
        searchMatch = temp;
        return searchMatch; ??
         */

        if (searchMatch.paperDB.length == 0) {
            System.out.println("There were no matches!");
            return;
        }

        System.out.printf("-----------------------------------------------------------------------\n");
        System.out.printf("%-25s  %-25s  %-10s  %-5s\n", "Author", "Title", "Pub.Date", "Pages");
        System.out.printf("-------------------------  -------------------------  ----------  -----\n");
        for (int i = 0; i < searchMatch.paperDB.length; i++) {
            printPaperLine(searchMatch, i);
        }
        System.out.printf("-----------------------------------------------------------------------\n");
        System.out.printf("%d element(s)\n", searchMatch.paperDB.length);
        System.out.printf("-----------------------------------------------------------------------\n");

    }

    /* ----------------------------------------- */
    /* ------- S E A R C H   S T R I N G ------- */
    /* ----------------------------------------- */
    public static PaperDB searchString(PaperDB db, PaperDB searchMatch, int choice) {
        int size = 0;
        System.out.println("Enter the search word. Careful, it's case-sensitive!");
        sc.nextLine(); //necessary to catch enter
        String searchTerm = sc.nextLine();

        if (choice == 0) {
            //yes, it's inefficient to run through the loop twice, but better than to copy everything to a new array every time
            for (int i = 0; i < db.free; i++) {
                if (db.paperDB[i].author.contains(searchTerm)) {
                    size++;
                }
            }
            Paper[] temp = new Paper[size];

            //in case there are no matches
            if (size == 0) {
                searchMatch.paperDB = temp;
                return searchMatch;
            }
            for (int i = 0, j = 0; i < db.free; i++) {
                if (db.paperDB[i].author.contains(searchTerm)) {
                    temp[j] = db.paperDB[i];
                    j++;
                }
            }
            searchMatch.paperDB = temp;
        } else {
            for (int i = 0; i < db.free; i++) {
                if (db.paperDB[i].title.contains(searchTerm)) {
                    size++;
                }
            }
            Paper[] temp = new Paper[size];

            //in case there are no matches
            if (size == 0) {
                searchMatch.paperDB = temp;
                return searchMatch;
            }
            for (int i = 0, j = 0; i < db.free; i++) {
                if (db.paperDB[i].title.contains(searchTerm)) {
                    temp[j] = db.paperDB[i];
                    j++;
                }
            }
            ;
            //wieso geht return temp nicht?? Error: Provided Paper[], required PaperDB.
            searchMatch.paperDB = temp;
        }
        return searchMatch;
    }

    /* ----------------------------- */
    /* ------- A N A L Y Z E ------- */
    /* ----------------------------- */
    public static void analyze(PaperDB db) {
        int numberPapers;
        int avgPages;
        //int avgReferences;
        int mostReferences;
        String mostRefPaper;
    }

    /* --------------------------------- */
    /* ------- M E R G E S O R T ------- */
    /* --------------------------------- */
    public static void sortMenu(PaperDB db) {
        System.out.println("Enter ( 0 ) to sort by author");
        System.out.println("Enter ( 1 ) to sort by title");
        System.out.println("Enter ( 2 ) to sort by publication Date");
        int choice = sc.nextInt();
        while (choice < 0 || choice > 2) {
            System.out.println("Enter ( 0 ) to sort by author");
            System.out.println("Enter ( 1 ) to sort by title");
            System.out.println("Enter ( 2 ) to sort by publication Date");
            choice = sc.nextInt();
        }
        if (choice == 0) {
            mergeSort(db, 0);
        } else if (choice == 1) {
            mergeSort(db, 1);
        } else if (choice == 2) {
            mergeSortDate(db, 2);
        }
    }

    public static void mergeSort(PaperDB db, int choice) {
        mSort(db, 0, db.free - 1, new PaperDB(), choice); //do not use db.paperDB.length -> Array is longer because some slots are null
    }

    public static void mergeSortDate(PaperDB db, int choice) {
        mSort(db, 0, db.free - 1, new PaperDB(), choice); //do not use db.paperDB.length -> Array is longer because some slots are empty
    }

    private static void mSort(PaperDB db, int left, int right, PaperDB aux, int choice) {
        int middle = (right + left) / 2;
        if (right <= left)
            return;
        mSort(db, left, middle, aux, choice);
        mSort(db, middle + 1, right, aux, choice);

        if (choice == 0) {
            mergeAuthor(db, left, middle, right, aux);
        } else if (choice == 1) {
            mergeTitle(db, left, middle, right, aux);
        } else if (choice == 2) {
            mergeDate(db, left, middle, right, aux);
        }
    }

    /* --------------------------------------- */
    /* ------- M E R G E   A U T H O R ------- */
    /* --------------------------------------- */
    public static void mergeAuthor(PaperDB db, int left, int middle, int right, PaperDB aux) {
        int i, j, k;
        for (i = middle + 1; i > left; i--)
            aux.paperDB[i - 1] = db.paperDB[i - 1];
        for (j = middle; j < right; j++)
            aux.paperDB[right + middle - j] = db.paperDB[j + 1];

        for (k = left; k <= right; k++)
            if (stringComparator(aux.paperDB[j].author, aux.paperDB[i].author) < 0) //passes paper at array[j/i]
                db.paperDB[k] = aux.paperDB[j--];
            else
                db.paperDB[k] = aux.paperDB[i++];
    }

    /* ------------------------------------- */
    /* ------- M E R G E   T I T L E ------- */
    /* ------------------------------------- */
    public static void mergeTitle(PaperDB db, int left, int middle, int right, PaperDB aux) {
        int i, j, k;
        for (i = middle + 1; i > left; i--)
            aux.paperDB[i - 1] = db.paperDB[i - 1];
        for (j = middle; j < right; j++)
            aux.paperDB[right + middle - j] = db.paperDB[j + 1];

        for (k = left; k <= right; k++)
            if (stringComparator(aux.paperDB[j].title, aux.paperDB[i].title) < 0) //passes paper at array[j/i]
                db.paperDB[k] = aux.paperDB[j--];
            else
                db.paperDB[k] = aux.paperDB[i++];
    }

    /* ----------------------------------- */
    /* ------- M E R G E   D A T E ------- */
    /* ----------------------------------- */
    public static void mergeDate(PaperDB db, int left, int middle, int right, PaperDB aux) {
        int i, j, k;
        for (i = middle + 1; i > left; i--)
            aux.paperDB[i - 1] = db.paperDB[i - 1];
        for (j = middle; j < right; j++)
            aux.paperDB[right + middle - j] = db.paperDB[j + 1];

        for (k = left; k <= right; k++)
            if (dateConverter(aux.paperDB[j]) < dateConverter(aux.paperDB[i])) //passes paper at array[j/i]
                db.paperDB[k] = aux.paperDB[j--];
            else
                db.paperDB[k] = aux.paperDB[i++];
    }

    //got the idea from _20_BSTPhoneBook
    public static int stringComparator(String db, String aux) {
        return db.compareToIgnoreCase(aux);
    }

    //convert dates from yyyy-mm-dd to yyyymmdd
    public static int dateConverter(Paper p) {
        return p.publicationDate.y * 10000 + p.publicationDate.m * 100 + p.publicationDate.d;
    }

    /* --------------------------------------------- */
    /* ------- L I S T   R E F E R E N C E S ------- */
    /* --------------------------------------------- */
    public static void listReferences(PaperDB db){
        System.out.println("From which paper do you want to see the references?");
        System.out.printf("----------------------------------------------------------\n");
        System.out.printf("%-5s %-25s  %-25s\n", "Nr", "Author", "Title");
        System.out.printf("-------------------------------  -------------------------\n");
        for (int i = 0; i < db.free; i++) {
            System.out.printf("( %d ) ", i);
            printPaperShort(db, i);
        }
        int choice = sc.nextInt();

        boolean wrongChoice = true;
        while (wrongChoice) {
            //does paper exist
            if (db.paperDB[choice] == null) {
                System.out.println("Paper doesn't exist! Please choose a paper, then a reference:");
                choice = sc.nextInt();
            } else {
                wrongChoice = false;
            }
        }

        while (db.paperDB[choice] == null) {
            System.out.println("Paper doesn't exist! Please choose a paper, then a reference:");
            choice = sc.nextInt();
        }
        printReferences(db.paperDB[choice].reference);
        if(db.paperDB[choice].reference == null){
            System.out.println("The paper has no references.");
        }
    }

    public static void printReferences(RefNode reference) {
        if (reference == null) {
            return;
        }

        printReferences(reference.left);
        System.out.printf("%-25.25s  %25.25s\n", reference.paper.author, reference.paper.title);
        printReferences(reference.right);
    }
}
