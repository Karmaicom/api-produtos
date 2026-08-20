package br.com.coti.apiprodutos.entities.dtos;

public record ProdutoRequestDto(
        String nome,
        String descricao,
        Double preco,
        Integer quantidade
) {
}
