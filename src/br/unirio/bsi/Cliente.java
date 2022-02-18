package br.unirio.bsi;

public class Cliente {
    private String nome, email, dataDeNascimento, telefone, cpf;
    private boolean temContaPoupanca = false;
    private boolean temContaCorrente = false;

    public Cliente(String nome, String email, String telefone, String cpf, String dataDeNascimento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public boolean isTemContaPoupanca() {
        return temContaPoupanca;
    }

    public boolean isTemContaCorrente() {
        return temContaCorrente;
    }

    public void setTemContaPoupanca(boolean temContaPoupanca) {
        this.temContaPoupanca = temContaPoupanca;
    }

    public void setTemContaCorrente(boolean temContaCorrente) {
        this.temContaCorrente = temContaCorrente;
    }
}
