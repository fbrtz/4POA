package model;
public abstract class Veiculo {
    // Template Method (não pode ser sobrescrito)
    public final void dirigir() {
        ligarMotor();
        mover();
        parar();
        System.out.println();
    }

    // Passos variáveis que cada veículo implementa
    protected abstract void ligarMotor();
    protected abstract void mover();
    protected abstract void parar();
}