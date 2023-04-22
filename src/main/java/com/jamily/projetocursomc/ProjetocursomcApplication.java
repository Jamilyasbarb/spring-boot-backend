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
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));

		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "38989078967", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("128776758839", "983476586"));
		
		Endereco end1 = new Endereco(null, "Rua Vera", "700", "casa", "Jardim", "187664",cli1, c1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "18777012",cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		Pedido pedido1 = new Pedido(null, sdf.parse("20/04/2023 15:33"), cli1, end1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2023 19:35"), cli1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,pedido2,sdf.parse("20/10/2023 15:21"), null);
		pedido2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(pedido1,pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, p2, 100.00, 2, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1,itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		p1.getItens().addAll(Arrays.asList(itemPedido1));
		p2.getItens().addAll(Arrays.asList(itemPedido2));
		p3.getItens().addAll(Arrays.asList(itemPedido3));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1,itemPedido2,itemPedido3));
		
	}

}
