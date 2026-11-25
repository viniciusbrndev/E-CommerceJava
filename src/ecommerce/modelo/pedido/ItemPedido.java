package ecommerce.modelo.pedido;

import ecommerce.modelo.produto.Produto;

/** Item de um carrinho/pedido: produto + quantidade (+ preço no momento da compra). */
public class ItemPedido {

    private final Produto produto;
    private int quantidade;
    private final double precoUnitario;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        precoUnitario = produto.getPreco();
    }

    public Produto getProduto() { 
        return produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    /** @throws IllegalArgumentException se quantidade menor que 1. */
    public void setQuantidade(int quantidade) {
        if(quantidade < 1)
            throw new IllegalArgumentException("Quantidade vazia não permitida");
        this.quantidade = quantidade;
    }
    public double getPrecoUnitario() {
        return precoUnitario;
    } 

    /** @return precoUnitario * quantidade. */
    public double calcularSubtotal() { 
        return precoUnitario * quantidade;
    }

    /** @return frete variável deste item (delega ao produto: POLIMORFISMO). */
    public double calcularFreteVariavel() { 
        return produto.calcularFreteVariavel(quantidade);
    }
    @Override 
    public String toString(){
        return produto.toString() + "Quantidade: " + quantidade;
    }
}
