package model;
public class Caminhao extends Veiculo {
    protected void ligarMotor() {
        System.out.println("Ligando motor pesado do caminhão...");
    }
    protected void mover() {
        System.out.println("Caminhão transportando carga");
    }
    protected void parar() {
        System.out.println("Caminhão parou no posto de abastecimento.");
    }
}
