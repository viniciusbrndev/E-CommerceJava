package ecommerce.modelo.pedido;

/** Situações possíveis de um pedido. Ajustem conforme a regra definida pelo grupo. */
public enum StatusPedido {
    AGUARDANDO_PAGAMENTO,
    PAGO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    /** @return true se um pedido neste status ainda pode ser cancelado (definir a regra). */
    public boolean permiteCancelamento() {
        if (this == AGUARDANDO_PAGAMENTO || this == PAGO)
            return true;
        return false;
    }
}
