// Marco Soekmono
// 11/30/22
// CS &141
// Assignment #3

// This program has added the following features: 
// A user menu (enter date and display corresponding calendar, get today's date and calendar
// display next month's calendar, display previous month's calendar, quit program)
// It can also display custom events from an input file and output a drawing of a month.


// work on linePartition for drawRow

// import libraries
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


// class init
public class MyCalendar3MS {
    public static String fileNameFormatIn(String s) {
        if (!s.contains(".txt")) {
            return "input/" + s + ".txt";
        } else {
            return "input/" + s;
        }
    }

    public static String fileNameFormatOut(String s) {
        if (!s.contains(".txt")) {
            return "output/" + s + ".txt";
        } else {
            return "output/" + s;
        }
    }

    // draw 43 equal signs as horizontal borders
    public static String drawHorizon() {
        String line = "";

        // 43 =
        System.out.print("=");
        line += "=";
        for (int i = 0; i < 7; i++) {
            System.out.print("=================");
            line += "=================";
        }
        System.out.println("");

        return line;

    }

    // method database that outputs at which start of the day each month has
    public static int startDay(int month) {
        switch (month) {
            case 1:
                return 6;
            case 2:
                return 2;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 0;
            case 6:
                return 3;
            case 7:
                return 5;
            case 8:
                return 1;
            case 9:
                return 4;
            case 10:
                return 6;
            case 11:
                return 2;
            case 12:
                return 4;
            default:
                return 0;
        }
    }

    // method database on how many days there are in each month
    public static int totalDays(int month) {
        switch (month) {
            case 1:
                return 31;
            case 2:
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 31;
        }
    }

