package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroVotos {

    private List<Voto> votos;

    public CadastroVotos() {
        this.votos = new ArrayList<>();
    }

    public void adicionarVoto(Voto voto) {
        votos.add(voto);
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public Voto buscarPorId(int id) {
        for (Voto voto : votos) {
            if (voto.getId() == id) {
                return voto;
            }
        }
        return null;
    }

    public int contarVotos(Candidato candidato) {
        int quantidade = 0;
        for (Voto voto : votos) {
            if (voto.getCandidato() == candidato) {
                quantidade++;
            }
        }
        return quantidade;
    }
    
    public int contarVotos(Partido partido) {
        int quantidade = 0;
        for (Voto voto : votos) {
            if (voto.getCandidato().getPartido() == partido) {
                quantidade++;
            }
        }
        return quantidade;
    }


    public void cadastrarVotos(
            BufferedReader reader,
            BufferedWriter writer,
            CadastroCandidatos cadastroCandidatos,
            CadastroLocalidades cadastroLocalidades) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {
            int id = Integer.parseInt(linha);
            int hora = Integer.parseInt(reader.readLine());
            int numeroCandidato = Integer.parseInt(reader.readLine());
            String cep = reader.readLine();

            if (buscarPorId(id) != null) {
                escrever(writer, "5: ERRO - id repetido.");
            } else if (hora < 8 || hora > 17) {
                escrever(writer, "5: ERRO - hora incorreta.");
            } else {
                Candidato candidato = cadastroCandidatos.buscarPorNumero(numeroCandidato);

                if (candidato == null) {
                    escrever(writer, "5: ERRO - candidato incorreto.");
                } else {
                    Localidade localidade = cadastroLocalidades.buscarPorCep(cep);

                    if (localidade == null) {
                        escrever(writer, "5: ERRO - localidade incorreta.");
                    } else if (candidato.getLocalidade() != localidade) {
                        escrever(writer, "5: ERRO - localidade do candidato incorreta.");
                    } else {
                        Voto voto = new Voto(id, hora, candidato, localidade);
                        adicionarVoto(voto);
                        escrever(
                                writer,
                                "5: " + id + " - " + hora + " - " + candidato.getNome()
                                + " - " + localidade.getNome()
                        );
                    }
                }
            }

            linha = reader.readLine();
        }
    }

    public void carregarDeArquivo(
            BufferedReader reader,
            List<Candidato> candidatos,
            List<Localidade> localidades) throws IOException {

        String linha = reader.readLine();

        while (linha != null && !linha.equals("-1")) {

            int id = Integer.parseInt(linha);

            int hora = Integer.parseInt(reader.readLine());

            int numeroCandidato =
                    Integer.parseInt(reader.readLine());

            String cep = reader.readLine();

            Candidato candidato = null;

            for (Candidato c : candidatos) {

                if (c.getNumero() == numeroCandidato) {
                    candidato = c;
                    break;
                }
            }

            Localidade localidade = null;

            for (Localidade l : localidades) {

                if (l.getCep().equals(cep)) {
                    localidade = l;
                    break;
                }
            }

            Voto voto =
                    new Voto(
                            id,
                            hora,
                            candidato,
                            localidade
                    );

            adicionarVoto(voto);

            linha = reader.readLine();
        }
    }

    private void escrever(BufferedWriter writer, String mensagem) throws IOException {
        writer.write(mensagem);
        writer.newLine();
    }
}