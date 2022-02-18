package br.unirio.bsi;

import java.util.ArrayList;

public abstract class Conta {
    protected String agencia = "0001";
    protected String senha, cpf, numeroDaConta;
    protected double saldo;
    protected boolean contaSalario = false;
    protected int diaPagamento;
    protected double salario;
    protected String chavePix;
    ArrayList<Extrato> extratos = new ArrayList<>();

    public Conta(String numeroDaConta, String senha, String cpf, String chavePix, int diaPagamento, double salario, boolean contaSalario) {
        this.numeroDaConta = numeroDaConta;
        this.senha = senha;
        this.cpf = cpf;
        this.chavePix = chavePix;
        this.diaPagamento = diaPagamento;
        this.salario = salario;
        this.contaSalario = contaSalario;
        this.saldo = 0;
    }

    public abstract String getNumeroDaConta();
    public abstract String getAgencia();
    public abstract String getSenha();
    public abstract String getCpf();
    public abstract double getSaldo();
    public abstract void setSaldo(double saldo);
    public abstract boolean getContaSalario();
    public abstract int getDiaPagamento();
    public abstract double getSalario();
    public abstract String getChavePix();
    public abstract ArrayList<Extrato> getExtratos();
    public abstract void setExtratos(ArrayList<Extrato> extratos);
}
