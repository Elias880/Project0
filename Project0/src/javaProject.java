import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

class Taxes{

  static int size = 0;
  public static void processFile(File input) throws IOException{
    
    int count = 0;
    Scanner file2 = new Scanner(input);
    
    while(file2.hasNextLine()){
      file2.nextLine();
      count++;
    }
    file2.close();

    Scanner data = new Scanner(input);
    size = count;
    int[][] information = new int[size][5];
    
    for(int i = 0; i < size; i++){
      information[i][0] = data.nextInt();
      information[i][1] = data.nextInt();
      information[i][2] = data.nextInt();
      information[i][3] = data.nextInt();
      information[i][4] = data.nextInt();
    }
    
    
    for(int j = 0; j < size ; j++){
      printToFile(j+1, calcAGI(information[j][0], information[j][1], information[j][2]), getDeduction(information[j][3]), calcTaxable(calcAGI(information[j][0], information[j][1], information[j][2]), getDeduction(information[j][3])), calcTax(information[j][3], calcTaxable(calcAGI(information[j][0], information[j][1], information[j][2]), getDeduction(information[j][3]))), calcTaxDue(calcTax(information[j][3], calcTaxable(calcAGI(information[j][0], information[j][1], information[j][2]), getDeduction(information[j][3]))), information[j][4]));
    }
    
    data.close();
   
    
  }
   
  public static int calcAGI(int wages, int interest, int unemployment){
    //sum of wages 1 interest 2 unemployment 3
    int AGI = 0;
    int w = Math.abs(wages);
    int in = Math.abs(interest);
    int u = Math.abs(unemployment);
    AGI = w + in + u;
    return AGI;
  }
   
  public static int getDeduction(int status){
    if(status == 0){
      return 6000;
    }else if(status == 1){
      return 12000;
    }else if(status == 2){
      return 24000;
    }else{
      return 6000;
    }
  }
   
  public static int calcTaxable(int AGI, int deduction){
    int taxable = 0;
    taxable = AGI - deduction;
    if(taxable >= 0){
      return taxable;
    }else{
      taxable = 0;
      return taxable;
    }
    
  }
   
  public static int calcTax(int status, int taxable){
    double tax = 0;
    
    if(status != 2){
      if(taxable >= 0 && taxable <= 10000){
        tax = (double)taxable * 0.10;
      }else if(taxable >= 10001 && taxable <= 40000){
        tax = 1000.00 + (double)(taxable-10000) * 0.12;
      }else if(taxable >= 40001 && taxable <= 85000){
        tax = 4600.00 + (double)(taxable-40000) * 0.22;
      }else if(taxable > 85000){
        tax = 14500.00 + (double)(taxable-85000) * 0.24;
      }
    }else{
      if(taxable >= 0 && taxable <= 20000){
        tax = (double)taxable * 0.10;
      }else if(taxable >= 20001 && taxable <= 80000){
        tax = 2000.00 + (double)(taxable-20000) * 0.12;
      }else if(taxable > 80000){
        tax = 9200.00 + (double)(taxable-80000) * 0.22;
      }
    }
    
    int taxF = (int)Math.round(tax);
    return taxF;
  }
   
  public static int calcTaxDue(int tax, int withheld){
    //tax - withheld
    int due = 0;
    if(withheld < 0){
      withheld = Math.abs(withheld);
    }
    due = tax - withheld;
    return due;
  }
  
  public static void printToFile(int house, int AGI, int deduction, int taxable, int tax, int due) throws IOException{
    
    if(house == 1){
      PrintWriter printer = new PrintWriter("TaxOutput.txt");
      
      printer.printf("Household %d:\n", house);
      printer.printf("AGI: $%,d\n", AGI);
      printer.printf("Deduction: $%,d\n", deduction);
      printer.printf("Taxable income: $%,d\n", taxable);
      printer.printf("Federal tax: $%,d\n", tax);
      printer.printf("Tax due: $%,d", due);
      if(house != size){
        printer.println("\n");
      }
      printer.close();
    }else{
      FileOutputStream fileStream = new FileOutputStream("TaxOutput.txt",true);
      PrintWriter printer = new PrintWriter(fileStream);

      printer.printf("Household %d:\n", house);
      printer.printf("AGI: $%,d\n", AGI);
      printer.printf("Deduction: $%,d\n", deduction);
      printer.printf("Taxable income: $%,d\n", taxable);
      printer.printf("Federal tax: $%,d\n", tax);
      printer.printf("Tax due: $%,d", due);
      if(house != size){
        printer.println("\n");
      }
      printer.close();
    }

  }
  
  public static void main(String[] args) throws IOException{
    Scanner scan = new Scanner(System.in);
    
    System.out.println("Enter the file name: ");
    String fileName = scan.nextLine();
    
    File input = new File(fileName);
    boolean exist = input.exists();
    
    while(exist == false){
      System.out.println("Enter the file name: ");
      fileName = scan.nextLine();
      input = new File(fileName);
      exist = input.exists();
    }
    
    scan.close();

    processFile(input);
    
    
  }

}