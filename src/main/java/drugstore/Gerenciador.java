package drugstore;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;


@ManagedBean(name = "gerenciador")
public class Gerenciador {
	private static EntityManager gerenciador;
	
	public static void cadastrar(Cliente cliente) {
		try {
			gerenciador = JpaUtil.getEntityManager();
			gerenciador.getTransaction().begin();
			gerenciador.persist(cliente);
			gerenciador.getTransaction().commit();
			System.out.println("Cadastrado com sucesso!");
			
		} catch(Exception e) {
			gerenciador.getTransaction().rollback();
		} finally { 
			gerenciador.close();
			
		}
		
	}
	public static void visualizar() {
		String jpql = "select c from Cliente c";
		List<Cliente> consulta;
		
		try {
			gerenciador = JpaUtil.getEntityManager();
			gerenciador.getTransaction().begin();
			consulta = gerenciador.createQuery(jpql, Cliente.class).getResultList();
			
			for (Cliente cliente : consulta) {
				System.out.println(cliente.getNome());
			}
			
			
		} catch(Exception e) {
			gerenciador.getTransaction().rollback();
			System.out.println("entrei no exception");
			e.printStackTrace();
		} finally {
			gerenciador.close();
		}
	}
	public static Cliente consultaPorId(Long id) {
			
		try {
			gerenciador = JpaUtil.getEntityManager();
			gerenciador.getTransaction().begin();
			Cliente cliente = gerenciador.find(Cliente.class, id);
			System.out.println(cliente.getNome());
			return(cliente);
			
		} catch (Exception e) {
			gerenciador.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			gerenciador.close();
		}	
	}
	
	public static List<Cliente> consultaPorNome(String nome) {
		String jpql = "select c from Cliente c where c.nome =:nome";
		List<Cliente> consulta;
		try {
			gerenciador = JpaUtil.getEntityManager();
			gerenciador.getTransaction().begin();
			consulta = gerenciador.createQuery(jpql, Cliente.class).setParameter("nome", nome).getResultList();
			
			for (Cliente cliente : consulta) {
				System.out.println("ID: " + cliente.getId() + " Nome: " +cliente.getNome());
			}
			return consulta;
		} catch(Exception e) {
			gerenciador.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			gerenciador.clear();
			
		}
	}
	public static void atualizar(String nome, String novoNome) {
		List<Cliente> clientes = Gerenciador.consultaPorNome(nome);
		if(clientes.isEmpty()) {
			System.out.println("Nenhum registro encontrado no banco para " + nome);
		}
		
		else {
			try {
				gerenciador = JpaUtil.getEntityManager();
				gerenciador.getTransaction().begin();
				if(!novoNome.isEmpty()) {
					for (Cliente c : clientes) {
						
						gerenciador.merge(c).setNome(novoNome);
					}
					System.out.println("Nome alterado com sucesso!");
				}
				gerenciador.getTransaction().commit();
				Gerenciador.visualizar();
				
			} catch(Exception e) {
				gerenciador.getTransaction().rollback();
				e.printStackTrace();
			} finally {
				gerenciador.close();
			}
		}
		
		
	}
	public static void remover(Long id) {
		try {
			gerenciador = JpaUtil.getEntityManager();
			gerenciador.getTransaction().begin();
			Cliente cliente = gerenciador.find(Cliente.class, id);
			gerenciador.remove(cliente);
			gerenciador.getTransaction().commit();
		} catch (Exception e) {
			gerenciador.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			gerenciador.close();
		}
	}
}