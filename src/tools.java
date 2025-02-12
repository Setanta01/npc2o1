import java.io.*;
import java.nio.file.*;
import java.util.*;

public class tools {

    public static List<String[]> lerRespostas(String nomeArquivo) throws IOException {
        List<String[]> respostas = new ArrayList<>();
        Path path = Paths.get(nomeArquivo);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.trim().split("\t");
                if (partes.length >= 2 && partes[1].length() == 10) {
                    respostas.add(new String[]{partes[0], partes[1]});
                }
            }
        }
        return respostas;
    }

    public static int calcularAcertos(String respostasAluno, String gabarito) {
        respostasAluno = respostasAluno.toLowerCase();
        gabarito = gabarito.toLowerCase();

        if (respostasAluno.length() != 10) return 0;
        if (respostasAluno.equals("ffffffffff") || respostasAluno.equals("vvvvvvvvvv")) {
            return 0;
        }

        int acertos = 0;
        for (int i = 0; i < 10; i++) {
            if (respostasAluno.charAt(i) == gabarito.charAt(i)) {
                acertos++;
            }
        }
        return acertos;
    }

    public static void gerarRelatorioAlfabetico(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[0];
            String respostasAluno = aluno[1];
            int acertos = calcularAcertos(respostasAluno, gabarito);
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }

        relatorio.sort(Comparator.comparing(a -> a[0].toLowerCase()));

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(nomeDisciplina + "_alfabetico.txt"))) {
            for (String[] aluno : relatorio) {
                bw.write(aluno[0] + "\t" + aluno[1]);
                bw.newLine();
            }
        }
    }

    public static void gerarRelatorioDecrescente(List<String[]> respostas, String gabarito, String nomeDisciplina) throws IOException {
        List<String[]> relatorio = new ArrayList<>();
        int totalAcertos = 0;
        
        for (String[] aluno : respostas) {
            String nomeAluno = aluno[0];
            String respostasAluno = aluno[1];
            int acertos = calcularAcertos(respostasAluno, gabarito);
            totalAcertos += acertos;
            relatorio.add(new String[]{nomeAluno, String.valueOf(acertos)});
        }

        relatorio.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(nomeDisciplina + "_decrescente.txt"))) {
            for (String[] aluno : relatorio) {
                bw.write(aluno[0] + "\t" + aluno[1]);
                bw.newLine();
            }
            double media = (double) totalAcertos / respostas.size();
            bw.write("Média da turma: " + String.format("%.2f", media));
            bw.newLine();
        }
    }

    public static void gerarRelatorioDisciplina(String nomeDisciplina, String gabarito) throws IOException {
        List<String[]> respostas = lerRespostas(nomeDisciplina + ".txt");
        gerarRelatorioAlfabetico(respostas, gabarito, nomeDisciplina);
        gerarRelatorioDecrescente(respostas, gabarito, nomeDisciplina);
        System.out.println("Relatórios gerados para a disciplina: " + nomeDisciplina);
    }
}
