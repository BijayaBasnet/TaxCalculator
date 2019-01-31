import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author bijayarajbasnet1
 */

// this program calculate the tax of the employee, searches the tax and displays the tax record if available
public class Assignment1 {

    static Double taxAmount = 0.00;

    static String employeeID;
    static int taxableIncome ;
    static String taxableincome;
    //intiaising the varialbes
    static Scanner input;
    private static DecimalFormat twoDForm = new DecimalFormat("0.00");
    // defining new decimal format two allow two digits after decimal


    //this method will allow user to allow 1,2,3 and if anything else error message will display
    public static void DisplayOption() throws FileNotFoundException {
        taxAmount = 0.00;
        System.out.println("Please Select one from following: \n 1. Calculate Tax \n 2: Search Tax \n 3: Exit");
        System.out.println("********************************************************************");
        Scanner input = new Scanner(System.in);

        switch (input.next()) {
            case "1":
                CalculateTax(); // call method Calculation()
                break;
            case "2":
                SearchTax();//call method SearchTax()
                DisplayOption();
                break;
            case "3":
                CloseProgram();//Call method CloseProgram()
                break;
            default:
                System.out.println("Please select one from the list. Wrong selection");
                System.out.println("------------------------------------------------------");
                DisplayOption();
                break;

        }


    }

    //this method calculate the tax based on the income and tax rate given in tasrate.txt file
//    private void CalculateTax() throws FileNotFoundException {
//        // reads for taxrates.txt and reads each line at at time
//        try (Stream<String> steam = Files.lines(Paths.get("/Users/bijayarajbasnet1/IdeaProjects/Assignment2/Assignment1/taxrates.txt"))) {
//            steam.forEach(i -> {
//
//            });
//        } catch (IOException io) {
//            // gives error message if file not found
//            System.out.println("File not Found !!.Please supply the tax rate file. ");
//            System.out.println("----------------------------------------");
//            CloseProgram();
//
//        }
//
//        System.out.println("Enter 4 digit Employee ID");
//        employeeID = input.next();
//        if (!employeeID.matches("\\d+") || employeeID.length() != 4) {
//            //gives erorr message if employee id isnt a 4 digit integer
//
//            System.out.println("Wrong Employee ID. TRY AGAIN..!!!");
//            System.out.println("----------------------------------------");
//            DisplayOption();
//
//        } else {
//
//            System.out.println("Please Enter the Taxable Income of the Employee");
//            String taxableIncomeInput = input.next();
//            if (taxableIncomeInput.matches("^([0-9]+\\.?[0-9]*|[0-9]*\\.[0-9]+)$")) {
//                //checks if the input string is in the integer or an decimal value
//                taxableIncome = Double.parseDouble(taxableIncomeInput);
//
//                if (taxableIncome <= 18200) {
//                } else if (taxableIncome >= 18201 && taxableIncome <= 37000) {
//                    taxAmount = taxAmount + (taxableIncome - 18200) * 0.19;
//                } else if (taxableIncome >= 37001 && taxableIncome <= 87000) {
//                    taxAmount = taxAmount + (3572 + (taxableIncome - 37000) * 0.325);
//                } else if (taxableIncome >= 87001 && taxableIncome <= 180000) {
//                    taxAmount = taxAmount + (19822 + (taxableIncome - 87000) * 0.37);
//                } else if (taxableIncome >= 180001) {
//                    taxAmount = 54232 + (taxableIncome - 180000) * 0.45;
//
//                }
//                System.out.println("The tax for Employee Id" + " " + employeeID + " is:" + twoDForm.format(taxAmount));
//
//                WriteToFile();
//            } else {
//                System.out.println("Invalid Annual Income ");
//                System.out.println("--------------------------------------------");
//
//                DisplayOption();
//            }
//
//
//        }
//    }

