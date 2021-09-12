package drugstore;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;


@Entity
@Table(name = "clientes")
@ManagedBean(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	private transient String novoNome;
	@Transient
	public String getNovoNome() {
		return novoNome;
	}
	@Transient
	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
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
	public String cadastrar() {
		Cliente cliente = new Cliente();
		cliente.setNome(getNome());
		Gerenciador.cadastrar(cliente);
		return null;
		
	} 
	
	public String visualizar() {
		Gerenciador.visualizar();
		return null;
	}
	public String atualizar() {
		Cliente cliente = new Cliente();
		cliente.setNome(getNome());
		cliente.setNovoNome(getNovoNome());
		
		Gerenciador.atualizar(cliente.getNome(), getNovoNome());
				
		return null;
	}
	
}
