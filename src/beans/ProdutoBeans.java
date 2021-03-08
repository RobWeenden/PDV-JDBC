package beans;

/**
 * Classe ProdutoBeans
 * Classe que provê o modelo de Objeto
 * @author ritim
 *
 */
public class ProdutoBeans {
	
	private Long id;
	private String nome;
	private Double quantidade;
	private Double valor;
	private Long categoria_id;
	
	
	
	public Long getCategoria_id() {
		return categoria_id;
	}
	public void setCategoria_id(Long categoria_id) {
		this.categoria_id = categoria_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	/**
	 * Metodo getValorEmTexto()
	 * Responsavel em substituir o ponto e virgula do valor
	 * No momento da atualização do Produto
	 * @return
	 */
	public String getValorEmTexto() {
		return Double.toString(valor).replace('.', ',');
	}

}