    public static void CalculateTax() throws FileNotFoundException {
        byte[] data = null;
        String str = null;
        try {
            File file = new File("/Users/bijayarajbasnet1/IdeaProjects/Assignment2/Assignment1/taxrates.txt");
            try (FileInputStream fis = new FileInputStream(file)) {
                data = new byte[(int) file.length()]; //read all the contetn of file and store it in one dimensional array
                fis.read(data);
            }
        } catch (FileNotFoundException f) {
            System.out.println("Taxrates.txt file not found. Please upload the file!!");
        } catch (IOException e) {
            System.out.println("Error!!");
        }
        System.out.println("Enter 4 digit Employee ID");
        employeeID = input.next();
        if (!employeeID.matches("\\d+") || employeeID.length() != 4) {
            //gives erorr message if employee id isnt a 4 digit integer

            System.out.println("Wrong Employee ID. TRY AGAIN..!!!");
            System.out.println("----------------------------------------");
            new Assignment1().DisplayOption();

        } else {
            System.out.println("Enter the Taxable Amount");
            try{taxableincome = input.next();

            }
            catch (Exception e){
                System.out.println("wrong");
            }
            if (!taxableincome.matches("\\d+")) {
                System.out.println("Wrong Taxable Income. Please Try Again");
                DisplayOption();

            } else {
                try {
                    //Remove any line breaks, tabs that are present in file
                    str = new String(data, "UTF-8");
                    str = str.replace("\n", " ").replace("\r", "");
                    str = str.replace("\t", " ").replace("\r", "");
                    str = str.replace("   ", " ").replace("\r", "");

                } catch (UnsupportedEncodingException e) {
                    System.out.println("Cannot encode data !!!!");
                }

                //Split the array word by word so we can take only data what we need.
                String[] array = str.split(" ");

                //Store tax range in 2 dimensional array so that it will be easy to calculate tax
                int[][] incomeRange = new int[5][2];
                double[] taxRate = new double[5];
                int[] fixedTax = new int[3];
                incomeRange[0][0] = parseStringToInt(array[5]);
                incomeRange[0][1] = parseStringToInt(array[7].replaceAll("[$,]", ""));
                incomeRange[1][0] = parseStringToInt(array[10].replaceAll("[$,]", ""));
                incomeRange[1][1] = parseStringToInt(array[12].replaceAll("[$,]", ""));
                incomeRange[2][0] = parseStringToInt(array[20].replaceAll("[$,]", ""));
                incomeRange[2][1] = parseStringToInt(array[22].replaceAll("[$,]", ""));
                incomeRange[3][0] = parseStringToInt(array[32].replaceAll("[$,]", ""));
                incomeRange[3][1] = parseStringToInt(array[34].replaceAll("[$,]", ""));
                incomeRange[4][0] = parseStringToInt(array[44].replaceAll("[$,]", ""));

                //get tax rate and put it in percentage.
                taxRate[0] = parseStringToDouble(array[9].replaceAll("[c]", ""));
                taxRate[1] = parseStringToDouble(array[14].replaceAll("[c]", ""));
                taxRate[2] = parseStringToDouble(array[26].replaceAll("[c]", ""));
                taxRate[3] = parseStringToDouble(array[38].replaceAll("[c]", ""));
                taxRate[4] = parseStringToDouble(array[50].replaceAll("[c]", ""));

                //If base tax price is given then store in an array

                fixedTax[0] = parseStringToInt(array[24].replaceAll("[$,]", ""));
                fixedTax[1] = parseStringToInt(array[36].replaceAll("[$,]", ""));
                fixedTax[2] = parseStringToInt(array[48].replaceAll("[$,]", ""));
                int taxableIncome=Integer.parseInt(taxableincome);
                if (incomeRange[0][1] >= taxableIncome) {
                    taxAmount = taxAmount + taxRate[0];
                    System.out.println(taxAmount);

                } else if (taxableIncome >= incomeRange[1][0] && taxableIncome <= incomeRange[1][1]) {
                    taxAmount = ((taxableIncome - incomeRange[0][1]) * taxRate[1]);
                    System.out.println(taxAmount);
                    System.out.println(incomeRange[0][1]);

                } else if (taxableIncome >= incomeRange[2][0] && taxableIncome <= incomeRange[2][1]) {
                    taxAmount = (taxableIncome - incomeRange[1][1]) * taxRate[2] + (double) fixedTax[0];
                    System.out.println(taxAmount);

                } else if (taxableIncome >= incomeRange[3][0] && taxableIncome <= incomeRange[3][1]) {
                    taxAmount = (taxableIncome - incomeRange[2][1]) * taxRate[3] + (double) fixedTax[1];
                    System.out.println(taxAmount);

                } else if (taxableIncome >= incomeRange[4][0]) {
                    taxAmount = (taxableIncome - incomeRange[3][1]) * taxRate[4] + (double) fixedTax[2];
                    System.out.println(taxAmount);
                }
                WriteToFile();
            }
        }
    }


