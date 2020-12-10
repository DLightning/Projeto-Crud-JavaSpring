package edu.ieso.projeto.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.ieso.projeto.Model.Enums.PedidoStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        private Instant moment ;

        private Integer pedidoStatus;

        private int quantidade;

        @ManyToOne
        @JoinColumn(name = "client_id")
        private Cliente cliente;

        @ManyToOne
        @JoinColumn(name = "produto_id", referencedColumnName = "id")
        private Produto produto;

        public Pedido(){

        }

        public Pedido(Long id, Instant moment, PedidoStatus pedidoStatus,int quantidade, Cliente cliente) {
            this.id = id;
            this.moment = moment;
            setPedidoStatus(pedidoStatus);
            this.quantidade = quantidade;
            this.cliente = cliente;

        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Instant getMoment() {
            return moment;
        }

        public void setMoment(Instant moment) {
            this.moment = moment;
        }

        public PedidoStatus getPedidoStatus() {
            return PedidoStatus.valueOf(pedidoStatus);
        }

        public void setPedidoStatus(PedidoStatus pedidoStatus) {
            if (pedidoStatus != null) {
                this.pedidoStatus = pedidoStatus.getCode();
            }
        }


    public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

    @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pedido other = (Pedido) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
    }
}
