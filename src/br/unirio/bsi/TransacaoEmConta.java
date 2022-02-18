package br.unirio.bsi;

import java.time.LocalDate;

public interface TransacaoEmConta {
    public double getValor();
    public String getDescricao();
    public String getTipoOperacao();
    public LocalDate getData();
}
