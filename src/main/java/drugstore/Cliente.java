package drugstore;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
@ManagedBean(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
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
	
}
