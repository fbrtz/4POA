package model;

import java.util.ArrayList;
import java.util.List;

// Coleção de veículos
public class Garagem {
    private List<Veiculo> veiculos = new ArrayList<>();

    public void adicionar(Veiculo v) {
        veiculos.add(v);
    }

    // criando o iterator
    public VeiculoIterador iterador() {
        return new VeiculoIterador(veiculos);
    }
}