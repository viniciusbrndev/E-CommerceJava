package ecommerce.modelo.pagamento;

/** Pix: 5% de desconto sobre o total do pedido. */
public class Pix extends FormaPagamento {
    @Override public double calcularTotal(double subtotal, double frete) { 
        return (subtotal + frete)*0.95;
    }
    @Override public boolean pagamentoImediato() { 
        return true;
    }
    @Override public String getDescricao() {
        return "Pagamento por Pix";
    }
    @Override public String getTipo() {
        return "PIX";
    }
    @Override public String parametrosParaTexto() {
        return "";
    }
}