    // draw each numerically-filled row
    public static String[] drawRow(int row, int month, int day, String[][] events) {
        // get starting and total days
        int startDay = startDay(month);
        int totalDays = totalDays(month);
        int[] daysOfWeek = {1, 1, 1, 1, 1, 1, 1};
        String[] forLP = new String[4];
        
        System.out.print("|");
        forLP[0] = "|";

        // print the number
        for (int i = 0 + row*7; i < 7 + row*7; i++) {
            if (i >= startDay) {
                int j = i-startDay+1;
                if (j < totalDays + 1) {
                    System.out.print(j);
                    forLP[0] += j;
                }
                if (j < 10) {
                    System.out.print("               |");
                    forLP[0] += "               |";
                } else if (j >= totalDays + 1) {
                    System.out.print("                |");
                    forLP[0] += "                |";
                } else {
                    System.out.print("              |");
                    forLP[0] += "              |";
                }
                if (j <= totalDays) {
                    daysOfWeek[i - row*7] = j;
                }
            } else {
                System.out.print("                |");
                forLP[0] += "                |";
            }
        }
        System.out.println();
        System.out.print("|");
        forLP[1] = "|";
        forLP[2] = "";

        // print two breaklines to make a square
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && daysOfWeek[j] == day) {
                    System.out.print("/\\              |");
                    forLP[1] += "/\\              |";
                } else if (i != 0 && events[month-1][daysOfWeek[j]-1] != null) {
                    String specialDay = events[month-1][daysOfWeek[j]-1];
                    if (specialDay.length() > 16) {
                        String seq = "";
                        for (int k = 0; k < specialDay.length()-16; k++) {
                            seq += specialDay.charAt(16+k);
                        }
                        specialDay = specialDay.replaceAll(seq, "");
                    }

                    System.out.print(specialDay);
                    forLP[2] += specialDay;
                    for (int k = 0; k < 16-specialDay.length(); k++) {
                        System.out.print(" ");
                        forLP[2] += " ";
                    }
                    System.out.print("|");
                    forLP[2] += "|";
                }
                else {
                    System.out.print("                |");
                    if (i == 0) {
                        forLP[1] += "                |";
                    } else {
                        forLP[2] += "                |";
                    }
                }
            }
            if (i == 0) {
                System.out.println();
                System.out.print("|");
                forLP[2] += "|";
            }
        }

        // closing square
        System.out.println();
        forLP[3] = drawHorizon();

        return forLP;
    }

    // method database for integer to month name
    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4: 
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "?";
        }
    }

    // draw the entire calendar based on month
    public static String[] drawMonth(int month, int day, String[][] events) {
        String[] linePartition = new String[29];

        // get month name
        String monthName = getMonthName(month);
        linePartition[0] = drawHorizon();

        //  Print month name as the header of the calendar
        System.out.print("|  (" +  month + ") " + monthName + " 2022");
        linePartition[1] = "|  (" +  month + ") " + monthName + " 2022";
        for (int i = 0; i < 120 - (12 + String.valueOf(month).length() + monthName.length()); i++) {
            System.out.print(" ");
            linePartition[1] += (" ");
        }
        System.out.println("|");
        linePartition[1] += "|";

        linePartition[2] = drawHorizon();
        linePartition[3] = "";

        // Days heading
        for (int i = 0; i < 7; i++) {
            System.out.print("|      ");
            linePartition[3] += "|      ";
            switch (i) {
                case 0:
                    System.out.print("Sun");
                    linePartition[3] += "Sun";
                    break;
                case 1:
                    System.out.print("Mon");
                    linePartition[3] += "Mon";
                    break;
                case 2:
                    System.out.print("Tue");
                    linePartition[3] += "Tue";
                    break;
                case 3:
                    System.out.print("Wed");
                    linePartition[3] += "Wed";
                    break;
                case 4:
                    System.out.print("Thu");
                    linePartition[3] += "Thu";
                    break;
                case 5:
                    System.out.print("Fri");
                    linePartition[3] += "Fri";
                    break;
                case 6:
                    System.out.print("Sat");
                    linePartition[3] += "Sat";
                    break;
            }
            System.out.print("       ");
            linePartition[3] += "       ";
        }
        System.out.println("|");
        linePartition[3] += "|";

        linePartition[4] = drawHorizon();

        // draw months
        for (int i = 0; i < 6; i++) {
            String[] rows = drawRow(i, month, day, events);
            for (int j = 0; j < 4; j++) {
                linePartition[5 + 4*i + j] = rows[j]; 
            }
        }

        return linePartition;
    }

    // display date and month
    public static void displayDate(int month, int day) {
        System.out.print("Day: " + day + ", Month: " + month + "\n");
    }


    // Use charAt() for partioning

    // get month from date string
    public static int monthFromDate(String date) {
        // d/mm
        if (date.charAt(1) == '/') {
            if (date.charAt(2) == '0') {
                if (date.length() == 3) {
                    return 12;
                } else {
                    return Character.getNumericValue(date.charAt(3));
                }

            } else {
                if (date.length() == 4) {
                    int month = Integer.parseInt(Character.toString(date.charAt(2)) + Character.toString(date.charAt(3)));
                    if (month % 13 == 0) {
                        return 1;
                    } else {
                        return month % 13;
                    }
                } else {
                    int month = Character.getNumericValue(date.charAt(2));
                    if (month == 0) {
                        return 12;
                    } else {
                        return month;
                    }
                }
            }

        // dd/mm
        } else {
            if (date.charAt(3) == '0') {
                if (date.length() == 4) {
                    return 12;
                } else {
                    return Character.getNumericValue(date.charAt(4));
                }
            } else {
                if (date.length() == 5) {
                    int month = Integer.parseInt(Character.toString(date.charAt(3)) + Character.toString(date.charAt(4)));
                    if (month % 13 == 0) {
                        return 1;
                    } else {
                        return month % 13;
                    }
                } else {
                    int month = Character.getNumericValue(date.charAt(3));
                    if (month == 0) {
                        return 12;
                    } else {
                        return month;
                    }

                }
            }
        }
    }

    // get day from date string
    public static int dayFromDate(String date) {
        // d/mm
        if (date.charAt(1) == '/') {
            return Character.getNumericValue(date.charAt(0));
        // dd/mm
        } else {
            if (date.charAt(0) == '0') {    
                return Character.getNumericValue(date.charAt(1));
            } else {
                return Integer.parseInt(Character.toString(date.charAt(0)) + Character.toString(date.charAt(1)));
            }
        }
    }

    // menu print
    public static void prompt() {
        System.out.println("\nPlease type a command");
        System.out.println("\t\"e\" to enter a date and display its corresponding calendar");
        System.out.println("\t\"t\" to display today's date");
        System.out.println("\t\"n\" to display the next month");
        System.out.println("\t\"p\" to display the previous month");
        System.out.println("\t\"q\" to quit program");
        System.out.println("\t\"ev\" to create an event");
        System.out.println("\t\"fp\" to print a month into a txt file");
    }


    // output in dd/mm format of an arbitrary month increment (+1 month or -1 month)
    public static String adjacentMonth(String date, int increment) {

        String monthValue = "";
        String[] partition = date.split("/", 2);

        // array partitioning
        if (partition[1].charAt(0) == '0') {
            monthValue = Character.toString(partition[1].charAt(1));
        } else if (partition[1].length() == 2 && partition[1].charAt(0) == '1') {
            monthValue = partition[1];
        } else {
            monthValue = partition[1];
        }

        String newMonth = String.valueOf((Integer.parseInt(monthValue) + increment));
        String changedDate = partition[0] + "/" + newMonth;

        return changedDate;
    }

    // make new event
    public static String[] newEvent(Scanner scan) {
        System.out.println("What event would you like to make? (DD/MM event_title)");
        String event = scan.nextLine();
        String date = event.split(" ")[0];
        int day = dayFromDate(date);
        int month = monthFromDate(date);


        String[] ev = {Integer.toString(day), Integer.toString(month), event.split(" ")[1]};

        return ev;
    }

    // calendarEvents into events array
    public static String[][] fileToEvents(String[][] events) {
        try {
            File f = new File("input/calendarEvents.txt");
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] partition = line.split(" ");
                int day = dayFromDate(partition[0]);
                int month = monthFromDate(partition[0]);
                events[month-1][day-1] = partition[1];
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        return events;
    }

    // create file by drawing
    public static void createFile(Scanner input, String[][] events) {
        System.out.print("Which month would you like to print out? ");
        int month = input.nextInt();
        input.nextLine();
        System.out.print("What would you like to name the file? ");
        String name = input.nextLine();

        try {
            File f = new File(fileNameFormatOut(name));
            if (f.createNewFile()) {
                try {
                    FileWriter writer = new FileWriter(fileNameFormatOut(name));
                    String[] contents = drawMonth(month, 3, events);
                    for (int i = 0; i < contents.length; i++) {
                        writer.write(contents[i] + "\n");
                    }
                    writer.close();
                    System.out.println("File " + f.getName() + " created!");
                } catch (IOException e) {
                    System.out.println("An error occured");
                    e.printStackTrace();
                }
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    // main
    public static void main(String[] args) {

        // declare variables
        String in = "";
        String date = "";
        int i = 0;
        int month = 0;
        int day = 0;
        boolean invalidFlag = false;
        Scanner input = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM");
        String dateNow = adjacentMonth(currentDate.format(formatDate), -1);
        String[][] events = new String[12][];
        for (int k = 0; k < 12; k++) {
            events[k] = new String[totalDays(k+1)];
        }
        events = fileToEvents(events);
        
        // welcome message
        System.out.println("\nWelcome to Calendar!");

        // loop session
        while (!in.equals("q")) {

            invalidFlag = false;

            prompt();

            in = input.next();
            input.nextLine();

            switch (in) {

                // enter date
                case "e":
                    // date query
                    System.out.println("What date do you want to see? (dd/mm)");
                    date = input.next();
                    input.nextLine();
                    System.out.println();
                    break;

                // todays date
                case "t":
                    date = dateNow;
                    System.out.println();
                    break;

                // next month
                case "n":
                    // iteration += 1;
                    if (i == 0) {
                        date = adjacentMonth(dateNow, 1);
                    } else {
                        date = adjacentMonth(date, 1);
                    }
                    break;

                // previous month
                case "p":
                    // iteration -= 1;
                    if (i == 0) {
                        date = adjacentMonth(dateNow, -1);
                    } else {
                        date = adjacentMonth(date, -1);
                    }
                    break;

                case "ev":
                    String[] ev = newEvent(input);
                    events[Integer.parseInt(ev[1])-1][Integer.parseInt(ev[0])-1] = ev[2];
                    System.out.println("\nEvent " + ev[2] + " created!"); 
                    invalidFlag = true;
                    break;

                case "fp":
                    createFile(input, events);
                    invalidFlag = true;
                    break;

                // quit program
                case "q":
                    System.out.println("\nThank you for using Calendar");
                    invalidFlag = true;
                    break;

                // unrecognized input
                default:
                    System.out.println("\nInvalid input!");
                    invalidFlag = true;
                    break;

            }

            // if input is invalid or user quit
            if (!invalidFlag) {

                // get int from partitioning methods
                if (date.length() <= 5 && date.contains("/")) {
                    month = monthFromDate(date);
                    day = dayFromDate(date);

                    date = day + "/" + month;

                    // check if days is valid in a month's max days
                    if (day > totalDays(month)) {
                        System.out.println("Invalid amount of days");
                        date = "1/1";
                        continue;
                    }

                // is input in the dd/mm format?
                } else {
                    System.out.println("Incorrect format");
                    date = "1/1";
                    continue;
                }
                
                // draw calendar
                drawMonth(month, day, events);
                displayDate(month, day);
                
            }

            i++;
            
        }

        input.close();

    }
}
