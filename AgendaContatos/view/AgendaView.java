package view;
import controller.AgendaController;
import java.util.ArrayList;
import java.util.Scanner;
import model.Contato;

public class AgendaView {
    @SuppressWarnings("FieldMayBeFinal")
    private AgendaController controller;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    public AgendaView(AgendaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== Agenda de Contatos ===");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Listar contatos");
            System.out.println("3 - Editar contato por nome");
            System.out.println("4 - Remover contato por nome");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> adicionarContato();
                case 2 -> listarContatos();
                case 3 -> alterarContato();
                case 4 -> excluirContato();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }


    public void adicionarContato(){
        String nome,telefone;
        
        System.out.println("\n\n\n");
        System.out.println("Digite o nome do contato: ");
        nome = scanner.nextLine();
        System.out.println("Digite o telefone do contato: ");
        telefone = scanner.nextLine();

        controller.adicionarContato(nome, telefone);

    }

    public void listarContatos(){
        ArrayList<Contato> contatos = new ArrayList<>(controller.listarContatos());

        for (int i = 0; i < contatos.size(); i++) {
            System.out.println((i + 1) + ". " + contatos.get(i));
        }
    }

    public void alterarContato(){
        String nome;

        System.out.println("\n\n\n");

        System.out.println("Digite o nome do contato que deseja apagar: ");
        nome = scanner.nextLine();

        ArrayList<Contato> contatos = new ArrayList<>(controller.buscarPorNome(nome));

        if (contatos.isEmpty()) {
            System.out.println("Contato não encontrado!");
            return;
        }
        for (int i = 0; i < contatos.size(); i++) {
            System.out.println((i + 1) + ". " + contatos.get(i));
        }

        System.out.print("Escolha qual deseja apagar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > contatos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Contato contato = contatos.get(escolha - 1);
        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo número: ");
        String novoNumero = scanner.nextLine();

        if (controller.alterarContato(contato, novoNome, novoNumero)){
            System.out.println("Alteração bem-sucedida");
        }else{
            System.out.println("Falha ao alterar");
        }

    }

    public void excluirContato(){
        String nome;

        System.out.println("\n\n\n");

        System.out.println("Digite o nome do contato que deseja apagar: ");
        nome = scanner.nextLine();

        ArrayList<Contato> contatos = new ArrayList<>(controller.buscarPorNome(nome));

        if (contatos.isEmpty()) {
            System.out.println("Contato não encontrado!");
            return;
        }
        for (int i = 0; i < contatos.size(); i++) {
            System.out.println((i + 1) + ". " + contatos.get(i));
        }

        System.out.print("Escolha qual deseja editar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > contatos.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Contato contato = contatos.get(escolha - 1);

        if (controller.excluirContato(contato)){
            System.out.println("Exclusão bem-sucedida");
        }else{
            System.out.println("Falha ao excluir");
        }
    }
}