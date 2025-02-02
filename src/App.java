
import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner a = new Scanner(System.in);
        int init = 0;
        
        boolean on1 = false;
        boolean on2 = false;
        boolean on3 = true;
        String zzz = "";
        while(on3){

            if (init == 0 ){
                System.out.println("Digite 1 para adicionar uma disciplina, 2 para outras funcionalidades ou 3 para sair");
                String aaa = a.nextLine();
                switch(aaa) {
                case "1":
                    on1 = true;
                    init = 1;
                    break;
                case "2":
                    on2 = true;
                    init = 1;
                    break;
                case "3":
                    on1 = false;
                    on2 = false;
                    on3 = false;
                    break;
                default:
                    System.out.println("opcao invalida");
                    
                }
                
            }    


            while(on1){
                System.out.println("Digite o nome da disciplina ou deixe em branco para sair");
                String disc = a.nextLine() + ".txt";
                if(disc.equals(zzz + ".txt")){
                    init = 0;
                    on1 = false;
                    break;
                }
                
                File z = new File(disc);
                FileWriter zx; 
                zx = new FileWriter(disc, true);
                BufferedWriter bw = new BufferedWriter(zx);
                int i = 0;
                
                boolean on = true;
                
                while(on){
                    i++;
                    System.out.println("Digite o nome do aluno " + i);
                    String nome = a.nextLine();
                    if(nome.equals(zzz)){
                        break;
                    }
                    System.out.println("Digite as respostas do aluno " + i );
                    String notas = a.nextLine();
                    bw.write(nome + "\t" + notas);
                    bw.newLine();



                    
                }
                
                bw.close();
                zx.close();
            }


            while (on2){
                
            }
        }

    }
}
