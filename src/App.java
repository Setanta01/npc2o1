
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
                    System.out.println("Opcão inválida.");
                    
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
                System.out.println("Digite o nome da disciplina para gerar os resultados ou deixe em branco para sair:");
                String nomeDisciplina = a.nextLine();
                if(nomeDisciplina.isEmpty()) {
                    break;
                }

                // Lê o arquivo de respostas dos alunos
                FileReader fr = new FileReader(nomeDisciplina + ".txt");
                BufferedReader br = new BufferedReader(fr);
                List<String[]> respostas = new ArrayList<>();
                String linha;
                while((linha = br.readLine()) != null) {
                    String[] partes = linha.split("\t");
                    respostas.add(partes);
                }
                br.close();

                // Solicita o gabarito da prova
                System.out.println("Digite o caminho do arquivo de gabarito:");
                String caminhoGabarito = a.nextLine();
                
                File arquivoGabarito = new File(caminhoGabarito);
                if (!arquivoGabarito.exists()) {
                    System.out.println("Erro: Arquivo de gabarito não encontrado.");
                    continue;
                }
                
                BufferedReader brGabarito = new BufferedReader(new FileReader(arquivoGabarito));
                StringBuilder sb = new StringBuilder();
                String linhaGabarito;
                while ((linhaGabarito = brGabarito.readLine()) != null) {
                    sb.append(linhaGabarito);
                }
                brGabarito.close();
                String gabarito = sb.toString();

                if (gabarito.isEmpty() || gabarito.length() != 10 || !gabarito.matches("[VFvf]{10}")) {
                    System.out.println("Erro: O gabarito deve ter exatamente 10 caracteres e conter apenas 'V' ou 'F'.");
                    continue;
                }

                // Gerar relatórios
                gerarRelatorioAlfabetico(respostas, gabarito, nomeDisciplina);
                gerarRelatorioDecrescente(respostas, gabarito, nomeDisciplina);
                
                // Exibir na tela
                System.out.println("Relatórios gerados para a disciplina " + nomeDisciplina + "!");
                    init = 0;
                    on1 = false;
                    break;
            }
        }
    }

    // Método para calcular os acertos de um aluno
public static int calcularAcertos(String respostasAluno, String gabarito) {
    respostasAluno = respostasAluno.toLowerCase();
    gabarito = gabarito.toLowerCase();

    int tamanho = respostasAluno.length();

    if (tamanho == 0 || respostasAluno.equals("f".repeat(tamanho)) || respostasAluno.equals("v".repeat(tamanho))) {
        return 0;
    }

    int acertos = 0;
    for (int i = 0; i < tamanho; i++) {
        if (i < 10 && respostasAluno.charAt(i) == gabarito.charAt(i)) {
            acertos++;
        }
    }
    return acertos;
}   

    // Método para gerar o relatório alfabético
    public static void gerarRelatorioAlfabetico(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[0];  // O nome do aluno está na primeira parte
            String respostasAluno = aluno[1];  // As respostas estão na segunda parte
            int acertos = calcularAcertos(respostasAluno, gabarito);
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }
        
        // Ordena por nome
        relatorio.sort(Comparator.comparing(a -> a[0]));
        
        // Grava o arquivo alfabético
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_alfabetico.txt"));
        for (String[] aluno : relatorio) {
            bw.write(aluno[0] + "\t" + aluno[1]);
            bw.newLine();
        }
        bw.close();
    }

    // Método para gerar o relatório com acertos em ordem decrescente
    public static void gerarRelatorioDecrescente(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        int totalAcertos = 0;
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[0];  // O nome do aluno está na primeira parte
            String respostasAluno = aluno[1];  // As respostas estão na segunda parte
            int acertos = calcularAcertos(respostasAluno, gabarito);
            totalAcertos += acertos;
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }
        
        // Ordena por acertos decrescentes
        relatorio.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));
        
        // Grava o arquivo com as notas decrescentes
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDisciplina + "_decrescente.txt"));
        for (String[] aluno : relatorio) {
            bw.write(aluno[0] + "\t" + aluno[1]);
            bw.newLine();
        }
        
        // Calcula e grava a média
        double media = (double) totalAcertos / respostas.size();
        bw.write("Média da turma: " + media);
        bw.newLine();
        bw.close();
    }
}