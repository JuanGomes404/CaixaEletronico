package br.unirio.bsi;

import java.time.LocalDate;

public class Extrato implements TransacaoEmConta {
    private double valor;
    private String descricao;
    private String tipoOperacao;
    private LocalDate data;

    //detalhes
    private String telefone;
    private String codigoDeBarras;
    private double multa;
    private LocalDate dataDeVencimento;
    private String contaDestinataria;
    private String contaDeOrigem;

    public Extrato(double valor, String descricao, String tipoOperacao, LocalDate data) {
        this.valor = valor;
        this.descricao = descricao;
        this.tipoOperacao = tipoOperacao;
        this.data = data;
    }

    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getTipoOperacao() {
        return tipoOperacao;
    }

    @Override
    public LocalDate getData() {
        return data;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getContaDeOrigem() {
        return contaDeOrigem;
    }

    public String getContaDestinataria() {
        return contaDestinataria;
    }

    public void setContaDestinataria(String numeroConta) {
        this.contaDestinataria = numeroConta;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public double getMulta() {
        return multa;
    }

    public LocalDate getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setContaDestinaria(String contaDestinataria) {
        this.contaDestinataria = contaDestinataria;
    }

    public void setContaDeOrigem(String contaDeOrigem) {
        this.contaDeOrigem = contaDeOrigem;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public void setDataDeVencimento(LocalDate dataDeVencimento) {
        this.dataDeVencimento = dataDeVencimento;
    }
}
