package model;
public class Carro extends Veiculo {
    protected void ligarMotor() {
        System.out.println("Ligando motor do carro...");
    }
    protected void mover() {
        System.out.println("Carro está rodando pelas ruas");
    }
    protected void parar() {
        System.out.println("Carro estacionado.");
    }
}