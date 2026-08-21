package br.com.coti.apiprodutos.repositories;

import br.com.coti.apiprodutos.entities.Produto;
import br.com.coti.apiprodutos.entities.dtos.ProdutoRequestDto;
import br.com.coti.apiprodutos.entities.mappers.ProdutoMapper;
import br.com.coti.apiprodutos.factories.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProdutoRepository {

    private final ConnectionFactory connectionFactory;
    private final ProdutoMapper produtoMapper;

    public void inserir(Produto produto) throws Exception {
        try {
            var query = """
                    insert into produtos (nome, descricao, preco, quantidade)
                    values (?, ?, ?, ?);
                """;

            try  (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setString(1, produto.getNome());
                prepareStatement.setString(2, produto.getDescricao());
                prepareStatement.setDouble(3, produto.getPreco());
                prepareStatement.setInt(4, produto.getQuantidade());

                prepareStatement.execute();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean excluir(Integer id) throws Exception {
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

                return linhasAfetadas > 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Produto> listar() throws Exception {
        try {
            var query = """
                        select id, nome, descricao, preco, quantidade,
                               ativo, data_cadastro, data_atualizacao, data_exclusao
                       from produtos
                        where ativo = 1;
                    """;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                var resultSet = prepareStatement.executeQuery();

                var listaProdutos =  new ArrayList<Produto>();
                while (resultSet.next()) {
                    var produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setDescricao(resultSet.getString("descricao"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setQuantidade(resultSet.getInt("quantidade"));
                    produto.setAtivo(resultSet.getInt("ativo"));
                    produto.setDataCadastro(resultSet.getObject("data_cadastro", LocalDateTime.class));
                    produto.setDataExclusao(resultSet.getObject("data_exclusao", LocalDateTime.class));
                    produto.setDataAtualizacao(resultSet.getObject("data_atualizacao", LocalDateTime.class));

                    listaProdutos.add(produto);
                }

                return listaProdutos;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produto> buscarPorNome(String nome) throws Exception {
        try {
            var query = """
                        select id, nome, descricao, preco, quantidade,
                               ativo, data_cadastro, data_atualizacao, data_exclusao
                        from produtos
                        where ativo = 1
                          and nome ilike ?
                        order by nome;
                    """;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setString(1, "%"+nome+"%");

                var resultSet = prepareStatement.executeQuery();

                var listaProdutosPorNome =  new ArrayList<Produto>();
                while (resultSet.next()) {
                    var produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setDescricao(resultSet.getString("descricao"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setQuantidade(resultSet.getInt("quantidade"));
                    produto.setAtivo(resultSet.getInt("ativo"));
                    produto.setDataCadastro(resultSet.getObject("data_cadastro", LocalDateTime.class));
                    produto.setDataExclusao(resultSet.getObject("data_exclusao", LocalDateTime.class));
                    produto.setDataAtualizacao(resultSet.getObject("data_atualizacao", LocalDateTime.class));

                    listaProdutosPorNome.add(produto);
                }

                return listaProdutosPorNome;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Produto buscarPorId(Integer id) throws Exception {
        try {
            var query = """
                        select id, nome,  descricao, preco, quantidade,
                               ativo, data_cadastro, data_atualizacao, data_exclusao
                       from produtos
                        where ativo = 1
                          and id = ?;
                    """;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1, id);

                var resultSet = prepareStatement.executeQuery();
                var produto = new Produto();
                if (resultSet.next()) {
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setDescricao(resultSet.getString("descricao"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setQuantidade(resultSet.getInt("quantidade"));
                    produto.setAtivo(resultSet.getInt("ativo"));
                    produto.setDataCadastro(resultSet.getObject("data_cadastro", LocalDateTime.class));
                    produto.setDataExclusao(resultSet.getObject("data_exclusao", LocalDateTime.class));
                    produto.setDataAtualizacao(resultSet.getObject("data_atualizacao", LocalDateTime.class));
                }

                return produto;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean atualizar(Produto produto) {
        try {
            var query = """
                    update produtos
                    set nome = ?, descricao = ?,
                        preco = ?, quantidade = ?,
                        data_atualizacao = current_timestamp
                    where id = ?
                      and ativo = 1;
                """;

            // Verifica se o produto existe no banco de dados
            if (this.buscarPorId(produto.getId()) == null)
                return false;

            var linhasAfetadas = 0;

            try (var connection = connectionFactory.getConnection()) {
                var prepareStatement = connection.prepareStatement(query);
                prepareStatement.setString(1, produto.getNome());
                prepareStatement.setString(2, produto.getDescricao());
                prepareStatement.setDouble(3, produto.getPreco());
                prepareStatement.setInt(4, produto.getQuantidade());
                prepareStatement.setObject(5, produto.getId());

                linhasAfetadas = prepareStatement.executeUpdate();
            }

            return linhasAfetadas == 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
