package aplicacao;

import dados.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppACMEPolling {

    private CadastroPartidos cadastroPartidos;
    private CadastroLocalidades cadastroLocalidades;
    private CadastroVotos cadastroVotos;

    private List<Candidato> candidatos;

    public AppACMEPolling() {
        cadastroPartidos = new CadastroPartidos();
        cadastroLocalidades = new CadastroLocalidades();
        cadastroVotos = new CadastroVotos();
        candidatos = new ArrayList<>();
    }

    public void executar() {

        File arquivoEntrada = resolverArquivoEntrada();
        File arquivoSaida = resolverArquivoSaida(arquivoEntrada);

        try (
            BufferedReader reader =
                    new BufferedReader(new FileReader(arquivoEntrada));

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(arquivoSaida))
        ) {

            // 1 - Partidos
            cadastrarPartidos(reader, writer);

            // 2 - Localidades
            cadastrarLocalidades(reader, writer);

            // 3 - Presidentes
            cadastrarPresidentes(reader, writer);

            // 4 - Governadores
            cadastrarGovernadores(reader, writer);

            // 5 - Votos
            cadastrarVotos(reader, writer);

            // 6 - Consultar candidato
            consultarCandidato(reader, writer);

            // 7 - Candidatos de um partido
            mostrarCandidatosPartido(reader, writer);

            // 8 - Eleito de uma localidade
            mostrarEleitoLocalidade(reader, writer);

            // 9 - Partido com mais votos
            mostrarPartidoMaisVotos(writer);

            // 10 - Partido com mais eleitos
            mostrarPartidoMaisEleitos(writer);

        } catch (IOException e) {
            System.out.println(
                    "Erro ao ler/gravar arquivo: "
                    + e.getMessage()
            );
        }
    }


    private File resolverArquivoEntrada() {
        String[] caminhos = {
            "src/aplicacao/entrada.txt",
            "entrada.txt"
        };

        for (String caminho : caminhos) {
            File arquivo = new File(caminho);
            if (arquivo.exists()) {
                return arquivo;
            }
        }

        return new File("entrada.txt");
    }

    private File resolverArquivoSaida(File arquivoEntrada) {
        if (arquivoEntrada.getParentFile() != null) {
            return new File(
                    arquivoEntrada.getParentFile(),
                    "saida.txt"
            );
        }

        return new File("saida.txt");
    }

    // =========================================================
    // MÉTODO PARA ESCREVER NO TXT
    // =========================================================

    private void escrever(
            BufferedWriter writer,
            String mensagem) throws IOException {

        writer.write(mensagem);
        writer.newLine();
    }


    // =========================================================
    // 1 - CADASTRAR PARTIDOS
    // =========================================================

    private void cadastrarPartidos(BufferedReader reader, BufferedWriter writer) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int codigo = Integer.parseInt(linha);
            String nome = reader.readLine();

            if (cadastroPartidos.existe(codigo)) {

                escrever(
                        writer,
                        "1: ERRO - partido repetido."
                );

            } else {

                Partido partido =
                        new Partido(codigo, nome);

                cadastroPartidos.adicionarPartido(partido);

                escrever(
                        writer,
                        "1: " + codigo + " - " + nome
                );
            }

            linha = reader.readLine();
        }
    }


    // =========================================================
    // 2 - CADASTRAR LOCALIDADES
    // =========================================================

    private void cadastrarLocalidades(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        String cep = reader.readLine();

        while (cep != null && !cep.equals("-1")) {

            String nome = reader.readLine();

            long qtdEleitores =
                    Long.parseLong(reader.readLine());

            String tipoTexto = reader.readLine();

            TipoLocalidade tipo;

            try {

                tipo = TipoLocalidade.valueOf(tipoTexto);

            } catch (IllegalArgumentException e) {

                escrever(
                        writer,
                        "2: ERRO - tipo de localidade incorreto."
                );

                cep = reader.readLine();
                continue;
            }

            if (cadastroLocalidades.existe(cep)) {

                escrever(
                        writer,
                        "2: ERRO - localidade repetida."
                );

            } else {

                Localidade localidade =
                        new Localidade(
                                cep,
                                nome,
                                qtdEleitores,
                                tipo
                        );

                cadastroLocalidades
                        .adicionarLocalidade(localidade);

                escrever(
                        writer,
                        "2: " + cep
                        + " - " + nome
                        + " - " + qtdEleitores
                        + " - " + tipo.getDescricao()
                );
            }

            cep = reader.readLine();
        }
    }


    // =========================================================
    // 3 - CADASTRAR PRESIDENTES
    // =========================================================

    private void cadastrarPresidentes(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int numero = Integer.parseInt(linha);

            String nome = reader.readLine();

            int codigoPartido =
                    Integer.parseInt(reader.readLine());

            String cep = reader.readLine();

            double patrimonio =
                    Double.parseDouble(reader.readLine());


            if (buscarCandidato(numero) != null) {

                escrever(
                        writer,
                        "3: ERRO - candidato repetido."
                );

            } else {

                Partido partido =
                        cadastroPartidos
                                .buscarPorCodigo(codigoPartido);

                if (partido == null) {

                    escrever(
                            writer,
                            "3: ERRO - partido incorreto."
                    );

                } else {

                    Localidade localidade =
                            cadastroLocalidades
                                    .buscarPorCep(cep);

                    if (localidade == null) {

                        escrever(
                                writer,
                                "3: ERRO - localidade incorreta."
                        );

                    } else {

                        Presidente presidente =
                                new Presidente(
                                        numero,
                                        nome,
                                        partido,
                                        localidade,
                                        patrimonio
                                );

                        candidatos.add(presidente);

                        escrever(
                                writer,
                                "3: " + numero
                                + " - " + nome
                                + " - " + partido.getNome()
                                + " - " + patrimonio
                        );
                    }
                }
            }

            linha = reader.readLine();
        }
    }


    // =========================================================
    // 4 - CADASTRAR GOVERNADORES
    // =========================================================

    private void cadastrarGovernadores(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int numero = Integer.parseInt(linha);

            String nome = reader.readLine();

            int codigoPartido =
                    Integer.parseInt(reader.readLine());

            String cep = reader.readLine();

            String escolaridade =
                    reader.readLine();


            if (buscarCandidato(numero) != null) {

                escrever(
                        writer,
                        "4: ERRO - candidato repetido."
                );

            } else {

                Partido partido =
                        cadastroPartidos
                                .buscarPorCodigo(codigoPartido);

                if (partido == null) {

                    escrever(
                            writer,
                            "4: ERRO - partido incorreto."
                    );

                } else {

                    Localidade localidade =
                            cadastroLocalidades
                                    .buscarPorCep(cep);

                    if (localidade == null) {

                        escrever(
                                writer,
                                "4: ERRO - localidade incorreta."
                        );

                    } else {

                        Governador governador =
                                new Governador(
                                        numero,
                                        nome,
                                        partido,
                                        localidade,
                                        escolaridade
                                );

                        candidatos.add(governador);

                        escrever(
                                writer,
                                "4: " + numero
                                + " - " + nome
                                + " - " + partido.getNome()
                                + " - " + escolaridade
                                + " - " + localidade.getNome()
                        );
                    }
                }
            }

            linha = reader.readLine();
        }
    }


    // =========================================================
    // 5 - CADASTRAR VOTOS
    // =========================================================

    private void cadastrarVotos(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int id = Integer.parseInt(linha);

            int hora =
                    Integer.parseInt(reader.readLine());

            int numeroCandidato =
                    Integer.parseInt(reader.readLine());

            String cep =
                    reader.readLine();


            if (buscarVoto(id) != null) {

                escrever(
                        writer,
                        "5: ERRO - id repetido."
                );

            } else if (hora < 0 || hora > 23) {

                escrever(
                        writer,
                        "5: ERRO - hora incorreta."
                );

            } else {

                Candidato candidato =
                        buscarCandidato(numeroCandidato);

                if (candidato == null) {

                    escrever(
                            writer,
                            "5: ERRO - candidato incorreto."
                    );

                } else {

                    Localidade localidade =
                            cadastroLocalidades
                                    .buscarPorCep(cep);

                    if (localidade == null) {

                        escrever(
                                writer,
                                "5: ERRO - localidade incorreta."
                        );

                    } else if (
                            candidato.getLocalidade()
                                    != localidade
                    ) {

                        escrever(
                                writer,
                                "5: ERRO - localidade do candidato incorreta."
                        );

                    } else {

                        Voto voto =
                                new Voto(
                                        id,
                                        hora,
                                        candidato,
                                        localidade
                                );

                        cadastroVotos.adicionarVoto(voto);

                        escrever(
                                writer,
                                "5: " + id
                                + " - " + hora
                                + " - " + candidato.getNome()
                                + " - " + localidade.getNome()
                        );
                    }
                }
            }

            linha = reader.readLine();
        }
    }


    // =========================================================
    // 6 - CONSULTAR CANDIDATO
    // =========================================================

    private void consultarCandidato(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        int numero =
                Integer.parseInt(reader.readLine());

        Candidato candidato =
                buscarCandidato(numero);

        if (candidato == null) {

            escrever(
                    writer,
                    "6: ERRO - candidato inexistente."
            );

        } else {

            escrever(
                    writer,
                    "6: " + candidato.getNumero()
                    + " - " + candidato.getNome()
                    + " - " + candidato.getPartido().getNome()
                    + " - " + candidato.getLocalidade().getNome()
                    + " - " + obterInformacaoEspecifica(candidato)
            );
        }
    }


    // =========================================================
    // 7 - CANDIDATOS DE UM PARTIDO
    // =========================================================

    private void mostrarCandidatosPartido(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        int codigoPartido =
                Integer.parseInt(reader.readLine());

        Partido partido =
                cadastroPartidos
                        .buscarPorCodigo(codigoPartido);

        if (partido == null) {

            escrever(
                    writer,
                    "7: ERRO - partido inexistente."
            );

            return;
        }

        boolean encontrou = false;

        for (Candidato candidato : candidatos) {

            if (candidato.getPartido() == partido) {

                encontrou = true;

                escrever(
                        writer,
                        "7: " + candidato.getNumero()
                        + " - " + candidato.getNome()
                        + " - " + candidato.getPartido().getNome()
                        + " - " + candidato.getLocalidade().getNome()
                        + " - " + obterInformacaoEspecifica(candidato)
                );
            }
        }

        if (!encontrou) {

            escrever(
                    writer,
                    "7: nenhum candidato cadastrado."
            );
        }
    }


    // =========================================================
    // 8 - ELEITO DE UMA LOCALIDADE
    // =========================================================

    private void mostrarEleitoLocalidade(
            BufferedReader reader,
            BufferedWriter writer) throws IOException {

        String cep = reader.readLine();

        Localidade localidade =
                cadastroLocalidades.buscarPorCep(cep);

        if (localidade == null) {

            escrever(
                    writer,
                    "8: ERRO - localidade inexistente."
            );

            return;
        }

        Candidato eleito = null;

        int maiorQuantidadeVotos = 0;

        boolean encontrouCandidato = false;

        boolean empate = false;


        for (Candidato candidato : candidatos) {

            if (candidato.getLocalidade() == localidade) {

                encontrouCandidato = true;

                int quantidadeVotos =
                        contarVotos(candidato);


                if (quantidadeVotos >
                        maiorQuantidadeVotos) {

                    maiorQuantidadeVotos =
                            quantidadeVotos;

                    eleito = candidato;

                    empate = false;

                } else if (
                        quantidadeVotos ==
                                maiorQuantidadeVotos
                        && quantidadeVotos > 0
                ) {

                    empate = true;
                }
            }
        }


        if (!encontrouCandidato) {

            escrever(
                    writer,
                    "8: nenhum candidato cadastrado."
            );

        } else if (
                eleito == null
                || maiorQuantidadeVotos == 0
        ) {

            escrever(
                    writer,
                    "8: nenhum candidato eleito."
            );

        } else if (empate) {

            escrever(
                    writer,
                    "8: nenhum candidato eleito."
            );

        } else {

            escrever(
                    writer,
                    "8: " + eleito.getNumero()
                    + " - " + eleito.getNome()
                    + " - " + maiorQuantidadeVotos
            );
        }
    }


    // =========================================================
    // 9 - PARTIDO COM MAIS VOTOS
    // =========================================================

    private void mostrarPartidoMaisVotos(
            BufferedWriter writer) throws IOException {

        if (cadastroPartidos.getPartidos().isEmpty()) {

            escrever(
                    writer,
                    "9: ERRO - nenhum partido cadastrado."
            );

            return;
        }

        Partido partidoMaisVotos = null;

        int maiorQuantidade = 0;


        for (Partido partido :
                cadastroPartidos.getPartidos()) {

            int quantidade = 0;


            for (Voto voto :
                    cadastroVotos.getVotos()) {

                if (voto.getCandidato()
                        .getPartido() == partido) {

                    quantidade++;
                }
            }


            if (quantidade > maiorQuantidade) {

                maiorQuantidade =
                        quantidade;

                partidoMaisVotos =
                        partido;
            }
        }


        if (partidoMaisVotos == null) {

            escrever(
                    writer,
                    "9: nenhum partido com votos."
            );

        } else {

            escrever(
                    writer,
                    "9: " + partidoMaisVotos.getCodigo()
                    + " - " + partidoMaisVotos.getNome()
                    + " - " + maiorQuantidade
            );
        }
    }


    // =========================================================
    // 10 - PARTIDO COM MAIS ELEITOS
    // =========================================================

    private void mostrarPartidoMaisEleitos(
            BufferedWriter writer) throws IOException {

        if (cadastroPartidos.getPartidos().isEmpty()) {

            escrever(
                    writer,
                    "10: ERRO - nenhum partido cadastrado."
            );

            return;
        }

        int maiorQuantidade = 0;

        Partido partidoMaisEleitos = null;


        for (Partido partido :
                cadastroPartidos.getPartidos()) {

            int quantidadeEleitos = 0;


            for (Localidade localidade :
                    cadastroLocalidades.getLocalidades()) {

                Candidato eleito =
                        encontrarEleito(localidade);


                if (eleito != null
                        && eleito.getPartido() == partido) {

                    quantidadeEleitos++;
                }
            }


            if (quantidadeEleitos >
                    maiorQuantidade) {

                maiorQuantidade =
                        quantidadeEleitos;

                partidoMaisEleitos =
                        partido;
            }
        }


        if (partidoMaisEleitos == null) {

            escrever(
                    writer,
                    "10: nenhum partido com eleitos."
            );

        } else {

            escrever(
                    writer,
                    "10: " + partidoMaisEleitos.getCodigo()
                    + " - " + partidoMaisEleitos.getNome()
                    + " - " + maiorQuantidade
            );
        }
    }


    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    private Candidato buscarCandidato(int numero) {

        for (Candidato candidato : candidatos) {

            if (candidato.getNumero() == numero) {
                return candidato;
            }
        }

        return null;
    }


    private Voto buscarVoto(int id) {

        for (Voto voto :
                cadastroVotos.getVotos()) {

            if (voto.getId() == id) {
                return voto;
            }
        }

        return null;
    }


    private String obterInformacaoEspecifica(
            Candidato candidato) {

        if (candidato instanceof Presidente) {

            Presidente presidente =
                    (Presidente) candidato;

            return String.valueOf(
                    presidente.getPatrimonio()
            );

        } else if (candidato instanceof Governador) {

            Governador governador =
                    (Governador) candidato;

            return governador.getEscolaridade();
        }

        return "";
    }


    private int contarVotos(Candidato candidato) {

        int quantidade = 0;

        for (Voto voto :
                cadastroVotos.getVotos()) {

            if (voto.getCandidato() == candidato) {
                quantidade++;
            }
        }

        return quantidade;
    }


    private Candidato encontrarEleito(
            Localidade localidade) {

        Candidato eleito = null;

        int maiorQuantidadeVotos = 0;

        boolean empate = false;


        for (Candidato candidato :
                candidatos) {

            if (candidato.getLocalidade()
                    == localidade) {

                int quantidadeVotos =
                        contarVotos(candidato);


                if (quantidadeVotos >
                        maiorQuantidadeVotos) {

                    maiorQuantidadeVotos =
                            quantidadeVotos;

                    eleito = candidato;

                    empate = false;

                } else if (
                        quantidadeVotos ==
                                maiorQuantidadeVotos
                        && quantidadeVotos > 0
                ) {

                    empate = true;
                }
            }
        }


        if (empate) {
            return null;
        }

        return eleito;
    }
}