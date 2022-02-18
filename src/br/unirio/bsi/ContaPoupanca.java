package br.unirio.bsi;

import java.util.ArrayList;

public class ContaPoupanca extends Conta {
    public ContaPoupanca(String numeroDaConta, String senha, String cpf, String chavePix, int diaPagamento, double salario, boolean contaSalario) {
        super(numeroDaConta, senha, cpf, chavePix, diaPagamento, salario, contaSalario);
    }

    @Override
    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    @Override
    public String getAgencia() {
        return agencia;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(double saldo) {
        this.saldo += saldo;
    }

    @Override
    public boolean getContaSalario() {
        return contaSalario;
    }

    @Override
    public int getDiaPagamento() {
        return diaPagamento;
    }

    @Override
    public double getSalario() {
        return salario;
    }

    @Override
    public String getChavePix() {
        return chavePix;
    }

    @Override
    public ArrayList<Extrato> getExtratos() {
        return extratos;
    }

    @Override
    public void setExtratos(ArrayList<Extrato> extratos) {
        this.extratos = extratos;
    }
}
