package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroPartidos {

    private List<Partido> partidos;

    public CadastroPartidos() {
        this.partidos = new ArrayList<>();
    }

    public void adicionarPartido(Partido partido) {
        partidos.add(partido);
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public Partido buscarPorCodigo(int codigo) {

        for (Partido partido : partidos) {

            if (partido.getCodigo() == codigo) {
                return partido;
            }
        }

        return null;
    }

    public boolean existe(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public void carregarDeArquivo(BufferedReader reader) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int codigo = Integer.parseInt(linha);

            String nome = reader.readLine();

            Partido partido = new Partido(codigo, nome);

            adicionarPartido(partido);

            linha = reader.readLine();
        }
    }

    public void cadastrarPartidos(BufferedReader reader, BufferedWriter writer) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int codigo = Integer.parseInt(linha);
            String nome = reader.readLine();

            if (existe(codigo)) {

                escrever(writer, "1: ERRO - partido repetido.");

            } else {

                Partido partido =
                        new Partido(codigo, nome);

                adicionarPartido(partido);

                escrever(writer, "1: " + codigo + " - " + nome);
            }

            linha = reader.readLine();
        }
    }

    public void mostrarCandidatosPartido(
            BufferedReader reader,
            BufferedWriter writer,
            CadastroCandidatos cadastroCandidatos) throws IOException {

        int codigoPartido = Integer.parseInt(reader.readLine());
        Partido partido = buscarPorCodigo(codigoPartido);

        if (partido == null) {
            escrever(writer, "7: ERRO - partido inexistente.");
            return;
        }

        boolean encontrou = false;
        for (Candidato candidato : cadastroCandidatos.getCandidatos()) {
            if (candidato.getPartido() == partido) {
                encontrou = true;
                escrever(
                        writer,
                        "7: " + candidato.getNumero() + " - " + candidato.getNome()
                        + " - " + candidato.getPartido().getNome()
                        + " - " + candidato.getLocalidade().getNome()
                        + " - " + obterInformacaoEspecifica(candidato)
                );
            }
        }

        if (!encontrou) {
            escrever(writer, "7: nenhum candidato cadastrado.");
        }
    }

    public void mostrarPartidoMaisVotos(
            BufferedWriter writer,
            CadastroVotos cadastroVotos) throws IOException {

        if (partidos.isEmpty()) {
            escrever(writer, "9: ERRO - nenhum partido cadastrado.");
            return;
        }

        Partido partidoMaisVotos = null;
        int maiorQuantidade = 0;

        for (Partido partido : partidos) {
            int quantidade = cadastroVotos.contarVotos(partido);
            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
                partidoMaisVotos = partido;
            }
        }

        if (partidoMaisVotos == null) {
            escrever(writer, "9: nenhum partido com votos.");
        } else {
            escrever(
                    writer,
                    "9: " + partidoMaisVotos.getCodigo() + " - "
                    + partidoMaisVotos.getNome() + " - " + maiorQuantidade
            );
        }
    }

    public void mostrarPartidoMaisEleitos(
            BufferedWriter writer,
            CadastroCandidatos cadastroCandidatos,
            CadastroLocalidades cadastroLocalidades,
            CadastroVotos cadastroVotos) throws IOException {

        if (partidos.isEmpty()) {
            escrever(writer, "10: ERRO - nenhum partido cadastrado.");
            return;
        }

        Partido partidoMaisEleitos = null;
        int maiorQuantidade = 0;

        for (Partido partido : partidos) {
            int quantidadeEleitos = 0;
            for (Localidade localidade : cadastroLocalidades.getLocalidades()) {
                Candidato eleito = cadastroLocalidades.buscarEleito(
                        localidade,
                    cadastroCandidatos,
                        cadastroVotos
                );
                if (eleito != null && eleito.getPartido() == partido) {
                    quantidadeEleitos++;
                }
            }

            if (quantidadeEleitos > maiorQuantidade) {
                maiorQuantidade = quantidadeEleitos;
                partidoMaisEleitos = partido;
            }
        }

        if (partidoMaisEleitos == null) {
            escrever(writer, "10: nenhum partido com eleitos.");
        } else {
            escrever(
                    writer,
                    "10: " + partidoMaisEleitos.getCodigo() + " - "
                    + partidoMaisEleitos.getNome() + " - " + maiorQuantidade
            );
        }
    }

    private String obterInformacaoEspecifica(Candidato candidato) {
        if (candidato instanceof Presidente) {
            return String.valueOf(((Presidente) candidato).getPatrimonio());
        } else if (candidato instanceof Governador) {
            return ((Governador) candidato).getEscolaridade();
        }
        return "";
    }

    private void escrever(BufferedWriter writer, String mensagem) throws IOException {
        writer.write(mensagem);
        writer.newLine();
    }

}