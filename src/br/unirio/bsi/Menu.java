package br.unirio.bsi;

import java.util.Scanner;

public class Menu {
    CaixaEletronico caixaEletronico = new CaixaEletronico();
    Scanner scan = new Scanner(System.in);

    public void selecionarProcedimento() {
        int acao;

        do {
            System.out.println("-----------------------------------------------");
            System.out.println("|               Menu Principal                |");
            System.out.println("-----------------------------------------------");
            System.out.println("|  1. Criar Conta       |  6. Transferencia   |");
            System.out.println("|  2. Deposito          |  7. Pix             |");
            System.out.println("|  3. Saque             |  8. Simulação       |");
            System.out.println("|  4. Ver Saldo         |  9. Gerar Extratos  |");
            System.out.println("|  5. Pagar Boleto      |                     |");
            System.out.println("|---------------------------------------------|");
            System.out.println("|                 10. ENCERRAR                |");
            System.out.println("-----------------------------------------------");

            acao = scan.nextInt();
            int tipoDeConta;

            switch (acao) {
                case 1: acessarOuCriarConta();
                    break;
                case 2: caixaEletronico.fazerDeposito();
                    break;
                case 3: caixaEletronico.fazerSaque();
                    break;
                case 4: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.verSaldo();
                    }
                    break;
                case 5: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.pagarBoleto();
                    }
                    break;
                case 6: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.fazerTransferencia();
                    }
                    break;
                case 7: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.fazerPix();
                    }
                    break;
                case 8: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.avancarTempo();
                    }
                    break;
                case 9: tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1 || tipoDeConta == 2) {
                        caixaEletronico.gerarExtratos();
                    }
                    break;
                case 10:
                    break;
                default:
                    System.out.println("Tente novamente.");
            }
        } while (acao != 10);
    }

    public void acessarOuCriarConta() {
        int acao;

        do {
            System.out.println("-----------------------------------------------");
            System.out.println("|       Você já tem algum tipo de conta?      |");
            System.out.println("-----------------------------------------------");
            System.out.println("|         1. Sim        |       2. Não        |");
            System.out.println("|---------------------------------------------|");
            System.out.println("|                  3. VOLTAR                  |");
            System.out.println("-----------------------------------------------");

            acao = scan.nextInt();

            switch (acao) {
                case 1: int tipoDeConta = caixaEletronico.acessarConta();
                    if (tipoDeConta == 1) {
                        caixaEletronico.conferirSeTemContaPoupanca();
                    } else if (tipoDeConta == 2) {
                        caixaEletronico.conferirSeTemContaCorrente();
                    }
                    acao = 3;
                    break;
                case 2: escolherTipoDeConta();
                    acao = 3;
                    break;
                case 3: selecionarProcedimento();
                    break;
                default:
                    System.out.println("Tente novamente.");
            }
        } while (acao != 3);
    }

    public void escolherTipoDeConta() {
        int acao;

        do {
            System.out.println("-----------------------------------------------");
            System.out.println("|           Escolha o Tipo de Conta           |");
            System.out.println("-----------------------------------------------");
            System.out.println("|  1. Conta Poupança   |   2. Conta Corrente  |");
            System.out.println("|---------------------------------------------|");
            System.out.println("|                 3. VOLTAR                   |");
            System.out.println("-----------------------------------------------");

            acao = scan.nextInt();

            switch (acao) {
                case 1: caixaEletronico.criarPerfilDoCliente();
                    caixaEletronico.criarContaPoupanca();
                    acao = 3;
                    break;
                case 2: caixaEletronico.criarPerfilDoCliente();
                    caixaEletronico.criarContaCorrente();
                    acao = 3;
                    break;
                case 3:
                    selecionarProcedimento();
                    break;
                default:
                    System.out.println("Tente novamente.");
            }
        } while (acao != 3);
    }
}
