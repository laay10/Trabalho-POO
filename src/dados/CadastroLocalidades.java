package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroLocalidades {

    private List<Localidade> localidades;

    public CadastroLocalidades() {
        this.localidades = new ArrayList<>();
    }

    public void adicionarLocalidade(Localidade localidade) {
        localidades.add(localidade);
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public Localidade buscarPorCep(String cep) {

        for (Localidade localidade : localidades) {

            if (localidade.getCep().equals(cep)) {
                return localidade;
            }
        }

        return null;
    }

    public boolean existe(String cep) {
        return buscarPorCep(cep) != null;
    }

    public void carregarDeArquivo(BufferedReader reader) throws IOException {

        String cep = reader.readLine();

        while (cep != null && !cep.equals("-1")) {

            String nome = reader.readLine();

            long qtdEleitores =
                    Long.parseLong(reader.readLine());

            TipoLocalidade tipo =
                    TipoLocalidade.valueOf(reader.readLine());

            Localidade localidade =
                    new Localidade(
                            cep,
                            nome,
                            qtdEleitores,
                            tipo
                    );

            adicionarLocalidade(localidade);

            cep = reader.readLine();
        }
    }

    public void cadastrarLocalidades(BufferedReader reader, BufferedWriter writer) throws IOException {
        String cep = reader.readLine();

        while (cep != null && !cep.equals("-1")) {
            String nome = reader.readLine();
            long qtdEleitores = Long.parseLong(reader.readLine());
            String tipoTexto = reader.readLine();
            TipoLocalidade tipo;

            try {
                tipo = TipoLocalidade.valueOf(tipoTexto);
            } catch (IllegalArgumentException e) {
                escrever(writer, "2: ERRO - tipo de localidade incorreto.");
                cep = reader.readLine();
                continue;
            }

            if (existe(cep)) {
                escrever(writer, "2: ERRO - localidade repetida.");
            } else {
                Localidade localidade = new Localidade(cep, nome, qtdEleitores, tipo);
                adicionarLocalidade(localidade);
                escrever(
                        writer,
                        "2: " + cep + " - " + nome + " - " + qtdEleitores
                        + " - " + tipo.getDescricao()
                );
            }

            cep = reader.readLine();
        }
    }

    public void mostrarEleitoLocalidade(
            BufferedReader reader,
            BufferedWriter writer,
            CadastroCandidatos cadastroCandidatos,
            CadastroVotos cadastroVotos) throws IOException {

        String cep = reader.readLine();
        Localidade localidade = buscarPorCep(cep);

        if (localidade == null) {
            escrever(writer, "8: ERRO - localidade inexistente.");
            return;
        }

        boolean encontrouCandidato = false;
        for (Candidato candidato : cadastroCandidatos.getCandidatos()) {
            if (candidato.getLocalidade() == localidade) {
                encontrouCandidato = true;
                break;
            }
        }

        if (!encontrouCandidato) {
            escrever(writer, "8: nenhum candidato cadastrado.");
            return;
        }

        Candidato eleito = buscarEleito(localidade, cadastroCandidatos, cadastroVotos);
        if (eleito == null) {
            escrever(writer, "8: nenhum candidato eleito.");
        } else {
            escrever(
                    writer,
                    "8: " + eleito.getNumero() + " - " + eleito.getNome()
                    + " - " + cadastroVotos.contarVotos(eleito)
            );
        }
    }

    public Candidato buscarEleito(
            Localidade localidade,
            CadastroCandidatos cadastroCandidatos,
            CadastroVotos cadastroVotos) {

        Candidato eleito = null;
        int maiorQuantidadeVotos = 0;
        boolean empate = false;

        for (Candidato candidato : cadastroCandidatos.getCandidatos()) {
            if (candidato.getLocalidade() == localidade) {
                int quantidadeVotos = cadastroVotos.contarVotos(candidato);

                if (quantidadeVotos > maiorQuantidadeVotos) {
                    maiorQuantidadeVotos = quantidadeVotos;
                    eleito = candidato;
                    empate = false;
                } else if (quantidadeVotos == maiorQuantidadeVotos
                        && quantidadeVotos > 0) {
                    empate = true;
                }
            }
        }

        return empate ? null : eleito;
    }

    private void escrever(BufferedWriter writer, String mensagem) throws IOException {
        writer.write(mensagem);
        writer.newLine();
    }
}