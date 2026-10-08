package aplicacao;

import dados.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppACMEPolling {

    private CadastroPartidos cadastroPartidos;
    private CadastroLocalidades cadastroLocalidades;
    private CadastroVotos cadastroVotos;
    private CadastroCandidatos cadastroCandidatos;

    //Construtor inicializando as listas necessárias para o funcionamento do programa
    public AppACMEPolling() {
        cadastroPartidos = new CadastroPartidos();
        cadastroLocalidades = new CadastroLocalidades();
        cadastroVotos = new CadastroVotos();
        cadastroCandidatos = new CadastroCandidatos();
    }

    // Método principal para executar o programa
    public void executar() {

        File pollingIn = resolverArquivoPollingIn();
        File pollingOut = resolverArquivoPollingOut(pollingIn);

        try (
            BufferedReader reader =
                    new BufferedReader(new FileReader(pollingIn));

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(pollingOut))
        ) {

            // 1 - Partidos
            cadastroPartidos.cadastrarPartidos(reader, writer);

            // 2 - Localidades
            cadastroLocalidades.cadastrarLocalidades(reader, writer);

            // 3 - Presidentes
            cadastroCandidatos.cadastrarPresidentes(
                    reader,
                    writer,
                    cadastroPartidos,
                    cadastroLocalidades
            );

            // 4 - Governadores
            cadastroCandidatos.cadastrarGovernadores(
                    reader,
                    writer,
                    cadastroPartidos,
                    cadastroLocalidades
            );

            // 5 - Votos
            cadastroVotos.cadastrarVotos(
                    reader,
                    writer,
                    cadastroCandidatos,
                    cadastroLocalidades
            );

            // 6 - Consultar candidato
            cadastroCandidatos.consultarCandidato(reader, writer);

            // 7 - Candidatos de um partido
            cadastroPartidos.mostrarCandidatosPartido(reader, writer, cadastroCandidatos);

            // 8 - Eleito de uma localidade
            cadastroLocalidades.mostrarEleitoLocalidade(
                    reader,
                    writer,
                    cadastroCandidatos,
                    cadastroVotos
            );

            // 9 - Partido com mais votos
            cadastroPartidos.mostrarPartidoMaisVotos(writer, cadastroVotos);

            // 10 - Partido com mais eleitos
            cadastroPartidos.mostrarPartidoMaisEleitos(
                    writer,
                    cadastroCandidatos,
                    cadastroLocalidades,
                    cadastroVotos
            );

        } catch (IOException e) {
            System.out.println(
                    "Erro ao ler/gravar arquivo: "
                    + e.getMessage()
            );
        }
    }

    // Método para resolver o arquivo de entrada, verificando se ele existe em diferentes caminhos

    private File resolverArquivoPollingIn() {
        String[] caminhos = {
            "src/aplicacao/pollingin.txt",
            "pollingin.txt"
        };

        for (String caminho : caminhos) {
            File arquivo = new File(caminho);
            if (arquivo.exists()) {
                return arquivo;
            }
        }

        return new File("pollingin.txt");
    }
    // Método para resolver o arquivo de saída, criando-o no mesmo diretório do arquivo de entrada

    private File resolverArquivoPollingOut(File pollingIn) {
        if (pollingIn.getParentFile() != null) {
            return new File(
                    pollingIn.getParentFile(),
                    "pollingout.txt"
            );
        }

        return new File("pollingout.txt");
    }

}