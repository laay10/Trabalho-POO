package dados;

import java.io.BufferedReader;
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
}