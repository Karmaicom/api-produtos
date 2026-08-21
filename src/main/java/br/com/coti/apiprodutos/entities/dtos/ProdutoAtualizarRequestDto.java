package br.com.coti.apiprodutos.entities.dtos;

public record ProdutoAtualizarRequestDto(
        Integer id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade
) {
}
