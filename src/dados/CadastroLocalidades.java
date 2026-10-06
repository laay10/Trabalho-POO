package dados;

import java.io.BufferedReader;
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
}