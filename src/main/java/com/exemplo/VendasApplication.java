package com.exemplo;

import com.exemplo.domain.entity.Cliente;
import com.exemplo.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {
    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args ->{
            System.out.println("----Salvando Cliente----");
            clientes.save(new Cliente("Rafael"));
            clientes.save(new Cliente("Gabriela"));

            System.out.println("----Buscando Cliente----");
            List<Cliente> todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("----Atualizando Cliente----");
            todosClientes.forEach(c->{
                c.setNome(c.getNome()+" Atualizado");
                clientes.save(c);
            });

            System.out.println("----Buscando Cliente----");
            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("----Buscando Cliente por nome----");
            clientes.findByNomeLike("%Rafa%").forEach(System.out::println);

            System.out.println("----Buscando Cliente por nome Query----");
            clientes.encontrarPorNome("%Gabri%").forEach(System.out::println);

            System.out.println("----Deletando Clientes----");
            todosClientes.forEach(c->{
                clientes.delete(c);
            });

            System.out.println("----Buscando Cliente----");
            todosClientes = clientes.findAll();
            if (todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else{
                todosClientes.forEach(System.out::println);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
