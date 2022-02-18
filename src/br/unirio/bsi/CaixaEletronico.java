package br.unirio.bsi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

public class CaixaEletronico {
    private String senha, cpf, telefone, email, numeroDaConta;
    private double valor, salario;
    private int diaPagamento;
    private final DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    HashMap<String, Cliente> clientes = new HashMap<>(); //chave: String cpf
    HashMap<String, ContaCorrente> contasCorrentes = new HashMap<>(); //chave: long numeroDaConta
    HashMap<String, ContaPoupanca> contasPoupancas = new HashMap<>(); //chave: long numeroDaConta
    HashMap<String, String> chavesPix = new HashMap<>();

    Scanner scan = new Scanner(System.in);

    public void criarPerfilDoCliente() {
        boolean contaExistente;

        do {
            contaExistente = false;

            System.out.println("-----------------------------------------------");
            System.out.println("|               Insira seus Dados             |");
            System.out.println("-----------------------------------------------");
            System.out.print("Nome: ");
            String nome = scan.nextLine();

            System.out.print("CPF: ");
            cpf = scan.nextLine();

            System.out.print("Data de Nascimento (dd/mm/aaaa): ");
            String dataDeNascimento = scan.nextLine();

            System.out.print("E-mail: ");
            email = scan.nextLine();

            System.out.print("Telefone: ");
            telefone = scan.nextLine();
            System.out.println("-----------------------------------------------");

            if (clientes.get(cpf) == null) {
                Cliente novoCliente = new Cliente(nome, email, telefone, cpf, dataDeNascimento);
                clientes.put(cpf, novoCliente);
            }
            else {
                System.out.println("Já existe uma conta com este CPF!");
                contaExistente = true;
            }
        } while (contaExistente);
    }

    public void conferirSeTemContaPoupanca() {
        if (Objects.equals(contasCorrentes.get(numeroDaConta).getCpf(), clientes.get(cpf).getCpf())) {
            if (!clientes.get(cpf).isTemContaPoupanca()) {
                criarContaPoupanca();
                boolean temContaPoupanca = true;
                clientes.get(cpf).setTemContaPoupanca(temContaPoupanca);
            }
            else {
                System.out.println("Este cliente já tem uma conta poupança!");
            }
        }
    }

    public void conferirSeTemContaCorrente() {
        if (Objects.equals(contasPoupancas.get(numeroDaConta).getCpf(), clientes.get(cpf).getCpf())) {
            if (!clientes.get(cpf).isTemContaCorrente()) {
                criarContaCorrente();
                boolean temContaCorrente = true;
                clientes.get(cpf).setTemContaPoupanca(temContaCorrente);
            }
            else {
                System.out.println("Este cliente já tem uma conta corrente!");
            }
        }
    }

    public String gerarNumeroDaConta() {
        boolean contaExistente;

        do {
            contaExistente = false;

            long numeroAleatorio = (long) (Math.random() * 100001); // 0 a 100000
            numeroDaConta = Long.toString(numeroAleatorio);

            if (contasCorrentes.get(numeroDaConta) != null || contasPoupancas.get(numeroDaConta) != null) {
                contaExistente = true;
            }
        } while (contaExistente);

        return numeroDaConta;
    }

    public String gerarChavePix() {
        boolean contaExistente;
        String chave;

        Random gerador = new Random();

        do {
            contaExistente = false;

            chave = "" + (gerador.nextInt(99999 - 10000) + 1);

            if (chavesPix.get(chave) != null) {
                contaExistente = true;
            }
        } while (contaExistente);

        return chave;
    }

