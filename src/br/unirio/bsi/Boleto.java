package br.unirio.bsi;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class Boleto {
    private String codigoDeBarras;
    private double valor;
    private LocalDate dataDeVencimento;

    public Boleto(String codigoDeBarras, double valor, LocalDate dataDeVencimento) {
        this.codigoDeBarras = codigoDeBarras;
        this.valor = valor;
        this.dataDeVencimento = dataDeVencimento;
    }

    public double calcularJuros() {
        double acrescimo;
        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(dataDeVencimento)) {
            long diasAtrasados = DAYS.between(dataDeVencimento, hoje);
            double juros = diasAtrasados * 0.001;
            acrescimo = valor * juros;
        }
        else {
            acrescimo = 0;
        }

        return acrescimo;
    }
}
