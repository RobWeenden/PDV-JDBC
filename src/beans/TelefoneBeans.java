package beans;

/**
 * Classe TelefoneBeans
 * Classe que provê o modelo de Objeto
 * @author ritim
 *
 */
public class TelefoneBeans {
	private Long id;
	private String numero;
	private String tipo;
	private Long usuario;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	
	

}