    //This method will return double value from string
    public static double parseStringToDouble(String string) {
        return Double.parseDouble(string)/ 100;
    }

    //This method will return integer value from string
    public static int parseStringToInt(String string) {
        return Integer.parseInt(string);
    }

    //This method will truncate decimal values up to 2 decimal points ony.
    public static double limitToTwoDecimalPlacesOnly(double number) {
        return Math.rint(number * 100) / 100;
    }
    // this method writes the tax record that has been calculated to taxreport.txt file

    private static void WriteToFile() throws FileNotFoundException {

        String EmployeeTaxInfo = "";
        EmployeeTaxInfo += employeeID + "\t\t\t" + twoDForm.format(taxableIncome) + "\t\t\t\t" + twoDForm.format(taxAmount) + "\n";
        // using defined decimal format to write in the file
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/bijayarajbasnet1/IdeaProjects/Assignment2/Assignment1/taxreport.txt", true)));
            out.print(EmployeeTaxInfo);
            System.out.println("Tax Record added successfully");
            System.out.println("--------------------------------------------");

        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Error!! try Again!");

        } finally {
            if (out != null) {
                out.close();
            }
        }


        DisplayOption();
    }

    // this methid searches for tax record on the taxreport.txt file based on employee id
    private static void SearchTax() throws FileNotFoundException {
        System.out.println("Enter 4 digit Employee ID To be Searched");

        String empID = input.next();
        if (!empID.matches("[0-9]+") || empID.length() != 4) {
            //checks if the emplyee id isn't number and the length isn't 4

            System.out.println("Wrong Employee ID. TRY AGAIN..!!!");
            System.out.println("----------------------------------------");
            DisplayOption();

        } else {
            try (Stream<String> stream = Files.lines(Paths.get("/Users/bijayarajbasnet1/IdeaProjects/Assignment2/Assignment1/taxreport.txt")).skip(1)) {
                //streaming lines from the taxreport.txt file one by one and skipping the first line of the file
                System.out.println("Employee ID" + "     " + "Taxable Income" + "         " + "Tax" + "\n");
                List<String> valueStore = new ArrayList<>();
                stream.forEach(i -> {
                            valueStore.add(i);
                        }
                );
                Collections.reverse(valueStore);
                //reversing the records in the file
                int counter = 0;
                for (String valu : valueStore) {
                    String[] val = valu.split("\\s+");
                    counter++;
                    if (Integer.parseInt(val[0]) == Integer.parseInt(empID)) {
                        System.out.println(valu);
                        System.out.println("----------------------------------------------------------------------------------");
                        return;
                    } else {
                        if (counter == valueStore.size()) {
                            System.out.println("No records found !!\n");
                            System.out.println("----------------------------------------------------------------------------------");

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // this method terminates the program and display the message
    private static void CloseProgram() {
        System.out.println("You have successfully exited.See you soon.");
    }

    // this is the main method of the program.
    public static void main(String[] args) throws FileNotFoundException {
        input = new Scanner(System.in);
        Assignment1 writefile = new Assignment1();
        writefile.DisplayOption();
    }

}
