import AgendaContatos.controller.AgendaController;
import AgendaContatos.view.AgendaView;

public class principal {
    public static void main(String[] args) {
        AgendaController controller = new AgendaController();
        AgendaView view = new AgendaView(controller);
        view.exibirMenu();
    }
}