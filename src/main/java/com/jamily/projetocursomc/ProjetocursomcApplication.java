package com.jamily.projetocursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jamily.projetocursomc.domain.Categoria;
import com.jamily.projetocursomc.domain.Cidade;
import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.domain.Endereco;
import com.jamily.projetocursomc.domain.Estado;
import com.jamily.projetocursomc.domain.ItemPedido;
import com.jamily.projetocursomc.domain.Pagamento;
import com.jamily.projetocursomc.domain.PagamentoComBoleto;
import com.jamily.projetocursomc.domain.PagamentoComCartao;
import com.jamily.projetocursomc.domain.Pedido;
import com.jamily.projetocursomc.domain.Produto;
import com.jamily.projetocursomc.domain.enums.EstadoPagamento;
import com.jamily.projetocursomc.domain.enums.TipoCliente;
import com.jamily.projetocursomc.repositories.CategoriaRepository;
import com.jamily.projetocursomc.repositories.CidadeRepository;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.repositories.EnderecoRepository;
import com.jamily.projetocursomc.repositories.EstadoRepository;
import com.jamily.projetocursomc.repositories.ItemPedidoRepository;
import com.jamily.projetocursomc.repositories.PagamentoRepository;
import com.jamily.projetocursomc.repositories.PedidoRepository;
import com.jamily.projetocursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetocursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetocursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
	}

}
