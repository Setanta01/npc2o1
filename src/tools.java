import java.io.*;
import java.util.*;
import java.nio.file.*;

public class tools {

    // Método para ler as respostas do gabarito e dos alunos de um arquivo
    public static List<String[]> lerRespostas(String nomeArquivo) throws IOException {
        List<String[]> respostas = new ArrayList<>();
        Path path = Paths.get(nomeArquivo);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\t"); // Divide a linha pelo "tab"
                respostas.add(partes);
            }
        }
        return respostas;
    }

    // Método para calcular o número de acertos de um aluno com base no gabarito
    public static int calcularAcertos(String respostasAluno, String gabarito) {
        int acertos = 0;
        for (int i = 0; i < respostasAluno.length(); i++) {
            if (respostasAluno.charAt(i) == gabarito.charAt(i)) {
                acertos++;
            }
        }
        return acertos;
    }

    // Método para gerar o relatório de alunos e suas notas, ordenados alfabeticamente
    public static void gerarRelatorioAlfabetico(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[1]; // O nome está na segunda parte
            String respostasAluno = aluno[0]; // As respostas estão na primeira parte
            int acertos = calcularAcertos(respostasAluno, gabarito);
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }
        
        // Ordena os alunos por nome
        relatorio.sort(Comparator.comparing(a -> a[0]));

        // Cria o arquivo com os dados ordenados alfabeticamente
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(nomeDisciplina + "_alfabetico.txt"))) {
            for (String[] aluno : relatorio) {
                bw.write(aluno[0] + "\t" + aluno[1]);
                bw.newLine();
            }
        }
    }

    // Método para gerar o relatório de alunos e suas notas, ordenados por ordem decrescente de notas
    public static void gerarRelatorioDecrescente(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        int totalAcertos = 0;
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[1]; // O nome está na segunda parte
            String respostasAluno = aluno[0]; // As respostas estão na primeira parte
            int acertos = calcularAcertos(respostasAluno, gabarito);
            totalAcertos += acertos;
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }

        // Ordena os alunos por número de acertos de forma decrescente
        relatorio.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        // Cria o arquivo com os dados ordenados por ordem decrescente de notas
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(nomeDisciplina + "_decrescente.txt"))) {
            for (String[] aluno : relatorio) {
                bw.write(aluno[0] + "\t" + aluno[1]);
                bw.newLine();
            }

            // Calcula a média da turma
            double media = (double) totalAcertos / respostas.size();
            bw.write("Média da turma: " + media);
            bw.newLine();
        }
    }

    // Método principal que gerencia o fluxo para a geração de relatórios
    public static void gerarRelatorioDisciplina(String nomeDisciplina, String gabarito) throws IOException {
        // Lê as respostas dos alunos
        List<String[]> respostas = lerRespostas(nomeDisciplina + ".txt");

        // Gera o relatório alfabético
        gerarRelatorioAlfabetico(respostas, gabarito, nomeDisciplina);
        
        // Gera o relatório decrescente
        gerarRelatorioDecrescente(respostas, gabarito, nomeDisciplina);

        // Exibe os relatórios na tela
        System.out.println("Relatórios gerados para a disciplina " + nomeDisciplina);
    }
}
