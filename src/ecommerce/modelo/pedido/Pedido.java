package ecommerce.modelo.pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.excecao.PedidoInvalidoException;
import ecommerce.modelo.pagamento.FormaPagamento;
import ecommerce.modelo.usuario.Cliente;

/** Pedido finalizado por um cliente. Concentra as regras de frete e total. */
public class Pedido {

    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    public Pedido(int id, Cliente cliente, List<ItemPedido> itens, FormaPagamento formaPagamento) {
        setId(id); setCliente(cliente); setItens(itens);
        setFormaPagamento(formaPagamento);
    }

    /** @return soma dos subtotais dos itens. */
    public double calcularSubtotal() {
        double sum = 0;
        for (ItemPedido item : itens) {
            sum += item.calcularSubtotal();
        }
        return sum;
    }

    /**
     * Frete do pedido: R$ 5,00 fixos (se houver item com entrega) + soma dos fretes variáveis;
     * grátis se subtotal >= R$ 300,00; zero se só houver produtos digitais.
     */
    public double calcularFrete() { 
        double sum = 0;
        for(ItemPedido item : itens){
            sum += item.calcularFreteVariavel();
        }
        if(sum > 0)
            sum+=5;
        return sum;
    }

    /** @return valor final = formaPagamento.calcularTotal(subtotal, frete). */
    public double calcularTotal() { 
        return formaPagamento.calcularTotal(this.calcularSubtotal(), this.calcularFrete());
    }

    /** Cancela o pedido, se a regra de status permitir. */
    public void cancelar() throws PedidoInvalidoException { 
        if(status.permiteCancelamento()){
            setStatus(StatusPedido.CANCELADO);
        }else{
            throw new PedidoInvalidoException("Pedido não pode ser cancelado!");
        }
    }

    /** @return resumo textual (itens, quantidades, valores e pagamento) para exibir na finalização. */
    public String gerarResumo() { 
        return itens.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public int getId() { 
        return id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public List<ItemPedido> getItens() {
        return itens;
    }
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public StatusPedido getStatus() {
        return status;
    }
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String paraLinhaTexto() {
        return id + ';' + cliente.toString() + ';' +formaPagamento.toString()+ ';' + status.toString()+ ';' + dataCriacao.toString() + ';' + gerarResumo() + ';';
    }

    public final void setId(int id) {
        if(id < 0)
            throw new IllegalArgumentException("ID inválido");
        this.id = id;
    }

    public final void setCliente(Cliente cliente) {
        if(cliente == null)
            throw new IllegalArgumentException("Cliente inválido");
        this.cliente = cliente;
    }

    public final void setItens(List<ItemPedido> itens) {
        if(itens == null)
            throw new IllegalArgumentException("Vetor nulo não permitido");
        this.itens = itens;
    }

    public final  void setFormaPagamento(FormaPagamento formaPagamento){
        if(formaPagamento == null)
            throw new IllegalArgumentException("Forma de pagamento inválida");
        this.formaPagamento = formaPagamento;
    }

    public final void setDataCriacao(LocalDateTime dataCriacao) {
        if(dataCriacao == null)
            throw new IllegalArgumentException("Data inválida!");
        this.dataCriacao = dataCriacao;
    }
}
