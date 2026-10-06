package dados;

import java.io.BufferedReader;
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
}