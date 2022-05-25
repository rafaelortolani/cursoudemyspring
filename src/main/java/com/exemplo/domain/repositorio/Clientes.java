package com.exemplo.domain.repositorio;

import com.exemplo.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static String UPDATE = "update cliente set nome = ? where id = ?";
    private static String DELETE = "delete from cliente where id = ?";
    private static String SELECT_POR_NOME = "select * from cliente where nome like ?";

    @Transactional
    public Cliente salvar(Cliente cliente){
        //jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        entityManager.persist(cliente);
        return cliente;

    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        //jdbcTemplate.update(UPDATE,new Object[]{cliente.getNome(), cliente.getId()});
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
        //return jdbcTemplate.query(SELECT_POR_NOME, new Object[]{"%"+nome+"%"}, obterMapper());
        String jpql = "select c from Cliente c where c.nome like :nome";
        TypedQuery<Cliente> query =entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    @Transactional
    public void deletar (Cliente cliente){
        //jdbcTemplate.update(DELETE, new Object[]{cliente.getId()});
        if (!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        //return jdbcTemplate.query(SELECT_ALL, obterMapper());
        TypedQuery<Cliente> query =entityManager.createQuery("from Cliente", Cliente.class);
        return query.getResultList();
    }

    /*
    private RowMapper<Cliente> obterMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Cliente(rs.getInt("id"), rs.getString("nome"));
            }
        };
    }*/
}
