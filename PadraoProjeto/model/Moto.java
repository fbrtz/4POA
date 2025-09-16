package model;
public class Moto extends Veiculo {
    protected void ligarMotor() {
        System.out.println("Girando a chave e ligando a moto...");
    }
    protected void mover() {
        System.out.println("Moto acelerando entre os carros");
    }
    protected void parar() {
        System.out.println("Moto estacionada na calçada.");
    }
}