    public void criarContaPoupanca() {
        numeroDaConta = gerarNumeroDaConta();
        String metodoPix = null;
        boolean eContaSalario = false;

        System.out.println("-----------------------------------------------");
        System.out.println("|              Criar Conta Poupança           |");
        System.out.println("-----------------------------------------------");
        System.out.println("Agência: 0001");
        System.out.println("Conta: " + numeroDaConta);

        System.out.print("Insira a senha: ");
        senha = scan.nextLine();

        System.out.println("Configurar como conta-salário? ");
        System.out.println("    1. Sim     |     2. Não    ");
        int contaSalario = scan.nextInt();
        scan.nextLine();

        if (contaSalario == 1) {
            eContaSalario = true;

            System.out.print("Salário: ");
            salario = scan.nextDouble();

            System.out.print("Dia de pagamento: ");
            diaPagamento = scan.nextInt();
        }

        System.out.println("Criar PIX? ");
        System.out.println("    1. Sim     |     2. Não    ");
        int criarPix = scan.nextInt();
        scan.nextLine();

        if (criarPix == 1) {
            System.out.println("Escolha a chave PIX ");
            System.out.println("    1. CPF     |     2. Telefone    |     3. E-mail    |     4. Criar chave    ");
            metodoPix = scan.nextLine();

            if (Objects.equals(metodoPix, "1")) {
                metodoPix = cpf;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "2")) {
                metodoPix = telefone;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "3")) {
                metodoPix = email;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "4")) {
                metodoPix = gerarChavePix();
                chavesPix.put(metodoPix, numeroDaConta);

                System.out.println("-----------------------------------------------");
                System.out.println("Chave PIX: " + metodoPix);
                System.out.println("-----------------------------------------------");
            }
            else {
                System.out.println("Opção inválida!");
            }
        }

        ContaPoupanca novaContaPoupanca = new ContaPoupanca(numeroDaConta, senha, cpf, metodoPix, diaPagamento, salario, eContaSalario);
        contasPoupancas.put(numeroDaConta, novaContaPoupanca);

        if (Objects.equals(contasPoupancas.get(numeroDaConta).getCpf(), clientes.get(cpf).getCpf())) {
            boolean temContaPoupanca = true;
            clientes.get(cpf).setTemContaPoupanca(temContaPoupanca);
        }

        System.out.println("\nConta poupança criada com sucesso!");
        System.out.println("-----------------------------------------------");
    }

    public void criarContaCorrente() {
        numeroDaConta = gerarNumeroDaConta();
        String metodoPix = null;
        boolean eContaSalario = false;

        System.out.println("-----------------------------------------------");
        System.out.println("|              Criar Conta Corrente           |");
        System.out.println("-----------------------------------------------");
        System.out.println("Agência: 0001");
        System.out.println("Conta: " + numeroDaConta);

        System.out.print("Insira a senha: ");
        senha = scan.nextLine();

        System.out.println("Configurar como conta-salário? ");
        System.out.println("    1. Sim     |     2. Não    ");
        int contaSalario = scan.nextInt();
        scan.nextLine();

        if (contaSalario == 1) {
            eContaSalario = true;

            System.out.print("Salário: ");
            salario = scan.nextDouble();

            System.out.print("Dia de pagamento: ");
            diaPagamento = scan.nextInt();
        }

        System.out.println("Criar PIX? ");
        System.out.println("    1. Sim     |     2. Não    ");
        int criarPix = scan.nextInt();
        scan.nextLine();

        if (criarPix == 1) {
            System.out.println("Escolha a chave PIX ");
            System.out.println("    1. CPF     |     2. Telefone    |     3. E-mail    |     4. Criar chave    ");
            metodoPix = scan.nextLine();

            if (Objects.equals(metodoPix, "1")) {
                metodoPix = cpf;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "2")) {
                metodoPix = telefone;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "3")) {
                metodoPix = email;
                chavesPix.put(metodoPix, numeroDaConta);
            }
            else if (Objects.equals(metodoPix, "4")) {
                metodoPix = gerarChavePix();
                chavesPix.put(metodoPix, numeroDaConta);

                System.out.println("-----------------------------------------------");
                System.out.println("Chave PIX: " + metodoPix);
                System.out.println("-----------------------------------------------");
            }
            else {
                System.out.println("Opção inválida!");
            }
        }

        ContaCorrente novaContaCorrente = new ContaCorrente(numeroDaConta, senha, cpf, metodoPix, diaPagamento, salario, eContaSalario);
        contasCorrentes.put(numeroDaConta, novaContaCorrente);

        if (Objects.equals(contasCorrentes.get(numeroDaConta).getCpf(), clientes.get(cpf).getCpf())) {
            boolean temContaCorrente = true;
            clientes.get(cpf).setTemContaCorrente(temContaCorrente);
        }

        System.out.println("\nConta corrente criada com sucesso!");
        System.out.println("-----------------------------------------------");
    }

    public int acessarConta() {
        int tipoDeConta = 0;
        String agencia;

        System.out.println("-----------------------------------------------");
        System.out.println("|                Acessar Conta                |");
        System.out.println("-----------------------------------------------");

        do {
            System.out.print("Agência: ");
            agencia = scan.nextLine();
        } while (!agencia.equals("0001"));

        System.out.print("Conta: ");
        numeroDaConta = scan.nextLine();

        System.out.print("Senha: ");
        senha = scan.nextLine();
        System.out.println("-----------------------------------------------");

        if (conferirContaDeAcesso() == 1) {
            System.out.println("Acesso concedido!");
            tipoDeConta = 1;
        }
        else if (conferirContaDeAcesso() == 2) {
            System.out.println("Acesso concedido!");
            tipoDeConta = 2;
        }
        else {
            System.out.println("Conta ou senha inválida!");
        }
        return tipoDeConta;
    }


    public int conferirContaDeAcesso() {
        int acessoConcedido = 0;

        if (contasCorrentes.get(numeroDaConta) != null) {
            if (Objects.equals(contasCorrentes.get(numeroDaConta).getSenha(), senha)) {
                cpf = String.valueOf(contasCorrentes.get(numeroDaConta).getCpf());
                acessoConcedido = 1;
            }

        } else if (contasPoupancas.get(numeroDaConta) != null) {
            if (Objects.equals(contasPoupancas.get(numeroDaConta).getSenha(), senha)) {
                cpf = String.valueOf(contasPoupancas.get(numeroDaConta).getCpf());
                acessoConcedido = 2;

            }
        }

        return acessoConcedido;
    }

    public boolean alterarSaldo() {
        boolean saldoAlterado = true;

        if (contasCorrentes.get(numeroDaConta) != null) {
            double saldo = contasCorrentes.get(numeroDaConta).getSaldo() + valor;
            contasCorrentes.get(numeroDaConta).setSaldo(saldo);
        } else if (contasPoupancas.get(numeroDaConta) != null) {
            double saldo = contasPoupancas.get(numeroDaConta).getSaldo() + valor;
            contasPoupancas.get(numeroDaConta).setSaldo(saldo);
        } else {
            System.out.println("Conta inexistente!");
            saldoAlterado = false;
        }

        return saldoAlterado;
    }

    public void fazerDeposito() {
        String agencia;

        System.out.println("-----------------------------------------------");
        System.out.println("|                    Depósito                  |");
        System.out.println("-----------------------------------------------");
        System.out.print("Valor: ");
        valor = scan.nextDouble();
        scan.nextLine();

        do {
            System.out.print("Agência: ");
            agencia = scan.nextLine();
        } while (!agencia.equals("0001"));

        System.out.print("Conta: ");
        numeroDaConta = scan.nextLine();

        System.out.print("Seu Telefone: ");
        String telefone = scan.nextLine();
        System.out.println("-----------------------------------------------");

        String descricao = "Depósito em caixa eletrônico.";
        String tipoOperacao = "Depósito";
        LocalDate data = LocalDate.now();

        ArrayList<Extrato> extratos;
        Extrato novoExtrato = new Extrato(valor, descricao, tipoOperacao, data);
        novoExtrato.setTelefone(telefone);

        if (alterarSaldo()) {
            System.out.println("Depósito feito com sucesso!");

            if (contasCorrentes.get(numeroDaConta) != null) {
                extratos = contasCorrentes.get(numeroDaConta).getExtratos();
                extratos.add(novoExtrato);
                contasCorrentes.get(numeroDaConta).setExtratos(extratos);
            } else if (contasPoupancas.get(numeroDaConta) != null) {
                extratos = contasPoupancas.get(numeroDaConta).getExtratos();
                extratos.add(novoExtrato);
                contasPoupancas.get(numeroDaConta).setExtratos(extratos);
            }
        }
    }

    private int validaAcesso(String conta, String agencia, String senha) {
        if (contasCorrentes.get(numeroDaConta) != null) {
            if (Objects.equals(contasCorrentes.get(numeroDaConta).getAgencia(), agencia) &&
                    Objects.equals(contasCorrentes.get(numeroDaConta).getSenha(), senha)) {
                return 1;
            }
        }

        if (contasPoupancas.get(numeroDaConta) != null) {
            if (Objects.equals(contasPoupancas.get(numeroDaConta).getAgencia(), agencia) &&
                    Objects.equals(contasPoupancas.get(numeroDaConta).getSenha(), senha)) {
                return 2;
            }
        }

        return 0;
    }

    public void fazerSaque() {
        String agencia;
        boolean sucesso = true;

        System.out.println("-----------------------------------------------");
        System.out.println("|                    Saque                    |");
        System.out.println("-----------------------------------------------");
        System.out.print("Valor: ");
        valor = scan.nextDouble();
        scan.nextLine();

        System.out.print("Agência: ");
        agencia = scan.nextLine();

        System.out.print("Conta: ");
        numeroDaConta = scan.nextLine();

        System.out.print("Senha: ");
        String senha = scan.nextLine();

        System.out.print("Seu Telefone: ");
        String telefone = scan.nextLine();
        System.out.println("-----------------------------------------------");

        int tipoConta = validaAcesso(numeroDaConta, agencia, senha);

        if (tipoConta == 1) {
            if (contasCorrentes.get(numeroDaConta).saldo - valor >= -3000) {
                contasCorrentes.get(numeroDaConta).setSaldo(valor * -1);
                System.out.println("Saque feito com sucesso!");
            }
            else {
                System.out.println("Saldo insuficiente para a operação.");
                sucesso = false;
            }
        }
        else if (tipoConta == 2) {
            if (contasPoupancas.get(numeroDaConta).saldo >= valor) {
                contasPoupancas.get(numeroDaConta).setSaldo(valor * -1);
                System.out.println("Saque feito com sucesso!");
            }
            else {
                System.out.println("Saldo insuficiente para a operação.");
                sucesso = false;
            }
        }
        else {
            System.out.println("Conta inexistente!");
            sucesso = false;
        }

        if (sucesso) {
            String descricao = "Saque em caixa eletrônico.";
            String tipoOperacao = "Saque";
            LocalDate data = LocalDate.now();

            ArrayList<Extrato> extratos;
            Extrato novaAcao = new Extrato(valor, descricao, tipoOperacao, data);
            novaAcao.setTelefone(telefone);

            if (tipoConta == 1) {
                extratos = contasCorrentes.get(numeroDaConta).getExtratos();
                extratos.add(novaAcao);
                contasCorrentes.get(numeroDaConta).setExtratos(extratos);
            }
            else {
                extratos = contasPoupancas.get(numeroDaConta).getExtratos();
                extratos.add(novaAcao);
                contasPoupancas.get(numeroDaConta).setExtratos(extratos);
            }
        }
    }


    public void verSaldo() {
        double saldo = 0;

        if (contasCorrentes.get(numeroDaConta) != null) {
            saldo = contasCorrentes.get(numeroDaConta).getSaldo();
        } else if (contasPoupancas.get(numeroDaConta) != null) {
            saldo = contasPoupancas.get(numeroDaConta).getSaldo();
        }

        System.out.printf("\nSaldo: R$ %.2f\n", saldo);
    }

    public void pagarBoleto() {
        System.out.println("-----------------------------------------------");
        System.out.println("|                Pagar Boleto                 |");
        System.out.println("-----------------------------------------------");
        System.out.print("Código de Barras: ");
        String codigoDeBarras = scan.nextLine();

        System.out.print("Valor: ");
        double valor = scan.nextDouble();

        System.out.print("Data de Vencimento (dia mes ano): ");
        int d = scan.nextInt();
        int m = scan.nextInt();
        int a = scan.nextInt();
        scan.nextLine();

        LocalDate dataDeVencimento = LocalDate.of( a , m , d );

        Boleto boleto = new Boleto(codigoDeBarras, valor, dataDeVencimento);

        double multa = boleto.calcularJuros();
        System.out.printf("\nMulta: %.2f", multa);
        valor = valor + multa;
        System.out.printf("\nValor Total: %.2f\n", valor);

        System.out.println("-----------------------------------------------");

        String descricao = "Pagamento de boleto em caixa eletrônico";
        String tipoOperacao = "Boleto";
        LocalDate data = LocalDate.now();
        valor = valor * -1;

        ArrayList<Extrato> extratos;
        Extrato novoExtrato = new Extrato(valor, descricao, tipoOperacao, data);
        novoExtrato.setCodigoDeBarras(codigoDeBarras);
        novoExtrato.setMulta(multa);
        novoExtrato.setDataDeVencimento(dataDeVencimento);


        if (contasCorrentes.get(numeroDaConta) != null) {
            if (contasCorrentes.get(numeroDaConta).getSaldo() + valor >= -3000) {
                alterarSaldo();

                extratos = contasCorrentes.get(numeroDaConta).getExtratos();
                extratos.add(novoExtrato);
                contasCorrentes.get(numeroDaConta).setExtratos(extratos);

                System.out.println("Boleto pago!");
            } else {
                System.out.println("Crédito insuficiente!");
            }

        } else if (contasPoupancas.get(numeroDaConta) != null) {
            if (contasPoupancas.get(numeroDaConta).getSaldo() < valor) {
                alterarSaldo();

                extratos = contasPoupancas.get(numeroDaConta).getExtratos();
                extratos.add(novoExtrato);
                contasPoupancas.get(numeroDaConta).setExtratos(extratos);

                System.out.println("Boleto pago!");
            } else {
                System.out.println("Saldo insuficiente!");
            }
        } else {
            System.out.println("Conta inexistente!");
        }
    }

    public void avancarTempo() {
        LocalDate dataAtual = LocalDate.now();
        int numDias = 0;
        double montante = 0;

        System.out.println("-----------------------------------------------");
        System.out.println("|               Avançar no Tempo              |");
        System.out.println("-----------------------------------------------");

        do {
            System.out.print("Número de dias: ");
            numDias += scan.nextInt();

            int tipoConta = validaAcesso(numeroDaConta, "0001", senha);

            if (tipoConta == 1) {
                montante = contasCorrentes.get(numeroDaConta).getSaldo();

                if (contasCorrentes.get(numeroDaConta).getContaSalario()) {
                    for (int contador = 1; contador <= numDias; contador++) {
                        LocalDate dataProjetada = dataAtual.plusDays(contador);

                        if (dataProjetada.getDayOfMonth() == contasCorrentes.get(numeroDaConta).getDiaPagamento()) {
                            montante += contasCorrentes.get(numeroDaConta).getSalario();
                        }
                    }
                }

                LocalDate novaData = dataAtual.plusDays(numDias);
                String dataProjetada = novaData.format(dt);

                System.out.println("-----------------------------------------------");
                System.out.println("SIMULAÇÃO PARA O DIA " + dataProjetada);
                System.out.println("\nSaldo: " + (contasCorrentes.get(numeroDaConta).getSaldo() + montante));
                System.out.println("-----------------------------------------------");
            } else {
                montante = contasPoupancas.get(numeroDaConta).getSaldo();

                if (contasPoupancas.get(numeroDaConta).getContaSalario()) {
                    for (int contador = 1; contador <= numDias; contador++) {
                        LocalDate dataProjetada = dataAtual.plusDays(contador);

                        if (dataProjetada.getDayOfMonth() == contasPoupancas.get(numeroDaConta).getDiaPagamento()) {
                            montante += contasPoupancas.get(numeroDaConta).getSalario();
                        }

                        if (contador % 30 == 0) {
                            montante = montante * 1.003;
                        }
                    }
                }
                else {
                    for (int contador = 1; contador <= numDias; contador++) {
                        if (contador % 30 == 0) {
                            montante = montante * 1.003;
                        }
                    }
                }

                LocalDate novaData = dataAtual.plusDays(numDias);
                String dataProjetada = novaData.format(dt);

                System.out.println("-----------------------------------------------");
                System.out.println("SIMULAÇÃO PARA O DIA " + dataProjetada);
                System.out.println("\nSaldo: " + montante);
                System.out.println("-----------------------------------------------");
            }

            System.out.println("Deseja continuar? (s/n)");
            String opcao = scan.next();
            scan.nextLine();

            if (Objects.equals(opcao, "n")) {
                break;
            }
        } while (true);
    }

    public void fazerTransferencia() {
        boolean sucesso = true;
        String agencia;

        String descricao = "Transferência bancária.";
        String tipoOperacao = "Transferência";
        LocalDate data = LocalDate.now();
        ArrayList<Extrato> extratos;

        System.out.println("-----------------------------------------------");
        System.out.println("|                Transferência                |");
        System.out.println("-----------------------------------------------");
        System.out.print("Valor: ");
        valor = scan.nextDouble();
        scan.nextLine();

        do {
            System.out.print("Agência da conta de destino: ");
            agencia = scan.nextLine();
        } while (!agencia.equals("0001"));

        System.out.print("Número da conta de destino: ");
        String conta = scan.nextLine();

        int tipoConta = validaAcesso(numeroDaConta, "0001", senha);

        if (tipoConta == 1) {
            if (contasCorrentes.get(numeroDaConta).getSaldo() - valor >= -3000) {
                sucesso = false;
            }
        }

        if (tipoConta == 2) {
            if (contasCorrentes.get(numeroDaConta).getSaldo() < valor) {
                sucesso = false;
            }
        }

        if (contasCorrentes.get(conta) != null || contasPoupancas.get(conta) != null) {
            Extrato novaAcao = new Extrato(valor, descricao, tipoOperacao, data);
            Extrato novaAcaoNegativa = new Extrato(valor * -1, descricao, tipoOperacao, data);
            novaAcao.setContaDestinataria(numeroDaConta);
            novaAcao.setContaDeOrigem(conta);

            if (tipoConta == 1) {
                contasCorrentes.get(numeroDaConta).setSaldo(valor * -1);

                extratos = contasCorrentes.get(numeroDaConta).getExtratos();
                extratos.add(novaAcaoNegativa);
                contasCorrentes.get(numeroDaConta).setExtratos(extratos);
            }
            else {
                contasPoupancas.get(numeroDaConta).setSaldo(valor * -1);

                extratos = contasPoupancas.get(numeroDaConta).getExtratos();
                extratos.add(novaAcaoNegativa);
                contasPoupancas.get(numeroDaConta).setExtratos(extratos);
            }

            if (contasCorrentes.get(conta) != null) {
                contasCorrentes.get(conta).setSaldo(valor);

                extratos = contasCorrentes.get(conta).getExtratos();
                extratos.add(novaAcao);
                contasCorrentes.get(conta).setExtratos(extratos);
            }
            else {
                contasPoupancas.get(conta).setSaldo(valor);

                extratos = contasPoupancas.get(conta).getExtratos();
                extratos.add(novaAcao);
                contasPoupancas.get(conta).setExtratos(extratos);
            }

            System.out.println("Transferência bancária realizada com sucesso!");
        }
        else {
            System.out.println("Conta de destino não encontrada!");
        }
    }

    public void fazerPix() {
        boolean sucesso = true;

        System.out.println("-----------------------------------------------");
        System.out.println("|                     Pix                     |");
        System.out.println("-----------------------------------------------");
        System.out.print("Valor: ");
        valor = scan.nextDouble();
        scan.nextLine();

        int tipoConta = validaAcesso(numeroDaConta, "0001", senha);

        if (tipoConta != 0) {
            if (tipoConta == 1) {
                if (contasCorrentes.get(numeroDaConta).saldo - valor >= -3000) {
                    sucesso = false;
                }
            }
            else if (tipoConta == 2) {
                if (contasPoupancas.get(numeroDaConta).saldo < valor) {
                    sucesso = false;
                }
            }

            if (sucesso) {
                System.out.println("Chave pix do destinatário:");
                String chavePix = scan.nextLine();

                if (chavesPix.get(chavePix) != null) {
                    String conta = chavesPix.get(chavePix);
                    System.out.println(conta);

                    String descricao = "Transferência por Pix.";
                    String tipoOperacao = "Pix";
                    LocalDate data = LocalDate.now();

                    ArrayList<Extrato> extratos;
                    Extrato novaAcao = new Extrato(valor, descricao, tipoOperacao, data);
                    Extrato novaAcaoNegativa = new Extrato(valor * -1, descricao, tipoOperacao, data);
                    novaAcao.setContaDestinataria(numeroDaConta);
                    novaAcao.setContaDeOrigem(conta);

                    if (contasCorrentes.get(conta) != null) {
                        contasCorrentes.get(conta).setSaldo(valor);

                        extratos = contasCorrentes.get(conta).getExtratos();
                        extratos.add(novaAcao);
                        contasCorrentes.get(conta).setExtratos(extratos);
                    }
                    else {
                        contasPoupancas.get(conta).setSaldo(valor);

                        extratos = contasPoupancas.get(conta).getExtratos();
                        extratos.add(novaAcao);
                        contasPoupancas.get(conta).setExtratos(extratos);
                    }

                    if (tipoConta == 1) {
                        contasCorrentes.get(numeroDaConta).setSaldo(valor * -1);

                        extratos = contasCorrentes.get(numeroDaConta).getExtratos();
                        extratos.add(novaAcaoNegativa);
                        contasCorrentes.get(numeroDaConta).setExtratos(extratos);
                    }
                    else {
                        contasPoupancas.get(numeroDaConta).setSaldo(valor * -1);

                        extratos = contasPoupancas.get(numeroDaConta).getExtratos();
                        extratos.add(novaAcaoNegativa);
                        contasPoupancas.get(numeroDaConta).setExtratos(extratos);
                    }

                    System.out.println("Transferência Pix realizada com sucesso!");
                }
                else {
                    System.out.println("Conta de destino não encontrada!");
                }
            }
            else {
                System.out.println("Saldo insuficiente!");
            }
        }
        else {
            System.out.println("Conta inexistente!");
        }
    }

    public void gerarExtratos() {
        ArrayList<Extrato> extratos = new ArrayList<>();

        if (contasCorrentes.get(numeroDaConta) != null) {
            extratos = contasCorrentes.get(numeroDaConta).getExtratos();
        } else if (contasPoupancas.get(numeroDaConta) != null) {
            extratos = contasPoupancas.get(numeroDaConta).getExtratos();
        }

        if(!extratos.equals(null)) {
            System.out.println("Posição         Data      Valor      Operação      Descrição");
//      System.out.println("   1.        aaaa-mm-dd   XXX,XX     Depósito      Depósito no caixa eletrônico");
            for (int i = 0; i < extratos.size(); i++) {
                System.out.println(i+1 + ".           " + extratos.get(i).getData() + "   " + extratos.get(i).getValor() + "      " + extratos.get(i).getTipoOperacao() + "      " + extratos.get(i).getDescricao());
            }

            System.out.println("\n1. Mostrar detalhes | 2. Voltar");
            int option = scan.nextInt();

            if(option == 1){
                System.out.println("Insira o número da posição: ");
                int posicao = scan.nextInt() - 1;

                if (extratos.get(posicao).getTipoOperacao().equals("Depósito") ||     extratos.get(posicao).getTipoOperacao().equals("Saque")) {
                    System.out.println("Telefone: " + extratos.get(posicao).getTelefone());

                } else if (extratos.get(posicao).getTipoOperacao().equals("Transferência") || extratos.get(posicao).getTipoOperacao().equals("Pix")) {
                    if (extratos.get(posicao).getValor() < 0) {
                        System.out.println("Conta Destinatária");
                        System.out.println("Agência: 0001");
                        System.out.println("Conta: " + extratos.get(posicao).getContaDestinataria());

                    } else {
                        System.out.println("Conta de origem: ");
                        System.out.println("Agência: 0001");
                        System.out.println("Conta: "+ extratos.get(posicao).getContaDeOrigem());
                    }

                } else if (extratos.get(posicao).getTipoOperacao().equals("Boleto")) {
                    System.out.println("Código de barras: " + extratos.get(posicao).getCodigoDeBarras());
                    System.out.println("Data de Vencimento: " + extratos.get(posicao).getDataDeVencimento());
                    System.out.println("Multa: " + extratos.get(posicao).getMulta());
                }
            }

            if(option == 2){
                return;
            }
        }
    }
}
