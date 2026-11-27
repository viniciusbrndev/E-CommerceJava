package ecommerce.modelo.pagamento;


/** Cartão: até 6x; sem juros até 3x; a partir da 4ª parcela, juros de 2% ao mês. */
public class CartaoCredito extends FormaPagamento {

    private int parcelas;

    /** @throws IllegalArgumentException se parcelas fora do intervalo 1..6. */
    public CartaoCredito(int parcelas) {
        setParcelas(parcelas);
    }

    public int getParcelas() { 
        return parcelas;
    }
    public final void setParcelas(int parcelas) {
        if(parcelas <= 0 || parcelas > 6)
            throw new IllegalArgumentException();
        this.parcelas = parcelas;
    }

    /** @return valor de cada parcela (total / parcelas). */
    public double calcularValorParcela(double subtotal, double frete) {
        return calcularTotal(subtotal, frete)/ parcelas;
    }

    @Override public double calcularTotal(double subtotal, double frete) { 
        double total = subtotal + frete;
        if (parcelas > 3)
            total *= Math.pow(1.02, parcelas - 3);
        return total;
    }
    @Override public boolean pagamentoImediato() { 
        return true;
    }
    @Override public String getDescricao() { 
        return "Cartão de crédito: " + getParcelas() + " - Parcelas;";
    }
    @Override public String getTipo() {
        return "CARTÃO";
    }
    @Override public String parametrosParaTexto() {
        return String.format(";%d", parcelas);
    }
}
