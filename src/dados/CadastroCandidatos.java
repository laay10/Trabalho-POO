package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroCandidatos {

    private final List<Candidato> candidatos;

    public CadastroCandidatos() {
        candidatos = new ArrayList<>();
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public Candidato buscarPorNumero(int numero) {
        for (Candidato candidato : candidatos) {
            if (candidato.getNumero() == numero) {
                return candidato;
            }
        }
        return null;
    }

    public void cadastrarPresidentes(
            BufferedReader reader,
            BufferedWriter writer,
            CadastroPartidos cadastroPartidos,
            CadastroLocalidades cadastroLocalidades) throws IOException {

        String linha = reader.readLine();
        while (linha != null && !linha.equals("-1")) {
            int numero = Integer.parseInt(linha);
            String nome = reader.readLine();
            int codigoPartido = Integer.parseInt(reader.readLine());
            String cep = reader.readLine();
            double patrimonio = Double.parseDouble(reader.readLine());

            if (buscarPorNumero(numero) != null) {
                escrever(writer, "3: ERRO - candidato repetido.");
            } else {
                Partido partido = cadastroPartidos.buscarPorCodigo(codigoPartido);
                if (partido == null) {
                    escrever(writer, "3: ERRO - partido incorreto.");
                } else {
                    Localidade localidade = cadastroLocalidades.buscarPorCep(cep);
                    if (localidade == null) {
                        escrever(writer, "3: ERRO - localidade incorreta.");
                    } else {
                        Presidente presidente = new Presidente(
                                numero,
                                nome,
                                partido,
                                localidade,
                                patrimonio
                        );
                        candidatos.add(presidente);
                        escrever(
                                writer,
                                "3: " + numero + " - " + nome + " - "
                                + partido.getNome() + " - " + patrimonio
                        );
                    }
                }
            }
            linha = reader.readLine();
        }
    }

    public void cadastrarGovernadores(
            BufferedReader reader,
            BufferedWriter writer,
            CadastroPartidos cadastroPartidos,
            CadastroLocalidades cadastroLocalidades) throws IOException {

        String linha = reader.readLine();
        while (linha != null && !linha.equals("-1")) {
            int numero = Integer.parseInt(linha);
            String nome = reader.readLine();
            int codigoPartido = Integer.parseInt(reader.readLine());
            String cep = reader.readLine();
            String escolaridade = reader.readLine();

            if (buscarPorNumero(numero) != null) {
                escrever(writer, "4: ERRO - candidato repetido.");
            } else {
                Partido partido = cadastroPartidos.buscarPorCodigo(codigoPartido);
                if (partido == null) {
                    escrever(writer, "4: ERRO - partido incorreto.");
                } else {
                    Localidade localidade = cadastroLocalidades.buscarPorCep(cep);
                    if (localidade == null) {
                        escrever(writer, "4: ERRO - localidade incorreta.");
                    } else {
                        Governador governador = new Governador(
                                numero,
                                nome,
                                partido,
                                localidade,
                                escolaridade
                        );
                        candidatos.add(governador);
                        escrever(
                                writer,
                                "4: " + numero + " - " + nome + " - "
                                + partido.getNome() + " - " + escolaridade
                                + " - " + localidade.getNome()
                        );
                    }
                }
            }
            linha = reader.readLine();
        }
    }

    public void consultarCandidato(BufferedReader reader, BufferedWriter writer)
            throws IOException {
        int numero = Integer.parseInt(reader.readLine());
        Candidato candidato = buscarPorNumero(numero);

        if (candidato == null) {
            escrever(writer, "6: ERRO - candidato inexistente.");
        } else {
            escrever(
                    writer,
                    "6: " + candidato.getNumero() + " - " + candidato.getNome()
                    + " - " + candidato.getPartido().getNome()
                    + " - " + candidato.getLocalidade().getNome()
                    + " - " + obterInformacaoEspecifica(candidato)
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
