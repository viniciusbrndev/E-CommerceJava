package ecommerce.modelo.pagamento;

/** Boleto: sem desconto e sem juros; pedido fica aguardando confirmação. */
public class BoletoBancario extends FormaPagamento {
    @Override public double calcularTotal(double subtotal, double frete) {
        return subtotal + frete;
    }
    @Override public boolean pagamentoImediato() {
        return false;
    }
    @Override public String getDescricao() {
        return "Boleto Bancário";
    }
    @Override public String getTipo() { 
        return "BOLETO";
    }
    @Override public String parametrosParaTexto() {
        return "";
    }
    
}
