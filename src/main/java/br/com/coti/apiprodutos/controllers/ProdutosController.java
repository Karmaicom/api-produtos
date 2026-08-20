package br.com.coti.apiprodutos.controllers;

import br.com.coti.apiprodutos.entities.mappers.ProdutoMapper;
import br.com.coti.apiprodutos.exceptions.CreateProdutoException;
import br.com.coti.apiprodutos.entities.Produto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoRequestDto;
import br.com.coti.apiprodutos.factories.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutosController {

    private final ConnectionFactory connectionFactory;
    private final ProdutoMapper produtoMapper;

    @PostMapping("criar")
    public ResponseEntity<?> criar(@RequestBody ProdutoRequestDto dto) {
        try {
            var produto = produtoMapper.toProduto(dto);

            var query = """
                    insert into produtos (nome, descricao, preco, quantidade)
                    values (?, ?, ?, ?)
                """;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setString(1, produto.getNome());
                prepareStatement.setString(2, produto.getDescricao());
                prepareStatement.setDouble(3, produto.getPreco());
                prepareStatement.setInt(4, produto.getQuantidade());

                prepareStatement.execute();

                return ResponseEntity.status(HttpStatus.OK).body("Produto '" + produto.getNome() + "' criado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CreateProdutoException("Erro na execução do método de criação de produto!", e.getCause());
        }
    }

    @PutMapping("alterar")
    public String alterar() {
        return "Produto alterado com sucesso";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            var query = """
                    update produtos
                    set ativo = 0, data_exclusao = current_timestamp
                    where id = ?
                      and ativo = 1;
                """;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1, id);

                int linhasAfetadas = prepareStatement.executeUpdate();

                if (linhasAfetadas > 0)
                    return ResponseEntity.status(HttpStatus.OK).body("Produto com id: '" + id + "', foi inativado com sucesso!");
                else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi inativado!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("listar")
    public String listar() {
        return "Produto consultado com sucesso";
    }

}
