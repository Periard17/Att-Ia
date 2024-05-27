
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do trabalhador:");
        String nome = scanner.nextLine();

        System.out.println("Digite o CPF do trabalhador (somente números):");
        String cpf = scanner.nextLine();

        System.out.println("Digite o CEP do trabalhador:");
        String cep = scanner.nextLine();

        System.out.println("Digite o salário bruto do trabalhador:");
        double salarioBruto = scanner.nextDouble();

        System.out.println("Digite o número de dependentes do trabalhador:");
        int nrDeDependentes = scanner.nextInt();

        Trabalhador trabalhador = new Trabalhador(nome, cpf, cep, salarioBruto, nrDeDependentes);

        System.out.println("Nome: " + trabalhador.getNome());
        System.out.println("CPF: " + trabalhador.getCpf());
        System.out.println("CEP: " + trabalhador.getCep());
        System.out.println("Endereço: " + trabalhador.enderecoCompleto);
        System.out.println("Salário Bruto: " + trabalhador.getSalarioBruto());
        System.out.println("INSS: " + trabalhador.getInss());
        System.out.println("IRRF: " + trabalhador.getIrrf());
        System.out.println("Salário Líquido: " + trabalhador.getSalarioLiquido());

        scanner.close();
    }
}
