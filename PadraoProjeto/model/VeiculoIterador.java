package model;

import java.util.List;

public class VeiculoIterador {
    private List<Veiculo> veiculos;
    private int posicao = 0;

    public VeiculoIterador(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public boolean hasNext() {
        return posicao < veiculos.size();
    }

    public Veiculo next() {
        return veiculos.get(posicao++);
    }
}