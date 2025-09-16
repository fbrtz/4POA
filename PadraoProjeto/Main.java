import model.Caminhao;
import model.Carro;
import model.Garagem;
import model.Moto;
import model.Veiculo;
import model.VeiculoIterador;

public class Main {
    public static void main(String[] args) {
        Garagem garagem = new Garagem();

        garagem.adicionar(new Carro());
        garagem.adicionar(new Caminhao());
        garagem.adicionar(new Moto());

        VeiculoIterador it = garagem.iterador();
        while (it.hasNext()) {
            Veiculo v = it.next();
            v.dirigir(); 
        }
    }
}